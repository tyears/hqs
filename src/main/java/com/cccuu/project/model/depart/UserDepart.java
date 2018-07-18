package com.cccuu.project.model.depart;

import com.cccuu.project.utils.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @Description 前台用户部门表
 * @author zhaixiaoliang
 * @author 2016年11月8日上午9:23:29
 */
@Entity
@Table(name="t_user_depart")
public class UserDepart extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private String userId;//用户id
	
	@Column(name = "depart_id")
	private String departId;//部门id

	@Column(name = "flag")
	private String flag;//标识(0:询单部门1：打印部门)

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
