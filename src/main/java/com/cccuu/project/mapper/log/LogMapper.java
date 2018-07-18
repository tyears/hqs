package com.cccuu.project.mapper.log;

import java.util.List;
import java.util.Map;

import com.cccuu.project.model.log.Log;
import com.cccuu.project.utils.BaseMapper;

public interface LogMapper extends BaseMapper<Log> {
	
	public List<Map<String,Object>> queryListByParams(Map<String,Object> params);

	public List<Map<String,Object>> queryListByParams2(Map<String,Object> params);
}
