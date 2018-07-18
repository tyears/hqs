package com.cccuu.project.service.${packageName}.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.${packageName}.${className};
import com.cccuu.project.mapper.${packageName}.${className}Mapper;
import com.cccuu.project.service.${packageName}.${className}Service;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * ${funName}ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date ${date?string("yyyy年MM月dd日 HH:mm:ss")}
 */
@Service("${className?uncap_first}Service")
public class ${className}ServiceImpl extends BaseServiceImpl<${className}> implements ${className}Service{
	
	private ${className}Mapper ${className?uncap_first}Mapper;
	
	@Autowired
	public void set${className}Mapper(${className}Mapper ${className?uncap_first}Mapper){
		this.${className?uncap_first}Mapper = ${className?uncap_first}Mapper;
		baseMapper = ${className?uncap_first}Mapper;
	}
	@Override
	@Transactional
	public PageInfo<${className}> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(${className}.class);
		Example.Criteria creiteria = example.createCriteria();
		
		<#if tableColumns?exists>
		<#list tableColumns as column>
		<#if column.isQuery=='y'>
		String ${column.filedName} = params.get("${column.filedName}");
		if(StringUtils.isNotBlank(${column.filedName})){
			creiteria.andLike("${column.filedName}","%"+${column.filedName}+"%");
		}
		</#if>
		</#list>
		</#if>
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<${className}> results = ${className?uncap_first}Mapper.selectByExample(example);
		
		PageInfo<${className}> pageInfo = new PageInfo<${className}>(results);
		
		return pageInfo;
	}

}