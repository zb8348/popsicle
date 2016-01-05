package com.pfw.popsicle.security.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfw.popsicle.security.entity.Resource;
import com.pfw.popsicle.security.service.ResourceService;

/**
 *
 * @author wulibing
 *
 */
@Service("pfwSecurityManager")
public class PfwSecurityManager {
//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ResourceService resourceService;
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
//	@Autowired
//	private UserService userService;
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
//	@Autowired
//	private RoleService roleService;
//	public void setRoleService(RoleService roleService) {
//		this.roleService = roleService;
//	}
	public List<Resource> findAllWithOrderNo() {
		return resourceService.findAllWithOrderNo();
	}
}
