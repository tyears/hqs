package com.cccuu.project.controller.${packageName};

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.${packageName}.${className};
import com.cccuu.project.service.${packageName}.${className}Service;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * ${funName}Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date ${date?string("yyyy年MM月dd日 HH:mm:ss")}
 */
@Controller
@RequestMapping("${className?uncap_first}")
public class ${className}Controller extends BaseController {
	
	@Resource
	private ${className}Service ${className?uncap_first}Service;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/${packageName}/${pagePreName}Query");
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
    	ModelAndView modelAndView = new ModelAndView("system/${packageName}/${pagePreName}ListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<${className}> pageInfo = ${className?uncap_first}Service.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
    /**
	 * 到编辑信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/${packageName}/${pagePreName}Edit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			${className} info = ${className?uncap_first}Service.get(id);
			modelAndView.addObject("info",info);
		}
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @param info
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(${className} info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
		}
		try {
			info = ${className?uncap_first}Service.insertOrUpdate(info);
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
	 * 删除记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		try {
			${className?uncap_first}Service.deleteById(id);
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