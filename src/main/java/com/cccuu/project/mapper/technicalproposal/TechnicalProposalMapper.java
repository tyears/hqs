package com.cccuu.project.mapper.technicalproposal;

import com.cccuu.project.model.technicalproposal.TechnicalProposal;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 技术建议Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月20日 10:55:15
 */
public interface TechnicalProposalMapper extends BaseMapper<TechnicalProposal>{
    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 根据参数查询列表
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByIdS(Map<String,Object> params);
}