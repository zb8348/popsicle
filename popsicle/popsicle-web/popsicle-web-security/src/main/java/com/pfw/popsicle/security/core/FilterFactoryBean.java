package com.pfw.popsicle.security.core;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pfw.popsicle.security.entity.Resource;

/**
 * 
 * @author wulibing
 *
 */
public class FilterFactoryBean extends ShiroFilterFactoryBean {
	private static final transient Logger log = LoggerFactory
			.getLogger(FilterFactoryBean.class);

	private PfwSecurityManager pfwSecurityManager;
	public void setPfwSecurityManager(PfwSecurityManager pfwSecurityManager) {
		this.pfwSecurityManager = pfwSecurityManager;
	}
	private String definitions;

	public void setFilterChainDefinitions(String definitions) {
		log.debug("load loginUrl:{},successUrl:{}",getLoginUrl(),getSuccessUrl());
		Ini ini = new Ini();
		ini.load(definitions);

		Ini.Section section = ini.getSection("urls");
		if (CollectionUtils.isEmpty(section)) {
			section = ini.getSection("");
		}
//		section.clear();
		
		List<Resource> resourses = pfwSecurityManager.findAllWithOrderNo();
		if (resourses != null && resourses.size() > 0) {
			for (Resource resource : resourses) {
				if (StringUtils.isNotBlank(resource.getAuthUrl()) && StringUtils.isNotBlank(resource.getHasAnyRoles())) {
					section.put(resource.getAuthUrl().trim(), "rolesOr["+ resource.getHasAnyRoles().trim() + "]");
				}
			}
		}

		super.setFilterChainDefinitionMap(section);
	}
	
	/**
	 * 不重启系统：动态更新授权信息
	 */
	public void updatePermission() {
		synchronized (this) {
			AbstractShiroFilter shiroFilter = null;
			try {
				shiroFilter = (AbstractShiroFilter) this.getObject();
			} catch (Exception e) {
				log.error(e.getMessage());
			}

			// 获取过滤管理器
			PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
					.getFilterChainResolver();
			DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
					.getFilterChainManager();

			// 清空初始权限配置
			manager.getFilterChains().clear();
			getFilterChainDefinitionMap().clear();

			// 重新构建生成
			setFilterChainDefinitions(definitions);
			Map<String, String> chains = getFilterChainDefinitionMap();

			for (Map.Entry<String, String> entry : chains.entrySet()) {
				String url = entry.getKey();
				String chainDefinition = entry.getValue().trim()
						.replace(" ", "");
				manager.createChain(url, chainDefinition);
			}
			log.debug("update shiro permission success...");
		}
	}
}