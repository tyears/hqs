package com.cccuu.project.service.area.impl;

import com.cccuu.project.mapper.area.AreaProductMapper;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.service.area.AreaProductService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单位市场产品ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:25:37
 */
@Scope("singleton")
@Service("areaProductService")
public class AreaProductServiceImpl extends BaseServiceImpl<AreaProduct> implements AreaProductService{

	private AreaProductMapper areaProductMapper;
	
	@Autowired
	public void setAreaProductMapper(AreaProductMapper areaProductMapper){
		this.areaProductMapper = areaProductMapper;
		baseMapper = areaProductMapper;
	}
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
		
		List<Map<String,Object>> results = areaProductMapper.queryListByParamsPage(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> queryListInArea(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return areaProductMapper.queryListByParams(parameterMap);
	}

	@Override
	public void updateJssOrGs(Map<String, String> params) {
		areaProductMapper.updateJssOrGs(params);
	}

	@Override
	@Transactional
	public void updateComment(Map<String, String> params) {
		areaProductMapper.updateComment(params);
	}

	@Override
	@Transactional
	public void updateAreaProductComment(Map<String, String> params) {
		areaProductMapper.updateAreaProductComment(params);
	}

	@Override
	@Transactional
	public int addMulti(List<AreaProduct> list) {
		return areaProductMapper.addMulti(list);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryOne(String areaNme, String productNum) {
		Map<String,String> map=new HashMap<>();
		map.put("areaName",areaNme);
		map.put("productNum",productNum);
		return areaProductMapper.queryOne(map);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryApByUuid(Map<String, String> params) {
		return areaProductMapper.queryApByUuid(params);
	}

	@Override
	@Transactional
	public void updateAuthor(Map<String,String> params) {
		areaProductMapper.updateAuthor(params);
	}
	public void emptyAuthorDealer(){areaProductMapper.emptyAuthorDealer();}

	@Override
	@Transactional
	public synchronized boolean addAP(AreaProduct areaProduct,String type) {
		AreaProduct queryAP=new AreaProduct();
		queryAP.setAreaId(areaProduct.getAreaId());
		queryAP.setProductId(areaProduct.getProductId());
		queryAP=areaProductMapper.selectOne(queryAP);
		if(queryAP!=null){
			if("gs".equals(type)){
				queryAP.setNoticeDealer(areaProduct.getNoticeDealer());
				queryAP.setNoticeDealerId(areaProduct.getNoticeDealerId());
				queryAP.setGiveDealer("");
				queryAP.setGiveDealerId("");
			}else if("jss".equals(type)){
				queryAP.setNoticeDealer("");
				queryAP.setNoticeDealerId("");
				queryAP.setGiveDealer(areaProduct.getGiveDealer());
				queryAP.setGiveDealerId(areaProduct.getAuthorDealerId());
			}else if("gsh".equals(type)){
				queryAP.setNoticeDealer("H0000");
				queryAP.setNoticeDealerId("");
				queryAP.setGiveDealer("");
				queryAP.setGiveDealerId("");
			}else if("tm".equals(type)){
				queryAP.setEffectTime(areaProduct.getEffectTime());
			}else if("pj".equals(type)){
				queryAP.setComment(areaProduct.getComment());
				queryAP.setIsImport("1");
				queryAP.setImportTime(new Date());
			}else if("sq".equals(type)){
				queryAP.setAuthorDealer(areaProduct.getAuthorDealer());
				queryAP.setAuthorDealerId(areaProduct.getAuthorDealerId());
				queryAP.setAuthorIsImport("1");
				queryAP.setAuthorImportTime(new Date());
			}else if("ht".equals(type)){
				return false;
			}
			areaProductMapper.updateByPrimaryKey(queryAP);
		}else {
			areaProduct.setId(UniqueIDGen.getUniqueID()+"");
			if("gs".equals(type) || "jss".equals(type) || "gsh".equals(type)) {
				areaProduct.setIsImport("0");
				areaProduct.setAuthorIsImport("0");
			}else if("tm".equals(type)){
				areaProduct.setIsImport("0");
				areaProduct.setAuthorIsImport("0");
				areaProduct.setNoticeDealer("H0000");
			}else if("pj".equals(type)){
				areaProduct.setIsImport("1");
				areaProduct.setImportTime(new Date());
				areaProduct.setAuthorIsImport("0");
				areaProduct.setNoticeDealer("H0000");
			}else if("sq".equals(type)){
				areaProduct.setAuthorIsImport("1");
				areaProduct.setAuthorImportTime(new Date());
				areaProduct.setIsImport("0");
				areaProduct.setNoticeDealer("H0000");
			}
			areaProductMapper.insert(areaProduct);
		}
		return true;
	}
}