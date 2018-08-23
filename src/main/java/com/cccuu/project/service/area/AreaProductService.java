package com.cccuu.project.service.area;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 单位市场产品Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:25:37
 */
public interface AreaProductService extends BaseService<AreaProduct>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);

	/**
	 * 不分页查询单位市场下的产品列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryListInArea(Map<String,String> params);

	/**
	 * 修改公司通知或经销商赠送
	 * @param params
	 */
	public void updateJssOrGs(Map<String,String> params);

	/**
	 * 修改单位市场评价
	 * @param params
	 */
	public void updateComment(Map<String,String > params);

	/**
	 * 修改单位市场下的产品评价
	 * @param params
	 */
	public void updateAreaProductComment(Map<String,String > params);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int addMulti(List<AreaProduct> list);

	/**
	 * 查询单个单位市场产品关联
	 * @param areaNme
	 * @param productNum
	 * @return
	 */
	public List<Map<String,Object>> queryOne(String areaNme,String productNum);

	/**
	 * 根据经销商uuid和产品id查询单位市场产品关联表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryApByUuid(Map<String,String> params);

	/**
	 * 修改产品授权
	 * @param params
	 */
	public void updateAuthor(Map<String,String> params);

	/**
	 * 根据区域id和产品id判断是否有数据，有时更新，无是新增
	 * @param areaProduct
	 * @param type 新增类型 gs:公司通知 jss:经销商赠送 gsh:H0000的公司通知 tm:修改有效时间
	 *             pj:市场品种月均评价导入 sq:授权导入 ht:后台新增
	 */
	public  boolean addAP(AreaProduct areaProduct,String type);
	public void emptyAuthorDealer();
	public void emptyAreaProductMerit();
}