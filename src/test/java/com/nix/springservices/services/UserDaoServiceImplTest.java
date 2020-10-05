package com.nix.springservices.services;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.springtestconfig.HibernateConfigTetst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@WebAppConfiguration(value = "com.nix")
public class UserDaoServiceImplTest {

	@Autowired
	private UserDaoService userDao;

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testCreateUserExpected.xml")
	public void testCreateRequiredFields() throws Exception {
		userDao.create(new User(4L, "admin", "passwordA", "emailAdmin@email.com", new Role(1L)));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testCreateUserAllFieldsExpected.xml")
	public void testCreateAllFields() throws Exception {
		userDao.create(new User(new Long(4), "admin", "passwordA", "emailAdmin@email.com", "Jack", "Jonson",
				new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()), new Role(1L)));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testUpdateUsersExpected.xml")
	public void testUpdate() throws Exception {
		userDao.update(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 8, 10).getTimeInMillis()), new Role(2L)));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testRemoveUserExpected.xml")
	public void testRemove() throws Exception {
		userDao.remove(new User(1L, "Clerk", "newPass", "newemail@mail.com", "Rob", "Bobbins",
				new Date(new GregorianCalendar(2002, 10, 10).getTimeInMillis()), new Role(1L)));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(value = "classpath:testDbData.xml", table = "APP_USERS")
	public void testFindAll() throws Exception {
		userDao.findAll();
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	public void testFindByLogin() {
		User user = userDao.findByLogin("ekvach");
		Assert.assertEquals("No records found by Login", new Long(1), user.getId());
		Assert.assertEquals("No records found by Login", "testemail1@test.com", user.getEmail());
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	public void testFindById() {
		User user = userDao.findById(1L);
		Assert.assertEquals("No records found by Login", new Long(1), user.getId());
		Assert.assertEquals("No records found by Login", "testemail1@test.com", user.getEmail());
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	public void testFindByEmail() {
		User user = userDao.findByEmail("testemail1@test.com");
		Assert.assertEquals("No records found by Email", new Long(1), user.getId());
		Assert.assertEquals("No records found by Email", "ekvach", user.getUsername());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNull() {
		userDao.create(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateNull() {
		userDao.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		userDao.remove(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByLoginNull() {
		userDao.findByLogin(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByEmailNull() throws Exception {
		userDao.findByEmail(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdNull() throws Exception {
		userDao.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdNegative() throws Exception {
		userDao.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindByIdZero() throws Exception {
		userDao.findById(0L);
	}
}
