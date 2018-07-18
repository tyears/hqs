package com.cccuu.project.model.articletype;

import javax.persistence.*;

import com.cccuu.project.model.article.Article;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.cccuu.project.utils.BaseModel;
/**
 * 文章类别
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:23:37
 */
@Entity
@Table(name="t_article_type")
public class ArticleType extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "type_name")
	private String typeName;//类型名称

	@Column(name = "sort")
	private Integer sort;//排序
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_time")
	private Date modifyTime;//更新时间

	@Transient
	private List<Article> articleList;
	
		
	/**
	 * 获取类型名称
	 * @return
	 */		
	public String getTypeName() {
        return this.typeName;
    }
    /**
	 * 设置类型名称
	 * @param typeName
	 */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
	/**
	 * 获取更新时间
	 * @return
	 */		
	public Date getModifyTime() {
        return this.modifyTime;
    }
    /**
	 * 设置更新时间
	 * @param modifyTime
	 */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
