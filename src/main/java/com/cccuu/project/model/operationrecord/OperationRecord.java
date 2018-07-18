package com.cccuu.project.model.operationrecord;

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
 * 操作记录表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月23日 11:02:17
 */
@Entity
@Table(name="t_operation_record")
public class OperationRecord extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "user_id")
	private String userId;//操作人id
	
	
	@Column(name = "user_name")
	private String userName;//操作人姓名
	
	
	@Column(name = "content")
	private String content;//操作内容
	
	
	@Column(name = "order_id")
	private String orderId;//订单id
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间

	@Column(name = "order_tel")
	private String orderTel;//电话

	@Column(name = "is_import")
	private String isImport;//是否最新导入 0，否，1,是

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "is_import_time")
	private Date isImportTime;//导入时间
	
		
	/**
	 * 获取操作人id
	 * @return
	 */		
	public String getUserId() {
        return this.userId;
    }
    /**
	 * 设置操作人id
	 * @param userId
	 */
    public void setUserId(String userId) {
        this.userId = userId;
    }
	/**
	 * 获取操作人姓名
	 * @return
	 */		
	public String getUserName() {
        return this.userName;
    }
    /**
	 * 设置操作人姓名
	 * @param userName
	 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
	/**
	 * 获取操作内容
	 * @return
	 */		
	public String getContent() {
        return this.content;
    }
    /**
	 * 设置操作内容
	 * @param content
	 */
    public void setContent(String content) {
        this.content = content;
    }
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

	public String getOrderTel() {
		return orderTel;
	}

	public void setOrderTel(String orderTel) {
		this.orderTel = orderTel;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}

	public Date getIsImportTime() {
		return isImportTime;
	}

	public void setIsImportTime(Date isImportTime) {
		this.isImportTime = isImportTime;
	}
}
