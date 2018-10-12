package com.cccuu.project.controller.area;

import java.io.InputStream;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.dealer.Dealer;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.dealer.DealerService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.area.Area;
import com.cccuu.project.service.area.AreaService;
import com.github.pagehelper.PageInfo;
/**
 * 区域Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 16:43:02
 */
@Controller
@RequestMapping("area")
public class AreaController extends BaseController {
	
	@Resource
	private AreaService areaService;
	@Resource
	private DealerService dealerService;
	@Resource
	private LogService logService;

	/**
	 * 去区域管理页面
	 * @return
	 */
	@RequestMapping("toAreaTreeManager")
	public ModelAndView toAreaTreeManager(){
		ModelAndView modelAndView = new ModelAndView("system/area/areaTreeManager");
		return modelAndView;
	}

	/**
	 * 获取区域树信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("getAreaNodes")
	public void getAreaNodes(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		this.writeJson(response,areaService.getListByFkId(id));
	}
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/area/areaQuery");
		Map<String, String> params = getParamsMap(request);
		String level = params.get("level");
		if(StringUtils.isBlank(level)){
			params.put("level", "0");
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
    	ModelAndView modelAndView = new ModelAndView("system/area/areaListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Area> pageInfo = areaService.queryListByPage(params);
		modelAndView.addObject("params", params);
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
		ModelAndView modelAndView = new ModelAndView("system/area/areaEdit");

		String id = request.getParameter("id");
		String fkId = request.getParameter("fkId");
		String level = request.getParameter("level");
		level = StringUtils.isBlank(level)?"0":level;
		Area area = new Area();
		if(StringUtils.isNotBlank(id)){
			area = areaService.get(id);
		}else{
			area.setFkId(fkId);
			area.setLevel(Integer.valueOf(level));
		}
		modelAndView.addObject("info",area);
		modelAndView.addObject("level",level);
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
	public void editSave(Area info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		Log log = new Log();
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			log.setOperation("添加操作");
			log.setContent("添加名称为"+info.getAreaName()+"的区域");
		}else {
			log.setOperation("修改操作");
			log.setContent("修改名称为"+info.getAreaName()+"的区域");
		}
		try {
			info = areaService.saveInfo(info);
			//记录
			SysUserInfo userObject = (SysUserInfo) getSessionAttribute(request, Constants.SYS_SESSION_USER);
			log.setUserId(userObject.getSysUserId());
			log.setType("0");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
			log.setMethod(userObject.getName());
			logService.insertOrUpdate(log);
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
		String fkId = request.getParameter("fkId");
		try {
			Area area=areaService.get(id);
			if(area.getLevel()==2){
				Dealer dealer=new Dealer();
				dealer.setDistrictId(area.getId());
				List<Dealer> dealerList=dealerService.queryBySelective(dealer);
				if(dealerList!=null &&dealerList.size()>0){
					returnInfo.setResult(false);
					returnInfo.setMsg("此区域下已有客户，不能删除！");
				}else {
					areaService.deleteInfoById(id,fkId);
					returnInfo.setResult(true);
					returnInfo.setMsg("删除成功");
				}
			}else {
				areaService.deleteInfoById(id,fkId);
				returnInfo.setResult(true);
				returnInfo.setMsg("删除成功");
			}
			if(returnInfo.isResult()){
				//记录
				Log log=new Log();
				SysUserInfo userObject = (SysUserInfo) getSessionAttribute(request, Constants.SYS_SESSION_USER);
				log.setOperation("删除操作");
				log.setContent("删除名称为"+area.getAreaName()+"的区域");
				log.setUserId(userObject.getSysUserId());
				log.setType("0");
				log.setRequestIp(request.getRemoteAddr());
				log.setCreateTime(new Date());
                log.setMethod(userObject.getName());
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
	 * 导入区域
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
			List<String> list1=new ArrayList<>();
			List<Area> areaList1=new ArrayList<>();
			List<String> list21=new ArrayList<>();
			List<String> list22=new ArrayList<>();
			List<String> list23=new ArrayList<>();
			List<Area> areaList2=new ArrayList<>();
			List<String> list3=new ArrayList<>();
			List<Area> areaList3=new ArrayList<>();
			Map<String,String> idMap=new HashMap<>();
			String exists_fk_id="";
			//已存在市区县级列表
			Area oldArea=new Area();
			oldArea.setLevel(2);
			List<Area> oldList=areaService.queryBySelective(oldArea);
			List<String> oldString=new ArrayList<>();
			for (Area area : oldList) {
				oldString.add(area.getAreaName());
			}
			//已存在市级区划列表
			Area oldCity=new Area();
			oldCity.setLevel(1);
			List<Area> oldCityList=areaService.queryBySelective(oldCity);
			List<String> oldCityString=new ArrayList<>();
			for (Area area : oldCityList) {
				oldCityString.add(area.getAreaName());
			}
			//已存在省级区划列表
			Area oldProvince=new Area();
			oldProvince.setLevel(0);
			List<Area> oldProvinceList=areaService.queryBySelective(oldProvince);
			List<String> oldProvinceString=new ArrayList<>();
			for (Area area : oldProvinceList) {
				oldProvinceString.add(area.getAreaName());
			}

			for (List<Object> objects : listob) {
				String distinct=(String) objects.get(2);
				if(!oldString.contains(distinct)){
					//省
					String province=(String) objects.get(0);
					if(!oldProvinceString.contains(province)){
						list1.add((String) province);
						Area area=new Area();
						area.setId(UniqueIDGen.getUniqueID()+"");
						area.setAreaName((String) province);
						area.setFkId("0");
						area.setSort(1);
						area.setIsParent("true");
						area.setLevel(0);
						areaList1.add(area);
						idMap.put(area.getAreaName(),area.getId());
					}

					//市
					String city=(String) objects.get(1);
					if("省直辖县级行政单位".equals(city)){
						if(!oldCityString.contains(city)){
							list21.add(province+city);
							Area area=new Area();
							area.setId(UniqueIDGen.getUniqueID()+"");
							area.setAreaName(city);
							//父级名称当父级id
							area.setFkId(province);
							area.setSort(1);
							area.setIsParent("true");
							area.setLevel(1);
							areaList2.add(area);
							idMap.put(province+city,area.getId());
						}
					}else if("县".equals((String) objects.get(1))){
						if(!oldCityString.contains(objects.get(0)+(String) objects.get(1))){
							list22.add(objects.get(0)+(String) objects.get(1));
							Area area=new Area();
							area.setId(UniqueIDGen.getUniqueID()+"");
							area.setAreaName((String) objects.get(1));
							//父级名称当父级id
							area.setFkId((String) objects.get(0));
							area.setSort(1);
							area.setIsParent("true");
							area.setLevel(1);
							areaList2.add(area);
							idMap.put(objects.get(0)+(String) objects.get(1),area.getId());
						}
					}else if(!oldCityString.contains(city)){
						list23.add(city);
						Area area=new Area();
						area.setId(UniqueIDGen.getUniqueID()+"");
						area.setAreaName(city);
						//父级名称当父级id
						area.setFkId(province);
						area.setSort(1);
						area.setIsParent("true");
						area.setLevel(1);
						areaList2.add(area);
						idMap.put(province+city,area.getId());
					}else{
						exists_fk_id=areaService.getFkId(city);
						idMap.put(province+city,exists_fk_id);
					}

					//区

					if(!list3.contains(distinct)){
						list3.add(distinct);
						Area area=new Area();
						area.setId(UniqueIDGen.getUniqueID()+"");
						area.setAreaName(distinct);
						area.setFkId(province+city);
						area.setSort(1);
						area.setIsParent("false");
						area.setLevel(2);
						areaList3.add(area);
					}
				}
			}

			if(areaList2.size()>0){
				for (Area area : areaList2) {
					area.setFkId(idMap.get(area.getFkId()));
					areaList1.add(area);
				}
			}

			if(areaList3.size()>0){
				for (Area area : areaList3) {
					area.setFkId(idMap.get(area.getFkId()));
					areaList1.add(area);
				}
			}

			if (areaList1.size()>0){
				areaService.addMulti(areaList1);
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