package com.nix.controllers.user;

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
import com.nix.utils.ReCaptchaValidationHelper;
import com.nix.utils.UserFormValidationHelper;

@Controller
public class UserRegistrationController {

	@Autowired
	private UserDaoService userDao;
	@Autowired
	private RoleDaoService roleDao;
	@Autowired
	private UserFormValidationHelper userFormValidationHelper;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	ReCaptchaValidationHelper reCaptchaValidationHelper;

	@GetMapping("/registration")
	public String editUserFormDisplaying(Model model) {

		model.addAttribute("user", new User());

		return "user/userregistrationform";
	}

	@PostMapping("/registration")
	public String userAdding(@Valid @ModelAttribute User user, BindingResult br, Model model,
			@RequestParam(name = "g-recaptcha-response") String gRecaptchaResponse) {
	
		Boolean isLoginExists = userFormValidationHelper.isLoginExists(user.getUsername());
		model.addAttribute("isLoginExists", isLoginExists);
	
		Boolean isEmailExists = userFormValidationHelper.isEmailExists(user.getEmail());
		model.addAttribute("isEmailExists", isEmailExists);
	
		Boolean isPassTheSame = userFormValidationHelper.isPassTheSameValidation(user.getPassword(),
				user.getConfirmPassword());
		model.addAttribute("isPassTheSame", isPassTheSame);
	
		Boolean iAmNotRobot = reCaptchaValidationHelper.verify(gRecaptchaResponse);
		model.addAttribute("iAmNotRobot", iAmNotRobot);

		if (br.hasErrors() || isLoginExists || isEmailExists || !isPassTheSame || !iAmNotRobot) {
	
			return "user/userregistrationform";
		} else {
			user.setUserRole(roleDao.findByName(user.getUserRole().getName()));
			user.setPassword(passwordEncoder.encode(user.getPassword()));
	
			userDao.create(user);
			return "redirect:login";
		}
	}
}