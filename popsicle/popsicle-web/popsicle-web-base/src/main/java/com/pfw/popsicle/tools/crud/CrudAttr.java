package com.pfw.popsicle.tools.crud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CrudAttr {

//	String attr();//属性
	String name();//名称
	String mappingName();//数据库映射
	boolean visible() default false;//是否可见
	boolean orderable() default false;//是否可排序
}
