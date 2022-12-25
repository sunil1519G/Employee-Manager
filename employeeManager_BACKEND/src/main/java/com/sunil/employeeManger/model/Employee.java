package com.sunil.employeeManger.model;

import java.io.Serializable;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class Employee implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;
	
	@NotEmpty
	@Size(min=3, message="User name should have atleast 3 characters")
	private String name;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@Size(min=2, message="Job Title should have atleast 2 characters")
	private String jobTitle;

	@NotEmpty
	@Size(min=10, max=10, message="Phone number should be 10 digits")
	private String phone;
	private String imageURL;
	
	@Column(nullable = false, updatable = false)
	private String empoyeeCode;

	public Employee() {
		super();
	}

	public Employee(Long id, String name, String email, String jobTitle, String phone, String imageURL,
			String empoyeeCode) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.jobTitle = jobTitle;
		this.phone = phone;
		this.imageURL = imageURL;
		this.empoyeeCode = empoyeeCode;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getEmpoyeeCode() {
		return empoyeeCode;
	}

	public void setEmpoyeeCode(String empoyeeCode) {
		this.empoyeeCode = empoyeeCode;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", jobTitle=" + jobTitle + ", phone="
				+ phone + ", imageURL=" + imageURL + ", empoyeeCode=" + empoyeeCode + "]";
	}
}
