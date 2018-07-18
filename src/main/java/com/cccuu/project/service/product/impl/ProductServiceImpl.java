package com.cccuu.project.service.product.impl;

import com.cccuu.project.mapper.area.AreaProductMapper;
import com.cccuu.project.mapper.dealerproduct.DealerProductMapper;
import com.cccuu.project.mapper.depart.DepartMapper;
import com.cccuu.project.mapper.giveproduct.GiveProductMapper;
import com.cccuu.project.mapper.log.LogMapper;
import com.cccuu.project.mapper.orderproduct.OrderProductMapper;
import com.cccuu.project.mapper.product.ProductFoodMapper;
import com.cccuu.project.mapper.product.ProductMapper;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.Constants;
import com.cccuu.project.utils.SysUserInfo;
import com.cccuu.project.utils.UniqueIDGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:39
 */
@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService{
	
	private ProductMapper productMapper;
	
	@Autowired
	public void setProductMapper(ProductMapper productMapper){
		this.productMapper = productMapper;
		baseMapper = productMapper;
	}

	@Autowired
	private ProductFoodMapper productFoodMapper;
	@Autowired
	private AreaProductMapper areaProductMapper;
	@Autowired
	private DealerProductMapper dealerProductMapper;
	@Autowired
	private OrderProductMapper orderProductMapper;
	@Autowired
	private GiveProductMapper giveProductMapper;
	@Autowired
	private LogMapper logMapper;
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
		
		List<Map<String,Object>> results = productMapper.queryListByParams(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	public PageInfo<Map<String, Object>> queryListByPageSys(Map<String, String> params) {
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

		List<Map<String,Object>> results = productMapper.queryListByParamsSys(parameterMap);

		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);

		return pageInfo;
	}

	@Override
	@Transactional
	public List<Product> queryList(Map<String, String> params) {
		//设置查询条件
		Example example = new Example(Product.class);
		example.setOrderByClause(" sort asc ");
		Example.Criteria creiteria = example.createCriteria();

		String typeId=params.get("typeId");
		if(StringUtils.isNotBlank(typeId)){
			creiteria.andEqualTo("typeId",typeId);
		}

		return productMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public Product saveInfo(Product product, String foodIds) {
		String productId = product.getId();
		//新增
		if(StringUtils.isBlank(productId)){
			productId = UniqueIDGen.getUniqueID()+"";
			product.setId(productId);
			productMapper.insertSelective(product);
		}else{//修改
			productMapper.updateByPrimaryKeySelective(product);
			//删除原来所有的适配食物
			ProductFood deleteProductFood = new ProductFood();
			deleteProductFood.setProductId(productId);
			productFoodMapper.delete(deleteProductFood);
		}
		//保存适配食物
		if(StringUtils.isNotBlank(foodIds)){
			String[] foodIdArry = foodIds.split(",");
			for (String foodId:foodIdArry) {
				ProductFood productFood = new ProductFood();
				productFood.setId(UniqueIDGen.getUniqueID()+"");
				productFood.setProductId(productId);
				productFood.setFoodId(foodId);
				productFoodMapper.insertSelective(productFood);
			}
		}
		return product;
	}

	@Override
	@Transactional
	public void deleteInfoById(String id) {
		//删除适用食品
		ProductFood productFood = new ProductFood();
		productFood.setProductId(id);
		productFoodMapper.delete(productFood);

		productMapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryListByFoodId(String foodId) {
		return productMapper.queryListByFoodId(foodId);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryListByKeyWord(String keyWord) {
		return productMapper.queryListByKeyWord(keyWord);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryProductByDealerId(String dealerId) {
		return productMapper.queryProductByDealerId(dealerId);
	}

	@Override
	public List<Map<String, Object>> isProductNum(Product product) {
		return productMapper.isProductNum(product);
	}

	@Override
	@Transactional
	public int addMulti(List<Product> productList) {
		return productMapper.addMulti(productList);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryProductByNum(Map<String, String> params) {
		return productMapper.queryProductByNum(params);
	}

	@Override
	@Transactional
	public void deleteProduct(String id, HttpServletRequest request) {
		//删除关联信息
		GiveProduct giveProduct=new GiveProduct();
		giveProduct.setProductId(id);
		List<GiveProduct> list1=giveProductMapper.select(giveProduct);

		OrderProduct orderProduct=new OrderProduct();
		orderProduct.setProductId(id);
		List<OrderProduct> list2=orderProductMapper.select(orderProduct);
		Product product=new Product();
		product.setId(id);
		product=productMapper.selectOne(product);
		AreaProduct areaProduct=new AreaProduct();
		areaProduct.setProductId(id);
		areaProductMapper.delete(areaProduct);
		DealerProduct dealerProduct=new DealerProduct();
		dealerProduct.setProductId(id);
		dealerProductMapper.delete(dealerProduct);
		ProductFood productFood=new ProductFood();
		productFood.setProductId(id);
		productFoodMapper.delete(productFood);
		if(list1.size()>0 || list2.size()>0){
			product.setReserved1("1");
			productMapper.updateByPrimaryKey(product);
		}else {
			productMapper.delete(product);
		}
	}
}