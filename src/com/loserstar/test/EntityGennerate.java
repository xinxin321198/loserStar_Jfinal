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
import com.loserstar.constants.DsConstans;
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
	private static DBType dbtype = DBType.mysql;

	// base model 所使用的包名
	private static String baseModelPackageName = "com.loserstar.entity.base";
	// base model 文件保存路径
	private static String baseModelOutputDir =PathKit.getWebRootPath() + "/../src/com/loserstar/entity/base";
	//base model 生成时所使用的模板文件路径（如果为空则使用jfinal官方的原版模板）
	private static String baseModelTemplate = "/codeGenTemplate/base_model_template.jf";
	// model 所使用的包名 (MappingKit 默认使用的包名)
	private static String modelPackageName = "com.loserstar.entity";
	//model 生成时所使用的模板文件（如果为空则使用jfinal官方的原版模板）
	private static String modelTemplate = "/codeGenTemplate/model_template.jf";
	
	//生成的dao文件的包名
	private static String daoPakageName = "com.loserstar.dao";
	//dao文件的生成目录
	private static String daoOutPath = PathKit.getWebRootPath()+File.separator+".."+File.separator+"src"+File.separator+"com"+File.separator+"loserstar"+File.separator+"dao"+File.separator;
	
	//后端字典常量的包名
	private static String  dictConstantsPakageName = "com.loserstar.constants";
	//后端字典常量生成路径
	private static String dictConstantsOutPath = PathKit.getWebRootPath()+File.separator+".."+File.separator+"src"+File.separator+"com"+File.separator+"loserstar"+File.separator+"constants"+File.separator;
	
	//Jqwidgets所使用的表字段定义的js文件
	private static String tableFieldOutPath = PathKit.getWebRootPath()+File.separator+"js"+File.separator+"tableField"+File.separator;
	
	//前端适应的常量js
	private static String dictJsOutPath = PathKit.getWebRootPath()+File.separator+"js"+File.separator+"dict"+File.separator;
	
	//Jqwidgets所使用的vo的字段定义
	private static String voFieldJsOutPath =PathKit.getWebRootPath()+File.separator+"js"+File.separator+"voField"+File.separator;
	
	
	private static String[] gennerateTableNames_local =  {
			//这里的代码要不按数据库显示顺序排序，调整一次太费力了
			"loserstar.da_account",
			"loserstar.sys_users",
			"loserstar.sys_dict"
	};

	/**
	 * 生成jqwidgets使用的前端字段定义（通过反射生成，需要类的全包路径）
	 */
	private static String[] gennerateVoNames =  {
			"com.loserstar.entity.vo.TestVo"
	};
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				//生成ztsw的表相关文件
				DataSource dataSource = GenCodeDBConfig_local.start();
				gengEntity(dataSource,DsConstans.dataSourceName.local,gennerateTableNames_local,baseModelPackageName,baseModelOutputDir,modelPackageName);
				genDao(dataSource,DsConstans.dataSourceName.local,gennerateTableNames_local,daoPakageName,daoOutPath);
				genJqwidgetsTableField(dataSource,DsConstans.dataSourceName.local,gennerateTableNames_local,"loserstar",tableFieldOutPath);
				
				genConstants(DsConstans.dataSourceName.local,dictConstantsPakageName,dictConstantsOutPath,"loserstar.sys_dict","dict_type");
				genDictJs(DsConstans.dataSourceName.local,dictJsOutPath,"loserstar.sys_dict","dict_type");
				genVoField(voFieldJsOutPath);
	}


	/**
	 *  jfinnal的代码生成器，生成entity，mapping文件
	 * @param dataSource 数据源
	 * @param dataSouceName 数据源名称
	 * @param genTableArray 要生成的table
	 * @param baseModelPackageName baseModel包名
	 * @param baseModelOutputDir baseModel文件输出路径
	 * @param modelPackageName model包名（model文件输出路径为baseModel文件输出路径的上一层）
	 */
	public static void gengEntity(DataSource dataSource,String dataSouceName,String [] genTableArray,String baseModelPackageName,String baseModelOutputDir,String modelPackageName) {
		//要生成的表名
		List<String> gennerateTableNameList =Arrays.asList(genTableArray);

		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		// 创建生成器
		Generator generator = new Generator(dataSource, baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		generator.setMetaBuilder(new LoserStarMetaBuilderDB2(dataSource));
		if (baseModelTemplate!=null&&!baseModelTemplate.equals("")) {
			generator.setBaseModelTemplate(baseModelTemplate);
		}
		if (modelTemplate!=null&&!modelTemplate.equals("")) {
			generator.setModelTemplate(modelTemplate);
		}
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(false);
		//查询表的sql（不同数据库的语句不同，自行调整）
		String queryTablesSql = "";
		if (dbtype==DBType.db2) {
			queryTablesSql = "select name as table_name,creator as schemas from sysibm.systables where type='T'  ORDER BY table_name ASC";
		}else if(dbtype==DBType.mysql) {
			queryTablesSql = "SELECT table_name,table_schema as `schemas` FROM information_schema.TABLES  ORDER BY table_name ASC";
		}else {
			System.out.println("未知的数据库类型");
			return;
		}
		List<Record> tableList = Db.use(dataSouceName).find(queryTablesSql);
		for (Record table : tableList) {
//						System.out.println("generator.addExcludedTable(\""+record.get("name")+"\");");
			//排除掉不在我打算生成的列表里的表名
			String tableName = table.getStr("table_name");
			if ("PERSONTEST".equalsIgnoreCase(tableName)) {
				System.out.println("123");
			}
			if ((!gennerateTableNameList.contains( table.getStr("schemas").trim()+"."+tableName))) {
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
		// 配置是否生成备注( 此版本貌似没有)（扩展了代码生成器和模板，可以生成备注了）
//		generator.setGenerateRemarks(true);
	
		// 生成
		generator.generate();
	}

	/**
	 * 生成dao文件
	 * @param dataSource 数据源
	 * @param dataSouceName 数据源名称
	 * @param genTableArray 要生成dao的表
	 * @param packgeName 包名
	 * @param daoOutPath 文件输出目录（不要包含文件名仅目录）
	 */
	public static void genDao(DataSource dataSource,String dataSouceName,String [] genTableArray,String packgeName,String daoOutPath) {
		System.out.println("--------------生成Dao--------------------begin");
		try {
			Connection conn = dataSource.getConnection();
			//要生成的表名
			List<String> gennerateTableNameList =Arrays.asList(genTableArray);
			for (String tableName : gennerateTableNameList) {
				tableName = (tableName.contains(".")? tableName.split("\\.")[1]:tableName);
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
				
				String queryTablesSql = "";
				if (dbtype==DBType.db2) {
					queryTablesSql = "select name as table_name,creator as schemas,remarks as remarks from sysibm.systables where type='T' and NAME='"+(tableName.contains(".")? tableName.split("\\.")[1]:tableName)+"' ORDER BY table_name ASC";
				}else if(dbtype==DBType.mysql) {
					queryTablesSql = "SELECT table_name,table_schema as `schemas`,table_comment as remarks FROM information_schema.TABLES where table_name='"+tableName+"' ORDER BY table_name ASC";
				}else {
					System.out.println("未知的数据库类型");
					return;
				}
				Record tableInfo = Db.use(dataSouceName).findFirst(queryTablesSql);
				Map<String, Object> data = new HashMap<String, Object>();
				System.out.println(tableName);
				if (tableInfo.get("REMARKS")==null||tableInfo.get("REMARKS").equals("")) {
					throw new Exception(tableName+"---还没有设置备注");
				}
				data.put("tableRemarks", tableInfo.get("REMARKS"));
				String className = "";
				String[] tableNameArray = tableName.split("_");
				for (int i = 0; i < tableNameArray.length; i++) {
					String tmpName=tableNameArray[i];
					if(tmpName.contains(".")){
						tmpName=tmpName.split("\\.")[1];
					}
					className+=tmpName.substring(0,1).toUpperCase()+tmpName.substring(1,tmpName.length()).toLowerCase();
				}
				className+="Dao";
				data.put("packgeName",packgeName);
				data.put("className", className);
				data.put("tableName", (tableName.contains(".")? tableName.split("\\.")[1]:tableName));
				data.put("primaryKey", primaryKeyColumnName);
				data.put("schemas", tableInfo.get("schemas").toString().trim());
				data.put("genDate", LoserStarDateUtils.format(new Date()));
//				String genPath = "D:\\development\\keWorkSpace\\HtWearhouse\\src\\com\\kaen\\dao\\"+className+".java";
				String genPath = daoOutPath+className+".java";
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
	 * @param dataSouceName 数据源名称
	 * @param dictConstantsPakageName 包名
	 * @param dictJavaOutPath 文件输出目录（不要包含文件名仅目录）
	 * @param tableName 字典表名称
	 * @param typeFieldName 用来区分字典类型的字段名称
	 */
	public static void genConstants(String dataSouceName,String  dictConstantsPakageName,String dictJavaOutPath,String tableName,String typeFieldName) {
		System.out.println("-----------------------生成后端常量java类Constans--------------begin");
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			List<Record> typeList = Db.use(dataSouceName).find("SELECT "+typeFieldName+" FROM "+tableName+" where del='0' GROUP BY "+typeFieldName+"");
			for (Record record : typeList) {
				List<Record> dictList = Db.use(dataSouceName).find("select * from "+tableName+" where "+typeFieldName+"='"+record.getStr(typeFieldName)+"' and del='0' order by dict_sort asc");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dict_type", record.get(typeFieldName));
				map.put("list", dictList);
				mapList.add(map);
			}
			data.put("packgeName", dictConstantsPakageName);
			data.put("data", mapList);
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "dictConstantsTemp.ftl", data);
			String outPath = dictJavaOutPath+"DictConstants.java";
			System.out.println(outPath);
			LoserStarFileUtil.PrintWriterToFile(outPath, string, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-----------------------生成后端常量java类Constans--------------end");
	}
	
	/**
	 * 生成每张表对应的前端js数据源字段
	 * @param dataSource 数据源
	 * @param dataSouceName 数据源名称
	 * @param genTableArray 要生成的表数组
	 * @param suffix 后缀，相当于schema的名称
	 * @param outPath 文件输出目录（不要包含文件名仅目录）
	 */
	public static void genJqwidgetsTableField(DataSource dataSource,String dataSouceName,String [] genTableArray,String suffix,String outPath) {
		System.out.println("--------------生成每张表的前端字段定义--------------------begin");
		try {
			Connection conn = dataSource.getConnection();
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			//要生成的表名
			List<String> gennerateTableNameList =Arrays.asList(genTableArray);
			for (String tableName : gennerateTableNameList) {
				Statement stm = conn.createStatement();
				DatabaseMetaData dbMetaData = conn.getMetaData();
				 ResultSet resultSet = dbMetaData.getTables(null, "%", "%", new String[] { tableName });
		            while (resultSet.next()) {
		                String remarkes = resultSet.getString("dict_remarks");
		                System.out.println(tableName+"="+remarkes);
		            }
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,tableName.toLowerCase().split("\\.")[1]);  
				while(primaryKeyResultSet.next()){  
					//主键
//					String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
//				    System.out.println(primaryKeyColumnName);
				} 
				
				List<Map<String, Object>> fiedList = new ArrayList<Map<String,Object>>();
				ResultSet rs = stm.executeQuery("select * from " + tableName + " where 1 = 2");
				ResultSetMetaData rsmd = rs.getMetaData();
//				System.out.println("字段：");
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
				
				String queryTablesSql = "";
				String querTableName = (tableName.contains(".")? tableName.split("\\.")[1]:tableName);
				if (dbtype==DBType.db2) {
					queryTablesSql = "select name as table_name,creator as schemas,remarks as remarks from sysibm.systables where type='T' and NAME='"+querTableName+"' ORDER BY table_name ASC";
				}else if(dbtype==DBType.mysql) {
					queryTablesSql = "SELECT table_name,table_schema as `schemas`,table_comment as remarks FROM information_schema.TABLES where table_name='"+querTableName+"' ORDER BY table_name ASC";
				}else {
					System.out.println("未知的数据库类型");
					return;
				}
				Record tableInfo = Db.use(dataSouceName).findFirst(queryTablesSql);
				Map<String, Object> tableMap = new HashMap<String, Object>();
				System.out.println(tableName);
				if (tableInfo.get("REMARKS")==null||tableInfo.get("REMARKS").equals("")) {
					throw new Exception(tableName+"---还没有设置备注");
				}
				tableMap.put("tableRemarks", tableInfo.get("REMARKS"));
				tableMap.put("tableName", tableName.toLowerCase().split("\\.")[1]);
				tableMap.put("fieldList", fiedList);
				mapList.add(tableMap);
			}
			data.put("data", mapList);
			data.put("variableName", "tableField_"+suffix);
			String genPath = outPath+"tableField_"+suffix+".js";
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
	 * @param dataSouceName 数据源名称
	 * @param outPath 文件输出目录（不要包含文件名仅目录）
	 * @param tableName 字典表名称
	 * @param typeFieldName 用来区分字典类型的字段名称
	 */
	public static void genDictJs(String dataSouceName,String outPath,String tableName,String typeFieldName) {
		System.out.println("--------------生成前端字典常量--------------------begin");
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			List<Record> typeList = Db.use(dataSouceName).find("SELECT "+typeFieldName+" FROM "+tableName+" where del='0' GROUP BY "+typeFieldName+"");
			for (Record record : typeList) {
				List<Record> dictList = Db.use(dataSouceName).find("select * from "+tableName+" where "+typeFieldName+"='"+record.getStr(typeFieldName)+"' and del='0' order by dict_sort asc");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dict_type", record.get(typeFieldName));
				map.put("list", dictList);
				mapList.add(map);
			}
			data.put("data", mapList);
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "dictJsTemp.ftl", data);
			String genPath =outPath+"dict.js";
			System.out.println(genPath);
			LoserStarFileUtil.PrintWriterToFile(genPath, string, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("--------------生成前端字典常量--------------------end");
	}
	
	/**
	 * 利用反射，生成每个vo对应的前端字段定义js
	 * @param outPath文件输出目录（不要包含文件名仅目录）
	 */
	public static void genVoField(String outPath) {
		System.out.println("--------------生成vo的前端字段定义--------------------begin");
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
			
			for (int i = 0; i < gennerateVoNames.length; i++) {
				Class<?> vo_class = Class.forName(gennerateVoNames[i]);
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
			String genPath = outPath+"voField.js";
			System.out.println(genPath);
			System.out.println(LoserStarJsonUtil.toJson(mapList));
			String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "voFieldJsTemp.ftl", data);
			LoserStarFileUtil.PrintWriterToFile(genPath, string, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("--------------生成vo的前端字段定义--------------------end");
	}

}
