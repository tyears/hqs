package com.cccuu.project.model.dealerproduct;

import javax.persistence.*;

import com.cccuu.project.model.product.Product;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 客户产品关联表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月15日 10:44:27
 */
@Entity
@Table(name="t_dealer_product")
public class DealerProduct extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "product_id")
	private String productId;//产品表id
	
	
	@Column(name = "give_num")
	private Integer giveNum;//赠送件数
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "first_time")
	private Date firstTime;//第一次赠送


	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_time")
	private Date lastTime;//最后赠送
	
	
	@Column(name = "dealer_give_num")
	private Integer dealerGiveNum;//经销商赠送
	
	
	@Column(name = "company_give_num")
	private Integer companyGiveNum;//公司赠送

	@Column(name = "notice_num")
	private Integer noticeNum;//通知信息数
	
	
	@Column(name = "comment")
	private String comment;//评价
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_purchase_time")
	private Date lastPurchaseTime;//最后进货
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "true_month_time")
	private Date trueMonthTime;//确定月数
	
	
	@Column(name = "remark")
	private String remark;//备注
	
	
	@Column(name = "dealer_id")
	private String dealerId;//客户表id

	@Column(name = "is_import")
	private String isImport;//评价是否导入

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time")
	private Date importTime;//评价导入时间

	@Column(name = "is_import_purchase")
	private String isImportPurchase;//最后进货日期是否导入

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_purchase_time")
	private Date importPurchaseTime;//最后进货日期导入时间

	@Transient
	private Product product;

	@Transient
	private String isGiveUpdate;//是否赠送修改 y/n
	
		
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
	 * 获取赠送件数
	 * @return
	 */		
	public Integer getGiveNum() {
        return this.giveNum;
    }
    /**
	 * 设置赠送件数
	 * @param giveNum
	 */
    public void setGiveNum(Integer giveNum) {
        this.giveNum = giveNum;
    }
	/**
	 * 获取第一次赠送
	 * @return
	 */		
	public Date getFirstTime() {
        return this.firstTime;
    }
    /**
	 * 设置第一次赠送
	 * @param firstTime
	 */
    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }
	/**
	 * 获取最后赠送
	 * @return
	 */		
	public Date getLastTime() {
        return this.lastTime;
    }
    /**
	 * 设置最后赠送
	 * @param lastTime
	 */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }
    /**
	 * 获取经销商赠送
	 * @return
	 */		
	public Integer getDealerGiveNum() {
        return this.dealerGiveNum;
    }
    /**
	 * 设置经销商赠送
	 * @param dealerGiveNum
	 */
    public void setDealerGiveNum(Integer dealerGiveNum) {
        this.dealerGiveNum = dealerGiveNum;
    }
    /**
	 * 获取公司赠送
	 * @return
	 */		
	public Integer getCompanyGiveNum() {
        return this.companyGiveNum;
    }
    /**
	 * 设置公司赠送
	 * @param companyGiveNum
	 */
    public void setCompanyGiveNum(Integer companyGiveNum) {
        this.companyGiveNum = companyGiveNum;
    }
	/**
	 * 获取评价
	 * @return
	 */		
	public String getComment() {
        return this.comment;
    }
    /**
	 * 设置评价
	 * @param comment
	 */
    public void setComment(String comment) {
        this.comment = comment;
    }
	/**
	 * 获取最后进货
	 * @return
	 */		
	public Date getLastPurchaseTime() {
        return this.lastPurchaseTime;
    }
    /**
	 * 设置最后进货
	 * @param lastPurchaseTime
	 */
    public void setLastPurchaseTime(Date lastPurchaseTime) {
        this.lastPurchaseTime = lastPurchaseTime;
    }
	/**
	 * 获取确定月数
	 * @return
	 */		
	public Date getTrueMonthTime() {
        return this.trueMonthTime;
    }
    /**
	 * 设置确定月数
	 * @param trueMonthTime
	 */
    public void setTrueMonthTime(Date trueMonthTime) {
        this.trueMonthTime = trueMonthTime;
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
	 * 获取客户表id
	 * @return
	 */		
	public String getDealerId() {
        return this.dealerId;
    }
    /**
	 * 设置客户表id
	 * @param dealerId
	 */
    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getNoticeNum() {
		return noticeNum;
	}

	public void setNoticeNum(Integer noticeNum) {
		this.noticeNum = noticeNum;
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

	public String getIsImportPurchase() {
		return isImportPurchase;
	}

	public void setIsImportPurchase(String isImportPurchase) {
		this.isImportPurchase = isImportPurchase;
	}

	public Date getImportPurchaseTime() {
		return importPurchaseTime;
	}

	public void setImportPurchaseTime(Date importPurchaseTime) {
		this.importPurchaseTime = importPurchaseTime;
	}

	public String getIsGiveUpdate() {
		return isGiveUpdate;
	}

	public void setIsGiveUpdate(String isGiveUpdate) {
		this.isGiveUpdate = isGiveUpdate;
	}
}
