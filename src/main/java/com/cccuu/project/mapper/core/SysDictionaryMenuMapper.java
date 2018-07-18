package com.cccuu.project.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.utils.BaseMapper;

public interface SysDictionaryMenuMapper extends BaseMapper<SysDictionaryMenu>{
	/**
	 * 根据fkId和值数组获取列表
	 * @param fkId
	 * @param valueArry
	 * @return
	 */
	List<SysDictionaryMenu> queryListByFkIdValueArry(@Param("fkId")String fkId,@Param("valueArry")String[] valueArry);
	
}