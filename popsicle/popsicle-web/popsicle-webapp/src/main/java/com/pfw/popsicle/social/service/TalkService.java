package com.pfw.popsicle.social.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.social.dao.TalkDao;
import com.pfw.popsicle.social.entity.Talk;
import com.pfw.popsicle.social.mongodb.entity.TalkContent;
import com.pfw.popsicle.social.mongodb.repostiory.TalkContentRepository;

@Service
@Transactional
public class TalkService {

	@Autowired
	private TalkDao talkDao;
	@Autowired
	private TalkContentRepository talkContentRepository;
	
	public List<Talk> findByCreateTimeDesc(long objectId, Long lastId, int limit) {
		List<Talk> talks = talkDao.findByCreateTimeDesc(objectId,lastId,limit);
		return talks;
	}
	public List<Talk> findByCreateTimeDescBeforeId(long objectId, Long lastId, int limit) {
		return talkDao.findByCreateTimeDescBeforeId(objectId,lastId,limit);
	}
	public TalkContent getLongContent(String key) {
		if(StringUtils.isNotBlank(key)){
			return talkContentRepository.findOne(key);
		}
		return null;
	}

	public void save(Talk talk) {
		if(talk.getObjectId()<=0||talk.getContent()==null||talk.getTalkerId()<=0){
			return ;
		}
		String c = talk.getContent();
		if(c!=null&&c.length()>500){
			String longContentKey = "talk"+talk.getObjectId()+System.currentTimeMillis();
			String content = new String(c);
			c = c.substring(0, 200)+"......";
			talk.setContent(c);
			talk.setLongContent(true);
			talk.setLongContentKey(longContentKey);
			talkDao.save(talk);
			
			TalkContent tc = new TalkContent();
			tc.setContent(content);
			tc.setKey(longContentKey);
			tc.setTargetId(talk.getId());
			talkContentRepository.save(tc);
		}else{
			talk.setLongContent(false);
			talkDao.save(talk);
		}
		
	}


}
