package com.pfw.popsicle.front.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pfw.popsicle.front.core.BaseFrontController;
import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.front.service.TransactGroupService;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.social.mongodb.entity.Base64Image;
import com.pfw.popsicle.social.mongodb.repostiory.Base64ImageRepository;

/**
 * 仪表盘
 * @author wulibing
 *
 */
@Controller
public class DashboardController extends BaseFrontController {
	
	@Autowired
	private TransactGroupService transactGroupService;
	@Autowired
	private Base64ImageRepository base64ImageRepository;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model) {
		String loginName = PFWSecurityUtil.getCurrentUser().getLoginName();
		List<TransactGroup> tgs = transactGroupService.findByCreator(loginName);
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
		return BASE_PATH+"dashboard";
	}
}
