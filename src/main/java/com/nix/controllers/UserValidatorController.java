package com.nix.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;

@Controller
public class UserValidatorController {

	@Autowired
	private UserDaoService userDao;
	@Autowired
	User user;

	@PostMapping("/uservalidator")
    public String userLoginProcedure(@RequestParam("username") String username,
                                     @RequestParam("password") String pass,
                                     Model model,
                                     HttpServletRequest request) {
		user = userDao.findByLogin(username);
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", user);
		switch (user.getUserRole().getName()) {
		case "Admin":
			return "redirect:homepageadmin";
		case "Cleaner":
		case "Director":
			return "redirect:homepageuser";
		default:
			session.invalidate();
			throw new IllegalArgumentException("Invalid User Role");
        }
    }
}
