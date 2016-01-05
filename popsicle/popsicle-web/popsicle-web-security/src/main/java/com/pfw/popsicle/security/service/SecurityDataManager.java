package com.pfw.popsicle.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pfw.popsicle.security.entity.Resource;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.enums.UserStatus;

public class SecurityDataManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	
	public void init(){
		User user = userService.getUserByLoginName("admin");
		if(user==null||user.getId()<=0){
			user = new User();
			user.setIsAdmin(true);
			user.setLoginName("admin");
			user.setName("系统管理员");
			user.setPlainPassword("admin");
			user.setStatus(UserStatus.natural.getType());
			userService.insertUser(user);
			logger.info("初始化系统权限数据：系统管理员用户信息");
			
			Role role = roleService.getRoleByCode(RoleService.ROLE_CODE_SYSTEM_USER);
			if(role==null){
				role = new Role();
				role.setCode(RoleService.ROLE_CODE_SYSTEM_USER);
				role.setName("管理员");
				role.setRemark("管理员");
				roleService.save(role);
				logger.info("初始化系统权限数据：系统管理员角色信息");
			} 
			
			roleService.addRoleToUser(role.getId(),user.getId());
			logger.info("初始化系统权限数据：系统管理员角色用户关联信息");
			
			List<Long> resourceIds = new ArrayList<Long>();
			initRes(resourceIds);
			
			
			roleService.saveOrUpdateRoleResource(role.getId(), resourceIds);
		}
	}
	
	
	private void initRes(List<Long> resourceIds){
		initSys(resourceIds);
		initMens(resourceIds);
	}
	
	private void initSys(List<Long> resourceIds){
		Resource system = new Resource();
		system.setAuthUrl("/security/**");
		system.setCode("security");
		system.setHasAnyRoles("管理员");
		system.setName("权限系统");
		system.setOrderNo(0);
		system.setType(Resource.TYPE_SYS);
		system.setParentCode("0");
		resourceService.saveOrUpdate(system);
		resourceIds.add(system.getId());
		logger.info("初始化系统权限数据：系统菜单");
	}
	
	private void initMens(List<Long> resourceIds){
		Resource user = new Resource();
		user.setAuthUrl("/security/user/**");
		user.setCode("security_user");
		user.setHasAnyRoles("管理员");
		user.setName("用户管理");
		user.setOrderNo(0);
		user.setUrl("/security/user");
		user.setType(Resource.TYPE_MENU);
		user.setParentCode("security");
		resourceService.saveOrUpdate(user);
		resourceIds.add(user.getId());
		logger.info("初始化系统权限数据：用户菜单");
		
		Resource role = new Resource();
		role.setAuthUrl("/security/role/**");
		role.setCode("security_role");
		role.setHasAnyRoles("管理员");
		role.setName("角色管理");
		role.setOrderNo(1);
		role.setUrl("/security/role");
		role.setType(Resource.TYPE_MENU);
		role.setParentCode("security");
		resourceService.saveOrUpdate(role);
		resourceIds.add(role.getId());
		logger.info("初始化系统权限数据：角色菜单");
		
		Resource resource = new Resource();
		resource.setAuthUrl("/security/resource/**");
		resource.setCode("security_resource");
		resource.setHasAnyRoles("管理员");
		resource.setName("菜单管理");
		resource.setOrderNo(1);
		resource.setUrl("/security/resource");
		resource.setType(Resource.TYPE_MENU);
		resource.setParentCode("security");
		resourceService.saveOrUpdate(resource);
		resourceIds.add(resource.getId());
		logger.info("初始化系统权限数据：菜单管理");
	}
}
