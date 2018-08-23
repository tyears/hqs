package com.cccuu.project.service.dealer;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 客户（经销商或大厂部）Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月14日 11:50:55
 */
public interface DealerService extends BaseService<Dealer>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Dealer> queryListByPage(Map<String,String> params);

	/**
	 * 分页查询列表（联合查询）
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryDealerListByPage(Map<String,String> params);

	/**
	 * 分页查询列表（联合查询）
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryDealerList(Map<String,String> params);


	/**
	 * 根据手机号查询经销商
	 * @param params
	 * @return
	 */
	public Dealer queryBytel(String tel);

	/**
	 *
	 */
	public Integer countDealer(Map<String,String> params);

	/**
	 * 修改总评价或合作情况	0:总评价 1:合作评价
	 * @param params
	 */
	public void updatePingText(Map<String,String> params);

	/**
	 *获取当前最大编号
	 */
	public Integer codeNum();

	/**
	 * 检查注册手机号和其他号码是否存在
	 * @param array
	 * @return
	 */
	public List<Map> isHasPhone(String [] array,String id);

	/**
	 * 分页查询经销商导入信息列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListImportByPage(Map<String,String> params);

	/**
	 * 更新经销商导入状态
	 */
	public void updateImport(String importType);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int addMulti(List<Dealer> list);

	/**
	 * 经销商和经销商下的产品
	 * @param params
	 * @return
	 */
	public List<Dealer> queryListByMapDouble(Map<String,String> params);
	public void updateDealerMerit();
	public List<Map<String,Object>> historyGiveByDealerId(String dealerID);
}