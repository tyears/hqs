package com.cccuu.project.utils;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Description 密码加密工具类
 * @author zhaixiaoliang
 * @author 2016年11月8日下午3:21:13
 */
public class PassWordEncrypt {
	private final static String MD5KEY = "chiputaobutuputaopi";
	//用MD5加密
	public static String EncryptByMd5(String text){
		text += MD5KEY;
		String encryptText = DigestUtils.md5Hex(getContentBytes(text, "UTF-8"));
		return encryptText;
	}
	
	/**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("获取字节集,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    //test
    public static void main(String[] args) {
    	System.out.println(PassWordEncrypt.EncryptByMd5("admin"));
	}
}
