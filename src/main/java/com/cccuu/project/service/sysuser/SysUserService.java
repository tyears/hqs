package com.cccuu.project.service.sysuser;

import java.util.List;
import java.util.Map;

import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

public interface SysUserService extends BaseService<SysUser>{
	/**
	 * 登录查询
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public SysUser sysLogin(String userName, String passWord);
	/**
	 * 分页查询信息
	 * @param params
	 * @return
	 */
	public PageInfo<SysUser> queryListByPage(Map<String, String> params);
	/**
	 * 获取用户拥有的顶部资源菜单
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> queryUserTopModule(String userId);
	/**
	 * 根据顶部一级id查询二级和三级菜单
	 * @param userId
	 * @param topTabId
	 * @return
	 */
	public List<Map<String,Object>> queryUserLeftMenuByTopTabId(String userId,String topTabId);
	
	/**
	 * 禁用启用用户
	 * @param SysUser
	 */
	public void changeStateById(String userId);
	/**
	 * 根据用户id删除
	 * @param userId
	 * @return
	 */
	public int deleteInfoById(String userId);
	
}
