package com.cccuu.project.model.food;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 食品表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
@Entity
@Table(name="t_food")
public class Food extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "food_name")
	private String foodName;//食品名称
	
	
	@Column(name = "type_id")
	private String typeId;//分类id

	@Column(name = "jianpin")
	private String jianpin;//简拼
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
		
	/**
	 * 获取食品名称
	 * @return
	 */		
	public String getFoodName() {
        return this.foodName;
    }
    /**
	 * 设置食品名称
	 * @param foodName
	 */
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
	/**
	 * 获取分类id
	 * @return
	 */		
	public String getTypeId() {
        return this.typeId;
    }
    /**
	 * 设置分类id
	 * @param typeId
	 */
    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
	/**
	 * 获取简拼
	 * @return
	 */
	public String getJianpin() {
		return jianpin;
	}

	/**
	 * 设置简拼
	 * @param jianpin
	 */
	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	/**
	 * 获取编辑时间
	 * @return
	 */		
	public Date getCreateTime() {
        return this.createTime;
    }
    /**
	 * 设置编辑时间
	 * @param createTime
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
