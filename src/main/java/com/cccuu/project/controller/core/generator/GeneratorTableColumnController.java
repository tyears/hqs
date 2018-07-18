package com.cccuu.project.controller.core.generator;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.generator.GeneratorTableColumn;
import com.cccuu.project.service.core.generator.GeneratorTableColumnService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
@Controller
@RequestMapping("generatorTableColumn")
public class GeneratorTableColumnController extends BaseController {
	@Resource
	private GeneratorTableColumnService generatorTableColumnService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableColumnQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableColumnListData");
    	String fkId = request.getParameter("fkId");
    	if(StringUtils.isNotBlank(fkId)){
    		GeneratorTableColumn generatorTableColumn = new GeneratorTableColumn();
    		generatorTableColumn.setFkId(fkId);
    		List<GeneratorTableColumn> resultList = generatorTableColumnService.queryBySelective(generatorTableColumn);
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
		ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableColumnEdit");
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		GeneratorTableColumn generatorTableColumn = new GeneratorTableColumn();
		generatorTableColumn.setFkId(fkId);
		if(StringUtils.isNotBlank(id)){
			generatorTableColumn = generatorTableColumnService.get(id);
		}
		modelAndView.addObject("info", generatorTableColumn);
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(GeneratorTableColumn generatorTableColumn,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			//新增
			if(StringUtils.isBlank(generatorTableColumn.getId())){
				generatorTableColumn.setId(null);
			}
			generatorTableColumnService.insertOrUpdate(generatorTableColumn);
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
			generatorTableColumnService.deleteById(id);
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
