package com.cccuu.project.model.give;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 赠送表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年10月12日 17:42:30
 */
@Entity
@Table(name="t_give")
public class Give extends BaseModel{
	
	private static final long serialVersionUID = 1L;

	//reserved1已使用 是否已打印 y/n
	
	@Column(name = "order_id")
	private String orderId;//订单id
	
	
	@Column(name = "give_man_id")
	private String giveManId;//赠送人id
	
	
	@Column(name = "give_man_name")
	private String giveManName;//赠送人姓名
	
	
	@Column(name = "state")
	private String state;//审核状态：1.未审核;2.审核通过;3.已驳回
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "give_time")
	private Date giveTime;//赠送时间

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
	
	@Column(name = "give_type")
	private String giveType;//赠送类型 :1,赠送给经销商；2，公司赠送；3经销商赠送
	
	
	@Column(name = "remark")
	private String remark;//赠送说明

	@Column(name = "give_content")
	private String giveContent;//赠送产品内容
	
	
	@Column(name = "goods_num")
	private String goodsNum;//快递单号
	
	
	@Column(name = "dealer_id")
	private String dealerId;//经销商id

	@Column(name = "is_notice")
	private String isNotice;//是否通知客户 0，未通知；1，已通知

	@Column(name = "is_dealer")
	private String isDealer;//是否通知经销商 0，未通知；1，已通知

	@Column(name = "only_num")
	private String onlyNum;//唯一编号 时间加最大值

	@Column(name = "only_num_max")
	private Integer onlyNumMax;//唯一编号最大值

	@Transient
	private String productIds;//赠送产品id拼接

	@Transient
	private String productNums;//赠送产品数量拼接
	
		
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
	 * 获取赠送人id
	 * @return
	 */		
	public String getGiveManId() {
        return this.giveManId;
    }
    /**
	 * 设置赠送人id
	 * @param giveManId
	 */
    public void setGiveManId(String giveManId) {
        this.giveManId = giveManId;
    }
	/**
	 * 获取赠送人姓名
	 * @return
	 */		
	public String getGiveManName() {
        return this.giveManName;
    }
    /**
	 * 设置赠送人姓名
	 * @param giveManName
	 */
    public void setGiveManName(String giveManName) {
        this.giveManName = giveManName;
    }
	/**
	 * 获取审核状态：1.未审核;2.审核通过;3.已驳回
	 * @return
	 */		
	public String getState() {
        return this.state;
    }
    /**
	 * 设置审核状态：1.未审核;2.审核通过;3.已驳回
	 * @param state
	 */
    public void setState(String state) {
        this.state = state;
    }
	/**
	 * 获取赠送时间
	 * @return
	 */		
	public Date getGiveTime() {
        return this.giveTime;
    }
    /**
	 * 设置赠送时间
	 * @param giveTime
	 */
    public void setGiveTime(Date giveTime) {
        this.giveTime = giveTime;
    }
	/**
	 * 获取赠送类型
	 * @return
	 */		
	public String getGiveType() {
        return this.giveType;
    }
    /**
	 * 设置赠送类型
	 * @param giveType
	 */
    public void setGiveType(String giveType) {
        this.giveType = giveType;
    }
	/**
	 * 获取赠送说明
	 * @return
	 */		
	public String getRemark() {
        return this.remark;
    }
    /**
	 * 设置赠送说明
	 * @param remark
	 */
    public void setRemark(String remark) {
        this.remark = remark;
    }
	/**
	 * 获取快递单号
	 * @return
	 */		
	public String getGoodsNum() {
        return this.goodsNum;
    }
    /**
	 * 设置快递单号
	 * @param goodsNum
	 */
    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }
	/**
	 * 获取经销商id
	 * @return
	 */		
	public String getDealerId() {
        return this.dealerId;
    }
    /**
	 * 设置经销商id
	 * @param dealerId
	 */
    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGiveContent() {
		return giveContent;
	}

	public void setGiveContent(String giveContent) {
		this.giveContent = giveContent;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getProductNums() {
		return productNums;
	}

	public void setProductNums(String productNums) {
		this.productNums = productNums;
	}

	public String getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(String isNotice) {
		this.isNotice = isNotice;
	}

	public String getOnlyNum() {
		return onlyNum;
	}

	public void setOnlyNum(String onlyNum) {
		this.onlyNum = onlyNum;
	}

	public Integer getOnlyNumMax() {
		return onlyNumMax;
	}

	public void setOnlyNumMax(Integer onlyNumMax) {
		this.onlyNumMax = onlyNumMax;
	}

	public String getIsDealer() {
		return isDealer;
	}

	public void setIsDealer(String isDealer) {
		this.isDealer = isDealer;
	}
}
