package com.cccuu.project.service.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cccuu.project.mapper.core.SysModuleMapper;
import com.cccuu.project.mapper.core.SysRoleModuleMapper;
import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.core.SysRoleModule;
import com.cccuu.project.service.core.SysRoleModuleService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.UniqueIDGen;

@Service("sysRoleModuleService")
public class SysRoleModuleServiceImpl extends BaseServiceImpl<SysRoleModule> implements SysRoleModuleService {
	
	private SysRoleModuleMapper sysRoleModuleMapper;
	
	@Autowired
	private SysModuleMapper sysModuleMapper;

	@Autowired
	public void setSysRoleModuleDao(SysRoleModuleMapper sysRoleModuleMapper) {
		this.sysRoleModuleMapper = sysRoleModuleMapper;
		baseMapper = sysRoleModuleMapper;
	}

	@Override
	@Transactional
	public List<SysModule> getRoleModuleTreeNodes(String roleId) {
		// TODO Auto-generated method stub
		List<SysModule> resultList =  new ArrayList<SysModule>();
		//获取所有资源菜单
		List<SysModule> sysRightsList = this.sysModuleMapper.selectAll();
		//获取角色对应的所有资源菜单
		SysRoleModule tempSysRoleModule = new SysRoleModule();
		tempSysRoleModule.setRoleId(roleId);
		List<SysRoleModule> sysRoleModuleList = sysRoleModuleMapper.select(tempSysRoleModule);
		for (SysModule sysModule : sysRightsList) {
			for (SysRoleModule sysRoleModule : sysRoleModuleList) {
				if(sysModule.getId().equals(sysRoleModule.getModuleId())){
					sysModule.setChecked("true");
					break;
				}
			}
			resultList.add(sysModule);
		}
		return resultList;
	}

	@Override
	@Transactional
	public void saveRoleModule(SysRole sysRole) {
		// TODO Auto-generated method stub
		//删除角色对应的所有权限
		SysRoleModule tempSysRoleModule = new SysRoleModule();
		tempSysRoleModule.setRoleId(sysRole.getId());
		sysRoleModuleMapper.delete(tempSysRoleModule);
		if(sysRole.getSysRoleModuleList()!=null&&sysRole.getSysRoleModuleList().size()>0){
			for (SysRoleModule sysRoleModule : sysRole.getSysRoleModuleList()) {
				sysRoleModule.setId(UniqueIDGen.getUniqueID()+"");
				this.sysRoleModuleMapper.insertSelective(sysRoleModule);
			}
		}
	}


}
