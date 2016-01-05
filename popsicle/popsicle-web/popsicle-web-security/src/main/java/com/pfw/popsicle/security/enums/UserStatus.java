package com.pfw.popsicle.security.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 账号状态
 */
public enum UserStatus {
	natural("正常",1),disabled("不可用",0),overdue("过期",2);
	private String name;
	private int type;
	private UserStatus(String name,int type){
		this.name = name;
		this.type = type;
	}
	public int getType() {
		return type;
	}

	@Override
    public String toString() {
        return name;
    }
	public String getName() {
		return name;
	}
	public static Map<String,String> asMap() {
		Map<String,String> map = new HashMap<String, String>();
		UserStatus v[] = UserStatus.values();
		for (UserStatus userStatus : v) {
			map.put(userStatus.getType()+"", userStatus.name);
		}
		return map;
	}
}
