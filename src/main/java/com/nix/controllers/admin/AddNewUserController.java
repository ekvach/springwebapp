package com.nix.controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nix.entity.User;
import com.nix.springservices.interfaces.RoleDaoService;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.utils.UserFormValidationHelper;

@Controller
public class AddNewUserController {

	@Autowired
	private UserDaoService userDao;
	@Autowired
	private RoleDaoService roleDao;
	@Autowired
	private UserFormValidationHelper userFormValidationHelper;
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/addnewuser")
	public String editUserFormDisplaying(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("roleList", roleDao.findAll());

		return "admin/addnewuserform";
	}

	@PostMapping("/addnewuser")
	public String userAdding(@Valid @ModelAttribute User user, BindingResult br, Model model) {

		Boolean isLoginExists = userFormValidationHelper.isLoginExists(user.getUsername());
		model.addAttribute("isLoginExists", isLoginExists);
		Boolean isEmailExists = userFormValidationHelper.isEmailExists(user.getEmail());
		model.addAttribute("isEmailExists", isEmailExists);
		Boolean isPassTheSame = userFormValidationHelper.isPassTheSameValidation(user.getPassword(),
				user.getConfirmPassword());
		model.addAttribute("isPassTheSame", isPassTheSame);

		if (br.hasErrors() || isLoginExists || isEmailExists || !isPassTheSame) {
			model.addAttribute("roleList", roleDao.findAll());
			return "admin/addnewuserform";
		} else {
			user.setUserRole(roleDao.findByName(user.getUserRole().getName()));
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userDao.create(user);
			return "redirect:homepageadmin";
		}
	}
}
