package com.cccuu.project.service.frontcore.impl;

import com.cccuu.project.mapper.frontcore.RoleMapper;
import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.service.frontcore.RoleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
        baseMapper = roleMapper;
    }

    @Override
    @Transactional
    public PageInfo<Role> queryListByPage(Map<String, String> params) {
        // TODO Auto-generated method stub
        //设置查询条件
        Example example = new Example(Role.class);
        example.setOrderByClause(" create_time desc");
        Example.Criteria creiteria = example.createCriteria();
        String name = params.get("name");
        if(StringUtils.isNotBlank(name)){
            creiteria.andLike("name","%"+name+"%");
        }
        String code = params.get("code");
        if(StringUtils.isNotBlank(code)){
            creiteria.andLike("code","%"+code+"%");
        }

        //设置分页参数
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
        int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

        //设置分页信息
        PageHelper.startPage(pageNum, pageSize);

        List<Role> roleList = roleMapper.selectByExample(example);

        PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);

        return pageInfo;
    }
}
