package com.cccuu.project.model.frontcore;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cccuu.project.utils.BaseModel;

/**
 * 前台权限类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年8月21日下午4:02:12
 */
@Entity
@Table(name="t_rights")
public class Rights extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;//权限名称
	
	@Column(name = "code")
	private String code;//权限编码
	
	@Column(name = "fk_id")
	private String fkId;//上级
	
	@Column(name = "sort")
	private Integer sort;//排序
	
	@Column(name = "is_parent")
	private String isParent;//是否是父节点（y：是n：不是）
	
	@Transient
	private String checked;//树节点是否被选中
	
	@Transient
	private String open;//树节点是否展开
	
	@Transient
	private Rights rights;//上级
	
	@Transient
	private List<Rights> rightsList;//下级

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

	public Rights getRights() {
		return rights;
	}

	public void setRights(Rights rights) {
		this.rights = rights;
	}

	public List<Rights> getRightsList() {
		return rightsList;
	}

	public void setRightsList(List<Rights> rightsList) {
		this.rightsList = rightsList;
	}
}
