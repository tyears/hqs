package com.cccuu.project.service.food;

import java.util.Map;
import com.cccuu.project.model.food.FoodType;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 食品类型Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:27
 */
public interface FoodTypeService extends BaseService<FoodType>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<FoodType> queryListByPage(Map<String,String> params);

	/**
	 * 根据id删除信息
	 * @param id
	 */
	public void deleteInfoById(String id);

}