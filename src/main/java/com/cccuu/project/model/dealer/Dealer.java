package com.cccuu.project.model.dealer;

import javax.persistence.*;

import com.cccuu.project.model.dealerproduct.DealerProduct;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cccuu.project.utils.BaseModel;
/**
 * 客户（经销商或大厂部）表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年09月14日 11:50:55
 */
@Entity
@Table(name="t_dealer")
public class Dealer extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	//reserved1预留字段已使用  是否最新导入 0，否  1，是
	
	@Column(name = "dealer_num")
	private String dealerNum;//客户编号

	@Column(name = "dealer_num_int")
	private Integer dealerNumInt;//对应客户编号,数字类型
	
	
	@Column(name = "register_tel")
	private String registerTel;//注册手机号
	
	
	@Column(name = "name")
	private String name;//姓名
	
	
	@Column(name = "administrative_division")
	private String administrativeDivision;
	
	
	@Column(name = "consignee")
	private String consignee;//收货人
	
	
	@Column(name = "postcode")
	private String postcode;//邮编
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cooperation_time")
	private Date cooperationTime;//合作日期
	
	
	@Column(name = "overall_merit")
	private String overallMerit;//总评价
	
	
	@Column(name = "sms_tel")
	private String smsTel;//短信接收号
	
	
	@Column(name = "district_name")
	private String districtName;//单位市场(区域全地址)
	
	
	@Column(name = "district_id")
	private String districtId;//区级id

	@Column(name = "city_id")
	private String cityId;//市级id

	@Column(name = "province_id")
	private String provinceId;//省级id
	
	
	@Column(name = "distribution_area")
	private String distributionArea;//经销区域
	
	
	@Column(name = "company_name")
	private String companyName;//单位名称
	
	
	@Column(name = "delivery_address")
	private String deliveryAddress;//收货地址 
	
	
	@Column(name = "delivery_tel")
	private String deliveryTel;//收货电话
	
	
	@Column(name = "fax")
	private String fax;//传真
	
	
	@Column(name = "cooperation_state")
	private String cooperationState;//合作情况
	
	
	@Column(name = "credit_evaluation")
	private String creditEvaluation;//信用评价
	
	
	@Column(name = "remark")
	private String remark;//备注
	
	
	@Column(name = "postal_address")
	private String postalAddress;//通讯地址
	
	
	@Column(name = "other_tel")
	private String otherTel;//其他号码
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;//创建时间
	
	
	@Column(name = "dealer_type")
	private String dealerType;//客户类型 1:经销商 2:面粉厂 3:食品厂

	@Column(name = "ishezuo")
	private String ishezuo;// y/n

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "import_time")
	private Date importTime;//总评价导入时间

	@Column(name = "uuid")
	private String uuid;//客户uuid

	@Column(name = "sms_address")
	private String smsAddress;//短信地址

	@Transient
	private List<DealerProduct> dpList;//客户下的产品

	public Integer getDealerNumInt() {
		return dealerNumInt;
	}

	public void setDealerNumInt(Integer dealerNumInt) {
		this.dealerNumInt = dealerNumInt;
	}

	/**
	 * 获取客户编号
	 * @return
	 */		
	public String getDealerNum() {
        return this.dealerNum;
    }
    /**
	 * 设置客户编号
	 * @param dealerNum
	 */
    public void setDealerNum(String dealerNum) {
        this.dealerNum = dealerNum;
    }
	/**
	 * 获取注册手机号
	 * @return
	 */		
	public String getRegisterTel() {
        return this.registerTel;
    }
    /**
	 * 设置注册手机号
	 * @param registerTel
	 */
    public void setRegisterTel(String registerTel) {
        this.registerTel = registerTel;
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
	 * 获取行政区划
	 * @return
	 */		
	public String getAdministrativeDivision() {
        return this.administrativeDivision;
    }
    /**
	 * 设置行政区划
	 * @param administrativeDivision
	 */
    public void setAdministrativeDivision(String administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }

	public String getIshezuo() {
		return ishezuo;
	}

	public void setIshezuo(String ishezuo) {
		this.ishezuo = ishezuo;
	}

	/**

	 * 获取收货人
	 * @return
	 */		
	public String getConsignee() {
        return this.consignee;
    }



	/**
	 * 设置收货人

	 * @param consignee
	 */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }
	/**
	 * 获取邮编
	 * @return
	 */		
	public String getPostcode() {
        return this.postcode;
    }
    /**
	 * 设置邮编
	 * @param postcode
	 */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
	/**
	 * 获取合作日期
	 * @return
	 */		
	public Date getCooperationTime() {
        return this.cooperationTime;
    }
    /**
	 * 设置合作日期
	 * @param cooperationTime
	 */
    public void setCooperationTime(Date cooperationTime) {
        this.cooperationTime = cooperationTime;
    }
	/**
	 * 获取总评价
	 * @return
	 */		
	public String getOverallMerit() {
        return this.overallMerit;
    }
    /**
	 * 设置总评价
	 * @param overallMerit
	 */
    public void setOverallMerit(String overallMerit) {
        this.overallMerit = overallMerit;
    }
	/**
	 * 获取短信接收号
	 * @return
	 */		
	public String getSmsTel() {
        return this.smsTel;
    }
    /**
	 * 设置短信接收号
	 * @param smsTel
	 */
    public void setSmsTel(String smsTel) {
        this.smsTel = smsTel;
    }
	/**
	 * 获取单位市场(区级名称)
	 * @return
	 */		
	public String getDistrictName() {
        return this.districtName;
    }
    /**
	 * 设置单位市场(区级名称)
	 * @param districtName
	 */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
	/**
	 * 获取区级id
	 * @return
	 */		
	public String getDistrictId() {
        return this.districtId;
    }
    /**
	 * 设置区级id
	 * @param districtId
	 */
    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }
	/**
	 * 获取经销区域
	 * @return
	 */		
	public String getDistributionArea() {
        return this.distributionArea;
    }
    /**
	 * 设置经销区域
	 * @param distributionArea
	 */
    public void setDistributionArea(String distributionArea) {
        this.distributionArea = distributionArea;
    }
	/**
	 * 获取单位名称
	 * @return
	 */		
	public String getCompanyName() {
        return this.companyName;
    }
    /**
	 * 设置单位名称
	 * @param companyName
	 */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
	/**
	 * 获取收货地址 
	 * @return
	 */		
	public String getDeliveryAddress() {
        return this.deliveryAddress;
    }
    /**
	 * 设置收货地址 
	 * @param deliveryAddress
	 */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
	/**
	 * 获取收货电话
	 * @return
	 */		
	public String getDeliveryTel() {
        return this.deliveryTel;
    }
    /**
	 * 设置收货电话
	 * @param deliveryTel
	 */
    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }
	/**
	 * 获取传真
	 * @return
	 */		
	public String getFax() {
        return this.fax;
    }
    /**
	 * 设置传真
	 * @param fax
	 */
    public void setFax(String fax) {
        this.fax = fax;
    }
	/**
	 * 获取合作情况
	 * @return
	 */		
	public String getCooperationState() {
        return this.cooperationState;
    }
    /**
	 * 设置合作情况
	 * @param cooperationState
	 */
    public void setCooperationState(String cooperationState) {
        this.cooperationState = cooperationState;
    }
	/**
	 * 获取信用评价
	 * @return
	 */		
	public String getCreditEvaluation() {
        return this.creditEvaluation;
    }
    /**
	 * 设置信用评价
	 * @param creditEvaluation
	 */
    public void setCreditEvaluation(String creditEvaluation) {
        this.creditEvaluation = creditEvaluation;
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
	 * 获取通讯地址
	 * @return
	 */		
	public String getPostalAddress() {
        return this.postalAddress;
    }
    /**
	 * 设置通讯地址
	 * @param postalAddress
	 */
    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }
	/**
	 * 获取其他号码
	 * @return
	 */		
	public String getOtherTel() {
        return this.otherTel;
    }
    /**
	 * 设置其他号码
	 * @param otherTel
	 */
    public void setOtherTel(String otherTel) {
        this.otherTel = otherTel;
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
	/**
	 * 获取客户类型 1:经销商 2:面粉厂 3:食品厂
	 * @return
	 */		
	public String getDealerType() {
        return this.dealerType;
    }
    /**
	 * 设置客户类型 1:经销商 2:面粉厂 3:食品厂
	 * @param dealerType
	 */
    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public List<DealerProduct> getDpList() {
		return dpList;
	}

	public void setDpList(List<DealerProduct> dpList) {
		this.dpList = dpList;
	}

	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}
}
