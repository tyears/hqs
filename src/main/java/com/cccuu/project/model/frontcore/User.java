package com.cccuu.project.model.frontcore;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;
/***
 * 前台人员表
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年8月21日下午3:57:23
 */
@Entity
@Table(name="t_user")
public class User extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_name")
	private String userName;//用户名
	
	@Column(name = "password")
	private String passWord;//密码
	
	@Column(name = "name")
	private String name;//姓名
	
	@Column(name = "user_type")
	private String userType;//用户类型 0 员工 1 经理

	@Column(name = "depart_id")
	private String departId;//归属部门id
	
	@Column(name = "state")
	private String state;//状态(1:启用0:禁用)
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_time")
	private Date lastLoginTime;//最后登录时间
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间

	@Column(name = "transfer_num")
	private Integer transferNum;//调单次数

	@Column(name = "extension_num")
	private String extensionNum;//分机号

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="obtain_time")
	private Date obtainTime;//调单时间

	public String getExtensionNum() {
		return extensionNum;
	}

	public void setExtensionNum(String extensionNum) {
		this.extensionNum = extensionNum;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
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

	public Integer getTransferNum() {
		return transferNum;
	}

	public void setTransferNum(Integer transferNum) {
		this.transferNum = transferNum;
	}

	public Date getObtainTime() {
		return obtainTime;
	}

	public void setObtainTime(Date obtainTime) {
		this.obtainTime = obtainTime;
	}
}
