package com.pfw.popsicle.tools.crud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
//@Inherited 注解 ：用于指定被描述的注解可以被其所描述的类的子类继承。默认情况
public @interface CrudEntity {
	String name();//名称
	String mappingName();//数据库映射
}
