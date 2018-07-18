package com.cccuu.project.mapper.product;

import com.cccuu.project.model.product.Product;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 产品Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:39
 */
public interface ProductMapper extends BaseMapper<Product>{
    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 根据参数查询列表(后台)
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParamsSys(Map<String,Object> params);

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
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryProductByDealerId(String dealerId);

    /**
     * 判断货号是否存在
     * @param product
     * @return
     */
    public List<Map<String,Object>> isProductNum(Product product);

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
}