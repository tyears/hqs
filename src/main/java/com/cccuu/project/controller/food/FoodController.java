package com.cccuu.project.controller.food;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.food.FoodType;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.service.food.FoodTypeService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.product.ProductFoodService;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.utils.Constants;
import com.cccuu.project.utils.SysUserInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.food.Food;
import com.cccuu.project.service.food.FoodService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 食品Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:38:49
 */
@Controller
@RequestMapping("food")
public class FoodController extends BaseController {
	
	@Resource
	private FoodService foodService;

	@Resource
	private FoodTypeService foodTypeService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductFoodService productFoodService;
	@Resource
	private LogService logService;

	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/food/foodQuery");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
        //查询所有分类
        List<FoodType> types = foodTypeService.findAll();
        modelAndView.addObject("types",types);
    	return modelAndView;
    }
    /**
     * 分页查询数据
     * @param request
     * @return
     */
    @RequestMapping("listPageData")
    public ModelAndView listPageData(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/food/foodListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = foodService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/food/foodEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Food info = foodService.get(id);
			modelAndView.addObject("info",info);
		}
		//查询所有分类
		List<FoodType> types = foodTypeService.findAll();

		modelAndView.addObject("types",types);
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
	public void editSave(Food info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		Log log=new Log();
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setCreateTime(new Date());
			log.setOperation("添加食品");
			log.setContent("添加名称为" + info.getFoodName() + "的食品");
		}else {
			log.setOperation("修改食品");
			log.setContent("修改名称为" + info.getFoodName() + "的食品");
		}
		try {
			info = foodService.insertOrUpdate(info);
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
			ProductFood productFood = new ProductFood();
			productFood.setFoodId(id);
			List<ProductFood> list = productFoodService.queryBySelective(productFood);
			if(list!=null&&list.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该食品下已有使用产品,无法删除");
			}else {
				Food food=foodService.get(id);
				foodService.deleteById(id);
				returnInfo.setResult(true);
				returnInfo.setMsg("删除成功");
				//记录
				Log log=new Log();
				log.setOperation("删除食品");
				log.setContent("删除名称为" + food.getFoodName() + "的食品");
				SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
				log.setUserId(userInfo.getSysUserId());
				log.setType("0");
				log.setRequestIp(request.getRemoteAddr());
				log.setCreateTime(new Date());
				log.setMethod(userInfo.getName());
				logService.insertOrUpdate(log);
			}
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
		
	}
	/**
	 * 查询可以适用的产品列表
	 * @param request
	 * @return
	 */
	@RequestMapping("canUseProducts")
	public ModelAndView canUseProducts(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("system/food/canUseProducts");
		String id = request.getParameter("id");
		List<Map<String,Object>> products = productService.queryListByFoodId(id);
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	/**
	 * 保存排序
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveSort")
	public void saveSort(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String productFoodId = request.getParameter("productFoodId");
		String sort = request.getParameter("sort");

		try {
			ProductFood productFood = new ProductFood();
			productFood.setId(productFoodId);
			productFood.setSort(new Integer(sort));
			productFoodService.updateSelective(productFood);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}

	}
}