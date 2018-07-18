package com.cccuu.project.model.order;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 订单表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月18日 15:19:31
 */
@Entity
@Table(name="t_order")
public class Order extends BaseModel{

	private static final long serialVersionUID = 1L;

	@Column(name = "order_num")
	private String orderNum;//订单编号

	@Column(name = "order_num_int")
	private Integer orderNumInt;//订单编号

	@Column(name = "dealer_type")
	private String dealerType;//客户类型  0，普通客户1:经销商 2:面粉厂 3:食品厂，4：其他


	@Column(name = "message_type")
	private String messageType;//留言类别


	@Column(name = "tel")
	private String tel;//手机号码


	@Column(name = "name")
	private String name;//姓名


	@Column(name = "number_attribution")
	private String numberAttribution;//号码归属地


	@Column(name = "dealer_message")
	private String dealerMessage;//客户留言


	@Column(name = "address")
	private String address;//地址


	@Column(name = "dealer_id")
	private String dealerId;//经销商id


	@Column(name = "user_name")
	private String userName;//导入员


	@Column(name = "user_id")
	private String userId;//导入员id


	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time")
	private Date importTime;//导入时间


	@Column(name = "state")
	private String state;//订单状态 1:市场部待处理 2:市场部经理待处理 3:大厂部待处理 4:技术部待处理 5:货运部待处理
						// 6:财务部待处理 7:完成 8：无效询单 9：恶意试用 10：无人接听 11：订货部待处理 12：调货部待处理
						// 13:办公室待处理 14：其他1待处理 15：其他2待处理 16:网络部待处理 17:信息部待处理

	@Column(name = "user_message")
	private String userMessage;//转单留言


	@Column(name = "print_state")
	private String printState;//打印状态 0:未打印 1:已打印

	@Column(name = "import_type")
	private String importType;//导入类型 0:网络信息导入 1:来电导入

