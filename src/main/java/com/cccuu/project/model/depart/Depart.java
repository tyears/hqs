package com.cccuu.project.model.depart;

import com.cccuu.project.utils.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 部门表
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年7月11日上午10:33:19
 */
@Entity
@Table(name = "t_depart")
public class Depart extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "depart_name")
	private String departName;//部门名称
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间

	@Transient
	private String checked;//是否选中

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
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
}
