package com.cccuu.project.service.frontcore.impl;

import com.cccuu.project.mapper.frontcore.RoleMapper;
import com.cccuu.project.mapper.frontcore.UserRoleMapper;
import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.UserRole;
import com.cccuu.project.service.frontcore.UserRoleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {

    private UserRoleMapper userRoleMapper;

    @Autowired
    public void setUserRoleMapper(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
        baseMapper = userRoleMapper;
    }

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public List<Role> getUserRoleInfo(String userId) {
        List<Role> resultList =  new ArrayList<Role>();
        //获取所有角色
        Example example = new Example(Role.class);
        example.setOrderByClause(" create_time desc");
        List<Role> roleList = this.roleMapper.selectByExample(example);
        //获取用户对应的所有角色
        UserRole tempUserRole = new UserRole();
        tempUserRole.setUserId(userId);
        List<UserRole> userRoleList = this.userRoleMapper.select(tempUserRole);
        for (Role role : roleList) {
            for (UserRole userRole : userRoleList) {
                if(role.getId().equals(userRole.getRoleId())){
                    role.setChecked("true");
                    break;
                }
            }
            resultList.add(role);
        }
        return resultList;
    }

    @Override
    @Transactional
    public void saveUserRole(String[] roleIdArry, String userId) {
        // TODO Auto-generated method stub
        //删除用户对应的所有角色
        UserRole tempUserRole = new UserRole();
        tempUserRole.setUserId(userId);
        userRoleMapper.delete(tempUserRole);
        if(roleIdArry!=null&&roleIdArry.length>0){
            for (String roleId : roleIdArry) {
                UserRole userRole = new UserRole();
                userRole.setId(UniqueIDGen.getUniqueID()+"");
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleMapper.insertSelective(userRole);
            }
        }
    }
}
