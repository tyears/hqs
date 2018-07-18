package com.cccuu.project.model.product;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;

import com.cccuu.project.utils.BaseModel;
/**
 * 产品类别
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:39:19
 */
@Entity
@Table(name="t_product_type")
public class ProductType extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "name")
	private String name;//类别名称
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间

	@Transient
	private List<Product> productList;//分类下产品列表
	
		
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

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
