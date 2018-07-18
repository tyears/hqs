package com.cccuu.project.controller.common;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.service.sysuser.SysUserService;

@Controller
public class CommonController extends BaseController {
	Logger log = Logger.getLogger(CommonController.class);
	
	@Resource
	private SysUserService sysUserService;
	@Resource
	private LogService logService;
	
	/**
	 * 去登录页面
	 * @return
	 */
	@RequestMapping("toLogin")
	public ModelAndView toLogin(){
		ModelAndView modelAndView = new ModelAndView("system/login");
		return modelAndView;
	}

	/**
	 * 去主页
	 * @return
	 */
	@RequestMapping(value = {"toIndex","admin"})
	public ModelAndView toIndex(HttpServletRequest request){
		SysUserInfo sysUserInfo = (SysUserInfo) getSessionAttribute(request, Constants.SYS_SESSION_USER);
		ModelAndView modelAndView = new ModelAndView("system/index");
		//查询有权访问的顶部菜单集合
		List<Map<String,Object>> topTabList = sysUserService.queryUserTopModule(sysUserInfo.getSysUserId());
		modelAndView.addObject("topTabList",topTabList);
		return modelAndView;
	}
	/**
	 * 根据顶级菜单Id获取左侧菜单
	 * @return
	 */
	@RequestMapping(value = "getLeftMenu")
	public void getLeftMenu(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String topTabId = request.getParameter("topTabId");
			SysUserInfo sysUserInfo = (SysUserInfo) getSessionAttribute(request, Constants.SYS_SESSION_USER);
			List<Map<String,Object>> resultList = sysUserService.queryUserLeftMenuByTopTabId(sysUserInfo.getSysUserId(),topTabId);
			returnInfo.setResult(true);
			returnInfo.setObj(resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		this.writeJson(response, returnInfo);
	}
	/**
	 * 去桌面
	 * @return
	 */
	@RequestMapping("toDesktop")
	public ModelAndView toDesktop(){
		ModelAndView modelAndView = new ModelAndView("system/desktop");
		return modelAndView;
	}
	/**
	 * 后台登录操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "doLogin" , method = RequestMethod.POST)
	public void doLogin(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		try {
			String userName = request.getParameter("userName");
			String passWord = request.getParameter("passWord");
			String checkCode = request.getParameter("checkCode");
			if(!checkCode.equalsIgnoreCase(getSessionAttribute(request, com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY).toString())){
				resultMap.put("result", false);
				resultMap.put("type", "checkCode");
				resultMap.put("msg", "验证码错误!");
			}else{
				SysUser tempSysUser = new SysUser();
				tempSysUser.setUserName(userName);
				List<SysUser> sysUserList = sysUserService.queryBySelective(tempSysUser);
				if(sysUserList==null||sysUserList.size()==0){
					resultMap.put("result", false);
					resultMap.put("type", "userName");
					resultMap.put("msg", "用户不存在!");
				}else{
					SysUser sysUser = sysUserService.sysLogin(userName, passWord);
					if(sysUser==null){
						resultMap.put("result", false);
						resultMap.put("type", "passWord");
						resultMap.put("msg", "密码错误!");
					}else{
						if(StringUtils.isBlank(sysUser.getState())||"0".equals(sysUser.getState())){
							resultMap.put("result", false);
							resultMap.put("type", "userName");
							resultMap.put("msg", "用户被禁用!");
						}else{
							SysUserInfo sysUserInfo = new SysUserInfo(sysUser);
							//用户信息放入session
							setSessionAttribute(request, Constants.SYS_SESSION_USER, sysUserInfo);
							resultMap.put("result", true);
							resultMap.put("msg", "登录成功!");
							request.getSession().setMaxInactiveInterval(4*60*60);
							//更新最后登录时间
							sysUser.setLastLoginTime(new Date());
							sysUserService.updateSelective(sysUser);
							//记录
							Log log = new Log();
							log.setUserId(sysUser.getId());
							log.setOperation("登录操作");
							log.setType("0");
							log.setContent("登录操作");
							log.setRequestIp(request.getRemoteAddr());
							log.setCreateTime(new Date());
                            log.setMethod(sysUser.getName());
							logService.insertOrUpdate(log);
						}
					}
				}
			}
			writeJson(response, resultMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 后台退出操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loginOut")
	@SystemControllerLog(description = "退出操作")
	public String loginOut(HttpServletRequest request,HttpServletResponse response){
		removeSessionAttribute(request, Constants.SYS_SESSION_USER);
		return "redirect:toLogin.htm";
		
	}
	/**
	 * 去修改密码页面
	 * @return
	 */
	@RequestMapping("toUpdatePsw")
	public ModelAndView toUpdatePsw(){
		ModelAndView modelAndView = new ModelAndView("system/sysuser/changePassWord");
		return modelAndView;
	}
	/**
	 * 后台修改密码操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updatePsw" , method = RequestMethod.POST)
	@SystemControllerLog(description = "修改密码操作")
	public void updatePsw(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String oldPassWord = request.getParameter("oldPassWord");
		String newPassWord = request.getParameter("newPassWord");
		SysUserInfo userObject = (SysUserInfo) getSessionAttribute(request, Constants.SYS_SESSION_USER);
		SysUser sysuser = sysUserService.get(userObject.getSysUserId());
		if(sysuser.getPassWord().equals(PassWordEncrypt.EncryptByMd5(oldPassWord))){
			sysuser.setPassWord(PassWordEncrypt.EncryptByMd5(newPassWord));
			sysUserService.update(sysuser);
			resultMap.put("result", true);
			resultMap.put("msg", "修改成功");
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", "原密码错误");
		}
		writeJson(response, resultMap);
	}

	/**
	 * 到备注管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toRemarks")
	public ModelAndView toRemarks(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/remarks/remarks");
		modelAndView.addObject("info", RemarksUtil.getAll());
		return modelAndView;
	}

	/**
	 * 备注管理保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveRemarks")
	public ModelAndView saveRemarks(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("redirect:/toRemarks.htm");
		RemarksUtil.setAll(request);
		return modelAndView;
	}
}
