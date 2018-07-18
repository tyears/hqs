package com.cccuu.project.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 自定义注解 拦截Controller  
 * @Description 
 * @author zhaixiaoliang
 * @author 2017年8月17日上午10:07:21
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented 
public @interface SystemControllerLog {
	
	String description()  default ""; 
	
}
