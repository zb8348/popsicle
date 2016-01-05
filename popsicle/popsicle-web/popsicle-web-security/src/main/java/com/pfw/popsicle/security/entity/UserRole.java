package com.pfw.popsicle.security.entity;

import java.io.Serializable;

public class UserRole implements Serializable{

	private static final long serialVersionUID = 4555610392528566152L;
	private long userId;
	private long roleId;
	public UserRole(){}
	public UserRole(long userId,long roleId){
		this.userId = userId;
		this.roleId = roleId;
	}
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
}
