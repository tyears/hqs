package com.cccuu.project.service.product;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 产品Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:39
 */
public interface ProductService extends BaseService<Product>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);

	/**
	 * 分页查询列表(后台)
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPageSys(Map<String,String> params);

	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<Product> queryList(Map<String,String> params);

	/**
	 * 保存信息
	 * @param product
	 * @param foodIds
	 * @return
	 */
	public Product saveInfo(Product product,String foodIds);

	/**
	 * 根据id删除信息
	 * @param id
	 */
	public void deleteInfoById(String id);
	/**
	 * 根据食品id查询适用产品列表
	 * @param foodId
	 * @return
	 */
	public List<Map<String,Object>> queryListByFoodId(String foodId);

	/**
	 * 根据关键字查询列表
	 * @param keyWord
	 * @return
	 */
	public List<Map<String,Object>> queryListByKeyWord(String keyWord);

	/**
	 * 查询和客户关联的全部产品
	 * @param dealerId
	 * @return
	 */
	public List<Map<String,Object>> queryProductByDealerId(String dealerId);

	/**
	 * 判断货号是否存在
	 * @param product
	 * @return
	 */
	public List<Map<String ,Object>> isProductNum(Product product);

	/**
	 * 批量插入
	 * @param productList
	 * @return
	 */
	public int addMulti(List<Product> productList);

	/**
	 * 赠送产品查询
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>>  queryProductByNum(Map<String,String> params);

	/**
	 * 删除产品
	 * @param id
	 */
	public void deleteProduct(String id, HttpServletRequest request);

}