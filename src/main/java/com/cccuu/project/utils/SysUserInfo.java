package com.cccuu.project.utils;

import java.io.Serializable;

import com.cccuu.project.model.sysuser.SysUser;
/**
 * 后台登录用户信息封装类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:29:07
 */
public class SysUserInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String sysUserId;//用户登录表id
	private String userName;//用户名
	private String name;//姓名
	private String userType;//用户类型
	
	public SysUserInfo(){
		
	}
	
	public SysUserInfo(SysUser sysUser){
		this.sysUserId = sysUser.getId();
		this.userName = sysUser.getUserName();
		this.name = sysUser.getName();
		this.userType = sysUser.getUserType();
	}

	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
}
