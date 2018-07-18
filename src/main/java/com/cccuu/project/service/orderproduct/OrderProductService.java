package com.cccuu.project.service.orderproduct;

import java.util.Map;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 订单产品关联Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月22日 17:44:40
 */
public interface OrderProductService extends BaseService<OrderProduct>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<OrderProduct> queryListByPage(Map<String,String> params);

}