package com.homingpigeon.service;

import com.homingpigeon.entity.Pigeon;

public interface TerminalService {
	//登陆
	String login(String telphone,String registerid);
	//查询
	String queryByParm(String parm);
	//抢单(终端)
	String grab(String tid);
	
	String queryPigeonInfo();
	//管理员推送
	String adminPush(Pigeon pigeon,String tid);
	//确认按钮（终端）
	String confirmation(String tid);
		
	
}
