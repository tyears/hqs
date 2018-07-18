package com.cccuu.project.service.core.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysRoleMapper;
import com.cccuu.project.mapper.core.SysRoleModuleMapper;
import com.cccuu.project.mapper.core.SysUserRoleMapper;
import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.core.SysRoleModule;
import com.cccuu.project.model.core.SysUserRole;
import com.cccuu.project.service.core.SysRoleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("sysRoleService")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService{
	
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysRoleModuleMapper sysRoleModuleMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	@Autowired
	public void setSysRoleDao(SysRoleMapper sysRoleMapper){
		this.sysRoleMapper = sysRoleMapper;
		baseMapper = sysRoleMapper;
	}
	
	@Override
	@Transactional
	public PageInfo<SysRole> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(SysRole.class);
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
		
		List<SysRole> results = sysRoleMapper.selectByExample(example);
		
		PageInfo<SysRole> pageInfo = new PageInfo<SysRole>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public int deleteInfoById(String roleId) {
		// TODO Auto-generated method stub
		//先删除角色拥有的资源菜单
		SysRoleModule tempSysRoleModule = new SysRoleModule();
		tempSysRoleModule.setRoleId(roleId);
		sysRoleModuleMapper.delete(tempSysRoleModule);
		//删除用户被赋予的这些角色
		SysUserRole tempSysUserRole = new SysUserRole();
		tempSysUserRole.setRoleId(roleId);
		sysUserRoleMapper.delete(tempSysUserRole);
		
		return this.sysRoleMapper.deleteByPrimaryKey(roleId);
	}

}
