package ${packageName}.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import ${packageName}.dao.${entityName}Dao;
import com.pfw.popsicle.base.crud.impl.CrudServiceImpl;
import ${packageName}.entity.${entityName};
import ${packageName}.service.${serviceName};
/**
 * 
 * @author wulibing
 *
 */
@Service("${serviceName?uncap_first}")
@Transactional
//or : public class ${serviceName}Impl implements CrudService<${entityName}> {
public class ${serviceName}Impl extends CrudServiceImpl<${entityName},${entityName}Dao> implements ${serviceName}{	
	@Autowired
	private ${entityName}Dao ${entityName?uncap_first}Dao;
	@Override
	public ${entityName}Dao getDao() {
		return ${entityName?uncap_first}Dao;
	}	
}
