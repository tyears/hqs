package com.cccuu.project.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	private static Properties prop = new Properties();
	static{
		try {
			prop.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("------------------配置文件加载失败！----------------------");
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return prop.getProperty(key);
	}
	public static void main(String[] args) {
		System.out.println(ConfigUtil.getValue("notify_url"));
	}
}
