package com.cccuu.project.mapper.order;

import com.cccuu.project.model.order.Order;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 订单Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月18日 15:19:31
 */
public interface OrderMapper extends BaseMapper<Order>{

    /**
     * 调取登录用户待处理最新订单
     * @param params
     * @return
     */
    public Order obtainOrder(Map<String,Object> params);

    /**
     * 查询咨询信息
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryConsultInfo(Map<String,Object> params);

    /**
     * 获取最大订单编号
     * @return
     */
    public int getOrdernumMax();

    /**
     * 根据编号获取订单,并且没有掉单人id
     * @param num
     * @return
     */
    public Map isOrderByNum(Map<String ,Object> map);

    /**
     * 获取订单总数
     * @return
     */
    public int allNum(Map<String ,Object> map);

    /**
     * 获取待处理订单
     * @return
     */
    public int dclNum(Map<String,Object> params);
    /**
     * 全部变为未导入
     * @return
     */
    public int updateImport();

    /**
     * 根据条件查询
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 查单数据
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryOrderData(Map<String,Object> params);

    /**
     * 根据手机号匹配询单，填充单位市场
     * @param params
     * @return
     */
    public List<Map<String,Object>> matchOrderAreaBytel(Map<String,Object> params);
}