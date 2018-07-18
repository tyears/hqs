package com.cccuu.project.service.giveproduct;

import java.util.Map;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 赠送产品关联Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月12日 17:45:31
 */
public interface GiveProductService extends BaseService<GiveProduct>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<GiveProduct> queryListByPage(Map<String,String> params);

}