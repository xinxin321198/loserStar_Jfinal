/**
 * author: loserStar
 * date: 2020年3月11日上午11:15:17
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.loserstar.config.generator.LoserStarMetaBuilderDB2;
import com.loserstar.utils.date.LoserStarDateUtils;
import com.loserstar.utils.db.jfinal.base.imp.BaseService.DBType;
import com.loserstar.utils.file.LoserStarFileUtil;
import com.loserstar.utils.freemarker.LoserStarFreemarkerUtil;

/**
 * author: loserStar
 * date: 2020年3月11日上午11:15:17
 * remarks:
 */
public class EntityGennerate {
	//数据库类型(根据实际使用的数据库进行调整)
	private static DBType dbtype = DBType.db2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gengEntity();
		genDao();
		genConstants();
	}
	//要生成的表名（作废的第一套表）
	/*private static String[] gennerateTableNames =  {
			"ZTSWPZ_BWART",
			"ZTSWPZ_LGORT",
			"ZTSWPZ_LIFNR",
			"ZTSWPZ_MATKL",
			"ZTSWPZ_MEINS",
			"ZTSWPZ_MIGO_INSMK",
			"ZTSWPZ_SHKZG",
			"ZTSWPZ_SOBKZ",
			"ZTSWPZ_TRANS_STOCK_TYPE",
			"ZTSWPZ_WERKS",
			"ZTSW_BATCH",
			"ZTSW_BUS_TYPE",
			"ZTSW_ERPTABLES",
			"ZTSW_GENERATE",
			"ZTSW_GOODS_LOCA",
			"ZTSW_MARA",
			"ZTSW_MARD",
			"ZTSW_MARM",
			"ZTSW_MATKL_WH",
			"ZTSW_MKPF",
			"ZTSW_MSEG",
			"ZTSW_ORDERLISTTABLE",
			"ZTSW_TRAYS_H",
			"ZTSW_TRAYS_H",
			"ZTSW_TRAYS_I",
			"ZTSW_TRAYS_REP",
			"ZTSW_WAREHOUSE",
			"ZTSW_RK_FIRST",
			"ZTSW_TRAY_H",
			"ZTSW_HOUSE",
			"ZTSW_HOUSE_FLOOR"
			};*/
	
	private static String[] gennerateTableNames =  {
			"IN_BATCH"
			/*"MASTER_FACTORY",
			"MASTER_GOODS_LOCA",
			"MASTER_WARE_HOUSE",
			"MASTER_LIFNR",
			"MASTER_MARA",
			"MASTER_MARA_AREA",
			"MASTER_MARA_GROUP",
			"MASTER_STORAGE_AREA",
			"TASK_BILL_D",
			"TASK_BILL_H",
			"TASK_BILL_I",
			"PLAN_H",
			"PLAN_D",
			"SYS_DICT",
			"MOVE_VOUCHER_H",
			"MOVE_VOUCHER_I",
			"MASTER_BUS_TYPE",
			"MOVE_HISTORY",
			"MASTER_UNIT",
			"STOCK_CURRENT",
			"STOCK_DISK_H",
			"STOCK_DISK_D",
			"PLAN_COMPUTE",
			"MASTER_WORKSHOP",
			"SYS_FILE",
			"SYS_FILE_META"*/
	};
	
	
	public static void gengEntity() {
		
		//查询表的sql（不同数据库的语句不同，自行调整）
		String queryTablesSql = "";
		if (dbtype==DBType.db2) {
			queryTablesSql = "select name as table_name from sysibm.systables where type='T'  ORDER BY NAME ASC";
		}else if(dbtype==DBType.mysql) {
			queryTablesSql = "SELECT table_name FROM information_schema.TABLES ORDER BY table_name ASC";
		}else {
			System.out.println("未知的数据库类型");
			return;
		}
		//要生成的表名
		List<String> gennerateTableNameList =Arrays.asList(gennerateTableNames);
				
		// base model 所使用的包名
		String baseModelPackageName = "com.kaen.entity.base";
		// base model 文件保存路径
		String baseModelOutputDir =PathKit.getWebRootPath() + "/../src/com/kaen/entity/base";
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.kaen.entity";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		// 创建生成器
		DataSource dataSource = PurchaseDBConfig.start();
		Generator generator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		generator.setMetaBuilder(new LoserStarMetaBuilderDB2(dataSource));
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(false);
		
		List<Record> tableList = Db.find(queryTablesSql);
		for (Record table : tableList) {
//						System.out.println("generator.addExcludedTable(\""+record.get("name")+"\");");
			//排除掉不在我打算生成的列表里的表名
			String tableName = table.getStr("table_name");
			if (!gennerateTableNameList.contains(tableName)) {
				generator.addExcludedTable(tableName);
			}
		}
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		//数据库方言
		if (dbtype==DBType.db2) {
			generator.setDialect(new AnsiSqlDialect());
		}else if(dbtype==DBType.mysql) {
			generator.setDialect(new MysqlDialect());
		}else if(dbtype==DBType.oracle) {
			generator.setDialect(new OracleDialect());
		}else if(dbtype==DBType.sqlserver) {
			generator.setDialect(new SqlServerDialect());
		}
		//生成字典文件
		generator.setGenerateDataDictionary(true);
		//在model中生成dao
		generator.setGenerateDaoInModel(true);
		//设置 BaseModel 是否生成链式 setter 方法
		generator.setGenerateChainSetter(true);
		// 配置是否生成备注( 此版本貌似没有)
//					generator.setGenerateRemarks(true);
		// 生成
		generator.generate();
	}

	public static void genDao() {
		try {
			DataSource dataSource = PurchaseDBConfig.start();
			Connection conn = dataSource.getConnection();
			//要生成的表名
			List<String> gennerateTableNameList =Arrays.asList(gennerateTableNames);
			for (String tableName : gennerateTableNameList) {
				Statement stm = conn.createStatement();
				DatabaseMetaData dbMetaData = conn.getMetaData();
				 ResultSet resultSet = dbMetaData.getTables(null, "%", "%", new String[] { tableName });
		            while (resultSet.next()) {
		                String remarkes = resultSet.getString("REMARKS");
		                System.out.println(tableName+"="+remarkes);
		            }
//				System.out.println("表名begin----------："+tableName);
//				System.out.println("主键：");
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,tableName);  
				String primaryKeyColumnName = "";
				while(primaryKeyResultSet.next()){  
				    primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
//				    System.out.println(primaryKeyColumnName);
				} 
				/*
				ResultSet rs = stm.executeQuery("select * from " + tableName + " where 1 = 2");
				ResultSetMetaData rsmd = rs.getMetaData();
								System.out.println("字段：");
								for (int i=1; i<=rsmd.getColumnCount(); i++) {
									System.out.println(rsmd.getColumnName(i));
								}*/
//				System.out.println("表名end----------："+tableName);
				
				Record tableInfo = Db.findFirst("select * from sysibm.systables where type='T' and NAME='"+tableName+"'  ORDER BY NAME ASC");
				Map<String, Object> data = new HashMap<String, Object>();
				System.out.println(tableName);
				data.put("tableRemarks", tableInfo.get("REMARKS"));
				String className = "";
				String[] tableNameArray = tableName.split("_");
				for (int i = 0; i < tableNameArray.length; i++) {
					className+=tableNameArray[i].substring(0,1).toUpperCase()+tableNameArray[i].substring(1,tableNameArray[i].length()).toLowerCase();
				}
				className+="Dao";
				data.put("className", className);
				data.put("tableName", tableName);
				data.put("primaryKey", primaryKeyColumnName);
				data.put("creator", tableInfo.get("CREATOR").toString().trim());
				data.put("genDate", LoserStarDateUtils.format(new Date()));
//				String genPath = "D:\\development\\keWorkSpace\\HtWearhouse\\src\\com\\kaen\\dao\\"+className+".java";
				String genPath = PathKit.getWebRootPath()+File.separator+".."+File.separator+"src"+File.separator+"com"+File.separator+"kaen"+File.separator+"dao"+File.separator+className+".java";
				if (!new File(genPath).exists()) {
					String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/").getPath(), "daoTemp.ftl", data);
					LoserStarFileUtil.WriteStringToFilePath(string,genPath , false);
					System.out.println("生成dao:"+genPath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void genConstants() {
				try {
					DataSource dataSource = PurchaseDBConfig.start();
					Map<String, Object> data = new HashMap<String, Object>();
					List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
					List<Record> typeList = Db.find("SELECT TYPE FROM ZTSW.SYS_DICT GROUP BY TYPE");
					for (Record record : typeList) {
						List<Record> dictList = Db.find("select * from ZTSW.SYS_DICT where type='"+record.getStr("type")+"'");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type", record.get("type"));
						map.put("list", dictList);
						mapList.add(map);
					}
					data.put("data", mapList);
					String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/").getPath(), "dictConstantsTemp.ftl", data);
					String outPath = PathKit.getWebRootPath()+File.separator+".."+File.separator+"src"+File.separator+"com"+File.separator+"kaen"+File.separator+"constants"+File.separator+"DictConstants.java";
					System.out.println(outPath);
					LoserStarFileUtil.PrintWriterToFile(outPath, string, false);
				} catch (Exception e) {
					e.printStackTrace();
				}

	}
}
