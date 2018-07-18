package com.cccuu.project.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
/**
 * xml工具类只适用根节点有一层子节点的xml
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年5月26日下午4:12:36
 */
public class XmlUtils {
	/**
	 * 根据节点名称获取节点值
	 * @param elementName
	 * @param filePath
	 * @return
	 */
	public static String getText(String elementName,String filePath){
		String text = null;
        try {
			Document document = new SAXReader().read(new File(filePath));
			Element element = document.getRootElement().element(elementName);
			text = element.getText();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return text;
	}
	/**
	 * 获取所有节点信息
	 * @param filePath
	 * @return
	 */
	public static Map<String,String> getMapText(String filePath){
		Map<String,String> mapText = new HashMap<String,String>();
		try {
			Document document = new SAXReader().read(new File(filePath));
			@SuppressWarnings("unchecked")
			List<Element> elementList = document.getRootElement().elements();
			if(elementList!=null&&elementList.size()>0){
				for (Element element : elementList) {
					mapText.put(element.getName(), element.getText());
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapText;
	}
	/**
	 * 设置节点的内容
	 * @param elementName 节点名称
	 * @param elementText 节点内容
	 * @param filePath 文件路径
	 */
	public static boolean setText(String elementName,String elementText,String filePath){
		boolean result = true;
		try {
			Document document = new SAXReader().read(new File(filePath));
			Element element = document.getRootElement().element(elementName);
			if(!element.getText().equals(elementText)){
				element.setText(elementText);
				FileOutputStream out = new FileOutputStream(new File(filePath));
				// 指定文本的写出的格式：
				OutputFormat format = OutputFormat.createPrettyPrint(); //漂亮格式：有空格换行
				format.setEncoding("UTF-8");
				//1.创建写出对象
				XMLWriter writer=new XMLWriter(out,format);
				//2.写出Document对象
				writer.write(document);
				//3.关闭流
				writer.close();
				out.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	/**
	 * 设置节点的内容
	 * @param elements
	 * @param filePath
	 */
	public static boolean setText(Map<String,String> elements,String filePath){
		boolean result = true;
		try {
			Document document = new SAXReader().read(new File(filePath));
			Element rootElement = document.getRootElement();
			if(elements!=null&&elements.size()>0){
				for (Map.Entry<String, String> entry : elements.entrySet()) {
					rootElement.element(entry.getKey()).setText(entry.getValue());
				}
				FileOutputStream out = new FileOutputStream(new File(filePath));
				// 指定文本的写出的格式：
		        OutputFormat format = OutputFormat.createPrettyPrint(); //漂亮格式：有空格换行
		        format.setEncoding("UTF-8");
		        //1.创建写出对象
		        XMLWriter writer=new XMLWriter(out,format);
		        //2.写出Document对象
		        writer.write(document);
		        //3.关闭流
		        writer.close();
		        out.close();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		}
		return result;
	}

}
