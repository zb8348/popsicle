package com.pfw.popsicle.social.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.social.entity.Talk;

/**
 * 
 * @author wulibing
 *
 */
public interface TalkDao extends IBaseMybatisSqlMapper{

	List<Talk> findByCreateTimeDesc(@Param(value = "objectId")long objectId, @Param(value = "lastId")Long lastId,@Param(value = "limit") int limit);

	void save(Talk talk);

	List<Talk> findByCreateTimeDescBeforeId(@Param(value = "objectId")long objectId, @Param(value = "lastId")Long lastId,@Param(value = "limit") int limit);
    
}
