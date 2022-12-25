package com.sunil.employeeManger.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;

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

	public AppUserRole(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public AppUserRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AppUserRole [id=" + id + ", name=" + name + "]";
	}

}