	@Column(name = "no_heard_num")
	private Integer noHeardNum;//无人接听次数 第一次 延时4小时  第二次 延时4小时 第三次 延时4小时

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "transfer_time")
	private Date transferTime;//转单时间

	@Column(name = "obtain_user_id")
	private String obtainUserId;//调单人id

	@Column(name = "area_name")
	private String areaName;//单位市场

	@Column(name = "area_id")
	private String areaId;//单位市场id

	@Column(name = "sms_tel")
	private String smsTel;//短信号码

	@Column(name = "product_names")
	private String productNames;//咨询产品

	@Column(name = "dealer_num")
	private String dealerNum;//客户编号

	@Column(name = "transfer_man_name")
	private String transferManName;//转单人姓名

	@Column(name = "transfer_man_id")
	private String transferManId;//转单人id

	@Column(name = "ex_product_names")
	private String exProductNames;//导入咨询产品

	@Column(name = "is_import")
	private String isImport;//是否留言导入 0，否，1,是

	@Column(name = "is_transfer")
	private String isTransfer;//是否转单 0，否，1,外部转单 -1 内部转单

	@Column(name = "consult_dealer_id")
	private String consultDealerId;//咨询告知关联经销商id

	@Column(name = "is_match")
	private String isMatch;//询单是否匹配经销商或询单 y/n

	@Transient
	private String productIds;//咨询产品id拼接字符串

	@Transient
	private String operationRecord;//操作记录

	@Transient
	private Integer allNum;//总单数

	@Transient
	private Integer dclNum;//待处理单数

	@Transient
	private Long telNum;//询单根据手机号匹配经销商数量

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderNumInt() {
		return orderNumInt;
	}

	public void setOrderNumInt(Integer orderNumInt) {
		this.orderNumInt = orderNumInt;
	}

	/**
	 * 获取客户类型
	 * @return
	 */
	public String getDealerType() {
        return this.dealerType;
    }
    /**
	 * 设置客户类型
	 * @param dealerType
	 */
    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }
	/**
	 * 获取留言类别
	 * @return
	 */
	public String getMessageType() {
        return this.messageType;
    }
    /**
	 * 设置留言类别
	 * @param messageType
	 */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
	/**
	 * 获取手机号码
	 * @return
	 */
	public String getTel() {
        return this.tel;
    }
    /**
	 * 设置手机号码
	 * @param tel
	 */
    public void setTel(String tel) {
        this.tel = tel;
    }
	/**
	 * 获取姓名
	 * @return
	 */
	public String getName() {
        return this.name;
    }
    /**
	 * 设置姓名
	 * @param name
	 */
    public void setName(String name) {
        this.name = name;
    }
	/**
	 * 获取号码归属地
	 * @return
	 */
	public String getNumberAttribution() {
        return this.numberAttribution;
    }
    /**
	 * 设置号码归属地
	 * @param numberAttribution
	 */
    public void setNumberAttribution(String numberAttribution) {
        this.numberAttribution = numberAttribution;
    }
	/**
	 * 获取客户留言
	 * @return
	 */
	public String getDealerMessage() {
        return this.dealerMessage;
    }
    /**
	 * 设置客户留言
	 * @param dealerMessage
	 */
    public void setDealerMessage(String dealerMessage) {
        this.dealerMessage = dealerMessage;
    }
	/**
	 * 获取地址
	 * @return
	 */
	public String getAddress() {
        return this.address;
    }
    /**
	 * 设置地址
	 * @param address
	 */
    public void setAddress(String address) {
        this.address = address;
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
	/**
	 * 获取操作员
	 * @return
	 */
	public String getUserName() {
        return this.userName;
    }
    /**
	 * 设置导入员
	 * @param userName
	 */
    public void setUserName(String userName) {
        this.userName = userName;
    }
	/**
	 * 获取导入员id
	 * @return
	 */
	public String getUserId() {
        return this.userId;
    }
    /**
	 * 设置操作员id
	 * @param userId
	 */
    public void setUserId(String userId) {
        this.userId = userId;
    }
	/**
	 * 获取导入时间
	 * @return
	 */
	public Date getImportTime() {
        return this.importTime;
    }
    /**
	 * 设置导入时间
	 * @param importTime
	 */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }
	/**
	 * 获取订单状态
	 * @return
	 */
	public String getState() {
        return this.state;
    }
    /**
	 * 设置订单状态
	 * @param state
	 */
    public void setState(String state) {
        this.state = state;
    }
	/**
	 * 获取转单留言
	 * @return
	 */
	public String getUserMessage() {
        return this.userMessage;
    }
    /**
	 * 设置转单留言
	 * @param userMessage
	 */
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }
	/**
	 * 获取打印状态 0:未打印 1:已打印
	 * @return
	 */
	public String getPrintState() {
        return this.printState;
    }
    /**
	 * 设置打印状态 0:未打印 1:已打印
	 * @param printState
	 */
    public void setPrintState(String printState) {
        this.printState = printState;
    }

	public String getImportType() {
		return importType;
	}

	public void setImportType(String importType) {
		this.importType = importType;
	}

	public Integer getNoHeardNum() {
		return noHeardNum;
	}

	public void setNoHeardNum(Integer noHeardNum) {
		this.noHeardNum = noHeardNum;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public String getObtainUserId() {
		return obtainUserId;
	}

	public void setObtainUserId(String obtainUserId) {
		this.obtainUserId = obtainUserId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getSmsTel() {
		return smsTel;
	}

	public void setSmsTel(String smsTel) {
		this.smsTel = smsTel;
	}

	public String getProductNames() {
		return productNames;
	}

	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public String getOperationRecord() {
		return operationRecord;
	}

	public void setOperationRecord(String operationRecord) {
		this.operationRecord = operationRecord;
	}

	public String getDealerNum() {
		return dealerNum;
	}

	public void setDealerNum(String dealerNum) {
		this.dealerNum = dealerNum;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getTransferManName() {
		return transferManName;
	}

	public void setTransferManName(String transferManName) {
		this.transferManName = transferManName;
	}

	public String getTransferManId() {
		return transferManId;
	}

	public void setTransferManId(String transferManId) {
		this.transferManId = transferManId;
	}

	public String getExProductNames() {
		return exProductNames;
	}

	public void setExProductNames(String exProductNames) {
		this.exProductNames = exProductNames;
	}

	public String getIsImport() {
		return isImport;
	}

	public void setIsImport(String isImport) {
		this.isImport = isImport;
	}

	public String getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(String isTransfer) {
		this.isTransfer = isTransfer;
	}

	public String getConsultDealerId() {
		return consultDealerId;
	}

	public void setConsultDealerId(String consultDealerId) {
		this.consultDealerId = consultDealerId;
	}

	public Long getTelNum() {
		return telNum;
	}

	public void setTelNum(Long telNum) {
		this.telNum = telNum;
	}

	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}
}
