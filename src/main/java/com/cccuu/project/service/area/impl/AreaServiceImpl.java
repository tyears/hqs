package com.cccuu.project.service.area.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cccuu.project.mapper.area.AreaProductMapper;
import com.cccuu.project.utils.UniqueIDGen;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.area.Area;
import com.cccuu.project.mapper.area.AreaMapper;
import com.cccuu.project.service.area.AreaService;
import com.cccuu.project.utils.BaseServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 区域ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 16:43:02
 */
@Service("areaService")
public class AreaServiceImpl extends BaseServiceImpl<Area> implements AreaService{
	
	private AreaMapper areaMapper;
	
	@Autowired
	public void setAreaMapper(AreaMapper areaMapper){
		this.areaMapper = areaMapper;
		baseMapper = areaMapper;
	}
	@Autowired
	private AreaProductMapper areaProductMapper;
	@Override
	@Transactional
	public PageInfo<Area> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(Area.class);
		example.setOrderByClause(" sort asc");
		Example.Criteria creiteria = example.createCriteria();
		String areaName = params.get("areaName");
		if(StringUtils.isNotBlank(areaName)){
			creiteria.andLike("areaName","%"+areaName+"%");
		}
		String fkId = params.get("fkId");
		if(StringUtils.isNotBlank(fkId)){
			creiteria.andEqualTo("fkId", fkId);
		}
		
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Area> results = areaMapper.selectByExample(example);
		
		PageInfo<Area> pageInfo = new PageInfo<Area>(results);
		
		return pageInfo;
	}

	@Override
	@Transactional
	public List<Area> getListByFkId(String fkId) {
		List<Area> resultList = new ArrayList<>();
		if(StringUtils.isBlank(fkId)){
			Area rootModule = new Area();
			rootModule.setId("0");
			rootModule.setIsParent("true");
			rootModule.setAreaName("全国");
			resultList.add(rootModule);
		}else{
			Example example = new Example(Area.class);
			example.setOrderByClause("sort asc");
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("fkId", fkId);
			resultList = areaMapper.selectByExample(example);
		}
		return resultList;
	}

	@Override
	@Transactional
	public Area saveInfo(Area area) {
		if(StringUtils.isNotBlank(area.getFkId())&&!"0".equals(area.getFkId())){
			Area parentArea = areaMapper.selectByPrimaryKey(area.getFkId());
			if(StringUtils.isBlank(parentArea.getIsParent())||!parentArea.getIsParent().equals("true")){
				parentArea.setIsParent("true");
				areaMapper.updateByPrimaryKeySelective(parentArea);
			}
		}
		//查询自身是否有子权限
		if(StringUtils.isNotBlank(area.getId())&&"false".equals(area.getIsParent())){

			Example example = new Example(Area.class);
			example.setOrderByClause("sort asc");
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("fkId", area.getId());
			List<Area> queryResult = areaMapper.selectByExample(example);
			if(queryResult!=null&&queryResult.size()>0){
				area.setIsParent("true");
			}
		}
		if(StringUtils.isNotBlank(area.getId())){

			areaMapper.updateByPrimaryKeySelective(area);
		}else{
			area.setId(UniqueIDGen.getUniqueID()+"");
			areaMapper.insertSelective(area);
		}
		return area;
	}

	@Override
	@Transactional
	public int deleteInfoById(String id, String fkId) {
		int result = areaMapper.deleteByPrimaryKey(id);

		if(StringUtils.isNotBlank(fkId)&&!"0".equals(fkId)){
			//查询父权限，判断是否还存在子权限
			Area tempObj = new Area();
			tempObj.setFkId(fkId);
			List<Area> areaList = areaMapper.select(tempObj);
			if(areaList==null||areaList.size()==0){
				Area area = areaMapper.selectByPrimaryKey(fkId);
				area.setIsParent("false");
				areaMapper.updateByPrimaryKeySelective(area);
			}
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> queryAreaList() {
		return areaProductMapper.queryAreaList(new HashMap<String, Object>());
	}

	@Override
	public List<Map<String, Object>> queryAreaOne(String areaName) {
		return areaProductMapper.queryAreaOne(areaName);
	}

	@Override
	@Transactional
	public List<Map<String, Object>> queryAllAreaList(Map<String, String> params) {
		//设置查询条件
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		//这里处理参数
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if(StringUtils.isNotBlank(entry.getValue())){
				parameterMap.put(entry.getKey(), entry.getValue());
			}
		}
		return areaProductMapper.queryAreaList(parameterMap);
	}

	@Override
	@Transactional
	public int addMulti(List<Area> list) {
		return areaMapper.addMulti(list);
	}

}