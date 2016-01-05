package com.pfw.popsicle.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.enums.Errors;
import com.pfw.popsicle.security.enums.UserStatus;
import com.pfw.popsicle.security.service.RoleService;
import com.pfw.popsicle.security.service.UserService;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.vo.RequestPageVO;
import com.pfw.popsicle.vo.RequestResultVO;

@Controller
@RequestMapping("/security/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@RequestMapping("")
	public String index(Model model){
		model.addAttribute("UserStatusMap", UserStatus.asMap());
		return "security/user";
	}
	@ModelAttribute("user")
	public User getUser(@RequestParam(value = "id", required = false) Long id) {
		if (id != null&&id>0) {
			return userService.findUserById(id);
		}
		return new User();
	}
	
	//添加,更新用户信息
	@RequestMapping("/saveOrUpdate")
	public @ResponseBody RequestResultVO<User> saveOrUpdate(@ModelAttribute("user") User user){
		if(user.getId()>0){
			user.setModifiedTime(DateUtil.getChineseTime());
			user.setModifier(PFWSecurityUtil.getLoginName());
		}else{
			user.setCreateTime(DateUtil.getChineseTime());
			user.setCreator(PFWSecurityUtil.getLoginName());
		}
		
		if(user.getId()>0){
			RequestResultVO<User> result = userService.updateUser(user);
			return result;
		}else{
			User userT = userService.getUserByLoginName(user.getLoginName());
			if(userT!=null&&userT.getId()>0){
				RequestResultVO<User> vo = new RequestResultVO<User>();
				vo.error(Errors.UserLoginNameExistsError.getCode(),Errors.UserLoginNameExistsError.getMessage());
				return vo;
			}else{
				return userService.insertUser(user);
			}
		}
	}
	
	//删除用户和用户拥有的角色
	@RequestMapping("/deleteUser")
	public @ResponseBody RequestResultVO<User> deleteUser(@RequestParam(value="userId") Long userId){
		return userService.deleteUser(userId);
	}

	@RequestMapping("/find")
	public @ResponseBody RequestPageVO<User> find(@RequestParam(value = "start", defaultValue="0") int start,@RequestParam(value = "length", defaultValue="10") int length, @ModelAttribute("user") User user){
		user.setIsAdmin(null);
		return userService.findPage(user,start,length);
	}
 
	@RequestMapping("/findRoleAndUserRole")
	public ModelAndView findRoleAndUserRole(@RequestParam(value="id") long id){
		User user = userService.findUserById(id);
		ModelAndView model = new ModelAndView();
		model.addObject("user", user);
		model.setViewName("security/userRole");
		return model;
	}
	@RequestMapping("/findRoleExitUserRole")
	public @ResponseBody RequestPageVO<Role> findRoleExitUserRole(@RequestParam(value = "start", defaultValue="0") int start,@RequestParam(value = "length", defaultValue="10") int length, @RequestParam(value="id") long id){
		Role r = new Role();
		r.setId(id);//这个存放的其实是用户id
		return roleService.findPageExitUserRole(r, start, length);
	}
	@RequestMapping("/findUserRole")
	public @ResponseBody RequestPageVO<Role> findUserRole(@RequestParam(value="id") long id){
		RequestPageVO<Role> result = new RequestPageVO<Role>();
		List<Role> userRole = userService.findRolesByUserId(id);
		result.setData(userRole);
		result.setStart(0);
		result.setLength(userRole.size());
		result.setRecordsFiltered(userRole.size());
		result.setRecordsTotal(userRole.size());
		return result;
	}
	//删除用户角色，传递的是角色id的集合
	@RequestMapping("/deleteUserRole")
	public @ResponseBody RequestResultVO<Role> deleteUserRole(@RequestParam(value="userId") Long userId,@RequestParam(value="roleId") List<Long> roleId){
		return userService.deleteUserRole(userId,roleId);
	}
	//增加用户角色
	@RequestMapping("/addUserRole")
	public @ResponseBody RequestResultVO<Role> addUserRole(@RequestParam(value="userId") Long userId,@RequestParam(value="roleId") List<Long> roleId){
		RequestResultVO<Role> result = userService.addUserRole(userId,roleId);
		return result;
	}
	
	
}
