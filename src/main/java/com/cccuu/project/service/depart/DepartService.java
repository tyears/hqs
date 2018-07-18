package com.cccuu.project.service.depart;

import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface DepartService extends BaseService<Depart> {
    /**
     * 分页查询数据
     * @param params
     * @return
     */
    public PageInfo<Depart> queryListByPage(Map<String,String> params);
}
