package com.cccuu.project.model.DTO;

import com.cccuu.project.utils.BaseModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品DTO
 *
 * @author zhaixiaoliang
 * @Description
 * @Date 2017年09月13日 11:39:39
 */
public class ProductDTO {

    private String id;

    private String product_name;//产品名称

    private String jianpin;//简拼

    private String type_id;//分类id

    private String product_num;//货号

    private String spec;//规格

    private BigDecimal retail_price_bag;//零售价/袋

    private BigDecimal retail_price_box;//零售价/箱

    private BigDecimal sell_price_bag;//经销价/袋

    private BigDecimal sell_price_box;//经销价/箱


    private String remark;//备注


    private Integer sort;//排序


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;//创建时间


    private Integer num_box;//每箱袋数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getJianpin() {
        return jianpin;
    }

    public void setJianpin(String jianpin) {
        this.jianpin = jianpin;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getProduct_num() {
        return product_num;
    }

    public void setProduct_num(String product_num) {
        this.product_num = product_num;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getRetail_price_bag() {
        return retail_price_bag;
    }

    public void setRetail_price_bag(BigDecimal retail_price_bag) {
        this.retail_price_bag = retail_price_bag;
    }

    public BigDecimal getRetail_price_box() {
        return retail_price_box;
    }

    public void setRetail_price_box(BigDecimal retail_price_box) {
        this.retail_price_box = retail_price_box;
    }

    public BigDecimal getSell_price_bag() {
        return sell_price_bag;
    }

    public void setSell_price_bag(BigDecimal sell_price_bag) {
        this.sell_price_bag = sell_price_bag;
    }

    public BigDecimal getSell_price_box() {
        return sell_price_box;
    }

    public void setSell_price_box(BigDecimal sell_price_box) {
        this.sell_price_box = sell_price_box;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getNum_box() {
        return num_box;
    }

    public void setNum_box(Integer num_box) {
        this.num_box = num_box;
    }
}
