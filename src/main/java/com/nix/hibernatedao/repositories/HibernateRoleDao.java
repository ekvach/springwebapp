package com.nix.hibernatedao.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nix.entity.Role;
import com.nix.hibernatedao.interfaces.RoleDao;

@Repository
@Transactional
public class HibernateRoleDao implements RoleDao {

	private SessionFactory sessionFactory;

	public HibernateRoleDao() {
	}

	@Autowired
	public HibernateRoleDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.save(role);
	}

	@Override
	public void update(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.update(role);
	}

	@Override
	public void remove(Role role) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(role);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Role findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		Query sql = session.createQuery("FROM USER_ROLE where name = :name");
		sql.setParameter("name", name);
		Role role = (Role) sql.uniqueResult();
		return role;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAll() {
		Session session = sessionFactory.getCurrentSession();
		List<Role> roleList = (List<Role>) session.createQuery("FROM USER_ROLE").list();
		return roleList;
	}

}
