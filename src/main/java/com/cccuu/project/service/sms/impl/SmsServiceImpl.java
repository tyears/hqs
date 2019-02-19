package com.cccuu.project.service.sms.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.cccuu.project.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import com.cccuu.project.model.sms.Sms;
import com.cccuu.project.mapper.sms.SmsMapper;
import com.cccuu.project.service.sms.SmsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信ServiceImpl
 * @Description 
 * @Author zhaixiaoliang
 * @Date 2017年10月25日 18:02:05
 */
@Service("smsService")
public class SmsServiceImpl extends BaseServiceImpl<Sms> implements SmsService{
	
	private SmsMapper smsMapper;
	
	@Autowired
	public void setSmsMapper(SmsMapper smsMapper){
		this.smsMapper = smsMapper;
		baseMapper = smsMapper;
	}
	@Override
	@Transactional
	public PageInfo<Sms> queryListByPage(Map<String,String> params){
		//设置查询条件
		Example example = new Example(Sms.class);
		example.setOrderByClause(" create_time desc ");
		Example.Criteria creiteria = example.createCriteria();

		String userName = params.get("userName");
		if(StringUtils.isNotBlank(userName)){
			creiteria.andLike("userName","%"+userName+"%");
		}

		String tel=params.get("tel");
		if(StringUtils.isNotBlank(tel)){
			creiteria.andLike("tel","%"+tel+"%");
		}
		
		
		//设置分页参数
		String pageNumStr = params.get("pageNum");
		String pageSizeStr = params.get("pageSize");
		int pageNum = StringUtils.isBlank(pageNumStr) || "0".equals(pageNumStr) ? 1 : Integer.parseInt(pageNumStr);
		int pageSize = StringUtils.isBlank(pageSizeStr) || "0".equals(pageSizeStr) ? 10 : Integer.parseInt(pageSizeStr);
		
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		
		List<Sms> results = smsMapper.selectByExample(example);
		
		PageInfo<Sms> pageInfo = new PageInfo<Sms>(results);
		
		return pageInfo;
	}

	/**
	 * 发送短信
	 * @param phone
	 * @param text
	 * @return
	 */
	public Map getsmsCodeByPhone(String phone, String text,HttpServletRequest request){
		Map map = null;
		try {
			if(StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(text)){
				String code = MD5.MD5Encode(phone+"leasom");
				//text+="退订回N";
//				String retu = HttpUtil.postRequest(ProjectGlobal.getConfig("smsService"),"phone:"+phone,"text:"+text,"code:"+code);
				Map params=new HashMap<>();
				params.put("phone",phone);
				params.put("text",text);
				params.put("code",code);
				String retu = HttpUtil.sendPost(ProjectGlobal.getConfig("smsService"),params);
				map = JSON.parseObject(retu);
				log.info(retu);
				if("true".equals(map.get("result"))){
					//新增短信记录
					UserInfo userInfo= (UserInfo) request.getSession().getAttribute(Constants.SESSION_USER);
					Sms sms=new Sms();
					sms.setId(UniqueIDGen.getUniqueID()+"");
					sms.setUserName(userInfo.getName());
					sms.setUserId(userInfo.getUserId());
					sms.setContent(text);
					sms.setTel(phone);
					sms.setCreateTime(new Date());
					smsMapper.insertSelective(sms);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}