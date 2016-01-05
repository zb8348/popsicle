package com.pfw.popsicle.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.security.dao.RoleDao;
import com.pfw.popsicle.security.entity.Role;
import com.pfw.popsicle.security.entity.RoleResource;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.security.vo.RoleAndResourcesVO;
import com.pfw.popsicle.vo.RequestPageVO;
import com.pfw.popsicle.vo.RequestResultVO;

@Service
@Transactional
public class RoleService {
//	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final String ROLE_CODE_SYSTEM_USER = "SYSTEM";//一般注册用户
	public static final String ROLE_CODE_GENERAL_USER = "GENERAL";//一般注册用户
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceService resourceService;
	
	
	
	@Transactional(readOnly=true)
	public List<Role> loadAll() {
		return roleDao.loadAll();
	}
	@Transactional(readOnly=true)
	public List<Role> findRolesByUserId(long userId) {
		if(userId>0){
			return roleDao.findRolesByUserId(userId);
		}
		return null;
	}
	@Transactional(readOnly=true)
	public Role getRoleByCode(String code) {
		if(StringUtils.isNotBlank(code)){
			return roleDao.getRoleByCode(code);
		}
		return null;
	}

	public Role save(Role role) {
		if(role!=null){
			roleDao.save(role);
			return role;
		}
		return null;
	}

	public void addRoleToUser(long roleId, long userId) {
		if(roleId>0&&userId>0){
			try {
				roleDao.addRoleToUser(roleId,userId);
			} catch (Exception e) {
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	public RequestPageVO<Role> findPageExitUserRole(Role role, int start, int limit) {
		RequestPageVO<Role> result = new RequestPageVO<Role>();
		result.setStart(start);
		result.setLength(limit);
		List<Role> roles = roleDao.findPageExitUserRole(role,start,limit);
		result.setData(roles);
		long totalCount = roleDao.findPageExitUserRoleSize(role);
		result.setRecordsFiltered(totalCount);
		result.setRecordsTotal(totalCount);
		return result;
	}
	
	public RequestPageVO<Role> findPage(Role role, int start, int limit) {
		RequestPageVO<Role> result = new RequestPageVO<Role>();
		result.setStart(start);
		result.setLength(limit);
		List<Role> roles = roleDao.findPage(role,start,limit);
		result.setData(roles);
		long totalCount = roleDao.findPageSize(role);
		result.setRecordsFiltered(totalCount);
		result.setRecordsTotal(totalCount);
		return result;
	}
	public Role findById(long id) {
		return roleDao.findById(id);
	}

	@Transactional
	public RequestResultVO<Role> update(Role role) {
		role.setModifiedTime(DateUtil.getChineseTime());
		role.setModifier(PFWSecurityUtil.getLoginName());
		int resultCode = roleDao.update(role);
		RequestResultVO<Role> vo = new RequestResultVO<Role>();
		if(resultCode == 1){
			vo.setSuccess(true);
		}else{
			vo.setErrorCode(resultCode+"");
			vo.setErrorMessage("更新失败");
		}
		return vo;
	}

	@Transactional
	public RequestResultVO<Role> delete(long id) {
		//校验角色下面有没有用户
		long count = roleDao.findUserRoleById(id);
		RequestResultVO<Role> r= new RequestResultVO<Role>(); 
		if(count == 0){
			try {
				roleDao.delete(id);
				roleDao.deleteRoleResourceByRoleId(id);
				r.setSuccess(true);
			} catch (Exception e) {
				r.setSuccess(false);
				r.setErrorMessage("删除角色失败，请联系管理员");
			}
			return r;
		}else{
			r.setSuccess(false);
			r.setErrorMessage("角色下存在用户不能删除");
			return r;
		}
	}

	@Transactional
	public RequestResultVO<Role> add(Role role) {
		role.setCreateTime(DateUtil.getChineseTime());
		role.setCreator(PFWSecurityUtil.getLoginName());
		Role r = roleDao.findByCode(role.getCode());
		int resultCode = 0;
		RequestResultVO<Role> vo = new RequestResultVO<Role>();
		if(r == null){
			roleDao.save(role);
			resultCode = 1;
		}else{
			resultCode = 2;
		}
		if(resultCode == 1){
			vo.setSuccess(true);
		}else if(resultCode == 2){
			vo.setErrorCode(2+"");
			vo.setErrorMessage("角色代码已经存在");
		}
		return vo;
	}

	public List<Role> findAll() {
		return roleDao.loadAll();
	}

	public List<RoleAndResourcesVO> findAllResources(long id) {
		return roleDao.findRoleResourcesById(id);
	}


	@Transactional
	public RequestResultVO<RoleAndResourcesVO> saveOrUpdateRoleResource(Long roleId,
			List<Long> resourceIds) {
		RequestResultVO<RoleAndResourcesVO>  vo = new RequestResultVO<RoleAndResourcesVO>();
		Map<Long,Long> toUpdateResourceIds = new HashMap<Long, Long>();
		
		//获取原资源：
		List<RoleResource> roleResources = roleDao.findRoleResourceByRoleId(roleId); //角色原来的资源
		if(roleResources!=null&&roleResources.size()>0){
			for (RoleResource roleResource : roleResources) {
				toUpdateResourceIds.put(roleResource.getResourceId(), roleResource.getResourceId());
			}
		}
		//删除需求修改的角色的历史资源
		roleDao.deleteRoleResourceByRoleId(roleId);
		
		//关联角色和资源
		if(resourceIds != null && resourceIds.size() > 0){
			List<RoleResource> rrList = new ArrayList<RoleResource>();
			for (Long rId : resourceIds) {
				toUpdateResourceIds.put(rId, rId);
				RoleResource rr = new RoleResource();
				rr.setResourceId(rId);
				rr.setRoleId(roleId);
				rrList.add(rr);
			}
			roleDao.saveOrUpdateRoleResource(rrList);
		}
		
		if(toUpdateResourceIds.size()>0){
			//更新权限字段
			resourceService.updateHasAnyRoles(new ArrayList<Long>(toUpdateResourceIds.keySet()));
		}
		vo.setSuccess(true);
		return vo;
	}
	
	@Transactional
	public RequestResultVO<RoleAndResourcesVO> deleteRoleResource(Long roleId,
			List<Long> resourceId) {
		RoleAndResourcesVO vo = new RoleAndResourcesVO();
		vo.setRoleId(roleId);
		vo.setRoleResourceIds(resourceId);
		int count = roleDao.deleteRoleResource(vo);
		RequestResultVO<RoleAndResourcesVO> result = new RequestResultVO<RoleAndResourcesVO>();
		if(count>0){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}
