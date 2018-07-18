package com.cccuu.project.service.frontcore.impl;

import com.cccuu.project.mapper.frontcore.RightsMapper;
import com.cccuu.project.mapper.frontcore.RoleRightsMapper;
import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.model.frontcore.RoleRights;
import com.cccuu.project.service.frontcore.RightsService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("rightsService")
public class RightsServiceImpl extends BaseServiceImpl<Rights> implements RightsService {

    private RightsMapper rightsMapper;

    @Autowired
    public void setRightsMapper(RightsMapper rightsMapper) {
        this.rightsMapper = rightsMapper;
        baseMapper = rightsMapper;
    }

    @Autowired
    private RoleRightsMapper roleRightsMapper;

    @Override
    @Transactional
    public PageInfo<Rights> queryListByPage(Map<String, String> params) {
        //设置查询条件
        Example example = new Example(Rights.class);
        example.setOrderByClause(" sort asc");
        Example.Criteria creiteria = example.createCriteria();
        String name = params.get("name");
        if (StringUtils.isNotBlank(name)) {
            creiteria.andLike("name", "%" + name + "%");
        }
        String fkId = params.get("fkId");
        if (StringUtils.isNotBlank(fkId)) {
            creiteria.andEqualTo("fkId", fkId);
        }

        //设置分页参数
        String pageNumStr = params.get("pageNum");
        String pageSizeStr = params.get("pageSize");
        int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
        int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);

        //设置分页信息
        PageHelper.startPage(pageNum, pageSize);

        List<Rights> results = rightsMapper.selectByExample(example);

        PageInfo<Rights> pageInfo = new PageInfo<Rights>(results);

        return pageInfo;
    }

    @Override
    public List<Rights> getListByFkId(String fkId) {
        List<Rights> resultList = new ArrayList<Rights>();
        if (StringUtils.isBlank(fkId)) {
            Rights rootRights = new Rights();
            rootRights.setId("0");
            rootRights.setIsParent("true");
            rootRights.setName("权限列表");
            resultList.add(rootRights);
        } else {
            Example example = new Example(Rights.class);
            example.setOrderByClause("sort asc");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fkId", fkId);
            resultList = rightsMapper.selectByExample(example);
        }
        return resultList;
    }

    @Override
    public Rights saveInfo(Rights rights) {
        if (StringUtils.isNotBlank(rights.getFkId()) && !"0".equals(rights.getFkId())) {
            Rights parentRights = rightsMapper.selectByPrimaryKey(rights.getFkId());
            if (StringUtils.isBlank(parentRights.getIsParent()) || !parentRights.getIsParent().equals("true")) {
                parentRights.setIsParent("true");
                rightsMapper.updateByPrimaryKeySelective(parentRights);
            }
        }
        //查询自身是否有子权限
        if (StringUtils.isNotBlank(rights.getId()) && "false".equals(rights.getIsParent())) {

            Example example = new Example(SysModule.class);
            example.setOrderByClause("sort asc");
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("fkId", rights.getId());
            List<Rights> queryResult = rightsMapper.selectByExample(example);
            if (queryResult != null && queryResult.size() > 0) {
                rights.setIsParent("true");
            }
        }
        if (StringUtils.isNotBlank(rights.getId())) {

            rightsMapper.updateByPrimaryKeySelective(rights);
        } else {
            rights.setId(UniqueIDGen.getUniqueID() + "");
            rightsMapper.insertSelective(rights);
        }
        return rights;
    }

    @Override
    public int deleteInfoById(String id, String fkId) {
        int result = rightsMapper.deleteByPrimaryKey(id);
        //删除角色中拥有的这些资源菜单
        RoleRights tempRoleRights = new RoleRights();
        tempRoleRights.setRightsId(id);
        roleRightsMapper.delete(tempRoleRights);
        if (StringUtils.isNotBlank(fkId) && !"0".equals(fkId)) {
            //查询父权限，判断是否还存在子权限
            Rights tempObj = new Rights();
            tempObj.setFkId(fkId);
            List<Rights> rightsList = rightsMapper.select(tempObj);
            if (rightsList == null || rightsList.size() == 0) {
                Rights rights = rightsMapper.selectByPrimaryKey(fkId);
                rights.setIsParent("false");
                rightsMapper.updateByPrimaryKeySelective(rights);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getRightsByUserid(String userid) {
        return rightsMapper.getRightsByUserid(userid);
    }
}
