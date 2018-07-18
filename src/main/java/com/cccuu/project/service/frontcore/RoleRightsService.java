package com.cccuu.project.service.frontcore;


import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.RoleRights;
import com.cccuu.project.utils.BaseService;

import java.util.List;

public interface RoleRightsService extends BaseService<RoleRights>{
    /**
     * 获取角色权限节点
     * @param roleId
     * @return
     */
    public List<Rights> getRoleRightsTreeNodes(String roleId);
    /**
     * 保存角色权限
     * @param role
     */
    public void saveRoleRights(Role role);
}
