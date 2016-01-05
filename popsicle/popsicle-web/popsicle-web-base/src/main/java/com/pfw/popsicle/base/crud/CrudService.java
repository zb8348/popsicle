package com.pfw.popsicle.base.crud;

import java.util.List;

public interface CrudService<E> {
	public ResponsePageVO<E> findPage(E e,int start,int limit, String orderName, String orderType) ;
	public E getById(Long id);
	public ResponseResultVO<E> save(E e);
	public ResponseResultVO<E> update(E e);
	public ResponseResultVO<Boolean> delete(List<Long> ids);
}
