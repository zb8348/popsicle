package com.pfw.popsicle.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.RoleResource;
import com.pfw.popsicle.security.vo.RoleAndResourcesVO;

/**
 * 
 * @author wulibing
 *
 */
public interface RoleDao  extends IBaseMybatisSqlMapper{

	List<Role> loadAll();

	List<Role> findRolesByUserId(long userId);

	Role getRoleByCode(String code);

	void save(Role role);

	void addRoleToUser(@Param(value="roleId")long roleId, @Param(value="userId")long userId);

	
	
	
	
	
	
	
	
	
	
	
	

	List<Role> findPage(@Param(value = "role")Role role, @Param(value = "start")int start, @Param(value = "pageSize")int pageSize);

	long findPageSize(@Param(value = "role")Role role);
	
	Role findById(long id);

	int update(Role role);

	long findUserRoleById(long id);

	long delete(long id);


	long deleteRoleResourceByRoleId(long roleId);

	List<RoleAndResourcesVO> findRoleResourcesById(long id);

	List<Role> findPageExitUserRole(@Param(value = "role")Role role, @Param(value = "start")int start, @Param(value = "pageSize")int pageSize);

	long findPageExitUserRoleSize(@Param(value = "role")Role role);

	int deleteRoleResource(RoleAndResourcesVO vo);

	int updateResourceDelHasAnyRole(Role role);

	void updateResourceAddHasAnyRole(Role role);

	Role findByCode(String code);

	List<RoleResource> findRoleResourceByRoleId(Long roleId);


	void saveOrUpdateRoleResource(@Param(value = "rrList")List<RoleResource> rrList);
}
