package com.cccuu.project.service.technicalproposal.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.technicalproposal.TechnicalProposal;
import com.cccuu.project.mapper.technicalproposal.TechnicalProposalMapper;
import com.cccuu.project.service.technicalproposal.TechnicalProposalService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 技术建议ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月20日 10:55:15
 */
@Service("technicalProposalService")
public class TechnicalProposalServiceImpl extends BaseServiceImpl<TechnicalProposal> implements TechnicalProposalService{
	
	private TechnicalProposalMapper technicalProposalMapper;
	
	@Autowired
	public void setTechnicalProposalMapper(TechnicalProposalMapper technicalProposalMapper){
		this.technicalProposalMapper = technicalProposalMapper;
		baseMapper = technicalProposalMapper;
	}
	@Override
	@Transactional
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params){
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Map<String,Object>> results = technicalProposalMapper.queryListByParams(parameterMap);
		
		PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryListByIdS(String[] ids) {
		Map<String,Object> map = new HashMap<>();
		map.put("ids",ids);
		return technicalProposalMapper.queryListByIdS(map);
	}
}