package com.cccuu.project.controller.area;

import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.service.area.AreaProductService;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.product.ProductService;
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
/**
 * 单位市场产品Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月15日 10:25:37
 */
@Controller
@RequestMapping("areaProduct")
public class AreaProductController extends BaseController {
	
	@Resource
	private AreaProductService areaProductService;

	@Resource
	private DealerService dealerService;

	@Resource
	private ProductService productService;

	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/area/areaProductQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/area/areaProductListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = areaProductService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/area/areaProductEdit");
		String id = request.getParameter("id");
		String areaId = request.getParameter("areaId");
		AreaProduct info = new AreaProduct();
		info.setAreaId(areaId);
		if(StringUtils.isNotBlank(id)){
			info = areaProductService.get(id);
			modelAndView.addObject("product",productService.get(info.getProductId()));
		}
		modelAndView.addObject("info",info);
		//查询该单位市场下的经销商
		Dealer dealer = new Dealer();
		dealer.setDealerType("1");
		dealer.setIshezuo("y");
		dealer.setDistrictId(areaId);
		List<Dealer> dealers = dealerService.queryBySelective(dealer);
		modelAndView.addObject("dealers",dealers);
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
	public void editSave(AreaProduct info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setIsImport("0");
			info.setAuthorIsImport("0");
			info.setNoticeDealer("H0000");
		}
		try {
			if(StringUtils.isNotBlank(info.getId())){
				info = areaProductService.insertOrUpdate(info);
			}else {
				boolean b=areaProductService.addAP(info,"ht");
				if(!b){
					returnInfo.setResult(false);
					returnInfo.setMsg("该商品已关联");
					writeJson(response, returnInfo);
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
			areaProductService.deleteById(id);
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
	 * 查询区域是否已关联该商品
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryProductIsAdd")
	public void queryIsAdd(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String areaId = request.getParameter("areaId");
		String productId = request.getParameter("productId");
		try {
			AreaProduct areaProduct=new AreaProduct();
			areaProduct.setAreaId(areaId);
			areaProduct.setProductId(productId);
			areaProduct=areaProductService.getBySelective(areaProduct);
			if (areaProduct!=null){
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