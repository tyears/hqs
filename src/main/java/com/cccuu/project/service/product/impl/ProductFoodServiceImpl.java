package com.cccuu.project.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.mapper.product.ProductFoodMapper;
import com.cccuu.project.service.product.ProductFoodService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 产品适用食品ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:40:47
 */
@Service("productFoodService")
public class ProductFoodServiceImpl extends BaseServiceImpl<ProductFood> implements ProductFoodService{
	
	private ProductFoodMapper productFoodMapper;
	
	@Autowired
	public void setProductFoodMapper(ProductFoodMapper productFoodMapper){
		this.productFoodMapper = productFoodMapper;
		baseMapper = productFoodMapper;
	}
	@Override
	@Transactional
	public PageInfo<ProductFood> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(ProductFood.class);
		Example.Criteria creiteria = example.createCriteria();
		
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<ProductFood> results = productFoodMapper.selectByExample(example);
		
		PageInfo<ProductFood> pageInfo = new PageInfo<ProductFood>(results);
		
		return pageInfo;
	}

}