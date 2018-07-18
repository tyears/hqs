package com.cccuu.project.service.${packageName};

import java.util.Map;
import com.cccuu.project.model.${packageName}.${className};
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * ${funName}Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date ${date?string("yyyy年MM月dd日 HH:mm:ss")}
 */
public interface ${className}Service extends BaseService<${className}>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<${className}> queryListByPage(Map<String,String> params);

}