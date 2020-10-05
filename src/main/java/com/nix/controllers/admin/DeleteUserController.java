package com.nix.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nix.springservices.interfaces.UserDaoService;

@Controller
public class DeleteUserController {

	@Autowired
	private UserDaoService userDao;

	@GetMapping("/deleteuser")
	public String editUserFormDisplaying(@RequestParam("id") Long id) {

		userDao.remove(userDao.findById(id));

		return "redirect:homepageadmin";

	}

}
