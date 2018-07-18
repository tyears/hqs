package com.cccuu.project.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.cccuu.project.service.core.SysDictionaryMenuService;

public class DictionaryWriteTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	
	private String dictionaryCode;
	private String property;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		StringBuffer html = new StringBuffer("");
		String value = pageContext.getRequest().getAttribute(property)+"";
		SysDictionaryMenuService sysDictionaryMenuService = SpringContextUtil.getBean("sysDictionaryMenuService");
		html.append(sysDictionaryMenuService.getNameByDictionaryCodeValue(dictionaryCode, value));
		try {
			pageContext.getOut().write(html.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doStartTag();
	}
	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
}
