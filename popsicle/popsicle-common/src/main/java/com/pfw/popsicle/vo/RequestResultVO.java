package com.pfw.popsicle.vo;

import java.io.Serializable;

import com.pfw.popsicle.enums.ErrorEnums;

public class RequestResultVO<T> implements Serializable{
	private static final long serialVersionUID = -72731801745817847L;

	private boolean success=false;
	private String errorCode;
	private String errorMessage;
	private T result;
	
	public void error(ErrorEnums error){
		this.success=false;
		this.errorCode=error.getCode();
		this.errorMessage=error.getMessage();
	}
	
	public void error(String errorCode,String message){
		this.success=false;
		this.errorCode=errorCode;
		this.errorMessage=message;
	}
	
	public void success(T result){
		this.success=true;
		this.result=result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
