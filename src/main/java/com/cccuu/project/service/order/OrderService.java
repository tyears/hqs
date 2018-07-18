package com.cccuu.project.service.order;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.utils.BaseService;
import com.cccuu.project.utils.UserInfo;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月18日 15:19:31
 */
public interface OrderService extends BaseService<Order>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Order> queryListByPage(Map<String,String> params);

	/**
	 * 分页查询待处理列表
	 * @param params
	 * @return
	 */
	public PageInfo<Order> queryPendListByPage(Map<String,String> params);

	/**
	 * 调取登录用户待处理最新订单
	 * @param params
	 * @return
	 */
	public Order obtainOrder(Map<String,Object> params);

	/**
	 * 查单数据
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryOrderData(Map<String,String> params);

	/**
	 * 订单完善信息
	 * @param order
	 * @return
	 */
	public Order updateOrder(Order order,UserInfo userInfo);
	/**
	 * 查询咨询信息
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryConsultInfo(Map<String,String> params);

	/**
	 * 获取最大订单编号
	 * @return
	 */
	public int getOrdernumMax();

	/**
	 * 生成时间+数字的组合
	 * @param ordernumMax 1-999
	 * @return
	 */
	public String getOrderNum(int ordernumMax);

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
	public int allNum(String state);

	/**
	 * 获取待处理订单
	 * @return
	 */
	public int dclNum(String userId,String state);

	/**
	 * 根据用户询单权限或打印权限查询对应的订单状态
	 * @param request
	 * @param flag 0 询单 1 打印
	 * @return
	 */
	public String queryUserToUserState(HttpServletRequest request,String flag);
	/**
	 * 全部变为未导入
	 * @return
	 */
	public int updateImport();

	/**
	 * 查询信息
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByParams(Map<String,String> params);

	/**
	 * 根据手机号匹配询单，填充单位市场
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> matchOrderAreaBytel(Map<String,String> params);
}