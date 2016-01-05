package com.pfw.popsicle.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfw.popsicle.security.controller.form.ResourceForm;
import com.pfw.popsicle.security.entity.Resource;
import com.pfw.popsicle.security.service.ResourceService;
import com.pfw.popsicle.security.vo.ResourceTreeVO;
import com.pfw.popsicle.vo.RequestResultVO;

@Controller
@RequestMapping("/security/resource")
public class ResourceController {
	
//	@ModelAttribute("resource")
//	public Resource getResource(
//			@RequestParam(value = "id", required = false) Long id) {
////		if (id != null&&id>0) {
////			return resourceService.getById(id);
////		}
//		return new Resource();
//	}
	
	@InitBinder("resource")    
    public void initBinder1(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("resource.");    
    } 
	
	@Autowired
	private ResourceService resourceService;
	@RequestMapping("")
	public String list(Model model){
		List<ResourceTreeVO> resources = resourceService.getTrees();
		model.addAttribute("resources", resources);
		return "security/resource";
	}
	
	
	@RequestMapping("/loadTree")
	public @ResponseBody List<ResourceTreeVO> loadTree(){
		return resourceService.getTrees();
	}
	
	@RequestMapping("/loadRoleTree")
	public @ResponseBody List<ResourceTreeVO> loadRoleTree(@RequestParam(value = "id") Long id){
		return resourceService.getRoleTree(id);
	}
	
	@RequestMapping("input")
	public String input(@RequestParam(value = "id", required = false) Long id,@ModelAttribute("resource")Resource resource,Model model) {
		model.addAttribute("resourceForm", ResourceForm.resource2Form(resource));
		return "security/resource-input";
	}
	
	@RequestMapping(value="/saveOrUpdate")
	public @ResponseBody RequestResultVO<Resource> saveOrUpdate(@ModelAttribute("resource") Resource resource,Model model) {
		RequestResultVO<Resource> vo = new RequestResultVO<Resource>();
		Resource r = resourceService.saveOrUpdate(resource);
		Long id = r.getId();
		if(id<=0){
			vo.setErrorCode(id+"");
		}else{
			resource.setId(id);
			vo.setResult(resource);
			vo.setSuccess(true);
		}
		return vo;
	}
	@RequestMapping(value="/deleteResource")
	public @ResponseBody RequestResultVO<Resource> deleteResource(@RequestParam(value = "code") String code) {
		RequestResultVO<Resource> vo = new RequestResultVO<Resource>();
		int resultCode = resourceService.deleteByCode(code);
		if(resultCode > 0){
			vo.setSuccess(true);
		}else{
			vo.setErrorMessage("删除失败");
		}
		return vo;
	}
	@RequestMapping(value="/selectResource")
	public @ResponseBody RequestResultVO<Resource> selectResource(@ModelAttribute("resource") Resource resource,Model model) {
		RequestResultVO<Resource> vo = new RequestResultVO<Resource>();
		Resource r = resourceService.selectResourceByCode(resource.getCode());
		vo.setResult(r);
		return vo;
	}
}
