package com.pfw.popsicle.web.core.interceptors;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.pfw.popsicle.security.service.ResourceService;
//import com.pfw.popsicle.security.service.ResourceService;
import com.pfw.popsicle.sys.PUtil;

/**
 * 为系统准备全局菜单数据
 * @author wulibing
 *
 */
public class SysDataInterceptor implements WebRequestInterceptor {//HandlerInterceptor {

	private ResourceService resourceService;
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void preHandle(WebRequest request)  {
		request.setAttribute("_P",new PUtil(), WebRequest.SCOPE_GLOBAL_SESSION);
		
		request.setAttribute("portletid",PUtil.genId(), WebRequest.SCOPE_REQUEST);
		
		//如果环境允许的话，它能在全局共享的会话中访问，否则就是在普通的当前会话中访问 
//		request.setAttribute("systems", SysDataUtil.loadAllsystems(), WebRequest.SCOPE_GLOBAL_SESSION);
//		request.setAttribute("menus", SysDataUtil.loadAllMenus(), WebRequest.SCOPE_GLOBAL_SESSION);
//		request.setAttribute("systems", resourceService.loadAllsystems(), WebRequest.SCOPE_GLOBAL_SESSION);
		
		//可变的数据，尽量不要使用：WebRequest.SCOPE_GLOBAL_SESSION
		Object sm = request.getAttribute("sys_menus", WebRequest.SCOPE_SESSION);
		if(sm==null){
			request.setAttribute("sys_menus", resourceService.getTrees(), WebRequest.SCOPE_SESSION);
		}
		
		Object smlist = request.getAttribute("sys_menus_nodes", WebRequest.SCOPE_SESSION);
		if(smlist==null){
			request.setAttribute("sys_menus_nodes", resourceService.getMenusTreeList(), WebRequest.SCOPE_SESSION);
		}
	}

	public void postHandle(WebRequest request, ModelMap model) {
	}

	public void afterCompletion(WebRequest request, Exception ex){
	}

}
