package com.nix.springservices.interfaces;

import java.util.List;

import com.nix.entity.User;

public interface UserDaoService {

	void create(User user);

	void update(User user);

	void remove(User user);

	List<User> findAll();

	User findByLogin(String login);

	User findByEmail(String email);

	User findById(Long id);

}
