package com.cccuu.project.service.order.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import com.cccuu.project.mapper.depart.DepartMapper;
import com.cccuu.project.mapper.depart.UserDepartMapper;
import com.cccuu.project.mapper.frontcore.UserMapper;
import com.cccuu.project.mapper.operationrecord.OperationRecordMapper;
import com.cccuu.project.mapper.orderproduct.OrderProductMapper;
import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.utils.Constants;
import com.cccuu.project.utils.UniqueIDGen;
import com.cccuu.project.utils.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.mapper.order.OrderMapper;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月18日 15:19:31
 */
@Service("orderService")
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{
	
	private OrderMapper orderMapper;

	@Autowired
	public void setOrderMapper(OrderMapper orderMapper){
		this.orderMapper = orderMapper;
		baseMapper = orderMapper;
	}
	@Autowired
	private OrderProductMapper orderProductMapper;
	@Autowired
	private OperationRecordMapper operationRecordMapper;
	@Autowired
	private UserDepartMapper userDepartMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DepartMapper departMapper;
	@Override
	@Transactional
	public PageInfo<Order> queryListByPage(Map<String,String> params){
		String isMarket=params.get("isMarket");

		//设置查询条件
		Example example = new Example(Order.class);
		example.setOrderByClause(" is_transfer desc,transfer_time asc,import_type desc,import_time desc ");
		Example.Criteria creiteria = example.createCriteria();

		if(StringUtils.isNotBlank(isMarket)){
			if("1".equals(isMarket)){
                List<String> objects=new ArrayList<>();
                objects.add("1");
                objects.add("2");
                creiteria.andIn("state",objects);
			}else {
				List<String> objects=new ArrayList<>();
				objects.add("3");
                objects.add("4");
                objects.add("5");
                objects.add("6");
                creiteria.andIn("state",objects);
			}
		}

		String importType=params.get("importType");
		if(StringUtils.isNotBlank(importType)){
			creiteria.andEqualTo("importType",importType);
		}
		String isImport=params.get("isImport");
		if(StringUtils.isNotBlank(isImport)){
			creiteria.andEqualTo("isImport",isImport);
		}
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Order> results = orderMapper.selectByExample(example);
		
		PageInfo<Order> pageInfo = new PageInfo<Order>(results);

		return pageInfo;
	}

	@Override
	@Transactional
	public PageInfo<Order> queryPendListByPage(Map<String, String> params) {
		//设置查询条件
		Example example = new Example(Order.class);
		example.setOrderByClause(" is_transfer desc,transfer_time asc,import_type desc,import_time asc ");

		Example.Criteria creiteria = example.createCriteria();
		String dclState=params.get("dclState");
		List<String> objects=new ArrayList<>();
		String[] objArry=dclState.split(",");
		Collections.addAll(objects, objArry);
		creiteria.andIn("state",objects);


		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);

		List<Order> results = orderMapper.selectByExample(example);

		PageInfo<Order> pageInfo = new PageInfo<Order>(results);

		return pageInfo;
	}

	@Override
	@Transactional
	public Order obtainOrder(Map<String, Object> params) {
		return orderMapper.obtainOrder(params);
	}

	@Override
	public PageInfo<Map<String,Object>> queryOrderData(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		if(StringUtils.isNotBlank(params.get("dclState"))){
			parameterMap.put("dclState",params.get("dclState").split(","));
		}

		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 15 : Integer.parseInt(pageSizeStr);

		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);

		List<Map<String,Object>> results = orderMapper.queryOrderData(parameterMap);

		PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

		return pageInfo;
	}

	@Override
	@Transactional
	public Order updateOrder(Order order,UserInfo userInfo) {
		//删除订单产品关联表
		OrderProduct delop=new OrderProduct();
		delop.setOrderId(order.getId());
		orderProductMapper.delete(delop);
		//新增订单产品关联表
		if(StringUtils.isNotBlank(order.getProductIds())){
			String[] productIds=order.getProductIds().split(",");
			for (String productId : productIds) {
				OrderProduct orderProduct=new OrderProduct();
				orderProduct.setId(UniqueIDGen.getUniqueID()+"");
				orderProduct.setOrderId(order.getId());
				orderProduct.setProductId(productId);
				orderProductMapper.insertSelective(orderProduct);
			}
		}
		//新增操作记录
		if(StringUtils.isNotBlank(order.getOperationRecord())){
			OperationRecord operationRecord=new OperationRecord();
			operationRecord.setId(UniqueIDGen.getUniqueID()+"");
			operationRecord.setCreateTime(new Date());
			operationRecord.setOrderId(order.getId());
			operationRecord.setOrderTel(order.getTel());
			operationRecord.setUserId(userInfo.getUserId());
			operationRecord.setUserName(userInfo.getName());
			operationRecord.setContent(order.getOperationRecord());
			operationRecordMapper.insertSelective(operationRecord);
		}
		orderMapper.updateByPrimaryKeySelective(order);
		return order;
	}

	@Override
	public PageInfo<Map<String,Object>> queryConsultInfo(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}


		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);

		List<Map<String,Object>> results = orderMapper.queryConsultInfo(parameterMap);

		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		return pageInfo;
	}

	@Override
	public int getOrdernumMax() {
		return orderMapper.getOrdernumMax();
	}

	@Override
	public String getOrderNum(int ordernumMax) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(new Date());
		if(ordernumMax<10)str+="00"+ordernumMax;
		if(ordernumMax>=10&&ordernumMax<100)str+="0"+ordernumMax;
		if(ordernumMax>=100&&ordernumMax<1000)str+=""+ordernumMax;
		return str;
	}

	@Override
	public Map isOrderByNum(Map<String ,Object> map) {
		return orderMapper.isOrderByNum(map);
	}

	@Override
	@Transactional
	public int allNum(String state) {
		String[] stateArray=state.split(",");
		Map<String, Object> params = new HashMap<>();
		params.put("state",stateArray);
		return orderMapper.allNum(params);
	}

	@Override
	@Transactional
	public int dclNum(String userId,String state) {
		String[] stateArray=state.split(",");
		Map<String, Object> params = new HashMap<>();
		params.put("state",stateArray);
		params.put("userId",userId);
		return orderMapper.dclNum(params);
	}

	@Override
	@Transactional
	public String queryUserToUserState(HttpServletRequest request,String flag) {
		UserInfo userInfo= (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
		//查询用户询单部门
		UserDepart userDepart = new UserDepart();
		userDepart.setFlag(flag);
		userDepart.setUserId(userInfo.getUserId());
		List<UserDepart> userDepartList=userDepartMapper.select(userDepart);
		StringBuilder state=new StringBuilder();
		User user=new User();
		user.setId(userInfo.getUserId());
		user=userMapper.selectOne(user);
//		if("1".equals(user.getUserType())){
//			Depart depart1=new Depart();
//			depart1.setId(user.getDepartId());
//			depart1=departMapper.selectOne(depart1);
//			if("市场部".equals(depart1.getDepartName())){
//				state.append("2");
//			}
//		}else{
			for (int i=0;i<userDepartList.size();i++) {
				Depart depart=new Depart();
				depart.setId(userDepartList.get(i).getDepartId());
				depart=departMapper.selectOne(depart);
				if(i==userDepartList.size()-1){
					switch (depart.getDepartName()){
						case "市场部":
							state.append("1");
							break;
						case "市场部经理":
							state.append("2");
							break;
						case "大厂部":
							state.append("3");
							break;
						case "技术部":
							state.append("4");
							break;
						case "货运部":
							state.append("5");
							break;
						case "财务部":
							state.append("6");
							break;
						case "订货部":
							state.append("11");
							break;
						case "调货部":
							state.append("12");
							break;
						case "办公室":
							state.append("13");
							break;
						case "其他1":
							state.append("14");
							break;
						case "其他2":
							state.append("15");
							break;
						case "网络部":
							state.append("16");
							break;
                        case "信息部":
                            state.append("17");
                            break;
					}
				}else {
					switch (depart.getDepartName()){
						case "市场部":
							state.append("1,");
							break;
						case "大厂部":
							state.append("3,");
							break;
						case "技术部":
							state.append("4,");
							break;
						case "货运部":
							state.append("5,");
							break;
						case "财务部":
							state.append("6,");
							break;
						case "订货部":
							state.append("11,");
							break;
						case "调货部":
							state.append("12,");
							break;
						case "办公室":
							state.append("13,");
							break;
						case "其他1":
							state.append("14,");
							break;
						case "其他2":
							state.append("15,");
							break;
						case "网络部":
							state.append("16,");
							break;
                        case "信息部":
                            state.append("17,");
                            break;
					}
				}

			}
//		}
		return state.toString();
	}

	@Override
	@Transactional
	public int updateImport() {
		return orderMapper.updateImport();
	}

	@Override
	@Transactional
	public PageInfo<Map<String, Object>> queryListByParams(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}


		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);

		List<Map<String,Object>> results = orderMapper.queryListByParams(parameterMap);

		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> matchOrderAreaBytel(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return orderMapper.matchOrderAreaBytel(parameterMap);
	}
}