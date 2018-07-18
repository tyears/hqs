package com.cccuu.project.controller.pc.technicalproposal;

import com.cccuu.project.model.technicalproposal.TechnicalProposal;
import com.cccuu.project.service.give.GiveService;
import com.cccuu.project.service.technicalproposal.TechnicalProposalService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 技术建议Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月20日 10:55:15
 */
@Controller
@RequestMapping("technicalProposal")
public class TechnicalProposalController extends BaseController {
	
	@Resource
	private TechnicalProposalService technicalProposalService;
	@Resource
	private GiveService giveService;
	
	/**
     * 去查询页面(带数据)
     * @param request
     * @return
     */
    @RequestMapping("toQuery.html")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("pc/jishubujian");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
		PageInfo<Map<String,Object>> pageInfo = technicalProposalService.queryListByPage(params);
		modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
    /**
     * 分页查询数据
     * @param request
     * @return
     */
    @RequestMapping("listPageData")
    public ModelAndView listPageData(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/technicalproposal/technicalProposalListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Map<String,Object>> pageInfo = technicalProposalService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/technicalproposal/technicalProposalEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			TechnicalProposal info = technicalProposalService.get(id);
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
	@RequestMapping("editSave.html")
	public void editSave(TechnicalProposal info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setState("0");
			info.setCreateTime(new Date());
			UserInfo sysUserInfo = (UserInfo)request.getSession().getAttribute(Constants.SESSION_USER);
			info.setUserId(sysUserInfo.getUserId());
		}
		try {
			info = technicalProposalService.insertOrUpdate(info);
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
			technicalProposalService.deleteById(id);
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
	 * 导出选中记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("exportProposalExcel.html")
	public void exportProposalExcel(HttpServletRequest request,HttpServletResponse response){
		String values = request.getParameter("values");
		String[] ids = values.split(",");
		if(ids.length>0){
			List<Map<String,Object>> result = technicalProposalService.queryListByIdS(ids);
			String[] sheetName = new String[] {"技术建议导出"};
			List<Object[]> titleList = new ArrayList<>();
			Object[] title = new Object[]{"序号","提交人","提交时间","意见和建议"};
			titleList.add(title);
			List<Object[]> dataList = new ArrayList<>();
			for (int i = 0; i <result.size() ; i++) {
				Object[] obj = new Object[4];
				obj[0]=i+1;
				obj[1]=result.get(i).get("name");
				obj[2]=result.get(i).get("createTime");
				if(null != obj[2] && "" != obj[2]){
					obj[2]=obj[2].toString().substring(0,10);
				}
				obj[3]=result.get(i).get("text");
				dataList.add(obj);
			}
			//将对象设置为已下载
			for (int i = 0; i <result.size() ; i++) {
				String state = (String) result.get(i).get("state");
				if("0".equals(state)){
					String id = (String) result.get(i).get("id");
					TechnicalProposal technicalProposal = technicalProposalService.get(id);
					technicalProposal.setState("1");
					technicalProposalService.update(technicalProposal);
				}
			}

			List<List<Object[]>> data = new ArrayList<>();
			data.add(dataList);
			try {
				Workbook wb = ExportExcelUtil.getWorkBook(sheetName,titleList,data);
				OutputStream os=response.getOutputStream();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				response.reset();
				response.setCharacterEncoding("UTF-8");
				String fileName = "技术建议"+s.format(new Date());
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
	 * 导出选中记录:综合查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("zongheExcel1.html")
	public void zongheExcel1(HttpServletRequest request,HttpServletResponse response){
		String cType = request.getParameter("cType");
		String values = request.getParameter("values");
		String[] ids = values.split(",");
		if(ids.length>0){
			List<Map<String,Object>> result = giveService.queryListByIdSZH(ids);
			String[] sheetName = new String[] {"综合查询导出"};
			List<Object[]> titleList = new ArrayList<>();
			List<Object[]> dataList = new ArrayList<>();
			if ("6".equals(cType)){
				Object[] title = new Object[]{"序号","经销商编号","日期","姓名","手机号码","咨询产品","宣传产品","地址",};
				titleList.add(title);
				for (int i = 0; i <result.size() ; i++) {
					Object[] obj = new Object[8];
					obj[0]=i+1;
                    String numArry= (String) result.get(i).get("give_content");
                    if(StringUtils.isNotBlank(numArry)){
                        obj[1]=result.get(i).get("gdnum");
                        obj[6]=numArry.substring(0,numArry.length());
                    }else {
                        obj[1]=result.get(i).get("odnum");
                        obj[6]=numArry;
                    }
					obj[2]=result.get(i).get("import_time");
					if(null != obj[2] && "" != obj[2]){
						obj[2]=obj[2].toString().substring(0,10);
					}
                    obj[3]=result.get(i).get("name");
                    obj[4]=result.get(i).get("sms_tel");
                    obj[5]=result.get(i).get("product_names");
                    obj[7]=result.get(i).get("address");
					dataList.add(obj);
				}
			}else {
                Object[] title = new Object[]{"序号","最近联系日期","单位市场","客户类型","手机号码","姓名","地址","咨询产品","宣传产品"};
                titleList.add(title);
                for (int i = 0; i <result.size() ; i++) {
                    Object[] obj = new Object[9];
                    obj[0]=i+1;
					obj[1]=result.get(i).get("import_time");
					if(null != obj[1] && "" != obj[1]){
						obj[1]=obj[1].toString().substring(0,10);
					}
//                    obj[1]=result.get(i).get("import_time").toString().substring(0,10);
                    obj[2]=result.get(i).get("area_name");
                    String usertype = "普通用户";
                    if("1".equals(result.get(i).get("dealer_type")))usertype="经销商";
                    if("2".equals(result.get(i).get("dealer_type")))usertype="面粉厂";
                    if("3".equals(result.get(i).get("dealer_type")))usertype="食品厂";
                    obj[3]=usertype;
                    obj[4]=result.get(i).get("sms_tel");
                    obj[5]=result.get(i).get("name");
                    obj[6]=result.get(i).get("address");
                    obj[7]=result.get(i).get("product_names");
                    String numArry= (String) result.get(i).get("give_content");
                    if(StringUtils.isNotBlank(numArry)){
                        obj[8]=numArry.substring(0,numArry.length());
                    }else {
                        obj[8]=numArry;
                    }
                    dataList.add(obj);
                }
			}
			List<List<Object[]>> data = new ArrayList<>();
			data.add(dataList);
			try {
				Workbook wb = ExportExcelUtil.getWorkBook(sheetName,titleList,data);
				OutputStream os=response.getOutputStream();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				response.reset();
				response.setCharacterEncoding("UTF-8");
				String fileName = "综合查询"+s.format(new Date());
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
	 * 导出查询记录:综合查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("zongheExcel.html")
	public void zongheExcel(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> params=getParamsMap(request);
		params.put("state","7");
		List<Map<String,Object>> result=giveService.zhQueryExcel(params);
		String cType = params.get("cType");
		String[] sheetName;
		if("6".equals(cType))sheetName= new String[] {"通知经销商导出"};
		else sheetName= new String[] {"综合查询导出"};
			List<Object[]> titleList = new ArrayList<>();
			List<Object[]> dataList = new ArrayList<>();
			if ("6".equals(cType)){
				Object[] title = new Object[]{"序号","经销商手机号","经销商编号","日期","姓名","手机号码","咨询产品","宣传产品","地址","操作方案"};
				titleList.add(title);
				for (int i = 0; i <result.size() ; i++) {
					Object[] obj = new Object[10];
					obj[0]=i+1;
					String numArry= (String) result.get(i).get("give_content");
					String dealerId= (String) result.get(i).get("dealer_id");
					String giveType=(String) result.get(i).get("give_type");
					if("3".equals(giveType)){
						obj[1]=result.get(i).get("gdtel");
						obj[2]=result.get(i).get("gdnum");
						obj[9]="经销商赠送";
					}else if("2".equals(giveType)){
						obj[1]=result.get(i).get("gdtel");
						obj[2]=result.get(i).get("gdnum");
						obj[9]="公司赠送并通知";
					}else {
						obj[1]=result.get(i).get("odtel");
						obj[2]=result.get(i).get("odnum");
						obj[9]="咨询并通知";
					}
					obj[3]=result.get(i).get("import_time");
					if(null != obj[3] && "" != obj[3]){
						obj[3]=obj[3].toString().substring(0,10);
					}
//					obj[3]=result.get(i).get("import_time").toString().substring(0,10);
					obj[4]=result.get(i).get("name");
					obj[5]=result.get(i).get("sms_tel");
					obj[6]=result.get(i).get("product_names");
					if(StringUtils.isNotBlank(numArry)){
						obj[7]=numArry.substring(0,numArry.length());
					}else {
						obj[7]=numArry;
					}
					obj[8]=result.get(i).get("address");
					dataList.add(obj);
				}
			}else {
				Object[] title = new Object[]{"序号","最近联系日期","单位市场","客户类型","手机号码","姓名","地址","咨询产品","宣传产品"};
				titleList.add(title);
				for (int i = 0; i <result.size() ; i++) {
					Object[] obj = new Object[9];
					obj[0]=i+1;
					obj[1]=result.get(i).get("import_time");
					if(null != obj[1] && "" != obj[1]){
						obj[1]=obj[1].toString().substring(0,10);
					}
					obj[2]=result.get(i).get("area_name");
					String usertype = "普通用户";
					if("1".equals(result.get(i).get("dealer_type")))usertype="经销商";
					if("2".equals(result.get(i).get("dealer_type")))usertype="面粉厂";
					if("3".equals(result.get(i).get("dealer_type")))usertype="食品厂";
					obj[3]=usertype;
					obj[4]=result.get(i).get("sms_tel");
					obj[5]=result.get(i).get("name");
					obj[6]=result.get(i).get("address");
					obj[7]=result.get(i).get("product_names");
					String numArry= (String) result.get(i).get("give_content");
//					if(StringUtils.isNotBlank(numArry)){
//						obj[8]=numArry.substring(0,numArry.length()-1);
//					}else {
//						obj[8]=numArry;
//					}
					obj[8]=numArry;
					dataList.add(obj);
				}
			}
			List<List<Object[]>> data = new ArrayList<>();
			data.add(dataList);
			try {
				Workbook wb = ExportExcelUtil.getWorkBook(sheetName,titleList,data);
				OutputStream os=response.getOutputStream();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				response.reset();
				response.setCharacterEncoding("UTF-8");
				String fileName;
				if("6".equals(cType))fileName= "通知经销商"+s.format(new Date());
				else fileName= "综合查询"+s.format(new Date());
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
}