package com.cccuu.project.mapper.operationrecord;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 操作记录Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月23日 11:02:17
 */
public interface OperationRecordMapper extends BaseMapper<OperationRecord>{

    /**
     * 根据条件查询
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

    /**
     * 批量插入
     * @param list
     * @return
     */
    public int addMulti(List<OperationRecord> list);

    /**
     * 全部变为未导入
     * @return
     */
    public int updateImport();
}