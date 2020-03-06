package com.wow.security.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class UserRole {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String role;
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
