package com.pfw.popsicle.web.controller.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
	private String errors;

	@NotNull
	@NotEmpty(message = "用户名不能为空")
	@Size(min = 4, max = 20, message = "长度4到20")
	private String username;

	@NotNull
	@NotEmpty
	@Size(min = 5, max = 20, message = "长度5到20")
	private String password;


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

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}
}
