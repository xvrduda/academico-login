package com.itb.mif3an.academicologin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity 
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
		
		// private: acesso dentro da própria classe
		// public: acesso livre a todas as classes
		// protected: acesso liberdo dentro das classes filhas (herança)
@Id
@GeneratedValue()
	private Long Id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
