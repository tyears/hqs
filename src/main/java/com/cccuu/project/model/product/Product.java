package com.cccuu.project.model.product;

import com.cccuu.project.utils.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 产品表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:39:39
 */
@Entity
@Table(name="t_product")
public class Product extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	//reserved1 是否伪删除 1:是 0：否
	
	@Column(name = "product_name")
	private String productName;//产品名称

	@Column(name = "jianpin")
	private String jianpin;//简拼
	
	
	@Column(name = "type_id")
	private String typeId;//分类id
	
	
	@Column(name = "product_num")
	private String productNum;//货号
	
	
	@Column(name = "spec")
	private String spec;//规格
	
	
	@Column(name = "retail_price_bag")
	private BigDecimal retailPriceBag;//零售价/袋
	
	
	@Column(name = "retail_price_box")
	private BigDecimal retailPriceBox;//零售价/箱
	
	
	@Column(name = "sell_price_bag")
	private BigDecimal sellPriceBag;//经销价/袋
	
	
	@Column(name = "sell_price_box")
	private BigDecimal sellPriceBox;//经销价/箱
	
	
	@Column(name = "remark")
	private String remark;//备注
	
	
	@Column(name = "sort")
	private Integer sort;//排序
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间


	@Column(name = "num_box")
	private Integer numBox;//每箱袋数
		
	/**
	 * 获取产品名称
	 * @return
	 */		
	public String getProductName() {
        return this.productName;
    }
    /**
	 * 设置产品名称
	 * @param productName
	 */
    public void setProductName(String productName) {
        this.productName = productName;
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
	 * 获取货号
	 * @return
	 */		
	public String getProductNum() {
        return this.productNum;
    }
    /**
	 * 设置货号
	 * @param productNum
	 */
    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }
	/**
	 * 获取规格
	 * @return
	 */		
	public String getSpec() {
        return this.spec;
    }
    /**
	 * 设置规格
	 * @param spec
	 */
    public void setSpec(String spec) {
        this.spec = spec;
    }
	/**
	 * 获取零售价/袋
	 * @return
	 */	
	public BigDecimal getRetailPriceBag() {
        return this.retailPriceBag;
    }
    /**
	 * 设置零售价/袋
	 * @param retailPriceBag
	 */
    public void setRetailPriceBag(BigDecimal retailPriceBag) {
        this.retailPriceBag = retailPriceBag;
    }
	/**
	 * 获取零售价/箱
	 * @return
	 */	
	public BigDecimal getRetailPriceBox() {
        return this.retailPriceBox;
    }
    /**
	 * 设置零售价/箱
	 * @param retailPriceBox
	 */
    public void setRetailPriceBox(BigDecimal retailPriceBox) {
        this.retailPriceBox = retailPriceBox;
    }
	/**
	 * 获取经销价/袋
	 * @return
	 */	
	public BigDecimal getSellPriceBag() {
        return this.sellPriceBag;
    }
    /**
	 * 设置经销价/袋
	 * @param sellPriceBag
	 */
    public void setSellPriceBag(BigDecimal sellPriceBag) {
        this.sellPriceBag = sellPriceBag;
    }
	/**
	 * 获取经销价/箱
	 * @return
	 */	
	public BigDecimal getSellPriceBox() {
        return this.sellPriceBox;
    }
    /**
	 * 设置经销价/箱
	 * @param sellPriceBox
	 */
    public void setSellPriceBox(BigDecimal sellPriceBox) {
        this.sellPriceBox = sellPriceBox;
    }
	/**
	 * 获取备注
	 * @return
	 */		
	public String getRemark() {
        return this.remark;
    }
    /**
	 * 设置备注
	 * @param remark
	 */
    public void setRemark(String remark) {
        this.remark = remark;
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

	public Integer getNumBox() {
		return numBox;
	}

	public void setNumBox(Integer numBox) {
		this.numBox = numBox;
	}
}
