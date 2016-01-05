package com.pfw.popsicle.front.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 一次交易
 * @author wulibing
 *
 */
public class Transaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8271541242914362376L;
	
	public static final int status_valid=1;
	public static final int status_invalid=0;
	private long id;
	private String ticket;
	private String currency;
	private Long volume;
	private Float lots;
	private Date openTime;
	private Date closeTime;
	private String openType;
	private String closeType;
	private String direction;
	private BigDecimal openPosition;
	private BigDecimal closePosition;
	private Integer netPL;
	private Integer grossPL;
	
	private Integer status;
	private Long groupId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	public Float getLots() {
		return lots;
	}
	public void setLots(Float lots) {
		this.lots = lots;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	public String getCloseType() {
		return closeType;
	}
	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public BigDecimal getOpenPosition() {
		return openPosition;
	}
	public void setOpenPosition(BigDecimal openPosition) {
		this.openPosition = openPosition;
	}
	public BigDecimal getClosePosition() {
		return closePosition;
	}
	public void setClosePosition(BigDecimal closePosition) {
		this.closePosition = closePosition;
	}
	public Integer getNetPL() {
		return netPL;
	}
	public void setNetPL(Integer netPL) {
		this.netPL = netPL;
	}
	public Integer getGrossPL() {
		return grossPL;
	}
	public void setGrossPL(Integer grossPL) {
		this.grossPL = grossPL;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
}
