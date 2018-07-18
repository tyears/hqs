package com.cccuu.project.controller.dealer;

import java.io.InputStream;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.model.order.Order;
import com.cccuu.project.model.sysuser.SysUser;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.service.order.OrderService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.service.dealer.DealerService;
import com.github.pagehelper.PageInfo;
/**
 * 客户（经销商或大厂部）Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月14日 11:50:55
 */
@Controller
@RequestMapping("dealer")
public class DealerController extends BaseController {
	
	@Resource
	private DealerService dealerService;
	@Resource
	private AreaService areaService;
	@Resource
	private LogService logService;
	@Resource
	private OrderService orderService;

	/**
	 * 去客户管理页面
	 * @return
	 */
	@RequestMapping("toDealerManager")
	public ModelAndView toDealerManager(){
		ModelAndView modelAndView = new ModelAndView("system/dealer/dealerManager");
		return modelAndView;
	}

	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/dealer/dealerQuery");
    	Map<String, String> params = getParamsMap(request);
		String fkId=params.get("fkId");
		String level=params.get("level");
		if(StringUtils.isNotBlank(level)){
			if("1".equals(level)){
				params.put("provinceId",fkId);
			}else if("2".equals(level)){
				params.put("cityId",fkId);
			}else if("3".equals(level)){
				params.put("districtId",fkId);
			}
		}
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
    	ModelAndView modelAndView = new ModelAndView("system/dealer/dealerListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = dealerService.queryDealerListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/dealer/dealerEdit");
		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		Dealer info=new Dealer();
		if(StringUtils.isNotBlank(id)){
			info = dealerService.get(id);
			List<String > otherphone = new ArrayList<>();	//其他号码
			if(StringUtils.isNotBlank(info.getOtherTel())){
				for(String t : info.getOtherTel().split(",")){
					otherphone.add(t);
				}
				if(otherphone!=null&&otherphone.size()>0){
					String fstotherphone =otherphone.get(0);		//第一个其他号码
					otherphone.remove(0);
					modelAndView.addObject("fstotherphone",fstotherphone);
				}
			}
			modelAndView.addObject("otherphone",otherphone);
			Area area=areaService.get(info.getDistrictId());
			String areaName=area.getAreaName();

			area=areaService.get(info.getCityId());
			areaName=area.getAreaName()+areaName;

			area=areaService.get(info.getCityId());
			areaName=area.getAreaName()+areaName;

			info.setDistrictName(areaName);
		}else {

			Area area=areaService.get(fkId);
			info.setDistrictId(fkId);
			info.setCityId(area.getFkId());
			String areaName=area.getAreaName();

			area=areaService.get(area.getFkId());
			info.setProvinceId(area.getFkId());
			areaName=area.getAreaName()+areaName;

			area=areaService.get(area.getFkId());
			areaName=area.getAreaName()+areaName;

			info.setDistrictName(areaName);
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
	public void editSave(Dealer info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();

		/************判断手机号*************/
		String othertel = info.getOtherTel();
		boolean ishas = true;
		//循环去掉逗号
		while(ishas) {
			if(othertel.indexOf(",,")==-1){
				ishas = false;
				continue;
			}
			othertel = othertel.replace(",,", ",");//如果有空的去掉
		}
		if(",".equals(othertel))othertel = "";
		info.setOtherTel(othertel);
		if("".equals(othertel)){
			othertel += info.getRegisterTel();
		}else{
			othertel += ","+info.getRegisterTel();
		}
		String [] array = othertel.split(",");

		List<String > stringList = Arrays.asList(array);
//		Set set = new HashSet<>(stringList);
//		if(stringList.size()>set.size()) {
//			returnInfo.setResult(false);
//			returnInfo.setMsg("请检查注册手机号或其他号码,其中有重复");
//			writeJson(response, returnInfo);
//			return;
//		}
//		List<Map> list = dealerService.isHasPhone(array,info.getId());
//		if(list!=null&&list.size()>0){
//			returnInfo.setResult(false);
//			returnInfo.setMsg("请检查注册手机号或其他号码,其中有已被注册");
//			writeJson(response, returnInfo);
//			return;
//		}
		/************判断手机号结束*************/
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setReserved1("0");
			info.setUuid(CSVUtils.getUuid());
			info.setCreateTime(new Date());
			if("1".equals(info.getDealerType())) {		//只有客户类型为经销商并且为已合作的才生成编号,其他类型的ishezu为n
				if("y".equals(info.getIshezuo())) {
					int maxnum = dealerService.codeNum();
					info.setDealerNumInt(++maxnum);
					//拼0
					String str = "H";
					if (maxnum < 10) str += "000" + maxnum;
					if (maxnum >= 10 && maxnum < 100) str += "00" + maxnum;
					if (maxnum >= 100 && maxnum < 1000) str += "0" + maxnum;
					if (maxnum >= 1000 && maxnum < 10000) str += maxnum;
					info.setDealerNum(str);
				}else{
					info.setDealerNumInt(0);
				}
			}else{
				info.setDealerNumInt(0);
				info.setIshezuo("n");
			}
		}else {
			if("1".equals(info.getDealerType())) {
				if("y".equals(info.getIshezuo())){
					if(StringUtils.isBlank(info.getDealerNum())){
						int maxnum = dealerService.codeNum();
						info.setDealerNumInt(++maxnum);
						//拼0
						String str = "H";
						if (maxnum < 10) str += "000" + maxnum;
						if (maxnum >= 10 && maxnum < 100) str += "00" + maxnum;
						if (maxnum >= 100 && maxnum < 1000) str += "0" + maxnum;
						if (maxnum >= 1000 && maxnum < 10000) str += maxnum;
						info.setDealerNum(str);
					}
				}else {
					if(StringUtils.isNotBlank(info.getDealerNum())){
						info.setDealerNum("");
						info.setDealerNumInt(0);
					}
				}
			}
		}
		if(!"1".equals(info.getDealerType())) {		//不是经销商就把ishezuo改为n
			info.setIshezuo("n");
		}
		try {
			Log log = new Log();
			if(StringUtils.isBlank(info.getId())) {
				log.setOperation("添加客户");
				log.setContent("添加姓名为" + info.getName() + "的客户");
			}else{
				log.setOperation("修改客户");
				log.setContent("修改姓名为" + info.getName() + "的客户");
			}
			info = dealerService.insertOrUpdate(info);
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
			Order order = new Order();
			order.setDealerId(id);
			List<Order> list = orderService.queryBySelective(order);
			if(list!=null&&list.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该客户已关联询单,无法删除");
			}else{
				//记录
				SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
				Dealer dealer = dealerService.get(id);


				dealerService.deleteById(id);
				returnInfo.setResult(true);
				returnInfo.setMsg("删除成功");
                Log log = new Log();
                log.setUserId(userInfo.getSysUserId());
                log.setOperation("删除客户");
                log.setType("0");
                log.setContent("删除姓名为"+dealer.getName()+"的客户");
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
	 * 导入客户
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
			List<Dealer> addList=new ArrayList<>();
			int maxnum = dealerService.codeNum();
			for (List<Object> objects : listob) {
				if(!"普通客户".equals(objects.get(1))){
//判断uuid是否重复
					Dealer qDealer=new Dealer();
					qDealer.setUuid((String) objects.get(0));
					qDealer=dealerService.getBySelective(qDealer);
					if(qDealer!=null){
//						//存在时更新,2018-5-21修改为不操作
//						qDealer.setReserved1("0");
//						qDealer.setRegisterTel((String) objects.get(3));
//						qDealer.setName((String) objects.get(5));
//						qDealer.setSmsAddress((String) objects.get(4));
//						qDealer.setConsignee((String) objects.get(5));
//						qDealer.setPostcode((String) objects.get(6));
//						//合作日期
//						qDealer.setCooperationTime(DateUtils.parseDate(objects.get(7)));
//						qDealer.setOverallMerit((String) objects.get(8));
//						qDealer.setSmsTel((String) objects.get(9));
//						qDealer.setDistributionArea((String) objects.get(11));
//						qDealer.setCompanyName((String) objects.get(12));
//						qDealer.setDeliveryAddress((String) objects.get(13));
//						qDealer.setDeliveryTel((String) objects.get(14));
//						qDealer.setFax((String) objects.get(15));
//						qDealer.setCooperationState((String) objects.get(16));
//						qDealer.setCreditEvaluation((String) objects.get(17));
//						qDealer.setRemark((String) objects.get(18));
//						qDealer.setPostalAddress((String) objects.get(19));
//						//其他号码
//						if(objects.size()==21){
//							if(StringUtils.isNotBlank((String) objects.get(20))){
//								String[] telArry=((String) objects.get(20)).split(";");
//								String otherTel="";
//								for(int i=0;i<telArry.length;i++){
//									if(!telArry[i].equals(qDealer.getRegisterTel())){
//										if(i==telArry.length-1){
//											otherTel = otherTel +telArry[i];
//										}else {
//											otherTel = otherTel +telArry[i]+",";
//										}
//									}
//								}
//								qDealer.setOtherTel(otherTel);
//							}else {
//								qDealer.setOtherTel("");
//							}
//						}else {
//							qDealer.setOtherTel("");
//						}
//						dealerService.update(qDealer);
					}else {
						Dealer dealer=new Dealer();
						dealer.setId(UniqueIDGen.getUniqueID()+"");
						dealer.setCreateTime(new Date());
						dealer.setReserved1("0");
						dealer.setUuid((String) objects.get(0));
						if("经销商".equals(objects.get(1))){
//						1:经销商 2:面粉厂 3:食品厂
							dealer.setDealerType("1");
						}else if("面粉厂".equals(objects.get(1))){
							dealer.setDealerType("2");
						}else if("食品厂".equals(objects.get(1))){
							dealer.setDealerType("3");
						}
						dealer.setIshezuo((String) objects.get(2));
						if("y".equals((String) objects.get(2)) && "1".equals(dealer.getDealerType())){
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
						dealer.setRegisterTel((String) objects.get(3));
						dealer.setName((String) objects.get(5));
						dealer.setSmsAddress((String) objects.get(4));
						dealer.setConsignee((String) objects.get(5));
						dealer.setPostcode((String) objects.get(6));
						//合作日期
						dealer.setCooperationTime(DateUtils.parseDate(objects.get(7)));
						dealer.setOverallMerit((String) objects.get(8));
						dealer.setSmsTel((String) objects.get(9));
						//单位市场
						List<Map<String,Object>> mapList=areaService.queryAreaOne((String) objects.get(10));
						dealer.setDistrictId((String) mapList.get(0).get("id"));
						dealer.setCityId((String) mapList.get(0).get("fkId"));
						dealer.setProvinceId((String) mapList.get(0).get("ffkId"));
						dealer.setDistrictName((String) objects.get(10));
						dealer.setDistributionArea((String) objects.get(11));
						dealer.setCompanyName((String) objects.get(12));
						dealer.setDeliveryAddress((String) objects.get(13));
						dealer.setDeliveryTel((String) objects.get(14));
						dealer.setFax((String) objects.get(15));
						dealer.setCooperationState((String) objects.get(16));
						dealer.setCreditEvaluation((String) objects.get(17));
						dealer.setRemark((String) objects.get(18));
						dealer.setPostalAddress((String) objects.get(19));
						//其他号码
						if(objects.size()==21){
							if(StringUtils.isNotBlank((String) objects.get(20))){
								String[] telArry=((String) objects.get(20)).split(";");
								String otherTel="";
								for(int i=0;i<telArry.length;i++){
									if(!telArry[i].equals(dealer.getRegisterTel())){
										if(i==telArry.length-1){
											otherTel = otherTel +telArry[i];
										}else {
											otherTel = otherTel +telArry[i]+",";
										}
									}
								}
								dealer.setOtherTel(otherTel);
							}else {
								dealer.setOtherTel("");
							}
						}else {
							dealer.setOtherTel("");
						}
						addList.add(dealer);
					}
				}
			}

			if(addList.size() > 0){
				dealerService.addMulti(addList);
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
}