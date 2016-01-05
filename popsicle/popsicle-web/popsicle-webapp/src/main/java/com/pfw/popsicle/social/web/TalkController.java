package com.pfw.popsicle.social.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.social.entity.Talk;
import com.pfw.popsicle.social.mongodb.entity.TalkContent;
import com.pfw.popsicle.social.service.TalkService;
import com.pfw.popsicle.vo.RequestResultVO;

@Controller
public class TalkController{
	@Autowired
	private TalkService talkService;
	
	@RequestMapping(value = "/viewTg/talks/{objectId}")
	public @ResponseBody List<Talk> listTalks(@PathVariable(value="objectId") long objectId,Model model){
		List<Talk> talks = talkService.findByCreateTimeDesc(objectId,null,10);
		return talks;
	}
	@RequestMapping(value = "/view/latest/{objectId}/{lastId}")
	public @ResponseBody List<Talk> lastTalks(@PathVariable(value="objectId")long objectId,@PathVariable(value="lastId")Long lastId,Model model){
		List<Talk> talks = talkService.findByCreateTimeDesc(objectId,lastId,10);
		return talks;
	}
	@RequestMapping(value = "/view/before/{objectId}/{lastId}")
	public @ResponseBody List<Talk> beforeTalks(@PathVariable(value="objectId")long objectId,@PathVariable(value="lastId")Long lastId,Model model){
		List<Talk> talks = talkService.findByCreateTimeDescBeforeId(objectId,lastId,10);
		return talks;
	}
	
	
	
	@RequestMapping(value = "/talk/longContent/{id}/{key}")
	public @ResponseBody TalkContent lastTalks(@PathVariable(value="id")long id,@PathVariable(value="key")String key,Model model){
		TalkContent tc = talkService.getLongContent(key);
//		if(tc!=null&&id==tc.getTargetId()){
//			return tc;
//		}
		return tc;
	}
	
	@InitBinder("talk")   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }  
	
	@RequestMapping(value = "/front/talk/save",method = RequestMethod.POST)
	public @ResponseBody RequestResultVO<Talk> saveTalk(@ModelAttribute("talk") Talk talk){
		RequestResultVO<Talk> rr = new RequestResultVO<Talk>();
		try {
			User u = PFWSecurityUtil.getCurrentUser();
			talk.setCreateTime(DateUtil.getChineseTime());
			talk.setTalker(u.getLoginName());
			talk.setTalkerId(u.getId());
			talkService.save(talk);
			rr.success(talk);
		} catch (Exception e) {
			rr.error("save fail", e.getMessage());
		}
		return rr;
	}
}
