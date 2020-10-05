package com.nix.hibernatedao.repositories;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nix.entity.User;
import com.nix.hibernatedao.interfaces.UserDao;

@Repository
@Transactional
public class HibernateUserDao implements UserDao {

	private SessionFactory sessionFactory;

	public HibernateUserDao() {
	}

	@Autowired
	public HibernateUserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void create(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public void update(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public void remove(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll() {
		Session session = sessionFactory.getCurrentSession();
		List<User> userList = (List<User>) session.createQuery("FROM APP_USERS").list();
		return userList;

	}

	@Override
	public User findByLogin(String login) {
		Session session = sessionFactory.getCurrentSession();
		Query sql = session.createQuery("FROM APP_USERS where login = :login");
		sql.setParameter("login", login);
		User user = (User) sql.uniqueResult();
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User findByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query sql = session.createQuery("FROM APP_USERS where email = :email");
		sql.setParameter("email", email);
		User user = (User) sql.uniqueResult();
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User findById(Long id) {
		Session session = sessionFactory.getCurrentSession();
		Query sql = session.createQuery("FROM APP_USERS where id = :id");
		sql.setParameter("id", id);
		User user = (User) sql.uniqueResult();
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Long getMaxUserId() {
		Session session = sessionFactory.getCurrentSession();
		Query sql = session.createQuery("select max(id) from APP_USERS");
		Long maxUserId = (Long) sql.uniqueResult();
		return maxUserId;
	}
}