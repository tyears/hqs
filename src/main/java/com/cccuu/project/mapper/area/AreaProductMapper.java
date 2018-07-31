package com.cccuu.project.mapper.area;

import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 单位市场产品Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:25:37
 */
public interface AreaProductMapper extends BaseMapper<AreaProduct>{

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);
    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParamsPage(Map<String,Object> params);

    /**
     * 修改公司通知或经销商赠送
     * @param params
     */
    public void updateJssOrGs(Map<String,String > params);

    /**
     * 修改单位市场评价
     * @param params
     */
    public void updateComment(Map<String,String > params);

    /**
     * 修改单位市场导入状态
     */
    public void updateAreaImport();

    /**
     * 修改单位市场下的产品评价
     * @param params
     */
    public void updateAreaProductComment(Map<String,String > params);


    /**
     * 修改单位市场下产品的导入状态
     */
    public void updateAreaProductImport();

    /**
     * 查询单位市场导入评价
     * @param params
     */
    public List<Map<String,Object>> queryAreaImport(Map<String,Object> params);


    /**
     * 查询单位市场下的产品导入评价
     * @param params
     */
    public List<Map<String,Object>> queryAreaProductImport(Map<String,Object> params);

    /**
     * 查询全部单位市场
     */
    public List<Map<String,Object>> queryAreaList(Map<String,Object> paramsMap);

    /**
     * 根据全名称查询
     * @return
     */
    public List<Map<String,Object>> queryAreaOne(String areaName);

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int addMulti(List<AreaProduct> list);

    /**
     * 查询单个单位市场产品关联
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryOne(Map<String,String> params);

    /**
     * 根据经销商uuid和产品id查询单位市场产品关联表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryApByUuid(Map<String,String> params);

    /**
     * 修改产品授权
     * @param params
     */
    public void updateAuthor(Map<String,String> params);

    /**
     * 修改单位市场下产品的授权导入状态
     */
    public void updateAuthorImport();

    public void emptyAuthorDealer();
}