package com.pfw.popsicle.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pfw.popsicle.security.core.FilterFactoryBean;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.service.RoleService;
import com.pfw.popsicle.security.vo.RoleAndResourcesVO;
import com.pfw.popsicle.vo.RequestPageVO;
import com.pfw.popsicle.vo.RequestResultVO;

@Controller
@RequestMapping("/security/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("")
	public String index(){
		return "security/role";
	}
	@RequestMapping("/find")
	public @ResponseBody RequestPageVO<Role> find(@RequestParam(value = "start", defaultValue="0") int start,@RequestParam(value = "length", defaultValue="10") int length, @ModelAttribute("role") Role role){
//		List<ResourceTreeVO> resources = resourceService.loadTrees();
//		model.addAttribute("resources", resources);
		RequestPageVO<Role> page = roleService.findPage(role,start,length);
		return page;
	}
	
	@RequestMapping("/add")
	public @ResponseBody RequestResultVO<Role> add(@ModelAttribute("role") Role role){
		
		return roleService.add(role);
	}
	@RequestMapping("/findById")
	public @ResponseBody Role findById(@RequestParam(value="id") long id){
		return roleService.findById(id);
	}
	
	@RequestMapping("/update")
	public @ResponseBody RequestResultVO<Role> update(@ModelAttribute("role") Role role){
		return roleService.update(role);
	}
	@RequestMapping("/delete")
	public @ResponseBody RequestResultVO<Role> delete(@RequestParam(value="id") long id){
		
		return roleService.delete(id);
	}
	//查找角色的资源和未分配的资源
	@RequestMapping("/findAllResources")
	public ModelAndView findAllResources(@RequestParam(value="id") long id){
		ModelAndView model = new ModelAndView();
		model.setViewName("security/roleResource");
		model.addObject("role", roleService.findById(id));
		model.addObject("roleResources", roleService.findAllResources(id));
		return model;
	}
	//删除角色资源，传递的是角色id的集合
	@RequestMapping("/deleteRoleResource")
	public @ResponseBody RequestResultVO<RoleAndResourcesVO> deleteRoleResource(@RequestParam(value="roleId") Long roleId,@RequestParam(value="resourceId") List<Long> resourceId){
		return roleService.deleteRoleResource(roleId,resourceId);
	}
	//增加角色资源
	@RequestMapping("/addRoleResource")
	public @ResponseBody RequestResultVO<RoleAndResourcesVO> addRoleResource(@RequestParam(value="roleId") Long roleId,@RequestParam(value="resourceId") List<Long> resourceId){
		return roleService.saveOrUpdateRoleResource(roleId,resourceId);
	}
	
	
	@Autowired
	private FilterFactoryBean shiroFilter ;
	//不重启系统：动态更新授权信息
	@RequestMapping("/updatePermission")
	public @ResponseBody RequestResultVO<Boolean> updatePermission(){
		RequestResultVO<Boolean> r = new RequestResultVO<Boolean>();
		try {
			shiroFilter.updatePermission();
			r.success(Boolean.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			r.error("update fail", "系统全局授权信息更新失败");
		}
		return r;
	}
}
