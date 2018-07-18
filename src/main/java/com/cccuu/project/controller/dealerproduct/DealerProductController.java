package com.cccuu.project.controller.dealerproduct;

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

import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 客户产品关联Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:44:27
 */
@Controller
@RequestMapping("dealerProduct")
public class DealerProductController extends BaseController {
	
	@Resource
	private DealerProductService dealerProductService;
	@Resource
	private ProductService productService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/dealerproduct/dealerProductQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/dealerproduct/dealerProductListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = dealerProductService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/dealerproduct/dealerProductEdit");
		String id = request.getParameter("id");
		String dealerId=request.getParameter("dealerId");
		DealerProduct info=new DealerProduct();
		if(StringUtils.isNotBlank(id)){
			info = dealerProductService.get(id);
			Product product=productService.get(info.getProductId());
			info.setProduct(product);
		}else {
			info.setDealerId(dealerId);
		}
		modelAndView.addObject("info",info);
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
	public void editSave(DealerProduct info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setIsImport("0");
			info.setIsImportPurchase("0");
		}
		if(info.getGiveNum()==null){
			info.setGiveNum(0);
		}
		if(info.getDealerGiveNum()==null){
			info.setDealerGiveNum(0);
		}
		if(info.getCompanyGiveNum()==null){
			info.setCompanyGiveNum(0);
		}
		if(info.getNoticeNum()==null){
			info.setNoticeNum(0);
		}
		try {
			if(StringUtils.isNotBlank(info.getId())){
				info = dealerProductService.insertOrUpdate(info);
			}else {
				boolean b=dealerProductService.addDP(info,"ht");
				if(!b){
					returnInfo.setResult(false);
					returnInfo.setMsg("此商品已关联");
					writeJson(response,returnInfo);
					return;
				}
			}
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
			dealerProductService.deleteById(id);
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
	 * 查询客户是否已关联该商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryProductIsAdd")
	public void queryIsAdd(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String dealerId = request.getParameter("dealerId");
		String productId = request.getParameter("productId");
		try {
			DealerProduct dealerProduct=new DealerProduct();
			dealerProduct.setDealerId(dealerId);
			dealerProduct.setProductId(productId);
			dealerProduct=dealerProductService.getBySelective(dealerProduct);
			if (dealerProduct!=null){
				returnInfo.setResult(true);
				returnInfo.setObj(true);
			}else {
				returnInfo.setResult(true);
				returnInfo.setObj(false);
			}


		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}
}