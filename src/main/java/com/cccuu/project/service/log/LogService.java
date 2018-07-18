package com.cccuu.project.service.log;

import java.util.Map;

import com.cccuu.project.model.log.Log;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface LogService extends BaseService<Log> {
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String,Object>> queryListByPage(Map<String,String> params);
}
