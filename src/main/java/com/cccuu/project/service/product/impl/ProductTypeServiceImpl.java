package com.cccuu.project.service.product.impl;

import java.util.List;
import java.util.Map;

import com.cccuu.project.mapper.product.ProductMapper;
import com.cccuu.project.model.product.Product;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.mapper.product.ProductTypeMapper;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 产品类别ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:19
 */
@Service("productTypeService")
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements ProductTypeService{
	
	private ProductTypeMapper productTypeMapper;
	
	@Autowired
	public void setProductTypeMapper(ProductTypeMapper productTypeMapper){
		this.productTypeMapper = productTypeMapper;
		baseMapper = productTypeMapper;
	}

	@Autowired
	private ProductMapper productMapper;

	@Override
	@Transactional
	public PageInfo<ProductType> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(ProductType.class);
		example.setOrderByClause("create_time desc");
		Example.Criteria creiteria = example.createCriteria();
		
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<ProductType> results = productTypeMapper.selectByExample(example);
		
		PageInfo<ProductType> pageInfo = new PageInfo<ProductType>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public void deleteInfoById(String id) {
		//删除所有该分类下的产品
		Product product = new Product();
		product.setTypeId(id);
		productMapper.delete(product);

		productTypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ProductType> queryListByName(String typeName, String typeId) {
		//设置查询条件
		Example example = new Example(ProductType.class);
		Example.Criteria creiteria = example.createCriteria();

		if(StringUtils.isNotBlank(typeName)){
			creiteria.andEqualTo("name",typeName);
		}

		if(StringUtils.isNotBlank(typeId)){
			creiteria.andNotEqualTo("id",typeId);
		}
		return productTypeMapper.selectByExample(example);
	}

}