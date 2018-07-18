package com.cccuu.project.service.articletype.impl;

import java.util.List;
import java.util.Map;

import com.cccuu.project.model.product.ProductType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.articletype.ArticleType;
import com.cccuu.project.mapper.articletype.ArticleTypeMapper;
import com.cccuu.project.service.articletype.ArticleTypeService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 文章类别ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:23:37
 */
@Service("articleTypeService")
public class ArticleTypeServiceImpl extends BaseServiceImpl<ArticleType> implements ArticleTypeService{
	
	private ArticleTypeMapper articleTypeMapper;
	
	@Autowired
	public void setArticleTypeMapper(ArticleTypeMapper articleTypeMapper){
		this.articleTypeMapper = articleTypeMapper;
		baseMapper = articleTypeMapper;
	}
	@Override
	@Transactional
	public PageInfo<ArticleType> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(ArticleType.class);
		example.setOrderByClause(" sort asc ");
		Example.Criteria creiteria = example.createCriteria();
		
		String typeName = params.get("typeName");
		if(StringUtils.isNotBlank(typeName)){
			creiteria.andLike("typeName","%"+typeName+"%");
		}

		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<ArticleType> results = articleTypeMapper.selectByExample(example);
		
		PageInfo<ArticleType> pageInfo = new PageInfo<ArticleType>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<ArticleType> queryAllList() {
		//设置查询条件
		Example example = new Example(ArticleType.class);
		example.setOrderByClause(" sort asc ");

		return articleTypeMapper.selectByExample(example);
	}

	@Override
	public List<ArticleType> queryListByName(String typeName, String typeId) {
		//设置查询条件
		Example example = new Example(ArticleType.class);
		Example.Criteria creiteria = example.createCriteria();

		if(StringUtils.isNotBlank(typeName)){
			creiteria.andEqualTo("typeName",typeName);
		}

		if(StringUtils.isNotBlank(typeId)){
			creiteria.andNotEqualTo("id",typeId);
		}
		return articleTypeMapper.selectByExample(example);
	}

}