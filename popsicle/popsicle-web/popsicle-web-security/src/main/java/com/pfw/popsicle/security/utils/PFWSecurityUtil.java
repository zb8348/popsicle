package com.pfw.popsicle.security.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pfw.popsicle.security.core.ShiroUser;
import com.pfw.popsicle.security.entity.User;

/**
 * 系统基础权限工具类
 * 
 * @author wulibing
 *
 */
public class PFWSecurityUtil {
	private static Logger logger = LoggerFactory.getLogger(PFWSecurityUtil.class);

	/**
	 * 获取当前登录账号信息
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		Subject currentUser = SecurityUtils.getSubject();
		if (currentUser == null)
			return null;
		if (currentUser.getPrincipal() == null)
			return null;
		if (currentUser != null && currentUser.isAuthenticated() && currentUser.getPrincipal() instanceof ShiroUser) {
			ShiroUser shiro = (ShiroUser) currentUser.getPrincipal();
			User user = new User();
			user.setId(shiro.getUserId());
			user.setLoginName(shiro.toString());
			user.setName(shiro.getName());
			// user.setId(shiro.id);
			return user;
		} else {
			User user = new User();
			user.setLoginName(currentUser.getPrincipal().toString());
			return user;
		}
	}

	/**
	 * 获取当前登录账号信息
	 * 
	 * @return
	 */
	public static String getLoginName() {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser != null && currentUser.getPrincipal() != null)
				return currentUser.getPrincipal().toString();
			return null;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 系统登录
	 * 
	 * @param username
	 * @param password
	 * @param isRemerberMe
	 * @return
	 */
	public synchronized static boolean login(String username, String password) {
		// Example using most common scenario of username/password pair:
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// token.setRememberMe(isRemerberMe);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (Exception e) {
			logger.error("login faild", e);
			return false;
		}
		return true;
	}

	/**
	 * 系统退出
	 */
	public static void logout() {
		Subject sub = SecurityUtils.getSubject();
		if (sub != null) {
			logger.info("{} logout", sub.getPrincipal().toString());
			sub.logout();
		}
	}
}
