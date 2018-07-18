package com.cccuu.project.mapper.food;

import com.cccuu.project.model.DTO.FoodDTO;
import com.cccuu.project.model.food.Food;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 食品Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
public interface FoodMapper extends BaseMapper<Food>{
    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 查询所有
     * @return
     */
    public List<Map<String,Object>>queryAll();

    public List<FoodDTO> queryListByMapDouble(Map<String,Object> params);
}