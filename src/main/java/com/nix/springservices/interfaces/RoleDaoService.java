package com.nix.springservices.interfaces;

import java.util.List;

import com.nix.entity.Role;

public interface RoleDaoService {

	void create(Role role);

	void update(Role role);

	void remove(Role role);

	Role findByName(String name);
	
	List<Role> findAll();
}
