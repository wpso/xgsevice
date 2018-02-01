


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.homingpigeon.dao.PigeonMapper;
import com.homingpigeon.dao.TerminalMapper;
import com.homingpigeon.entity.Pigeon;
import com.homingpigeon.service.PigeonService;
import com.homingpigeon.service.TerminalService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:dispatcher-servlet.xml"})  
public class BaseLocationTest {
//	@Autowired
//	PigeonMapper PigeonMapper;
	@Autowired
	TerminalService terminalService;
	SerializerFeature[] features = {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty};
	@Autowired
	PigeonService pigeonService;
	@Test
	public void test1() {
		Pigeon pigeon = new Pigeon();
		pigeon.setId("444444");
		pigeon.setName("高鹏2");
		pigeon.setLocation("大连2");
		pigeon.setCage("高扬的鸽笼2");
		String result = terminalService.adminPush(pigeon, "10000000018");
		System.out.println(result);
//		System.out.println(terminalService.grab(""));
		
		
	}
	
	
}
