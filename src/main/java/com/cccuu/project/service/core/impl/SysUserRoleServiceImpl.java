package com.cccuu.project.service.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysRoleMapper;
import com.cccuu.project.mapper.core.SysUserRoleMapper;
import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.core.SysUserRole;
import com.cccuu.project.service.core.SysUserRoleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService{
	
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	public void setSysUserRoleDao(SysUserRoleMapper sysUserRoleMapper){
		this.sysUserRoleMapper = sysUserRoleMapper;
		baseMapper = sysUserRoleMapper;
	}

	@Override
	@Transactional
	public List<SysRole> getUserRoleInfo(String userId) {
		List<SysRole> resultList =  new ArrayList<SysRole>();
		//获取所有角色
		Example example = new Example(SysRole.class);
		example.setOrderByClause(" create_time desc");
		List<SysRole> sysRoleList = this.sysRoleMapper.selectByExample(example);
		//获取用户对应的所有角色
		SysUserRole tempSysUserRole = new SysUserRole();
		tempSysUserRole.setUserId(userId);
		List<SysUserRole> sysUserRoleList = this.sysUserRoleMapper.select(tempSysUserRole);
		for (SysRole sysRole : sysRoleList) {
			for (SysUserRole sysUserRole : sysUserRoleList) {
				if(sysRole.getId().equals(sysUserRole.getRoleId())){
					sysRole.setChecked("true");
					break;
				}
			}
			resultList.add(sysRole);
		}
		return resultList;
	}

	@Override
	@Transactional
	public void saveUserRole(String[] roleIdArry,String userId) {
		// TODO Auto-generated method stub
		//删除用户对应的所有角色
		SysUserRole tempSysUserRole = new SysUserRole();
		tempSysUserRole.setUserId(userId);
		sysUserRoleMapper.delete(tempSysUserRole);
		if(roleIdArry!=null&&roleIdArry.length>0){
			for (String roleId : roleIdArry) {
				SysUserRole sysUserRole = new SysUserRole();
				sysUserRole.setId(UniqueIDGen.getUniqueID()+"");
				sysUserRole.setRoleId(roleId);
				sysUserRole.setUserId(userId);
				sysUserRoleMapper.insertSelective(sysUserRole);
			}
		}
		
	}

}
