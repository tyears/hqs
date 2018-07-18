package com.cccuu.project.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.service.core.SysDictionaryMenuService;

public class DictionarySelectTag extends TagSupport{

	private static final long serialVersionUID = 1L;
	
	private String dictionaryCode;
	private String id;
	private String name;
	private String value;
	private String style;
	private String clazz;

	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		StringBuffer html = new StringBuffer("<select");
		html.append(" id='"+id+"'");
		html.append(" name='"+name+"'");
		if(StringUtils.isNotBlank(style)){
			html.append(" style='"+style+"'");
		}
		if(StringUtils.isNotBlank(clazz)){
			html.append(" class='"+clazz+"'");
		}
		html.append(">");
		//获取字典项列表
		SysDictionaryMenuService sysDictionaryMenuService = SpringContextUtil.getBean("sysDictionaryMenuService");
		List<SysDictionaryMenu> resultList = sysDictionaryMenuService.queryListByDictionaryCode(dictionaryCode);
		html.append("<option value=''>--请选择--</option>");
		for (SysDictionaryMenu sysDictionaryMenu : resultList) {
			String selected = StringUtils.isNotBlank(value)&&value.equals(sysDictionaryMenu.getValue())?"selected":"";
			html.append("<option value='"+sysDictionaryMenu.getValue()+"'"+selected+">"+sysDictionaryMenu.getName()+"</option>");
		}
		html.append("</select>");
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getDictionaryCode() {
		return dictionaryCode;
	}

	public void setDictionaryCode(String dictionaryCode) {
		this.dictionaryCode = dictionaryCode;
	}
}
