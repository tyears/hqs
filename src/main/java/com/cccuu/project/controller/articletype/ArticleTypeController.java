package com.cccuu.project.controller.articletype;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.article.Article;
import com.cccuu.project.service.article.ArticleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.articletype.ArticleType;
import com.cccuu.project.service.articletype.ArticleTypeService;
import com.cccuu.project.utils.BaseController;
import com.cccuu.project.utils.ReturnInfo;
import com.github.pagehelper.PageInfo;
/**
 * 文章类别Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:23:37
 */
@Controller
@RequestMapping("articleType")
public class ArticleTypeController extends BaseController {
	
	@Resource
	private ArticleTypeService articleTypeService;
	@Resource
	private ArticleService articleService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/articletype/articleTypeQuery");
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
    	ModelAndView modelAndView = new ModelAndView("system/articletype/articleTypeListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<ArticleType> pageInfo = articleTypeService.queryListByPage(params);
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
		ModelAndView modelAndView = new ModelAndView("system/articletype/articleTypeEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			ArticleType info = articleTypeService.get(id);
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
	@RequestMapping("editSave")
	public void editSave(ArticleType info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
		}
		try {
			List<ArticleType> typeList=articleTypeService.queryListByName(info.getTypeName(),info.getId());
			if(typeList==null || typeList.size()==0){
				info.setModifyTime(new Date());
				info = articleTypeService.insertOrUpdate(info);
				returnInfo.setResult(true);
				returnInfo.setMsg("保存成功");
			}else {
				returnInfo.setResult(false);
				returnInfo.setMsg("类别名称已存在");
			}

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
			Article article=new Article();
			article.setArticleTypeId(id);
			List<Article> articleList= articleService.queryBySelective(article);
			if(articleList!=null && articleList.size()>0){
				returnInfo.setResult(false);
				returnInfo.setMsg("请先删除该类别下的文章！");
			}else {
				articleTypeService.deleteById(id);
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
}