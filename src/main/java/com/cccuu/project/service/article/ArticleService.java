package com.cccuu.project.service.article;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.article.Article;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 文章Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:49:50
 */
public interface ArticleService extends BaseService<Article>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Article> queryListByPage(Map<String,String> params);

	/**
	 * 查询列表
	 * @param params
	 * @return
	 */
	public List<Article> queryList(Map<String,String> params);
	/**
	 * 批量插入
	 * @param articleList
	 * @return
	 */
	public int addMulti(List<Article> articleList);
}