package com.loserstar.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.sap.jco.SapService;
import com.loserstar.sap.jco.SapService.SapVo;
import com.loserstar.utils.json.LoserStarJsonUtil;

public class JunitTestService {
	private SapService sapService = new SapService();
	
	@Before
	public void startDb() {
		PurchaseDBConfig.start();
	}
	
	@Test
	public void test1() {
		List<Record> list = Db.find("select * from erp_org where OT='O' order by objid desc");
		for (Record record : list) {
			System.out.println(record.getStr("shtxt"));
		}
	}
	
	@Test
	public void testRfc() {
		List<SapVo> sapVoList =sapService.get("Z_ERPHR_P033_01",null);
		System.out.println(LoserStarJsonUtil.toJson(sapVoList));
	}
}
