package com.loserstar.test;

import java.util.UUID;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 
 * author: loserStar
 * date: 2018年3月28日下午2:45:40
 * remarks:
 */
public class PurchaseDBConfig {
	public static final boolean isTest = true;//false为生产，true为测试机
	
	//测试机
/*	private static final String jdbcUrl_test="jdbc:db2://10.96.66.23:50000/HT313DB";
	private static final String user_test="db2inst1";
	private static final String password_test="init1234";
	private static final String driverClass_test="com.ibm.db2.jcc.DB2Driver";*/
	private static final String jdbcUrl_test="jdbc:mysql://localhost:3306/dataagg";
	private static final String user_test="root";
	private static final String password_test="root";
	private static final String driverClass_test="com.mysql.jdbc.Driver";
	//生产
	private static final String jdbcUrl_product="jdbc:db2://10.96.66.23:50000/HT313DB";
	private static final String user_product="db2inst1";
	private static final String password_product="init1234";
	private static final String driverClass_product="com.ibm.db2.jcc.DB2Driver";
	
	public static String getJdbcurl() {
		return isTest?jdbcUrl_test:jdbcUrl_product;
	}
	public static String getUser() {
		return isTest?user_test:user_product;
	}
	public static String getPassword() {
		return isTest?password_test:password_product;
	}
	public static String getDriverclass() {
		return isTest?driverClass_test:driverClass_product;
	}
	
	public  static DataSource start() {
		String connectionStr = PurchaseDBConfig.getJdbcurl();
		DruidPlugin dp  = new DruidPlugin(connectionStr, PurchaseDBConfig.getUser(),PurchaseDBConfig.getPassword());
		dp.setDriverClass(PurchaseDBConfig.getDriverclass());
		ActiveRecordPlugin arp = new ActiveRecordPlugin(UUID.randomUUID().toString(),dp);
		arp.setShowSql(true);//打印出执行的sql
		arp.setDialect(new AnsiSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 不区分大小写
		dp.start();
		arp.start();
		
		
		return dp.getDataSource();
	}
}
