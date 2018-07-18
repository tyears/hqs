package com.cccuu.project.service.orderproduct.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.mapper.orderproduct.OrderProductMapper;
import com.cccuu.project.service.orderproduct.OrderProductService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 订单产品关联ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月22日 17:44:40
 */
@Service("orderProductService")
public class OrderProductServiceImpl extends BaseServiceImpl<OrderProduct> implements OrderProductService{
	
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	public void setOrderProductMapper(OrderProductMapper orderProductMapper){
		this.orderProductMapper = orderProductMapper;
		baseMapper = orderProductMapper;
	}
	@Override
	@Transactional
	public PageInfo<OrderProduct> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(OrderProduct.class);
		Example.Criteria creiteria = example.createCriteria();
		
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<OrderProduct> results = orderProductMapper.selectByExample(example);
		
		PageInfo<OrderProduct> pageInfo = new PageInfo<OrderProduct>(results);
		
		return pageInfo;
	}

}