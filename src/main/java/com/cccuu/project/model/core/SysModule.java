package com.cccuu.project.model.core;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cccuu.project.utils.BaseModel;

/**
 * 资源菜单
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年7月4日上午10:35:30
 */
@Entity
@Table(name = "sys_module")
public class SysModule extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;//资源名称
	
	@Column(name = "path")
	private String path;//资源路径
	
	@Column(name = "icon_value")
	private String iconValue;//图标
	
	@Column(name = "fk_id")
	private String fkId;//上级
	
	@Column(name = "sort")
	private Integer sort;//排序
	
	@Column(name = "is_parent")
	private String isParent;//是否是父节点（true：是false：不是）
	
	@Transient
	private String checked;//树节点是否被选中
	
	@Transient
	private String open;//树节点是否展开
	
	@Transient
	private SysModule sysModule;//上级
	
	@Transient
	private List<SysModule> sysModuleList;//下级

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getIconValue() {
		return iconValue;
	}

	public void setIconValue(String iconValue) {
		this.iconValue = iconValue;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	public List<SysModule> getSysModuleList() {
		return sysModuleList;
	}

	public void setSysModuleList(List<SysModule> sysModuleList) {
		this.sysModuleList = sysModuleList;
	}
}
