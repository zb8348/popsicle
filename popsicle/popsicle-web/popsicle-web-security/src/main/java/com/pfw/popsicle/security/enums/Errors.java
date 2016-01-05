package com.pfw.popsicle.security.enums;

public enum Errors {
	UserLoginNameExistsError("User LoginName Exists EERROR","用户名已存在");//
	
	private String code;
	private String message;
	private Errors(String code,String message){
		this.code = code;
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
