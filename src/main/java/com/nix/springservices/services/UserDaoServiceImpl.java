package com.nix.springservices.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nix.entity.User;
import com.nix.hibernatedao.interfaces.UserDao;
import com.nix.hibernatedao.repositories.HibernateUserDao;
import com.nix.springservices.interfaces.UserDaoService;

@Service
@Transactional
public class UserDaoServiceImpl implements UserDaoService {

	private static final Logger logger = LoggerFactory.getLogger(HibernateUserDao.class);

	private UserDao userDao;

	public UserDaoServiceImpl() {
	}

	@Autowired
	public UserDaoServiceImpl(UserDao UserDao) {
		this.userDao = UserDao;
	}

	@Override
	public void create(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		try {
			if (user.getId() == null) {
				user.setId(userDao.getMaxUserId() + 1);
			}
			userDao.create(user);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon creating", e);
		}
	}

	@Override
	public void update(User user) {
		if (user == null) {
			logger.error("cannot update a record from null reference",
					new NullPointerException("cannot update a record from null reference"));
			throw new NullPointerException("cannot update a record from null reference");
		}
		try {
			userDao.update(user);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon updating", e);
		}
	}

	@Override
	public void remove(User user) {
		if (user == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot find a record from null reference"));
			throw new NullPointerException("cannot find a record from null reference");
		}
		try {
			userDao.remove(user);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon removing", e);
		}
	}

	@Override
	public List<User> findAll() {
		try {
			return userDao.findAll();
		} catch (Exception e) {
			logger.error("something went wrong with global search", e);
			throw new IllegalArgumentException("something went wrong with global search", e);
		}
	}

	@Override
	public User findByLogin(String login) {
		if (login == null) {
			logger.error("cannot parse null login", new NullPointerException("cannot parse null login"));
			throw new NullPointerException("cannot parse null login");
		}
		try {
			return userDao.findByLogin(login);
		} catch (Exception e) {
			logger.error("something went wrong with search by login", e);
			throw new IllegalArgumentException("something went wrong with search by login", e);
		}
	}

	@Override
	public User findByEmail(String email) {
		if (email == null) {
			logger.error("cannot parse null email", new NullPointerException("cannot parse null email"));
			throw new NullPointerException("cannot parse null email");
		}
		try {
			return userDao.findByEmail(email);
		} catch (Exception e) {
			logger.error("something went wrong with search by email", e);
			throw new IllegalArgumentException("something went wrong with search by email", e);
		}
	}

	@Override
	public User findById(Long id) {
		if (id == null || id <= 0) {
			logger.error("Id should be a positive number",
					new IllegalArgumentException("Id should be a positive number"));
			throw new IllegalArgumentException("Id should be a positive number");
		}
		try {
			return userDao.findById(id);
		} catch (Exception e) {
			logger.error("something went wrong with search by id", e);
			throw new IllegalArgumentException("something went wrong with search by id", e);
		}
	}
}