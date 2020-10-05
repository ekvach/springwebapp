package com.nix.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Entity(name = "APP_USERS")
@Repository
public class User implements UserDetails {

	@Id
	@Positive
	@Column(name = "ID")
	private Long id;
	@Column(name = "LOGIN")
	@NotNull
	@Size(min = 3)
	@NotBlank
	private String username;
	@Column(name = "PASSWORD")
	@NotNull
	@NotBlank
	@Size(min = 4)
	private String password;
	@Transient
	private String confirmPassword;
	@Email
	@NotBlank
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "FIRSTNAME")
	private String firstName;
	@Column(name = "LASTNAME")
	private String lastName;
	@Past
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY")
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date birthday;
	@Transient
	private int age;

	@ManyToOne
	@JoinColumn(name = "USER_ROLEID", nullable = false)
	@NotNull
	private Role userRole;

	public User() {
	}

	// Constructor with required fields
	public User(Long id, String username, String password, String email, Role userRole) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRole = userRole;
	}

	// Constructor with all the fields
	public User(Long id, String username, String password, String email, String firstName, String lastName,
			Date birthday, Role userRole) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.userRole = userRole;
	}

	// Constructor with fields from the site user creation form
	public User(String username, String password, String email, String firstName, String lastName, Date birthday,
			Role userRole) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.userRole = userRole;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Role getUserRole() {
		return userRole;
	}

	public void setUserRole(Role userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + username + ", password=" + password + ", email=" + email + ", firstName="
				+ firstName + ", lastName=" + lastName + ", birthday=" + birthday + ", age=" + age + ", userRole="
				+ userRole + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<Role> roles = new ArrayList<>();
		roles.add(userRole);
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
