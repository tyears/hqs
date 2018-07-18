package com.cccuu.project.utils;

import java.io.Serializable;

public class ReturnInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean result;//结果
	private String msg;//信息
	private Object obj;//数据
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
}
