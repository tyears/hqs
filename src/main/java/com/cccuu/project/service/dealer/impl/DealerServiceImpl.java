package com.cccuu.project.service.dealer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cccuu.project.mapper.area.AreaMapper;
import com.cccuu.project.mapper.area.AreaProductMapper;
import com.cccuu.project.mapper.dealerproduct.DealerProductMapper;
import com.cccuu.project.mapper.operationrecord.OperationRecordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.mapper.dealer.DealerMapper;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 客户（经销商或大厂部）ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月14日 11:50:55
 */
@Service("dealerService")
public class DealerServiceImpl extends BaseServiceImpl<Dealer> implements DealerService{
	
	private DealerMapper dealerMapper;
	
	@Autowired
	public void setDealerMapper(DealerMapper dealerMapper){
		this.dealerMapper = dealerMapper;
		baseMapper = dealerMapper;
	}
	@Autowired
	private DealerProductMapper dealerProductMapper;
	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private AreaProductMapper areaProductMapper;
	@Autowired
	private OperationRecordMapper operationRecordMapper;
	@Override
	@Transactional
	public PageInfo<Dealer> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(Dealer.class);
		example.setOrderByClause(" create_time desc ");
		Example.Criteria creiteria = example.createCriteria();
		
		String dealerNum = params.get("dealerNum");
		if(StringUtils.isNotBlank(dealerNum)){
			creiteria.andLike("dealerNum","%"+dealerNum+"%");
		}

		String districtId=params.get("districtId");
		if(StringUtils.isNotBlank(districtId)){
			creiteria.andEqualTo("districtId",districtId);
		}

		String cityId=params.get("cityId");
		if(StringUtils.isNotBlank(cityId)){
			creiteria.andEqualTo("cityId",cityId);
		}

		String provinceId=params.get("provinceId");
		if(StringUtils.isNotBlank(provinceId)){
			creiteria.andEqualTo("provinceId",provinceId);
		}

		String areaId=params.get("areaId");
		if(StringUtils.isNotBlank(areaId)){
			creiteria.andEqualTo("districtId",areaId);
		}

		String registerTel = params.get("registerTel");
		if(StringUtils.isNotBlank(registerTel)){
			creiteria.andLike("registerTel","%"+registerTel+"%");
		}

		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}

		String deliveryAddress = params.get("deliveryAddress");
		if(StringUtils.isNotBlank(deliveryAddress)){
			creiteria.andLike("deliveryAddress","%"+deliveryAddress+"%");
		}

		if("0".equals(params.get("type"))){
			creiteria.andIsNotNull("dealerNum");
		}
		if("1".equals(params.get("type"))){
			creiteria.andIsNull("dealerNum");
		}

		String type = params.get("type");
		if("0".equals(type))type="1";
		if(StringUtils.isNotBlank(type)){
			creiteria.andEqualTo("dealerType",type);
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Dealer> results = dealerMapper.selectByExample(example);
		
		PageInfo<Dealer> pageInfo = new PageInfo<Dealer>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public PageInfo<Map<String, Object>> queryDealerListByPage(Map<String, String> params) {
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

		List<Map<String,Object>> results = dealerMapper.queryListByPage(parameterMap);

		return new PageInfo<Map<String,Object>>(results);
	}

	@Override
	public PageInfo<Map<String, Object>> queryDealerList(Map<String, String> params) {
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

		List<Map<String,Object>> results = dealerMapper.queryDealerList(parameterMap);

		return new PageInfo<>(results);
	}

	@Override
	@Transactional
	public Dealer queryBytel(String tel) {
        //设置查询条件
		Example example = new Example(Dealer.class);
		Example.Criteria creiteria = example.createCriteria();
		creiteria.andEqualTo("dealerType","1");
		creiteria.andEqualTo("registerTel",tel);

		Example.Criteria creiteria2 = example.createCriteria();
		creiteria2.andLike("otherTel","%"+tel+"%");
		creiteria2.andEqualTo("dealerType","1");
		example.or(creiteria2);

		List<Dealer> dealerList=dealerMapper.selectByExample(example);
		if(dealerList!=null && dealerList.size()>0){
			return dealerList.get(0);
		}else {
			return null;
		}
	}

	@Override
	@Transactional
	public Integer countDealer(Map<String,String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return dealerMapper.countDealer(parameterMap);
	}

	@Override
	public void updatePingText(Map<String, String> params) {
		dealerMapper.updatePingText(params);
	}

	@Override
	public Integer codeNum() {
		return dealerMapper.codeNum();
	}

	@Override
	public List<Map> isHasPhone(String[] array,String id) {
		Map map = new HashMap();
		map.put("array",array);
		map.put("id",id);
		return dealerMapper.isHasPhone(map);
	}

	@Override
	@Transactional
	public PageInfo<Map<String, Object>> queryListImportByPage(Map<String, String> params) {
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

		String importType=params.get("importType");
		List<Map<String,Object>> results=new ArrayList<>();
		if("1".equals(importType)){
			results = dealerMapper.queryListImportByPage(parameterMap);
		}else if("2".equals(importType)){
			parameterMap.put("isImport","1");
			results =dealerProductMapper.queryListImportByParams(parameterMap);
		}else if("3".equals(importType)){
			results =areaProductMapper.queryAreaImport(parameterMap);
		}else if("4".equals(importType)){
			parameterMap.put("isImport","1");
			results =areaProductMapper.queryAreaProductImport(parameterMap);
		}else if("5".equals(importType)){
			parameterMap.put("isImportPurchase","1");
			results =dealerProductMapper.queryListImportByParams(parameterMap);
		}else if("6".equals(importType)){
			parameterMap.put("authorIsImport","1");
			results =areaProductMapper.queryAreaProductImport(parameterMap);
		}else if("7".equals(importType)){
			parameterMap.put("isImport","1");
			results =operationRecordMapper.queryListByParams(parameterMap);
		}


		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);

		return pageInfo;
	}

	@Override
	@Transactional
	public void updateImport(String importType) {
		Map<String,String> params=new HashMap<>();
		if("1".equals(importType)){
			dealerMapper.updateImport();
		}else if ("2".equals(importType)){
			params.put("isImport","0");
			dealerProductMapper.updateImport(params);
		}else if ("3".equals(importType)){
			areaProductMapper.updateAreaImport();
		}else if ("4".equals(importType)){
			areaProductMapper.updateAreaProductImport();
		}else if ("5".equals(importType)){
			params.put("isImportPurchase","0");
			dealerProductMapper.updateImport(params);
		}else if ("6".equals(importType)){
			areaProductMapper.updateAreaProductImport();
		}else if ("7".equals(importType)){
			operationRecordMapper.updateImport();
		}
	}

	@Override
	@Transactional
	public int addMulti(List<Dealer> list) {
		return dealerMapper.addMulti(list);
	}

	@Override
	@Transactional
	public List<Dealer> queryListByMapDouble(Map<String, String> params) {
		return dealerMapper.queryListByMapDouble(params);
	}

	public void updateDealerMerit(){
		dealerMapper.updateDealerMerit();
	}
}