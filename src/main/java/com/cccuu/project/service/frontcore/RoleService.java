package com.cccuu.project.service.frontcore;

import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface RoleService extends BaseService<Role> {
    /**
     * 分页查询信息
     * @param params
     * @return
     */
    public PageInfo<Role> queryListByPage(Map<String, String> params);

}
