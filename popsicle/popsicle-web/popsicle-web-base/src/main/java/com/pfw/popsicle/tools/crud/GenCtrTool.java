package com.pfw.popsicle.tools.crud;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pfw.popsicle.common.JsonUtils;
import com.pfw.popsicle.tools.crud.entity.Create;
import com.pfw.popsicle.tools.crud.entity.Delete;
import com.pfw.popsicle.tools.crud.entity.Find;
import com.pfw.popsicle.tools.crud.entity.ListValue;
import com.pfw.popsicle.tools.crud.entity.Update;
import com.pfw.popsicle.tools.crud.entity.Validate;
import com.pfw.popsicle.util.FreemarkerUtil;

public class GenCtrTool {
	private static final String pathPrefix="/template/crud";
	
	public static void gen(Class<?> entity) {
		String orgName="com.pfw.popsicle";
		System.out.println("==========================================================");
		System.out.println("= "+orgName);
		System.out.println("= 注意——适应前提：");
		System.err.println("=1.实体key 必输是 Long id");
		System.err.println("=2.实体的路径 必须在 a.xxx.entity 下面");
		System.err.println("=3.自动生成的mybatis 文件需要手动调整	");
		System.err.println("=4.如果需求生成的sql脚本，务必要调整");
		System.out.println("==========================================================");
		String entityName = "";
		String daoName = "";
		String serviceName = "";
		String ctrlName = "";
		String packageName = "";
//		String filePath = "";
		String tableName= "";
		String projectPath = System.getProperty("user.dir");
		
		// 检查类AnnotationTest是否含有@MyAnnotation注解
		if (entity.isAnnotationPresent(CrudEntity.class)) {
			entityName = entity.getSimpleName();
			packageName = entity.getPackage().getName().replace(".entity", "");
//			filePath = entity.getClass().getResource("/").getPath();
			
			CrudEntity classAnon = entity.getAnnotation(CrudEntity.class);
			tableName = classAnon.mappingName();
					
			Map<String,String> dbMapping = new HashMap<String, String>();
			// 若存在就获取注解
			Field[] fls = entity.getDeclaredFields();
			
			List<Map<String,Object>> entityJson = new ArrayList<Map<String,Object>>();
			
			CrudAttr cAnon=null;
			Create cCreateAnon=null;
			Delete cDeleteAnon=null;
			Update cUpdateAnon=null;
			Find cFindAnon=null;
			Validate cValidateAnon=null;
			for (Field field : fls) {
				if(field.isAnnotationPresent(CrudAttr.class)){
					Map<String,Object> att = new HashMap<String, Object>();
					att.put("attr", field.getName());
					cAnon = field.getAnnotation(CrudAttr.class);
					parseAttr(cAnon,att);
					
					dbMapping.put(field.getName(),cAnon.mappingName());
					if(field.isAnnotationPresent(Create.class)){
						Map<String,Object> crudMap = new HashMap<String, Object>();
						cCreateAnon = field.getAnnotation(Create.class);
						parseCrud(cCreateAnon,crudMap);
						att.put("create", crudMap);
					}
					if(field.isAnnotationPresent(Delete.class)){
						Map<String,Object> crudMap = new HashMap<String, Object>();
						cDeleteAnon = field.getAnnotation(Delete.class);
						parseCrud(cDeleteAnon,crudMap);
						att.put("delete", crudMap);
					}
					if(field.isAnnotationPresent(Update.class)){
						Map<String,Object> crudMap = new HashMap<String, Object>();
						cUpdateAnon = field.getAnnotation(Update.class);
						parseCrud(cUpdateAnon,crudMap);
						att.put("update", crudMap);
					}
					if(field.isAnnotationPresent(Find.class)){
						Map<String,Object> crudMap = new HashMap<String, Object>();
						cFindAnon = field.getAnnotation(Find.class);
						parseCrud(cFindAnon,crudMap);
						att.put("find", crudMap);
					}
					if(field.isAnnotationPresent(Validate.class)){
						Map<String,Object> crudMap = new HashMap<String, Object>();
						cValidateAnon = field.getAnnotation(Validate.class);
						parseCrud(cValidateAnon,crudMap);
						att.put("validate", crudMap);
					}
					
					entityJson.add(att);
				}
			}
			
			String javaPath =projectPath+"/src/main/java/"+packageName.replace(".","/");
			daoName = entityName+"Dao";
			String daoPath=javaPath+"/dao/"+daoName+".java";
			genDao(daoPath,packageName,daoName,entityName);
			
			String sqlPath =projectPath+"/sql/create/"+entityName+".sql";
			genSql(sqlPath,tableName,dbMapping);
			
			String sqlMappingPath =projectPath+"/src/main/resources/conf/mybatis/Crud_"+entityName+"_SqlMap.xml";
			genSqlMapping(sqlMappingPath,packageName,daoName,entityName,dbMapping,tableName,entityJson);
			
			serviceName =  entityName+"Service";
			genService(javaPath,packageName,serviceName,entityName);

			
			String ctrUrl = packageName.replace(orgName, "").replace(".", "/");
			ctrlName = entityName+"Controller";
			String jsPath=projectPath+"/src/main/webapp/statics/popsicle/js/web/"+ctrUrl+"/"+ctrlName+".js";
			genJs(jsPath,JsonUtils.toJsonString(entityJson),ctrUrl);
			
			String ctlPath=javaPath+"/web/"+ctrlName+".java";
			genCtl(ctlPath,packageName,ctrlName,entityName,ctrUrl);
		}
	}

