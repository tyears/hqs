package com.cccuu.project.model.core.generator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cccuu.project.utils.BaseModel;
import com.cccuu.project.utils.StringUtils;
@Entity
@Table(name = "sys_generator_table_column")
public class GeneratorTableColumn extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "fk_id")
	private String fkId;//父级id
	
	@Column(name = "name")
	private String name;//字段名
	
	@Column(name = "type")
	private String type;//类型（1：varchar2：int3：double4：datetime5：decimal）
	
	@Column(name = "length")
	private Integer length;//长度
	
	@Column(name = "is_primary_key")
	private String isPrimaryKey;//是否主键y/n
	
	@Column(name = "remark")
	private String remark;//注释
	
	@Column(name = "is_query")
	private String isQuery;//是否作为查询条件y/n
	
	@Column(name = "is_required")
	private String isRequired;//是否必填y/n
	
	@Column(name = "is_list")
	private String isList;//是否作为列表字段y/n
	
	
	@Transient
	private String filedName;//类中字段名（驼峰转is_primary_key->isPrimaryKey）

	public String getFkId() {
		return fkId;
	}

	public void setFkId(String fkId) {
		this.fkId = fkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	//驼峰转
	public String getFiledName() {
		return StringUtils.toCamelCase(name);
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
}
