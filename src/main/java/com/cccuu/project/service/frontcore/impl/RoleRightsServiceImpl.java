package com.cccuu.project.service.frontcore.impl;

import com.cccuu.project.mapper.frontcore.RightsMapper;
import com.cccuu.project.mapper.frontcore.RoleRightsMapper;
import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.RoleRights;
import com.cccuu.project.service.frontcore.RoleRightsService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("roleRightsService")
public class RoleRightsServiceImpl extends BaseServiceImpl<RoleRights> implements RoleRightsService {

    private RoleRightsMapper roleRightsMapper;

    @Autowired
    public void setRoleRightsMapper(RoleRightsMapper roleRightsMapper) {
        this.roleRightsMapper = roleRightsMapper;
        baseMapper = roleRightsMapper;
    }

    @Autowired
    private RightsMapper rightsMapper;

    @Override
    @Transactional
    public List<Rights> getRoleRightsTreeNodes(String roleId) {
        // TODO Auto-generated method stub
        List<Rights> resultList =  new ArrayList<Rights>();
        //获取所有权限
        List<Rights> rightsList = rightsMapper.selectAll();
        //获取角色对应的所有权限
        RoleRights tempRoleRights = new RoleRights();
        tempRoleRights.setRoleId(roleId);
        List<RoleRights> roleRightsList = roleRightsMapper.select(tempRoleRights);
        for (Rights rights : rightsList) {
            for (RoleRights roleRights : roleRightsList) {
                if(rights.getId().equals(roleRights.getRightsId())){
                    rights.setChecked("true");
                    break;
                }
            }
            resultList.add(rights);
        }
        return resultList;
    }

    @Override
    @Transactional
    public void saveRoleRights(Role role) {
        // TODO Auto-generated method stub
        //删除角色对应的所有权限
        RoleRights tempRoleRights = new RoleRights();
        tempRoleRights.setRoleId(role.getId());
        roleRightsMapper.delete(tempRoleRights);
        if(role.getRoleRightsList()!=null&&role.getRoleRightsList().size()>0){
            for (RoleRights roleRights : role.getRoleRightsList()) {
                roleRights.setId(UniqueIDGen.getUniqueID()+"");
                roleRightsMapper.insertSelective(roleRights);
            }
        }
    }
}
