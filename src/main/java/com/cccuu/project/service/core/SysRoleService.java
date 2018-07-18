package com.cccuu.project.service.core;

import java.util.Map;

import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface SysRoleService extends BaseService<SysRole>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<SysRole> queryListByPage(Map<String,String> params);
	/**
	 * 删除角色
	 * @param roleId
	 * @return 影响条数
	 */
	public int  deleteInfoById(String roleId);

}
