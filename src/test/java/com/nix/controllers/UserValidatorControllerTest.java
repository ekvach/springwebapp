package com.nix.controllers;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.springtestconfig.HibernateConfigTetst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@WebAppConfiguration
public class UserValidatorControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private User user;
	@Mock
	private UserDaoService userDaoMock;
	@Mock
	private HttpSession sessionMock;
	@InjectMocks
	private UserValidatorController userValidatorControllerMock;

	private MockMvc mockMvc;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();

		mockMvc = MockMvcBuilders.standaloneSetup(userValidatorControllerMock).build();

		user.setId(2L);
		user.setUsername("user");
		user.setEmail("user@user.user");
		user.setPassword("user");
		user.setConfirmPassword("user");
		user.setFirstName("user");
		user.setLastName("user");
		user.setBirthday(new Date(new GregorianCalendar(2000, 8, 05).getTimeInMillis()));
		
		when(userDaoMock.findByLogin(anyString())).thenReturn(user);
		
	}
	
	@Test
	public void adminRoleRedirection() throws Exception {
		
		user.setUserRole(new Role(1L, "Admin"));
		
		this.mockMvc.perform(post("/uservalidator")
				.param("username", user.getUsername())
				.param("password", user.getPassword()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("homepageadmin"));
		
		verify(userDaoMock).findByLogin(user.getUsername());
	}
	
	@Test
	public void cleanerRoleRedirection() throws Exception {
		
		user.setUserRole(new Role(1L, "Cleaner"));
		
		this.mockMvc.perform(post("/uservalidator")
				.param("username", user.getUsername())
				.param("password", user.getPassword()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("homepageuser"));
		
		verify(userDaoMock).findByLogin(user.getUsername());
	}
	
	@Test
	public void directorRoleRedirection() throws Exception {
		
		user.setUserRole(new Role(1L, "Director"));
		
		this.mockMvc.perform(post("/uservalidator")
				.param("username", user.getUsername())
				.param("password", user.getPassword()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("homepageuser"));
		
		verify(userDaoMock).findByLogin(user.getUsername());
	}
	
	@Test(expected = NestedServletException.class)
	public void unknownRoleRedirection() throws Exception {
		
		user.setUserRole(new Role(1L, "Uknown"));
		
		this.mockMvc.perform(post("/uservalidator")
				.param("username", user.getUsername())
				.param("password", user.getPassword()))
				.andDo(print());

		verify(sessionMock).invalidate();
	}
	
}
