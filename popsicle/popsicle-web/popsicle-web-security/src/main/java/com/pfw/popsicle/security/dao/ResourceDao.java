package com.pfw.popsicle.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.security.entity.Resource;

/**
 * 
 * @author wulibing
 *
 */
public interface ResourceDao extends IBaseMybatisSqlMapper {

	List<Resource> findAllWithOrderNo();

	List<Resource> findRoleResourceWithOrderNo(Long id);

	Long save(Resource entity);

	Resource selectResourceByCode(String code);

	void updateSelfSunParentCode(@Param(value="parentCode")String parentCode,@Param(value="code") String code);

	int deleteByCode(String code);

	List<Resource> findResourcesByRoleId(long roleId);

	List<String> findAllRoleNamesByResourceId(Long id);

	void updateHasAnyRoleName(@Param(value="id")long id,@Param(value="names") String names);

	void update(Resource entity);

}
