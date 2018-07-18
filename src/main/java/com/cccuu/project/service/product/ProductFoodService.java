package com.cccuu.project.service.product;

import java.util.Map;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 产品适用食品Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:40:47
 */
public interface ProductFoodService extends BaseService<ProductFood>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<ProductFood> queryListByPage(Map<String,String> params);

}