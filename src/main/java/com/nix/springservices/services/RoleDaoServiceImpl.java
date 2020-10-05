package com.nix.springservices.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nix.entity.Role;
import com.nix.hibernatedao.interfaces.RoleDao;
import com.nix.hibernatedao.repositories.HibernateRoleDao;
import com.nix.springservices.interfaces.RoleDaoService;

@Service
@Transactional
public class RoleDaoServiceImpl implements RoleDaoService {

	private static final Logger logger = LoggerFactory.getLogger(HibernateRoleDao.class);

	RoleDao roleDao;

	public RoleDaoServiceImpl() {
	}

	@Autowired
	public RoleDaoServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void create(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		try {
			roleDao.create(role);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon creating", e);
		}
	}

	@Override
	public void update(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		try {
			roleDao.update(role);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon updating", e);
		}
	}

	@Override
	public void remove(Role role) {
		if (role == null) {
			logger.error("cannot create a record from null reference",
					new NullPointerException("cannot create a record from null reference"));
			throw new NullPointerException("cannot create a record from null reference");
		}
		try {
			roleDao.remove(role);
		} catch (Exception e) {
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon removing", e);
		}
	}

	@Override
	public Role findByName(String name) {
		if (name == null) {
			logger.error("cannot parse null name", new NullPointerException("cannot parse null name"));
			throw new NullPointerException("cannot parse null name");
		}
		try {
			return roleDao.findByName(name);
		} catch (Exception e) {
			logger.error("something went wrong with search by role name", e);
			throw new IllegalArgumentException("something went wrong with search by role name", e);
		}
	}

	@Override
	public List<Role> findAll() {
		try {
			return roleDao.findAll();
		} catch (Exception e) {
			logger.error("something went wrong with global role search", e);
			throw new IllegalArgumentException("something went wrong with global role search", e);
		}
	}

}
