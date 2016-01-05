package com.pfw.popsicle.front.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.front.entity.Transaction;
import com.pfw.popsicle.front.service.TransactGroupService;
import com.pfw.popsicle.front.service.TransactionService;
import com.pfw.popsicle.social.mongodb.entity.Base64Image;
import com.pfw.popsicle.social.mongodb.repostiory.Base64ImageRepository;

@Controller
public class FrontController {
	
	
	@Autowired
	private TransactGroupService transactGroupService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private Base64ImageRepository base64ImageRepository;
	
	@RequestMapping(value={"/share"})
	public String share(Model model){
		listTgs(model);
		return "front/pub/share";
	}
	
	private void listTgs(Model model){
		List<TransactGroup> tgs = transactGroupService.findByCreateTimeDesc(10);
		model.addAttribute("tgs", tgs);
		if(tgs!=null){
			List<String> thumbnails = new ArrayList<String>();
			
			Base64Image img = null;
			for (TransactGroup transactGroup : tgs) {
				img = base64ImageRepository.findOne(transactGroup.getId()+"");
				if(img!=null){
					thumbnails.add(img.getImg());
				}
			}
			
			model.addAttribute("thumbnails", thumbnails);
		}
	}
	
	@RequestMapping(value = "/tg/{id}")
	public String viewTg(@PathVariable(value = "id") long id, Model model) {
		TransactGroup tg = transactGroupService.getById(id);
		model.addAttribute("tg", tg);
		Base64Image img = base64ImageRepository.findOne(id + "");
		String thumbnail = "";
		if (img != null) {
			thumbnail = base64ImageRepository.findOne(id + "").getImg();
		}

		if (StringUtils.isNotBlank(thumbnail) && thumbnail.length() > 30) {
			model.addAttribute("thumbnail", thumbnail);
		}
		return "front/pub/dashboard-tg"; 
	}
	
	@RequestMapping(value = {"/tg/ts/{groupId}/{status}"})
	public @ResponseBody List<Transaction> tsWithStatus(@PathVariable(value="groupId")long groupId,@PathVariable(value="status") Integer status,Model model) {
		return transactionService.findByGroupId(groupId,status);
	}
}
