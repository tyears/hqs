package com.cccuu.project.utils;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringEx {
	/**
	 * 产生随机字符串（长度自定）
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度  
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();
	 }
	/**
	 * 生成随机唯一随机字符串
	 * @param stList  字符串集合
	 * @param i		要生成的字符串的长度
	 * @return
	 */
	public static String generate(List<String> stList,int i){
		String newst = getRandomString(i);
		boolean repeat = true;
		for (String st : stList) {
			if(StringUtils.isNotBlank(st)){
				if(st.equals(newst)){
					repeat = false;
					break;
				}
			}
		}
		if(repeat){
			return newst;
		}else{
			generate(stList,i); 
		}
		return null;
	}
	/**
	 * 返回context中 汉字的数量
	 * @param context
	 * @return
	 */
	public static int getChineseNum(String context){
        int lenOfChinese=0;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(context);
        while(m.find()){
            lenOfChinese++;
        }
        return lenOfChinese;
    }
	
}
