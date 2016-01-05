package com.pfw.popsicle.web.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.service.ResourceService;
import com.pfw.popsicle.security.service.RoleService;
import com.pfw.popsicle.security.service.UserService;

public class SecurityManager {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	private void init(){
		User user = userService.getUserByLoginName("admin");
		if(user==null||user.getId()<=0){
			user = new User();
			user.setIsAdmin(true);
			user.setLoginName("admin");
			user.setName("系统管理员");
			user.setPlainPassword("admin");
			userService.insertUser(user);
		}
		
		roleService.getRoleByCode("SYSTEM");
		Role role = roleService.getRoleByCode(RoleService.ROLE_CODE_SYSTEM_USER);
		long roleId = 0;
		if(role==null){
			role = new Role();
			role.setCode(RoleService.ROLE_CODE_SYSTEM_USER);
			role.setName("普通用户");
			role.setRemark("普通注册用户");
			roleService.save(role);
		} 
		
		roleId =role.getId();
		roleService.addRoleToUser(roleId,user.getId());
	}
}
