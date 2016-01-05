package com.pfw.popsicle.front.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.front.entity.Transaction;

/**
 * 
 * @author wulibing
 *
 */
public interface TransactionDao extends IBaseMybatisSqlMapper {

	void batchSave(@Param(value = "transactions") List<Transaction> transactions);

	List<Transaction> findByGroupId(@Param(value="groupId")long groupId,@Param(value="status")Integer status);

	void deleteByGroupId(@Param(value="groupId")long groupId);

	void deleteByIds(@Param(value="ids")List<Long> ids);

	void update(@Param(value="t")Transaction t);

	List<Transaction> findByIds(@Param(value="ids")List<Long> ids);

}
