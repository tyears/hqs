package com.cccuu.project.model.frontcore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cccuu.project.utils.BaseModel;
/**
 * 
 * @Description 前台角色权限类
 * @author zhaixiaoliang
 * @author 2016年11月7日上午9:53:00
 */
@Entity
@Table(name="t_role_rights")
public class RoleRights extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "role_id")
	private String roleId;//角色id
	
	@Column(name = "rights_id")
	private String rightsId;//权限id

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRightsId() {
		return rightsId;
	}

	public void setRightsId(String rightsId) {
		this.rightsId = rightsId;
	}
	
}
