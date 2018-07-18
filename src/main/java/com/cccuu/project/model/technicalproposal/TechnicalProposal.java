package com.cccuu.project.model.technicalproposal;

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
 * 技术建议表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月20日 10:55:15
 */
@Entity
@Table(name="t_technical_proposal")
public class TechnicalProposal extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "text")
	private String text;//建议内容
	
	
	@Column(name = "user_id")
	private String userId;//添加人id
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//添加时间
	
	
	@Column(name = "state")
	private String state;//是否下载(0:未下载 1:已下载 )
	
		
	/**
	 * 获取建议内容
	 * @return
	 */		
	public String getText() {
        return this.text;
    }
    /**
	 * 设置建议内容
	 * @param text
	 */
    public void setText(String text) {
        this.text = text;
    }
	/**
	 * 获取添加人id
	 * @return
	 */		
	public String getUserId() {
        return this.userId;
    }
    /**
	 * 设置添加人id
	 * @param userId
	 */
    public void setUserId(String userId) {
        this.userId = userId;
    }
	/**
	 * 获取添加时间
	 * @return
	 */		
	public Date getCreateTime() {
        return this.createTime;
    }
    /**
	 * 设置添加时间
	 * @param createTime
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	/**
	 * 获取是否下载(0:未下载 1:已下载 )
	 * @return
	 */		
	public String getState() {
        return this.state;
    }
    /**
	 * 设置是否下载(0:未下载 1:已下载 )
	 * @param state
	 */
    public void setState(String state) {
        this.state = state;
    }
}
