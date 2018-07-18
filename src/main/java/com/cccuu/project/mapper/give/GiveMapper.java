package com.cccuu.project.mapper.give;

import com.cccuu.project.model.give.Give;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 赠送Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月12日 17:42:30
 */
public interface GiveMapper extends BaseMapper<Give>{
    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 根据参数查询赠送
     * @param params
     * @return
     */
    public Map<String,Object> queryByParams(Map<String,Object> params);

    /**
     * 修改备注
     * @param map
     */
    public void updateBeizhu(Map<String ,String> map);

    /**
     * 根据赠送表id查赠送记录
     * @param map
     * @return
     */
    public List<Map<String,Object>> queryProductByGiveId(String giveId);

    /**
     * 查询订单是否已经公司赠送或经销商赠送
     * @param params
     * @return
     */
    public Integer queryIsGive(String orderId);

    /**
     * 查询唯一编号最大值
     * @return
     */
    public Integer onlyNumMax();

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByIdS(Map<String,Object> params);

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByIdSZH(Map<String,Object> params);

    /**
     * 根据ids批量删除
     * @param params
     * @return
     */
    public void deleteByIds(Map<String,Object> params);

    /**
     * 综合查询
     * @param params
     * @return
     */
    public List<Map<String,Object>> zhQuery(Map<String,Object> params);

    /**
     * 判断今日是否赠送给经销商
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryIsGiveDealer(Map<String,Object> params);

    public void updateNameOrder(Map<String ,String> map);

    public void updateAddressOrder(Map<String ,String> map);

    public void updateNameDealer(Map<String ,String> map);

    public void updateAddressDealer(Map<String ,String> map);
}