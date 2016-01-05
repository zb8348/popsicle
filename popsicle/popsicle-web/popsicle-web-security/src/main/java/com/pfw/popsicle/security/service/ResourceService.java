package com.pfw.popsicle.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.common.CollectionUtils;
import com.pfw.popsicle.common.DateUtil;
import com.pfw.popsicle.security.dao.ResourceDao;
import com.pfw.popsicle.security.entity.Resource;
import com.pfw.popsicle.security.utils.PFWSecurityUtil;
import com.pfw.popsicle.security.vo.ResourceTreeVO;

@Service("resourceService")
@Transactional
public class ResourceService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ResourceDao resourceDao;
	
	public List<Resource> findAllWithOrderNo() {
		List<Resource> resources = null;
		resources = resourceDao.findAllWithOrderNo();
		if(resources==null||resources.size()==0){
			logger.error("not find resource");
		}
		return resources;
	}

	
	public List<ResourceTreeVO> getTrees(){
		List<Resource> list = findAllWithOrderNo();
		List<ResourceTreeVO> tree = toTreesUtil(list);
		CollectionUtils.sortTheList(tree, "node.orderNo", CollectionUtils.SORT_ORDER_ASC);
		return tree;
	}
	
	private List<ResourceTreeVO> toTreesUtil(List<Resource> list) {
		List<ResourceTreeVO> tree = null;
			if(list!=null){
				tree = new ArrayList<ResourceTreeVO>();
				Map<String,ResourceTreeVO> treeMap = new HashMap<String, ResourceTreeVO>();
				
				ResourceTreeVO node = null;
				ResourceTreeVO parentNode = null;
				for (Resource resource : list) {
					node = new ResourceTreeVO();
					node.setNode(resource);
					treeMap.put(resource.getCode(), node);
				}
				
				List<ResourceTreeVO> children = null;
				Set<Map.Entry<String, ResourceTreeVO>> sets = treeMap.entrySet();
				for (Entry<String, ResourceTreeVO> entry : sets) {
					node = entry.getValue();
					if(node==null||node.getNode()==null){
						continue;
					}
					parentNode = treeMap.get(node.getNode().getParentCode());
					if(parentNode==null){
						tree.add(node);
					}else{
						children = parentNode.getChildren();
						if(children==null){
							children = new ArrayList<ResourceTreeVO>();
							children.add(node);
							parentNode.setChildren(children);
						}else{
							parentNode.getChildren().add(node);
						}
					}
				}
			}
		
		if(tree==null){
			tree = new ArrayList<ResourceTreeVO>();
		}
		return tree;
	}
	
	public List<ResourceTreeVO> getRoleTree(Long id) {
		List<Resource> resources = null;
		resources = resourceDao.findRoleResourceWithOrderNo(id);
		return toTreesUtil(resources);
	}
	
	public List<ResourceTreeVO> getMenusTreeList() {
		List<ResourceTreeVO> resList = null;
		List<ResourceTreeVO> list = getTrees();
		resList = new ArrayList<ResourceTreeVO>();
		if(list!=null){
			CollectionUtils.sortTheList(list, "node.orderNo", CollectionUtils.SORT_ORDER_ASC);
			  
			for (ResourceTreeVO resourceTreeVO : list) {
				getMenusNode(resourceTreeVO,resList);
			}
			logger.debug("find resources and put into cache");
		}else{
			logger.error("not find resources");
		}
		return resList;
	}
	
	private void getMenusNode(ResourceTreeVO resourceTreeVO,
			List<ResourceTreeVO> resList) {
		if(resourceTreeVO.getNode().getType()==Resource.TYPE_SYS){
			List<ResourceTreeVO> children = resourceTreeVO.getChildren();
			if(children!=null){
				CollectionUtils.sortTheList(children, "node.orderNo", CollectionUtils.SORT_ORDER_ASC);
				for (ResourceTreeVO child : children) {
					getMenusNode(child,resList);
				}
			}
		}else{
			resList.add(resourceTreeVO);
		}
	}
	@Transactional
	public Resource saveOrUpdate(Resource entity) {
		if(entity.getId()>0){
			entity.setModifiedTime(DateUtil.getChineseTime());
			entity.setModifier(PFWSecurityUtil.getLoginName());
			resourceDao.update(entity);
		}else{
			entity.setCreateTime(DateUtil.getChineseTime());
			entity.setCreator(PFWSecurityUtil.getLoginName());
			resourceDao.save(entity);
		}
		return entity;
	}

	@Transactional
	public int deleteByCode(String code) {
		//查询当前节点的是否含有子节点
//		List<String> codes = resourceDao.findSelfSun(code);
//		if(codes.size() > 0){
//			
//		}
		Resource r = resourceDao.selectResourceByCode(code);
		resourceDao.updateSelfSunParentCode(r.getParentCode(),r.getCode());
		return resourceDao.deleteByCode(code);
	}
	
	public Resource selectResourceByCode(String code) {
		return resourceDao.selectResourceByCode(code);
	}
	
	public List<Resource> findResourcesByRoleId(long roleId) {
		return resourceDao.findResourcesByRoleId(roleId);
	}

	@Transactional
	public void updateHasAnyRoles(List<Long> rIds) {
		if(rIds!=null&&rIds.size()>0){
			List<String> list = null;
			StringBuffer names = null;
			for (Long id : rIds) {
				names = new StringBuffer();
				list = resourceDao.findAllRoleNamesByResourceId(id);
				if(list!=null&&list.size()>0){
					String name = null; 
					for (int i = 0,size = list.size(); i < size; i++) {
						name = list.get(i);
						names.append(name);
						if(i<(size-1)){
							names.append(",");
						}
					}
					resourceDao.updateHasAnyRoleName(id,names.toString());
				}
			}
		}
	}
}
