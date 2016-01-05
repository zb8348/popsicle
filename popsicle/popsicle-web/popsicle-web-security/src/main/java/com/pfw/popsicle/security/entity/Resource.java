package com.pfw.popsicle.security.entity;

import com.pfw.popsicle.base.BaseEntity;

/**
 * 资源类型:	1("子系统")-最多支持两级,2("菜单")-必须挂在子系统下面,3("url")-最终的请求,下面不能挂菜单;
 * @author wulibing
 */
public class Resource extends BaseEntity{
	private static final long serialVersionUID = 5641224846963666511L;
	

	public static final int  TYPE_SYS=1;//("子系统"),
	public static final int  TYPE_MENU=2;//("菜单"),
	public static final int  TYPE_URL=3;//("url");
	
	
	private String name;
	private String code;
	private String url;
	
	private String authUrl;//标注 /xxxx/** 下的所有url hasAnyRoles 的才算通过
	private String hasAnyRoles;//非强依赖常用参数,所以不用外键
	private int orderNo=0;//排序
	private int type;//资源类型:	1("子系统")-最多支持两级,2("菜单")-必须挂在子系统下面,3("url")-最终的请求,下面不能挂菜单;
	private String parentCode;

	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String authUrl) {
		this.authUrl = authUrl;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHasAnyRoles() {
		return hasAnyRoles;
	}
	public void setHasAnyRoles(String hasAnyRoles) {
		this.hasAnyRoles = hasAnyRoles;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
}
