package com.cccuu.project.controller.pc.dealer;

import com.alibaba.druid.support.json.JSONUtils;
import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.area.AreaProduct;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.dealerproduct.DealerProduct;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.operationrecord.OperationRecord;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.service.area.AreaProductService;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.dealerproduct.DealerProductService;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.service.depart.UserDepartService;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.operationrecord.OperationRecordService;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.service.sms.SmsService;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package com.cccuu.project.controller.pc.dealer
 * @Author ke
 * @DATE 2017/9/29.
 */
@Controller
@RequestMapping("dealerPc")
public class DealerPcController extends BaseController{

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
    private AreaProductService areaProductService;
    @Resource
    private DealerProductService dealerProductService;
    @Resource
    private LogService logService;
    @Resource
    private OperationRecordService operationRecordService;
    @Resource
    private SmsService smsService;
    /**
     * 查询单位市场商家数量
     * @param request
     * @param response
     */
    @RequestMapping("queryAreaNum.html")
    public void queryAreaNum(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String areaId=request.getParameter("areaId");
            if(StringUtils.isBlank(areaId)){
                areaId = (String) getSessionAttribute(request,"areaId");
            }
            setSessionAttribute(request,"areaId",areaId);
            Map<String,String> params=new HashMap<>();
            Area area=new Area();
            if(StringUtils.isNotBlank(areaId)){
                area=areaService.get(areaId);
            }else {
                area.setAreaName("全部");
            }
                params.put("areaId",areaId);
                params.put("dealerType","1");
                params.put("cooperationState","1");
                Integer hzNum=dealerService.countDealer(params);
                area.setHzNum(hzNum);
                params.put("cooperationState","0");
                Integer whzNum=dealerService.countDealer(params);
                area.setWhzNum(whzNum);
                params.put("dealerType","2");
                params.put("cooperationState","10");
                Integer spNum=dealerService.countDealer(params);
                area.setSpNum(spNum);
                params.put("dealerType","3");
                Integer mfNum=dealerService.countDealer(params);
                area.setMfNum(mfNum);

            returnInfo.setObj(area);
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
     * 去单位市场页面
     * @param request
     * @return
     */
    @RequestMapping("toAreaMarket.html")
    public ModelAndView toAreaMarket(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/danwei");
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> areaList=areaService.queryAreaList();
        UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
        Order info=new Order();
        info.setObtainUserId(userInfo.getUserId());
        info=orderService.getBySelective(info);
        if(StringUtils.isBlank(params.get("areaId"))){
            if(info!=null && StringUtils.isNotBlank(info.getAreaId())){
                Area area3=areaService.get(info.getAreaId());
                params.put("areaId",area3.getId());
            }else {
                String areaId = (String) getSessionAttribute(request,"areaId");
                if(StringUtils.isNotBlank(areaId)){
                    Area area3=areaService.get(areaId);
                    params.put("areaId",area3.getId());
                }else{
                    if(areaList!=null&&areaList.size()>0) {
                        params.put("areaId", (String) areaList.get(0).get("id"));
                    }
                }

            }
        }
        String areaId = params.get("areaId");
        String ishezuo = params.get("ishezuo");
        if(com.cccuu.project.utils.StringUtils.isBlank(ishezuo)){
            params.put("ishezuo","y");
        }
        setSessionAttribute(request,"areaId",areaId);
        modelAndView.addObject("info",info);
        modelAndView.addObject("areaList",areaList);
        if(StringUtils.isBlank(params.get("sortNum"))){
            params.put("sortNum","asc");
        }
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 查询单位市场页面数据
     * @param request
     * @return
     */
    @RequestMapping("toAreaMarketData.html")
    public ModelAndView toTest(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pc/danweiData");
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> productList=areaProductService.queryListInArea(params);
        List<Dealer> dealerList=dealerService.queryListByMapDouble(params);
        modelAndView.addObject("productList",productList);
        modelAndView.addObject("dealerList",dealerList);
        return modelAndView;
    }

    /**
     * 单位市场通知经销商
     * @param request
     * @param response
     */
    @RequestMapping("tzDealer.html")
    public void tzDealer(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        String dealerId=request.getParameter("dealerId");
        String orderId=request.getParameter("orderId");
        String smsContent=request.getParameter("smsContent");
        try {
            Order order=orderService.get(orderId);
            order.setConsultDealerId(dealerId);
            orderService.update(order);
            //给经销商发短信
            Dealer dealer=dealerService.get(dealerId);
            if(StringUtils.isNotBlank(smsContent) && StringUtils.isNotBlank(dealer.getSmsTel()) ){
                smsService.getsmsCodeByPhone(dealer.getSmsTel(),smsContent,request);
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
     * 去单个经销商页面
     * @param request
     */
    @RequestMapping("toDealerDetail.html")
    public ModelAndView toDealerDetail(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/dange");
        Map<String,String> params=getParamsMap(request);
        Dealer dealer=dealerService.get(params.get("dealerId"));
        Area area=areaService.get(dealer.getDistrictId());
        modelAndView.addObject("area",area);
        modelAndView.addObject("dealer",dealer);
        modelAndView.addObject("params",params);
        UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
        Order info=new Order();
        info.setObtainUserId(userInfo.getUserId());
        info=orderService.getBySelective(info);
        modelAndView.addObject("info",info);
        return modelAndView;
    }

    /**
     * 单个经销商页面数据
     * @param request
     * @param response
     */
    @RequestMapping("toDealerDetailData.html")
    public ModelAndView toDealerDetailData(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView("pc/dangeData");
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> infoList=dealerProductService.queryProductByDealerId(params);
        modelAndView.addObject("infoList",infoList);
        return modelAndView;
    }

    /**
     * 去经销商选择页面
     * @param request
     * @return
     */
    @RequestMapping("toDealerChoose.html")
    public ModelAndView toDealerChoose(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/jingxiaoshang");
        //单位市场
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> areaList=areaService.queryAreaList();
        if(StringUtils.isBlank(params.get("areaId"))){
            UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
            Order info=new Order();
            info.setObtainUserId(userInfo.getUserId());
            info=orderService.getBySelective(info);
            if(info!=null && StringUtils.isNotBlank(info.getAreaId())){
                params.put("areaId",info.getAreaId());
            }else {
                if(areaList != null&&areaList.size()>0) {
                    params.put("areaId", (String) areaList.get(0).get("id"));
                }
            }
        }
        Area area=areaService.get(params.get("areaId"));
        modelAndView.addObject("area",area);
        modelAndView.addObject("areaList",areaList);
        //产品和经销商
        List<Map<String,Object>> productList=areaProductService.queryListInArea(params);
        List<Dealer> dealerList=dealerService.queryListByMapDouble(params);
        modelAndView.addObject("productList",productList);
        String productListJson = JSONUtils.toJSONString(productList);
        modelAndView.addObject("productListJson",productListJson);
        modelAndView.addObject("dealerList",dealerList);
        //单个经销商
        if(StringUtils.isBlank(params.get("dealerId"))){
            if(dealerList!=null&&dealerList.size()>0){
                Dealer dealer=dealerList.get(0);
                params.put("dealerId",dealer.getId());
            }
        }
        if(StringUtils.isBlank(params.get("sortNum"))){
            params.put("sortNum","asc");
        }
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 经销商选择单个经销商页面数据
     * @param request
     * @param response
     */
    @RequestMapping("toDealerChooseData.html")
    public ModelAndView toDealerChooseData(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView("pc/jingxiaoshangData");
        Map<String,String> params=getParamsMap(request);
        if(StringUtils.isNotBlank(params.get("dealerId"))) {
            Dealer dealer=dealerService.get(params.get("dealerId"));
            List<Map<String,Object>> infoList=dealerProductService.queryProductByDealerId(params);
            modelAndView.addObject("dealer",dealer);
            modelAndView.addObject("infoList",infoList);
        }

        return modelAndView;
    }

    /**
     * 去客户查询页面
     * @param request
     * @return
     */
    @RequestMapping("toDealerQuery.html")
    public ModelAndView toDealerQuery(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/weihe");
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> areaList=areaService.queryAreaList();
        if(StringUtils.isBlank(params.get("areaId"))){
            UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
            Order info=new Order();
            info.setObtainUserId(userInfo.getUserId());
            info=orderService.getBySelective(info);
            if(info!=null && StringUtils.isNotBlank(info.getAreaId())){
                Area area3=areaService.get(info.getAreaId());
                params.put("areaId",area3.getId());
            }else {
                    params.put("areaId", "");
            }
        }
        modelAndView.addObject("areaList",areaList);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 客户查询页面数据
     * @param request
     * @return
     */
    @RequestMapping("dealerData.html")
    public ModelAndView dealerData(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("pc/weiheData");
        Map<String,String> params=getParamsMap(request);
         PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerList(params);
        modelAndView.addObject("pageInfo", pageInfo);
        return modelAndView;
    }

    /**
     * 发送短信
     * @param request
     * @param response
     */
    @RequestMapping("sendSms.html")
    public void sendSms(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        String tel=request.getParameter("sendTel");
        String content=request.getParameter("sendContent");
        try {
            if(StringUtils.isNotBlank(tel) && StringUtils.isNotBlank(content)){
                returnInfo.setResult(true);
                returnInfo.setMsg("发送成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("发送失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 修改公司通知或经销商赠送
     * @param request
     * @param response
     */
    @RequestMapping("updateJssOrGs.html")
    public void updateJssOrGs(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        Map<String ,String > map = getParamsMap(request);
        try {
            if(StringUtils.isNotBlank(map.get("jssid"))){
                Dealer dealer = dealerService.get(map.get("jssid"));
                map.put("jsscode",dealer.getDealerNum());
            }
            if(StringUtils.isNotBlank(map.get("apid"))){
                areaProductService.updateJssOrGs(map);
                returnInfo.setObj(map.get("jsscode"));
                returnInfo.setResult(true);
                returnInfo.setMsg("修改成功");
            }else {
                AreaProduct areaProduct=new AreaProduct();
                areaProduct.setAreaId(map.get("areaId"));
                areaProduct.setProductId(map.get("productId"));
                if("gs".equals(map.get("type"))){
                    areaProduct.setNoticeDealer(map.get("jsscode"));
                    areaProduct.setNoticeDealerId(map.get("jssid"));
                }else if("jss".equals(map.get("type"))){
                    areaProduct.setGiveDealer(map.get("jsscode"));
                    areaProduct.setGiveDealerId(map.get("jssid"));
                }else if("gsh".equals(map.get("type"))){
                    areaProduct.setNoticeDealer("H0000");
                }
                areaProductService.addAP(areaProduct,map.get("type"));

//                AreaProduct areaProduct=new AreaProduct();
//                areaProduct.setAreaId(map.get("areaId"));
//                areaProduct.setProductId(map.get("productId"));
//                areaProduct=areaProductService.getBySelective(areaProduct);
//                if(areaProduct!=null){
//                    map.put("apid",areaProduct.getId());
//                    areaProductService.updateJssOrGs(map);
//                }else {
//                    AreaProduct newAreaProduct=new AreaProduct();
//                    newAreaProduct.setAreaId(map.get("areaId"));
//                    newAreaProduct.setProductId(map.get("productId"));
//                    if("gs".equals(map.get("type"))){
//                        newAreaProduct.setNoticeDealer(map.get("jsscode"));
//                        newAreaProduct.setNoticeDealerId(map.get("jssid"));
//                    }else if("jss".equals(map.get("type"))){
//                        newAreaProduct.setGiveDealer(map.get("jsscode"));
//                        newAreaProduct.setGiveDealerId(map.get("jssid"));
//                    }else if("gsh".equals(map.get("type"))){
//                        newAreaProduct.setNoticeDealer("H0000");
//                    }
//                    newAreaProduct.setIsImport("0");
//                    newAreaProduct.setAuthorIsImport("0");
//                    areaProductService.insertOrUpdate(newAreaProduct);
//                }
                returnInfo.setObj(map.get("jsscode"));
                returnInfo.setResult(true);
                returnInfo.setMsg("修改成功");
            }
        } catch (Exception e) {
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }finally{
            this.writeJson(response, returnInfo);
         }
    }

    /**
     * 查询产品类别
     * @param response
     */
    @RequestMapping("queryProductType.html")
    public void queryProductType(HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            List<ProductType> productTypeList=productTypeService.findAll();
            returnInfo.setObj(productTypeList);
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
     * 查询类别下产品
     * @param response
     */
    @RequestMapping("queryProduct.html")
    public void queryProduct(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String typeId=request.getParameter("typeId");
            Product product=new Product();
            product.setTypeId(typeId);
            List<Product> productList=productService.queryBySelective(product);
            returnInfo.setObj(productList);
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
     * 赠送根据货号查询产品
     * @param response
     */
    @RequestMapping("queryProductByNum.html")
    public void queryProductByNum(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> params=getParamsMap(request);
            List<Map<String,Object>> productList=productService.queryProductByNum(params);
            returnInfo.setObj(productList);
            returnInfo.setResult(true);
            returnInfo.setMsg("查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    @RequestMapping("updateAPtime.html")
    public void updateAPtime(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String apid=request.getParameter("apid");
            String timeStr = request.getParameter("time");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date time=new Date();
            if(StringUtils.isNotBlank(timeStr)){
                time=simpleDateFormat.parse(timeStr);
            }else {
                time=null;
            }
            String productId=request.getParameter("productId");
            String areaId=request.getParameter("areaId");

            if(StringUtils.isNotBlank(apid)){
                AreaProduct areaProduct = areaProductService.get(apid);
                areaProduct.setEffectTime(time);
                areaProductService.update(areaProduct);
            }else {
                AreaProduct areaProduct=new AreaProduct();
                areaProduct.setAreaId(areaId);
                areaProduct.setProductId(productId);
                areaProduct.setEffectTime(time);
                areaProductService.addAP(areaProduct,"tm");



//                areaProduct=areaProductService.getBySelective(areaProduct);
//                if(areaProduct!=null){
//                    areaProduct.setEffectTime(time);
//                    areaProductService.update(areaProduct);
//                }else {
//                    AreaProduct newAreaProduct=new AreaProduct();
//                    newAreaProduct.setEffectTime(time);
//                    newAreaProduct.setAreaId(areaId);
//                    newAreaProduct.setProductId(productId);
//                    newAreaProduct.setIsImport("0");
//                    newAreaProduct.setAuthorIsImport("0");
//                    newAreaProduct.setNoticeDealer("H0000");
//                    areaProductService.insertOrUpdate(newAreaProduct);
//                }
            }

            returnInfo.setResult(true);
            returnInfo.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 修改总评价或合作情况	0:总评价 1:合作评价
     * @param request
     * @param response
     */
    @RequestMapping("updatePingText.html")
    public void updatePingText(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String ,String > map = getParamsMap(request);
            dealerService.updatePingText(map);
            returnInfo.setResult(true);
            returnInfo.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 修改备注
     * @param request
     * @param response
     */
    @RequestMapping("updateRemark.html")
    public void updateRemark(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String ,String > map = getParamsMap(request);
            dealerProductService.updateRemark(map);
            returnInfo.setResult(true);
            returnInfo.setMsg("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 到经销商信息导入页面
     * @param request
     * @param response
     */
    @RequestMapping("toDealerImport.html")
    public ModelAndView toDealerImport(HttpServletRequest request,HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView("pc/jingxiaoshangdaoru");
        Map<String,String> params=getParamsMap(request);
        if(StringUtils.isBlank(params.get("importType"))){
            params.put("importType","1");
        }
        PageInfo<Map<String,Object>> pageInfo=dealerService.queryListImportByPage(params);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 经销商信息导入
     * @param request
     * @param response
     */
    @RequestMapping("dealerImport.html")
    public void dealerImport(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        //1,经销商总量月均 dealer; 2,经销商品种月均dealerProduct; 3,市场总量月均area;
        // 4,市场品种月均areaProduct; 5,最新进货日期dealerProduct;6,经销商品种授权areaProduct
        //7,交往记录导入

        String importType=request.getParameter("importType");

        //导入经销商及市场各评价以及授权情况前，先清空所有评价及授权情况
        if("6".equals(importType)){
            areaProductService.emptyAuthorDealer();
        }else if ("4".equals(importType)){
            areaProductService.emptyAreaProductMerit();
        }else if ("3".equals(importType)){
            areaService.updateAreaMerit();
        }else if("2".equals(importType)){
            dealerProductService.updateDealerProductMerit();
        }else if ("1".equals(importType)){
            dealerService.updateDealerMerit();
        }

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
                dealerService.updateImport(importType);
                //交往记录
                List<OperationRecord> recordList=new ArrayList<>();
                for (List<Object> objects : listob) {
                    if("1".equals(importType)){
                        if(objects.size()==2){
                            Dealer dealer=new Dealer();
                            dealer.setUuid((String) objects.get(0));
                            dealer=dealerService.getBySelective(dealer);
                            if(dealer!=null){
                                dealer.setOverallMerit((String) objects.get(1));
                                dealer.setReserved1("1");
                                dealer.setImportTime(new Date());
                                dealerService.update(dealer);
                            }
                        }
                    }else if("2".equals(importType)){
                        //查询产品
                        Product product=new Product();
                        product.setProductNum((String) objects.get(1));
                        product.setReserved1("0");
                        product=productService.getBySelective(product);
                        //查询经销商
                        Dealer dealer=new Dealer();
                        dealer.setUuid((String) objects.get(0));
                        dealer=dealerService.getBySelective(dealer);
                        if(product!=null && dealer!=null){
                            DealerProduct dealerProduct=new DealerProduct();
                            dealerProduct.setProductId(product.getId());
                            dealerProduct.setDealerId(dealer.getId());
                            dealerProduct.setComment((String) objects.get(2));
                            dealerProductService.addDP(dealerProduct,"pj");
                        }
                    }else if("3".equals(importType)){
                        if(objects.size()==2){
                            Map<String,String> params=new HashMap<>();
                            params.put("areaName",(String) objects.get(0));
                            params.put("comment",(String) objects.get(1));
                            areaProductService.updateComment(params);
                        }
                    }else if("4".equals(importType)){
                            //查询区域
                            Area area=new Area();
                            area.setLevel(2);
                            area.setAreaName((String) objects.get(0));
                            area=areaService.getBySelective(area);
                            //查询产品
                            Product product=new Product();
                            product.setProductNum((String) objects.get(1));
                            product.setReserved1("0");
                            product=productService.getBySelective(product);
                            if(product!=null && area!=null){
                                AreaProduct areaProduct=new AreaProduct();
                                areaProduct.setAreaId(area.getId());
                                areaProduct.setProductId(product.getId());
                                areaProduct.setComment((String) objects.get(2));
                                areaProductService.addAP(areaProduct,"pj");
                            }
                    }else if("5".equals(importType)){
                        if(objects.size()==3){
                            //查询产品
                            Product product=new Product();
                            product.setProductNum((String) objects.get(1));
                            product.setReserved1("0");
                            product=productService.getBySelective(product);
                            //查询经销商
                            Dealer dealer=new Dealer();
                            dealer.setUuid((String) objects.get(0));
                            dealer=dealerService.getBySelective(dealer);
                            if(product!=null && dealer!=null){
                                DealerProduct dealerProduct=new DealerProduct();
                                dealerProduct.setProductId(product.getId());
                                dealerProduct.setDealerId(dealer.getId());
                                dealerProduct.setLastPurchaseTime(DateUtils.parseDate(objects.get(2)));
                                dealerProductService.addDP(dealerProduct,"jh");
                            }
                        }
                    }else if("6".equals(importType)){
                        //查询经销商
                        Dealer dealer=new Dealer();
                        dealer.setUuid((String) objects.get(0));
                        dealer=dealerService.getBySelective(dealer);
                        //查询产品
                        Product product=new Product();
                        product.setProductNum((String) objects.get(1));
                        product.setReserved1("0");
                        product=productService.getBySelective(product);
                        if(product!=null && dealer!=null){
                            AreaProduct areaProduct = new AreaProduct();
                            areaProduct.setAreaId(dealer.getDistrictId());
                            areaProduct.setProductId(product.getId());
                            areaProduct.setAuthorDealer(dealer.getDealerNum());
                            areaProduct.setAuthorDealerId(dealer.getId());
                            areaProductService.addAP(areaProduct,"sq");
                        }
                    }else if("7".equals(importType)){
                        OperationRecord operationRecord=new OperationRecord();
                        operationRecord.setId(UniqueIDGen.getUniqueID()+"");
                        operationRecord.setOrderTel((String) objects.get(0));
                        operationRecord.setCreateTime(DateUtils.parseDate(objects.get(1)));
                        operationRecord.setContent((String) objects.get(2));
                        operationRecord.setIsImport("1");
                        operationRecord.setIsImportTime(new Date());
                        recordList.add(operationRecord);
                    }
                }
                if("7".equals(importType) && recordList.size()>0){
                    operationRecordService.addMulti(recordList);
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
     * 修改经销商
     * @param dealer
     * @param request
     * @param response
     */
    @RequestMapping("updatedealer.html")
    public void updatedealer(Dealer dealer,HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
//            String[] arry=new String[]{dealer.getRegisterTel()};
//            List<Map> list = dealerService.isHasPhone(arry,dealer.getId());
//            if(list!=null&&list.size()>0){
//                returnInfo.setResult(false);
//                returnInfo.setMsg("注册手机号重复，请重新填写");
//                writeJson(response, returnInfo);
//                return;
//            }
            dealerService.updateSelective(dealer);
            returnInfo.setResult(true);
            returnInfo.setMsg("修改成功");

            Dealer dealer1 = dealerService.get(dealer.getId());
            //记录
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
            Log log = new Log();
            log.setUserId(userInfo.getUserId());
            log.setOperation("修改客户信息");
            log.setType("1");
            log.setContent("修改了客户姓名为"+dealer1.getName()+"的信息");
            log.setRequestIp(request.getRemoteAddr());
            log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
            logService.insertOrUpdate(log);
        }catch (Exception e){
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg("修改经销商出现错误");
        }
        writeJson(response, returnInfo);
    }

    /**
     * 根据条件获取单位市场
     * @param request
     * @param response
     */
    @RequestMapping("queryAllArea.html")
    public void queryAllArea(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            Map<String,String> params=getParamsMap(request);
            List<Map<String,Object>> areaList=areaService.queryAllAreaList(params);
            returnInfo.setObj(areaList);
            returnInfo.setResult(true);
            returnInfo.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        writeJson(response, returnInfo);
    }

    /**
     * 根据条件获取单位市场
     * @param request
     * @param response
     */
    @RequestMapping("queryDealer.html")
    public void queryDealer(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String dealerId=request.getParameter("dealerId");
            Dealer dealer=dealerService.get(dealerId);
            Area area=areaService.get(dealer.getDistrictId());
            returnInfo.setObj(area.getAreaName());
            returnInfo.setResult(true);
            returnInfo.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        writeJson(response, returnInfo);
    }

    /**
     * 单个经销商新增操作纪录
     * @param request
     * @param response
     */
    @RequestMapping("addRecord.html")
    public void addRecord(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            String tel=request.getParameter("tel");
            String content=request.getParameter("content");
            UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
            OperationRecord operationRecord=new OperationRecord();
            operationRecord.setCreateTime(new Date());
            operationRecord.setOrderTel(tel);
            operationRecord.setUserId(userInfo.getUserId());
            operationRecord.setUserName(userInfo.getName());
            operationRecord.setContent(content);
            operationRecordService.insertOrUpdate(operationRecord);
            returnInfo.setResult(true);
            returnInfo.setMsg("保存成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        writeJson(response, returnInfo);
    }
}
