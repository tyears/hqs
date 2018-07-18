package com.cccuu.project.controller.sysuser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.service.core.SysUserRoleService;
import com.cccuu.project.service.sysuser.SysUserService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("sysUser")
public class SysUserController extends BaseController {
	
	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private SysUserRoleService sysUserRoleService;
	@Resource
	private LogService logService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/sysuser/sysUserQuery");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
    	return modelAndView;
    }
    /**
     * 分页查询数据
     * @param request
     * @return
     */
    @RequestMapping("listPageData")
    public ModelAndView listPageData(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/sysuser/sysUserListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<SysUser> pageInfo = sysUserService.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
	/**
	 * 去编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/sysuser/sysUserEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			SysUser sysUser = sysUserService.get(id);
			modelAndView.addObject("info", sysUser);
		}
		modelAndView.addObject("defaultPassWord", Constants.DEFAULT_PASSWORD);
		modelAndView.addObject("kemu", "php");
		modelAndView.addObject("sex", "1");
		modelAndView.addObject("favorite", "run,sing,");
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(SysUser sysUser,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			Log log=new Log();
			//新增
			if(StringUtils.isBlank(sysUser.getId())){
				sysUser.setId(null);
				sysUser.setPassWord(PassWordEncrypt.EncryptByMd5(Constants.DEFAULT_PASSWORD));
				sysUser.setCreateTime(new Date());
				log.setOperation("添加系统用户");
				log.setContent("添加名称为" + sysUser.getName() + "的系统用户");
			}else {
				log.setOperation("修改系统用户");
				log.setContent("修改名称为" + sysUser.getName() + "的系统用户");
			}
			sysUser.setUserType(StringUtils.isBlank(sysUser.getUserType())?"1":sysUser.getUserType());
			sysUserService.insertOrUpdate(sysUser);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
			//记录
			SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
			log.setUserId(userInfo.getSysUserId());
			log.setType("0");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
			log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
		
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(String id,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			SysUser sysUser=sysUserService.get(id);
			sysUserService.deleteInfoById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
			//记录
			Log log=new Log();
			log.setOperation("删除系统用户");
			log.setContent("删除名称为" + sysUser.getName() + "的系统用户");
			SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
			log.setUserId(userInfo.getSysUserId());
			log.setType("0");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
			log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
		
	}
	/**
	 * 改变状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("changeStateById")
	public void changeStateById(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String userId = request.getParameter("id");
			sysUserService.changeStateById(userId);
			returnInfo.setResult(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		this.writeJson(response, returnInfo);
	}
	
	@RequestMapping("checkUserName")
	public void checkUserName(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("userName");
		boolean have = true;		//是否重复
		SysUser tempObj = new SysUser();
		tempObj.setUserName(userName);
		List<SysUser> userList = sysUserService.queryBySelective(tempObj);
		if(userList != null && userList.size()>0){
			have = false;
		}
		writeJson(response, have);
	}
	/**
	 * 去设置角色页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toSetRole")
	public ModelAndView toSetRole(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/sysuser/sysUserRole");
		String userId = request.getParameter("userId");
		List<SysRole> sysRoleList = sysUserRoleService.getUserRoleInfo(userId);
		modelAndView.addObject("sysRoleList",sysRoleList);
		modelAndView.addObject("userId",userId);
		return modelAndView;
	}
	/**
	 * 保存用户角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveUserRole")
	public void saveRoleRights(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String roleIds = request.getParameter("roleIds");
			String userId = request.getParameter("userId");
			String[] roleIdArry = null;
			if(StringUtils.isNotBlank(roleIds)){
				roleIdArry = roleIds.split(",");
			}
			sysUserRoleService.saveUserRole(roleIdArry, userId);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}
}
