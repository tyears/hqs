package com.cccuu.project.controller.depart;

import com.cccuu.project.model.depart.Depart;
import com.cccuu.project.model.depart.UserDepart;
import com.cccuu.project.model.frontcore.User;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.depart.DepartService;
import com.cccuu.project.service.depart.UserDepartService;
import com.cccuu.project.service.frontcore.UserService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.Constants;
import com.cccuu.project.utils.ReturnInfo;
import com.cccuu.project.utils.SysUserInfo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("depart")
public class DepartController extends BaseController {
	@Resource
	private DepartService departService;
	@Resource
	private UserService userService;
	@Resource
	private UserDepartService userDepartService;
	@Resource
	private LogService logService;
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/depart/departQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/depart/departListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Depart> pageInfo = departService.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
    	return modelAndView;
    }
    
    /**
	 * 去编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/depart/departEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Depart info = departService.get(id);
			modelAndView.addObject("info", info);
		}
		return modelAndView;
	}
	/**
	 * 编辑信息保存
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("editSave")
	public void editSave(Depart depart,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			Log log=new Log();
			//新增
			if(StringUtils.isBlank(depart.getId())){
				depart.setId(null);
				depart.setCreateTime(new Date());
				log.setOperation("添加部门");
				log.setContent("添加名称为" + depart.getDepartName() + "的部门");
			}else {
				log.setOperation("修改部门");
				log.setContent("修改名称为" + depart.getDepartName() + "的部门");
			}
			departService.insertOrUpdate(depart);
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
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("deleteData")
	public void deleteData(String id,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			User user = new User();
			user.setDepartId(id);
			List<User> list = userService.queryBySelective(user);
			UserDepart userDepart = new UserDepart();
			userDepart.setDepartId(id);
			List<UserDepart> list2 = userDepartService.queryBySelective(userDepart);
			if(list!=null&&list.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该部门已存在用户,无法删除");
			}else if(list2!=null&&list2.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("该部门已存在用户,无法删除");
			}
			else{
				Depart depart=departService.get(id);
				departService.deleteById(id);
				returnInfo.setResult(true);
				returnInfo.setMsg("删除成功");
				//记录
				Log log=new Log();
				log.setOperation("删除部门");
				log.setContent("删除名称为" + depart.getDepartName() + "的部门");
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
}
