package com.loserstar.test;

import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.bpm.RestAPI;
import com.loserstar.sap.jco.SapService;
import com.loserstar.sap.jco.SapService.SapVo;
import com.loserstar.utils.json.LoserStarJsonUtil;

public class JunitTestService {
	private SapService sapService = new SapService();
	
	/**
	 * 前置方法，加载配置文件，连接数据库
	 */
	@Before
	public void startDb() {
		PurchaseDBConfig.start();
	}
	
	/**
	 * 查询数据库demo
	 */
	@Test
	public void test1() {
		List<Record> list = Db.find("select * from erp_org where OT='O' order by objid desc");
		for (Record record : list) {
			System.out.println(record.getStr("shtxt"));
		}
	}
	
	/**
	 * 调用sap rfc的demo
	 */
	@Test
	public void testRfc() {
		List<SapVo> sapVoList =sapService.get("Z_ERPHR_P033_01",null);
		System.out.println(LoserStarJsonUtil.toJson(sapVoList));
	}
	
	/**
	 * 启动bpm流程demo
	 */
	@Test
	public void testBpm() {
		try {
			RestAPI restAPI = new RestAPI();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("startUser", "01009938")
			.put("userList", new String[] {"05001386"})
			.put("msgTittle", "开发人员测试");
			 String piid = restAPI.runFlow("", "", jsonObject);
			 System.out.println(piid);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
