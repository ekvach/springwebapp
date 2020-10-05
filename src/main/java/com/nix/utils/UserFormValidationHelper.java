package com.nix.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;

@Component
public class UserFormValidationHelper {

	@Autowired
	private UserDaoService userDao;

	public String[] getErrorMessagesAsStringArray(Set<ConstraintViolation<User>> set) {
		String[] array = null;
		if (set.size() > 0) {
			array = new String[2];
			array[0] = set.iterator().next().getInvalidValue().toString();
			array[1] = set.iterator().next().getMessage();
		}
		return array;
	}

	public Boolean isPassTheSameValidation(String pass, String confirmPass) {

		if (pass.equals(confirmPass)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isLoginExists(String login) {
		return userDao.findByLogin(login) != null;
	}

	public Boolean isEmailExists(String email) {
		return userDao.findByEmail(email) != null;
	}

}
