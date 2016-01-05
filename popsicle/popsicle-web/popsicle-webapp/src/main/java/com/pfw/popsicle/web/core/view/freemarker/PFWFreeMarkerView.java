package com.pfw.popsicle.web.core.view.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContextException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.pfw.popsicle.common.JsonUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.ParseException;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author wulibing
 */
public class PFWFreeMarkerView extends FreeMarkerView {
	private static Logger logger = LoggerFactory.getLogger(PFWFreeMarkerView.class);

	private String encoding;
	private Configuration configuration;
	private TaglibFactory taglibFactory;
	private ServletContextHashModel servletContextHashModel;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	protected String getEncoding() {
		return this.encoding;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	protected Configuration getConfiguration() {
		return this.configuration;
	}

	protected void initServletContext(ServletContext servletContext)
			throws BeansException {
		if (getConfiguration() != null) {
			this.taglibFactory = new TaglibFactory(servletContext);
		} else {
			FreeMarkerConfig config = autodetectConfiguration();
			ClassTemplateLoader ctl = new ClassTemplateLoader(this.getClass(),
					"/");
			TemplateLoader tl = config.getConfiguration().getTemplateLoader();
			TemplateLoader[] loaders = new TemplateLoader[] { tl, ctl };
			MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
			config.getConfiguration().setTemplateLoader(mtl);
			setConfiguration(config.getConfiguration());
			this.taglibFactory = config.getTaglibFactory();
		}

		GenericServlet servlet = new GenericServletAdapter();
		try {
			servlet.init(new DelegatingServletConfig());
		} catch (ServletException ex) {
			throw new BeanInitializationException(
					"Initialization of GenericServlet adapter failed", ex);
		}
		this.servletContextHashModel = new ServletContextHashModel(servlet,
				getObjectWrapper());
	}

	protected FreeMarkerConfig autodetectConfiguration() throws BeansException {
		try {
			return (FreeMarkerConfig) BeanFactoryUtils
					.beanOfTypeIncludingAncestors(getApplicationContext(),
							FreeMarkerConfig.class, true, false);
		} catch (NoSuchBeanDefinitionException ex) {
			throw new ApplicationContextException(
					"Must define a single FreeMarkerConfig bean in this web application context (may be inherited): FreeMarkerConfigurer is the usual implementation. This bean may be given any name.",
					ex);
		}
	}

	protected ObjectWrapper getObjectWrapper() {
		ObjectWrapper ow = getConfiguration().getObjectWrapper();
		return ow != null ? ow : ObjectWrapper.DEFAULT_WRAPPER;
	}

	public boolean checkResource(Locale locale) {
		try {
			getTemplate(getUrl(), locale);
			return true;
		} catch (FileNotFoundException localFileNotFoundException) {
			logger.error("No FreeMarker view found for URL: " + getUrl(),localFileNotFoundException.getMessage());
			return false;
		} catch (ParseException ex) {
			throw new ApplicationContextException(
					"Failed to parse FreeMarker template for URL [" + getUrl()
							+ "]", ex);
		} catch (IOException ex) {
			throw new ApplicationContextException(
					"Could not load FreeMarker template for URL [" + getUrl()
							+ "]", ex);
		}
	}

	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		exposeHelpers(model, request);
		doRender(model, request, response);
	}

	protected void exposeHelpers(Map<String, Object> model,
			HttpServletRequest request) {
	}

	protected void doRender(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		exposeModelAsRequestAttributes(model, request);

		SimpleHash fmModel = buildTemplateModel(model, request, response);

		logger.debug("Rendering FreeMarker template [" + getUrl()
				+ "] in FreeMarkerView '" + getBeanName() + "'");

		Locale locale = RequestContextUtils.getLocale(request);
		if (StringUtils.isNotBlank(request.getParameter("jsonpCallback"))) {
			String jsonP = JsonUtils.toJsonString(FreeMarkerTemplateUtils.processTemplateIntoString(getTemplate(locale), fmModel));
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("utf-8");
			response.getOutputStream().write(jsonP.getBytes());

		} else {
			processTemplate(getTemplate(locale), fmModel, response);
		}

	}

	protected SimpleHash buildTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {
		AllHttpScopesHashModel fmModel = new AllHttpScopesHashModel(
				getObjectWrapper(), getServletContext(), request);
		fmModel.put("JspTaglibs", this.taglibFactory);
		fmModel.put("Application", this.servletContextHashModel);
		fmModel.put("Session", buildSessionModel(request, response));
		fmModel.put("Request", new HttpRequestHashModel(request, response,
				getObjectWrapper()));
		fmModel.put("RequestParameters", new HttpRequestParametersHashModel(
				request));
		fmModel.putAll(model);
		return fmModel;
	}

	private HttpSessionHashModel buildSessionModel(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return new HttpSessionHashModel(session, getObjectWrapper());
		}

		return new HttpSessionHashModel(null, request, response,
				getObjectWrapper());
	}

	protected Template getTemplate(Locale locale) throws IOException {
		return getTemplate(getUrl(), locale);
	}

	protected Template getTemplate(String name, Locale locale)
			throws IOException {
		return getEncoding() != null ? getConfiguration().getTemplate(name,
				locale, getEncoding()) : getConfiguration().getTemplate(name,
				locale);
	}

	protected void processTemplate(Template template, SimpleHash model,
			HttpServletResponse response) throws IOException, TemplateException {
		template.process(model, response.getWriter());
	}

	private class DelegatingServletConfig implements ServletConfig {
		private DelegatingServletConfig() {
		}

		public String getServletName() {
			return PFWFreeMarkerView.this.getBeanName();
		}

		public ServletContext getServletContext() {
			return PFWFreeMarkerView.this.getServletContext();
		}

		public String getInitParameter(String paramName) {
			return null;
		}

		public Enumeration<String> getInitParameterNames() {
			return Collections.enumeration(new HashSet<String>());
		}
	}

	private static class GenericServletAdapter extends GenericServlet {
		private static final long serialVersionUID = -489779104786781312L;

		public void service(ServletRequest servletRequest,
				ServletResponse servletResponse) {
		}
	}
}
