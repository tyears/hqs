package com.cccuu.project.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cccuu.project.utils.BaseModel;
/**
 * 
 * @Description 后台用户角色类
 * @author zhaixiaoliang
 * @author 2016年11月8日上午9:23:29
 */
@Entity
@Table(name="sys_user_role")
public class SysUserRole extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id", nullable = false)
	private String userId;//用户id
	
	@Column(name = "role_id", nullable = false)
	private String roleId;//角色id

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
