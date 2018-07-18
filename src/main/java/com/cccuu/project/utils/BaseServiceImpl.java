package com.cccuu.project.utils;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基础service实现类
 *
 * @param <T>
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:26:25
 * @Description
 */
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

    protected Logger log = Logger.getLogger(getClass());
    
    protected BaseMapper<T> baseMapper;

	@Override
	public T get(String id) {
		// TODO Auto-generated method stub
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public T insertSelective(T t) {
		// TODO Auto-generated method stub
		t = setID(t, UniqueIDGen.getUniqueID()+"");
		baseMapper.insertSelective(t);
		return t;
	}

	@Override
	public T updateSelective(T t) {
		// TODO Auto-generated method stub
		baseMapper.updateByPrimaryKeySelective(t);
		return t;
	}

	@Override
	public T update(T t) {
		// TODO Auto-generated method stub
		baseMapper.updateByPrimaryKey(t);
		return t;
	}
	@Override
	public T insertOrUpdate(T t){
		//插入
		if(StringUtils.isBlank(getID(t))){
			t = setID(t, UniqueIDGen.getUniqueID()+"");
			baseMapper.insertSelective(t);
		}else{//更新
			baseMapper.updateByPrimaryKeySelective(t);
		}
		return t;
	}

	@Override
	public T getBySelective(T t) {
		// TODO Auto-generated method stub
		return baseMapper.selectOne(t);
	}

	@Override
	public List<T> queryBySelective(T t) {
		// TODO Auto-generated method stub
		return baseMapper.select(t);
	}
	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return baseMapper.selectAll();
	}
	/**
	 * 获取对象的id
	 * @param t
	 * @return
	 */
	protected String getID(T t){
		String id = null;
		Class<? extends Object> classT = t.getClass().getSuperclass();
		try {
			Method getIdMethod = classT.getDeclaredMethod("getId");
			Object IdObj = getIdMethod.invoke(t);
			if(IdObj!=null){
				id = IdObj.toString();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("获取对象id出错："+e.getMessage());
		}
		return id;
	}
	/**
	 * 设置对象的id
	 * @param t
	 * @param id
	 * @return
	 */
	protected T setID(T t,String id){
		Class<? extends Object> classT = t.getClass().getSuperclass();
		try {
			Method IdSetMethod = classT.getDeclaredMethod("setId",String.class);
			IdSetMethod.invoke(t, id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("设置对象id出错："+e.getMessage());
		}
		return t;
	}
	
}
