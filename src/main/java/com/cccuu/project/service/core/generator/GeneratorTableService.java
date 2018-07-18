package com.cccuu.project.service.core.generator;

import java.util.Map;

import com.cccuu.project.model.core.generator.GeneratorTable;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface GeneratorTableService extends BaseService<GeneratorTable> {
	/**
	 * 分页查询数据
	 * @param params
	 * @return
	 */
	public PageInfo<GeneratorTable> queryListByPage(Map<String, String> params);
	
	/**
	 * 执行创建表语句
	 * @param id
	 */
	public void executeCreateTable(String id);
	/**
	 * 创建代码
	 * @param params
	 */
	public void createCode(Map<String, String> params);

}
