package com.cccuu.project.model.${packageName};

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
 * ${tableHyName}
 * @Description 
 * @author zhaixiaoliang
 * @Date ${date?string("yyyy年MM月dd日 HH:mm:ss")}
 */
@Entity
@Table(name="${tableName}")
public class ${className} extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	<#if tableColumns?exists>
	<#list tableColumns as column>
	
	<#if column.type=='4'>
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	</#if>
	@Column(name = "${column.name}")
	<#if column.type=='1'>
	private String ${column.filedName};//${column.remark}
	<#elseif column.type=='2'>
	private Integer ${column.filedName};//${column.remark}
	<#elseif column.type=='3'>
	private Double ${column.filedName};//${column.remark}
	<#elseif column.type=='4'>
	private Date ${column.filedName};//${column.remark}
	<#else>
	private BigDecimal ${column.filedName};//${column.remark}
	</#if>
	
	</#list>
		
	<#list tableColumns as column>
	<#if column.type=='1'>
	/**
	 * 获取${column.remark}
	 * @return
	 */		
	public String get${column.filedName?cap_first}() {
        return this.${column.filedName};
    }
    /**
	 * 设置${column.remark}
	 * @param ${column.filedName}
	 */
    public void set${column.filedName?cap_first}(String ${column.filedName}) {
        this.${column.filedName} = ${column.filedName};
    }
    <#elseif column.type=='2'>
    /**
	 * 获取${column.remark}
	 * @return
	 */		
	public Integer get${column.filedName?cap_first}() {
        return this.${column.filedName};
    }
    /**
	 * 设置${column.remark}
	 * @param ${column.filedName}
	 */
    public void set${column.filedName?cap_first}(Integer ${column.filedName}) {
        this.${column.filedName} = ${column.filedName};
    }
	<#elseif column.type=='3'>
	/**
	 * 获取${column.remark}
	 * @return
	 */		
	public Double get${column.filedName?cap_first}() {
        return this.${column.filedName};
    }
    /**
	 * 设置${column.remark}
	 * @param ${column.filedName}
	 */
    public void set${column.filedName?cap_first}(Double ${column.filedName}) {
        this.${column.filedName} = ${column.filedName};
    }
	<#elseif column.type=='4'>
	/**
	 * 获取${column.remark}
	 * @return
	 */		
	public Date get${column.filedName?cap_first}() {
        return this.${column.filedName};
    }
    /**
	 * 设置${column.remark}
	 * @param ${column.filedName}
	 */
    public void set${column.filedName?cap_first}(Date ${column.filedName}) {
        this.${column.filedName} = ${column.filedName};
    }
	<#else>
	/**
	 * 获取${column.remark}
	 * @return
	 */	
	public BigDecimal get${column.filedName?cap_first}() {
        return this.${column.filedName};
    }
    /**
	 * 设置${column.remark}
	 * @param ${column.filedName}
	 */
    public void set${column.filedName?cap_first}(BigDecimal ${column.filedName}) {
        this.${column.filedName} = ${column.filedName};
    }
    </#if>
	</#list>
	</#if>
}
