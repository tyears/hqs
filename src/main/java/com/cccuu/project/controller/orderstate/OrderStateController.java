package com.cccuu.project.controller.orderstate;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.orderstate.OrderState;
import com.cccuu.project.service.orderstate.OrderStateService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 订单状态排序Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年11月15日 17:52:12
 */
@Controller
@RequestMapping("orderState")
public class OrderStateController extends BaseController {
	
	@Resource
	private OrderStateService orderStateService;

    /**
	 * 到编辑信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(HttpServletRequest request,HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("system/orderstate/orderStateEdit");
		List<OrderState> infoList=orderStateService.queryList();
		modelAndView.addObject("infoList",infoList);
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
	public void editSave(OrderState info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			Map map=getParamsMap(request);
			orderStateService.updateSort(map);
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