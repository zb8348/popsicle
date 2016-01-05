package com.pfw.popsicle.front.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.front.entity.TransactGroup;
/**
 * 
 * @author wulibing
 *
 */
public interface TransactGroupDao  extends IBaseMybatisSqlMapper{

	void save(TransactGroup tg);

	List<TransactGroup> findByCreator(String loginName);

	TransactGroup getById(long id);

	void delete(@Param(value="id")long id);

	void update(@Param(value="tg")TransactGroup tg);


	List<TransactGroup> findByCreateTimeDesc(@Param(value="limit")int limit);
}
