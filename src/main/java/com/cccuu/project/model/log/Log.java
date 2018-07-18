package com.cccuu.project.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;
@Entity
@Table(name = "t_log")
public class Log extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private String userId;//用户id
	
	@Column(name = "operation")
	private String operation;//操作名称
	
	@Column(name = "type")
	private String type;	//后台:0  前台:1
	
	@Column(name = "content")
	private String content;//内容
	
	@Column(name = "method")
	private String method;//用户姓名
	
	@Column(name = "request_ip")
	private String requestIp;//访问ip
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
