package com.nix.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.nix.entity.User;

@Controller
public class UserHomePageController {

	@GetMapping("/homepageuser")
	public String loginPage(@ModelAttribute User user, Model model) {
		return "user/homepageuser";
	}

}
