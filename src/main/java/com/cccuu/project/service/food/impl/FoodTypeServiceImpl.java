package com.cccuu.project.service.food.impl;

import java.util.List;
import java.util.Map;

import com.cccuu.project.mapper.food.FoodMapper;
import com.cccuu.project.model.food.Food;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.food.FoodType;
import com.cccuu.project.mapper.food.FoodTypeMapper;
import com.cccuu.project.service.food.FoodTypeService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 食品类型ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:27
 */
@Service("foodTypeService")
public class FoodTypeServiceImpl extends BaseServiceImpl<FoodType> implements FoodTypeService{
	
	private FoodTypeMapper foodTypeMapper;
	
	@Autowired
	public void setFoodTypeMapper(FoodTypeMapper foodTypeMapper){
		this.foodTypeMapper = foodTypeMapper;
		baseMapper = foodTypeMapper;
	}

	@Autowired
	private FoodMapper foodMapper;
	@Override
	@Transactional
	public PageInfo<FoodType> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(FoodType.class);
		example.setOrderByClause("create_time desc");
		Example.Criteria creiteria = example.createCriteria();
		
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<FoodType> results = foodTypeMapper.selectByExample(example);
		
		PageInfo<FoodType> pageInfo = new PageInfo<FoodType>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public void deleteInfoById(String id) {
		//删除所有此分类的食品
		/*
		Food food = new Food();
		food.setTypeId(id);
		foodMapper.delete(food);
		*/
		foodTypeMapper.deleteByPrimaryKey(id);
	}
}