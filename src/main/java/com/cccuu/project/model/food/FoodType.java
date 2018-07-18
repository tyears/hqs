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
 * 食品类别
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:38:27
 */
@Entity
@Table(name="t_food_type")
public class FoodType extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "name")
	private String name;//类别名称
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
		
	/**
	 * 获取类别名称
	 * @return
	 */		
	public String getName() {
        return this.name;
    }
    /**
	 * 设置类别名称
	 * @param name
	 */
    public void setName(String name) {
        this.name = name;
    }
	/**
	 * 获取创建时间
	 * @return
	 */		
	public Date getCreateTime() {
        return this.createTime;
    }
    /**
	 * 设置创建时间
	 * @param createTime
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
