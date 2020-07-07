/**
 * author: loserStar
 * date: 2020年3月11日上午11:15:17
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.test;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import com.loserstar.utils.json.LoserStarJsonUtil;

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
				DataSource dataSource = PurchaseDBConfig.start();
				gengEntity(dataSource);
				genDao(dataSource);
				genConstants();
				genJqwidgetsTableField(dataSource);
				genDictJs();
				genVoField();
		
	}
	
	private static String[] gennerateTableNames =  {
			"IN_BATCH"
	};
	
	/**
	 * 生成jqwidgets使用的前端字段定义（通过反射生成，需要类的全包路径）
	 */
	private static String[] gennerateVoNames =  {
			"com.kaen.entity.vo2.Disk"
	};
	
	
	public static void gengEntity(DataSource dataSource) {
		

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
		Generator generator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		generator.setMetaBuilder(new LoserStarMetaBuilderDB2(dataSource));
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(false);
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

	/**
	 * 生成dao文件
	 * @param dataSource
	 */
	public static void genDao(DataSource dataSource) {
		System.out.println("--------------生成Dao--------------------begin");
		try {
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
					String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "daoTemp.ftl", data);
					LoserStarFileUtil.WriteStringToFilePath(string,genPath , false);
					System.out.println("生成dao:"+genPath);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 生成字典常量（后端使用）
	 */
	public static void genConstants() {
		String tableName = "ZTSW.SYS_DICT";//字典表名称
		String typeFieldName = "type";//用来区分字典类型的字段名称
				try {
					Map<String, Object> data = new HashMap<String, Object>();
					List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
					List<Record> typeList = Db.find("SELECT TYPE FROM "+tableName+" GROUP BY "+typeFieldName+"");
					for (Record record : typeList) {
						List<Record> dictList = Db.find("select * from "+tableName+" where "+typeFieldName+"='"+record.getStr(typeFieldName)+"'");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type", record.get(typeFieldName));
						map.put("list", dictList);
						mapList.add(map);
					}
					data.put("data", mapList);
					String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "dictConstantsTemp.ftl", data);
					String outPath = PathKit.getWebRootPath()+File.separator+".."+File.separator+"src"+File.separator+"com"+File.separator+"kaen"+File.separator+"constants"+File.separator+"DictConstants.java";
					System.out.println(outPath);
					LoserStarFileUtil.PrintWriterToFile(outPath, string, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("--------------生成Dao--------------------end");
	}
	
	/**
	 * 生成每张表对应的前端js数据源字段
	 */
	public static void genJqwidgetsTableField(DataSource dataSource) {
		System.out.println("--------------生成每张表的前端字段定义--------------------begin");
		try {
			Connection conn = dataSource.getConnection();
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
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
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,tableName);  
				String primaryKeyColumnName = "";
				while(primaryKeyResultSet.next()){  
				    primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
//				    System.out.println(primaryKeyColumnName);
				} 
				
				List<Map<String, Object>> fiedList = new ArrayList<Map<String,Object>>();
				ResultSet rs = stm.executeQuery("select * from " + tableName + " where 1 = 2");
				ResultSetMetaData rsmd = rs.getMetaData();
				System.out.println("字段：");
				for (int i=1; i<=rsmd.getColumnCount(); i++) {
//					System.out.println(rsmd.getColumnName(i)+" "+rsmd.getColumnTypeName(i));
					Map<String, Object> fieldMap = new HashMap<String, Object>();
					fieldMap.put("name", rsmd.getColumnName(i).toLowerCase());
					String jsType = "string";
					if(rsmd.getColumnTypeName(i).equalsIgnoreCase("DOUBLE")||rsmd.getColumnTypeName(i).equalsIgnoreCase("DECIMAL")||rsmd.getColumnTypeName(i).equalsIgnoreCase("BIGINT")||rsmd.getColumnTypeName(i).equalsIgnoreCase("INTEGER")) {
						jsType = "number";
					}else if(rsmd.getColumnTypeName(i).equalsIgnoreCase("TIMESTAMP")||rsmd.getColumnTypeName(i).equalsIgnoreCase("TIME")||rsmd.getColumnTypeName(i).equalsIgnoreCase("DATE")) {
						jsType = "date";
					}
					fieldMap.put("type",jsType);
					fiedList.add(fieldMap);
				}
				
				Record tableInfo = Db.findFirst("select * from sysibm.systables where type='T' and NAME='"+tableName+"'  ORDER BY NAME ASC");
				Map<String, Object> tableMap = new HashMap<String, Object>();
				System.out.println(tableName);
				tableMap.put("tableRemarks", tableInfo.get("REMARKS"));
				tableMap.put("tableName", tableName.toLowerCase());
				tableMap.put("fieldList", fiedList);
				mapList.add(tableMap);
			}
			data.put("data", mapList);
			String genPath = PathKit.getWebRootPath()+File.separator+"js"+File.separator+"tableField"+File.separator+"tableField.js";
			System.out.println(genPath);
			System.out.println(LoserStarJsonUtil.toJson(mapList));
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableFieldJsTemp.ftl", data);
			LoserStarFileUtil.WriteStringToFilePath(string,genPath , false);
			System.out.println("生成tableField.js:"+genPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------------生成每张表的前端字段定义--------------------end");
	}
	
	/**
	 * 生成字典常量（前端使用）
	 */
	public static void genDictJs() {
		System.out.println("--------------生成字典常量--------------------begin");
		String tableName = "ZTSW.SYS_DICT";//字典表名称
		String typeFieldName = "type";//用来区分字典类型的字段名称
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			List<Record> typeList = Db.find("SELECT TYPE FROM "+tableName+" GROUP BY "+typeFieldName+"");
			for (Record record : typeList) {
				List<Record> dictList = Db.find("select * from "+tableName+" where type='"+record.getStr(typeFieldName)+"'");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", record.get(typeFieldName));
				map.put("list", dictList);
				mapList.add(map);
			}
			data.put("data", mapList);
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "dictJsTemp.ftl", data);
			String genPath = PathKit.getWebRootPath()+File.separator+"js"+File.separator+"dict"+File.separator+"dict.js";
			System.out.println(genPath);
			LoserStarFileUtil.PrintWriterToFile(genPath, string, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------------生成字典常量--------------------end");
	}
	
	/**
	 * 利用反射，生成每个vo对应的前端字段定义js
	 */
	public static void genVoField() {
		System.out.println("--------------生成vo的前端字段定义--------------------begin");
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			
			for (int i = 0; i < gennerateVoNames.length; i++) {
				Class vo_class = Class.forName(gennerateVoNames[i]);
				String voName = vo_class.getSimpleName();
				Field[] fieldArray = vo_class.getDeclaredFields();  
				List<Map<String, Object>> fiedList = new ArrayList<Map<String,Object>>();
				for(Field f : fieldArray){  
					Map<String, Object> fieldMap = new HashMap<String, Object>();
					fieldMap.put("name", f.getName());
					String jsType = "string";
					if(f.getType().getSimpleName().equalsIgnoreCase("float")||
							f.getType().getSimpleName().equalsIgnoreCase("Double")||
							f.getType().getSimpleName().equalsIgnoreCase("BigDecimal")||
							f.getType().getSimpleName().equalsIgnoreCase("int")||
							f.getType().getSimpleName().equalsIgnoreCase("Integer")) {
						jsType = "number";
					}else if(f.getType().getSimpleName().equalsIgnoreCase("Date")) {
						jsType = "date";
					}
					fieldMap.put("type",jsType);
					fiedList.add(fieldMap);
				}
				Map<String, Object> tableMap = new HashMap<String, Object>();
				tableMap.put("voName", voName);
				tableMap.put("fieldList", fiedList);
				mapList.add(tableMap);
			}
			
			
			data.put("data", mapList);
			String genPath = PathKit.getWebRootPath()+File.separator+"js"+File.separator+"voField"+File.separator+"voField.js";
			System.out.println(genPath);
			System.out.println(LoserStarJsonUtil.toJson(mapList));
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "voFieldJsTemp.ftl", data);
			LoserStarFileUtil.PrintWriterToFile(genPath, string, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--------------生成vo的前端字段定义--------------------begin");
	}
}