	public static void genCtl(String ctlPath,String packageName,String ctrlName,String entityName,String ctrUrl){
		Map<String,String> pars = new HashMap<String, String>();
		pars.put("packageName", packageName);
		pars.put("ctrlName", ctrlName);
		pars.put("entityName", entityName);
		pars.put("ctrUrl", ctrUrl);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "CrudController.ftl", pars);
			File file = new File(ctlPath);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void genService(String javaPath,String packageName,String serviceName,String entityName){
		String servicePath=javaPath+"/service/"+serviceName+".java";
		
		Map<String,String> pars = new HashMap<String, String>();
		pars.put("packageName", packageName);
		pars.put("serviceName", serviceName);
		pars.put("entityName", entityName);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "CrudService.ftl", pars);
			File file = new File(servicePath);
			FileUtils.writeStringToFile(file, content);
			
			String serviceImplPath=javaPath+"/service/impl/"+serviceName+"Impl.java";
			String content2 = FreemarkerUtil.writeHtml(pathPrefix, "CrudServiceImpl.ftl", pars);
			File file2 = new File(serviceImplPath);
			FileUtils.writeStringToFile(file2, content2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genSql(String sqlPath,String tableName,Map<String,String> mapping){
		Map<String,Object> pars = new HashMap<String, Object>();
		pars.put("tableName", tableName);
		pars.put("mapping", mapping);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "Crud_Sql.ftl", pars);
			File file = new File(sqlPath);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genSqlMapping(String sqlMappingPath,String packageName,String daoName,String entityName,Map<String,String> mapping,String tableName,List<Map<String,Object>> entityJson){
		Map<String,Object> pars = new HashMap<String, Object>();
		pars.put("packageName", packageName);
		pars.put("daoName", daoName);
		pars.put("entityName", entityName);
		pars.put("mapping", mapping);
		pars.put("tableName", tableName);
		pars.put("entityJson", entityJson);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "Crud_SqlMap.ftl", pars);
			File file = new File(sqlMappingPath);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void genDao(String daoPath,String packageName,String daoName,String entityName){
		Map<String,String> pars = new HashMap<String, String>();
		pars.put("packageName", packageName);
		pars.put("daoName", daoName);
		pars.put("entityName", entityName);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "CrudDao.ftl", pars);
			File file = new File(daoPath);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void genJs(String jsPath,String jsonEntityinfo,String ctrUrl){
		Map<String,String> pars = new HashMap<String, String>();
		pars.put("jsonEntityinfo", jsonEntityinfo);
		pars.put("preUrl", ctrUrl);
		try {
			String content = FreemarkerUtil.writeHtml(pathPrefix, "CrudCtrlJs.ftl", pars);
			File file = new File(jsPath);
			FileUtils.writeStringToFile(file, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void parseCrud(Validate cValidateAnon, Map<String, Object> result) {
		result.put("rules",JSON.parse(cValidateAnon.rules()));
		result.put("messages",JSON.parse( cValidateAnon.messages()));
	}

	private static void parseCrud(Find cFindAnon, Map<String, Object> result) {
		result.put("type", cFindAnon.type());
		result.put("defualtValue", cFindAnon.defualtValue());
		ListValue[] list = cFindAnon.list();
		if(list!=null&&list.length>0){
			List<Map<String,Object>> listS = new ArrayList<Map<String,Object>>();
			for (ListValue listValue : list) {
				Map<String,Object> att = new HashMap<String, Object>();
				att.put("name", listValue.name());
				att.put("value", listValue.value());
				listS.add(att);
			}
			result.put("list",listS);
		}
	}

	private static void parseCrud(Update cUpdateAnon, Map<String, Object> result) {
		result.put("type", cUpdateAnon.type());
		result.put("defualtValue", cUpdateAnon.defualtValue());
		ListValue[] list = cUpdateAnon.list();
		if(list!=null&&list.length>0){
			List<Map<String,Object>> listS = new ArrayList<Map<String,Object>>();
			for (ListValue listValue : list) {
				Map<String,Object> att = new HashMap<String, Object>();
				att.put("name", listValue.name());
				att.put("value", listValue.value());
				listS.add(att);
			}
			result.put("list",listS);
		}
	}

	private static void parseCrud(Delete cDeleteAnon, Map<String, Object> result) {
		result.put("type", cDeleteAnon.type());
	}

	private static void parseCrud(Create cCreateAnon, Map<String, Object> result) {
		result.put("type", cCreateAnon.type());
		result.put("defualtValue", cCreateAnon.defualtValue());
		ListValue[] list = cCreateAnon.list();
		if(list!=null&&list.length>0){
			List<Map<String,Object>> listS = new ArrayList<Map<String,Object>>();
			for (ListValue listValue : list) {
				Map<String,Object> att = new HashMap<String, Object>();
				att.put("name", listValue.name());
				att.put("value", listValue.value());
				listS.add(att);
			}
			result.put("list",listS);
		}
	}

	private static void parseAttr(CrudAttr attr,Map<String, Object> result){
		result.put("name", attr.name());
		//result.put("mappingName", attr.mappingName());
		result.put("visible", attr.visible());
		result.put("orderable", attr.orderable());
	}
}
