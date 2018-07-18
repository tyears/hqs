package com.cccuu.project.model.article;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 文章表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:49:50
 */
@Entity
@Table(name="t_article")
public class Article extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "title")
	private String title;//标题
	
	
	@Column(name = "technology_url")
	private String technologyUrl;//技术平台网址
	
	
	@Column(name = "content")
	private String content;//内容
	
	
	@Column(name = "reply_sms")
	private String replySms;//答复短信
	
	
	@Column(name = "prompt_sms")
	private String promptSms;//提示短信
	
	
	@Column(name = "remark")
	private String remark;//备注
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_time")
	private Date modifyTime;//更新时间
	
	
	@Column(name = "article_type_id")
	private String articleTypeId;//文章类别表id

	@Column(name = "sort")
	private Integer sort;//排序

	@Column(name = "sms_num")
	private Integer smsNum;//发送短信数量
	
		
	/**
	 * 获取标题
	 * @return
	 */		
	public String getTitle() {
        return this.title;
    }
    /**
	 * 设置标题
	 * @param title
	 */
    public void setTitle(String title) {
        this.title = title;
    }
	/**
	 * 获取技术平台网址
	 * @return
	 */		
	public String getTechnologyUrl() {
        return this.technologyUrl;
    }
    /**
	 * 设置技术平台网址
	 * @param technologyUrl
	 */
    public void setTechnologyUrl(String technologyUrl) {
        this.technologyUrl = technologyUrl;
    }
	/**
	 * 获取内容
	 * @return
	 */		
	public String getContent() {
        return this.content;
    }
    /**
	 * 设置内容
	 * @param content
	 */
    public void setContent(String content) {
        this.content = content;
    }
	/**
	 * 获取答复短信
	 * @return
	 */		
	public String getReplySms() {
        return this.replySms;
    }
    /**
	 * 设置答复短信
	 * @param replySms
	 */
    public void setReplySms(String replySms) {
        this.replySms = replySms;
    }
	/**
	 * 获取提示短信
	 * @return
	 */		
	public String getPromptSms() {
        return this.promptSms;
    }
    /**
	 * 设置提示短信
	 * @param promptSms
	 */
    public void setPromptSms(String promptSms) {
        this.promptSms = promptSms;
    }
	/**
	 * 获取备注
	 * @return
	 */		
	public String getRemark() {
        return this.remark;
    }
    /**
	 * 设置备注
	 * @param remark
	 */
    public void setRemark(String remark) {
        this.remark = remark;
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
	/**
	 * 获取文章类别表id
	 * @return
	 */		
	public String getArticleTypeId() {
        return this.articleTypeId;
    }
    /**
	 * 设置文章类别表id
	 * @param articleTypeId
	 */
    public void setArticleTypeId(String articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSmsNum() {
		return smsNum;
	}

	public void setSmsNum(Integer smsNum) {
		this.smsNum = smsNum;
	}
}
