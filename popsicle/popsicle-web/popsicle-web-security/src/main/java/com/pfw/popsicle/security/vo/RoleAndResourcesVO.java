package com.pfw.popsicle.security.vo;

import java.util.List;

import com.pfw.popsicle.vo.AbsJSonVO;

public class RoleAndResourcesVO extends AbsJSonVO{

	private static final long serialVersionUID = 1L;
	
	private Long roleId;
	private String roleName;
	private Long resourceId;
	private String resourceName;
	private int resourceType;
	private String url;
	private List<Long> roleResourceIds;//角色批量添加或删除的资源id
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getResourceId() {
		return resourceId;
	}
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public int getResourceType() {
		return resourceType;
	}
	public void setResourceType(int resourceType) {
		this.resourceType = resourceType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Long> getRoleResourceIds() {
		return roleResourceIds;
	}
	public void setRoleResourceIds(List<Long> roleResourceIds) {
		this.roleResourceIds = roleResourceIds;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
