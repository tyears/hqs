package com.cccuu.project.service.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysModuleMapper;
import com.cccuu.project.mapper.core.SysRoleModuleMapper;
import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.model.core.SysRoleModule;
import com.cccuu.project.service.core.SysModuleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("sysModuleService")
public class SysModuleServiceImpl extends BaseServiceImpl<SysModule> implements SysModuleService {
	
	private SysModuleMapper sysModuleMapper;
	
	@Autowired
	public void setSysModuleDao(SysModuleMapper sysModuleMapper) {
		this.sysModuleMapper = sysModuleMapper;
		baseMapper = sysModuleMapper;
	}
	
	@Autowired
	private SysRoleModuleMapper sysRoleModuleMapper;

	@Override
	public PageInfo<SysModule> queryListByPage(Map<String, String> params) {
		// TODO Auto-generated method stub
		//设置查询条件
		Example example = new Example(SysModule.class);
		example.setOrderByClause(" sort asc");
		Example.Criteria creiteria = example.createCriteria();
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		String fkId = params.get("fkId");
		if(StringUtils.isNotBlank(fkId)){
			creiteria.andEqualTo("fkId", fkId);
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<SysModule> results = sysModuleMapper.selectByExample(example);
		
		PageInfo<SysModule> pageInfo = new PageInfo<SysModule>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<SysModule> getListByFkId(String fkId) {
		// TODO Auto-generated method stub
		List<SysModule> resultList = new ArrayList<SysModule>();
		if(StringUtils.isBlank(fkId)){
			SysModule rootModule = new SysModule();
			rootModule.setId("0");
			rootModule.setIsParent("true");
			rootModule.setName("资源菜单");
			resultList.add(rootModule);
		}else{
			Example example = new Example(SysModule.class);
			example.setOrderByClause("sort asc");
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("fkId", fkId);
			resultList = sysModuleMapper.selectByExample(example);
		}
		return resultList;
	}

	@Override
	public SysModule saveInfo(SysModule sysModule) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(sysModule.getFkId())&&!"0".equals(sysModule.getFkId())){
			SysModule parentSysModule = sysModuleMapper.selectByPrimaryKey(sysModule.getFkId());
			if(StringUtils.isBlank(parentSysModule.getIsParent())||!parentSysModule.getIsParent().equals("true")){
				parentSysModule.setIsParent("true");
				sysModuleMapper.updateByPrimaryKeySelective(parentSysModule);
			}
		}
		//查询自身是否有子权限
		if(StringUtils.isNotBlank(sysModule.getId())&&"false".equals(sysModule.getIsParent())){
			
			Example example = new Example(SysModule.class);
			example.setOrderByClause("sort asc");
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("fkId", sysModule.getId());
			List<SysModule> queryResult = sysModuleMapper.selectByExample(example);
			if(queryResult!=null&&queryResult.size()>0){
				sysModule.setIsParent("true");
			}
		}
		if(StringUtils.isNotBlank(sysModule.getId())){
			
			sysModuleMapper.updateByPrimaryKeySelective(sysModule);
		}else{
			sysModule.setId(UniqueIDGen.getUniqueID()+"");
			sysModuleMapper.insertSelective(sysModule);
		}
		return sysModule;
	}

	@Override
	public int deleteInfoById(String id, String fkId) {
		// TODO Auto-generated method stub
		
		int result = sysModuleMapper.deleteByPrimaryKey(id);
		//删除角色中拥有的这些资源菜单
		SysRoleModule tempSysRoleModule = new SysRoleModule();
		tempSysRoleModule.setModuleId(id);
		sysRoleModuleMapper.delete(tempSysRoleModule);
		if(StringUtils.isNotBlank(fkId)&&!"0".equals(fkId)){
			//查询父权限，判断是否还存在子权限
			SysModule tempObj = new SysModule();
			tempObj.setFkId(fkId);	
			List<SysModule> sysModuleList = sysModuleMapper.select(tempObj);
			if(sysModuleList==null||sysModuleList.size()==0){
				SysModule sysModule = sysModuleMapper.selectByPrimaryKey(fkId);
				sysModule.setIsParent("false");
				sysModuleMapper.updateByPrimaryKeySelective(sysModule);
			}
		}
		return result;
	}

}
