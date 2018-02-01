package com.homingpigeon.push;

import java.util.HashMap;



import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;




import com.homingpigeon.entity.Pigeon;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;

//@Service
public class Push {
	


	
	private String apiKey = PushKeys.apiKey;
	private String secretKey = PushKeys.secretKey;
//	private Logger logger = Logger.getLogger(Push.class);
	
	private JPushClient jpushClient = new JPushClient(secretKey, apiKey);
	
	
	public void pushUser(String title,String content,String registerid,Pigeon pigeon) throws APIConnectionException, APIRequestException{
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", pigeon.getId());
		map.put("location", pigeon.getLocation());
		map.put("name", pigeon.getName());
		map.put("cage", pigeon.getCage());
		jpushClient.sendAndroidNotificationWithRegistrationID(title,content,map,registerid);
	}
	public static void main(String[] args) throws APIConnectionException, APIRequestException {
		JPushClient jpushClient = new JPushClient("361a80946f40e1701554e4ce", "190d70133c3da4678436167b");
		PushResult result = null;
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", "0005");
		map.put("location", "大连");
		map.put("name", "李四");
		map.put("cage", "李四的鸽箱");
		result = jpushClient.sendAndroidNotificationWithRegistrationID("测试一下", "这能行吗？",map,"100d85590944d8ab1ae");
		System.out.println(result.sendno);
	}
	
}
