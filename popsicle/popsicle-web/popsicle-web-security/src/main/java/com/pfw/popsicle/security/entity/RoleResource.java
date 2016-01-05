package com.pfw.popsicle.security.entity;

import java.io.Serializable;

public class RoleResource  implements Serializable{

	private static final long serialVersionUID = -5549112855472670835L;
	private long resourceId;
	private long roleId;
	public long getResourceId() {
		return resourceId;
	}
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	
	

}
