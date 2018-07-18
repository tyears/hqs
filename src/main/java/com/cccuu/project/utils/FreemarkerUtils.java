package com.cccuu.project.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerUtils {
	
	private static Logger log = Logger.getLogger(FreemarkerUtils.class);
	
	private static final String ENCODING = "UTF-8";
	
	private static Configuration cfg = new Configuration();
	
	//初始化cfg
	static{
		// 这里有三种方式读取  
        // （一个文件目录）  
        // cfg.setDirectoryForTemplateLoading(new File("templates"));  
        // classpath下的一个目录（读取jar文件）  
        cfg.setClassForTemplateLoading(FreemarkerUtils.class,"/templates");  
        // 相对web的根路径来说 根目录  
        //cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "templates");  
        // setEncoding这个方法一定要设置国家及其编码，不然在ftl中的中文在生成html后会变成乱码  
        cfg.setEncoding(Locale.getDefault(), ENCODING);  
          
        // 设置对象的包装器  
        cfg.setObjectWrapper(new DefaultObjectWrapper());  
        // 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错  
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);  

	}
	//获取模板对象
	public static Template getTemplate(String templateFileName) throws IOException{
		return cfg.getTemplate(templateFileName,ENCODING);
	}
	 /** 
     * 据数据及模板生成文件 
     * @param data Map的数据结果集 
     * @param templateFileName ftl模版文件名
     * @param outFilePath 生成文件名称(可带路径) 
     */  
    public static void crateFile(Map<String, Object> data, String templateFileName, String outFilePath) {  
        Writer out = null;  
        try {  
            // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致  
            Template template = getTemplate(templateFileName);  
              
            File outFile = new File(outFilePath);
            if(!outFile.getParentFile().exists()){
            	outFile.getParentFile().mkdirs();
            }
            out = new OutputStreamWriter(new FileOutputStream(outFile), ENCODING);  
              
            // 处理模版  
            template.process(data, out);  
              
            out.flush();  
            log.info("由模板文件" + templateFileName + "生成" + outFilePath + "成功.");  
        } catch (Exception e) {
        	log.error("由模板文件" + templateFileName + "生成" + outFilePath + "出错");
        	e.printStackTrace();
        } finally{ 
            try {
            	/*String osName = System.getProperty("os.name");
            	if(osName.indexOf("Windows")!=-1){
            		Runtime.getRuntime().exec("cmd /c start explorer D:\\project");
            	}*/
                if(out != null){  
                    out.close();  
                }  
            } catch (IOException e) {
            	log.error("关闭Write对象出错", e);
            	e.printStackTrace();
            }  
        }
        
    } 
    public static void main(String[] args) {
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("packageName", "sysuser");
		data.put("className", "SysUser");
		crateFile(data, "MapperTemplate.ftl", "D:/project/SysUserMapper.java");
	}
}
