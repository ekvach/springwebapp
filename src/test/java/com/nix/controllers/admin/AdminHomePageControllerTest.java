package com.nix.controllers.admin;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

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

import com.nix.entity.User;
import com.nix.springtestconfig.HibernateConfigTetst;
import com.nix.utils.UserListBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { HibernateConfigTetst.class })
@WebAppConfiguration
public class AdminHomePageControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	@Mock
	private UserListBuilder userListBuilderMock;
	@Mock
	List<User> userListMock;
	@InjectMocks
	private AdminHomePageController adminHomePageControllerMock;

	private MockMvc mockMvc;

	@SuppressWarnings("deprecation")
	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = webAppContextSetup(this.wac).build();
		mockMvc = MockMvcBuilders.standaloneSetup(adminHomePageControllerMock).build();

		when(userListBuilderMock.buildUserWithoutPassList()).thenReturn(userListMock);
	}

	@Test
	public void getAdminHomePage() throws Exception {

		this.mockMvc.perform(get("/homepageadmin"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("admin/homepageadmin"));
		
		verify(userListBuilderMock).buildUserWithoutPassList();
	}

}
