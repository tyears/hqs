package com.cccuu.project.mapper.article;

import com.cccuu.project.model.article.Article;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;

/**
 * 文章Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:49:50
 */
public interface ArticleMapper extends BaseMapper<Article>{
    /**
     * 批量插入
     * @param articleList
     * @return
     */
    public int addMulti(List<Article> articleList);
}