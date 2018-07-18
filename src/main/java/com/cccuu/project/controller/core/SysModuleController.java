package com.cccuu.project.controller.core;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.SysModule;
import com.cccuu.project.service.core.SysModuleService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("sysModule")
public class SysModuleController extends BaseController {
	
	@Resource
	private SysModuleService sysModuleService;
	/**
	 * 去资源菜单管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toModuleTreeManager")
	public ModelAndView toModuleTreeManager(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/core/module/moduleTreeManager");
		return modelAndView;
	}
	/**
	 * 获取资源菜单树信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getModuleNodes")
	public void getModuleNodes(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		this.writeJson(response,sysModuleService.getListByFkId(id));
	}
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/module/moduleQuery");
    	Map<String, String> params = getParamsMap(request);
    	String level = params.get("level");
		if(StringUtils.isBlank(level)){
			params.put("level", "0");
		}
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
    	ModelAndView modelAndView = new ModelAndView("system/core/module/moduleListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<SysModule> pageInfo = sysModuleService.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
	/**
	 * 到编辑资源菜单信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/core/module/moduleEdit");
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		String level = request.getParameter("level");
		level = StringUtils.isBlank(level)?"0":level;
		SysModule sysModule = new SysModule();
		if(StringUtils.isNotBlank(id)){
			sysModule = sysModuleService.get(id);
		}else{
			sysModule.setFkId(fkId);
		}
		modelAndView.addObject("info",sysModule);
		modelAndView.addObject("level",level);
		return modelAndView;
	}
	/**
	 * 编辑资源菜单信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(SysModule sysModule,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增专家
		if(StringUtils.isBlank(sysModule.getId())){
			sysModule.setId(null);
		}
		try {
			sysModule = sysModuleService.saveInfo(sysModule);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			writeJson(response, returnInfo);
		}
		
	}
	/**
	 * 删除资源菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		try {
			sysModuleService.deleteInfoById(id, fkId);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
		
	}
}
