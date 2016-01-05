package com.pfw.popsicle.base.crud;

import java.util.Collections;
import java.util.List;

/**
 * 使用js插件：datatables 用来做数据分页展现
 * @link http://dt.thxopen.com
 * http://dt.thxopen.com/manual/#option
 * 
 * @author wulibing
 *
 * @param <T>
 */
public class ResponsePageVO<T> extends ResponseResultVO<T>{
	private static final long serialVersionUID = 1457789249277494847L;
	//-- 分页参数 --//
	private int draw=0;//请求次数
	protected int length = 10;//数据长度
	private int start = 0;//数据起始位置
	protected long recordsTotal = 0;//总记录数
	protected long recordsFiltered = 0;//过滤后记录数
	
	//-- 返回结果 --//
	protected List<T> data = Collections.emptyList();

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
