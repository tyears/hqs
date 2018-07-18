package com.cccuu.project.controller.remarks;

import com.cccuu.project.service.remarks.RemarksService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by wuyongjie on 2017/10/26 11:34.
 */
@Controller
@RequestMapping("remarks")
public class RemarksController extends BaseController {
    Logger log = Logger.getLogger(RemarksController.class);

    @Resource
    private RemarksService remarksService;

    /**
     * 去修改修改备注页面
     * @return
     */
    @RequestMapping("toRemark")
    public ModelAndView toRemarks(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("system/remarks/remarks");
        Map<String,String> map=getParamsMap(request);
        List<Map> list = remarksService.getRemarksList(map);
        modelAndView.addObject("list",list);
        return modelAndView;
    }

    @RequestMapping("updateRemark")
    public void toRemarks(HttpServletRequest request, HttpServletResponse response){
        ReturnInfo returnInfo = new ReturnInfo();
        Map map = getParamsMap(request);
        try{
            remarksService.updateRemark(map);
            returnInfo.setMsg("修改成功");
            returnInfo.setResult(false);
        }catch (Exception e){
            e.printStackTrace();
            returnInfo.setMsg("修改失败");
            returnInfo.setResult(false);
        }
        writeJson(response,returnInfo);
    }
}
