package com.pfw.popsicle.front.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * k线
 * @author wulibing
 *
 */
public class Kschema implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5255296058833599251L;
	private String ticker;//货币种类
	private Date time;
	private String dayStr;
	private String timeStr;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private long volume;

	public String getDayStr() {
		return dayStr;
	}

	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public long getVolume() {
		return volume;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}
	
	public static Kschema copy(Kschema data){
		Kschema k = new Kschema();
 		k.setTicker(data.getTicker());
 		k.setDayStr(data.getDayStr());
 		k.setTimeStr(data.getTimeStr());
 		k.setOpen(data.getOpen());
 		k.setHigh(data.getHigh());
 		k.setLow(data.getLow());
 		k.setClose(data.getClose());
 		k.setVolume(data.getVolume());
 		k.setTime(data.getTime());
 		return k;
	}
}
