package com.pfw.popsicle.security.entity;

import java.beans.Transient;

import com.pfw.popsicle.base.BaseEntity;

/**
 * 
 * @author wulibing
 *
 */
public class User extends BaseEntity  {
	private static final long serialVersionUID = -1985488557135432327L;
	private String loginName;
	private String password;
	private String salt;
	private String name;
	private String email;
	private String imgUrl;
	private Boolean isAdmin=null;
	private Integer status;//=UserStatus.natural.getType();
	
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
	public Boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}




	private String plainPassword;
	@Transient
	public String getPlainPassword() {
		return plainPassword;
	}
	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}
}
