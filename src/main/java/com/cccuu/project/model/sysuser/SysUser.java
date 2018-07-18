package com.cccuu.project.model.sysuser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;
/**
 * 后台用户表
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:41:55
 */
@Entity
@Table(name="sys_user")
public class SysUser extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column(name="name")
	private String name;//姓名
	
	@Column(name="user_name")
	private String userName;//用户名
	
	@Column(name="password")
	private String passWord;//密码
	
	@Column(name="user_type")
	private String userType;//用户类型(0:超级管理员1:普通管理员:)
	@Column(name="state")
	private String state;//状态(0:禁用1:启用)
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login_time")
	private Date lastLoginTime;//最后登录时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;//创建时间
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
