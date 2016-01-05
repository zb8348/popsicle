package com.pfw.popsicle.security.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.common.Digests;
import com.pfw.popsicle.common.Encodes;
import com.pfw.popsicle.enums.ErrorEnums;
import com.pfw.popsicle.security.dao.UserDao;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.User;
import com.pfw.popsicle.security.entity.UserRole;
import com.pfw.popsicle.security.enums.UserStatus;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.vo.RequestPageVO;
import com.pfw.popsicle.vo.RequestResultVO;

@Service
@Transactional
public class UserService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleService roleService;

	
	@Transactional
	public RequestResultVO<User> insertUser(User user) {
		RequestResultVO<User> vo = new  RequestResultVO<User>();
		if(user==null){
			vo.error("NO DATA","空数据");
			return vo;
		}
		
		user.setCreateTime(DateUtil.getChineseTime());
		user.setCreator(PFWSecurityUtil.getLoginName());
		
		// 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		try {
			userDao.save(user);
			log.info("create  user:" + user.getLoginName() + " for " + user.getName());
			vo.success(user);
		} catch (Exception e) {
			vo.error(ErrorEnums.DbError);
		}
		return vo;
	}
	
	@Transactional(readOnly=true)
	public User getUserByLoginName(String loginName) {
		if(StringUtils.isNotBlank(loginName)){
			return userDao.getUserByLoginName(loginName);
		}
		return null;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),
				salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public User register(String name,String loginName, String password, String email) {
		if(StringUtils.isBlank(loginName)||StringUtils.isBlank(password)||StringUtils.isBlank(name)||StringUtils.isBlank(email)){
			return null;
		}
		User user = new User();
		user.setEmail(email);
		user.setLoginName(loginName);
		user.setPlainPassword(password);
		user.setName(name);
		user.setIsAdmin(false);
		user.setStatus(UserStatus.natural.getType());
		insertUser(user);
		
		Role role = roleService.getRoleByCode(RoleService.ROLE_CODE_GENERAL_USER);
		long roleId = 0;
		if(role==null){
			role = new Role();
			role.setCode(RoleService.ROLE_CODE_GENERAL_USER);
			role.setName("普通用户");
			role.setRemark("普通注册用户");
			roleService.save(role);
		} 
		roleId =role.getId();
		roleService.addRoleToUser(roleId,user.getId());
		return user;
	}
	
	
	
	
	
	
	
	
	public RequestPageVO<User> findPage(User user, int start, int pagesize) {
		RequestPageVO<User> result = new RequestPageVO<User>();
		result.setStart(start);
		result.setLength(pagesize);
		List<User> users = userDao.findPage(user,start,pagesize);
		result.setData(users);
		long totalCount = userDao.findPageSize(user);
		result.setRecordsFiltered(totalCount);
		result.setRecordsTotal(totalCount);
		return result;
	}

	public List<Role> findRolesByUserId(long userId) {
		return roleService.findRolesByUserId(userId);
	}

	public User findUserById(long id) {
		return userDao.getById(id);
	}
	@Transactional
	public RequestResultVO<Role> deleteUserRole(long userId,List<Long> roleIds) {
		RequestResultVO<Role> resule = new RequestResultVO<Role>();
		try {
			userDao.deleteUserRole(userId,roleIds);
			resule.setSuccess(true);
		} catch (Exception e) {
			resule.setErrorMessage("删除失败");
		}
		return resule;
	}
	@Transactional
	public RequestResultVO<Role> addUserRole(long userId,List<Long> roleIds) {
		List<UserRole> ur = new ArrayList<UserRole>();
		RequestResultVO<Role> resule = new RequestResultVO<Role>();
		for(long temp : roleIds){
			ur.add(new UserRole(userId,temp));
		}
		if(ur.size() > 0){
			try {
				 userDao.addUserRole(ur);
				resule.setSuccess(true);
			} catch (Exception e) {
				resule.setErrorMessage("增加失败！");
			}
		}else{
			resule.setErrorMessage("没有选择角色");
		}
		return resule;
	}
	@Transactional
	public RequestResultVO<User> updateUser(User user) {
		user.setModifiedTime(DateUtil.getChineseTime());
		user.setModifier(PFWSecurityUtil.getCurrentUser().getLoginName());
		RequestResultVO<User> vo = new  RequestResultVO<User>();
		try {
			userDao.updateUser(user);
			vo.setSuccess(true);
		} catch (Exception e) {
			vo.setErrorMessage("更新失败");
		}
		return vo;
	}
	@Transactional
	public RequestResultVO<User> deleteUser(Long userId) {
		RequestResultVO<User> result = new RequestResultVO<User>();
		try {
			//删除用户
			userDao.deleteUser(userId);
			List<Role> roles = roleService.findRolesByUserId(userId);
			List<Long> roleIds = new ArrayList<Long>();
			for(Role role : roles){
				roleIds.add(role.getId());
			}
			//删除用户含有的角色
			
			userDao.deleteUserRole(userId, roleIds);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMessage("删除角色失败，请联系管理员");
		}
		return result;
	}
	@Transactional
	public boolean updateAndRemoveUserRole(long userId, List<Long> roleIds) {
		userDao.deleteUserRole(userId,null);
		RequestResultVO<Role> r = addUserRole(userId,roleIds);
		return r.isSuccess();
	}
	
	
	
}
