package com.cccuu.project.model.area;

import javax.persistence.*;

import com.cccuu.project.model.frontcore.Rights;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.cccuu.project.utils.BaseModel;
/**
 * 区域表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 16:43:02
 */
@Entity
@Table(name="t_area")
public class Area extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 区域名字 area_name
	 */
	@Column(name = "area_name")
	private String areaName;

	/**
	 * 上级
	 */
	@Column(name = "fk_id")
	private String fkId;
	
	
	@Column(name = "sort")
	private Integer sort;//排序
	
	
	@Column(name = "is_parent")
	private String isParent;//是否是父节点（y：是n：不是）
	
	
	@Column(name = "level")
	private Integer level;//层级 0省 1市 2区

	@Column(name = "spell")
	private String spell;//拼音

	@Column(name = "comment")
	private String comment;//评价（只有区级有）

	@Column(name = "is_import")
	private String isImport;//是否导入

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time")
	private Date importTime;//评价导入时间

	@Transient
	private String checked;//树节点是否被选中

	@Transient
	private String open;//树节点是否展开

	@Transient
	private Area area;//上级

	@Transient
	private List<Area> areaList;//下级

	@Transient
	private Integer hzNum;//合作商家数量

	@Transient
	private Integer whzNum;//未合作商家数量

	@Transient
	private Integer spNum;//食品厂数量

	@Transient
	private Integer mfNum;//面粉厂数量
	
		
	/**
	 * 获取区域名字
	 * @return
	 */		
	public String getAreaName() {
        return this.areaName;
    }
    /**
	 * 设置区域名字
	 * @param areaName
	 */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
	/**
	 * 获取上级
	 * @return
	 */		
	public String getFkId() {
        return this.fkId;
    }
    /**
	 * 设置上级
	 * @param fkId
	 */
    public void setFkId(String fkId) {
        this.fkId = fkId;
    }
    /**
	 * 获取排序
	 * @return
	 */		
	public Integer getSort() {
        return this.sort;
    }
    /**
	 * 设置排序
	 * @param sort
	 */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
	/**
	 * 获取是否是父节点（y：是n：不是）
	 * @return
	 */		
	public String getIsParent() {
        return this.isParent;
    }
    /**
	 * 设置是否是父节点（y：是n：不是）
	 * @param isParent
	 */
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

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	/**
	 * 获取层级
	 * @return
	 */

	public Integer getLevel() {
        return this.level;
    }
    /**
	 * 设置层级
	 * @param level
	 */
    public void setLevel(Integer level) {
        this.level = level;
    }

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public Integer getHzNum() {
		return hzNum;
	}

	public void setHzNum(Integer hzNum) {
		this.hzNum = hzNum;
	}

	public Integer getWhzNum() {
		return whzNum;
	}

	public void setWhzNum(Integer whzNum) {
		this.whzNum = whzNum;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getSpNum() {
		return spNum;
	}

	public void setSpNum(Integer spNum) {
		this.spNum = spNum;
	}

	public Integer getMfNum() {
		return mfNum;
	}

	public void setMfNum(Integer mfNum) {
		this.mfNum = mfNum;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}
}
