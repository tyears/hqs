package com.cccuu.project.service.dealerproduct.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cccuu.project.mapper.area.AreaMapper;
import com.cccuu.project.mapper.area.AreaProductMapper;
import com.cccuu.project.mapper.dealer.DealerMapper;
import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.utils.UniqueIDGen;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.mapper.dealerproduct.DealerProductMapper;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 客户产品关联ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:44:27
 */
@Scope("singleton")
@Service("dealerProductService")
public class DealerProductServiceImpl extends BaseServiceImpl<DealerProduct> implements DealerProductService{
	
	private DealerProductMapper dealerProductMapper;
	
	@Autowired
	public void setDealerProductMapper(DealerProductMapper dealerProductMapper){
		this.dealerProductMapper = dealerProductMapper;
		baseMapper = dealerProductMapper;
	}
	@Autowired
	private DealerMapper dealerMapper;
	@Autowired
	private AreaProductMapper areaProductMapper;
	@Override
	@Transactional
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params){
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
		
		List<Map<String,Object>> results = dealerProductMapper.queryListByParams(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryProductByDealerId(Map<String,String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return dealerProductMapper.queryProductByDealerId(parameterMap);
	}

	@Override
	public void updateRemark(Map<String, String> params) {
		dealerProductMapper.updateRemark(params);
	}

	@Override
	@Transactional
	public void updateComment(Map<String, String> params) {
		dealerProductMapper.updateComment(params);
	}

	@Override
	@Transactional
	public int addMulti(List<DealerProduct> list) {
		return dealerProductMapper.addMulti(list);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryOne(String uuid, String productNum) {
		Map<String,String> map=new HashMap<>();
		map.put("uuid",uuid);
		map.put("productNum",productNum);
		return dealerProductMapper.queryOne(map);
	}

	@Override
	@Transactional
	public synchronized boolean addDP(DealerProduct dealerProduct, String type) {
		DealerProduct queryDP=new DealerProduct();
		queryDP.setDealerId(dealerProduct.getDealerId());
		queryDP.setProductId(dealerProduct.getProductId());
		queryDP=dealerProductMapper.selectOne(queryDP);
		if(queryDP!=null){
			switch (type){
				case "1":
					if(queryDP.getFirstTime()==null){
						queryDP.setFirstTime(new Date());
					}
					queryDP.setLastTime(new Date());
					break;
				case "2":
					queryDP.setCompanyGiveNum(queryDP.getCompanyGiveNum()+1);
					if(!"y".equals(dealerProduct.getIsGiveUpdate())){
						queryDP.setNoticeNum(queryDP.getNoticeNum()+1);
					}
					break;
				case "3":
					queryDP.setDealerGiveNum(queryDP.getDealerGiveNum()+1);
					if(!"y".equals(dealerProduct.getIsGiveUpdate())){
						queryDP.setNoticeNum(queryDP.getNoticeNum()+1);
					}
					break;
				case "pj":
					queryDP.setComment(dealerProduct.getComment());
					queryDP.setIsImport("1");
					queryDP.setImportTime(new Date());
					break;
				case "jh":
					queryDP.setLastPurchaseTime(dealerProduct.getLastPurchaseTime());
					queryDP.setIsImportPurchase("1");
					queryDP.setImportPurchaseTime(new Date());
					break;
				case "ht":
					return false;
			}
			dealerProductMapper.updateByPrimaryKey(queryDP);
		}else {
			dealerProduct.setId(UniqueIDGen.getUniqueID()+"");
			switch (type){
				case "1":
					dealerProduct.setGiveNum(0);
					dealerProduct.setCompanyGiveNum(0);
					dealerProduct.setDealerGiveNum(0);
					dealerProduct.setNoticeNum(0);
					dealerProduct.setFirstTime(new Date());
					dealerProduct.setLastTime(new Date());
					break;
				case "2":
					dealerProduct.setGiveNum(0);
					dealerProduct.setCompanyGiveNum(1);
					dealerProduct.setDealerGiveNum(0);
					if("y".equals(dealerProduct.getIsGiveUpdate())){
						dealerProduct.setNoticeNum(0);
					}else {
						dealerProduct.setNoticeNum(1);
					}
					break;
				case "3":
					dealerProduct.setGiveNum(0);
					dealerProduct.setCompanyGiveNum(0);
					dealerProduct.setDealerGiveNum(1);
					if("y".equals(dealerProduct.getIsGiveUpdate())){
						dealerProduct.setNoticeNum(0);
					}else {
						dealerProduct.setNoticeNum(1);
					}
					break;
				case "pj":
					dealerProduct.setIsImport("1");
					dealerProduct.setImportTime(new Date());
					dealerProduct.setGiveNum(0);
					dealerProduct.setDealerGiveNum(0);
					dealerProduct.setCompanyGiveNum(0);
					dealerProduct.setNoticeNum(0);
					dealerProduct.setIsImportPurchase("0");
					break;
				case "jh":
					dealerProduct.setIsImport("0");
					dealerProduct.setIsImportPurchase("1");
					dealerProduct.setImportPurchaseTime(new Date());
					dealerProduct.setGiveNum(0);
					dealerProduct.setDealerGiveNum(0);
					dealerProduct.setCompanyGiveNum(0);
					dealerProduct.setNoticeNum(0);
					break;
			}
			dealerProductMapper.insert(dealerProduct);
		}
		return true;
	}
	public void updateDealerProductMerit(){
		dealerProductMapper.updateDealerProductMerit();
	}
}