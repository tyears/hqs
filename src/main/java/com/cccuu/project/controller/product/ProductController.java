package com.cccuu.project.controller.product;

import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.service.area.AreaProductService;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.service.food.FoodService;
import com.cccuu.project.service.giveproduct.GiveProductService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.orderproduct.OrderProductService;
import com.cccuu.project.service.product.ProductFoodService;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 产品Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:39:39
 */
@Controller
@RequestMapping("product")
public class ProductController extends BaseController {
	
	@Resource
	private ProductService productService;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private FoodService foodService;

	@Resource
	private ProductFoodService productFoodService;

	@Resource
	private LogService logService;
	@Resource
	private AreaProductService areaProductService;
	@Resource
	private DealerProductService dealerProductService;
	@Resource
	private GiveProductService giveProductService;
	@Resource
	private OrderProductService orderProductService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/product/productQuery");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
		List<ProductType> types = productTypeService.findAll();
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
    	ModelAndView modelAndView = new ModelAndView("system/product/productListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = productService.queryListByPageSys(params);
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
		ModelAndView modelAndView = new ModelAndView("system/product/productEdit");
		String id = request.getParameter("id");
		List<ProductFood> productFoods = null;
		if(StringUtils.isNotBlank(id)){
			Product info = productService.get(id);
			modelAndView.addObject("info",info);

			//查询所有适用食品
			ProductFood productFood = new ProductFood();
			productFood.setProductId(id);
			productFoods = productFoodService.queryBySelective(productFood);
		}
		//查询所有分类
		List<ProductType> types = productTypeService.findAll();
		modelAndView.addObject("types",types);
		//查询所有食物
		List<Map<String,Object>> foods = foodService.queryAll();
		StringBuilder stringBuilder=new StringBuilder();
		StringBuilder foodIds=new StringBuilder();
		if(productFoods!=null&&productFoods.size()>0){
			int i=0;
			for (Map<String,Object> loopFood:foods) {
				String foodId = loopFood.get("id")+"";
				for (ProductFood loopProductFood:productFoods) {
					if(foodId.equals(loopProductFood.getFoodId())){
						if(i==productFoods.size()-1){
							stringBuilder.append(loopFood.get("food_name"));
							foodIds.append(loopFood.get("id"));
						}else {
							stringBuilder.append(loopFood.get("food_name")).append(",");
							foodIds.append(loopFood.get("id")).append(",");
						}
						i++;
					}
				}
			}
		}
		modelAndView.addObject("foods",stringBuilder.toString());
		modelAndView.addObject("foodIds",foodIds.toString());

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
	public void editSave(Product info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String foodIds = request.getParameter("foodIds");
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setCreateTime(new Date());
			info.setReserved1("0");
		}
		try {
			String huohao = info.getProductNum();
			List<Map<String ,Object>> map = productService.isProductNum(info);
			if(map!=null&&map.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("货号已重复,请修改");
			}else {
				Log log = new Log();
				if (StringUtils.isBlank(info.getId())) {
					log.setOperation("添加产品");
					log.setContent("添加货号为" + info.getProductNum() + "的产品");
				} else {
					log.setOperation("修改产品");
					log.setContent("修改货号为" + info.getProductNum() + "的产品");
				}

				info = productService.saveInfo(info, foodIds);
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
			Product product=productService.get(id);
			productService.deleteProduct(id,request);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
			//记录
			Log log=new Log();
			log.setOperation("删除产品");
			log.setContent("删除货号为" + product.getProductNum() + "的产品");
			SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
			log.setUserId(userInfo.getSysUserId());
			log.setType("0");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
			log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
			this.writeJson(response, returnInfo);
	}

	/**
	 * 查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queryList")
	public void queryList(HttpServletRequest request,HttpServletResponse response){
		String keyWord = request.getParameter("keyWord");
		List<Map<String,Object>> resultList = productService.queryListByKeyWord(keyWord);
		this.writeJson(response,resultList);
	}



	/**
	 * 前台分页查询数据
	 * @param request
	 * @return
	 */
	@RequestMapping("productPage.html")
	public ModelAndView productPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("pc/jiage");
		Map<String, String> params = getParamsMap(request);
		modelAndView.addObject("params", params);
		params.put("jiage","1");
		PageInfo<Map<String,Object>> pageInfo = productService.queryListByPage(params);
		modelAndView.addObject("pageInfo", pageInfo);
		return modelAndView;
	}

	/**
	 * 导入产品
	 * @param request
	 * @param response
	 */
	@RequestMapping("excelProduct")
	public void excelProduct(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

			InputStream in = null;
			List<List<Object>> listob = null;
			MultipartFile file = multipartRequest.getFile("upfile");
			if (file.isEmpty()) {
				throw new Exception("文件不存在！");
			}

			in = file.getInputStream();
			listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());
			List<Object> typeNameList=new ArrayList<>();
			List<Product> productList=new ArrayList<>();
			for (List<Object> objects : listob) {
				//判断货号是否重复
				Product queryProduct=new Product();
				queryProduct.setProductNum((String) objects.get(1));
				queryProduct.setReserved1("0");
				queryProduct=productService.getBySelective(queryProduct);
				if(queryProduct!=null){
					queryProduct.setProductNum((String) objects.get(1));
					queryProduct.setProductName((String) objects.get(2));
					queryProduct.setSpec((String) objects.get(3));


					if(StringUtils.isNotBlank((String) objects.get(4))){
						queryProduct.setNumBox(CSVUtils.getBigDecimal(objects.get(4)).intValue());
					}else {
						queryProduct.setNumBox(0);
					}
					if(StringUtils.isNotBlank((String) objects.get(5))){
						queryProduct.setRetailPriceBag(CSVUtils.getBigDecimal(objects.get(5)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else {
						queryProduct.setRetailPriceBag(new BigDecimal(0));
					}
					if(StringUtils.isNotBlank((String) objects.get(6))){
						queryProduct.setRetailPriceBox(CSVUtils.getBigDecimal(objects.get(6)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else {
						queryProduct.setRetailPriceBox(new BigDecimal(0));
					}
					if(StringUtils.isNotBlank((String) objects.get(7))){
						queryProduct.setSellPriceBag(CSVUtils.getBigDecimal(objects.get(7)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else {
						queryProduct.setSellPriceBag(new BigDecimal(0));
					}
					if(StringUtils.isNotBlank((String) objects.get(8))){
						queryProduct.setSellPriceBox(CSVUtils.getBigDecimal(objects.get(8)).setScale(2,BigDecimal.ROUND_HALF_UP));
					}else {
						queryProduct.setSellPriceBox(new BigDecimal(0));
					}

					queryProduct.setNumBox(CSVUtils.getBigDecimal(objects.get(4)).intValue());
					queryProduct.setRetailPriceBag(CSVUtils.getBigDecimal(objects.get(5)).setScale(2,BigDecimal.ROUND_HALF_UP));
					queryProduct.setRetailPriceBox(CSVUtils.getBigDecimal(objects.get(6)).setScale(2,BigDecimal.ROUND_HALF_UP));
					queryProduct.setSellPriceBag(CSVUtils.getBigDecimal(objects.get(7)).setScale(2,BigDecimal.ROUND_HALF_UP));
					queryProduct.setSellPriceBox(CSVUtils.getBigDecimal(objects.get(8)).setScale(2,BigDecimal.ROUND_HALF_UP));
					productService.update(queryProduct);
				}else{
					Product product=new Product();
					ProductType productType=new ProductType();
					productType.setName((String) objects.get(0));
					productType=productTypeService.getBySelective(productType);
					if(productType!=null){
						product.setId(UniqueIDGen.getUniqueID()+"");
						product.setTypeId(productType.getId());
						product.setProductNum((String) objects.get(1));
						product.setProductName((String) objects.get(2));
						product.setSpec((String) objects.get(3));
						if(StringUtils.isNotBlank((String) objects.get(4))){
							product.setNumBox(CSVUtils.getBigDecimal(objects.get(4)).intValue());
						}else {
							product.setNumBox(0);
						}
						if(StringUtils.isNotBlank((String) objects.get(5))){
							product.setRetailPriceBag(CSVUtils.getBigDecimal(objects.get(5)).setScale(2,BigDecimal.ROUND_HALF_UP));
						}else {
							product.setRetailPriceBag(new BigDecimal(0));
						}
						if(StringUtils.isNotBlank((String) objects.get(6))){
							product.setRetailPriceBox(CSVUtils.getBigDecimal(objects.get(6)).setScale(2,BigDecimal.ROUND_HALF_UP));
						}else {
							product.setRetailPriceBox(new BigDecimal(0));
						}
						if(StringUtils.isNotBlank((String) objects.get(7))){
							product.setSellPriceBag(CSVUtils.getBigDecimal(objects.get(7)).setScale(2,BigDecimal.ROUND_HALF_UP));
						}else {
							product.setSellPriceBag(new BigDecimal(0));
						}
						if(StringUtils.isNotBlank((String) objects.get(8))){
							product.setSellPriceBox(CSVUtils.getBigDecimal(objects.get(8)).setScale(2,BigDecimal.ROUND_HALF_UP));
						}else {
							product.setSellPriceBox(new BigDecimal(0));
						}
						product.setSort(1);
						product.setCreateTime(new Date());
						product.setReserved1("0");
						productList.add(product);
					}
				}
			}

			if(productList.size() > 0){
				productService.addMulti(productList);
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
	 * 到食品列表页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("chooseFood")
	public ModelAndView chooseFood(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView=new ModelAndView("system/product/chooseFood");
		//查询所有食物
		String ids=request.getParameter("ids");
		modelAndView.addObject("ids",ids);
		return modelAndView;
	}

	/**
	 * 查询食品列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("queryAllFoodList")
	public void queryAllFoodList(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		Map<String,String> params=getParamsMap(request);
		try {
			List<Map<String,Object>> resultList = foodService.queryListByMap(params);
			returnInfo.setObj(resultList);
			returnInfo.setResult(true);
			returnInfo.setMsg("查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		this.writeJson(response,returnInfo);
	}
}