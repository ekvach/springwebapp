package com.nix.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity(name = "USER_ROLE")
@Repository
public class Role implements GrantedAuthority {

	@Id
	@Positive
	@Column
	private Long id;

	@Column
	@NotNull
	private String name;

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}

	public Role(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
//        return this.name.toUpperCase();
		return String.format("ROLE_%s", name.toUpperCase());
	}
}
