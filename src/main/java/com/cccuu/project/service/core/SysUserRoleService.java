package com.cccuu.project.service.core;

import java.util.List;

import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.core.SysUserRole;
import com.cccuu.project.utils.BaseService;

public interface SysUserRoleService extends BaseService<SysUserRole>{
	
	/**
	 * 根据用户id获取角色树节点（带着是否选中）
	 * @param userId 用户id
	 * @return
	 */
	public List<SysRole> getUserRoleInfo(String userId);
	/**
	 * 保存用户角色
	 * @param roleIdArry
	 * @param userId
	 */
	public void saveUserRole(String[] roleIdArry,String userId);
}
