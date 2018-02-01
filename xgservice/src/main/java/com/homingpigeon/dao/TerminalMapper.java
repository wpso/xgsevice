package com.homingpigeon.dao;

import java.util.List;
import java.util.Map;

import com.homingpigeon.entity.Pigeon;
import com.homingpigeon.entity.QueryResult;
import com.homingpigeon.entity.Terminal;

public interface TerminalMapper {
	
	Terminal selectByTid(String id);//登陆 id手机号码
	
	int updateByPrimaryKeySelective(Terminal Terminal);
	
	//输入鸽子编号，点击查询得到所有推送的设备编号及接收状态
	List<QueryResult> queryByParm(String parm);
	
	int insert_pigeon_terminal(Map<String,String> map);
	
	Pigeon selectPigeonInfo();
	
	List<Terminal> selectUserList();
}
