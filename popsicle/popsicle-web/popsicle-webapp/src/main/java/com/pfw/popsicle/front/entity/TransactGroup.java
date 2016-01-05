package com.pfw.popsicle.front.entity;

import java.util.Date;

/**
 * 交易组
 * @author wulibing
 *
 */
public class TransactGroup{

	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	private String creator;
	private Date createTime;
	private String name;
	private String fileName;
	private String broker;
	private String type;
	private String accountName;
	private String address;
	
	private Long analysisAmount;
	private Long analysisPl;
	
	private Long parentId;
	private Long userId;
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBroker() {
		return broker;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getAnalysisAmount() {
		return analysisAmount;
	}
	public void setAnalysisAmount(Long analysisAmount) {
		this.analysisAmount = analysisAmount;
	}
	public Long getAnalysisPl() {
		return analysisPl;
	}
	public void setAnalysisPl(Long analysisPl) {
		this.analysisPl = analysisPl;
	}

	
}
