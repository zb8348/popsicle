package com.pfw.popsicle.util;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * @author wulibing
 *
 */
public class FreemarkerUtil {

	/**
	 * 依据freemarker模板生成静态文件
	 * @param pathPrefix   模板所在资源路径:相对路径
	 * @param templateFile 生成静态统计页面的模板（带文件名后缀）
	 * @param pars
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String writeHtml(String pathPrefix,
			String templateFile, Map pars) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(FreemarkerUtil.class, pathPrefix);
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding("UTF-8");

		Template template = cfg.getTemplate(templateFile, "utf-8");
		
		StringWriter out = new StringWriter();
		template.process(pars, out);
		return out.toString();
	}
}
