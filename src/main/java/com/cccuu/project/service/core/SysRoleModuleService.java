package com.cccuu.project.service.core;

import java.util.List;

import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.core.SysRoleModule;
import com.cccuu.project.utils.BaseService;

public interface SysRoleModuleService extends BaseService<SysRoleModule> {
	/**
	 * 获取角色资源数节点
	 * @param roleId
	 * @return
	 */
	public List<SysModule> getRoleModuleTreeNodes(String roleId);
	/**
	 * 保存角色资源信息
	 * @param sysRole
	 */
	public void saveRoleModule(SysRole sysRole);

}
