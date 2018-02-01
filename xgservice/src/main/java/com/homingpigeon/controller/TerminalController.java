package com.homingpigeon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.homingpigeon.entity.Pigeon;
import com.homingpigeon.service.TerminalService;

@Controller
@RequestMapping("/terminal")
public class TerminalController {
	
	@Autowired
	private TerminalService terminalService;
	
	/**
	 * 登陆
	 * @param telphone 手机号
	 * @param registerid 推送标识
	 * @return {"error":"1","message":"登陆失败，没有此手机号码","data":{"channelid":"","id":"","isadmin":0,"password":"","status":0}}
	 */
	@RequestMapping("/login")
	@ResponseBody
	public String login(String telphone,String registerid){
		String result = terminalService.login(telphone, registerid);
		return result;
	}
	/**
	 * 多查询
	 * @param parm
	 * @return List<QueryResult>
	 */
	@RequestMapping("/query")
	@ResponseBody
	public String query(String parm){
		String result = terminalService.queryByParm(parm);
		return result;
	}
//	/**
//	 * 抢单（终端）
//	 * @param tid 终端ID
//	 * @return
//	 */
//	@RequestMapping("/grab")
//	@ResponseBody
//	public String grab(String tid){
//		return terminalService.grab(tid);
//	}
	/**
	 * 管理员推送
	 * @param pigeon 鸽子信息
	 * @return
	 */
	@RequestMapping("/push")
	@ResponseBody
	public String adminpush(Pigeon pigeon,String tid){
		return terminalService.adminPush(pigeon,tid);
	}
	
	/**
	 * 确定按钮（终端）更新终端状态 = 空闲
	 * @param tid
	 * @return
	 */
	@RequestMapping("/confirmation")
	@ResponseBody
	public String confirmation(String tid){
		return terminalService.confirmation(tid);
	}
	
	
	@RequestMapping("/getPigeonInfo")
	@ResponseBody
	public String test(){
		return terminalService.queryPigeonInfo();
	}
	
}
