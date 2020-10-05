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
import org.springframework.web.bind.annotation.RequestParam;

import com.nix.entity.User;
import com.nix.springservices.interfaces.RoleDaoService;
import com.nix.springservices.interfaces.UserDaoService;
import com.nix.utils.UserFormValidationHelper;

@Controller
public class EditUserController {

	@Autowired
	private UserDaoService userDao;
	@Autowired
	private RoleDaoService roleDao;
	@Autowired
	private UserFormValidationHelper userFormValidationHelper;
	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/edituser")
	public String editUserFormDisplaying(@RequestParam("id") Long id, Model model) {

		User user = userDao.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("roleList", roleDao.findAll());

		return "admin/edituserform";

	}

	@PostMapping("/edituser")
	public String userAdding(@Valid @ModelAttribute User user, BindingResult br, Model model) {

		Boolean isEmailExists = userFormValidationHelper.isEmailExists(user.getEmail());
		if (isEmailExists && userDao.findByEmail(user.getEmail()).getId().equals(user.getId())) {
			isEmailExists = false;
		}

		Boolean isPassTheSame = userFormValidationHelper.isPassTheSameValidation(user.getPassword(),
				user.getConfirmPassword());
		if (!isPassTheSame) {
			user.setPassword(null);
			user.setConfirmPassword(null);
		}

		if (br.hasErrors() || isEmailExists || !isPassTheSame) {
			model.addAttribute("isEmailExists", isEmailExists);
			model.addAttribute("roleList", roleDao.findAll());
			model.addAttribute("isPassTheSame", isPassTheSame);
			return "admin/edituserform";

		} else {
			user.setUserRole(roleDao.findByName(user.getUserRole().getName()));
			if (!(user.getPassword().equals(userDao.findById(user.getId()).getPassword()))) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			userDao.update(user);
			return "redirect:homepageadmin";
		}
	}
}
