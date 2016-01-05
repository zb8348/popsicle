package com.pfw.popsicle.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class PopSpringBeanFactory implements BeanFactoryAware{
	private volatile static BeanFactory beanFactory;  
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		PopSpringBeanFactory.init(beanFactory);
	}
	
	/**
	 * fixed:  高危 - 通过一个实例方法更新静态属性  
	 * @param factory
	 */
	private static synchronized void init(BeanFactory factory){
		beanFactory = factory;
	}
	/**
	 * 从spring配置文件中,获取id对应的对象
	 * @param beanName  查找的bean的id
	 * 
	 * @return beanName 对应的对象
	 */
	public static Object getBean(String beanName){
		return beanFactory.getBean(beanName);
	}
	
	/**
	 * 从spring配置文件中,获取id对应的对象
	 * @param beanName  查找的bean的id
	 * 
	 * @return beanName 对应的对象
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getBean(String beanName, Class requiredType){
		return beanFactory.getBean(beanName, requiredType);
	}
}
