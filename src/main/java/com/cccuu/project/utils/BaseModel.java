package com.cccuu.project.utils;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 所有持久化实体类都继承
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:25:38
 */
@MappedSuperclass
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -6690941339269328820L;
	
	@Id
    @GeneratedValue(generator = "UUID")
   // @GenericGenerator(name = "myGenerator", strategy = "com.cccuu.project.utils.MyGenerator")
    @Column(name = "id", unique = true, nullable = false)
	protected String id;// ID
	@Column(name="reserved1")
	protected String reserved1;// 预留字符字段
	@Column(name="reserved2")
	protected String reserved2;// 预留字符字段
	@Column(name="reserved3")
	protected String reserved3;// 预留字符字段
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	
	
}
