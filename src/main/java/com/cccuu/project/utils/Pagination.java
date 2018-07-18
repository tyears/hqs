package com.cccuu.project.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页数据信息封装类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:28:26
 * @param <T>
 */
public class Pagination<T> implements Serializable{
	public static final int DEFAULT_PAGE_SIZE = 10;

	private static final long serialVersionUID = 1188127446407349574L;
	
	private int totalCount = 0;
	private int pageSize = 10;
	private int pageNo = 1;
	private List<T> results;
	
	public Pagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public Pagination(int pageNo, int pageSize, int totalCount) {
		setPageSize(pageSize);
		setPageNo(pageNo);
		setTotalCount(totalCount);
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public Pagination(int pageNo, int pageSize, int totalCount, List<T> results) {
		this(pageNo,pageSize,totalCount);
		this.results = results;
	}
	
	
	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getPageNo()
	 */
	public int getPageNo() {
		return pageNo;
	}
	
	/**
	 * 设置当前页码
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else{
			this.pageNo = pageNo;
		}
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getPageSize()
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置每页条数
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEFAULT_PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getTotalCount()
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * 设置记录条数
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
		
		int totalPage = getTotalPage();
		if(this.pageNo > totalPage){
			this.pageNo = totalPage;
		}
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getTotalPage()
	 */
	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	
	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#isFirstPage()
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#isLastPage()
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}
	
	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getPrePage()
	 */
	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getNextPage()
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	/* (non-Javadoc)
	 * @see net.huaat.commons.paging.IPagination#getResults()
	 */
	public List<T> getResults() {
		return results;
	}

	/**
	 * 设置记录
	 * @param results
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	/**
	 * 获取当前也第一条的序号
	 * @return
	 */
	public int getCurrPageFirst() {
		return (pageNo - 1) * pageSize;
	}
}
