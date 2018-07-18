package com.cccuu.project.controller.core;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.SysRole;
import com.cccuu.project.service.core.SysRoleModuleService;
import com.cccuu.project.service.core.SysRoleService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
@Controller
@RequestMapping("sysRole")
public class SysRoleController extends BaseController {
	
	@Resource
	private SysRoleService sysRoleService;
	
	@Resource
	private SysRoleModuleService sysRoleModuleService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/role/sysRoleQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/core/role/sysRoleListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<SysRole> pageInfo = sysRoleService.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
	/**
	 * 到编辑角色信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/core/role/sysRoleEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			SysRole role = sysRoleService.get(id);
			modelAndView.addObject("info",role);
		}
		return modelAndView;
	}
	/**
	 * 编辑角色信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(SysRole sysRole,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增专家
		if(StringUtils.isBlank(sysRole.getId())){
			sysRole.setId(null);
			sysRole.setCreateTime(new Date());
		}
		try {
			sysRole = sysRoleService.insertOrUpdate(sysRole);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
		
	}
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		try {
			this.sysRoleService.deleteInfoById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
		
	}
	/**
	 * 去角色资源菜单树页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toModuleTreeUi")
	public ModelAndView toModuleTreeUi(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/core/role/roleModuleTree");
		String roleId = request.getParameter("roleId");
		modelAndView.addObject("roleId",roleId);
		return modelAndView;
	}
	/**
	 * 获取资源菜单树节点
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getModuleNodes")
	public void getModuleNodes(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		this.writeJson(response, sysRoleModuleService.getRoleModuleTreeNodes(roleId));
	}
	/**
	 * 保存角色资源菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveRoleModule")
	public void saveRoleRights(SysRole sysRole,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			this.sysRoleModuleService.saveRoleModule(sysRole);
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
