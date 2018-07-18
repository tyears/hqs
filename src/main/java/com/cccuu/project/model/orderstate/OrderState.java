package com.cccuu.project.model.orderstate;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.math.BigDecimal;
import com.cccuu.project.utils.BaseModel;
/**
 * 订单状态排序表
 * @Description 
 * @author zhaixiaoliang
 * @Date 2017年11月15日 17:52:12
 */
@Entity
@Table(name="sys_order_state")
public class OrderState extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "sort")
	private Integer sort;//排序
	
		
    /**
	 * 获取排序
	 * @return
	 */		
	public Integer getSort() {
        return this.sort;
    }
    /**
	 * 设置排序
	 * @param sort
	 */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
