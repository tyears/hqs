package com.cccuu.project.service.frontcore;

import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.UserRole;
import com.cccuu.project.utils.BaseService;

import java.util.List;

public interface UserRoleService extends BaseService<UserRole> {
    /**
     * 根据用户id获取角色树节点（带着是否选中）
     * @param userId 用户id
     * @return
     */
    public List<Role> getUserRoleInfo(String userId);
    /**
     * 保存用户角色
     * @param roleIdArry
     * @param userId
     */
    public void saveUserRole(String[] roleIdArry,String userId);
}
