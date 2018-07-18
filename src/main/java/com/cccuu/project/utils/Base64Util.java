package com.cccuu.project.utils;
import java.io.UnsupportedEncodingException;  

import sun.misc.*;  
/**
 * Base64工具类
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年2月23日上午11:23:57
 */
public class Base64Util {  
    /**
     * 字符串转换base64格式
     * @param str
     * @return
     */
    @SuppressWarnings("restriction")
	public static String getBase64(String str) {  
        byte[] b = null;  
        String base64Str = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
        	base64Str = new BASE64Encoder().encode(b);  
        }  
        return base64Str;  
    }  
  
    /**
     * 从base64字符串得到正常字符串
     * @param base64Str
     * @return
     */
    @SuppressWarnings("restriction")
	public static String getStrFromBase64(String base64Str){  
        byte[] b = null;  
        String result = null;  
        if (base64Str != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(base64Str);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }  
}  
