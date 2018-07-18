package com.cccuu.project.service.food;

import com.cccuu.project.model.DTO.FoodDTO;
import com.cccuu.project.model.food.Food;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
/**
 * 食品Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
public interface FoodService extends BaseService<Food>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);

	/**
	 * 查询所有
	 * @return
	 */
	public List<Map<String,Object>> queryAll();

	/**
	 * 根据条件查询
	 * @return
	 */
	public List<Map<String,Object>> queryListByMap(Map<String,String> params);

	/**
	 * 根据条件查询
	 * @param params
	 * @return
	 */
	public List<FoodDTO> queryListByMapDouble(Map<String,String> params);

}