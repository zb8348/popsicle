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
import com.pfw.popsicle.tools.crud.entity.Validate;

@CrudEntity(name = "crud用户示例", mappingName = "t_demo_user")
public class UserEntity {// extends CrudRecordEntity{
	@CrudAttr(name = "ID", mappingName = "id")
	@Update(type = HtmlType.hidden)
	@Delete(type = HtmlType.checkbox)
	private Long id;

	@CrudAttr(name = "姓名", mappingName = "name", visible = true, orderable = true)
	@Create(type = HtmlType.text)
	@Delete(type = HtmlType.none)
	@Update(type = HtmlType.text)
	@Find(type = HtmlType.text)
	@Validate(rules = "{name: {required:true,minlength: 2,maxlength:30}}",
		messages = "{name: {required: '没有填写姓名',minlength: '姓名不能小于{0}个字符',maxlength: '分组名不能大于{0}个字符'}}")
	private String name;

	@CrudAttr(name = "性别", mappingName = "sex", visible = true, orderable = true)
	@Create(type = HtmlType.radio, defualtValue = "0", list = { @ListValue(name = "不详", value = "0"),
			@ListValue(name = "男", value = "1"), @ListValue(name = "女", value = "2") })
	@Update(type = HtmlType.radio, defualtValue = "0", list = { @ListValue(name = "不详", value = "0"),
			@ListValue(name = "男", value = "1"), @ListValue(name = "女", value = "2") })
	@Delete(type = HtmlType.none)
	@Find(type = HtmlType.radio, list = { @ListValue(name = "不详", value = "0"),
			@ListValue(name = "男", value = "1"), @ListValue(name = "女", value = "2") })
	@Validate(rules = "{sex: {required:true}}")
	private Integer sex;

	@CrudAttr(name = "创建时间", mappingName = "create_time", visible = true, orderable = true)
	@Create(type = HtmlType.dateTime)
	@Find(type = HtmlType.dateTime)
	private Date createTime;

	@CrudAttr(name = "备注", mappingName = "remark", visible = false, orderable = false)
	@Create(type = HtmlType.textarea)
	@Update(type = HtmlType.textarea)
	private String remark;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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
