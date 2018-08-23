package com.cccuu.project.mapper.dealerproduct;

import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 客户产品关联Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:44:27
 */
public interface DealerProductMapper extends BaseMapper<DealerProduct>{

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 根据参数查询导入列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListImportByParams(Map<String,Object> params);

    /**
     * 修改备注
     * @param params
     */
    public void updateRemark(Map<String,String> params);

    /**
     * 修改评价
     * @param params
     */
    public void updateComment(Map<String,String> params);

    /**
     * 修改是否导入
     * @param params
     */
    public void updateImport(Map<String,String> params);

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int addMulti(List<DealerProduct> list);

    /**
     * 查询单个经销商的产品
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryProductByDealerId(Map<String,Object> params);

    /**
     * 查询单个经销商产品关联
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryOne(Map<String,String> map);
    public void updateDealerProductMerit();
}