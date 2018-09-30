package com.cccuu.project.service.operationrecord.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.mapper.operationrecord.OperationRecordMapper;
import com.cccuu.project.service.operationrecord.OperationRecordService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 操作记录ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月23日 11:02:17
 */
@Service("operationRecordService")
public class OperationRecordServiceImpl extends BaseServiceImpl<OperationRecord> implements OperationRecordService{
	
	private OperationRecordMapper operationRecordMapper;
	
	@Autowired
	public void setOperationRecordMapper(OperationRecordMapper operationRecordMapper){
		this.operationRecordMapper = operationRecordMapper;
		baseMapper = operationRecordMapper;
	}
	@Override
	@Transactional
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params){
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		String orderTel = params.get("orderTel");
		String[] telList = orderTel.split(",");
		parameterMap.put("telList", telList);
		String dealerId=params.get("dealerId");
		parameterMap.put("dealerId",dealerId);

		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Map<String,Object>> results = operationRecordMapper.queryListByParams(parameterMap);
		
		return new PageInfo<Map<String,Object>>(results);
	}

	@Override
	@Transactional
	public int addMulti(List<OperationRecord> list) {
		return operationRecordMapper.addMulti(list);
	}

}