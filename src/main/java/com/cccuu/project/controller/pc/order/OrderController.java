package com.cccuu.project.controller.pc.order;

import com.cccuu.project.model.DTO.FoodDTO;
import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.model.food.Food;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.give.Give;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.model.orderproduct.OrderProduct;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.model.product.ProductFood;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.service.area.AreaProductService;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.service.depart.UserDepartService;
import com.cccuu.project.service.food.FoodService;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.service.give.GiveService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.operationrecord.OperationRecordService;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.service.orderproduct.OrderProductService;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

import static java.awt.SystemColor.info;

/**
 * 订单Controller
 *
 * @Description
 * @Author zhaixiaoliang
 * @Date 2017年09月18日 15:19:31
 */
@Controller
@RequestMapping("order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;
    @Resource
    private DealerService dealerService;
    @Resource
    private AreaService areaService;
    @Resource
    private ProductService productService;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private UserDepartService userDepartService;
    @Resource
    private DepartService departService;
    @Resource
    private UserService userService;
    @Resource
    private FoodService foodService;
    @Resource
    private OperationRecordService operationRecordService;
	@Resource
	private LogService logService;
	@Resource
    private GiveService giveService;
	@Resource
	private DealerProductService dealerProductService;
	@Resource
	private AreaProductService areaProductService;
	@Resource
	private OrderProductService orderProductService;

    /**
     * 留言excel导入
     *
     * @param request
     * @param response
     */
    @RequestMapping("uploadExcel.html")
    public void uploadExcel(HttpServletRequest request, HttpServletResponse response) {
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
            if(listob!=null && listob.size()>0){
            	orderService.updateImport();
				for (List<Object> objects : listob) {
					if(objects.size()>4 && StringUtils.isNotBlank((String) objects.get(1))){
						Order order=new Order();
						order.setId(null);
						order.setMessageType((String) objects.get(0));
						//去除手机号前面0
						String tel= String.valueOf(((String) objects.get(1)).charAt(0));
						if("0".equals(tel)){
							tel=((String) objects.get(1)).substring(1);
						}else {
							tel=(String) objects.get(1);
						}
						order.setTel(tel);
						order.setSmsTel(tel);
						order.setName((String) objects.get(2));
						order.setDealerMessage((String) objects.get(3));
						order.setAddress((String) objects.get(4));
						if(objects.size()>5){
							order.setExProductNames((String) objects.get(5));
						}
						if(objects.size()>6){
							if("普通客户".equals(objects.get(6))){
								order.setDealerType("0");
								order.setState("1");
							}else if("经销商".equals( objects.get(6))){
								order.setDealerType("1");
								order.setState("1");
							}else if("面粉厂".equals( objects.get(6))){
								order.setDealerType("2");
								order.setState("3");
							}else if("食品厂".equals( objects.get(6))){
								order.setDealerType("3");
								order.setState("1");
							}else if("其他".equals( objects.get(6))){
								order.setDealerType("4");
								order.setState("1");
							}
						}
						order.setNumberAttribution(JuHeUtils.getNumberAttribution(tel));
						order.setImportTime(new Date());
						order.setPrintState("0");
						order.setImportType("0");
						UserInfo user= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
						order.setUserId(user.getUserId());
						order.setUserName(user.getName());
						order.setIsTransfer("0");
						order.setNoHeardNum(0);
						Integer iMax=orderService.getOrdernumMax()+1;
						order.setOrderNumInt(iMax);
						order.setOrderNum(orderService.getOrderNum(iMax));
						order.setIsImport("1");
						order.setIsMatch("n");
						orderService.insertOrUpdate(order);

						//记录
						UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
						Log log = new Log();
						log.setUserId(userInfo.getUserId());
						log.setOperation("添加询单");
						log.setType("1");
						log.setContent("添加询单编号为"+order.getOrderNum()+"的询单");
						log.setRequestIp(request.getRemoteAddr());
						log.setCreateTime(new Date());
                        log.setMethod(userInfo.getName());
						logService.insertOrUpdate(log);
					}
				}
				returnInfo.setResult(true);
				returnInfo.setMsg("保存成功");
			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("请选择有数据的表格");
			}
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

	/**
	 * 来电导入
	 * @param order
	 * @param request
	 * @param response
	 */
	@RequestMapping("callImprot")
	public void callImprot(Order order,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo=new ReturnInfo();
		try {
			UserInfo userInfo= (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
			order.setId(null);
			order.setNumberAttribution(JuHeUtils.getNumberAttribution(order.getTel()));
			order.setUserId(userInfo.getUserId());
			order.setUserName(userInfo.getName());
			order.setImportTime(new Date());
			order.setPrintState("0");
			order.setImportType("1");
			order.setNoHeardNum(0);
			order.setIsTransfer("0");
			order.setSmsTel(order.getTel());
			Integer iMax=orderService.getOrdernumMax()+1;
			order.setOrderNumInt(iMax);
			order.setOrderNum(orderService.getOrderNum(iMax));
			order.setIsImport("0");
			order.setIsMatch("n");
			order.setMessageType("来电录入");

			String isSave=request.getParameter("isSave");
			if("y".equals(isSave)){
				orderService.insertOrUpdate(order);
				returnInfo.setResult(true);
				returnInfo.setMsg("保存成功");
			}else if ("n".equals(isSave)){
				//判断该用户是否能处理该单
				Order info=new Order();
				info.setObtainUserId(userInfo.getUserId());
				info=orderService.getBySelective(info);
				if(info==null){
					String[] stateArray=orderService.queryUserToUserState(request,"0").split(",");
					if(Arrays.asList(stateArray).contains(order.getState())){
						order.setObtainUserId(userInfo.getUserId());
						//匹配经销商
						if("n".equals(order.getIsMatch()) && StringUtils.isNotBlank(order.getTel())){
							Map<String,String> dealerParams=new HashMap<>();
							dealerParams.put("matchTel",order.getTel());
							PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(dealerParams);
							if(pageInfo.getTotal()==1){
								Map<String,Object> dealer=pageInfo.getList().get(0);
								order.setDealerId((String) dealer.get("id"));
								order.setDealerNum((String) dealer.get("dealer_num"));
								order.setAreaId((String) dealer.get("district_id"));
								Map<String,String> areaParams=new HashMap<>();
								areaParams.put("areaId", (String) dealer.get("district_id"));
								List<Map<String,Object>> areaList=areaService.queryAllAreaList(areaParams);
								order.setAreaName((String) areaList.get(0).get("areaName"));
								order.setDealerType((String)dealer.get("dealer_type"));
								order.setIsMatch("y");
							}else if(pageInfo.getTotal()>1){
								returnInfo.setObj(order.getTel());
							}else {
								//匹配询单
								Map<String,String> areaMap=new HashMap<>();
								areaMap.put("tel",order.getTel());
								areaMap.put("id",order.getId());
								List<Map<String,Object>> areaOrderList=orderService.matchOrderAreaBytel(areaMap);
								if (areaOrderList.size()>0){
									if(StringUtils.isNotBlank((String) areaOrderList.get(0).get("area_id"))){
										order.setAreaName((String) areaOrderList.get(0).get("area_name"));
										order.setAreaId((String) areaOrderList.get(0).get("area_id"));
									}
									order.setDealerType((String) areaOrderList.get(0).get("dealer_type"));
									order.setIsMatch("y");
								}
							}
						}
						//增加调单数量
						User user=userService.get(userInfo.getUserId());
						Date oTime=new Date();
						if(user.getObtainTime()!=null){
							if(DateUtils.getDistanceOfTwoDate(oTime,user.getObtainTime())==0){
								user.setTransferNum(user.getTransferNum()+1);
							}else {
								user.setTransferNum(1);
							}
						}else {
							user.setTransferNum(1);
						}
						user.setObtainTime(oTime);
						userService.update(user);

						orderService.insertOrUpdate(order);
						returnInfo.setResult(true);
						returnInfo.setMsg("保存成功并处理");
					}else {
						orderService.insertOrUpdate(order);
						returnInfo.setResult(true);
						returnInfo.setMsg("保存成功,但无权处理该单");
					}
				}else {
					orderService.insertOrUpdate(order);
					returnInfo.setResult(true);
					returnInfo.setMsg("保存成功,但已有询单无法处理该单");
				}
			}
			//记录
			Log log = new Log();
			log.setUserId(userInfo.getUserId());
			log.setOperation("添加询单");
			log.setType("1");
			log.setContent("添加询单编号为"+order.getOrderNum()+"的询单");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response,returnInfo);

	}

	/**
     * 去留言导入页面
     * @param request
     * @return
     */
    @RequestMapping("toLiuYan.html")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("pc/liuyan");
    	Map<String, String> params = getParamsMap(request);
		String importType=params.get("importType");
		if(StringUtils.isBlank(importType)){
			params.put("importType","0");
		}
		params.put("isImport","1");
    	modelAndView.addObject("params", params);
		PageInfo<Order> pageInfo = orderService.queryListByPage(params);
		modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
	/**
	 * 完善信息
	 * @param request
	 * @param response
	 * @param infoNew
	 * @return
	 */
	@RequestMapping("editSave.html")
	public void editSave(Order infoNew,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			Order info=new Order();
			info.setObtainUserId(userInfo.getUserId());
			info=orderService.getBySelective(info);
			if(info!=null){
				infoNew.setId(info.getId());
				if(StringUtils.isNotBlank(infoNew.getAreaName()) && StringUtils.isNotBlank(infoNew.getAreaId())){
					Map<String,String> queryMap=new HashMap<>();
					queryMap.put("areaId",infoNew.getAreaId());
					List<Map<String,Object>> mapList=areaService.queryAllAreaList(queryMap);
					if(mapList!=null && mapList.size()==1){
						infoNew.setAreaName((String) mapList.get(0).get("areaName"));
					}
				}else {
					infoNew.setAreaName("");
					infoNew.setAreaId("");
				}
				orderService.updateOrder(infoNew,userInfo);
				returnInfo.setResult(true);
				returnInfo.setMsg("保存成功");

				//记录
				Log log = new Log();
				log.setUserId(userInfo.getUserId());
				log.setOperation("修改询单");
				log.setType("1");
				log.setContent("修改询单编号为"+orderService.get(infoNew.getId()).getOrderNum()+"的询单");
				log.setRequestIp(request.getRemoteAddr());
				log.setCreateTime(new Date());
                log.setMethod(userInfo.getName());
				logService.insertOrUpdate(log);

			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("请先调单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
	}
	/**
	 * 删除订单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData.html")
	public void deleteData(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		try {
		    Give give=new Give();
		    give.setOrderId(id);
		    List<Give> giveList=giveService.queryBySelective(give);
		    if(giveList!=null && giveList.size()>0){
                returnInfo.setResult(false);
                returnInfo.setMsg("此询单已有赠送记录不能删除");
            }else {
                orderService.deleteById(id);
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

	/**
	 * 待处理页面
	 * @param request
	 * @return
	 */
	@RequestMapping("toMarket.html")
	public ModelAndView toMarket(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("pc/shichang");
		Map<String, String> params = getParamsMap(request);
		String isMarket=params.get("isMarket");
		if(StringUtils.isBlank(isMarket)){
			params.put("isMarket","1");
		}
		String dclState;
		if(StringUtils.isNotBlank(params.get("isPrint"))){
			dclState=orderService.queryUserToUserState(request,"1");
		}else {
			dclState=orderService.queryUserToUserState(request,"0");
		}
		params.put("dclState",dclState);
		modelAndView.addObject("params", params);

		PageInfo<Order> pageInfo = orderService.queryPendListByPage(params);
		modelAndView.addObject("pageInfo", pageInfo);
		return modelAndView;
	}


	/**
	 * 待处理页面打印状态修改
	 * @param giveId
	 * @param request
	 * @param response
	 */
	@RequestMapping("updateOrderPrint.html")
	public void updateOrderPrint(String giveId,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
        	String orderId=request.getParameter("orderId");
           	Order order=orderService.get(orderId);
           	order.setPrintState("1");
           	orderService.update(order);
            returnInfo.setResult(true);
            returnInfo.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
	}

	/**
	 * 去左上角客户信息页面
	 * @param request
	 * @return
	 */
	@RequestMapping("toLeftTop.html")
	public ModelAndView toLeftTop(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("pc/left_top");
		UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
		Order info=new Order();
		info.setObtainUserId(userInfo.getUserId());
		info=orderService.getBySelective(info);
		//根据手机号查询对应经销商数量
		if(info!=null ){
			if(StringUtils.isNotBlank(info.getTel())){
				if("n".equals(info.getIsMatch())){
					Map<String,String> params=new HashMap<>();
					params.put("matchTel",info.getTel());
					params.put("dealerType","1");
					PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(params);
					info.setTelNum(pageInfo.getTotal());
				}else {
					info.setTelNum((long) 1);
				}
			}else {
				info.setTelNum((long) 0);
			}
			OrderProduct orderProduct=new OrderProduct();
			orderProduct.setOrderId(info.getId());
			List<OrderProduct> orderProductList=orderProductService.queryBySelective(orderProduct);
			StringBuilder sb=new StringBuilder();
			for (OrderProduct product : orderProductList) {
				sb.append(product.getProductId()).append(",");
			}
			info.setProductIds(sb.toString());
		}

		modelAndView.addObject("info", info);

		List<Map<String,Object>> areaList=areaService.queryAreaList();
		modelAndView.addObject("areaList",areaList);

		return modelAndView;
	}

	/**
	 * 询单根据手机号关联经销商数据
	 * @param request
	 * @return
	 */
	@RequestMapping("dealerData.html")
	public ModelAndView dealerData(HttpServletRequest request){
		ModelAndView modelAndView = new ModelAndView("pc/dealerMatchData");
		Map<String,String> params=getParamsMap(request);
		PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(params);
		modelAndView.addObject("pageInfo", pageInfo);
		return modelAndView;
	}

	/**
	 * 询单匹配客户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("matchSave.html")
	public void matchSave(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String dealerId=request.getParameter("dealerId");
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			Order info=new Order();
			info.setObtainUserId(userInfo.getUserId());
			info=orderService.getBySelective(info);
			if(info!=null){
				Dealer dealer=dealerService.get(dealerId);
				info.setDealerId(dealer.getId());
				info.setDealerNum(dealer.getDealerNum());
				info.setAreaId(dealer.getDistrictId());
				Map<String,String> params=new HashMap<>();
				params.put("areaId",dealer.getDistrictId());
				List<Map<String,Object>> areaList=areaService.queryAllAreaList(params);
				info.setAreaName((String) areaList.get(0).get("areaName"));
				info.setDealerType(dealer.getDealerType());
				info.setIsMatch("y");
				orderService.update(info);
				returnInfo.setResult(true);
				returnInfo.setMsg("匹配成功");

				//记录
				Log log = new Log();
				log.setUserId(userInfo.getUserId());
				log.setOperation("询单匹配客户");
				log.setType("1");
				log.setContent("编号为"+info.getOrderNum()+"的询单匹配客户");
				log.setRequestIp(request.getRemoteAddr());
				log.setCreateTime(new Date());
                log.setMethod(userInfo.getName());
				logService.insertOrUpdate(log);

			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("请先调单");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}
		writeJson(response, returnInfo);
	}

	/**
	 * 一键调单
	 * @param request
	 * @param response
	 */
	@RequestMapping("oneKeyOrder.html")
	public void oneKeyOrder(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			Order info=new Order();
			info.setObtainUserId(userInfo.getUserId());
			info=orderService.getBySelective(info);
			if(info==null){
				String[] stateArray=orderService.queryUserToUserState(request,"0").split(",");
				Map<String, Object> params = new HashMap<>();
				params.put("state",stateArray);
				info=orderService.obtainOrder(params);
				if(info!=null){
					info.setObtainUserId(userInfo.getUserId());
					//匹配经销商或询单
					if("n".equals(info.getIsMatch()) && StringUtils.isNotBlank(info.getTel())){
						Map<String,String> dealerParams=new HashMap<>();
						dealerParams.put("matchTel",info.getTel());
						PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(dealerParams);
						if(pageInfo.getTotal()==1){
							Map<String,Object> dealer=pageInfo.getList().get(0);
							info.setDealerId((String) dealer.get("id"));
							info.setDealerNum((String) dealer.get("dealer_num"));
							info.setAreaId((String) dealer.get("district_id"));
							Map<String,String> areaParams=new HashMap<>();
							areaParams.put("areaId", (String) dealer.get("district_id"));
							List<Map<String,Object>> areaList=areaService.queryAllAreaList(areaParams);
							info.setAreaName((String) areaList.get(0).get("areaName"));
							info.setDealerType((String)dealer.get("dealer_type"));
							info.setIsMatch("y");
						}else if(pageInfo.getTotal()>1){
							returnInfo.setObj(info.getTel());
						}else {
							//匹配询单
							Map<String,String> areaMap=new HashMap<>();
							areaMap.put("tel",info.getTel());
							areaMap.put("id",info.getId());
							List<Map<String,Object>> areaOrderList=orderService.matchOrderAreaBytel(areaMap);
							if (areaOrderList.size()>0){
								if(StringUtils.isNotBlank((String) areaOrderList.get(0).get("area_id"))){
									info.setAreaName((String) areaOrderList.get(0).get("area_name"));
									info.setAreaId((String) areaOrderList.get(0).get("area_id"));
								}
								info.setDealerType((String) areaOrderList.get(0).get("dealer_type"));
								info.setIsMatch("y");
							}
						}
					}
					//增加调单数量
					User user=userService.get(userInfo.getUserId());
					Date oTime=new Date();
					if(user.getObtainTime()!=null){
						if(DateUtils.getDistanceOfTwoDate(oTime,user.getObtainTime())==0){
							user.setTransferNum(user.getTransferNum()+1);
						}else {
							user.setTransferNum(1);
						}
					}else {
						user.setTransferNum(1);
					}
					user.setObtainTime(oTime);
					userService.update(user);
					orderService.insertOrUpdate(info);

					//记录
					Log log = new Log();
					log.setUserId(userInfo.getUserId());
					log.setOperation("调单");
					log.setType("1");
					log.setContent("调取询单编号为"+info.getOrderNum()+"的询单");
					log.setRequestIp(request.getRemoteAddr());
					log.setCreateTime(new Date());
                    log.setMethod(userInfo.getName());
					logService.insertOrUpdate(log);

					returnInfo.setResult(true);
					returnInfo.setMsg("调单成功");
				}else{
					returnInfo.setResult(false);
					returnInfo.setMsg("当前无符合条件的询单");
				}
			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("已有调单");
			}
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 转单
	 * @param request
	 * @param response
	 */
	@RequestMapping("giveOrder.html")
	public void giveOrder(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		Map<String,String> map= getParamsMap(request);
		try {
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			Order info=new Order();
			info.setObtainUserId(userInfo.getUserId());
			info=orderService.getBySelective(info);
			if(info!=null){
				String state=map.get("state");
				if(!"7".equals(state) && !"8".equals(state) && !"9".equals(state) && !"10".equals(state) && info.getState().equals(state)){
					info.setIsTransfer("-1");
				}else {
					info.setIsTransfer("1");
				}
				if("10".equals(state)){
					if(info.getNoHeardNum()==0){
						info.setTransferTime(DateUtils.addDateMinutWithMinutes(new Date(),30));
						info.setNoHeardNum(1);
					}else if(info.getNoHeardNum()==1){
						info.setTransferTime(DateUtils.addDateMinutWithMinutes(new Date(),30));
						info.setNoHeardNum(2);
					}else if(info.getNoHeardNum()==2){
						info.setTransferTime(DateUtils.addDateMinutWithMinutes(new Date(),30));
						info.setNoHeardNum(3);
					}else if(info.getNoHeardNum()==3){
						info.setTransferTime(new Date());
						info.setState("10");
					}
				}else {

					info.setTransferTime(new Date());
					info.setState(state);
				}
				info.setTransferManName(userInfo.getName());
				info.setTransferManId(userInfo.getUserId());
				info.setUserMessage(map.get("userMessage"));

				User user=userService.get(info.getObtainUserId());
				userService.insertOrUpdate(user);

				info.setObtainUserId("");
				info.setIsImport("1");
				orderService.insertOrUpdate(info);

				//记录
				Log log = new Log();
				log.setUserId(userInfo.getUserId());
				log.setOperation("转单");
				log.setType("1");
				log.setContent("转走询单编号为"+info.getOrderNum()+"的询单");
				log.setRequestIp(request.getRemoteAddr());
				log.setCreateTime(new Date());
                log.setMethod(userInfo.getName());
				logService.insertOrUpdate(log);
			}


			returnInfo.setResult(true);
			returnInfo.setMsg("保存成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 咨询产品
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryAllProduct.html")
	public void queryAllProduct(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		Map map = new HashMap();
		try {
			List<ProductType> productTypeList=productTypeService.findAll();
			int maxnum = 0;
			for (ProductType productType : productTypeList) {
				Map<String,String> map1=new HashMap<>();
				map1.put("typeId",productType.getId());
				List<Product> productList=productService.queryList(map1);
				productType.setProductList(productList);

				if(productList.size()>maxnum){
					maxnum = productList.size();
				}
			}
			map.put("list",productTypeList);
			map.put("maxnum",maxnum);
			returnInfo.setObj(map);
			returnInfo.setResult(true);
			returnInfo.setMsg("查询成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 食品查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryAllFood.html")
	public void queryAllFood(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			Map<String,String> params=getParamsMap(request);
			List<FoodDTO> foodList=foodService.queryListByMapDouble(params);
			returnInfo.setObj(foodList);
			returnInfo.setResult(true);
			returnInfo.setMsg("查询成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 去操作记录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("toChangeRecord.html")
	public ModelAndView toChangeRecord(HttpServletRequest request){
		ModelAndView modelAndView=new ModelAndView("pc/jiben");
		Map<String,String> params=getParamsMap(request);
		String orderTelStr = "";
		if(StringUtils.isNotBlank(params.get("orderTel"))){
			orderTelStr += params.get("orderTel") + ",";
		}
		Dealer dealer=dealerService.get(params.get("dealerId"));
		if(null != dealer){
			if(StringUtils.isNotBlank(dealer.getRegisterTel())){
				orderTelStr += dealer.getRegisterTel() + ",";
			}
			if(StringUtils.isNotBlank(dealer.getSmsTel())){
				orderTelStr += dealer.getSmsTel() + ",";
			}
			if(StringUtils.isNotBlank(dealer.getDeliveryTel())){
				orderTelStr += dealer.getDeliveryTel() + ",";
			}
			if(StringUtils.isNotBlank(dealer.getOtherTel())){
				orderTelStr += dealer.getOtherTel() + ",";
			}
//			orderTelStr += dealer.getSmsTel() + ",";
//			orderTelStr += dealer.getDeliveryTel() + ",";
//			orderTelStr += dealer.getOtherTel() + ",";
		}

//		//查当前订单id
//		UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
//		Order info=new Order();
//		info.setObtainUserId(userInfo.getUserId());
//		info=orderService.getBySelective(info);
//		if(info!=null && StringUtils.isBlank(params.get("orderTel"))){
//			params.put("orderTel",info.getTel());
//		}
		String orderId = params.get("orderId");
		if(com.cccuu.project.utils.StringUtils.isNotBlank(orderId)){
			Order order = orderService.get(orderId);
			orderTelStr += order.getTel() + ",";
			orderTelStr += order.getSmsTel() + ",";
			params.put("type","0");//询单客户的交往记录
		}else {
			params.put("type","1");//查询客户的交往记录
		}
		orderTelStr = orderTelStr.substring(0,orderTelStr.length() -1);
		params.put("orderTelStr",orderTelStr);

		Area area=new Area();
		if(dealer!=null){
			area=areaService.get(dealer.getDistrictId());
		}
		modelAndView.addObject("area",area);
		modelAndView.addObject("dealer",dealer);
		modelAndView.addObject("params",params);
		return modelAndView;
	}

	/**
	 * 查询操作记录页面数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("saveChangeRecord.html")
	public void saveChangeRecord(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			OperationRecord operationRecord;
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			if(StringUtils.isNotBlank(id)){
				operationRecord = operationRecordService.get(id);
				if(null == operationRecord){
					operationRecord = new OperationRecord();
				}
			}else {
				operationRecord = new OperationRecord();
				String orderId = request.getParameter("orderId");
				String orderTel = request.getParameter("orderTel");
				operationRecord.setOrderId(orderId);
				operationRecord.setOrderTel(orderTel);
				operationRecord.setCreateTime(new Date());
			}
			String dealerId = request.getParameter("dealerId");
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			operationRecord.setContent(content);
			operationRecord.setUserId(userInfo.getUserId());
			operationRecord.setUserName(userInfo.getUserName());
			operationRecord.setIsImport("0");
			operationRecord.setReserved1(dealerId);
//			operationRecord.setIsImportTime(new Date());
			operationRecordService.insertOrUpdate(operationRecord);
			returnInfo.setResult(true);
			returnInfo.setMsg("添加成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		} finally {
			this.writeJson(response, returnInfo);
		}
	}
	/**
	 * 查询操作记录页面数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryChangeRecord.html")
	public ModelAndView queryChangeRecord(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView=new ModelAndView("pc/jibenData");
		Map<String,String> params=getParamsMap(request);
		PageInfo<Map<String,Object>> pageInfo=operationRecordService.queryListByPage(params);
		modelAndView.addObject("params", params);
		modelAndView.addObject("pageInfo", pageInfo);
		return modelAndView;
	}

    /**
     * 删除交往记录
     *
     * @param request
     * @param response
     */
    @RequestMapping("delrecord.html")
    public void delrecord(HttpServletRequest request, HttpServletResponse response) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String id = request.getParameter("id");
            operationRecordService.deleteById(id);
            returnInfo.setResult(true);
            returnInfo.setMsg("删除成功");
        } catch (Exception e) {
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        } finally {
            this.writeJson(response, returnInfo);
        }
    }

    /**
     * 获取经销商根据id
     *
     * @param request
     * @param response
     */
    @RequestMapping("getDealerById.html")
    public void getDealerById(HttpServletRequest request, HttpServletResponse response) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String id = request.getParameter("id");
            Dealer dealer = dealerService.get(id);
            List<String> phone = Arrays.asList(dealer.getOtherTel().split(","));
            returnInfo.setObj(phone);
            returnInfo.setResult(true);
            returnInfo.setMsg("删除成功");
        } catch (Exception e) {
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        } finally {
            this.writeJson(response, returnInfo);
        }
    }

    /**
     * 修改其他手机号
     *
     * @param request
     * @param response
     */
    @RequestMapping("updateOtherPhoneById.html")
    public void updateOtherPhoneById(HttpServletRequest request, HttpServletResponse response) {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            Map map = getParamsMap(request);
            String id = request.getParameter("xiugaiid");
            String otherphone = (String) map.get("otherphone");


            boolean ishas = true;
            //循环去掉逗号
            while (ishas) {
                if (otherphone.indexOf(",,") == -1) {
                    ishas = false;
                    continue;
                }
                otherphone = otherphone.replace(",,", ",");//如果有空的去掉
            }
            if (",".equals(otherphone)) otherphone = "";
            if (!"".equals(otherphone)) {
                List list = Arrays.asList(otherphone.split(","));
                Set set = new HashSet(list);
                if (list.size() > set.size()) {
                    returnInfo.setResult(false);
                    returnInfo.setMsg("输入的号码有相同的");
                    writeJson(response, returnInfo);
                    return;
                }
            }
//            List<Map> list = dealerService.isHasPhone(otherphone.split(","), id);
//            if (list != null && list.size() > 0) {
//                returnInfo.setResult(false);
//                returnInfo.setMsg("输入的手机号码中有已经被使用的");
//                writeJson(response, returnInfo);
//                return;
//            }

            Dealer dealer = dealerService.get(id);
            dealer.setOtherTel(otherphone);
            dealerService.update(dealer);

            returnInfo.setObj(otherphone);
            returnInfo.setResult(true);
            returnInfo.setMsg("修改成功");

			//记录
			Log log = new Log();
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
			log.setUserId(userInfo.getUserId());
			log.setOperation("修改用户其他号码");
			log.setType("1");
			log.setContent("修改用户名为"+dealer.getName()+"的其他号码");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
        } catch (Exception e) {
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        } finally {
            this.writeJson(response, returnInfo);
        }
    }


    /**
     * 生成编号
     *
     * @param request
     * @param response
     */
    @RequestMapping("shengchengcode.html")
    public void shengchengcode(HttpServletRequest request, HttpServletResponse response) {
        ReturnInfo returnInfo = new ReturnInfo();
        String id = request.getParameter("id");
        Dealer dealer = dealerService.get(id);
        try {
            //生成编号
            int maxnum = dealerService.codeNum();
            dealer.setDealerNumInt(++maxnum);
            //拼0
            String str = "H";
            if (maxnum < 10) str += "000" + maxnum;
            if (maxnum >= 10 && maxnum < 100) str += "00" + maxnum;
            if (maxnum >= 100 && maxnum < 1000) str += "0" + maxnum;
            if (maxnum >= 1000 && maxnum < 10000) str += maxnum;
            dealer.setIshezuo("y");
            dealer.setDealerNum(str);
            dealerService.update(dealer);

            //如果有订单并且经销商id相等则把编号存入订单
            UserInfo userInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
            Order info = new Order();
            info.setObtainUserId(userInfo.getUserId());
            info = orderService.getBySelective(info);
            if (info != null) {
                if (id.equals(info.getDealerId())) {
                    info.setDealerNum(str);
                    orderService.update(info);
                }
            }

			//记录
			Log log = new Log();
			log.setUserId(userInfo.getUserId());
			log.setOperation("为客户生成编号");
			log.setType("1");
			log.setContent("为姓名为"+dealer.getName()+"的客户生成编号");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);

            returnInfo.setResult(true);
            returnInfo.setMsg("生成成功");
            returnInfo.setObj(str);
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg("生成编号出现错误了");
        }

        this.writeJson(response, returnInfo);
    }

	/**
	 * 生成编号
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping("delNum.html")
	public void delNum(HttpServletRequest request, HttpServletResponse response) {
		ReturnInfo returnInfo = new ReturnInfo();
		String id = request.getParameter("id");
		try {
			Dealer dealer = dealerService.get(id);
			//取消编号
			dealer.setReserved1(dealer.getDealerNum());
			dealer.setDealerNumInt(0);
			dealer.setIshezuo("n");
			dealer.setDealerNum("");
			dealerService.update(dealer);

			//如果有订单并且经销商id相等则把编号存入订单
			UserInfo userInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
			Order info = new Order();
			info.setObtainUserId(userInfo.getUserId());
			info = orderService.getBySelective(info);
			if (info != null) {
				if (id.equals(info.getDealerId())) {
					info.setDealerNum("");
					orderService.update(info);
				}
			}

			//记录
			Log log = new Log();
			log.setUserId(userInfo.getUserId());
			log.setOperation("为客户取消编号");
			log.setType("1");
			log.setContent("为姓名为"+dealer.getName()+"的客户取消编号");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);

			returnInfo.setResult(true);
			returnInfo.setMsg("取消成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg("取消编号出现错误了");
		}

		this.writeJson(response, returnInfo);
	}


	/**
	 * 查单数据
	 * @param request
	 * @return
	 */
	@RequestMapping("queryOrderData.html")
	public ModelAndView queryOrderData(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("pc/cdData");
		Map<String, String> params = getParamsMap(request);
		String dclState;
		if(StringUtils.isNotBlank(params.get("isPrint"))){
			dclState=orderService.queryUserToUserState(request,"1");
		}else {
			dclState=orderService.queryUserToUserState(request,"0");
		}
		params.put("dclState",dclState);
		params.put("isxdcx","y");
		modelAndView.addObject("params", params);
		PageInfo<Map<String,Object>> pageInfo = orderService.queryOrderData(params);
		modelAndView.addObject("pageInfo", pageInfo);
		return modelAndView;
	}

    /**
     * 查询当前有没有单
     *
     * @param request
     * @param response
     */
    @RequestMapping("chadan.html")
    public void chadan(HttpServletRequest request, HttpServletResponse response) {
        ReturnInfo returnInfo = new ReturnInfo();
        String num = request.getParameter("num");
        try {
            //先查有没有正在进行的单
            UserInfo userInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
            Order info = new Order();
            info.setObtainUserId(userInfo.getUserId());
            info = orderService.getBySelective(info);
            if (info != null) {
                returnInfo.setResult(false);
                returnInfo.setMsg("您当前有正在进行的单子");
            } else {
                String[] stateArray = orderService.queryUserToUserState(request,"0").split(",");
                Map<String, Object> params = new HashMap<>();
                params.put("state", stateArray);
                params.put("num", num);

                //再查输入的单子存不存在,被别人接了没有
                Map map = orderService.isOrderByNum(params);
                if(map!=null&&map.size()>0){
                    Order order = orderService.get((String) map.get("id"));
                    order.setObtainUserId(userInfo.getUserId());
					//匹配经销商
					if("n".equals(order.getIsMatch()) && StringUtils.isNotBlank(order.getTel())){
						Map<String,String> dealerParams=new HashMap<>();
						dealerParams.put("matchTel",order.getTel());
						PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(dealerParams);
						if(pageInfo.getTotal()==1){
							Map<String,Object> dealer=pageInfo.getList().get(0);
							order.setDealerId((String) dealer.get("id"));
							order.setDealerNum((String) dealer.get("dealer_num"));
							order.setAreaId((String) dealer.get("district_id"));
							Map<String,String> areaParams=new HashMap<>();
							areaParams.put("areaId", (String) dealer.get("district_id"));
							List<Map<String,Object>> areaList=areaService.queryAllAreaList(areaParams);
							order.setAreaName((String) areaList.get(0).get("areaName"));
							order.setDealerType((String)dealer.get("dealer_type"));
							order.setIsMatch("y");
						}else if(pageInfo.getTotal()>1){
							returnInfo.setObj(order.getTel());
						}else {
							//匹配询单
							Map<String,String> areaMap=new HashMap<>();
							areaMap.put("tel",order.getTel());
							areaMap.put("id",order.getId());
							List<Map<String,Object>> areaOrderList=orderService.matchOrderAreaBytel(areaMap);
							if (areaOrderList.size()>0){
								if(StringUtils.isNotBlank((String) areaOrderList.get(0).get("area_id"))){
									order.setAreaName((String) areaOrderList.get(0).get("area_name"));
									order.setAreaId((String) areaOrderList.get(0).get("area_id"));
								}
								order.setDealerType((String) areaOrderList.get(0).get("dealer_type"));
								order.setIsMatch("y");
							}
						}
					}
					//增加调单数量
					User user=userService.get(userInfo.getUserId());
					Date oTime=new Date();
					if(user.getObtainTime()!=null){
						if(DateUtils.getDistanceOfTwoDate(oTime,user.getObtainTime())==0){
							user.setTransferNum(user.getTransferNum()+1);
						}else {
							user.setTransferNum(1);
						}
					}else {
						user.setTransferNum(1);
					}
					user.setObtainTime(oTime);
					userService.update(user);

                    orderService.update(order);
                    returnInfo.setResult(true);
					returnInfo.setMsg("查单成功");
					//记录
					Log log = new Log();
					log.setUserId(userInfo.getUserId());
					log.setOperation("调单");
					log.setType("1");
					log.setContent("调取询单编号为"+order.getOrderNum()+"的询单");
					log.setRequestIp(request.getRemoteAddr());
					log.setCreateTime(new Date());
                    log.setMethod(userInfo.getName());
					logService.insertOrUpdate(log);
                }else{
                    returnInfo.setMsg("输入询单不存在,或单子正在处理");
                    returnInfo.setResult(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg("查询当前有没有单出现错误");
        } finally {
            this.writeJson(response, returnInfo);
        }
    }

	/**
	 * 匹配客户
	 * @param request
	 * @param response
	 */
	@RequestMapping("pipei.html")
	public void pipei(HttpServletRequest request, HttpServletResponse response) {
		ReturnInfo returnInfo = new ReturnInfo();
		String did = request.getParameter("did");
		try {
			//先查有没有正在进行的单
			UserInfo userInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
			Order info = new Order();
			info.setObtainUserId(userInfo.getUserId());
			info = orderService.getBySelective(info);
			if (info == null) {
                returnInfo.setResult(false);
                returnInfo.setMsg("请先选择一个单");
            }else{
                if(StringUtils.isBlank(info.getDealerId())){
                    Dealer dealer = dealerService.get(did);
                    //补充订单表
                    info.setDealerId(dealer.getId());
                    info.setDealerNum(dealer.getDealerNum());
                    info.setDealerType(dealer.getDealerType());
                    orderService.update(info);
                    //补充经销商其他电话
                    String str = dealer.getOtherTel();
                    if(StringUtils.isNotBlank(info.getTel())) {
                        if ("".equals(str)) {
                            str = info.getTel();
                        } else {
                            str += "," + info.getTel();
                        }
                    }
                    dealer.setOtherTel(str);
                    dealerService.update(dealer);
                    returnInfo.setResult(true);
                    returnInfo.setMsg("匹配成功");

                    //记录
                    Log log = new Log();
                    log.setUserId(userInfo.getUserId());
                    log.setOperation("匹配客户");
                    log.setType("1");
                    log.setContent("为询单编号为"+info.getOrderNum()+"的询单匹配客户");
                    log.setRequestIp(request.getRemoteAddr());
                    log.setCreateTime(new Date());
                    log.setMethod(userInfo.getName());
                    logService.insertOrUpdate(log);
                }else{
                    returnInfo.setResult(false);
                    returnInfo.setMsg("该项询单已经匹配过经销商");
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
		}

		this.writeJson(response, returnInfo);
	}


//	@RequestMapping("uploadExcel.html")
//	public void uploadExcel(HttpServletRequest request, HttpServletResponse response) {
//		ReturnInfo returnInfo = new ReturnInfo();
//		long start = System.currentTimeMillis();
//		try {
//			List<Dealer> dealerList=dealerService.findAll();
//			List<Product> productList=productService.findAll();
//			List<DealerProduct> dealerProductList=new ArrayList<>();
//			for (Product product : productList) {
//				for (Dealer dealer : dealerList) {
//					DealerProduct dealerProduct=new DealerProduct();
//					dealerProduct.setId(UniqueIDGen.getUniqueID()+"");
//					dealerProduct.setProductId(product.getId());
//					dealerProduct.setGiveNum(0);
//					dealerProduct.setDealerGiveNum(0);
//					dealerProduct.setCompanyGiveNum(0);
//					dealerProduct.setNoticeNum(0);
//					dealerProduct.setDealerId(dealer.getId());
//					dealerProduct.setIsImport("0");
//					dealerProduct.setIsImportPurchase("0");
//					dealerProductList.add(dealerProduct);
//				}
//			}
//			if(dealerProductList.size()>0){
//				dealerProductService.addMulti(dealerProductList);
//			}
//			//单位市场关联产品
//			List<Area> areaList=areaService.findAll();
//			List<Product> productList=productService.findAll();
//			List<AreaProduct> areaProductList=new ArrayList<>();
//			for (Product product : productList) {
//
//				for (Area area : areaList) {
//					AreaProduct areaProduct=new AreaProduct();
//					areaProduct.setId(UniqueIDGen.getUniqueID()+"");
//					areaProduct.setAreaId(area.getId());
//					areaProduct.setProductId(product.getId());
//					areaProduct.setIsImport("0");
//					areaProductList.add(areaProduct);
//				}
//			}
//			if(areaProductList.size()>10000){
//				List<AreaProduct> list1=new ArrayList<>();
//				List<AreaProduct> list2=new ArrayList<>();
//				List<AreaProduct> list3=new ArrayList<>();
//				List<AreaProduct> list4=new ArrayList<>();
//				List<AreaProduct> list5=new ArrayList<>();
//				List<AreaProduct> list6=new ArrayList<>();
//				Integer i=1;
//				for (AreaProduct areaProduct : areaProductList) {
//					if(i<20000){
//						list1.add(areaProduct);
//					}else if(i>=20000 && i<40000){
//						list2.add(areaProduct);
//					}else if(i>=40000 && i<60000){
//						list3.add(areaProduct);
//					}else if(i>=60000 && i<80000){
//						list4.add(areaProduct);
//					}else if(i>=80000 && i<100000){
//						list5.add(areaProduct);
//					}else if(i>=100000){
//						list6.add(areaProduct);
//					}
//					i++;
//				}
//				areaProductService.addMulti(list1);
//				areaProductService.addMulti(list2);
//				areaProductService.addMulti(list3);
//				areaProductService.addMulti(list4);
//				areaProductService.addMulti(list5);
//				areaProductService.addMulti(list6);
//				System.out.println("长度："+areaProductList.size());
//				System.out.println("长度："+list1.size()+list3.size()+list4.size()+list5.size()+list6.size()+list2.size());
//			}
//			long end = System.currentTimeMillis();
//			System.out.println("耗时："+(end-start)+"ms");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		this.writeJson(response, returnInfo);
//	}

	/**
	 * 获取当前询单
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryNowOrder.html")
	public void queryNowOrder(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
			Order info=new Order();
			info.setObtainUserId(userInfo.getUserId());
			info=orderService.getBySelective(info);
			returnInfo.setObj(info);
			returnInfo.setResult(true);
			returnInfo.setMsg("查询成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}

	/**
	 * 获取当前询单
	 * @param request
	 * @param response
	 */
	@RequestMapping("queryNumberAttribution.html")
	public void queryNumberAttribution(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String phone=request.getParameter("phone");
			String result=JuHeUtils.getNumberAttribution(phone);
			returnInfo.setObj(result);
			returnInfo.setResult(true);
			returnInfo.setMsg("查询成功");
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
	}
}