package com.cccuu.project.utils;


import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @Description 后台权限标签
 * @author zhaixiaoliang
 * @author 2016年11月8日下午2:19:53
 */
public class UserRightTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	//页面使用标签是需要传入的参数(权限编码)
	private String funCode;
	@Override
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		HttpSession session = request.getSession();
		//用户为"root"拥有所有权限
		/*if(StringUtils.isNotBlank(userInfo.getUserName())&&"root".equals(userInfo.getUserName())){
			return EVAL_BODY_INCLUDE;
		}*/
		if(StringUtils.isBlank(funCode)){
			//权限code没有输入返回EVAL_BODY_INCLUDE，即输出标签体的内容
			return EVAL_BODY_INCLUDE;
		}
		//用户拥有的权限
		@SuppressWarnings("unchecked")
		Set<String> holdFuns = (Set<String>) session.getAttribute(Constants.SESSION_USER_HOLDFUNS);
		if (holdFuns!=null&&holdFuns.contains(funCode)) {
			//用户权限列表中包含访问所需权限则返回EVAL_BODY_INCLUDE，即输出标签体的内容
			return EVAL_BODY_INCLUDE;
		} else {
			//否则跳过标签体，即不显示标签包含的html内容
			return SKIP_BODY;
		}
	}
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return EVAL_PAGE;//让服务器继续执行页面
	}
	public String getFunCode() {
		return funCode;
	}
	public void setFunCode(String funCode) {
		this.funCode = funCode;
	}
}
