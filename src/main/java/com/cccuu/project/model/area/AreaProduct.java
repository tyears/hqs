package com.cccuu.project.model.area;

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
 * 单位市场产品表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月15日 10:25:37
 */
@Entity
@Table(name="t_area_product")
public class AreaProduct extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "area_id")
	private String areaId;//单位市场id
	
	
	@Column(name = "product_id")
	private String productId;//产品id
	
	
	@Column(name = "author_dealer")
	private String authorDealer;//授权经销商编号
	
	
	@Column(name = "author_dealer_id")
	private String authorDealerId;//授权经销商id
	
	
	@Column(name = "comment")
	private String comment;//市场评价
	
	
	@Column(name = "notice_dealer")
	private String noticeDealer;//公司通知经销商编号
	
	
	@Column(name = "notice_dealer_id")
	private String noticeDealerId;//公司通知经销商id
	
	
	@Column(name = "give_dealer")
	private String giveDealer;//经销商赠送编号
	
	
	@Column(name = "give_dealer_id")
	private String giveDealerId;//经销商赠送id
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "effect_time")
	private Date effectTime;//有效时间

	@Column(name = "is_import")
	private String isImport;//评价是否导入

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time")
	private Date importTime;//评价导入时间

	@Column(name = "author_is_import")
	private String authorIsImport;//授权经销商是否导入

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "author_import_time")
	private Date authorImportTime;//授权经销商导入时间


	
		
	/**
	 * 获取单位市场id
	 * @return
	 */		
	public String getAreaId() {
        return this.areaId;
    }
    /**
	 * 设置单位市场id
	 * @param areaId
	 */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
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
	 * 获取授权经销商
	 * @return
	 */		
	public String getAuthorDealer() {
        return this.authorDealer;
    }
    /**
	 * 设置授权经销商
	 * @param authorDealer
	 */
    public void setAuthorDealer(String authorDealer) {
        this.authorDealer = authorDealer;
    }
	/**
	 * 获取授权经销商id
	 * @return
	 */		
	public String getAuthorDealerId() {
        return this.authorDealerId;
    }
    /**
	 * 设置授权经销商id
	 * @param authorDealerId
	 */
    public void setAuthorDealerId(String authorDealerId) {
        this.authorDealerId = authorDealerId;
    }
	/**
	 * 获取市场评价
	 * @return
	 */		
	public String getComment() {
        return this.comment;
    }
    /**
	 * 设置市场评价
	 * @param comment
	 */
    public void setComment(String comment) {
        this.comment = comment;
    }
	/**
	 * 获取公司通知经销商
	 * @return
	 */		
	public String getNoticeDealer() {
        return this.noticeDealer;
    }
    /**
	 * 设置公司通知经销商
	 * @param noticeDealer
	 */
    public void setNoticeDealer(String noticeDealer) {
        this.noticeDealer = noticeDealer;
    }
	/**
	 * 获取公司通知经销商id
	 * @return
	 */		
	public String getNoticeDealerId() {
        return this.noticeDealerId;
    }
    /**
	 * 设置公司通知经销商id
	 * @param noticeDealerId
	 */
    public void setNoticeDealerId(String noticeDealerId) {
        this.noticeDealerId = noticeDealerId;
    }
	/**
	 * 获取经销商赠送
	 * @return
	 */		
	public String getGiveDealer() {
        return this.giveDealer;
    }
    /**
	 * 设置经销商赠送
	 * @param giveDealer
	 */
    public void setGiveDealer(String giveDealer) {
        this.giveDealer = giveDealer;
    }
	/**
	 * 获取经销商赠送id
	 * @return
	 */		
	public String getGiveDealerId() {
        return this.giveDealerId;
    }
    /**
	 * 设置经销商赠送id
	 * @param giveDealerId
	 */
    public void setGiveDealerId(String giveDealerId) {
        this.giveDealerId = giveDealerId;
    }
	/**
	 * 获取有效时间
	 * @return
	 */		
	public Date getEffectTime() {
        return this.effectTime;
    }
    /**
	 * 设置有效时间
	 * @param effectTime
	 */
    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
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

	public String getAuthorIsImport() {
		return authorIsImport;
	}

	public void setAuthorIsImport(String authorIsImport) {
		this.authorIsImport = authorIsImport;
	}

	public Date getAuthorImportTime() {
		return authorImportTime;
	}

	public void setAuthorImportTime(Date authorImportTime) {
		this.authorImportTime = authorImportTime;
	}
}
