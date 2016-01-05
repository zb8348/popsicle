package com.pfw.popsicle.social.entity;

import java.util.Date;
/**
 * 话题或点评
 * @author wulibing
 *
 */
public class Talk {
	private long id;
	private long objectId;
	private long talkerId;//userId
	private String talker;
	private Date createTime;
	private long targetId;//回复目标
	private String targetStr;//回复目标：@xxxx
	private String content;//长信息转存到hbase中,只存250字符
	private boolean longContent;//长信息标识
	private String longContentKey;
	
	public String getTargetStr() {
		return targetStr;
	}
	public void setTargetStr(String targetStr) {
		this.targetStr = targetStr;
	}
	public boolean isLongContent() {
		return longContent;
	}
	public void setLongContent(boolean longContent) {
		this.longContent = longContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLongContentKey() {
		return longContentKey;
	}
	public void setLongContentKey(String longContentKey) {
		this.longContentKey = longContentKey;
	}
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTalkerId() {
		return talkerId;
	}
	public void setTalkerId(long talkerId) {
		this.talkerId = talkerId;
	}
	public String getTalker() {
		return talker;
	}
	public void setTalker(String talker) {
		this.talker = talker;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getTargetId() {
		return targetId;
	}
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}
}
