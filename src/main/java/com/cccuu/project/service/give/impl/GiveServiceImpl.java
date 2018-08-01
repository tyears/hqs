package com.cccuu.project.service.give.impl;

import java.util.*;

import com.cccuu.project.mapper.dealerproduct.DealerProductMapper;
import com.cccuu.project.mapper.giveproduct.GiveProductMapper;
import com.cccuu.project.mapper.product.ProductMapper;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.service.giveproduct.GiveProductService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.give.Give;
import com.cccuu.project.mapper.give.GiveMapper;
import com.cccuu.project.service.give.GiveService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.util.WebUtils.getSessionAttribute;

/**
 * 赠送ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月12日 17:42:30
 */
@Service("giveService")
@Scope("singleton")
public class GiveServiceImpl extends BaseServiceImpl<Give> implements GiveService{
	
	private GiveMapper giveMapper;
	
	@Autowired
	public void setGiveMapper(GiveMapper giveMapper){
		this.giveMapper = giveMapper;
		baseMapper = giveMapper;
	}
	@Autowired
	private GiveProductMapper giveProductMapper;
	@Autowired
	private DealerProductMapper dealerProductMapper;
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private DealerProductService dealerProductService;

	@Override
	public List<Map<String, Object>> queryIsGiveDealer(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		return giveMapper.queryIsGiveDealer(parameterMap);
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
		
		if(StringUtils.isBlank(params.get("isPage"))){
			//设置分页参数
			String pageNumStr = params.get("pageNum");
			String pageSizeStr = params.get("pageSize");
			int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
			int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

			//设置分页信息
			PageHelper.startPage(pageNum, pageSize);
		}

		
		List<Map<String,Object>> results = giveMapper.queryListByParams(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	public List<Map<String, Object>> queryListGive(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		return giveMapper.queryListByParams(parameterMap);
	}

	@Override
	public Map<String, Object> queryGive(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}

		return giveMapper.queryByParams(parameterMap);
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized Give saveGive(Give give, HttpServletRequest request) {
			give.setId(UniqueIDGen.getUniqueID()+"");
			UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
			give.setGiveManId(userInfo.getUserId());
			give.setGiveManName(userInfo.getName());
			give.setGiveTime(new Date());
			give.setCreateTime(new Date());
			give.setIsNotice("0");
			Integer iMax=giveMapper.onlyNumMax()+1;
			give.setOnlyNumMax(iMax);
			if(iMax<10){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+"00"+iMax);
			}else if(iMax>=10 && iMax<100){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+"0"+iMax);
			}else if(iMax>=100 && iMax<1000){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+iMax);
			}
			give.setReserved1("n");
			giveMapper.insertSelective(give);

			String[] productIdArry=give.getProductIds().split(",");
//			String[] productNumArry=give.getProductNums().split(",");
			for (int i=0;i<productIdArry.length;i++){
				GiveProduct giveProduct=new GiveProduct();
				giveProduct.setId(UniqueIDGen.getUniqueID()+"");
				giveProduct.setGiveId(give.getId());
				giveProduct.setGiveNum(0);
				giveProduct.setProductId(productIdArry[i]);
				giveProductMapper.insertSelective(giveProduct);
				if(StringUtils.isNotBlank(give.getDealerId())){
					//增加赠送数量
					DealerProduct dealerProduct=new DealerProduct();
					dealerProduct.setDealerId(give.getDealerId());
					dealerProduct.setProductId(productIdArry[i]);
					dealerProductService.addDP(dealerProduct,give.getGiveType());
				}
			}

