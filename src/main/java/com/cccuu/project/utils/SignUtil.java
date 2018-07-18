package com.cccuu.project.utils;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author zhaixiaoliang
 * @Description
 * @date 2017/6/16
 */
public class SignUtil {
    private  final static String SIGN_KEY = "jutouwangmingyuditousiguxiang";
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};
    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }
    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin){
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 签名算法
     * @param paramsMap
     * @return
     */
    public static String sign(Map<String,String> paramsMap){
        Map<String,String> filterMap = new HashMap<String,String>();
        if (paramsMap==null||paramsMap.size()==0){
            filterMap =  paramsMap;
        }else{
            for(Map.Entry<String,String> entry:paramsMap.entrySet()){
                String mapValue = entry.getValue();
                String mapKey = entry.getKey();
                if(StringUtils.isBlank(mapValue)||"signStr".equalsIgnoreCase(mapKey)){
                    continue;
                }
                filterMap.put(mapKey,mapValue);
            }
        }
        List<String> keys = new ArrayList<String>(filterMap.keySet());

        Collections.sort(keys);

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = filterMap.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                builder.append(key + "=" + value);
                //最后拼接上加密key
                builder.append("&key=" + SIGN_KEY);
            } else {
                builder.append(key + "=" + value + "&");
            }
        }
        return MD5Encode(builder.toString()).toLowerCase();
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(MD5Encode("admin"));
    }
}
