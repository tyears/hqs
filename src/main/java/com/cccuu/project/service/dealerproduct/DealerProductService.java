package com.cccuu.project.service.dealerproduct;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 客户产品关联Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:44:27
 */
public interface DealerProductService extends BaseService<DealerProduct>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);

	/**
	 * 查询全部列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryProductByDealerId(Map<String,String> params);

	/**
	 * 修改备注
	 * @param params
	 */
	public void updateRemark(Map<String,String> params);

	/**
	 * 修改评价
	 * @param params
	 */
	public void updateComment(Map<String,String > params);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int addMulti(List<DealerProduct> list);

	/**
	 * 查询单个经销商产品关联
	 * @param uuid
	 * @param productNum
	 * @return
	 */
	public List<Map<String, Object>> queryOne(String uuid,String productNum);
	/**
	 * 根据经销商id和产品id判断是否有数据，有时更新，无是新增
	 * @param dealerProduct
	 * @param type 新增类型  1:赠送给经销商  2:公司赠送  3:经销商赠送
	 *                pj:经销商品种月均评价导入 jh:最新进货日期导入 ht:后台新增
	 */
	public  boolean addDP(DealerProduct dealerProduct,String type);

}