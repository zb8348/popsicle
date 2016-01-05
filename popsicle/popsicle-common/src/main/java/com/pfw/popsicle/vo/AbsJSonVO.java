package com.pfw.popsicle.vo;

import java.io.Serializable;

import com.pfw.popsicle.common.JsonUtils;

public abstract class AbsJSonVO implements Serializable{
	private static final long serialVersionUID = -7626723626359223536L;

	/**
	 * 将对象转化为json字符串
	 * @creator @wulibing
	 * @create @2015年4月16日 
	 * @return
	 */
	public String toJsonString(){
		return JsonUtils.toJsonString(this);
	}
}
