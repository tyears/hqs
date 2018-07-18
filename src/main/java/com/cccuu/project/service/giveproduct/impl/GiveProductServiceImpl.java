package com.cccuu.project.service.giveproduct.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.mapper.giveproduct.GiveProductMapper;
import com.cccuu.project.service.giveproduct.GiveProductService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 赠送产品关联ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月12日 17:45:31
 */
@Service("giveProductService")
public class GiveProductServiceImpl extends BaseServiceImpl<GiveProduct> implements GiveProductService{
	
	private GiveProductMapper giveProductMapper;
	
	@Autowired
	public void setGiveProductMapper(GiveProductMapper giveProductMapper){
		this.giveProductMapper = giveProductMapper;
		baseMapper = giveProductMapper;
	}
	@Override
	@Transactional
	public PageInfo<GiveProduct> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(GiveProduct.class);
		Example.Criteria creiteria = example.createCriteria();
		
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<GiveProduct> results = giveProductMapper.selectByExample(example);
		
		PageInfo<GiveProduct> pageInfo = new PageInfo<GiveProduct>(results);
		
		return pageInfo;
	}

}