package com.pfw.popsicle.sys;

import java.io.Serializable;


public class PUtil implements Serializable{
	private static final long serialVersionUID = 2937143680169922851L;

	public static String genId(){
		return "popsicle_html_"+System.currentTimeMillis();
	}
}
