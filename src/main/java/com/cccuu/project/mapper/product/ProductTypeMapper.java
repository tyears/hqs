package com.cccuu.project.mapper.product;

import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 产品类别Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:19
 */
public interface ProductTypeMapper extends BaseMapper<ProductType>{

    public List<Map<String,Object>> checkRepeat(String id);
}