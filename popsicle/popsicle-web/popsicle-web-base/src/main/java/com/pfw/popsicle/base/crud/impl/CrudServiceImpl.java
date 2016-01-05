package com.pfw.popsicle.base.crud.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pfw.popsicle.base.crud.CrudDao;
import com.pfw.popsicle.base.crud.CrudService;
import com.pfw.popsicle.base.crud.ResponsePageVO;
import com.pfw.popsicle.base.crud.ResponseResultVO;

public abstract class CrudServiceImpl<E,D extends CrudDao<E>> implements CrudService<E>{
	
	public abstract D getDao();
	
	public ResponsePageVO<E> findPage(E e, int start, int limit, String orderName, String orderType) {
		if(!StringUtils.equalsIgnoreCase(orderType, "asc")&&!StringUtils.equalsIgnoreCase(orderType, "desc")){
			orderType="asc";
		}
		ResponsePageVO<E> result = new ResponsePageVO<E>();
		try {
			result.setStart(start);
			result.setLength(limit);
			List<E> es = getDao().findPage(e,start,limit,orderName,orderType);
			result.setData(es);
			long totalCount = getDao().findPageSize(e);
			result.setRecordsFiltered(totalCount);
			result.setRecordsTotal(totalCount);
		} catch (Exception e1) {
			result.error("Paging ERROR", e1.getMessage());
		}
		return result;
	}

	public E getById(Long id) {
		if(id==null){
			return null;
		}
		try {
			return getDao().getById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ResponseResultVO<E> save(E e) {
		ResponseResultVO<E> result = new ResponseResultVO<E>();
		try {
			getDao().save(e);
			result.success(e);
		} catch (Exception e1) {
			result.error("Paging ERROR", e1.getMessage());
		}
		return result;
	}
	
	public ResponseResultVO<E> update(E e) {
		ResponseResultVO<E> result = new ResponseResultVO<E>();
		try {
			getDao().update(e);
			result.success(e);
		} catch (Exception e1) {
			result.error("Paging ERROR", e1.getMessage());
		}
		return result;
	}


	public ResponseResultVO<Boolean> delete(List<Long> ids) {
		ResponseResultVO<Boolean> result = new ResponseResultVO<Boolean>();
		try {
			if(ids!=null&&ids.size()>0){
				for (Long id : ids) {
					getDao().delete(id);
				}
				result.success(Boolean.TRUE);
			}else{
				result.error("NONE TO DEL","没有删除数量");
			}
			
		} catch (Exception e1) {
			result.error("DELETE ERROR", e1.getMessage());
		}
		return result;
	}
}
