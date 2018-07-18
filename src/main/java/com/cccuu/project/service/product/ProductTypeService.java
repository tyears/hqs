package com.cccuu.project.service.product;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 产品类别Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:19
 */
public interface ProductTypeService extends BaseService<ProductType>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<ProductType> queryListByPage(Map<String,String> params);

	/**
	 * 根据id删除记录信息
	 * @param id
	 */
	public void deleteInfoById(String id);

	/**
	 * 判断是否名称重复
	 * @return
	 */
	public List<ProductType> queryListByName(String typeName, String typeId);
}