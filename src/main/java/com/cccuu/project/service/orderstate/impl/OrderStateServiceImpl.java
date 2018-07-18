package com.cccuu.project.service.orderstate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.orderstate.OrderState;
import com.cccuu.project.mapper.orderstate.OrderStateMapper;
import com.cccuu.project.service.orderstate.OrderStateService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 订单状态排序ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年11月15日 17:52:12
 */
@Service("orderStateService")
public class OrderStateServiceImpl extends BaseServiceImpl<OrderState> implements OrderStateService{
	
	private OrderStateMapper orderStateMapper;
	
	@Autowired
	public void setOrderStateMapper(OrderStateMapper orderStateMapper){
		this.orderStateMapper = orderStateMapper;
		baseMapper = orderStateMapper;
	}
	@Override
	@Transactional
	public List<OrderState> queryList(){
		//设置查询条件
		Example example = new Example(OrderState.class);
		example.setOrderByClause(" sort asc ");
		Example.Criteria creiteria = example.createCriteria();
		
		List<OrderState> results = orderStateMapper.selectByExample(example);
		
		return orderStateMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public void updateSort(Map map) {
		String stateId=(String) map.get("stateId");
		String[] arryId=stateId.split(",");
		String stateSort=(String)map.get("stateSort");
		String[] arrySort=stateSort.split(",");
		if(StringUtils.isNotBlank(stateId) && StringUtils.isNotBlank(stateSort) && arryId.length==arrySort.length){
			for (int i=0;i<arryId.length;i++){
                OrderState info=new OrderState();
                info.setId(arryId[i]);
                info.setSort(Integer.valueOf(arrySort[i]));
                orderStateMapper.updateByPrimaryKeySelective(info);
			}
		}
	}

}