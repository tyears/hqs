package com.cccuu.project.service.core.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysDictionaryMapper;
import com.cccuu.project.mapper.core.SysDictionaryMenuMapper;
import com.cccuu.project.model.core.SysDictionary;
import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.service.core.SysDictionaryMenuService;
import com.cccuu.project.utils.BaseServiceImpl;

@Service("sysDictionaryMenuService")
public class SysDictionaryMenuServiceImpl extends BaseServiceImpl<SysDictionaryMenu> implements SysDictionaryMenuService {

	private SysDictionaryMenuMapper sysDictionaryMenuMapper;
	
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;
	
	@Autowired
	public void setSysDictionaryMenuDao(SysDictionaryMenuMapper sysDictionaryMenuMapper) {
		this.sysDictionaryMenuMapper = sysDictionaryMenuMapper;
		baseMapper = sysDictionaryMenuMapper;
	}

	@Override
	@Transactional
	public List<SysDictionaryMenu> queryListByDictionaryCode(
			String dictionaryCode) {
		// TODO Auto-generated method stub
		List<SysDictionaryMenu> resultList = new ArrayList<SysDictionaryMenu>();
		SysDictionary dictionary = new SysDictionary();
		dictionary.setCode(dictionaryCode);
		dictionary = sysDictionaryMapper.selectOne(dictionary);
		if(dictionary!=null){
			Example example = new Example(SysDictionaryMenu.class);
			example.setOrderByClause("sort asc");
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("fkId", dictionary.getId());
			resultList = sysDictionaryMenuMapper.selectByExample(example);
		}
		return resultList;
	}

	@Override
	@Transactional
	public String getNameByDictionaryCodeValue(String dictionaryCode,String value) {
		// TODO Auto-generated method stub
		StringBuffer strBuf = new StringBuffer("");
		if(StringUtils.isNotBlank(value)&&StringUtils.isNotBlank(dictionaryCode)){
			SysDictionary dictionary = new SysDictionary();
			dictionary.setCode(dictionaryCode);
			dictionary = sysDictionaryMapper.selectOne(dictionary);
			if(dictionary!=null){
				String[] valueArry = value.split(",");
				Example example = new Example(SysDictionaryMenu.class);
				example.setOrderByClause("sort asc");
				Example.Criteria criteria = example.createCriteria();
				criteria.andIn("value", Arrays.asList(valueArry));
				List<SysDictionaryMenu> resultList = sysDictionaryMenuMapper.selectByExample(example);
				if(resultList!=null&&resultList.size()>0){
					for (int i = 0; i < resultList.size(); i++) {
						if(i==resultList.size()-1){
							strBuf.append(resultList.get(i).getName());
						}else{
							strBuf.append(resultList.get(i).getName()+",");
						}
					}
				}
			}
		}
		return strBuf.toString();
	}
}
