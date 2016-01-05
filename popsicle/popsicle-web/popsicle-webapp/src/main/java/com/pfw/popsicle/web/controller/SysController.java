package com.pfw.popsicle.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.SetUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.pfw.popsicle.web.vo.RequestToMethodItem;

@Controller
@RequestMapping("/sys")
public class SysController {

	@RequestMapping(value = "/allurl", method = RequestMethod.GET)
	public @ResponseBody List<RequestToMethodItem> allurl(HttpServletRequest request) {
		ServletContext servletContext = request.getSession().getServletContext();
		if (servletContext == null) {
			return null;
		}
		WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		// 请求url和处理方法的映射
		List<RequestToMethodItem> requestToMethodItemList = new ArrayList<RequestToMethodItem>();
		// 获取所有的RequestMapping
		Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,

		HandlerMapping.class, true, false);

		for (HandlerMapping handlerMapping : allRequestMappings.values()) {
			// 本项目只需要RequestMappingHandlerMapping中的URL映射
			if (handlerMapping instanceof RequestMappingHandlerMapping) {
				RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
				Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping
						.getHandlerMethods();
				for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods
						.entrySet()) {
					RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
					HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();

					RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
					String requestType = null;
					if(methodCondition.getMethods()!=null&&methodCondition.getMethods().size()>0){
						requestType = methodCondition.getMethods().iterator().next().name();
					}
					PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
					String requestUrl = null;
					if(methodCondition.getMethods()!=null&&patternsCondition.getPatterns().size()>0){
						requestUrl = patternsCondition.getPatterns().iterator().next();
					}

					String controllerName = mappingInfoValue.getBeanType().toString();
					String requestMethodName = mappingInfoValue.getMethod().getName();
					Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();
					
					
					RequestToMethodItem item = new RequestToMethodItem(requestUrl, requestType, controllerName,
							requestMethodName,

					methodParamTypes);

					requestToMethodItemList.add(item);
				}
				break;
			}
		}
		return requestToMethodItemList;
	}

}
