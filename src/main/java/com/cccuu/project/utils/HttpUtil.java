package com.cccuu.project.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * http访问工具类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:27:47
 */
public class HttpUtil {
    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;
    //读取超时时间
    private static final int READ_TIMEOUT = 5000;
    // 参数编码
    private static final String ENCODE_CHARSET = "utf-8";

    /**
     * 发送HTTP_POST请求
     *
     * @see 本方法默认的连接和读取超时均为30秒
     * @param reqURL
     *            请求地址
     * @param params
     *            发送到远程主机的正文数据[a:1,b:2]
     * @return String
     */
    public static String postRequest(String reqURL, String... params) {
        StringBuilder resultData = new StringBuilder();
        URL url = null;
        try {

            url = new URL(reqURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConn = null;
        InputStreamReader in = null;
        BufferedReader buffer = null;
        String inputLine = null;
        DataOutputStream out = null;
        if (url != null) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setDoInput(true);// 设置输入流采用字节流
                urlConn.setDoOutput(true);// 设置输出流采用字节流
                urlConn.setRequestMethod("POST");
                urlConn.setUseCaches(false); // POST请求不能使用缓存
                urlConn.setInstanceFollowRedirects(true);
                urlConn.setConnectTimeout(CONNECTION_TIMEOUT);// 设置连接超时
                urlConn.setReadTimeout(READ_TIMEOUT); // 设置读取超时
                // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                urlConn.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                urlConn.setRequestProperty("Charset", ENCODE_CHARSET);//
                String param = sendPostParams(params);
                urlConn.setRequestProperty("Content-Length",
                        param.getBytes().length + "");//
                // urlConn.setRequestProperty("Connection", "Keep-Alive");
                // //设置长连接
                urlConn.connect();// 连接服务器发送消息
                if (!"".equals(param)) {
                    out = new DataOutputStream(urlConn.getOutputStream());
                    // 将要上传的内容写入流中
                    out.writeBytes(param);
                    // 刷新、关闭
                    out.flush();
                    out.close();

                }
                in = new InputStreamReader(urlConn.getInputStream(),
                        HttpUtil.ENCODE_CHARSET);
                buffer = new BufferedReader(in);
                if (urlConn.getResponseCode() == 200) {
                    while ((inputLine = buffer.readLine()) != null) {
                        resultData.append(inputLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null) {
                        buffer.close();
                    }

                    if (in != null) {
                        in.close();
                    }

                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultData.toString();
    }

    /**
     * 发送HTTP_GET请求
     *
     * @see 本方法默认的连接和读取超时均为30秒
     * @param httpUrl
     *            请求地址
     * @param map
     *            发送到远程主机的正文数据[a:1,b:2]
     * @return String
     */
    public static String getRequest(String httpUrl, String... params) {
        StringBuilder resultData = new StringBuilder();
        URL url = null;
        try {

            String paramurl = sendGetParams(httpUrl, params);
            url = new URL(paramurl);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConn = null;
        InputStreamReader in = null;
        BufferedReader buffer = null;
        String inputLine = null;
        if (url != null) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestMethod("GET");
                urlConn.setConnectTimeout(CONNECTION_TIMEOUT);
                in = new InputStreamReader(urlConn.getInputStream(),
                        HttpUtil.ENCODE_CHARSET);
                buffer = new BufferedReader(in);
                if (urlConn.getResponseCode() == 200) {
                    while ((inputLine = buffer.readLine()) != null) {
                        resultData.append(inputLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null) {
                        buffer.close();
                    }

                    if (in != null) {
                        in.close();
                    }

                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultData.toString();
    }

    /**
     * Post追加参数
     *
     * @see <code>params</code>
     * @param reqURL
     *            请求地址
     * @param params
     *            发送到远程主机的正文数据[a:1,b:2]
     * @return
     */
    private static String sendPostParams(String... params) {
        StringBuilder sbd = new StringBuilder("");
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                String[] temp = params[i].split(":");
                sbd.append(temp[0]);
                sbd.append("=");
                sbd.append(urlEncode(temp[1]));
                sbd.append("&");

            }
            sbd.setLength(sbd.length() - 1);// 删掉最后一个
        }
        return sbd.toString();
    }

    /**
     * Get追加参数
     *
     * @see <code>params</code>
     * @param reqURL
     *            请求地址
     * @param params
     *            发送到远程主机的正文数据[a:1,b:2]
     * @return
     */
    private static String sendGetParams(String reqURL, String... params) {
        StringBuilder sbd = new StringBuilder(reqURL);
        if (params != null && params.length > 0) {
            if (isexist(reqURL, "?")) {// 存在?
                sbd.append("&");
            } else {
                sbd.append("?");
            }
            for (int i = 0; i < params.length; i++) {
                String[] temp = params[i].split(":");
                sbd.append(temp[0]);
                sbd.append("=");
                sbd.append(urlEncode(temp[1]));
                sbd.append("&");

            }
            sbd.setLength(sbd.length() - 1);// 删掉最后一个
        }
        return sbd.toString();
    }

    // 查找某个字符串是否存在
    private static boolean isexist(String str, String fstr) {
        return str.indexOf(fstr) == -1 ? false : true;
    }

    /**
     * 编码
     *
     * @param source
     * @return
     */
    private static String urlEncode(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder
                    .encode(source, HttpUtil.ENCODE_CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendPost(String url, Map<String, String> parameters) {
        String result = "";// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        PrintWriter out = null;
        StringBuffer sb = new StringBuffer();// 处理请求参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() > 0) {
                if (parameters.size() == 1) {
                    for (String name : parameters.keySet()) {
                        sb.append(name).append("=").append(
                                java.net.URLEncoder.encode(parameters.get(name),
                                        "UTF-8"));
                    }
                    params = sb.toString();
                } else {
                    for (String name : parameters.keySet()) {
                        if (HttpUtil.isNull(parameters.get(name))) {
                            sb.append(name).append("=").append(" ").append("&");
                        } else {
                            sb.append(name).append("=").append(
                                    java.net.URLEncoder.encode(parameters.get(name),
                                            "UTF-8")).append("&");
                        }
                    }
                    String temp_params = sb.toString();
                    params = temp_params.substring(0, temp_params.length() - 1);
                }
            }
            System.out.println("拼接参数：" + params);
            // 创建URL对象
            java.net.URL connURL = new java.net.URL(url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 设置通用属性
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
            httpConn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            // 设置POST方式
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(httpConn.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应，设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn
                    .getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static boolean isNull(String str) {
        return str == null || str.trim().equals("") || str.trim().equals("null");
    }
}
