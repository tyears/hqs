package com.cccuu.project.service.technicalproposal;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.technicalproposal.TechnicalProposal;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 技术建议Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月20日 10:55:15
 */
public interface TechnicalProposalService extends BaseService<TechnicalProposal>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);


	/**
	 * 根据id数组查询列表
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> queryListByIdS(String[] ids);
}