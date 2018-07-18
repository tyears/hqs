package com.cccuu.project.controller.common;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.article.Article;
import com.cccuu.project.model.articletype.ArticleType;
import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.model.orderstate.OrderState;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.service.article.ArticleService;
import com.cccuu.project.service.articletype.ArticleTypeService;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.service.frontcore.RightsService;
import com.cccuu.project.service.frontcore.RoleRightsService;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.service.orderstate.OrderStateService;
import com.cccuu.project.service.remarks.RemarksService;
import com.cccuu.project.service.sms.SmsService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.*;

@Controller
public class CommonPCController extends BaseController {
    Logger log = Logger.getLogger(CommonPCController.class);

    @Resource
    private UserService userService;

    @Resource
    private RoleRightsService roleRightsService;

    @Resource
    private RightsService rightsService;
    @Resource
    private OrderService orderService;
    @Resource
    private DepartService departService;
    @Resource
    private ArticleService articleService;
    @Resource
    private ArticleTypeService articleTypeService;
    @Resource
    private SmsService smsService;
    @Resource
    private RemarksService remarksService;
    @Resource
    private OrderStateService orderStateService;
    @Resource
    private LogService logService;
    @Resource
    private DealerService dealerService;
    @Resource
    private AreaService areaService;

    /**
     * 去登录页面
     *
     * @return
     */
    @RequestMapping("toLogin.html")
    public ModelAndView toLogin() {
        ModelAndView modelAndView = new ModelAndView("pc/login");
        return modelAndView;
    }

    /**
     * 去技术服务页面
     *
     * @return
     */
    @RequestMapping("toJsfw.html")
    public ModelAndView toJsfw(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pc/jishufuwu");
        List<ArticleType> articleTypeList=articleTypeService.queryAllList();
        for (ArticleType articleType : articleTypeList) {
           Map<String,String> map=new HashMap<>();
            map.put("articleTypeId",articleType.getId());
            List<Article> articleList=articleService.queryList(map);
            articleType.setArticleList(articleList);
        }
//        ArticleType articleType1=new ArticleType();
        Map<String, String> params=new HashMap<>();
        List<Article> articleList=articleService.queryList(params);
        Article article1=new Article();
        if(articleList.size() > 0){
            article1=articleList.get(0);
        }
        modelAndView.addObject("articleList",articleList);
        modelAndView.addObject("firstArticle",article1);
        modelAndView.addObject("articleTypeList",articleTypeList);
        return modelAndView;
    }

