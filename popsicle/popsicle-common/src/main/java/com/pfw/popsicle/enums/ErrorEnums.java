package com.pfw.popsicle.enums;

public enum ErrorEnums {
	RuntimeError("RUNTIME EERROR","服务器异常"),
	ParmsError("Parms ERROR","请求参数错误"),
	LoginError("Login ERROR","账号验证失败"),
	AuthError("Auth ERROR","授权验证失败"),
	DbError("Db ERROR","数据保存失败"),
	DataError("Data ERROR","输入数据异常"),
	RequestError("REQUEST ERROR","请求异常");//
	
	private String code;
	private String message;
	private ErrorEnums(String code,String message){
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
