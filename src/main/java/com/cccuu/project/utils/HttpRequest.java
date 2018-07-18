package com.cccuu.project.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
/**
 * http请求信息封装类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:26:54
 */
public class HttpRequest {

	private String url;											//请求的URL地址
	private Proxy proxy;										//代理
	private String method;										//请求类型
	private boolean doInput = true;								//是否接受输入
	private boolean doOutput = false;							//是否接受输出
	private int readTimeout = 0;								//读取返回信息超时时间
	private int connectTimeout = 0;					  			//连接服务器超时时间
	private int code;											//记录返回状态
	private Map<String,List<String>> headerMap = new HashMap<>();		//封装请求参数
	private String postParams;									//POST请求参数 类似 a=1&b=2&c=3
	
	public String getPostData() {
		return postParams;
	}

	/**  
	 * 封装POST请求参数
	 * @param map
	 */
	public void setPostData(Map<String, Object> map) {
		System.out.println(map);
		if(map != null){
			int cnt = 0;
			StringBuilder sb = new StringBuilder();
			for(Entry<String, Object> entry :map.entrySet()){
				if(cnt!=0){
					sb.append("&");
				}
				sb.append(entry.getKey()).append("=").append(entry.getValue());
				cnt++;
			}
			postParams = sb.toString();
		}
	}
	
	public HttpRequest(){}
	
	public HttpRequest(String url){
		this.url = url;
	}
	
	public HttpRequest(String url,Proxy proxy){
		this(url);
		this.proxy = proxy;
	}
	
	/**
	 * 获取输出
	 * @param charsetName 字符编码
	 * @return 
	 * @throws Exception
	 */
	public HttpResponse getResponse(String charsetName) throws Exception{
		URL uri = null;
		if(url!=null){
			uri = new URL(url);
		}else{
			throw new RuntimeException("URL IS NULL");
		}
		HttpResponse response = new HttpResponse();
		HttpURLConnection con = null;
		InputStream is;
		if(proxy == null){
			con = (HttpURLConnection) uri.openConnection();
		}else{
			con = (HttpURLConnection) uri.openConnection(proxy);
		}
		if(this.method.equalsIgnoreCase("post")){
			this.doOutput = true;
		}
		this.config(con);
		
		/**设置POST请求参数*/
		if(this.doOutput==true && this.postParams != null){
			OutputStream os = con.getOutputStream();
			os.write(postParams.getBytes());
			os.flush();
			os.close();
		}
		con.connect();
		code = con.getResponseCode();
		if(code == HttpURLConnection.HTTP_NOT_FOUND){
			response.setResult("您要访问的页面不存在。");
			return response;
		}
		if(code == HttpURLConnection.HTTP_INTERNAL_ERROR){
			throw new RuntimeException("服务器异常。");
		}
		if(code==HttpURLConnection.HTTP_OK){
			is = con.getInputStream();
			String line;
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,charsetName));
			while(null != (line=reader.readLine())){
				sb.append(line);
				sb.append("\r\n");
			}
			reader.close();
			response.setResult(sb.toString());
			
			Map<String, List<String>> headerFields = con.getHeaderFields();
			response.setHeaders(headerFields);
			//自动维持Cookie
//			for (Entry<String, List<String>> e : headerFields.entrySet()) {
//				System.out.println(e.getKey()+"==="+e.getValue());
//			}
			
		}
		return response;
	}
	
	/**
	 * 设置请求参数
	 * @param con
	 * @throws ProtocolException
	 */
	private void config(HttpURLConnection con) throws ProtocolException {
		con.setRequestMethod(method);
		con.setDoOutput(doOutput);
		con.setDoInput(doInput);
		con.setUseCaches(false);
		con.setReadTimeout(readTimeout );
		con.setConnectTimeout(connectTimeout);
		if(headerMap != null){
			for (Entry<String, List<String>> entry : headerMap.entrySet()) {
				String key = entry.getKey();
				List<String> valueList = entry.getValue();
				for (String value : valueList) {
					con.setRequestProperty(key, value);
				}
				
			}
		}
	}
	
	public void setUserAgent(String userAgent){
		this.addHeader("User-Agent", userAgent);
	}

	public HttpResponse getResponse() throws Exception{
		return this.getResponse("UTF-8");
	}
	
	public void addHeader(String name,String value){
		if(name!=null && value!=null){
			List<String> list = headerMap.get(name);
			if(list==null){
				list = new ArrayList<String>();
			}
			list.add(value);
			headerMap.put(name, list);
		}
		
	}
	
	public void setHeaders(Map<String, List<String>> headers){
		if(headers!=null)
			this.headerMap = headers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	
}
