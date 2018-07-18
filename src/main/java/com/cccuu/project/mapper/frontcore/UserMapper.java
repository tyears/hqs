package com.cccuu.project.mapper.frontcore;

import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper<User> {

    public List<Map<String,Object>> queryListByParams(Map<String,Object> parameterMap);

    public int checkExtensionNum(Map<String,String> parameterMap);
}
