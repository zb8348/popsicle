package com.pfw.popsicle.security.controller.form;

import java.lang.reflect.InvocationTargetException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.pfw.popsicle.security.entity.Resource;

public class ResourceForm {
	
	private Long id;
	@NotNull
	@NotEmpty
	@Size(min = 3, max = 20)
	private String name;
	@NotNull
	@NotEmpty
	@Size(min = 3, max = 20)
	private String code;
	private String url;
	private String hasAnyRoles;
	@NotNull
	private int orderNo=0;//排序
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	
	
	
	public static ResourceForm resource2Form(Resource resource){
		ResourceForm resourceForm = new ResourceForm();
		try {
			BeanUtils.copyProperties(resourceForm, resource);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return resourceForm;
	}
}
