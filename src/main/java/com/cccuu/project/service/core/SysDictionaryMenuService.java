package com.cccuu.project.service.core;

import java.util.List;

import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.utils.BaseService;

public interface SysDictionaryMenuService extends BaseService<SysDictionaryMenu> {
	
	/**
	 * 根据字典code查询字典项
	 * @param dictionaryCode
	 * @return
	 */
	public List<SysDictionaryMenu> queryListByDictionaryCode(String dictionaryCode);
	/**
	 * 根据字典code和字典项值获取字典显示值
	 * @param dictionaryCode
	 * @param value
	 * @return
	 */
	public String getNameByDictionaryCodeValue(String dictionaryCode,String value);

}
