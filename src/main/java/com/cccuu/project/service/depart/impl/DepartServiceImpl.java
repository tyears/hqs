package com.cccuu.project.service.depart.impl;

import com.cccuu.project.mapper.depart.DepartMapper;
import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service("departService")
public class DepartServiceImpl extends BaseServiceImpl<Depart> implements DepartService {

    private DepartMapper departMapper;

    @Autowired
    public void setDepartMapper(DepartMapper departMapper) {
        this.departMapper = departMapper;
        baseMapper = departMapper;
    }

    @Override
    public PageInfo<Depart> queryListByPage(Map<String, String> params) {
        // TODO Auto-generated method stub
        //设置查询条件
        Example example = new Example(Depart.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria creiteria = example.createCriteria();
        String departName = params.get("departName");
        if(StringUtils.isNotBlank(departName)){
            creiteria.andLike("departName","%"+departName+"%");
        }

        //设置分页参数
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
        int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

        //设置分页信息
        PageHelper.startPage(pageNum, pageSize);

        List<Depart> results = departMapper.selectByExample(example);

        PageInfo<Depart> pageInfo = new PageInfo<Depart>(results);

        return pageInfo;
    }
}
