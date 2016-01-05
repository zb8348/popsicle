package com.pfw.popsicle.base.crud;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pfw.popsicle.common.DateUtil;

public abstract class CrudController<S extends CrudService<E>, E> implements InitializingBean {

	private String path;// eg:"","xxx/aa"
	private String ctlName;// eg:"user","role","user_role"
	private S service;

	public abstract S initService();
	public abstract String getLoginName();
	public abstract E createNewEntity();
	public abstract String getMVCJSPath();//默认指定/statics/popsicle/js/web 后的路径

	public void afterPropertiesSet() throws Exception {
		service = initService();
		path = initBasePath();
		ctlName = initCtlName();
	}
	public String initBasePath(){
		return "crud";
	};
	public String initCtlName(){
		return "crud";
	};
	
	@RequestMapping(value = "/")
	public String home(Model model) {
		model.addAttribute("mvc_js", getMVCJSPath());
		return path + "/" + ctlName;
	}

	@InitBinder("entity")   
    public void initBinder(WebDataBinder binder) {   
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
//        dateFormat.setLenient(true);   
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }  
	
	
	@RequestMapping(value = "/r",method = RequestMethod.POST)
	public @ResponseBody ResponsePageVO<E> find(@ModelAttribute("entity") E e,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "length", defaultValue = "10") int length,
			@RequestParam(value = "orderName", defaultValue = "") String orderName,
			@RequestParam(value = "orderType", defaultValue = "asc") String orderType, Model model) {
		beforeFinde(e);
		return service.findPage(e, start, length,orderName,orderType);
	}
	
	
	public static <E> E createInstance(Class<E> cls) {
        E obj=null;
        try {
            obj=cls.newInstance();
        } catch (Exception e) {
            obj=null;
        }
        return obj;
    }
	
	@ModelAttribute("e")
	public E getE(@RequestParam(value = "id", required = false) Long id) {
		if (id!=null) {
			return service.getById(id);
		}
		return createNewEntity();
	}
	@RequestMapping(value = "/c",method = RequestMethod.POST)
	public @ResponseBody ResponseResultVO<E> create(@ModelAttribute("entity") E e) {
		beforeCreate(e);
		if(e instanceof CrudRecordEntity){
			((CrudRecordEntity) e).setValid(Boolean.TRUE);
			((CrudRecordEntity) e).setCreateTime(DateUtil.getChineseTime());
			((CrudRecordEntity) e).setCreator(getLoginName());
		}
		return service.save(e);
	}
	@RequestMapping(value = "/u",method = RequestMethod.POST)
	public @ResponseBody ResponseResultVO<E> update(@ModelAttribute("e") E e) {
		beforeUpdate(e);
		if(e instanceof CrudRecordEntity){
			((CrudRecordEntity) e).setModifiedTime(DateUtil.getChineseTime());
			((CrudRecordEntity) e).setModifier(getLoginName());
		}
		return service.update(e);
	}
	
//	@RequestMapping(value = "/d")
//	public @ResponseBody ResponseResultVO<Boolean> delete(@RequestParam(value="ids[]",required=true)  List<Long> ids) {
//		beforeDelete(ids);
//		return service.delete(ids);
//	}
	@RequestMapping(value = "/d")
	public @ResponseBody ResponseResultVO<Boolean> delete(@RequestParam(value="id",required=true)List<Long> ids) {
		beforeDelete(ids);
		return service.delete(ids);
	}
	void beforeFinde(Object ...e){}
	void beforeCreate(Object ...e){}
	void beforeUpdate(Object ...e){}
	void beforeDelete(Object ...e){}
}
