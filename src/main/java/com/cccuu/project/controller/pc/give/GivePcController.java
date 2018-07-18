package com.cccuu.project.controller.pc.give;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.give.Give;
import com.cccuu.project.model.giveproduct.GiveProduct;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.model.product.Product;
import com.cccuu.project.model.product.ProductType;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.give.GiveService;
import com.cccuu.project.service.giveproduct.GiveProductService;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.service.product.ProductService;
import com.cccuu.project.service.product.ProductTypeService;
import com.cccuu.project.service.sms.SmsService;
import com.cccuu.project.utils.*;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Package com.cccuu.project.controller.pc.give
 * @Author ke
 * @DATE 2017/10/12.
 */
@Controller
@RequestMapping("givePc")
public class GivePcController extends BaseController {

    @Resource
    private GiveService giveService;
    @Resource
    private OrderService orderService;
    @Resource
    private AreaService areaService;
    @Resource
    private ProductTypeService productTypeService;
    @Resource
    private ProductService productService;
    @Resource
    private DealerService dealerService;
    @Resource
    private SmsService smsService;
    @Resource
    private GiveProductService giveProductService;

    /**
     * 去赠送审核页面
     * @param request
     * @return
     */
    @RequestMapping("toGiveExamine.html")
    public ModelAndView toGiveExamine(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/zengsongshen");
        Map<String,String> params=getParamsMap(request);
        params.put("giveType","1");
        PageInfo<Map<String,Object>> pageInfo=giveService.queryListByPage(params);
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }

