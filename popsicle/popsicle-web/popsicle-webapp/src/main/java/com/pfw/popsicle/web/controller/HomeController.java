package com.pfw.popsicle.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.enums.UserStatus;
import com.pfw.popsicle.security.service.UserService;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;

@Controller
@RequestMapping(value={"/backend"})
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value={"","/home"})
	public String home(Model model){
		User user = PFWSecurityUtil.getCurrentUser();
		if(user!=null){
			user = userService.getUserByLoginName(user.getLoginName());
			if(user.getStatus()==UserStatus.natural.getType()){
				return "home";
			}
		}
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX+"/";
	}
}
