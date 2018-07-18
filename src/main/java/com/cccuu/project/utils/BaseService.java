package com.cccuu.project.utils;

import java.util.List;

/**
 * 基础service接口
 * @param <T>
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:26:08
 * @Description
 */
public interface BaseService<T> {
	/**
	 * 根据id获取记录
	 * @param id
	 * @return
	 */
	T get(String id);
	/**
	 * 根据id删除记录
	 * @param id
	 * @return
	 */
	int deleteById(String id);
	/**
	 * 插入对象（不为空的字段）
	 * @param record
	 * @return
	 */
	T insertSelective(T t);
	/**
	 * 更新不为空的字段
	 * @param record
	 * @return
	 */
	T updateSelective(T t);
	/**
	 * 更新所有字段
	 * @param record
	 * @return
	 */
    T update(T t);
    /**
     * 插入或更新对象（不为空的字段）
     * @param t
     * @return
     */
    T insertOrUpdate(T t);
    /**
     * 根据模板查询单一对象
     * @param record
     * @return
     */
    T getBySelective(T t);
    /**
     * 根据模板查询对象集合
     * @param record
     * @return
     */
    List<T> queryBySelective(T t);
    /**
     * 查询所有
     * @return
     */
    List<T> findAll();
}
