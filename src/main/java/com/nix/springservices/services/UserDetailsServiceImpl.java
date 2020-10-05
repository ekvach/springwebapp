package com.nix.springservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nix.entity.User;
import com.nix.springservices.interfaces.UserDaoService;

@Service("UserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDaoService userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = userDao.findByLogin(username);
		if (u == null) {
			throw new UsernameNotFoundException("no such username found");
		} else {
			return new org.springframework.security.core.userdetails.User(u.getUsername(),
					u.getPassword(),
					u.isEnabled(),
					u.isAccountNonExpired(),
					u.isCredentialsNonExpired(),
					u.isAccountNonLocked(),
					u.getAuthorities());
		}
	}
}
