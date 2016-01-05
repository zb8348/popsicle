package com.pfw.popsicle.security.entity;

import com.pfw.popsicle.base.BaseEntity;

/**
 * 
 * @author wulibing
 *
 */
public class Role extends BaseEntity  {
	private static final long serialVersionUID = -8912558631200794231L;
	private String name;
	private String code;
	private String remark;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
