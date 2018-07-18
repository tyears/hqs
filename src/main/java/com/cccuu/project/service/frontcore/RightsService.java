package com.cccuu.project.service.frontcore;

import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RightsService extends BaseService<Rights> {

    /**
     * 分页查询列表
     *
     * @param params
     * @return
     */
    public PageInfo<Rights> queryListByPage(Map<String, String> params);

    /**
     * 根据父id查询列表
     *
     * @param fkId
     * @return
     */
    public List<Rights> getListByFkId(String fkId);

    /**
     * 保存资源菜单
     *
     * @param rights
     * @return
     */
    public Rights saveInfo(Rights rights);

    /**
     * 删除权限
     *
     * @param id
     * @param fkId 父权限id
     * @return 影响条数
     */
    public int deleteInfoById(String id, String fkId);

    /**
     * 获取角色权限（PC登录获取权限）
     *
     * @param userid
     * @return
     */
    List<Map<String, Object>> getRightsByUserid(String userid);
}
