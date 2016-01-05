package com.pfw.popsicle.backend.web;

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

import com.pfw.popsicle.backend.service.TransactGroupCrudService;
import com.pfw.popsicle.base.crud.CrudController;
import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;

/**
 * 交易组
 * @author wulibing
 *
 */
@Controller("transactGroupCrudController")
@RequestMapping(value = "/backend/tg")
public class TransactGroupController extends CrudController<TransactGroupCrudService,TransactGroup>{
	
	@Autowired(required=true)
	@Qualifier("transactGroupCrudService")
	private TransactGroupCrudService transactGroupCrudService;

	@Override
	public TransactGroupCrudService initService() {
		return transactGroupCrudService;
	}
	
	@Override
	public String getMVCJSPath() {
		return "/backend/tg/TransactGroupController.js";
	}
	
	@Override
	public String getLoginName() {
		return PFWSecurityUtil.getLoginName();
	}
	@Override
	public TransactGroup createNewEntity() {
		return new TransactGroup();
	} 
	
	@InitBinder("entity")   
	@Override
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }

 

}
