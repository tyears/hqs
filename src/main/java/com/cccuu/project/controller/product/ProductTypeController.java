package com.cccuu.project.controller.product;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.product.Product;
import com.cccuu.project.service.product.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 产品类别Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:19
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController extends BaseController {
	
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/product/productTypeQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/product/productTypeListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<ProductType> pageInfo = productTypeService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/product/productTypeEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			ProductType info = productTypeService.get(id);
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
	public void editSave(ProductType info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setCreateTime(new Date());
		}
		try {
			List<ProductType> list=productTypeService.queryListByName(info.getName(),info.getId());
			if(list==null || list.size()==0){
			info = productTypeService.insertOrUpdate(info);
			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("类别名称已存在");
			}
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
			Product product = new Product();
			product.setTypeId(id);
			List<Product> list = productService.queryBySelective(product);
			if(list!=null&&list.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该产品类别下有产品,无法删除");
			}else {
				productTypeService.deleteInfoById(id);
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