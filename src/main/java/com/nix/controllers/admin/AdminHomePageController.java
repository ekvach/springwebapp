package com.nix.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nix.utils.UserListBuilder;

@Controller
public class AdminHomePageController {

	@Autowired
	private UserListBuilder userListBuilder;

		@GetMapping("/homepageadmin")
		public String loginPage(Model model) {
	
	// HERE SHOULD BE SOME SECURITY VERIFICATION
	
			model.addAttribute("userList", userListBuilder.buildUserWithoutPassList());
	
			return "admin/homepageadmin";
		}

}
