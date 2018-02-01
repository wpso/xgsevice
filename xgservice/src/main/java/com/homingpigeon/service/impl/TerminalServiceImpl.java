package com.homingpigeon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.homingpigeon.dao.PigeonMapper;
import com.homingpigeon.dao.TerminalMapper;
import com.homingpigeon.entity.Pigeon;
import com.homingpigeon.entity.QueryResult;
import com.homingpigeon.entity.Terminal;
import com.homingpigeon.push.Push;
import com.homingpigeon.service.TerminalService;
import com.homingpigeon.util.DBContextHolder;
import com.homingpigeon.util.JSONUtil;

/**
 * 终端服务
 * @author gaopeng
 * @createtime 2016-03-03 23:00:57
 */
@Service
public class TerminalServiceImpl implements TerminalService {
	private Logger logger = Logger.getLogger(this.getClass());
	private SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty};
	@Autowired
	TerminalMapper terminalMapper;
	@Autowired
	PigeonMapper pigeonMapper;
	
	public String login(String telphone, String registerid) {
		if(telphone==null||"".equals(telphone)||registerid==null&&"".equals(registerid)){
			return JSONUtil.appendError("1", "登陆失败，参数错误", JSONObject.toJSONString(new Terminal(), features));
		}
		Terminal dbtermianl = terminalMapper.selectByTid(telphone);
		if(dbtermianl!=null){
			int islogin = dbtermianl.getIslogin();
			if(islogin==1){
				//正在登录中
				return JSONUtil.appendError("1", "登陆失败，此账号正在登录中。。", JSONObject.toJSONString(new Terminal(), features));
			}
			//登陆成功 更新 registerid
			Terminal terminal = new Terminal();
			terminal.setChannelid(registerid);
			terminal.setId(telphone);
//			terminal.setIslogin(1);
			if(terminalMapper.updateByPrimaryKeySelective(terminal)>0){
				logger.info(telphone + "登陆成功");
				return JSONUtil.appendError("0", "登陆成功", JSONObject.toJSONString(dbtermianl, features));
			}
			return JSONUtil.appendError("1", "登陆失败，更新registerid失败", JSONObject.toJSONString(new Terminal(), features));
		}
		return JSONUtil.appendError("1", "登陆失败，没有此手机号码", JSONObject.toJSONString(new Terminal(), features));
	}
	/**
	 * 多查询
	 */
	public String queryByParm(String parm) {
		List<QueryResult> queryResultlist = terminalMapper.queryByParm(parm);
		String jsonstr = "";
		if(queryResultlist.size()<1){
			return JSONUtil.appendError("0", "查询成功", JSONArray.toJSONString(new ArrayList<QueryResult>(), features));
		}
		jsonstr = JSONArray.toJSONString(queryResultlist, features);
		return JSONUtil.appendError("0", "查询成功", jsonstr);
	}
	
	/**
	 * 终端（普通用户） 抢单
	 * 终端登录后，点击开始，相当于抢单。抢单成功出来左面的界面，
	 * 用户的状态变为1（非空闲），表示不可接收，鸽子的状态变为1，
	 * 不可以在被其他终端抢。抢到鸽子的终端送完鸽子，点击确认按钮，
	 * 终端的状态变为0（空闲），表示可接收，出现右面页面，显示鸽子已归位，
	 * 等待接受新鸽子，点击开始，进行新一轮抢单。
	 */
	public String grab(String tid) {
		Pigeon pigeon = null;
		int result = -1;
		// 判断终端是否为空闲
		Terminal terminal =  terminalMapper.selectByTid(tid);
		int terminalStatus = terminal.getStatus() ;
		if(terminalStatus == 1){
			return JSONUtil.appendError("0", "用户在非空闲状态，不可操作！", "{}");
		}
		do {
			// 取一条未推送的 鸽子
			pigeon = pigeonMapper.selPigeonBystatus();
			if (pigeon == null) {
				return JSONUtil.appendError("0", "已经被抢光了！", "{}");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pigeon.getId());
			map.put("version", pigeon.getVersion());
			
			result = pigeonMapper.updatePigeon_statusByid(map);

		} while (result != 1);
		//更新用户状态
		terminal.setStatus(1);
		terminalMapper.updateByPrimaryKeySelective(terminal);
		//插入 鸽子、终端关联表
		Map<String,String> map = new HashMap<String, String>();
		map.put("pid", pigeon.getId());
		map.put("tid", terminal.getId());
		int insertresult = terminalMapper.insert_pigeon_terminal(map);
		if(insertresult!=1){
			return JSONUtil.appendError("1", "抢单失败！请重新开始。","{}");
		}
		return JSONUtil.appendError("0", "抢单成功！",
				JSONObject.toJSONString(pigeon, features));
	}
	
	
	public String queryPigeonInfo() {
		DBContextHolder.setDBType(DBContextHolder.DATASOURCE_SQLSERVER);
		Pigeon pigeon = terminalMapper.selectPigeonInfo();
		if(pigeon == null){
			return JSONUtil.appendError("0","查询成功!", "{}");
		}
		DBContextHolder.setDBType(DBContextHolder.DATASOURCE_MYSQL);
		String info = JSONUtil.appendError("0","查询成功!", JSONObject.toJSONString(pigeon));
		return info;
	}
	
	public String adminPush(Pigeon pigeon,String tid) {
		Pigeon dbpigeon = pigeonMapper.selectByPrimaryKey(pigeon.getId());
		if(dbpigeon != null){
			return JSONUtil.appendError("1", "发送失败，编号"+pigeon.getId()+"鸽子已存在！", "");
		}
		//TODO 查询用户列表的registerid（不包括管理员）
//		List<Terminal> terminallist = terminalMapper.selectUserList();
//		if(terminallist == null){
//			return JSONUtil.appendError("0", "发送成功！", "");
//		}
//		Push push = new Push();
//		for (Terminal terminal : terminallist) {
//			if(terminal.getChannelid() != null && !("".equals(terminal.getChannelid()))){
//				try {
//					push.pushUser("信鸽推送", "编号为"+pigeon.getId()+"放到"+pigeon.getName()+"鸽箱里", terminal.getChannelid(),pigeon);
//				} catch (APIConnectionException e) {
//					continue;
//				} catch (APIRequestException e) {
//					continue;
//				}
//			}
//			
//		}
		try {
			int result = pigeonMapper.insertSelective(pigeon);
			Map<String,String> map = new HashMap<String, String>();
			map.put("pid", pigeon.getId());
			map.put("tid", tid);
			int insertresult = terminalMapper.insert_pigeon_terminal(map);
			insertresult = 0;
			if(result != 1 || insertresult != 1){
//				throw new Exception("失败了啊");
			}
		} catch (Exception e) {
			
			throw new RuntimeException();
			
			
		}
		
		return JSONUtil.appendError("0", "保存成功！", "");
	}
	public String confirmation(String tid) {
		int result = 0;
		Terminal terminal = new Terminal();
		terminal.setId(tid);
		terminal.setStatus(0);
		result = terminalMapper.updateByPrimaryKeySelective(terminal);
		if(result == 1){
			return JSONUtil.appendError("0", "成功!", "");
		}
		
		return JSONUtil.appendError("1", "未知错误，失败。请重试!", "");
	}


}
