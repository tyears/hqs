package com.cccuu.project.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.service.core.SysDictionaryMenuService;

public class DictionaryCheckBoxTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	
	private String dictionaryCode;
	private String name;
	private String value;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		StringBuffer html = new StringBuffer("");
		List<String> valueList = null;
		//获取字典项列表
		if(StringUtils.isNotBlank(value)){
			valueList = Arrays.asList(value.split(","));
		}
		SysDictionaryMenuService sysDictionaryMenuService = SpringContextUtil.getBean("sysDictionaryMenuService");
		List<SysDictionaryMenu> resultList = sysDictionaryMenuService.queryListByDictionaryCode(dictionaryCode);
		if(resultList!=null&&resultList.size()>0){
			for (int i = 0; i < resultList.size(); i++) {
				SysDictionaryMenu loopObj = resultList.get(i);
				String checked = valueList!=null&&valueList.contains(loopObj.getValue())?"checked":"";
				html.append("<div class='check-box'>");
				html.append("<input name='"+name+"' type='checkbox' value='"+loopObj.getValue()+"' id='"+name+i+1+"' "+checked+"/>");
				html.append("<label for='"+name+i+1+"'>"+loopObj.getName()+"</label>");
				html.append("</div>");
			}
		}
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}
}
