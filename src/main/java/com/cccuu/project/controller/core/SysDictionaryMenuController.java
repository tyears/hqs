package com.cccuu.project.controller.core;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.service.core.SysDictionaryMenuService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
@Controller
@RequestMapping("sysDictionaryMenu")
public class SysDictionaryMenuController extends BaseController {
	
	@Resource
	private SysDictionaryMenuService sysDinctionaryMenuService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/dictionary/menu/menuQuery");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
    	return modelAndView;
    }
    /**
     * 查询数据
     * @param request
     * @return
     */
    @RequestMapping("listData")
    public ModelAndView listData(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/dictionary/menu/menuListData");
    	String fkId = request.getParameter("fkId");
    	if(StringUtils.isNotBlank(fkId)){
    		SysDictionaryMenu tempSysDictionaryMenu = new SysDictionaryMenu();
    		tempSysDictionaryMenu.setFkId(fkId);
    		List<SysDictionaryMenu> resultList = sysDinctionaryMenuService.queryBySelective(tempSysDictionaryMenu);
    		modelAndView.addObject("dataList", resultList);
    	}
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
		ModelAndView modelAndView = new ModelAndView("system/core/dictionary/menu/menuEdit");
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		SysDictionaryMenu sysDictionaryMenu = new SysDictionaryMenu();
		sysDictionaryMenu.setFkId(fkId);
		if(StringUtils.isNotBlank(id)){
			sysDictionaryMenu = sysDinctionaryMenuService.get(id);
		}
		modelAndView.addObject("info", sysDictionaryMenu);
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(SysDictionaryMenu sysDictionaryMenu,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			//新增
			if(StringUtils.isBlank(sysDictionaryMenu.getId())){
				sysDictionaryMenu.setId(null);
			}
			sysDinctionaryMenuService.insertOrUpdate(sysDictionaryMenu);
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
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(String id,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			sysDinctionaryMenuService.deleteById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
		
	}

}
