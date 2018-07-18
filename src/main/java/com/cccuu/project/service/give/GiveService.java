package com.cccuu.project.service.give;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.give.Give;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 赠送Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月12日 17:42:30
 */
public interface GiveService extends BaseService<Give>{
	/**
	 * 判断今日是否赠送给经销商
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryIsGiveDealer(Map<String,String> params);
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);

	/**
	 * 不分页查询列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryListGive(Map<String,String> params);

	/**
	 * 根据参数查询赠送
	 * @param params
	 * @return
	 */
	public Map<String,Object> queryGive(Map<String,String> params);

	/**
	 * 保存赠送记录
	 * @param give
	 * @return
	 */
	public Give saveGive(Give give, HttpServletRequest request);

	/**
	 * 保存H0000赠送记录
	 * @param give
	 * @return
	 */
	public Give saveHGive(Give give, HttpServletRequest request);

	/**
	 * 快递页面修改产品
	 * @param give
	 * @return
	 */
	public Give updateGiveProduct(Give give, HttpServletRequest request);

	/**
	 * 修改审核状态
	 * @param give
	 * @return
	 */
	public void updateGiveState(Give give);

	/**
	 * 修改备注
	 * @param map
	 */
	public void updateBeizhu(Map<String ,String > map);

	/**
	 * 根据赠送表id查赠送记录
	 * @param map
	 * @return
	 */
	public List<Map<String,Object>> queryProductByGiveId(String giveId);

	/**
	 * 查询订单是否已经公司赠送或经销商赠送
	 * @param params
	 * @return
	 */
	public Integer queryIsGive(String orderId);

	/**
	 * 查询唯一编号最大值
	 * @return
	 */
	public Integer onlyNumMax();

	/**
	 * 根据参数查询列表
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> queryListByIdS(String[] ids);

	/**
	 * 综合导出
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> queryListByIdSZH(String[] ids);

	/**
	 * 根据ids批量删除
	 * @param params
	 * @return
	 */
	public void deleteByIds(String[] ids);

	/**
	 * 综合查询
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> zhQuery(Map<String,String> params);

	/**
	 * 综合查询下载
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> zhQueryExcel(Map<String,String> params);

	/**
	 * 快递页面修改信息
	 * @param map
	 */
	public void updateInfo(Map<String ,String > map);
}