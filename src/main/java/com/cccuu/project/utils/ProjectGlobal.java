package com.cccuu.project.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置类
 * @author zhaixiaoliang
 * @version 2017-08-25
 */
public class ProjectGlobal {

	/**
	 * 当前对象实例
	 */
	private static ProjectGlobal global = new ProjectGlobal();

	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String,String>();

	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("config.properties");


	/**
	 * 获取当前对象实例
	 */
	public static ProjectGlobal getInstance() {
		return global;
	}

	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : "");
		}
		return value;
	}

	/**
	 * 页面获取常量
	 */
	public static Object getConst(String field) {
		try {
			return Constants.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取工程路径
	 * @return
	 */
	public static String getProjectPath(){
		// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = ProjectGlobal.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
	}
	public static void main(String[] args) throws IOException {
		System.out.println(getProjectPath());
		System.out.println("com.cccuu.project".replace(".", "\\"));
	}

}
