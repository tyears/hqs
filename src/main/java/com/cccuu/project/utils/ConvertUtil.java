package com.cccuu.project.utils;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;


public class ConvertUtil {
	
	public static Boolean obj2Boolean(Object obj){
		return obj == null ? false : Boolean.getBoolean(obj.toString());
	}
	/**
	 * List转Map
	 * @param dataList
	 * @param key
	 * @return
	 * 作者：杨凯
	 */
	public static Map<Object,Object> list2Map(List<Map<Object,Object>> dataList,Object key){
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		if (dataList != null && dataList.size() > 0) {
			for (Map<Object,Object> map : dataList) {
				if (map.get(key) != null) {
					resultMap.put(map.get(key), map);
				}
			}
		}
		return resultMap;
	}
	
	/**
	 * List转Map
	 * @param dataList
	 * @param key
	 * @param value
	 * @return
	 * 作者：杨凯
	 */
	public static Map<Object,Object> list2Map(List<Map<Object,Object>> dataList,Object key,Object value){
		Map<Object,Object> resultMap = new LinkedHashMap<Object,Object>();
		if (dataList != null && dataList.size() > 0) {
			for (Map<Object,Object> map : dataList) {
				resultMap.put(map.get(key), map.get(value));
			}
		}
		return resultMap;
	}
 	
	
	/**
	 * 如果为空，使用提供的值替换
	 * @param obj
	 * @param o
	 * @return
	 * 作者：杨凯
	 */
	public static Object defaultIfEmpty(Object obj,Object o){
		return obj == null || StringUtils.isBlank(obj.toString()) || StringUtils.equalsIgnoreCase(obj.toString(), "null") ? o : obj;
	}
	
	/**將GBK转化为GBK
	 * @param str
	 * @return
	 * 作者：杨凯
	 */
	public static String gbk2UTF8(String str){
		if (StringUtils.isBlank(str)) {
			return str;
		}
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String html2Text(String html){
		return html.replaceAll("<[^>]+>", "").replaceAll("&nbsp;","");
	}
	
	/**
	 * 功能描述：Object类转换为String，避免在Object为null时，直接toString()出错<BR>
	 * @param obj
	 * @return
	 * @author:杨凯<BR>
	 * 时间：Mar 22, 2009 10:17:29 PM<BR>
	 */
	public static String obj2Str(Object obj) {
		return obj == null ? null : obj.toString();
	}
	public static String obj2StrBlank(Object obj) {
		return obj == null ? "" : obj.toString();
	}
	public static Integer obj2Integer(Object obj){
		return obj == null || obj.toString().trim().equals("")  ? null : Integer.parseInt(obj.toString()); 
	}
	
	public static int obj2Int(Object obj){
		if(null==obj) {
			System.out.println("1121313");
		}
		return obj == null || obj.toString().trim().equals("")  ? null : Double.valueOf(obj.toString()).intValue(); 
	}
	
	public static Long obj2Long(Object obj){
		return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString()); 
	}
	
	public static Long obj2Long(Object obj,boolean filter){
		if (!filter) {
			return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString()); 
		} else {
			return obj == null || obj.toString().trim().equals("") ? null : Long.parseLong(obj.toString().replaceAll("[^0-9]", "")); 
		}
	}
	
	/**
	 * 功能描述：过滤用户输入的html、sql、javascript脚本<BR>
	 * @param map
	 * @return
	 * @author:杨凯<BR>
	 * 时间：Mar 23, 2009 3:41:12 PM<BR>
	 */
	public static Map<String,Object> filter(Map<String,Object> map) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		for (Map.Entry<String,Object> entry : map.entrySet()) {
			String value = StringEscapeUtils.escapeHtml(ConvertUtil.obj2Str(entry.getValue()));
			value = StringEscapeUtils.escapeSql(value);
			value = StringEscapeUtils.escapeJavaScript(value);
			resultMap.put(entry.getKey(), value);
		}
		return resultMap;
	}
    public static  Map<String, Object> beanToMap(Object model) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if (model == null)
            return null;
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> class1 = model.getClass();
        Class<?> upClasses = class1.getSuperclass();
        //添加父类属性
        fieldList.addAll(Arrays.asList(upClasses.getDeclaredFields()));
        //添加类属性
        fieldList.addAll(Arrays.asList(class1.getDeclaredFields()));

        for (Field field : fieldList) {
            String fieldName = field.getName();
            Object fieldValue=null;
            try {
                Method method = class1.getMethod(
                        "get" + Character.toUpperCase(fieldName.charAt(0))
                                + fieldName.substring(1), model.getClass());
                fieldValue = method.invoke(class1);
            } catch (Exception e) {
                try {
                    if (!Modifier.isPublic(field.getModifiers())) {
                        field.setAccessible(true);
                    }

                    fieldValue = field.get(model);
                } catch (Exception exception) {

                }
            }
            if (fieldValue != null) {
                if (fieldValue instanceof String) {
                    String fieldValueString = String.valueOf(fieldValue);
                    if (StringUtils.isNotBlank(fieldValueString)) {
                        resultMap.put(fieldName, fieldValueString.trim()); // 处理查询参数
                    }
                } else if(fieldValue instanceof Long ||fieldValue instanceof Integer){
                    resultMap.put(fieldName, fieldValue);
                    //getConditionMap(fieldValue);// 处理查询参数TODO
                }
            }
        }
        return resultMap;
    }
    
    /**
     * 将byte转化为K或M，大于1024K的按M计算，返回*K或*M
     * @param byteSize
     * @param scale 保留小数位数
     * @return
     */
    public static String byte2KM(double byteSize, int scale) {
    	BigDecimal b1 = new BigDecimal(byteSize);
        BigDecimal b2 = new BigDecimal(1024);
        double k = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    	
        //大于1024，转化为M
    	if (k > 1024) {
    		b2 = new BigDecimal(1024*1024);
    		k = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    		
    		return k + "M";
    	}
    	
    	return k + "K";
    }
}
