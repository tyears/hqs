package com.cccuu.project.service.articletype;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.articletype.ArticleType;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 文章类别Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:23:37
 */
public interface ArticleTypeService extends BaseService<ArticleType>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<ArticleType> queryListByPage(Map<String,String> params);

	/**
	 * 分页查询列表
	 * @return
	 */
	public List<ArticleType> queryAllList();
	/**
	 * 判断是否名称重复
	 * @return
	 */
	public List<ArticleType> queryListByName(String typeName, String typeId);
}