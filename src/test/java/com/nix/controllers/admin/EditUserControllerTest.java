package com.nix.controllers.admin;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.springservices.interfaces.RoleDaoService;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.springtestconfig.HibernateConfigTetst;
import com.nix.utils.UserFormValidationHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@WebAppConfiguration
public class EditUserControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private User user;
	@Mock
	private UserDaoService userDaoMock;
	@Mock
	private RoleDaoService roleDaoMock;
	@Mock
	private UserFormValidationHelper userFormValidationHelperMock;
	@Mock
	private PasswordEncoder passwordEncoderMock;
	@InjectMocks
	private EditUserController editUserControllerMock;

	private MockMvc mockMvc;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();

		mockMvc = MockMvcBuilders.standaloneSetup(editUserControllerMock).build();

		user.setId(2L);
		user.setUsername("user");
		user.setEmail("user@user.user");
		user.setPassword("user");
		user.setConfirmPassword("user");
		user.setFirstName("user");
		user.setLastName("user");
		user.setBirthday(new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()));
		user.setUserRole(new Role(2L, "Cleaner"));

		when(userDaoMock.findById(user.getId())).thenReturn(user);
	}

	@Test
	public void applicationContextLoadingTest() {
		ServletContext servletContext = wac.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("editUserController"));
	}

	@Test
	public void getEditUserPage() throws Exception {
		this.mockMvc.perform(get("/edituser?id={id}", user.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("admin/edituserform"));
		
		verify(userDaoMock).findById(user.getId());
		verify(roleDaoMock).findAll();
	}

	@Test
	public void postEditUserPagePositive() throws Exception {

		expectedValidationStubbing(false, true);

		this.mockMvc.perform(post("/edituser")
				.flashAttr("user", user))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("homepageadmin"));

		verify(userDaoMock).update(user);
		verify(roleDaoMock).findByName("Cleaner");
		verifyMockMethodInvoking();
	}
	
	@Test
	public void postEditUserPageInvalidUserFields() throws Exception {
		
		expectedValidationStubbing(false, true);
				
		this.mockMvc.perform(post("/edituser")
				.flashAttr("user", new User()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("admin/edituserform"));
		
		verify(roleDaoMock).findAll();

	}

	@Test
	public void postEditUserPageExistingEmail() throws Exception {
		
		expectedValidationStubbing(true, true);
		
		User tempUser = new User();
		tempUser.setId(5L);

		when(userDaoMock.findByEmail(user.getEmail())).thenReturn(tempUser);
		
		this.mockMvc.perform(post("/edituser")
				.flashAttr("user", user))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("admin/edituserform"));

		verify(roleDaoMock).findAll();
		verifyMockMethodInvoking();
	}
	
	@Test
	public void postEditUserPageDifferentPass() throws Exception {
		
		expectedValidationStubbing(false, false);

		this.mockMvc.perform(post("/edituser")
				.flashAttr("user", user))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("admin/edituserform"));

		verify(roleDaoMock).findAll();
		verifyMockMethodInvoking();
	}
	
	private void verifyMockMethodInvoking() {
		verify(userFormValidationHelperMock).isEmailExists(anyString());
		verify(userFormValidationHelperMock).isPassTheSameValidation(anyString(), anyString());
	}

	private void expectedValidationStubbing (boolean isEmailExists,
			boolean isPassTheSameValidation) {
		when(userFormValidationHelperMock.isEmailExists(anyString())).thenReturn(isEmailExists);
		when(userFormValidationHelperMock.isPassTheSameValidation(anyString(), anyString()))
				.thenReturn(isPassTheSameValidation);
	}

}
