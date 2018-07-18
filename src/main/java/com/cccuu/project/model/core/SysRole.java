package com.cccuu.project.model.core;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;

/**
 * 
 * @Description 后台角色类
 * @author zhaixiaoliang
 * @author 2016年11月7日上午9:53:00
 */
@Entity
@Table(name="sys_role")
public class SysRole extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;//角色名称
	
	@Column(name = "code")
	private String code;//角色编码
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
	@Transient
	private String checked;//树节点是否被选中
	
	@Transient
	private List<SysRoleModule> sysRoleModuleList;//角色资源

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

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public List<SysRoleModule> getSysRoleModuleList() {
		return sysRoleModuleList;
	}

	public void setSysRoleModuleList(List<SysRoleModule> sysRoleModuleList) {
		this.sysRoleModuleList = sysRoleModuleList;
	}
}
