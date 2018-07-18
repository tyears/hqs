package com.cccuu.project.utils;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 所有controller继承此controller
 *
 * @author zhaixiaoliang
 * @author 2017年3月23日上午11:24:36
 * @Description
 */
public class BaseController {
    protected Logger log = Logger.getLogger(getClass());

    /**
     * 将对象转换成JSON字符串，并响应回前台
     *
     * @param object
     * @param response
     */
    protected void writeJson(HttpServletResponse response, Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteMapNullValue, SerializerFeature
                    .WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullNumberAsZero, SerializerFeature
                    .WriteNullStringAsEmpty);
            log.info("转换后的JSON字符串：" + json);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象转换成JSON字符串
     *
     * @param object
     */
    protected String transObjToJson(Object object) {
        try {
            String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
            log.info("转换后的JSON字符串：" + json);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置K.V 到SESSION 中
     *
     * @param request
     * @param attString
     * @param attValue
     */
    protected void setSessionAttribute(HttpServletRequest request, String attString, Object attValue) {
        request.getSession().setAttribute(attString, attValue);
    }

    /**
     * 获取SESSION 中的值
     *
     * @param request
     * @param attString
     * @return
     */
    protected Object getSessionAttribute(HttpServletRequest request, String attString) {
        return request.getSession().getAttribute(attString);
    }

    /**
     * 移除SESSION中的值
     *
     * @param request
     * @param attString
     */
    protected final void removeSessionAttribute(HttpServletRequest request, String attString) {
        request.getSession().removeAttribute(attString);
    }

    /**
     * 文件上传工具
     *
     * @param request
     * @param savePath      保存路径 如：upload/aa/
     * @param formFieldName <input type="file" name="formFieldName"/>中的name值,不传则上传页面所有文件
     * @return 上传的文件信息 新文件名称、文件相对路径、文件大小
     */
    protected List<Map<String, String>> uploadOneFile(HttpServletRequest request, String savePath, String formFieldName) {
        List<Map<String, String>> uploadFiles = new ArrayList<Map<String, String>>();
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            String absoluteSavePath = request.getSession().getServletContext().getRealPath("/") + savePath;
            if (StringUtils.isNotBlank(formFieldName)) {

                List<MultipartFile> multypartfiles = multipartRequest.getFiles(formFieldName);
                if (multypartfiles != null && multypartfiles.size() > 0) {
                    for (MultipartFile multipartFile : multypartfiles) {
                        if (multipartFile.getSize() > 10 * 1024 * 1024) {
                            throw new RuntimeException("文件大小超过10M，不允许上传");
                        }
                        Map<String, String> fileInfo = new HashMap<String, String>();
                        String originalFileName = multipartFile.getOriginalFilename();
                        if (StringUtils.isNotBlank(originalFileName)) {
                            String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newFileName = df.format(new Date()) + new Random().nextInt(1000) + fileExt;
                            fileInfo.put("fileSize", multipartFile.getSize() + "");
                            fileInfo.put("fileName", newFileName);
                            fileInfo.put("filePath", savePath + newFileName);

                            File uploadDir = new File(absoluteSavePath);//创建要上传文件到指定的目录
                            if (!uploadDir.exists()) {
                                uploadDir.mkdirs();
                            }
                            File uploadedFile = new File(absoluteSavePath, newFileName);
                            try {
                                FileUtils.writeByteArrayToFile(uploadedFile, multipartFile.getBytes());
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            uploadFiles.add(fileInfo);
                        }
                    }
                }

            } else {
                Iterator<String> fileNamesIterator = multipartRequest.getFileNames();
                while (fileNamesIterator.hasNext()) {
                    String tempFieldName = fileNamesIterator.next();
                    List<MultipartFile> multypartfiles = multipartRequest.getFiles(tempFieldName);
                    if (multypartfiles != null && multypartfiles.size() > 0) {
                        for (MultipartFile multipartFile : multypartfiles) {
                            if (multipartFile.getSize() > 10 * 1024 * 1024) {
                                throw new RuntimeException("文件大小超过10M，不允许上传");
                            }
                            Map<String, String> fileInfo = new HashMap<String, String>();
                            String originalFileName = multipartFile.getOriginalFilename();
                            String fileExt = originalFileName.substring(originalFileName.lastIndexOf(".")).toLowerCase();
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newFileName = df.format(new Date()) + new Random().nextInt(1000) + fileExt;
                            fileInfo.put("fileSize", multipartFile.getSize() + "");
                            fileInfo.put("fileName", newFileName);
                            fileInfo.put("filePath", savePath + newFileName);

                            File uploadDir = new File(absoluteSavePath);//创建要上传文件到指定的目录
                            if (!uploadDir.exists()) {
                                uploadDir.mkdirs();
                            }
                            File uploadedFile = new File(absoluteSavePath, newFileName);
                            try {
                                FileUtils.writeByteArrayToFile(uploadedFile, multipartFile.getBytes());
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            uploadFiles.add(fileInfo);
                        }
                    }
                }
            }
        }
        return uploadFiles;

    }

    /**
     * 构建request参数为map
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
	protected Map<String, String> getParamsMap(HttpServletRequest request) {
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
        return resultMap;
    }
    protected boolean delFile(HttpServletRequest request,String path){
        boolean result = true;
        path  = request.getSession().getServletContext().getRealPath("/") + path;
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }else{
            result = false;
        }
        return result;
    }

}
