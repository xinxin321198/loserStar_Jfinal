/**
 * author: loserStar
 * date: 2020年3月20日上午8:53:24
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.test;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

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
		PurchaseDBConfig.start();
		test1();
	}
	
	
	public static void test1() {
		List<Record> list = Db.find("select * from erp_org where OT='O' order by objid desc");
		for (Record record : list) {
			System.out.println(record.getStr("shtxt"));
		}
	}

}
