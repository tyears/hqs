package com.cccuu.project.model.core.generator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.cccuu.project.utils.BaseModel;

@Entity
@Table(name = "sys_generator_table")
public class GeneratorTable extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "table_name")
	private String tableName;//表名
	
	@Column(name = "name")
	private String name;//表汉语名
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_time")
	private Date modifyTime;//更新时间

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
