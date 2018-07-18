package com.cccuu.project.service.depart;

import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.utils.BaseService;

import java.util.List;

public interface UserDepartService extends BaseService<UserDepart> {
    public List<UserDepart> queryListByUserId(String userId);
}
