package com.curso.devdojo.model;

import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Entity
public class Student extends AbstractEntity {
	
	@NotEmpty
	private String name;
	@NotEmpty
	@Email(message = "Invalid Email")
	private String email;
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

}
