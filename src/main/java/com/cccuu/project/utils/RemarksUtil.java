package com.cccuu.project.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RemarksUtil {
    private static Properties prop = new Properties();
    static{
        try {
            prop.load(new InputStreamReader(RemarksUtil.class.getClassLoader().getResourceAsStream("remarks.properties"),"UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("------------------配置文件加载失败！----------------------");
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return prop.getProperty(key);
    }
    public static void setValue(String key,String value){
        prop.setProperty(key,value);
    }
    public static void main(String[] args) {
        RemarksUtil.setValue("projectPath","你好");
        System.out.println(RemarksUtil.getAll());
    }

    public static Map getAll(){
        Map map = new HashMap();
        for (Object key:prop.keySet()) {
            map.put((String)key,prop.getProperty((String) key));
        }
        return map;
    }

    public static void setAll(HttpServletRequest request){
        for (Object key:request.getParameterMap().keySet()) {
            prop.setProperty((String)key,request.getParameter((String)key));
        }
    }
}
