package com.cccuu.project.utils;



import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	private static JPushClient jpushClient = null;
	
	public static void setJPushClient(String tag){
        try {
            if(tag.equals("user")){
                //根据用户端appKey创建对象
                jpushClient = new JPushClient("275f7ee284f90e0735c11a32", "ff11e3c1d207dd4aa64b3e03");
            }else{
                //根据司机端appKey创建对象
                jpushClient = new JPushClient("2b14362d2f06008bd61d4667", "0420c1c7394da69de633396e");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 备注：推送方式不为空时，推送的值也不能为空；推送方式为空时，推送值不做要求
	 * @param type 推送方式：1、“tag”标签推送，2、“alias”别名推送
	 * @param value 推送的标签或别名值
	 * @param alert 推送的内容
	 */
	public static void pushMsg(String type,String value,String alert){
		Builder builder= PushPayload.newBuilder();
		builder.setPlatform(Platform.all());//设置接受的平台
		if(type.equals("alias")){
			builder.setAudience(Audience.alias(value));//别名推送
		}else if(type.equals("tag")){
			builder.setAudience(Audience.tag(value));//标签推送
		}else{
			builder.setAudience(Audience.all());//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
		}
		builder.setNotification(Notification.alert(alert));
		PushPayload pushPayload = builder.build();
		try {
			PushResult result = jpushClient.sendPush(pushPayload);
			System.out.println("推送结果======="+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		PushPayload pushPayload = PushPayload.newBuilder()
//        .setPlatform(Platform.all())//设置接受的平台
//        .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
//        .setNotification(Notification.alert(title))
//        .build();
	}

	/**
	 *
	 * @param type 推送方式：1、“tag”标签推送，2、“alias”别名推送
	 * @param value 推送的标签或别名值
	 * @param map 推送的内容
	 */
    /**
     * 自定义消息推送  备注：推送方式不为空时，推送的值也不能为空；推送方式为空时，推送值不做要求
     * @param type 推送方式：1、“tag”标签推送，2、“alias”别名推送
     * @param value 推送的标签或别名值
     * @param alert 推送的内容
     * @param title 订单id  没有则为""
     * @param contentType "o":订单 "m":公告
     */
	public static void pushMsgDiy(String type, String value,String alert,String title,String contentType){
		Builder builder= PushPayload.newBuilder();
		builder.setPlatform(Platform.all());//设置接受的平台
		if(type.equals("alias")){
			builder.setAudience(Audience.alias(value));//别名推送
		}else if(type.equals("tag")){
			builder.setAudience(Audience.tag(value));//标签推送
		}else{
			builder.setAudience(Audience.all());//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
		}
        Message.Builder newBuilder =  Message.newBuilder();
		newBuilder.setMsgContent(alert);//推送内容
        newBuilder.setTitle(title);//订单id  没有则为""
        newBuilder.setContentType(contentType);//"o":订单 "m":公告
        Message message = newBuilder.build();
		builder.setMessage(message);
		PushPayload pushPayload = builder.build();
		try {
			PushResult result = jpushClient.sendPush(pushPayload);
			System.out.println("推送结果======="+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
        JPushUtil.setJPushClient("user");
        JPushUtil.pushMsgDiy("alias", "8a2896355b84eb49015b84f6803a0003", "调页面","8a21a1c35be72856015be739b40a0002","o");
//        JPushUtil.setJPushClient("driver");
//        JPushUtil.pushMsgDiy("alias", "8a2896355b291f14015b3328454f0002", "你有新。","","m");
	}

}
