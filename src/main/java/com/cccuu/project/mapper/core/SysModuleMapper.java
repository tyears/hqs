package com.cccuu.project.mapper.core;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.utils.BaseMapper;

public interface SysModuleMapper extends BaseMapper<SysModule>{
	/**
     * 根据fkId集合查询记录
     * @param fkIdArry
     * @return
     */
    List<Map<String,Object>> queryByFkIdArry(@Param("fkIdArry")String[] fkIdArry);
    /**
     * 根据用户id获取顶级菜单
     * @param userId
     * @return
     */
    List<Map<String, Object>> queryTopModuleByUserId(String userId);
    /**
     * 根据用户id和上级id查询记录
     * @param userId
     * @param prevModuleIdArry
     * @return
     */
    List<Map<String, Object>> queryModulesByUserIdPrevModuleIdArry(@Param("userId")String userId,@Param("prevModuleIdArry")String[] prevModuleIdArry);
}