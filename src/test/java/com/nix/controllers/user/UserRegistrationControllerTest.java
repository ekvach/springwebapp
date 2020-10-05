package com.nix.controllers.user;

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
import com.nix.utils.ReCaptchaValidationHelper;
import com.nix.utils.UserFormValidationHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@WebAppConfiguration
public class UserRegistrationControllerTest {

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
	private ReCaptchaValidationHelper reCaptchaValidationHelperMock;
	@Mock
	private PasswordEncoder passwordEncoderMock;
	@InjectMocks
	private UserRegistrationController userRegistrationControllerMock;

	private MockMvc mockMvc;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();

		mockMvc = MockMvcBuilders.standaloneSetup(userRegistrationControllerMock).build();

		user.setId(2L);
		user.setUsername("user");
		user.setEmail("user@user.user");
		user.setPassword("user");
		user.setConfirmPassword("user");
		user.setFirstName("user");
		user.setLastName("user");
		user.setBirthday(new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()));
		user.setUserRole(new Role(2L, "Cleaner"));
	}

	@Test
	public void applicationContextLoadingTest() {
		ServletContext servletContext = wac.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(wac.getBean("userRegistrationController"));
	}

	@Test
	public void getUserRegistrationPage() throws Exception {
		this.mockMvc.perform(get("/registration"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));
		}

	@Test
	public void postUserRegistrationPagePositive() throws Exception {

		expectedValidationStubbing(false, false, true, true);

		this.mockMvc.perform(post("/registration")
				.flashAttr("user", user)
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(
						"login?isLoginExists=false&isEmailExists=false&isPassTheSame=true&iAmNotRobot=true"));

		verify(userDaoMock).create(user);
		verify(passwordEncoderMock).encode("user");
		verify(roleDaoMock).findByName("Cleaner");
		verifyMockMethodInvoking();
	}
	
	@Test
	public void postUserRegistrationPageInvalidUserFields() throws Exception {
		
		expectedValidationStubbing(false, false, true, true);
				
		this.mockMvc.perform(post("/registration")
				.flashAttr("user", new User())
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));
		
	}

	@Test
	public void postUserRegistrationPageExistingEmail() throws Exception {
		
		expectedValidationStubbing(false, true, true, true);

		this.mockMvc.perform(post("/registration")
				.flashAttr("user", user)
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));

		verifyMockMethodInvoking();
	}

	@Test
	public void postUserRegistrationPageExistingLogin() throws Exception {
		
		expectedValidationStubbing(true, false, true, true);

		this.mockMvc.perform(post("/registration")
				.flashAttr("user", user)
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));

		verifyMockMethodInvoking();
	}
	
	@Test
	public void postUserRegistrationPageDifferentPass() throws Exception {
		
		expectedValidationStubbing(false, false, false, true);

		this.mockMvc.perform(post("/registration")
				.flashAttr("user", user)
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));

		verifyMockMethodInvoking();
	}
	
	@Test
	public void postUserRegistrationPageCaptchaIsFailed() throws Exception {
		
		expectedValidationStubbing(false, false, true, false);

		this.mockMvc.perform(post("/registration")
				.flashAttr("user", user)
				.param("g-recaptcha-response", "true"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("user/userregistrationform"));

		verifyMockMethodInvoking();
	}
	
	private void verifyMockMethodInvoking() {
		verify(userFormValidationHelperMock).isLoginExists(anyString());
		verify(userFormValidationHelperMock).isEmailExists(anyString());
		verify(userFormValidationHelperMock).isPassTheSameValidation(anyString(), anyString());
		verify(reCaptchaValidationHelperMock).verify(anyString());
	}

	private void expectedValidationStubbing (boolean isLoginExists, boolean isEmailExists,
			boolean isPassTheSameValidation, boolean captchaResponse) {
		when(userFormValidationHelperMock.isLoginExists(anyString())).thenReturn(isLoginExists);
		when(userFormValidationHelperMock.isEmailExists(anyString())).thenReturn(isEmailExists);
		when(userFormValidationHelperMock.isPassTheSameValidation(anyString(), anyString()))
				.thenReturn(isPassTheSameValidation);
		when(reCaptchaValidationHelperMock.verify(anyString())).thenReturn(captchaResponse);
	}
}
