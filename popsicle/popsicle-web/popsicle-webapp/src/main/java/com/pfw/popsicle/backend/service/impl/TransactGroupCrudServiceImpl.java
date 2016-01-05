package com.pfw.popsicle.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.backend.dao.TransactGroupCrudDao;
import com.pfw.popsicle.backend.service.TransactGroupCrudService;
import com.pfw.popsicle.base.crud.impl.CrudServiceImpl;
import com.pfw.popsicle.front.entity.TransactGroup;
/**
 * 
 * @author wulibing
 *
 */
@Service("transactGroupCrudService")
@Transactional
public class TransactGroupCrudServiceImpl extends CrudServiceImpl<TransactGroup,TransactGroupCrudDao> implements TransactGroupCrudService {
//or : public class TransactGroupCrudService implements CrudService<TransactGroup> {
	@Autowired
	private TransactGroupCrudDao transactGroupCrudDao;

	@Override
	public TransactGroupCrudDao getDao() {
		return transactGroupCrudDao;
	}	
}
