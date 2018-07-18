package com.cccuu.project.service.article.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.article.Article;
import com.cccuu.project.mapper.article.ArticleMapper;
import com.cccuu.project.service.article.ArticleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 文章ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:49:50
 */
@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService{
	
	private ArticleMapper articleMapper;
	
	@Autowired
	public void setArticleMapper(ArticleMapper articleMapper){
		this.articleMapper = articleMapper;
		baseMapper = articleMapper;
	}
	@Override
	@Transactional
	public PageInfo<Article> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(Article.class);
		example.setOrderByClause(" sort asc ");
		Example.Criteria creiteria = example.createCriteria();
		
		String title = params.get("title");
		if(StringUtils.isNotBlank(title)){
			creiteria.andLike("title","%"+title+"%");
		}

		String articleTypeId = params.get("articleTypeId");
		if(StringUtils.isNotBlank(articleTypeId)){
			creiteria.andEqualTo("articleTypeId",articleTypeId);
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Article> results = articleMapper.selectByExample(example);
		
		PageInfo<Article> pageInfo = new PageInfo<Article>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Article> queryList(Map<String, String> params) {
		//设置查询条件
		Example example = new Example(Article.class);
		example.setOrderByClause(" sort asc ");
		Example.Criteria creiteria = example.createCriteria();

		String articleTypeId = params.get("articleTypeId");
		if(StringUtils.isNotBlank(articleTypeId)){
			creiteria.andEqualTo("articleTypeId",articleTypeId);
		}
		String articleTitle = params.get("articleTitle");
		if(StringUtils.isNotBlank(articleTitle)){
			creiteria.andLike("title","%"+articleTitle+"%");
		}
		return  articleMapper.selectByExample(example);
	}

	@Override
	@Transactional
	public int addMulti(List<Article> articleList) {
		return articleMapper.addMulti(articleList);
	}

}