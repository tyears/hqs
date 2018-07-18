package com.cccuu.project.service.sysuser.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysModuleMapper;
import com.cccuu.project.mapper.core.SysUserRoleMapper;
import com.cccuu.project.mapper.sysuser.SysUserMapper;
import com.cccuu.project.model.core.SysUserRole;
import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.service.sysuser.SysUserService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.cccuu.project.utils.PassWordEncrypt;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {
	
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;
	
	@Autowired
	private SysModuleMapper sysModuleMapper;
	
	@Autowired
	public void setSysUserMapper(SysUserMapper sysUserMapper) {
		this.sysUserMapper = sysUserMapper;
		baseMapper = sysUserMapper;
	}

	@Override
	@Transactional
	public SysUser sysLogin(String userName, String passWord) {
		// TODO Auto-generated method stub
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		sysUser.setPassWord(PassWordEncrypt.EncryptByMd5(passWord));
		sysUser = sysUserMapper.selectOne(sysUser);
		return sysUser;
	}

	@Override
	@Transactional
	public PageInfo<SysUser> queryListByPage(Map<String, String> params) {
		
		//设置查询条件
		Example example = new Example(SysUser.class);
		example.setOrderByClause(" create_time desc");
		Example.Criteria creiteria = example.createCriteria();
		String userName = params.get("userName");
		if(StringUtils.isNotBlank(userName)){
			creiteria.andLike("userName","%"+userName+"%");
		}
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		String state = params.get("state");
		if(StringUtils.isNotBlank(state)){
			creiteria.andEqualTo("state",state);
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<SysUser> sysUserList = sysUserMapper.selectByExample(example);
		
		PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(sysUserList);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryUserTopModule(String userId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
		if("admin".equals(sysUser.getUserName())){
			//查询所有顶级菜单
			Example example = new Example(SysUser.class);
			example.setOrderByClause("sort asc");
			resultList = sysModuleMapper.queryByFkIdArry(new String[]{"0"});
		}else{
			resultList = sysModuleMapper.queryTopModuleByUserId(userId);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Map<String, Object>> queryUserLeftMenuByTopTabId(String userId,String topTabId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		List<Map<String,Object>> threeLevelModule = new ArrayList<Map<String,Object>>();
		
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
		if("admin".equals(sysUser.getUserName())){
			resultList = sysModuleMapper.queryByFkIdArry(new String[]{topTabId});
			if(resultList!=null&&resultList.size()>0){
				//组合所有二级id
				List<String> twoIdList = new ArrayList<String>();
				for (Map<String,Object> loopObj : resultList) {
					String tempTwoId = loopObj.get("id")+"";
					if(!twoIdList.contains(tempTwoId)){
						twoIdList.add(tempTwoId);
					}
				}
				if(twoIdList!=null&&twoIdList.size()>0){
					threeLevelModule = sysModuleMapper.queryByFkIdArry(twoIdList.toArray(new String[twoIdList.size()]));
				}
			}
		}else{
			//根据userId查询所有角色
			resultList = sysModuleMapper.queryModulesByUserIdPrevModuleIdArry(userId, new String[]{topTabId});
			if(resultList!=null&&resultList.size()>0){
				//组合所有二级id
				List<String> twoIdList = new ArrayList<String>();
				for (Map<String,Object> loopObj : resultList) {
					String tempTwoId = loopObj.get("id")+"";
					if(!twoIdList.contains(tempTwoId)){
						twoIdList.add(tempTwoId);
					}
				}
				if(twoIdList!=null&&twoIdList.size()>0){
					threeLevelModule = sysModuleMapper.queryModulesByUserIdPrevModuleIdArry(userId,twoIdList.toArray(new String[twoIdList.size()]));
				}
			}
		}
		if(resultList!=null&&resultList.size()>0&&threeLevelModule!=null&&threeLevelModule.size()>0){
			for (Map<String,Object> loopThree : threeLevelModule) {
				String threeFkId = loopThree.get("fk_id")+"";
				for (Map<String, Object> loopTwo : resultList) {
					String twoId = loopTwo.get("id")+"";
					if(twoId.equals(threeFkId)){
						List<Map<String,Object>> children = null;
						Object childrenObject = loopTwo.get("children");
						if(childrenObject==null){
							children = new ArrayList<Map<String,Object>>();
						}else{
							children = (List<Map<String, Object>>) childrenObject;
						}
						children.add(loopThree);
						loopTwo.put("children", children);
						break;
					}
				}
			}
		}
		//根据角色查询权限
		return resultList;
	}

	@Override
	@Transactional
	public void changeStateById(String userId) {
		// TODO Auto-generated method stub
		SysUser sysUser = sysUserMapper.selectByPrimaryKey(userId);
		SysUser updateUser = new SysUser();
		updateUser.setId(userId);
		updateUser.setState("0".equals(sysUser.getState())?"1":"0");
		sysUserMapper.updateByPrimaryKeySelective(updateUser);
	}

	@Override
	@Transactional
	public int deleteInfoById(String userId) {
		// TODO Auto-generated method stub
		//删除用户角色表数据
		SysUserRole tempsysUserRole = new SysUserRole();
		tempsysUserRole.setUserId(userId);
		sysUserRoleMapper.delete(tempsysUserRole);
		return sysUserMapper.deleteByPrimaryKey(userId);
	}
}
