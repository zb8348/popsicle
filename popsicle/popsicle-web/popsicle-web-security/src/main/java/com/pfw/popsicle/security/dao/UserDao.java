package com.pfw.popsicle.security.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;

//import org.apache.ibatis.annotations.Result;
//import org.apache.ibatis.annotations.Results;
//import org.apache.ibatis.annotations.Select;

import com.pfw.popsicle.base.IBaseMybatisSqlMapper;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.entity.UserRole;

/**
 * 
 * @author wulibing
 *
 */
public interface UserDao extends IBaseMybatisSqlMapper{
//	@Select("select * from t_user where login_name = #{loginName}")
//	@Results(value = { 
//			@Result(property = "id", column = "CONTACT_ID"),
//			@Result(property = "name", column = "CONTACT_NAME"), 
//			@Result(property = "phone", column = "CONTACT_PHONE"),
//			@Result(property = "email", column = "CONTACT_EMAIL") })
    public User getUserByLoginName(String loginName);
    public void save(User account);
    

	List<User> findPage(@Param(value = "user")User user, @Param(value = "start")int start, @Param(value = "pagesize")int pagesize);

	long findPageSize(@Param(value = "user")User user);

	User getById(@Param(value = "id")long id);

	/**
	 * 删除指定用户的指定角色，当不指定角色时，表示默认删除所有角色
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	void deleteUserRole(@Param(value = "userId") long userId,@Param(value = "roleIds")List<Long> roleIds);

	void addUserRole(@Param(value = "userRoles")List<UserRole> userRoles);

	void updateUser(User user);

	void deleteUser(Long userId);   
    
}
