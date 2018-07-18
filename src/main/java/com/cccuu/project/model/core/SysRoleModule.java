package com.cccuu.project.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cccuu.project.utils.BaseModel;

/**
 * 
 * @Description 后台角色资源类
 * @author zhaixiaoliang
 * @author 2016年11月7日上午9:53:00
 */
@Entity
@Table(name="sys_role_module")
public class SysRoleModule extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "role_id", nullable = false)
	private String roleId;//角色id
	
	@Column(name = "module_id", nullable = false)
	private String moduleId;//资源id

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
