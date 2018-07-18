package com.cccuu.project.service.operationrecord;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 操作记录Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月23日 11:02:17
 */
public interface OperationRecordService extends BaseService<OperationRecord>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int addMulti(List<OperationRecord> list);

}