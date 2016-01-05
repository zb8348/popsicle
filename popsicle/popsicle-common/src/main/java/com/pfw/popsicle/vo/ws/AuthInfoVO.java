package com.pfw.popsicle.vo.ws;

import java.io.Serializable;
import java.util.List;

public class AuthInfoVO implements Serializable{

	private static final long serialVersionUID = 1642954159936948538L;

	private String userName;
	private List<String> roleNames;
	private List<MenuVO> urls;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<String> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}
	public List<MenuVO> getUrls() {
		return urls;
	}
	public void setUrls(List<MenuVO> urls) {
		this.urls = urls;
	}
}
