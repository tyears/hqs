package com.cccuu.project.utils;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @Package com.cccuu.project.utils
 * @Author ke
 * @DATE 2017/10/31.
 */
public class JuHeUtils {

    public static String getNumberAttribution(String phone){
        String text="未知";
        try {
            for(int i=0;i<5;i++){
                String retu = HttpUtil.postRequest(ProjectGlobal.getConfig("weizhiService"),"phone:"+phone);
                Map map = JSON.parseObject(retu);
                if(0 == (Integer) map.get("error_code")){
                    map= (Map) map.get("result");
                    text= (String) map.get("province")+map.get("city");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
