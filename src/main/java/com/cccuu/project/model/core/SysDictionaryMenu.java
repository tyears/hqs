package com.cccuu.project.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cccuu.project.utils.BaseModel;
/**
 * 数据字典表项
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年7月11日上午10:33:19
 */
@Entity
@Table(name = "sys_dictionary_menu")
public class SysDictionaryMenu extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;//字典项名称
	
	@Column(name = "value")
	private String value;//字典项值
	
	@Column(name = "sort")
	private Integer sort;//排序
	
	@Column(name = "fk_id")
	private String fkId;//字典id
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort == null ? 0:sort;
	}

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}
}
