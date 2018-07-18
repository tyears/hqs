package com.cccuu.project.service.depart.impl;

import com.cccuu.project.mapper.depart.UserDepartMapper;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.service.depart.UserDepartService;
import com.cccuu.project.utils.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userDepartService")
public class UserDepartServiceImpl extends BaseServiceImpl<UserDepart> implements UserDepartService {

    private UserDepartMapper userDepartMapper;

    @Autowired
    public void setUserDepartMapper(UserDepartMapper userDepartMapper) {
        this.userDepartMapper = userDepartMapper;
        baseMapper = userDepartMapper;
    }

    @Override
    @Transactional
    public List<UserDepart> queryListByUserId(String userId) {
        UserDepart userDepart = new UserDepart();
        userDepart.setUserId(userId);
        return userDepartMapper.select(userDepart);
    }
}
