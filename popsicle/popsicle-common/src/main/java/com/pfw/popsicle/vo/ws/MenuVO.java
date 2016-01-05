package com.pfw.popsicle.vo.ws;

import java.io.Serializable;

public class MenuVO implements Serializable{
	private static final long serialVersionUID = -4348569280957009994L;
	
	private long id;
	private String text;
	private String url;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