    /**
     * 快递信息修改赠送产品
     * @param give
     * @param request
     * @param response
     */
    @RequestMapping("updateGiveExamine.html")
    public void updateGiveExamine(Give give,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String productIds=request.getParameter("productIds");
            if(StringUtils.isNotBlank(productIds) ){
                giveService.updateGiveProduct(give,request);
                returnInfo.setResult(true);
                returnInfo.setMsg("保存成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("请选择产品");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 改变审核状态
     * @param request
     * @param response
     */
    @RequestMapping("updateGiveState.html")
    public void updateGiveState(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String giveId=request.getParameter("giveId");
            String state=request.getParameter("state");
            Give give=giveService.get(giveId);
            give.setState(state);
            if("2".equals(state)){
                giveService.updateGiveState(give);
                //给经销商发短信
                String smsContent=request.getParameter("smsContent");
                Dealer dealer=dealerService.get(give.getDealerId());
                if(StringUtils.isNotBlank(smsContent) && StringUtils.isNotBlank(dealer.getSmsTel()) ){
                    smsService.getsmsCodeByPhone(dealer.getSmsTel(),smsContent,request);
                }
            }else if("3".equals(state)) {
                giveService.update(give);
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
     * 根据赠送表Id查询产品
     * @param giveId
     * @param request
     * @param response
     */
    @RequestMapping("queryGiveProduct.html")
    public void queryGiveProduct(String giveId,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            List<Map<String,Object>> giveList=giveService.queryProductByGiveId(giveId);
            returnInfo.setObj(giveList);
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
     * 去赠送甄别页面
     * @param request
     * @return
     */
    @RequestMapping("toGiveRecord.html")
    public ModelAndView toGiveRecord(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/zengsong");
        Map<String,String> params=getParamsMap(request);

        UserInfo userInfo= (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
        Order info=new Order();
        info.setObtainUserId(userInfo.getUserId());
        info=orderService.getBySelective(info);

        if(info!=null && StringUtils.isNotBlank(info.getTel())){
            params.put("dealerTel",info.getTel());
        }
        modelAndView.addObject("info",info);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 赠送甄别页面数据
     * @param request
     * @return
     */
    @RequestMapping("giveRecordData.html")
    public ModelAndView giveRecordData(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/zengsongData");
        Map<String,String> params=getParamsMap(request);
//        params.put("isPage","0");
        params.put("iszengsongjilu","y");
        params.put("giveState","2");
        PageInfo<Map<String,Object>> pageInfo=giveService.queryListByPage(params);
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }

    /**
     * 赠送给经销商保存(需审核)
     * @param give
     * @param request
     * @param response
     */
    @RequestMapping("saveGive.html")
    public void saveGive(Give give,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String productIds=request.getParameter("productIds");
//            String productNums=request.getParameter("productNums");
            if(StringUtils.isNotBlank(productIds)){
                give.setState("2");
                give=giveService.saveGive(give,request);
                returnInfo.setResult(true);
                returnInfo.setObj(give.getId());
                returnInfo.setMsg("保存成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("请选择产品");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 公司赠送或经销商赠送保存(不需审核)
     * @param give
     * @param request
     * @param response
     */
    @RequestMapping("saveCompanyGive.html")
    public void saveCompanyGive(Give give,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String productIds=request.getParameter("productIds");
//            String productNums=request.getParameter("productNums");
            if(StringUtils.isNotBlank(productIds) ){
                give.setState("2");
                giveService.saveGive(give,request);
                if(StringUtils.isNotBlank(give.getDealerId())){
                    //给经销商发短信
                    String jxsSms=request.getParameter("jxsSms");
                    Dealer dealer=dealerService.get(give.getDealerId());
                    if(StringUtils.isNotBlank(jxsSms) && StringUtils.isNotBlank(dealer.getSmsTel()) ){
                        smsService.getsmsCodeByPhone(dealer.getSmsTel(),jxsSms,request);
                    }
                    //给客户发短信
                    String khSms=request.getParameter("khSms");
                    Order order=orderService.get(give.getOrderId());
                    if(StringUtils.isNotBlank(khSms) && StringUtils.isNotBlank(order.getSmsTel()) ){
                        smsService.getsmsCodeByPhone(order.getSmsTel(),khSms,request);
                    }
                }
                returnInfo.setResult(true);
                returnInfo.setMsg("保存成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("请填写数量");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * H0000赠送保存(不需审核)
     * @param give
     * @param request
     * @param response
     */
    @RequestMapping("saveHGive.html")
    public void saveHGive(Give give,HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String productIds=request.getParameter("productIds");
            if(StringUtils.isNotBlank(productIds) ){
                give.setState("2");
                give.setReserved1("n");
                giveService.saveHGive(give,request);
                returnInfo.setResult(true);
                returnInfo.setMsg("保存成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 查询订单是否已经公司赠送或经销商赠送
     * @param request
     * @param response
     */
    @RequestMapping("queryIsGive.html")
    public void queryIsGive(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
           String orderId=request.getParameter("orderId");
           String isDealer=request.getParameter("isDealer");
           Give give=new Give();
           give.setOrderId(orderId);
           List<Give> giveList=giveService.queryBySelective(give);
//           Map map=getParamsMap(request);
            //               Integer i=giveService.queryIsGive(map);
            if(giveList.size()==0){
                returnInfo.setResult(true);
                returnInfo.setMsg("查询成功");
            }else {
                if("y".equals(isDealer)){
                    returnInfo.setResult(false);
                    returnInfo.setMsg("此询单已经赠送");
                }else {
                    give=giveList.get(0);
                    if(StringUtils.isNotBlank(give.getDealerId())){
                        returnInfo.setResult(false);
                        returnInfo.setMsg("此询单已经赠送");
                    }else {
                        GiveProduct giveProduct=new GiveProduct();
                        giveProduct.setGiveId(give.getId());
                        List<GiveProduct> giveProductList=giveProductService.queryBySelective(giveProduct);
                        String productId=request.getParameter("productId");
                        boolean isTrue=true;
                        for (GiveProduct giveProduct1 : giveProductList) {
                            if(giveProduct1.getProductId().equals(productId)){
                                isTrue=false;
                                break;
                            }
                        }
                        if(isTrue){
                            if(giveProductList.size()<3){
                                returnInfo.setResult(true);
                                returnInfo.setMsg("查询成功");
                            }else {
                                returnInfo.setResult(false);
                                returnInfo.setMsg("最多赠送三个品种");
                            }
                        }else {
                            returnInfo.setResult(false);
                            returnInfo.setMsg("此品种已经赠送");
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }

    /**
     * 查询经销商是否已今日赠送给经销商
     * @param request
     * @param response
     */
    @RequestMapping("queryIsGiveDealer.html")
    public void queryIsGiveDealer(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> map=getParamsMap(request);
            List<Map<String,Object>> giveList=giveService.queryIsGiveDealer(map);
            if(giveList.size()>0){
                returnInfo.setResult(false);
                returnInfo.setMsg("查询成功");
            }else {
                returnInfo.setResult(true);
                returnInfo.setMsg("查询成功");
            }

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
    @RequestMapping("updateBeizhu.html")
    public void updateBeizhu(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String ,String > map = getParamsMap(request);
            giveService.updateBeizhu(map);
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
     * 去快递信息页面
     * @param request
     * @return
     */
    @RequestMapping("toExpressInfo.html")
    public ModelAndView toExpressInfo(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/kuaidi");
        Map<String,String> params=getParamsMap(request);
        params.put("giveState","2");
        if(StringUtils.isBlank(params.get("giveTypeKD"))){
            params.put("giveTypeKD","4");
        }
        PageInfo<Map<String,Object>> pageInfo=giveService.queryListByPage(params);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 快递信息页面查询赠送记录
     * @param request
     * @param response
     */
    @RequestMapping("queryGive.html")
    public void queryGive(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> map=getParamsMap(request);
            Map map1=giveService.queryGive(map);
            returnInfo.setObj(map1);
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
     * 快递信息页面发送通知
     * @param request
     * @param response
     */
    @RequestMapping("updateGoodsNum.html")
    public void updateGoodsNum(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> map=getParamsMap(request);
            Give give=giveService.get(map.get("giveId"));
            give.setGoodsNum(map.get("goodsNum"));
            give.setIsNotice("1");
            giveService.update(give);
            if(!"1".equals(give.getGiveType())){
                //给客户发短信
                String smsContent=request.getParameter("smsContent");
                Order order=orderService.get(give.getOrderId());
                if(StringUtils.isNotBlank(smsContent) && StringUtils.isNotBlank(order.getSmsTel()) ){
                    smsService.getsmsCodeByPhone(order.getSmsTel(),smsContent,request);
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
     * 快递信息导出全部
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("expressInfoExcel.html")
    public void expressInfoExcel(HttpServletRequest request,HttpServletResponse response){
            Map<String,String> params=new HashMap<>();
            params.put("giveState","2");
            String numKd=request.getParameter("numKd");
            if(StringUtils.isNotBlank(numKd)){
                params.put("numKd","2");
            }
            String giveTypeKD=request.getParameter("giveTypeKD");
            params.put("giveTypeKD",giveTypeKD);
            List<Map<String,Object>> result = giveService.queryListGive(params);
            String[] sheetName = new String[] {"快递信息导出"};
            List<Object[]> titleList = new ArrayList<>();
            Object[] title = new Object[]{"单号","日期","姓名","手机号码","地址","产品","宣传类型","操作员","快递单号"};
            titleList.add(title);
            List<Object[]> dataList = new ArrayList<>();
            for (int i = 0; i <result.size() ; i++) {
                Object[] obj = new Object[9];
                obj[0]=result.get(i).get("only_num");
                obj[1]=result.get(i).get("create_time").toString().substring(0,10);
//                String numArry=result.get(i).get("give_content").toString();
//                obj[2]=numArry.substring(0,numArry.length()-1);
                String[] numArry=result.get(i).get("give_content").toString().split(";");
                StringBuilder numStr=new StringBuilder();
                for (String s : numArry) {
                    numStr.append(s.substring(0,4)).append(";");
                }
                obj[5]="食品"+numStr.substring(0,numStr.length()-1);
                String giveType= (String) result.get(i).get("give_type");
                if("1".equals(giveType)){
                    obj[2]=result.get(i).get("dname");
                    obj[3]=result.get(i).get("register_tel");
                    obj[4]=result.get(i).get("delivery_address");
                }else {
                    obj[2]=result.get(i).get("name");
                    obj[3]=result.get(i).get("tel");
                    obj[4]=result.get(i).get("address");
                }
                if("1".equals(result.get(i).get("give_type"))){
                    obj[6]="赠送给经销商";
                }else if("2".equals(result.get(i).get("give_type"))){
                    obj[6]="公司赠送";
                }else if("3".equals(result.get(i).get("give_type"))){
                    obj[6]="经销商赠送";
                }
                obj[7]=result.get(i).get("give_man_name");
                obj[8]=result.get(i).get("goods_num");
                dataList.add(obj);
            }

            List<List<Object[]>> data = new ArrayList<>();
            data.add(dataList);
            try {
                Workbook wb = ExportExcelUtil.getWorkBook(sheetName,titleList,data);
                OutputStream os=response.getOutputStream();
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                response.reset();
                response.setCharacterEncoding("UTF-8");
                String fileName = "快递信息"+s.format(new Date());
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + ExportExcelUtil.encodeFilename(fileName + ".xls", request));
                os.flush();
                wb.write(os);
                wb.close();
                os.close();
                log.info("导出成功");
            } catch (IOException e) {
                e.printStackTrace();
                log.info("系统内部出现错误");
            }
    }


    /**
     * 去咨询信息页面
     * @param request
     * @return
     */
    @RequestMapping("toConsultInfo.html")
    public ModelAndView toConsultInfo(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/zixun");
        Map<String,String> params=getParamsMap(request);
        params.put("notNull","2");
        PageInfo<Map<String,Object>> pageInfo=orderService.queryListByParams(params);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 去综合查询页面
     * @param request
     * @return
     */
    @RequestMapping("toZhonghe.html")
    public ModelAndView toZhonghe(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/caozuo");
        Map<String,String> params=getParamsMap(request);
        params.put("state","7");
        PageInfo<Map<String,Object>> pageInfo=giveService.zhQuery(params);
        modelAndView.addObject("pageInfo",pageInfo);
        modelAndView.addObject("params",params);
        return modelAndView;
    }

    /**
     * 咨询信息页面发送通知
     * @param request
     * @param response
     */
    @RequestMapping("updateConsultInfo.html")
    public void updateConsultInfo(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> map=getParamsMap(request);
            Give give=giveService.get(map.get("giveId"));
            give.setIsDealer("1");
            giveService.update(give);
            //给经销商发短信
            String smsContent=request.getParameter("smsContent");
            Dealer dealer=dealerService.get(give.getDealerId());
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
     * 咨询信息页面发送通知下载
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("consultInfoExcel.html")
    public void consultInfoExcel(HttpServletRequest request,HttpServletResponse response){
        String values = request.getParameter("giveIds");
        String[] ids = values.split(",");
        if(ids.length>0){
            List<Map<String,Object>> result = giveService.queryListByIdS(ids);
            String[] sheetName = new String[] {"咨询信息导出"};
            List<Object[]> titleList = new ArrayList<>();
            Object[] title = new Object[]{"序号","日期","手机号码","姓名","地址","咨询产品","操作员","合作商","经销商手机号"};
            titleList.add(title);
            List<Object[]> dataList = new ArrayList<>();
            for (int i = 0; i <result.size() ; i++) {
                Object[] obj = new Object[9];
                obj[0]=i+1;
                obj[1]=result.get(i).get("import_time").toString().substring(0,10);
                obj[2]=result.get(i).get("tel");
                obj[3]=result.get(i).get("name");
                obj[4]=result.get(i).get("address");
                obj[5]=result.get(i).get("product_names");
                obj[6]=result.get(i).get("transfer_man_name");
                obj[7]=result.get(i).get("dealerNum");
                obj[8]=result.get(i).get("register_tel");
                dataList.add(obj);
            }

            List<List<Object[]>> data = new ArrayList<>();
            data.add(dataList);
            try {
                Workbook wb = ExportExcelUtil.getWorkBook(sheetName,titleList,data);
                OutputStream os=response.getOutputStream();
                SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                response.reset();
                response.setCharacterEncoding("UTF-8");
                String fileName = "咨询信息"+s.format(new Date());
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + ExportExcelUtil.encodeFilename(fileName + ".xls", request));
                os.flush();
                wb.write(os);
                wb.close();
                os.close();
                log.info("导出成功");
            } catch (IOException e) {
                e.printStackTrace();
                log.info("系统内部出现错误");
            }
        }else{
            log.info("未选中任何数据");
        }
    }

    /**
     * 快递信息页面批量删除
     * @param request
     * @param response
     */
    @RequestMapping("deleteConsultInfo.html")
    public void deleteConsultInfo(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String ids=request.getParameter("giveIds");
            String[] idArry=ids.split(",");
            giveService.deleteByIds(idArry);
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
     * 去打印发货单
     * @param request
     * @return
     */
    @RequestMapping("toPrintInvoice.html")
    public ModelAndView toPrintInvoice(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("pc/dayinfa");
        Map<String,String> params=getParamsMap(request);
        params.put("giveState","2");
        params.put("giveType","1");
        PageInfo<Map<String,Object>> pageInfo=giveService.queryListByPage(params);
        modelAndView.addObject("pageInfo",pageInfo);
        return modelAndView;
    }

    /**
     * 打印发货单查询
     * @param giveId
     * @param request
     * @param response
     */
    @RequestMapping("queryGivePrint.html")
    public ModelAndView queryGivePrint(String giveId,HttpServletRequest request, HttpServletResponse response){
        ModelAndView modelAndView=new ModelAndView("pc/dayinData");
        Map<String,String> params=getParamsMap(request);
        List<Map<String,Object>> giveProductList=giveService.queryProductByGiveId(params.get("giveId"));
        params.put("printGiveId",params.get("giveId"));
        List<Map<String,Object>> giveList=giveService.queryListGive(params);
        Map<String,Object> give=giveList.get(0);
        give.put("now_date",DateUtils.getTime().substring(0,5));
        modelAndView.addObject("giveProductList",giveProductList);
        modelAndView.addObject("give",give);
        return modelAndView;
    }

    /**
     * 快递页面修改  1，姓名 2,地址
     * @param request
     * @param response
     */
    @RequestMapping("updateInfo.html")
    public void updateInfo(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String ,String > map = getParamsMap(request);
            giveService.updateInfo(map);
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
     * 赠送给经销商确认已打印
     * @param request
     * @param response
     */
    @RequestMapping("surePrint.html")
    public void surePrint(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            String id=request.getParameter("id");
            Give give=giveService.get(id);
            give.setReserved1("y");
            giveService.update(give);
            returnInfo.setResult(true);
            returnInfo.setMsg("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
        writeJson(response, returnInfo);
    }
}
