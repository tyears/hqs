package com.cccuu.project.service.food.impl;

import com.cccuu.project.mapper.food.FoodMapper;
import com.cccuu.project.model.DTO.FoodDTO;
import com.cccuu.project.model.food.Food;
import com.cccuu.project.service.food.FoodService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 食品ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
@Service("foodService")
public class FoodServiceImpl extends BaseServiceImpl<Food> implements FoodService{
	
	private FoodMapper foodMapper;
	
	@Autowired
	public void setFoodMapper(FoodMapper foodMapper){
		this.foodMapper = foodMapper;
		baseMapper = foodMapper;
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
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Map<String,Object>> results = foodMapper.queryListByParams(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryAll() {
		return foodMapper.queryAll();
	}

	@Override
	public List<Map<String, Object>> queryListByMap(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		return foodMapper.queryListByParams(parameterMap);
	}

	@Override
	public List<FoodDTO> queryListByMapDouble(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		return foodMapper.queryListByMapDouble(parameterMap);
	}
}