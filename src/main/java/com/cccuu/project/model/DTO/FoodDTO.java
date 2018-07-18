package com.cccuu.project.model.DTO;

import com.cccuu.project.model.product.Product;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 食品DTO
 * @Description
 * @author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
public class FoodDTO {

	private String id;

	private String food_name;//食品名称
	
	private String type_id;//分类id

	private String jianpin;//简拼

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date create_time;//创建时间

	private List<ProductDTO> products;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFood_name() {
		return food_name;
	}

	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getJianpin() {
		return jianpin;
	}

	public void setJianpin(String jianpin) {
		this.jianpin = jianpin;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
}
