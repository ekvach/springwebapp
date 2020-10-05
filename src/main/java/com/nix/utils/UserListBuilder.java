package com.nix.utils;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;

@Component
public class UserListBuilder {

	private UserDaoService userDao;

	@Autowired
	public UserListBuilder(UserDaoService userDao) {
		this.userDao = userDao;
	}

	public List<User> buildFullUserList() {
		List<User> userList = userDao.findAll();
		for (User u : userList) {
			if (u.getBirthday() != null) {
				fillUserAge(u);
			}
		}
		return userList;
	}

	@SuppressWarnings("deprecation")
	public User fillUserAge(User u) {
		if (u.getBirthday() != null) {
			Date currentDate = new java.util.Date();
			Date userBirthday = new java.util.Date();
			userBirthday.setTime(u.getBirthday().getTime());
			u.setAge(currentDate.getYear() - userBirthday.getYear());
		}
		return u;
	}

	public List<User> buildUserWithoutPassList() {
		List<User> userList = userDao.findAll();
		for (User u : userList) {
			if (u.getBirthday() != null) {
				fillUserAge(u);
			}
			u = setNullUserPass(u);
		}
		return userList;
	}

	public User setNullUserPass(User u) {
		u.setPassword(null);
		return u;
	}

}
