package com.cccuu.project.model.giveproduct;

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
 * 赠送产品关联表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年10月12日 17:45:31
 */
@Entity
@Table(name="t_give_product")
public class GiveProduct extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "give_id")
	private String giveId;//赠送表id
	
	
	@Column(name = "product_id")
	private String productId;//产品表id
	
	
	@Column(name = "give_num")
	private Integer giveNum;//赠送产品数量
	
		
	/**
	 * 获取赠送表id
	 * @return
	 */		
	public String getGiveId() {
        return this.giveId;
    }
    /**
	 * 设置赠送表id
	 * @param giveId
	 */
    public void setGiveId(String giveId) {
        this.giveId = giveId;
    }
	/**
	 * 获取产品表id
	 * @return
	 */		
	public String getProductId() {
        return this.productId;
    }
    /**
	 * 设置产品表id
	 * @param productId
	 */
    public void setProductId(String productId) {
        this.productId = productId;
    }
	/**
	 * 获取赠送产品数量
	 * @return
	 */		
	public Integer getGiveNum() {
        return this.giveNum;
    }
    /**
	 * 设置赠送产品数量
	 * @param giveNum
	 */
    public void setGiveNum(Integer giveNum) {
        this.giveNum = giveNum;
    }
}
