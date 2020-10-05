package com.nix.springservices.services;

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
import com.nix.springservices.interfaces.RoleDaoService;
import com.nix.springtestconfig.HibernateConfigTetst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@WebAppConfiguration(value = "com.nix")
public class RoleDaoServiceImplTest {

	@Autowired
	private RoleDaoService roleDao;

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testCreateRoleExpected.xml")
	public void testCreate() throws Exception {
		roleDao.create(new Role(3L, "Director"));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testUpdateRoleExpected.xml")
	public void testUpdate() throws Exception {
		roleDao.update(new Role(1L, "Director"));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:testRemoveRoleExpected.xml")
	public void testRemove() throws Exception {
		roleDao.remove(new Role(3L));
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	public void testFindByName() throws Exception {
		Role role = roleDao.findByName("Admin");
		Assert.assertEquals("No records found by Role name", "Admin", role.getName());
		Assert.assertEquals("No records found by Role name", new Long(1), role.getId());
	}

	@Test
	@DatabaseSetup("classpath:testDbData.xml")
	@ExpectedDatabase(value = "classpath:testDbData.xml", table = "USER_ROLE")
	public void testFindAll() throws Exception {
		roleDao.findAll();
	}

	@Test(expected = NullPointerException.class)
	public void testCreateNull() {
		roleDao.create(null);
	}

	@Test(expected = NullPointerException.class)
	public void testUpdateNull() {
		roleDao.update(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveNull() {
		roleDao.remove(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFindByNameNull() {
		roleDao.findByName(null);
	}
}
