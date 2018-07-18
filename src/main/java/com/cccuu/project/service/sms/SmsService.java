package com.cccuu.project.service.sms;

import java.util.Map;
import com.cccuu.project.model.sms.Sms;
import com.cccuu.project.utils.BaseService;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信Service
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月25日 18:02:05
 */
public interface SmsService extends BaseService<Sms>{
	/**
	 * 分页查询列表
	 * @param params
	 * @return
	 */
	public PageInfo<Sms> queryListByPage(Map<String,String> params);

	/**
	 * 发送短信
	 * @param phone
	 * @param text
	 * @return
	 */
	public Map getsmsCodeByPhone(String phone, String text,HttpServletRequest request);
}