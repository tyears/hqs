package com.cccuu.project.service.core;

import java.util.List;
import java.util.Map;

import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface SysModuleService extends BaseService<SysModule>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<SysModule> queryListByPage(Map<String,String> params);
	/**
	 * 根据父id查询列表
	 * @param fkId
	 * @return
	 */
	public List<SysModule> getListByFkId(String fkId);
	/**
	 * 保存资源菜单
	 * @param sysModule
	 * @return
	 */
	public SysModule saveInfo(SysModule sysModule);
	/**
	 * 删除权限
	 * @param id
	 * @param fkId 父权限id
	 * @return 影响条数
	 */
	public int deleteInfoById(String id, String fkId);
}
