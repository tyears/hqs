package com.cccuu.project.service.orderstate;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.orderstate.OrderState;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 订单状态排序Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年11月15日 17:52:12
 */
public interface OrderStateService extends BaseService<OrderState>{
	/**
	 * 分页查询列表
	 * @return
	 */
	public List<OrderState> queryList();

	/**
	 * 更新排序
	 * @param info
	 */
	public void updateSort(Map map);

}