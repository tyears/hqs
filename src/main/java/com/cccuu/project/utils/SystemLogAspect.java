package com.cccuu.project.utils;  
import java.lang.reflect.Method;  
import java.util.Date;  
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;  
import org.aspectj.lang.annotation.Aspect;  
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cccuu.project.model.log.Log;
import com.cccuu.project.service.log.LogService;

@Component
@Aspect  
public class SystemLogAspect {  
      
	//本地异常日志记录对象    
    Logger logger = Logger.getLogger(SystemLogAspect.class);
      
    @Autowired  
    private LogService logService;  
      
    //Controller层切点    
    @Pointcut("@annotation(com.cccuu.project.utils.SystemControllerLog)")    
    public  void controllerAspect() {    
    }    
      
    /**  
     * 前置通知 用于拦截Controller层记录用户的操作  
     *  
     * @param joinPoint 切点  
     */
    
    @Before("controllerAspect()")    
	public void doBefore(JoinPoint joinPoint) {
	
	    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
	    HttpSession session = request.getSession();    
	    //读取session中的用户    
	    Object object = session.getAttribute(Constants.SYS_SESSION_USER);
	    SysUserInfo sysUserInfo = new SysUserInfo();
	    sysUserInfo.setName("无");
	    if(object!=null){
	    	sysUserInfo = (SysUserInfo)object;
	    }
	    //请求的IP    
	    String ip = request.getRemoteAddr();    
	     try {    
	        //*========控制台输出=========*//    
	        System.out.println("=====前置通知开始=====");    
	        System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));    
	        System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));    
	        System.out.println("请求人:" + sysUserInfo.getName());    
	        System.out.println("请求IP:" + ip);    
	        //*========数据库日志=========*//    
	        Log log = new Log();
	        log.setOperation(getControllerMethodDescription(joinPoint));
	        log.setContent(getControllerMethodDescription(joinPoint));
	        log.setMethod(sysUserInfo.getName());
	        log.setUserId(sysUserInfo.getSysUserId());
	        log.setType("0");    
	        log.setRequestIp(ip);    
	        log.setCreateTime(new Date());    
	        //保存数据库    
	        logService.insertSelective(log);    
	        System.out.println("=====前置通知结束=====");    
	    }  catch (Exception e) {    
	        //记录本地异常日志    
	        logger.error("==前置通知异常==");    
	        logger.error(e.getMessage());    
	    }    
	}  
    /**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
    public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();    
        String methodName = joinPoint.getSignature().getName();    
        Object[] arguments = joinPoint.getArgs();    
        Class<?> targetClass = Class.forName(targetName);    
        Method[] methods = targetClass.getMethods();    
        String description = "";    
         for (Method method : methods) {    
             if (method.getName().equals(methodName)) {    
                Class<?>[] clazzs = method.getParameterTypes();    
                if (clazzs.length == arguments.length) {    
                	description = method.getAnnotation(SystemControllerLog.class).description();    
                    break;    
                }    
            }    
        }    
         return description;    
    }
    /**
     * 构建request请求参数 
     * @param request
     * @return
     */
	@SuppressWarnings("unchecked")
	protected String getParamsMap(HttpServletRequest request) {
		String json = "无";
        Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String[]> parametersMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parametersMap.entrySet()) {
            String parameterValue = "";
            String[] entruValue = entry.getValue();
            if (entruValue != null && entruValue.length > 0) {
                if (entruValue.length == 1) {
                    parameterValue = entruValue[0];
                } else {
                    for (int i = 0; i < entruValue.length; i++) {
                        if (i == entruValue.length - 1) {
                            parameterValue += entruValue[i];
                        } else {
                            parameterValue += entruValue[i] + ",";
                        }
                    }
                }
            }
            resultMap.put(entry.getKey(), parameterValue);
        }
        if(resultMap!=null&&resultMap.size()>0){
        	json = JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue, SerializerFeature
        			.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature
        			.WriteNullStringAsEmpty);
        }
        return json;
    }
}  