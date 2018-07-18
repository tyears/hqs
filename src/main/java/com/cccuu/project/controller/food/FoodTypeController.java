package com.cccuu.project.controller.food;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.food.Food;
import com.cccuu.project.service.food.FoodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.food.FoodType;
import com.cccuu.project.service.food.FoodTypeService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 食品类型Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:27
 */
@Controller
@RequestMapping("foodType")
public class FoodTypeController extends BaseController {
	
	@Resource
	private FoodTypeService foodTypeService;
	@Resource
	private FoodService foodService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/food/foodTypeQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/food/foodTypeListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<FoodType> pageInfo = foodTypeService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/food/foodTypeEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			FoodType info = foodTypeService.get(id);
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
	public void editSave(FoodType info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setCreateTime(new Date());
		}
		try {
			info = foodTypeService.insertOrUpdate(info);
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
			Food food = new Food();
			food.setTypeId(id);
			List<Food> foodList = foodService.queryBySelective(food);
			if(foodList!=null&&foodList.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该食品分类下有食品,无法删除");
			}else {
				foodTypeService.deleteInfoById(id);
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
}