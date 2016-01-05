package ${packageName}.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import ${packageName}.service.${entityName}Service;
import com.pfw.popsicle.base.crud.CrudController;
import ${packageName}.entity.${entityName};
import com.pfw.popsicle.security.utils.PFWSecurityUtil;

/**
 *
 * @author wulibing
 *
 */
		
@Controller("${ctrlName}")
@RequestMapping(value = "${ctrUrl}")
public class ${ctrlName} extends CrudController<${entityName}Service,${entityName}>{
	@Autowired
	@Qualifier("${entityName?uncap_first}Service")
	private ${entityName}Service ${entityName?uncap_first}Service;
	
	@Override
	public ${entityName}Service initService() {
		return ${entityName?uncap_first}Service;
	}
	@Override
	public String getLoginName() {
		return PFWSecurityUtil.getLoginName();
	}
	@Override
	public ${entityName} createNewEntity() {
		return new ${entityName}();
	} 
	
	public String getMVCJSPath(){
		return "${ctrUrl}/${ctrlName}.js";
	}
	
	@InitBinder("entity")   
	@Override
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }
}
