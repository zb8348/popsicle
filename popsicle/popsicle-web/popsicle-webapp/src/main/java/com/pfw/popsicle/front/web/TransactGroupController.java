package com.pfw.popsicle.front.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.common.JsonUtils;
import com.pfw.popsicle.front.core.BaseFrontController;
import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.front.entity.Transaction;
import com.pfw.popsicle.front.service.TransactGroupService;
import com.pfw.popsicle.front.service.TransactionService;
import com.pfw.popsicle.front.vo.TgVO;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.social.mongodb.entity.Base64Image;
import com.pfw.popsicle.social.mongodb.repostiory.Base64ImageRepository;
import com.pfw.popsicle.vo.RequestResultVO;

/**
 * 交易组
 * @author wulibing
 *
 */
@Controller
public class TransactGroupController extends BaseFrontController{

	@Autowired
	private TransactGroupService transactGroupService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private Base64ImageRepository base64ImageRepository;
	@RequestMapping(value = "/tg/{id}")
	public String viewTg(@PathVariable(value="id")long id,Model model){
		TransactGroup tg = transactGroupService.getById(id);
		model.addAttribute("tg", tg);
		
		Base64Image img = base64ImageRepository.findOne(id + "");
		String thumbnail = "";
		if (img != null) {
			thumbnail = base64ImageRepository.findOne(id + "").getImg();
		}
		
		if(StringUtils.isNotBlank(thumbnail)&&thumbnail.length()>30){
			model.addAttribute("thumbnail", thumbnail);
		}
		
		
		return BASE_PATH+"dashboard-tg"; 
	}
	
	@RequestMapping(value = "/tg/input")
	public String toAddTg(Model model){
		return BASE_PATH+"dashboard-tg-input"; 
	}
	
	
	@RequestMapping(value = "/tg/importJson",method=RequestMethod.POST)
	public String importJson(@RequestParam(value="tg",required=true) String tg) {
		User user = PFWSecurityUtil.getCurrentUser();
		if(tg!=null){
			TgVO tgVO = (TgVO)JsonUtils.toObject(tg, TgVO.class);
			TransactGroup tgEntity = (TransactGroup)tgVO;
			tgEntity.setUserId(user.getId());
			tgEntity.setCreator(user.getLoginName());
			tgEntity.setCreateTime(DateUtil.getChineseTime());
			
			List<Transaction> transactions = tgVO.getTransactions();
			
			tgEntity = transactGroupService.analysis(tgEntity,transactions);
			transactGroupService.save(tgEntity);
			
			if(tgEntity.getId()>0&&transactions!=null&&transactions.size()>0){
				transactionService.batchSave(tgEntity.getId(),transactions);
			}
			return InternalResourceViewResolver.REDIRECT_URL_PREFIX+ "/"+BASE_PATH+"tg" + "/"+tgEntity.getId();  
		}
		  
		return BASE_PATH+"dashboard";
	}
	
	
	@RequestMapping(value = "/tg/update/{id}")
	public String updateTg(@PathVariable(value="id")long id,Model model) {
		TransactGroup tgEntity = transactGroupService.getById(id);
		model.addAttribute("tg", tgEntity);
		return BASE_PATH+"dashboard-tg-update";
	}
	@RequestMapping(value = "/tg/updateInfo",method=RequestMethod.POST)
	public @ResponseBody RequestResultVO<TransactGroup> updateTg(@ModelAttribute("tg") TransactGroup tg, Model model) {
		return transactGroupService.update(tg);
	}
	
	@RequestMapping(value = "/tg/remove/{id}")
	public String removeTg(@PathVariable(value="id")long id,Model model){
		transactGroupService.remove(id);
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX+"/"+BASE_PATH+"dashboard"; 
	}
	@RequestMapping(value = "/tg/savePic/{groupId}")
	public @ResponseBody void savePic(@PathVariable(value="groupId")long groupId,@RequestParam(value="base64") String base64) {
		Base64Image img = new Base64Image();
		img.setName(groupId+"");
		img.setImg(base64);
		img.setCreateTime(DateUtil.getChineseTime());
		base64ImageRepository.save(img);
	}
	
	
	
	@RequestMapping(value = {"/tg/ts/{groupId}/{status}"})
	public @ResponseBody List<Transaction> tsWithStatus(@PathVariable(value="groupId")long groupId,@PathVariable(value="status") Integer status,Model model) {
		return transactionService.findByGroupId(groupId,status);
	}
	@RequestMapping(value = {"/tg/ts/{groupId}"})
	public @ResponseBody List<Transaction> ts(@PathVariable(value="groupId")long groupId,Model model) {
		return transactionService.findByGroupId(groupId,null);
	}
	@RequestMapping(value = "/tg/ts/delete/{groupId}",method=RequestMethod.POST)
	public @ResponseBody RequestResultVO<TransactGroup> deleteTS(@PathVariable(value="groupId")long groupId,@RequestParam(value="ids[]",required=true)  List<Long> ids, Model model) {
		return transactGroupService.deleteTs(ids,groupId);
	}
	
	
	@InitBinder("transaction")   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }  
	
	@RequestMapping(value = "/tg/ts/updateInfo",method=RequestMethod.POST)
	public @ResponseBody RequestResultVO<TransactGroup> updateTS(@ModelAttribute("transaction")Transaction t, Model model) {
		return transactGroupService.updateT(t);
	}
	
	@RequestMapping(value = "/tg/saveAs",method=RequestMethod.POST)
	public String saveAs(@RequestParam(value="groupId")long groupId,@RequestParam(value="name")String name,@RequestParam(value="ids[]",required=true)  List<Long> ids, Model model) {
		User user = PFWSecurityUtil.getCurrentUser();
		if(groupId>0&&StringUtils.isNotBlank(name)&&ids!=null&&ids.size()>0){
			TransactGroup tgEntity = transactGroupService.getById(groupId);
			tgEntity.setId(null);
			tgEntity.setName(name);
			tgEntity.setUserId(user.getId());
			tgEntity.setCreator(user.getLoginName());
			tgEntity.setCreateTime(DateUtil.getChineseTime());
			tgEntity.setParentId(groupId);
			
			List<Transaction> transactions = transactionService.findByIds(ids);
			
			tgEntity = transactGroupService.analysis(tgEntity,transactions);
			transactGroupService.save(tgEntity);
			
			if(tgEntity.getId()>0&&transactions!=null&&transactions.size()>0){
				transactionService.batchSave(tgEntity.getId(),transactions);
			}
			return InternalResourceViewResolver.REDIRECT_URL_PREFIX+ "/"+BASE_PATH+"tg" + "/"+tgEntity.getId();  
		}
		return BASE_PATH+"dashboard"; 
	}
}
