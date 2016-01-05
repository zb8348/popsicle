package com.pfw.popsicle.web.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class UserForm {
	private String errors;

	@NotNull
	@NotEmpty(message = "用户名不能为空")
	@Size(min = 2, max = 30, message = "长度2到30")
	private String name;
	
	@NotNull
	@NotEmpty(message = "登录名不能为空")
	@Size(min = 4, max = 30, message = "长度4到30")
	private String loginName;
	
	@NotNull
	@NotEmpty
	@Size(min = 5, max = 20, message = "长度5到20")
	private String password;
	@NotNull
	@Email
	@Size(max = 50, message = "最长50")
	private String email;
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
