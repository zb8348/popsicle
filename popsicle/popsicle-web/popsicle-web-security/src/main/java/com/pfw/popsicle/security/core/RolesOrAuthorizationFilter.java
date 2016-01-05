package com.pfw.popsicle.security.core;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class RolesOrAuthorizationFilter extends AuthorizationFilter {

	public RolesOrAuthorizationFilter() {
	}

	public boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws IOException {
		Subject subject = getSubject(request, response);
		if (subject == null) {
			return false;
		}
		ShiroUser shiro = (ShiroUser) subject.getPrincipal();
		if (shiro == null) {
			return false;
		}
		if (shiro.getIsAdmin()!=null&&shiro.getIsAdmin().booleanValue()) {// 系统管理员有全部权限
			return true;
		}
		String rolesArray[] = (String[]) mappedValue;
		if (rolesArray == null || rolesArray.length == 0) {
			return true;
		} else {
			boolean isAccess = false;
			String rs[] = null;
			for (String role : rolesArray) {
				try {
					if (StringUtils.isNotBlank(role)) {
						if (role.contains(",")) {
							rs = role.split(",");
							if (rs.length > 0) {
								for (String r : rs) {
									if (StringUtils.isNotBlank(r)) {
										isAccess = subject.hasRole(r);
										if (isAccess) {
											break;
										}
									}
								}
							}
						}
					}
					isAccess = subject.hasRole(role);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isAccess) {
					break;
				}
			}
			return isAccess;
		}
	}

}
