package com.cccuu.project.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * http响应信息封装类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:27:15
 */
public class HttpResponse {

	private String result;
	private Map<String, List<String>> headerMap = new HashMap<>();
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public void setHeaders(Map<String, List<String>> headers){
		this.headerMap = headers;
	}
	
	public Map<String, List<String>> getHeaders(){
		return headerMap;
	}
}

