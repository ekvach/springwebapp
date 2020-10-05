package com.nix.controllers.admin;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;
import java.util.GregorianCalendar;

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

import com.nix.entity.Role;
import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.springtestconfig.HibernateConfigTetst;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@WebAppConfiguration
public class DeleteUserControllerTest {

	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private User user;
	@Mock
	private UserDaoService userDaoMock;
	@InjectMocks
	private DeleteUserController deleteUserControllerMock;

	private MockMvc mockMvc;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = webAppContextSetup(this.wac).build();

		mockMvc = MockMvcBuilders.standaloneSetup(deleteUserControllerMock).build();
		
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
	public void getDeleteUser() throws Exception {
		
		this.mockMvc.perform(get("/deleteuser?id={id}", user.getId()))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl(
						"homepageadmin"));
		
		verify(userDaoMock).remove(user);

		
	}

}
