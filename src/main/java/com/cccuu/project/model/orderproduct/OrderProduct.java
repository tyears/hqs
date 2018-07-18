package com.cccuu.project.model.orderproduct;

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
 * 订单产品关联表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月22日 17:44:40
 */
@Entity
@Table(name="t_order_product")
public class OrderProduct extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "order_id")
	private String orderId;//订单id
	
	
	@Column(name = "product_id")
	private String productId;//产品表id
	
		
	/**
	 * 获取订单id
	 * @return
	 */		
	public String getOrderId() {
        return this.orderId;
    }
    /**
	 * 设置订单id
	 * @param orderId
	 */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
