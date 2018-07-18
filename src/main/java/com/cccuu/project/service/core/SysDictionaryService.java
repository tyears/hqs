package com.cccuu.project.service.core;

import java.util.Map;

import com.cccuu.project.model.core.SysDictionary;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface SysDictionaryService extends BaseService<SysDictionary>{
	/**
	 * 根据参数分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<SysDictionary> queryListByPage(Map<String,String> params);
	
	/**
	 * 根据id删除数据
	 * @param idArry
	 * @return
	 */
	public int deleteInfoById(String id);
}
