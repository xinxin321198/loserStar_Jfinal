/**
 * author: loserStar
 * date: 2020年3月20日上午8:53:24
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.test;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.bpm.RestAPI;

import bpm.rest.client.BPMClientException;
import bpm.rest.client.authentication.AuthenticationTokenHandlerException;

/**
 * author: loserStar
 * date: 2020年3月20日上午8:53:24
 * remarks:
 */
public class TestDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GenCodeDBConfig_local.start();
		test1();
		testBpm();
	}
	
	
	public static void test1() {
		List<Record> list = Db.find("select * from erp_org where OT='O' order by objid desc");
		for (Record record : list) {
			System.out.println(record.getStr("shtxt"));
		}
	}
	
	public static void testBpm() {
		try {
			RestAPI restAPI = new RestAPI();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("startUser", "01009938")
			.put("userList", "05001386")
			.put("msgTittle", "开发人员测试");
			 String piid = restAPI.runFlow("25.e15e7f72-8d04-4004-8ee6-8150dd8ab457", "2066.e84b2876-79fa-4b06-8ad5-6e89d910c29c", jsonObject);
			 System.out.println(piid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BPMClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationTokenHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