		return give;
	}

	@Override
	public Give updateGiveProduct(Give give, HttpServletRequest request) {
		//删除原来赠送产品表
		GiveProduct delGiveProduct=new GiveProduct();
		delGiveProduct.setGiveId(give.getId());
		giveProductMapper.delete(delGiveProduct);
		//增加赠送产品表
		String[] productIdArry=give.getProductIds().split(",");
		for (int i=0;i<productIdArry.length;i++){
			GiveProduct giveProduct=new GiveProduct();
			giveProduct.setId(UniqueIDGen.getUniqueID()+"");
			giveProduct.setGiveId(give.getId());
			giveProduct.setGiveNum(0);
			giveProduct.setProductId(productIdArry[i]);
			giveProductMapper.insertSelective(giveProduct);
			if(StringUtils.isNotBlank(give.getDealerId())){
				//增加赠送数量
				DealerProduct dealerProduct=new DealerProduct();
				dealerProduct.setDealerId(give.getDealerId());
				dealerProduct.setProductId(productIdArry[i]);
				dealerProduct.setIsGiveUpdate("y");
				dealerProductService.addDP(dealerProduct,give.getGiveType());
			}
		}

		//更新赠送表
		give.setGiveTime(new Date());
		giveMapper.updateByPrimaryKeySelective(give);
		return give;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public synchronized Give saveHGive(Give give, HttpServletRequest request) {
		Give oldGive=new Give();
		oldGive.setOrderId(give.getOrderId());
		oldGive=giveMapper.selectOne(oldGive);
		if(oldGive!=null){
			give.setId(oldGive.getId());
			//增加赠送产品表
			String[] productIdArry=give.getProductIds().split(",");
			for (int i=0;i<productIdArry.length;i++){
				GiveProduct giveProduct=new GiveProduct();
				giveProduct.setId(UniqueIDGen.getUniqueID()+"");
				giveProduct.setGiveId(give.getId());
				giveProduct.setGiveNum(0);
				giveProduct.setProductId(productIdArry[i]);
				giveProductMapper.insertSelective(giveProduct);
			}
			//更新赠送表
			UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
			give.setGiveManId(userInfo.getUserId());
			give.setGiveManName(userInfo.getName());
			give.setGiveTime(new Date());
			//更改赠送内容
			GiveProduct queryGP=new GiveProduct();
			queryGP.setGiveId(give.getId());
			List<GiveProduct> giveProductList=giveProductMapper.select(queryGP);
			StringBuilder sb=new StringBuilder();
			for (GiveProduct giveProduct : giveProductList) {
				Product product=new Product();
				product.setId(giveProduct.getProductId());
				product=productMapper.selectOne(product);
				sb.append(product.getProductNum()).append(" ").append(product.getProductName()).append(";");
			}
			give.setGiveContent(sb.toString());
			giveMapper.updateByPrimaryKeySelective(give);
		}else {
			give.setId(UniqueIDGen.getUniqueID()+"");
			UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
			give.setGiveManId(userInfo.getUserId());
			give.setGiveManName(userInfo.getName());
			give.setGiveTime(new Date());
			give.setCreateTime(new Date());
			give.setIsNotice("0");
			Integer iMax=giveMapper.onlyNumMax()+1;
			give.setOnlyNumMax(iMax);
			if(iMax<10){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+"00"+iMax);
			}else if(iMax>=10 && iMax<100){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+"0"+iMax);
			}else if(iMax>=100 && iMax<1000){
				give.setOnlyNum(DateUtils.getDate("yyyyMMdd")+iMax);
			}
			giveMapper.insertSelective(give);
			//增加赠送产品表
			String[] productIdArry=give.getProductIds().split(",");
			for (int i=0;i<productIdArry.length;i++){
				GiveProduct giveProduct=new GiveProduct();
				giveProduct.setId(UniqueIDGen.getUniqueID()+"");
				giveProduct.setGiveId(give.getId());
				giveProduct.setGiveNum(0);
				giveProduct.setProductId(productIdArry[i]);
				giveProductMapper.insertSelective(giveProduct);
			}
		}
		return give;
	}

	@Override
	@Transactional
	public void updateGiveState(Give give) {
		giveMapper.updateByPrimaryKeySelective(give);
		//增加赠送数量
		GiveProduct giveProduct=new GiveProduct();
		giveProduct.setGiveId(give.getId());
		List<GiveProduct> giveProductList=giveProductMapper.select(giveProduct);
		for (GiveProduct giveProduct1 : giveProductList) {
			DealerProduct dealerProduct=new DealerProduct();
			dealerProduct.setDealerId(give.getDealerId());
			dealerProduct.setProductId(giveProduct1.getProductId());
			dealerProduct=dealerProductMapper.selectOne(dealerProduct);
			Product product=new Product();
			product.setId(giveProduct1.getProductId());
			product=productMapper.selectOne(product);
			dealerProduct.setGiveNum(dealerProduct.getGiveNum()+giveProduct1.getGiveNum()*product.getNumBox());
			dealerProduct.setLastTime(new Date());
			dealerProduct.setNoticeNum(dealerProduct.getNoticeNum()+1);
			dealerProductMapper.updateByPrimaryKeySelective(dealerProduct);
		}
	}

	@Override
	public void updateBeizhu(Map<String, String> map) {
		giveMapper.updateBeizhu(map);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryProductByGiveId(String giveId) {
		return giveMapper.queryProductByGiveId(giveId);
	}

	@Override
	@Transactional
	public Integer queryIsGive(String orderId) {
		return giveMapper.queryIsGive(orderId);
	}

	@Override
	@Transactional
	public Integer onlyNumMax() {
		return giveMapper.onlyNumMax();
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryListByIdS(String[] ids) {
		Map<String,Object> map = new HashMap<>();
		map.put("ids",ids);
		return giveMapper.queryListByIdS(map);
	}

	@Override
	public List<Map<String, Object>> queryListByIdSZH(String[] ids) {
		Map<String,Object> map = new HashMap<>();
		map.put("ids",ids);
		return giveMapper.queryListByIdSZH(map);
	}

	@Override
	@Transactional
	public void deleteByIds(String[] ids) {
		//删除赠送产品关联表
		for (String id : ids) {
			GiveProduct delGive=new GiveProduct();
			delGive.setGiveId(id);
			giveProductMapper.delete(delGive);
		}
		//删除赠送表
		Map<String,Object> map = new HashMap<>();
		map.put("ids",ids);
		giveMapper.deleteByIds(map);
	}

	@Override
	@Transactional
	public void updateInfo(Map<String, String> map) {
		if("1".equals(map.get("giveType"))){
			if("y".equals(map.get("isName")))giveMapper.updateNameDealer(map);
			else giveMapper.updateAddressDealer(map);
		}else {
			if("y".equals(map.get("isName")))giveMapper.updateNameOrder(map);
			else giveMapper.updateAddressOrder(map);
		}
	}

	@Override
	public List<Map<String, Object>> zhQueryExcel(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return giveMapper.zhQuery(parameterMap);
	}

	@Override
	@Transactional
	public PageInfo<Map<String,Object>> zhQuery(Map<String, String> params) {
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


		List<Map<String,Object>> results = giveMapper.zhQuery(parameterMap);
		for (Map<String, Object> result : results) {
			if(StringUtils.isNotBlank((String) result.get("area_name"))){
				String[] strArr=((String) result.get("area_name")).split("-");

				result.put("area_name", strArr.length == 1 ? strArr[0] : strArr[2]);
			}
		}

		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);

		return pageInfo;
	}
}