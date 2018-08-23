package com.cccuu.project.mapper.area;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.utils.BaseMapper;

import java.util.List;

/**
 * 区域Mapper
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 16:43:02
 */
public interface AreaMapper extends BaseMapper<Area>{
    /**
     * 批量插入
     * @param list
     * @return
     */
    public int addMulti(List<Area> list);
    public void updateAreaMerit();
}