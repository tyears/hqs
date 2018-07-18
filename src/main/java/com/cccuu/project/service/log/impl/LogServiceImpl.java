package com.cccuu.project.service.log.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.cccuu.project.mapper.log.LogMapper;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log> implements LogService {
	
	private LogMapper logMapper;

	@Autowired
	public void setLogMapper(LogMapper logMapper) {
		this.logMapper = logMapper;
		baseMapper = logMapper;
	}

	@Override
	@Transactional
	public PageInfo<Map<String,Object>> queryListByPage(Map<String, String> params) {
		// TODO Auto-generated method stub
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Map<String,Object>> results = null;

		if("0".equals(params.get("type"))){
			results = logMapper.queryListByParams(parameterMap);
		}else{
			results = logMapper.queryListByParams2(parameterMap);
		}
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

}
