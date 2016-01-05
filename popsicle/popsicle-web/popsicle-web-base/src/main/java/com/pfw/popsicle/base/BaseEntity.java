package com.pfw.popsicle.base;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体基本属性
 * @author wulibing
 *
 */
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = -5911311998556562539L;
	
	private long id;
	private Date createTime;
	private String creator;
	private Date modifiedTime;
	private String modifier;
	private boolean valid=true;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
