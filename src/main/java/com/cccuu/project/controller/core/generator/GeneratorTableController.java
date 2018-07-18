package com.cccuu.project.controller.core.generator;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.core.generator.GeneratorTable;
import com.cccuu.project.service.core.generator.GeneratorTableService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
@Controller
@RequestMapping("generatorTable")
public class GeneratorTableController extends BaseController {
	@Resource
	private GeneratorTableService generatorTableService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<GeneratorTable> pageInfo = generatorTableService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorTableEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			GeneratorTable generatorTable = generatorTableService.get(id);
			modelAndView.addObject("info", generatorTable);
		}
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(GeneratorTable generatorTable,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			//新增
			if(StringUtils.isBlank(generatorTable.getId())){
				generatorTable.setId(null);
			}
			generatorTable.setModifyTime(new Date());
			generatorTableService.insertOrUpdate(generatorTable);
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
			generatorTableService.deleteById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
		
	}
	/**
	 * 生成表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("createTable")
	public void createTable(String id,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			generatorTableService.executeCreateTable(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("建表成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg("建表错误，请查看日志");
		}
		writeJson(response, returnInfo);
		
	}
	/**
	 * 去生成设置页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toCreatSet")
	public ModelAndView toCreatSet(String id,HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/core/generator/generatorSet");
		modelAndView.addObject("id",id);
		return modelAndView;
	}
	/**
	 * 生成代码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("createCode")
	public void createCode(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			generatorTableService.createCode(getParamsMap(request));
			returnInfo.setResult(true);
			returnInfo.setMsg("创建代码成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg("创建代码失败");
		}
		writeJson(response, returnInfo);
		
	}
}
