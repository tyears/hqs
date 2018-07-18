package com.cccuu.project.model.sms;

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
 * 短信表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年10月25日 18:02:05
 */
@Entity
@Table(name="t_sms")
public class Sms extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "sms_type")
	private String smsType;//短信类型
	
	
	@Column(name = "content")
	private String content;//短信内容
	
	
	@Column(name = "user_name")
	private String userName;//操作人
	
	
	@Column(name = "user_id")
	private String userId;//操作人id
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//操作时间
	
	
	@Column(name = "tel")
	private String tel;//接收号码
	
		
	/**
	 * 获取短信类型
	 * @return
	 */		
	public String getSmsType() {
        return this.smsType;
    }
    /**
	 * 设置短信类型
	 * @param smsType
	 */
    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }
	/**
	 * 获取短信内容
	 * @return
	 */		
	public String getContent() {
        return this.content;
    }
    /**
	 * 设置短信内容
	 * @param content
	 */
    public void setContent(String content) {
        this.content = content;
    }
	/**
	 * 获取操作人
	 * @return
	 */		
	public String getUserName() {
        return this.userName;
    }
    /**
	 * 设置操作人
	 * @param userName
	 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
	/**
	 * 获取操作人id
	 * @return
	 */		
	public String getUserId() {
        return this.userId;
    }
    /**
	 * 设置操作人id
	 * @param userId
	 */
    public void setUserId(String userId) {
        this.userId = userId;
    }
	/**
	 * 获取操作时间
	 * @return
	 */		
	public Date getCreateTime() {
        return this.createTime;
    }
    /**
	 * 设置操作时间
	 * @param createTime
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
	/**
	 * 获取接收号码
	 * @return
	 */		
	public String getTel() {
        return this.tel;
    }
    /**
	 * 设置接收号码
	 * @param tel
	 */
    public void setTel(String tel) {
        this.tel = tel;
    }
}
