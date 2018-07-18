package com.cccuu.project.controller.article;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cccuu.project.model.articletype.ArticleType;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.articletype.ArticleTypeService;
import com.cccuu.project.service.log.LogService;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cccuu.project.model.article.Article;
import com.cccuu.project.service.article.ArticleService;
import com.github.pagehelper.PageInfo;
/**
 * 文章Controller
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 11:49:50
 */
@Controller
@RequestMapping("article")
public class ArticleController extends BaseController {
	
	@Resource
	private ArticleService articleService;
	@Resource
	private ArticleTypeService articleTypeService;
	@Resource
	private LogService logService;
	
	/**
     * 去查询页面
     * @param request
     * @return
     */
    @RequestMapping("toQuery")
    public ModelAndView toQuery(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/article/articleQuery");
    	Map<String, String> params = getParamsMap(request);
    	modelAndView.addObject("params", params);
		List<ArticleType> articleTypeList=articleTypeService.queryAllList();
		modelAndView.addObject("articleTypeList",articleTypeList);
    	return modelAndView;
    }
    /**
     * 分页查询数据
     * @param request
     * @return
     */
    @RequestMapping("listPageData")
    public ModelAndView listPageData(HttpServletRequest request) {
    	ModelAndView modelAndView = new ModelAndView("system/article/articleListData");
    	Map<String, String> params = getParamsMap(request);
    	PageInfo<Article> pageInfo = articleService.queryListByPage(params);
    	modelAndView.addObject("pageInfo", pageInfo);
		List<ArticleType> articleTypeList=articleTypeService.queryAllList();
		modelAndView.addObject("articleTypeList",articleTypeList);
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
		ModelAndView modelAndView = new ModelAndView("system/article/articleEdit");
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			Article info = articleService.get(id);
			modelAndView.addObject("info",info);
		}
		List<ArticleType> articleTypeList=articleTypeService.queryAllList();
		modelAndView.addObject("articleTypeList",articleTypeList);
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
	public void editSave(Article info,HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		//新增
		if(StringUtils.isBlank(info.getId())){
			info.setId(null);
			info.setSmsNum(0);
		}
		try {
			Log log = new Log();
			if (StringUtils.isBlank(info.getId())) {
				log.setOperation("添加文章");
				log.setContent("添加标题为" + info.getTitle() + "的文章");
			} else {
				log.setOperation("修改文章");
				log.setContent("修改标题为" + info.getTitle() + "的文章");
			}

			info.setModifyTime(new Date());
			info = articleService.insertOrUpdate(info);
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
			Article article=articleService.get(id);
			articleService.deleteById(id);
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
			//记录
			Log log=new Log();
			log.setOperation("删除文章");
			log.setContent("删除标题为" + article.getTitle() + "的文章");
			SysUserInfo userInfo = (SysUserInfo) request.getSession().getAttribute(Constants.SYS_SESSION_USER);
			log.setUserId(userInfo.getSysUserId());
			log.setType("0");
			log.setRequestIp(request.getRemoteAddr());
			log.setCreateTime(new Date());
            log.setMethod(userInfo.getName());
			logService.insertOrUpdate(log);
		} catch (Exception e) {
			returnInfo.setResult(false);
			returnInfo.setMsg(e.getMessage());
		}finally{
			this.writeJson(response, returnInfo);
		}
		
	}

	/**
	 * 删除多个文章
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteIds")
	public void deleteIds(HttpServletRequest request,HttpServletResponse response){
		ReturnInfo returnInfo = new ReturnInfo();
		try {
			String ids = request.getParameter("ids");
			String id[] = ids.split(",");
			for (String s : id) {
				if (StringUtils.isNotBlank(s)) {
					articleService.deleteById(s);
				}
			}
			returnInfo.setResult(true);
			returnInfo.setMsg("删除成功");
		}catch (Exception e){
			e.printStackTrace();
			returnInfo.setResult(false);
			returnInfo.setMsg("删除文章出现错误");
		}

		this.writeJson(response, returnInfo);
	}

	/**
	 * 导入文章
	 * @param request
	 * @param response
	 */
	@RequestMapping("excelArticle")
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
			List<Article> articleList=new ArrayList<>();
			for (List<Object> objects : listob) {
				ArticleType articleType=new ArticleType();
				articleType.setTypeName((String) objects.get(5));
				List<ArticleType> articleTypeList=articleTypeService.queryBySelective(articleType);
				if(articleTypeList!=null && articleTypeList.size()==1){
					Article article=new Article();
					article.setId(UniqueIDGen.getUniqueID()+"");
					article.setTitle((String) objects.get(0));
					article.setTechnologyUrl((String) objects.get(1));
					article.setContent((String) objects.get(2));
					article.setReplySms((String) objects.get(3));
					article.setPromptSms((String) objects.get(4));
					article.setArticleTypeId(articleTypeList.get(0).getId());
					article.setSort(1);
					article.setModifyTime(new Date());
					article.setSmsNum(0);
					articleList.add(article);
				}
			}
			if(articleList.size() > 0){
				articleService.addMulti(articleList);
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