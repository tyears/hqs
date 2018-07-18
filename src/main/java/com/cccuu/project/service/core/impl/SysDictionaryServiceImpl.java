package com.cccuu.project.service.core.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.cccuu.project.mapper.core.SysDictionaryMapper;
import com.cccuu.project.mapper.core.SysDictionaryMenuMapper;
import com.cccuu.project.model.core.SysDictionary;
import com.cccuu.project.model.core.SysDictionaryMenu;
import com.cccuu.project.service.core.SysDictionaryService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("sysDictionaryService")
public class SysDictionaryServiceImpl extends BaseServiceImpl<SysDictionary> implements SysDictionaryService {
	
	private SysDictionaryMapper sysDictionaryMapper;
	
	@Autowired
	private SysDictionaryMenuMapper sysDictionaryMenuMapper;
	
	@Autowired
	public void setSysDictionaryDao(SysDictionaryMapper sysDictionaryMapper) {
		this.sysDictionaryMapper = sysDictionaryMapper;
		baseMapper = sysDictionaryMapper;
	}
	
	@Override
	@Transactional
	public PageInfo<SysDictionary> queryListByPage(Map<String, String> params) {
		// TODO Auto-generated method stub
		//设置查询条件
		Example example = new Example(SysDictionary.class);
		example.setOrderByClause(" create_time desc");
		Example.Criteria creiteria = example.createCriteria();
		String name = params.get("name");
		if(StringUtils.isNotBlank(name)){
			creiteria.andLike("name","%"+name+"%");
		}
		String code = params.get("code");
		if(StringUtils.isNotBlank(code)){
			creiteria.andLike("code","%"+code+"%");
		}
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<SysDictionary> results = sysDictionaryMapper.selectByExample(example);
		
		PageInfo<SysDictionary> pageInfo = new PageInfo<SysDictionary>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public int deleteInfoById(String id) {
		// TODO Auto-generated method stub
		SysDictionaryMenu tempObj = new SysDictionaryMenu();
		tempObj.setFkId(id);
		sysDictionaryMenuMapper.delete(tempObj);
		return sysDictionaryMapper.deleteByPrimaryKey(id);
	}



}
