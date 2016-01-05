package com.pfw.popsicle.demo.crud.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pfw.popsicle.demo.crud.service.UserEntityService;
import com.pfw.popsicle.base.crud.CrudController;
import com.pfw.popsicle.demo.crud.entity.UserEntity;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;

/**
 *
 * @author wulibing
 *
 */
		
@Controller("UserEntityController")
@RequestMapping(value = "/demo/crud")
public class UserEntityController extends CrudController<UserEntityService,UserEntity>{
	@Autowired
	@Qualifier("userEntityService")
	private UserEntityService userEntityService;
	
	@Override
	public UserEntityService initService() {
		return userEntityService;
	}
	@Override
	public String getLoginName() {
		return PFWSecurityUtil.getLoginName();
	}
	@Override
	public UserEntity createNewEntity() {
		return new UserEntity();
	} 
	
	public String getMVCJSPath(){
		return "/demo/crud/UserEntityController.js";
	}
	
	@InitBinder("entity")   
	@Override
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }
}
