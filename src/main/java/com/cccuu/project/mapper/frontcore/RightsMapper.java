package com.cccuu.project.mapper.frontcore;

import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;
import java.util.Map;

public interface RightsMapper extends BaseMapper<Rights> {

    List<Map<String, Object>> getRightsByUserid(String userid);
}
