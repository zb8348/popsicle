package com.pfw.popsicle.demo.crud.entity;

import java.util.Date;

import com.pfw.popsicle.tools.crud.CrudAttr;
import com.pfw.popsicle.tools.crud.CrudEntity;
import com.pfw.popsicle.tools.crud.GenCtrTool;
import com.pfw.popsicle.tools.crud.entity.Create;
import com.pfw.popsicle.tools.crud.entity.Delete;
import com.pfw.popsicle.tools.crud.entity.Find;
import com.pfw.popsicle.tools.crud.entity.HtmlType;
import com.pfw.popsicle.tools.crud.entity.ListValue;
import com.pfw.popsicle.tools.crud.entity.Update;

@CrudEntity(name="用户",mappingName="t_user")
public class UserEntity {//extends CrudRecordEntity<String>{
//	String name();//名称
//	String mappingName();//数据库映射
//	boolean visible() default false;//是否可见
//	boolean orderable() default false;//是否可排序
	@CrudAttr(name="ID",mappingName="id")
	@Update(type=HtmlType.hidden)
	@Delete(type=HtmlType.checkbox)
	private Long id;
	@CrudAttr(name="姓名",mappingName="name",visible=true,orderable=true)
	@Create(type=HtmlType.text)
	@Delete(type=HtmlType.none)
	@Update(type=HtmlType.text)
	@Find(type=HtmlType.text)
	private String name;
	@CrudAttr(name="性别",mappingName="sex",visible=true,orderable=true)
	@Create(type=HtmlType.text,defualtValue="0",list={@ListValue(name="不详",value="0"),@ListValue(name="男",value="1"),@ListValue(name="女",value="2")})
	@Update(type=HtmlType.text)
	@Find(type=HtmlType.text)
	private long sex;
	@CrudAttr(name="创建时间",mappingName="create_time",visible=true,orderable=true)
//	@Create(type=HtmlType.text)
//	@Delete
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public static void main(String[] args) {
		GenCtrTool.gen(UserEntity.class);
	}
}
