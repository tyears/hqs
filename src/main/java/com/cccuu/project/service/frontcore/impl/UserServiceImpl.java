package com.cccuu.project.service.frontcore.impl;

import com.cccuu.project.mapper.depart.UserDepartMapper;
import com.cccuu.project.mapper.frontcore.UserMapper;
import com.cccuu.project.mapper.frontcore.UserRoleMapper;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.frontcore.UserRole;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.PassWordEncrypt;
import com.cccuu.project.utils.UniqueIDGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
        baseMapper = userMapper;
    }

    @Autowired
    private UserDepartMapper userDepartMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    @Transactional
    public PageInfo<Map<String, Object>> queryListByPage(Map<String, String> params) {
        // TODO Auto-generated method stub
        //设置查询条件
        //设置查询条件
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        //这里处理参数
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                parameterMap.put(entry.getKey(), entry.getValue());
            }
        }

        //设置分页参数
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
        int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

        //设置分页信息
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> userList = userMapper.queryListByParams(parameterMap);

        PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(userList);

        return pageInfo;
    }

    @Override
    @Transactional
    public void changeStateById(String userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setState("0".equals(user.getState()) ? "1" : "0");
        userMapper.updateByPrimaryKeySelective(updateUser);

    }

    @Override
    @Transactional
    public User saveOrUpdateUserInfo(User user, String[] xdDeparts, String[] dyDeparts) {
        String userId = user.getId();
        if (StringUtils.isBlank(userId)) {//新增
            userId = UniqueIDGen.getUniqueID() + "";
            user.setId(userId);
            userMapper.insertSelective(user);
        } else {//修改
            userMapper.updateByPrimaryKeySelective(user);
            UserDepart tempUserDepart = new UserDepart();
            tempUserDepart.setUserId(userId);
            userDepartMapper.delete(tempUserDepart);
        }
        if (xdDeparts != null && xdDeparts.length > 0) {
            for (String xdDepartId : xdDeparts) {
                UserDepart userDepart = new UserDepart();
                userDepart.setId(UniqueIDGen.getUniqueID() + "");
                userDepart.setUserId(userId);
                userDepart.setDepartId(xdDepartId);
                userDepart.setFlag("0");
                userDepartMapper.insertSelective(userDepart);
            }
        }
        if (dyDeparts != null && dyDeparts.length > 0) {
            for (String dyDepartId : dyDeparts) {
                UserDepart userDepart = new UserDepart();
                userDepart.setId(UniqueIDGen.getUniqueID() + "");
                userDepart.setUserId(userId);
                userDepart.setDepartId(dyDepartId);
                userDepart.setFlag("1");
                userDepartMapper.insertSelective(userDepart);
            }
        }
        return user;
    }

    @Override
    @Transactional
    public void deleteInfoById(String userId) {
        //删除用户角色表
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRoleMapper.delete(userRole);

        //删除用户部门表
        UserDepart userDepart = new UserDepart();
        userDepart.setUserId(userId);
        userDepartMapper.delete(userDepart);

        //删除用户表
        userMapper.deleteByPrimaryKey(userId);

    }

    @Override
    @Transactional
    public User login(String userName, String passWord) {
        User sysUser = new User();
        sysUser.setUserName(userName);
        sysUser.setPassWord(PassWordEncrypt.EncryptByMd5(passWord));
        sysUser = userMapper.selectOne(sysUser);
        return sysUser;
    }
    @Override
    @Transactional
    public int checkExtensionNum(Map<String, String> params) {
        return userMapper.checkExtensionNum(params);
    }
}
