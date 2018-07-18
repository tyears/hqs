package com.cccuu.project.utils;

import java.util.HashMap;
import java.util.Map;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HttpRequest request = new HttpRequest("http://localhost:8080/shengda/bankCardApp/myBnkCards.do");
		//HttpRequest request = new HttpRequest("http://localhost:8080/shengda/bankCardApp/getBankCardInfo.do");
		//HttpRequest request = new HttpRequest("http://localhost:8080/shengda/areaApp/areaList.do");
		request.setMethod("POST");
		request.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		Map<String,Object> map = new HashMap<String,Object>();
		//map.put("userId", "1496194633934567");
		map.put("id", "1496213776587472");
		request.setPostData(map);
		try {
			HttpResponse response = request.getResponse();
			String result = response.getResult();
			System.out.println("结果为："+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
