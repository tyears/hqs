package com.cccuu.project.controller.frontcore;

import com.cccuu.project.model.frontcore.Rights;
import com.cccuu.project.service.frontcore.RightsService;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("rights")
public class RightsController extends BaseController {
	
	@Resource
	private RightsService rightsService;
	/**
	 * 去权限管理页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toRightsTreeManager")
	public ModelAndView toRightsTreeManager(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/frontcore/rights/rightsTreeManager");
		return modelAndView;
	}
	/**
	 * 获取权限树信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getRightsNodes")
	public void getRightsNodes(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		this.writeJson(response,rightsService.getListByFkId(id));
	}
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/rights/rightsQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/frontcore/rights/rightsListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Rights> pageInfo = rightsService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/frontcore/rights/rightsEdit");
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		String level = request.getParameter("level");
		level = StringUtils.isBlank(level)?"0":level;
		Rights rights = new Rights();
		if(StringUtils.isNotBlank(id)){
			rights = rightsService.get(id);
		}else{
			rights.setFkId(fkId);
		}
		modelAndView.addObject("info",rights);
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
	public void editSave(Rights rights,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		if(StringUtils.isBlank(rights.getId())){
			rights.setId(null);
		}
		try {
			rights = rightsService.saveInfo(rights);
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
			rightsService.deleteInfoById(id, fkId);
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
	 * 检查code是否重复
	 * @param request
	 * @param response
	 */
	@RequestMapping("checkCode")
	public void checkCode(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		String code = request.getParameter("code");
		boolean have = true;		//是否可用
		Rights queryTemp = new Rights();
		queryTemp.setCode(code);
		List<Rights> rightsList = rightsService.queryBySelective(queryTemp);
		if(rightsList != null && rightsList.size()>0){
			if(StringUtils.isBlank(id)||!id.equals(rightsList.get(0).getId())){
				have = false;
			}
		}
		writeJson(response, have);
	}

}
