package com.pfw.popsicle.demo.crud.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.pfw.popsicle.demo.crud.dao.UserEntityDao;
import com.pfw.popsicle.base.crud.impl.CrudServiceImpl;
import com.pfw.popsicle.demo.crud.entity.UserEntity;
import com.pfw.popsicle.demo.crud.service.UserEntityService;
/**
 * 
 * @author wulibing
 *
 */
@Service("userEntityService")
@Transactional
//or : public class UserEntityServiceImpl implements CrudService<UserEntity> {
public class UserEntityServiceImpl extends CrudServiceImpl<UserEntity,UserEntityDao> implements UserEntityService{	
	@Autowired
	private UserEntityDao userEntityDao;
	@Override
	public UserEntityDao getDao() {
		return userEntityDao;
	}	
}
