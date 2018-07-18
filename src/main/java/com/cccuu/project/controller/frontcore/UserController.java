package com.cccuu.project.controller.frontcore;

import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.service.depart.UserDepartService;
import com.cccuu.project.service.frontcore.UserRoleService;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	
	@Resource
	private UserService userService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private DepartService departService;

	@Resource
	private UserDepartService userDepartService;
	@Resource
	private LogService logService;

	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/user/userQuery");
    	Map<String, String> params = getParamsMap(request);
		//查询部门列表
		List<Depart> departList = departService.findAll();
		modelAndView.addObject("departList",departList);
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
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/user/userListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = userService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/frontcore/user/userEdit");
		String id = request.getParameter("id");
		//查询部门列表
		List<Depart> departList = departService.findAll();
		modelAndView.addObject("departList",departList);
		//询单单位
		List<Depart> xdDepartList = new ArrayList<Depart>();
		//打印单位
		List<Depart> dyDepartList = new ArrayList<Depart>();
		if (departList!=null&&departList.size()>0){
			xdDepartList = copyDeptList(departList);
			dyDepartList = copyDeptList(departList);
		}
		if(StringUtils.isNotBlank(id)){
			User user = userService.get(id);
			modelAndView.addObject("info", user);
			//
			if (departList!=null&&departList.size()>0){
				List<UserDepart> userDepartList = userDepartService.queryListByUserId(id);
				if (userDepartList!=null&&userDepartList.size()>0){
					for (Depart xdDepart:xdDepartList) {
						for (UserDepart userDepart:userDepartList) {
							if(userDepart.getFlag()!=null&&"0".equals(userDepart.getFlag())&&xdDepart.getId().equals(userDepart.getDepartId())){
								xdDepart.setChecked("true");
							}
						}
					}
					for (Depart dyDepart:dyDepartList) {
						for (UserDepart userDepart:userDepartList) {
							if(userDepart.getFlag()!=null&&"1".equals(userDepart.getFlag())&&dyDepart.getId().equals(userDepart.getDepartId())){
								dyDepart.setChecked("true");
							}
						}
					}

				}
			}
	}
		modelAndView.addObject("xdDepartList", xdDepartList);
		modelAndView.addObject("dyDepartList", dyDepartList);
		modelAndView.addObject("defaultPassWord", Constants.DEFAULT_PASSWORD);
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(User user,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			Log log=new Log();
			//新增
			if(StringUtils.isBlank(user.getId())){
				user.setId(null);
				user.setPassWord(PassWordEncrypt.EncryptByMd5(Constants.DEFAULT_PASSWORD));
				user.setCreateTime(new Date());
				user.setTransferNum(0);
				user.setObtainTime(new Date());
				log.setOperation("添加前台用户");
				log.setContent("添加名称为" + user.getName() + "的前台用户");
			}else {
				log.setOperation("修改前台用户");
				log.setContent("修改名称为" + user.getName() + "的前台用户");
			}
			String[] xdDeparts = request.getParameterValues("xdDeparts");
			String[] dyDeparts = request.getParameterValues("dyDeparts");
			userService.saveOrUpdateUserInfo(user,xdDeparts,dyDeparts);
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
			User user=userService.get(id);
			userService.deleteInfoById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
			//记录
			Log log=new Log();
			log.setOperation("删除前台用户");
			log.setContent("删除名称为" + user.getName() + "的前台用户");
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
			userService.changeStateById(userId);
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
		User tempObj = new User();
		tempObj.setUserName(userName);
		List<User> userList = userService.queryBySelective(tempObj);
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
		ModelAndView modelAndView = new ModelAndView("system/frontcore/user/userRole");
		String userId = request.getParameter("userId");
		List<Role> roleList = userRoleService.getUserRoleInfo(userId);
		modelAndView.addObject("roleList",roleList);
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
	public void saveUserRole(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String roleIds = request.getParameter("roleIds");
			String userId = request.getParameter("userId");
			String[] roleIdArry = null;
			if(StringUtils.isNotBlank(roleIds)){
				roleIdArry = roleIds.split(",");
			}
			userRoleService.saveUserRole(roleIdArry, userId);
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

	/**
	 * 部门list复制
	 * @param departList
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public List<Depart> copyDeptList(List<Depart> departList){
		List<Depart> resultList = new ArrayList<Depart>();
		if (departList!=null&&departList.size()>0){
			for (Depart loopDepart:departList) {
				Depart depart = new Depart();
				try {
					BeanUtils.copyProperties(depart,loopDepart);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				resultList.add(depart);
			}
		}
		return resultList;
	}

	/**
	 * 重置密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("resetpassword")
	public void resetpassword(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		try {
			User user = userService.get(id);
			user.setPassWord(PassWordEncrypt.EncryptByMd5(Constants.DEFAULT_PASSWORD));
			userService.update(user);
			returnInfo.setMsg("修改成功");
			returnInfo.setResult(true);
		}catch (Exception e){
			e.printStackTrace();
			returnInfo.setMsg("重置密码错误");
			returnInfo.setResult(false);
		}finally {
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 检查
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkExtensionNum")
	public void checkExtensionNum(HttpServletRequest request,HttpServletResponse response){
		String extensionNum = request.getParameter("extensionNum");
		Map<String,String> params=getParamsMap(request);
		boolean have = true;		//是否重复
		if(StringUtils.isNotBlank(extensionNum)){
			Integer i=userService.checkExtensionNum(params);
			if(i>0){
				have = false;
			}
		}
		writeJson(response, have);
	}
}
