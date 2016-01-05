package com.pfw.popsicle.security.core;

import java.io.Serializable;
import java.util.List;

import com.pfw.popsicle.security.entity.Role;

/**
 * 
 * @author wulibing
 *
 */
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = -1373760761780840081L;
	private String sesssionId;
	
	private long userId;
	private String loginName;
	private String name;
	private Boolean isAdmin=Boolean.FALSE;
	
	private List<Role> roles = null;
	
	public ShiroUser(long userId,String loginName,String name,Boolean isAdmin,List<Role> rList) {
		this.userId = userId;
		this.loginName = loginName;
		this.name = name;
		this.isAdmin=isAdmin;
		this.roles = rList;
	}

	public String getName() {
		return this.name;
	}
	
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getSesssionId() {
		return sesssionId;
	}

	public void setSesssionId(String sesssionId) {
		this.sesssionId = sesssionId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return this.loginName;
	}

	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return this.loginName.hashCode();
	}

	/**
	 * 重载equals,只比较loginName
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj!=null){
			return ((ShiroUser)obj).toString().equals(this.toString());
		}else{
			return false;
		}
	}
	
	
}
