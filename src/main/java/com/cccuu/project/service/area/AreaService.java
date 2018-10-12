package com.cccuu.project.service.area;

import java.util.List;
import java.util.Map;
import com.cccuu.project.model.area.Area;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;
/**
 * 区域Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年09月13日 16:43:02
 */
public interface AreaService extends BaseService<Area>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Area> queryListByPage(Map<String,String> params);

	/**
	 * 根据父id查询列表
	 * @param fkId
	 * @return
	 */
	public List<Area> getListByFkId(String fkId);

	/**
	 * 保存区域
	 * @param area
	 * @return
	 */
	public Area saveInfo(Area area);

	/**
	 * 删除区域
	 * @param id
	 * @param fkId 父权限id
	 * @return 影响条数
	 */
	public int deleteInfoById(String id, String fkId);

	/**
	 * 查询全部单位市场
	 */
	public List<Map<String,Object>> queryAreaList();

	/**
	 * 根据全名称查询
	 * @return
	 */
	public List<Map<String,Object>> queryAreaOne(String areaName);

	/**
	 * 查询全部单位市场
	 */
	public List<Map<String,Object>> queryAllAreaList(Map<String,String> params);
	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	public int addMulti(List<Area> list);
	public void updateAreaMerit();
	public String getFkId(String areaName);
}