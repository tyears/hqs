package com.cccuu.project.model.core;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;
/**
 * 数据字典表
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年7月11日上午10:33:19
 */
@Entity
@Table(name = "sys_dictionary")
public class SysDictionary extends BaseModel{
	

	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;//字典名称
	
	@Column(name = "code")
	private String code;//字典编号（唯一）
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
