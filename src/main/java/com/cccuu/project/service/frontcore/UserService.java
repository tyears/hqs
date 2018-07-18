package com.cccuu.project.service.frontcore;

import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface UserService extends BaseService<User> {
    /**
     * 分页查询信息
     *
     * @param params
     * @return
     */
    public PageInfo<Map<String, Object>> queryListByPage(Map<String, String> params);

    /**
     * 禁用启用用户
     *
     * @param userId
     */
    public void changeStateById(String userId);

    /**
     * 保存用户
     *
     * @param user
     * @param xdDeparts
     * @param dyDeparts
     * @return
     */
    public User saveOrUpdateUserInfo(User user, String[] xdDeparts, String[] dyDeparts);

    /**
     * 删除用户信息
     *
     * @param userId
     */
    public void deleteInfoById(String userId);

    /**
     * PC登录
     *
     * @param userName
     * @param passWord
     * @return
     */
    User login(String userName, String passWord);

    /**
     * 检查分机号是否重复
     * @param params
     * @return
     */
    public int checkExtensionNum(Map<String,String> params);
}
