package com.cccuu.project.model.product;

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
 * 产品适用食品表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:40:47
 */
@Entity
@Table(name="t_product_food")
public class ProductFood extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "product_id")
	private String productId;//产品id
	
	
	@Column(name = "food_id")
	private String foodId;//食品id
	
	
	@Column(name = "sort")
	private Integer sort;//排序
	
		
	/**
	 * 获取产品id
	 * @return
	 */		
	public String getProductId() {
        return this.productId;
    }
    /**
	 * 设置产品id
	 * @param productId
	 */
    public void setProductId(String productId) {
        this.productId = productId;
    }
	/**
	 * 获取食品id
	 * @return
	 */		
	public String getFoodId() {
        return this.foodId;
    }
    /**
	 * 设置食品id
	 * @param foodId
	 */
    public void setFoodId(String foodId) {
        this.foodId = foodId;
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
}
