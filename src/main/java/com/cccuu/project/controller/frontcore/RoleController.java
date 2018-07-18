package com.cccuu.project.controller.frontcore;

import com.cccuu.project.model.frontcore.Role;
import com.cccuu.project.model.frontcore.UserRole;
import com.cccuu.project.service.frontcore.RoleRightsService;
import com.cccuu.project.service.frontcore.RoleService;
import com.cccuu.project.service.frontcore.UserRoleService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private RoleRightsService roleRightsService;

	@Resource
	private UserRoleService userRoleService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/role/roleQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/role/roleListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Role> pageInfo = roleService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/frontcore/role/roleEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Role role = roleService.get(id);
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
	public void editSave(Role role,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增专家
		if(StringUtils.isBlank(role.getId())){
            role.setId(null);
            role.setCreateTime(new Date());
		}
		try {
            role = roleService.insertOrUpdate(role);
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
			UserRole userRole = new UserRole();
			userRole.setRoleId(id);
			List<UserRole> list = userRoleService.queryBySelective(userRole);
			if(list!=null&&list.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该角色已有用户在使用,无法删除");
			}else {
				roleService.deleteById(id);
				returnInfo.setResult(true);
				returnInfo.setMsg("删除成功");
			}
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
	@RequestMapping("toRightsTreeUi")
	public ModelAndView toModuleTreeUi(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/frontcore/role/roleRightsTree");
		String roleId = request.getParameter("roleId");
		modelAndView.addObject("roleId",roleId);
		return modelAndView;
	}
	/**
	 * 获取权限树节点
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getRightsNodes")
	public void getRightsNodes(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		this.writeJson(response, roleRightsService.getRoleRightsTreeNodes(roleId));
	}
	/**
	 * 保存角色权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveRoleRights")
	public void saveRoleRights(Role role, HttpServletRequest request, HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			roleRightsService.saveRoleRights(role);
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