    /**
     * 根据文章类别查询文章
     * @param request
     * @param response
     */
    @RequestMapping("queryArticleList.html")
    public void queryArticleList(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        try {
            Map<String,String> map=getParamsMap(request);
            List<Article> articleList=articleService.queryList(map);
            returnInfo.setObj(articleList);
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
     * 查询文章详情
     * @param request
     * @param response
     */
    @RequestMapping("queryArticle.html")
    public void queryArticle(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo=new ReturnInfo();
        String id = request.getParameter("id");
        try {
            Article article=articleService.get(id);
            returnInfo.setObj(article);
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
     * 去右侧菜单页面
     *
     * @return
     */
    @RequestMapping("toRightSide.html")
    public ModelAndView toRightSide(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("pc/rightside");
        UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
        Order info=new Order();
        info.setObtainUserId(userInfo.getUserId());
        info=orderService.getBySelective(info);
        modelAndView.addObject("info",info);
        //订单数
        Map<String,Object> mapNum=new HashMap<>();
        String  dclState=orderService.queryUserToUserState(request,"0");
        Integer allNum=orderService.allNum(dclState);
        mapNum.put("allNum", allNum);
        Integer dclNum=orderService.dclNum(userInfo.getUserId(),dclState);
        mapNum.put("dclNum",dclNum);
        User user=userService.get(userInfo.getUserId());
        Integer clNum=0;
        if(user.getObtainTime()!=null && DateUtils.getDistanceOfTwoDate(new Date(),user.getObtainTime())==0){
                clNum=user.getTransferNum();
        }
        mapNum.put("clNum",clNum);
        modelAndView.addObject("mapNum",mapNum);
        //订单状态排序
        List<OrderState> orderStateList=orderStateService.queryList();
        modelAndView.addObject("orderStateList",orderStateList);
        return modelAndView;
    }

    /**
     * 去主页
     *
     * @return
     */
    @RequestMapping(value = {"toIndex.html", "pc.html"})
    public ModelAndView toIndex(HttpServletRequest request) {
        UserInfo sysUserInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
        ModelAndView modelAndView = new ModelAndView("pc/index");
        Depart depart=departService.get(sysUserInfo.getDepartId());
        modelAndView.addObject("depart",depart);
        return modelAndView;
    }

    /**
     * PC端登录操作
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "doLogin.html", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String userName = request.getParameter("userName");
            String passWord = request.getParameter("passWord");
            User tempUser = new User();
            tempUser.setUserName(userName);
            List<User> sysUserList = userService.queryBySelective(tempUser);
            if (sysUserList == null || sysUserList.size() == 0) {
                resultMap.put("result", false);
                resultMap.put("type", "userName");
                resultMap.put("msg", "用户不存在!");
            } else {
                User sysUser = userService.login(userName, passWord);
                if (sysUser == null) {
                    resultMap.put("result", false);
                    resultMap.put("type", "passWord");
                    resultMap.put("msg", "密码错误!");
                } else {
                    if (StringUtils.isBlank(sysUser.getState()) || "0".equals(sysUser.getState())) {
                        resultMap.put("result", false);
                        resultMap.put("type", "userName");
                        resultMap.put("msg", "用户被禁用!");
                    } else {
                        UserInfo sysUserInfo = new UserInfo(sysUser);
                        request.getSession().setAttribute(Constants.SESSION_USER_TYPE,sysUser.getUserType());
                        //用户信息放入session
                        setSessionAttribute(request, Constants.SESSION_USER, sysUserInfo);
                        resultMap.put("result", true);
                        resultMap.put("msg", "登录成功!");
                        //更新最后登录时间
                        sysUser.setLastLoginTime(new Date());
                        userService.updateSelective(sysUser);

                        List<Map> remarkslist = remarksService.getRemarksList(new HashMap<String, String>());
                        Map remarksmap = new HashMap();
                        for (Map m : remarkslist) {
                            remarksmap.put(m.get("id"),m.get("text"));
                        }
                        setSessionAttribute(request,"remarks",remarksmap);
                        request.getSession().setMaxInactiveInterval(4*60*60);

                        //查询有权访问的右部菜单集合
                        List<Map<String, Object>> list = rightsService.getRightsByUserid(sysUserInfo.getUserId());
                        Set<Map<String, Object>> set = new HashSet<>(list);
                        Set<String > strings = new HashSet<>();
                        for (Map<String, Object> map : set) {
                            strings.add((String) map.get("code"));
                        }
                        setSessionAttribute(request, Constants.SESSION_USER_HOLDFUNS, strings);
                        //记录
                        Log log = new Log();
                        log.setUserId(sysUser.getId());
                        log.setOperation("登录操作");
                        log.setType("1");
                        log.setContent("用户登录");
                        log.setRequestIp(request.getRemoteAddr());
                        log.setCreateTime(new Date());
                        log.setMethod(sysUser.getName());
                        logService.insertOrUpdate(log);
                    }
                }
            }
            writeJson(response, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PC端退出操作
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "loginOut.html")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo= (UserInfo) getSessionAttribute(request,Constants.SESSION_USER);
        //记录
        Log log = new Log();
        log.setUserId(userInfo.getUserId());
        log.setOperation("退出操作");
        log.setType("1");
        log.setContent("用户退出");
        log.setRequestIp(request.getRemoteAddr());
        log.setCreateTime(new Date());
        log.setMethod(userInfo.getName());
        logService.insertOrUpdate(log);
        removeSessionAttribute(request, Constants.SESSION_USER);
        return "redirect:toLogin.html";

    }

    /**
     * 去修改密码页面
     *
     * @return
     */
    @RequestMapping("toUpdatePsw.html")
    public ModelAndView toUpdatePsw() {
        ModelAndView modelAndView = new ModelAndView("pc/changePassWordPc");
        return modelAndView;
    }

    /**
     * PC端修改密码操作
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "updatePsw.html", method = RequestMethod.POST)
    public void updatePsw(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String ,String > map = getParamsMap(request);
        String oldPassWord = map.get("oldpw");
        String newPassWord = map.get("newpw");
        UserInfo userObject = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
        User sysuser = userService.get(userObject.getUserId());
        if (sysuser.getPassWord().equals(PassWordEncrypt.EncryptByMd5(oldPassWord))) {
            sysuser.setPassWord(PassWordEncrypt.EncryptByMd5(newPassWord));
            userService.update(sysuser);
            resultMap.put("result", true);
            resultMap.put("msg", "修改成功");
            //记录
            Log log = new Log();
            log.setUserId(sysuser.getId());
            log.setOperation("修改密码操作");
            log.setType("1");
            log.setContent("用户修改密码");
            log.setRequestIp(request.getRemoteAddr());
            log.setCreateTime(new Date());
            log.setMethod(sysuser.getName());
            logService.insertOrUpdate(log);
        } else {
            resultMap.put("result", false);
            resultMap.put("msg", "原密码错误");
        }
        writeJson(response, resultMap);
    }

    /**
     *
     * @param phone 如果需要用订单里面的电话把phone为空即可
     * @param text
     * @param response
     */
    @RequestMapping(value = "sendOutSms.html")
    public void sendOutSms(String phone,String text,HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            if(StringUtils.isBlank(phone)){
                UserInfo userInfo = (UserInfo) getSessionAttribute(request, Constants.SESSION_USER);
                Order info = new Order();
                info.setObtainUserId(userInfo.getUserId());
                info = orderService.getBySelective(info);
                if(info==null){
                    returnInfo.setResult(false);
                    returnInfo.setMsg("请先调单");
                    writeJson(response,returnInfo);
                    return;
                }else{
                    phone = info.getSmsTel();
                }
            }
            Map map=smsService.getsmsCodeByPhone(phone, text,request);
            if(map!=null && "true".equals(map.get("result"))){
                String articleId=request.getParameter("articleId");
                if(StringUtils.isNotBlank(articleId)){
                    Article article=articleService.get(articleId);
                    if(article.getSmsNum()!=null){
                        article.setSmsNum(article.getSmsNum()+1);
                    }else {
                        article.setSmsNum(1);
                    }
                    article=articleService.update(article);
                    returnInfo.setObj(article.getSmsNum());
                }
                returnInfo.setResult(true);
                returnInfo.setMsg("发送成功");
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("发送失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnInfo.setResult(false);
            returnInfo.setMsg("发送短信异常");
        }
        writeJson(response,returnInfo);
    }

    /**
     * 接口导入来电
     * @param request
     * @param response
     */
    @RequestMapping("callImport.html")
    public void callImport(Order order,HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        //条件限制
        if(StringUtils.isBlank(order.getTel())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数tel值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(order.getName())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数name值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(order.getDealerType()) || !"0,1,2,3,4".contains(order.getDealerType())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数dealerType值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(order.getState()) || !"1,2,3,4,5,6,11,12,13,14,15,16,17".contains(order.getState())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数state值错误");
            writeJson(response, returnInfo);
            return;
        }
        try {
            order.setId(null);
//            if(StringUtils.isNotBlank(order.getTel())){
//                Dealer dealer=dealerService.queryBytel(order.getTel());
//                if(dealer!=null){
//                    order.setDealerId(dealer.getId());
//                    order.setAreaName(dealer.getDistrictName());
//                    order.setDealerNum(dealer.getDealerNum());
//                }
//            }
            order.setNumberAttribution(JuHeUtils.getNumberAttribution(order.getTel()));
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
            order.setMessageType("来电导入");
            orderService.insertOrUpdate(order);
            returnInfo.setResult(true);
            returnInfo.setMsg("保存成功");

            //记录
            Log log = new Log();
            log.setOperation("添加询单");
            log.setType("1");
            log.setContent("接口添加询单编号为"+order.getOrderNum()+"的询单");
            log.setRequestIp(request.getRemoteAddr());
            log.setCreateTime(new Date());
            logService.insertOrUpdate(log);
        }catch (Exception e){
            e.printStackTrace();
        }
        writeJson(response, returnInfo);
    }

    /**
     * 接口导入客户
     * @param request
     * @param response
     */
    @RequestMapping("dealerImportAPI.html")
    public void dealerImportAPI(Dealer dealer,HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        //条件限制
        if(StringUtils.isBlank(dealer.getUuid())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数uuid值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(dealer.getDealerType()) || !"1,2,3".contains(dealer.getDealerType())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数dealerType值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(dealer.getIshezuo()) || !"y,n".contains(dealer.getIshezuo())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数ishezuo值错误");
            writeJson(response, returnInfo);
            return;
        }
        if(StringUtils.isBlank(dealer.getDistrictName())){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数districtName值错误");
            writeJson(response, returnInfo);
            return;
        }
        try {
            List<Map<String,Object>> mapList=areaService.queryAreaOne(dealer.getDistrictName());
            if(mapList.size()==1){
                Dealer qDealer=new Dealer();
                qDealer.setUuid(dealer.getUuid());
                qDealer=dealerService.getBySelective(qDealer);
                int maxnum = dealerService.codeNum();
                if(qDealer!=null){
                    //存在时更新
                    if("1".equals(qDealer.getDealerType()) && !dealer.getIshezuo().equals(qDealer.getIshezuo())){
                        if("y".equals(dealer.getIshezuo())){
                            if(StringUtils.isBlank(qDealer.getDealerNum())){
                                //生成编号
                                qDealer.setDealerNumInt(++maxnum);
                                //拼0
                                String str = "H";
                                if (maxnum < 10) str += "000" + maxnum;
                                if (maxnum >= 10 && maxnum < 100) str += "00" + maxnum;
                                if (maxnum >= 100 && maxnum < 1000) str += "0" + maxnum;
                                if (maxnum >= 1000 && maxnum < 10000) str += maxnum;
                                qDealer.setDealerNum(str);
                            }
                        }else {
//                            if(StringUtils.isNotBlank(qDealer.getDealerNum())){
//                                qDealer.setDealerNum("");
//                                qDealer.setDealerNumInt(0);
//                            }
                        }
                    }
                    qDealer.setIshezuo(dealer.getIshezuo());
                    qDealer.setReserved1("0");
                    qDealer.setRegisterTel(dealer.getRegisterTel());
                    qDealer.setName(dealer.getConsignee());
                    qDealer.setSmsAddress(dealer.getSmsAddress());
                    qDealer.setConsignee(dealer.getConsignee());
                    qDealer.setPostcode(dealer.getPostcode());
                    qDealer.setCooperationTime(dealer.getCooperationTime());
                    qDealer.setOverallMerit(dealer.getOverallMerit());
                    qDealer.setSmsTel(dealer.getSmsTel());
                    qDealer.setDistributionArea(dealer.getDistributionArea());
                    qDealer.setCompanyName(dealer.getCompanyName());
                    qDealer.setDeliveryAddress(dealer.getDeliveryAddress());
                    qDealer.setDeliveryTel(dealer.getDeliveryTel());
                    qDealer.setFax(dealer.getFax());
                    qDealer.setCooperationState(dealer.getCooperationState());
                    qDealer.setCreditEvaluation(dealer.getCreditEvaluation());
                    qDealer.setRemark(dealer.getRemark());
                    qDealer.setPostalAddress(dealer.getPostalAddress());
                    qDealer.setOtherTel(dealer.getOtherTel());
                    dealerService.update(qDealer);
                }else {
                    dealer.setReserved1("0");
                    dealer.setCreateTime(new Date());
                    dealer.setName(dealer.getConsignee());
                    if("y".equals(dealer.getIshezuo()) && "1".equals(dealer.getDealerType())){
                        //生成编号
                        dealer.setDealerNumInt(++maxnum);
                        //拼0
                        String str = "H";
                        if (maxnum < 10) str += "000" + maxnum;
                        if (maxnum >= 10 && maxnum < 100) str += "00" + maxnum;
                        if (maxnum >= 100 && maxnum < 1000) str += "0" + maxnum;
                        if (maxnum >= 1000 && maxnum < 10000) str += maxnum;
                        dealer.setDealerNum(str);
                    }
                    //单位市场
                    dealer.setDistrictId((String) mapList.get(0).get("id"));
                    dealer.setCityId((String) mapList.get(0).get("fkId"));
                    dealer.setProvinceId((String) mapList.get(0).get("ffkId"));
                    dealerService.insertOrUpdate(dealer);
                }
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("参数districtName值错误");
                writeJson(response, returnInfo);
                return;
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
    @RequestMapping("deleteDealerAPI.html")
    public void deleteData(HttpServletRequest request,HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        String uuid = request.getParameter("uuid");
        //条件限制
        if(StringUtils.isBlank(uuid)){
            returnInfo.setResult(false);
            returnInfo.setMsg("参数uuid值错误");
            writeJson(response, returnInfo);
            return;
        }
        try {
            Dealer dealer=new Dealer();
            dealer.setUuid(uuid);
            dealer=dealerService.getBySelective(dealer);
            if(dealer!=null){
                Order order = new Order();
                order.setDealerId(dealer.getId());
                List<Order> list = orderService.queryBySelective(order);
                if(list!=null&&list.size()>0){
                    returnInfo.setResult(false);
                    returnInfo.setMsg("该客户已关联询单,无法删除");
                }else{
                    dealerService.deleteById(dealer.getId());
                    returnInfo.setResult(true);
                    returnInfo.setMsg("删除成功");

                }
            }else {
                returnInfo.setResult(false);
                returnInfo.setMsg("客户不存在");
            }
        } catch (Exception e) {
            returnInfo.setResult(false);
            returnInfo.setMsg(e.getMessage());
        }
            this.writeJson(response, returnInfo);

    }
}
