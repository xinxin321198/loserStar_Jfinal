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
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.loserstar.config.generator.LoserStarMetaBuilderDB2;
import com.loserstar.constants.DsConstans;
import com.loserstar.constants.DsConstans.dataSourceName;
import com.loserstar.test.EntityGennerate.GenCodeTableVo.GenCodeFiledVo;
import com.loserstar.utils.date.LoserStarDateUtils;
import com.loserstar.utils.db.jfinal.base.imp.BaseDao.DBType;
import com.loserstar.utils.file.LoserStarFileUtil;
import com.loserstar.utils.freemarker.LoserStarFreemarkerUtil;
import com.loserstar.utils.json.LoserStarJsonUtil;

/**
 * 
 * author: loserStar
 * date: 2023年11月1日下午4:46:43
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:代码生成器
 */
public class EntityGennerate {
	// 数据库类型(根据实际使用的数据库进行调整)
	private static DBType dbtype = DBType.mysql;

	// base model 所使用的包名
	private static String baseModelPackageName = "com.loserstar.entity.base";
	// base model 文件保存路径
	private static String baseModelOutputDir = PathKit.getWebRootPath() + "/../src/com/loserstar/entity/base";
	// base model 生成时所使用的模板文件路径（如果为空则使用jfinal官方的原版模板）
	private static String baseModelTemplate = "/codeGenTemplate/tableBaseModelTemplate.jf";
	// model 所使用的包名 (MappingKit 默认使用的包名)
	private static String modelPackageName = "com.loserstar.entity";
	// model 生成时所使用的模板文件（如果为空则使用jfinal官方的原版模板）
	private static String modelTemplate = "/codeGenTemplate/tableModelTemplate.jf";

	// 生成的dao文件的包名
	private static String daoPakageName = "com.loserstar.dao";
	// dao文件的生成目录
	private static String daoOutPath = PathKit.getWebRootPath() + File.separator + ".." + File.separator + "src" + File.separator + "com" + File.separator + "loserstar" + File.separator + "dao" + File.separator;

	// 后端字典常量的包名
	private static String dictConstantsPakageName = "com.loserstar.constants";
	// 后端字典常量生成路径
	private static String dictConstantsOutPath = PathKit.getWebRootPath() + File.separator + ".." + File.separator + "src" + File.separator + "com" + File.separator + "loserstar" + File.separator + "constants" + File.separator;

	// Jqwidgets所使用的表字段定义的js文件
	private static String tableFieldOutPath = PathKit.getWebRootPath() + File.separator + "js" + File.separator + "tableField" + File.separator;

	// 前端适应的常量js
	private static String dictJsOutPath = PathKit.getWebRootPath() + File.separator + "js" + File.separator + "dict" + File.separator;

	// Jqwidgets所使用的vo的字段定义
	private static String voFieldJsOutPath = PathKit.getWebRootPath() + File.separator + "js" + File.separator + "voField" + File.separator;
	
	//bootstrap表单生成的目录
	private static String bootstrapFormHtmlOutPath =PathKit.getWebRootPath()+File.separator+"html"+File.separator+"bootstrapForm"+File.separator;
	//boostrap生成的列表上的查看、编辑、删除的按钮样式（original:原始，icon:图标，dropdown:下拉按钮组，all:全部形式）
	private static String bootstrapListHtmlBtnStyle = "all";
	

	private static String[] gennerateTableNames_local = {
			// 这里的代码要不按数据库显示顺序排序，调整一次太费力了
			"loserstar.sys_dict",
			"loserstar.sys_user",
			"loserstar.sys_file",
			};

	/**
	 * 生成jqwidgets使用的前端字段定义（通过反射生成，需要类的全包路径）
	 */
	private static String[] gennerateVoNames = { "com.loserstar.entity.vo.TestVo" };


	/**
	 * @param args
	 */
	public static void main(String[] args) {
				//生成ztsw的表相关文件
				DataSource dataSource = GenCodeDBConfig_local.start();
				gengEntity(dataSource,DsConstans.dataSourceName.local,gennerateTableNames_local,baseModelPackageName,baseModelOutputDir,modelPackageName);
				genDao(dataSource,DsConstans.dataSourceName.local,gennerateTableNames_local,daoPakageName,daoOutPath);
//				genJqwidgetsTableField(dataSource,DsConstans.dataSourceName.gbgl,gennerateTableNames_local,"kaen",tableFieldOutPath);
//				
				genConstants(DsConstans.dataSourceName.local,dictConstantsPakageName,dictConstantsOutPath,"loserstar.sys_dict","dict_type");
				genDictJs(DsConstans.dataSourceName.local,dictJsOutPath,"loserstar.sys_dict","dict_type");
//				genVoField(voFieldJsOutPath);
				genBootstrapFormHtml(dataSource, DsConstans.dataSourceName.local, gennerateTableNames_local, bootstrapFormHtmlOutPath);
	}
	
	/**
	 * 代码生成器使用的table vo
	 * author: loserStar
	 * date: 2023年9月19日下午2:50:15
	 * remarks:
	 */
	public static class GenCodeTableVo{
		
		/**
		 * 列表按钮生成的样式，参考com.loserstar.test.EntityGennerate.bootstrapListHtmlBtnStyle
		 */
		private String listBtnStyle;
		
		/**
		 * 数据源名称
		 */
		 private String dataSouceName;
		
		/**
		 * 表备注
		 */
		private String tableRemarks;
		
		/**
		 * 主键字段（小写）
		 */
		private String primaryKey;
		
		/**
		 * 主键字段转换为驼峰命名（首字母大写）
		 */
		private String primaryKeyHumName;
		

		/**
		 * 类名（表名转换为驼峰命名,首字母大写）
		 */
		private String className;
		
		/**
		 * 类名（表名转换为驼峰命名,首字母小写）
		 */
		private String fristLowerClassName;
		/**
		 * 表名（小写）
		 */
		private String tableName;
		/**
		 * 字段信息
		 */
		private List<GenCodeFiledVo> fieldList = new ArrayList<GenCodeFiledVo>();
		
		public String getListBtnStyle() {
			return listBtnStyle;
		}

		public void setListBtnStyle(String listBtnStyle) {
			this.listBtnStyle = listBtnStyle;
		}

		public String getDataSouceName() {
			return dataSouceName;
		}

		public void setDataSouceName(String dataSouceName) {
			this.dataSouceName = dataSouceName;
		}

		public String getTableRemarks() {
			return tableRemarks;
		}

		public void setTableRemarks(String tableRemarks) {
			this.tableRemarks = tableRemarks;
		}

		public String getPrimaryKey() {
			return primaryKey;
		}

		public void setPrimaryKey(String primaryKey) {
			this.primaryKey = primaryKey;
		}

		public String getPrimaryKeyHumName() {
			return primaryKeyHumName;
		}

		public void setPrimaryKeyHumName(String primaryKeyHumName) {
			this.primaryKeyHumName = primaryKeyHumName;
		}

		public String getFristLowerClassName() {
			return fristLowerClassName;
		}

		public void setFristLowerClassName(String fristLowerClassName) {
			this.fristLowerClassName = fristLowerClassName;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public List<GenCodeFiledVo> getFieldList() {
			return fieldList;
		}

		public void setFieldList(List<GenCodeFiledVo> fieldList) {
			this.fieldList = fieldList;
		}

		/**
		 * 代码生成器使用的字段vo
		 * author: loserStar
		 * date: 2023年9月19日下午2:51:55
		 * remarks:
		 */
		public static class GenCodeFiledVo{
			
			/**
			 * 是否是主键
			 * Boolean类型不能使用isXxx，需要使用getXxx ，因为Freemarker使用java会对isXxx映射返回boolean基本型，但是freemarker不支持基本类型boolean，会抛异常。
			 * freemarker中输出时可以使用这种方式输出${xxx?string("true","flase")}当xxx为true时显示字符串true,否则为字符串false，当然true,false字符串也可以换成其他字符串，比如yes和no。
			 */
			private Boolean isPrimaryKey = false;
			/**
			 * 字段名（小写）
			 */
			private String name;
			/**
			 * 字段类型（不同数据库不一样）
			 */
			private String type;
			
			/**
			 * 字段备注
			 */
			private String remarks;

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getRemarks() {
				return remarks;
			}

			public void setRemarks(String remarks) {
				this.remarks = remarks;
			}

			public Boolean getIsPrimaryKey() {
				return isPrimaryKey;
			}

			public void setIsPrimaryKey(Boolean isPrimaryKey) {
				this.isPrimaryKey = isPrimaryKey;
			}

		}
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
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(false);
		//查询表的sql（不同数据库的语句不同，自行调整）
		String queryTablesSql = getTableMetaSql(dbtype, null);
		List<Record> tableList = Db.use(dataSouceName).find(queryTablesSql);
		for (Record table : tableList) {
//						System.out.println("generator.addExcludedTable(\""+record.get("name")+"\");");
			//排除掉不在我打算生成的列表里的表名
			String tableName = table.getStr("table_name");
			//这里有BUG（不同的schemas，有多张同名的表，那么如果排除了该表，就叫该表名的表都不会生成实体）
			boolean isContains = false;//是否包含在我要生成的表列表中
			for (String genTableName : gennerateTableNameList) {
				String singleGenTableName = (genTableName.contains(".")? genTableName.split("\\.")[1]:genTableName);
				if (singleGenTableName.equals(tableName)) {
					isContains = true;
				}
			}
			if (!isContains) {
				generator.addExcludedTable(tableName);
			}
		}
		// 设置是否生成字典文件
		generator.setGenerateDataDictionary(false);
		//数据库方言
		if (dbtype==DBType.db2) {
			generator.setDialect(new AnsiSqlDialect());
		}else if(dbtype==DBType.mysql) {
			generator.setDialect(new AnsiSqlDialect());//mysql也用这个，否则通过带schemas的表名，拼出来的sql语法错误
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
//		generator.setGenerateRemarks(true);
	
		generator.setModelTemplate(modelTemplate);
		generator.setBaseModelTemplate(baseModelTemplate);
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
			for (String tableName1 : gennerateTableNameList) {
				String schemasTableName = tableName1;
				String singleTableName = (tableName1.contains(".")? tableName1.split("\\.")[1]:tableName1);
				Statement stm = conn.createStatement();
				DatabaseMetaData dbMetaData = conn.getMetaData();
				 ResultSet resultSet = dbMetaData.getTables(null, "%", "%", new String[] { singleTableName });
		            while (resultSet.next()) {
		                String remarkes = resultSet.getString("REMARKS");
		                System.out.println(singleTableName+"="+remarkes);
		            }
//				System.out.println("表名begin----------："+tableName);
//				System.out.println("主键：");
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,singleTableName);  
				String primaryKeyColumnName = "";
				while(primaryKeyResultSet.next()){  
				    primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
//				    System.out.println(primaryKeyColumnName);
				} 
				if (primaryKeyColumnName==null||primaryKeyColumnName.equals("")) {
					throw new Exception(tableName1+"没有设置主键");
				}
				/*
				ResultSet rs = stm.executeQuery("select * from " + tableName + " where 1 = 2");
				ResultSetMetaData rsmd = rs.getMetaData();
								System.out.println("字段：");
								for (int i=1; i<=rsmd.getColumnCount(); i++) {
									System.out.println(rsmd.getColumnName(i));
								}*/
//				System.out.println("表名end----------："+tableName);
				/*				if (tableName.equals("SYS_FILE")) {
									System.out.println("234");
								}
				*/				String queryTablesSql = getTableMetaSql(dbtype, schemasTableName);

				Record tableInfo = Db.use(dataSouceName).findFirst(queryTablesSql);
				Map<String, Object> data = new HashMap<String, Object>();
				System.out.println(singleTableName);
				if (tableInfo.get("REMARKS")==null||tableInfo.get("REMARKS").equals("")) {
					throw new Exception(schemasTableName+"---还没有设置备注");
				}
				data.put("tableRemarks", tableInfo.get("REMARKS"));
				String className = convertTableNameToHum(singleTableName,true);
				className+="Dao";
				data.put("packgeName",packgeName);
				data.put("className", className);
				data.put("tableName", singleTableName);
				data.put("primaryKey", primaryKeyColumnName);
				data.put("schemas", tableInfo.get("schemas").toString().trim());
				data.put("genDate", LoserStarDateUtils.format(new Date()));
//				String genPath = "D:\\development\\keWorkSpace\\HtWearhouse\\src\\com\\kaen\\dao\\"+className+".java";
				String genPath = daoOutPath+className+".java";
				if (!new File(genPath).exists()) {
					String string = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableDaoTemp.ftl", data);
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
			for (String tableName1 : gennerateTableNameList) {
				String schemasTableName = tableName1;
				String singleTableName = (tableName1.contains(".")? tableName1.split("\\.")[1]:tableName1);
				Statement stm = conn.createStatement();
				DatabaseMetaData dbMetaData = conn.getMetaData();
				 ResultSet resultSet = dbMetaData.getTables(null, "%", "%", new String[] { singleTableName });
		            while (resultSet.next()) {
		                String remarkes = resultSet.getString("dict_remarks");
		                System.out.println(singleTableName+"="+remarkes);
		            }
				ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,singleTableName);  
				while(primaryKeyResultSet.next()){  
					//主键
//					String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
//				    System.out.println(primaryKeyColumnName);
				} 
				
				List<Map<String, Object>> fiedList = new ArrayList<Map<String,Object>>();
				ResultSet rs = stm.executeQuery("select * from " + schemasTableName + " where 1 = 2");
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
				
				String queryTablesSql = getTableMetaSql(dbtype, schemasTableName);
				Record tableInfo = Db.use(dataSouceName).findFirst(queryTablesSql);
				Map<String, Object> tableMap = new HashMap<String, Object>();
				System.out.println(schemasTableName);
				if (tableInfo.get("REMARKS")==null||tableInfo.get("REMARKS").equals("")) {
					throw new Exception(schemasTableName+"---还没有设置备注");
				}
				tableMap.put("tableRemarks", tableInfo.get("REMARKS"));
				tableMap.put("tableName", singleTableName.toLowerCase());
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
	
	/**
	 * 生成没张表对应的bootstrap的form表单html
	 * @param dataSource 数据源
	 * @param dataSouceName 数据源名称
	 * @param genTableArray 要生成的表数组
	 * @param suffix 后缀，相当于schema的名称
	 * @param outPath 文件输出目录（不要包含文件名，仅目录）
	 */
	public static void genBootstrapFormHtml(DataSource dataSource,String dataSouceName,String [] genTableArray,String outPath) {
		System.out.println("--------------生成每张表的bootstrap表单核心代码--------------------begin");
		try {
			List<GenCodeTableVo> tableVos = getGenCodeTableVo(dataSource, dataSouceName, genTableArray);
			System.out.println(LoserStarJsonUtil.toJson(tableVos));
			for (GenCodeTableVo vo : tableVos) {
				//生成表单html
				String formHtmlPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+vo.getFristLowerClassName()+"Form.html";
				String formHtmlStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableBootstrapFromHtmlTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(formHtmlStr,formHtmlPath , false);
				
				//生成列表html
				String listPageHtmlPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+vo.getFristLowerClassName()+"List.html";
				String listPageHtmlStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableBootstrapTableListHtmlTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(listPageHtmlStr,listPageHtmlPath , false);
				
				//生成集成adminLTE的表单html
				String formHtmlPathForAdminLTE = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+vo.getFristLowerClassName()+"FormForAdminLTE.html";
				String formHtmlStrForAdminLTE = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableBootstrapFromHtmlTempForAdminLTE.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(formHtmlStrForAdminLTE,formHtmlPathForAdminLTE , false);
				
				//生成集成adminLTE的列表html
				String listPageHtmlPathForAdminLTE = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+vo.getFristLowerClassName()+"ListForAdminLTE.html";
				String listPageHtmlStrForAdminLTE = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableBootstrapTableListHtmlTempForAdminLTE.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(listPageHtmlStrForAdminLTE,listPageHtmlPathForAdminLTE , false);
				
				
				//生成action.js
				String actionJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"Action.js";
				String actionJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableActionJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(actionJsStr,actionJsPath , false);
				//生成表单的js
				String fromJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"Form.js";
				String fromJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableFormJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(fromJsStr,fromJsPath , false);
				//生成表单事件的js
				String fromEventJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"FormEvent.js";
				String fromEventJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableFormEventJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(fromEventJsStr,fromEventJsPath , false);
				//生成列表js
				String listJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"List.js";
				String listJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableListJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(listJsStr,listJsPath , false);
				//生成列表事件js
				String listEventJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"ListEvent.js";
				String listEventtJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableListEventJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(listEventtJsStr,listEventJsPath , false);
				//生成树形列表事件js
				String treeListEventJsPath = outPath+vo.getTableName()+File.separator+"web"+File.separator+vo.getFristLowerClassName()+File.separator+"js"+File.separator+vo.getFristLowerClassName()+"TreeListEvent.js";
				String treeListEventtJsStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableTreeListEventJsTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(treeListEventtJsStr,treeListEventJsPath , false);
				
				//生成jfinal的controller Java 类
				String controllerJavaPath = outPath+vo.getTableName()+File.separator+"java"+File.separator+vo.getClassName()+"Controller.java";
				String controllerJavaStr = LoserStarFreemarkerUtil.runForFileSystem(LoserStarFreemarkerUtil.class.getResource("/codeGenTemplate").getPath(), "tableControllerTemp.ftl", vo);
				LoserStarFileUtil.WriteStringToFilePath(controllerJavaStr,controllerJavaPath , false);
				System.out.println(vo.getTableName()+"-------bootstrap");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println("--------------生成每张表的bootstrap表单核心代码--------------------end");
	}
	
	/**
	 * 获取表信息
	 * @param dbType
	 * @param tableName
	 * @return
	 */
	private static String getTableMetaSql(DBType dbType,String tableName)
	{
		String queryTablesSql = "";
		String scheamasWhere = "";
		String singleTableName = "";

		if (dbtype==DBType.db2) {
			String tableWhere = "";
			if (tableName!=null&&!tableName.equals("")) {
				if (tableName.contains(".")) {
					scheamasWhere = " AND CREATOR='"+tableName.split("\\.")[0]+"'";
					singleTableName = tableName.split("\\.")[1];
				}else {
					singleTableName = tableName;
				}
				tableWhere+=" and NAME='"+singleTableName+"'";
			}
			queryTablesSql = "select name as table_name,creator as schemas,remarks as remarks from sysibm.systables where 1=1 and  type='T' "+tableWhere+" "+scheamasWhere+"  ORDER BY table_name ASC";
		}else if(dbtype==DBType.mysql) {
			String tableWhere = "";
			if (tableName!=null&&!tableName.equals("")) {
				if (tableName.contains(".")) {
					scheamasWhere = " AND table_schema='"+tableName.split("\\.")[0]+"'";
					singleTableName = tableName.split("\\.")[1];
				}else {
					singleTableName = tableName;
				}
				tableWhere+=" and table_name='"+singleTableName+"'";
			}
			queryTablesSql = "SELECT table_name,table_schema as `schemas`,table_comment as remarks FROM information_schema.TABLES where 1=1  "+tableWhere+" "+scheamasWhere+" ORDER BY table_name ASC";
		}else {
			System.out.println("未知的数据库类型");
		}
		return queryTablesSql;
	}

	/**
	 * 获取表字段的信息
	 * @param dbType
	 * @param tableName
	 * @return
	 */
	private static String getTableColumsSql(DBType dbType,String tableName)
	{
		String queryTablesSql = "";
		String scheamasWhere = "";
		String singleTableName = "";

		if (dbtype==DBType.db2) {
			String tableWhere = "";
			if (tableName!=null&&!tableName.equals("")) {
				if (tableName.contains(".")) {
					scheamasWhere = " AND TBCREATOR='"+tableName.split("\\.")[0]+"'";
					singleTableName = tableName.split("\\.")[1];
				}else {
					singleTableName = tableName;
				}
				tableWhere+=" and TBNAME='"+singleTableName+"'";
			}
			queryTablesSql = "SELECT NAME,TBNAME,TBCREATOR,REMARKS,COLTYPE,\"NULLS\",\"LENGTH\",COLNO FROM \"SYSIBM\".SYSCOLUMNS WHERE 1=1 "+tableWhere+" "+scheamasWhere+" ORDER BY COLNO ASC";
		}else if(dbtype==DBType.mysql) {
			String tableWhere = "";
			if (tableName!=null&&!tableName.equals("")) {
				if (tableName.contains(".")) {
					scheamasWhere = " AND table_schema='"+tableName.split("\\.")[0]+"'";
					singleTableName = tableName.split("\\.")[1];
				}else {
					singleTableName = tableName;
				}
				tableWhere+=" and table_name='"+singleTableName+"'";
			}
			queryTablesSql = "SELECT COLUMN_NAME AS NAME,TABLE_NAME AS TBNAME,TABLE_SCHEMA AS TBCREATOR,COLUMN_COMMENT AS REMARKS,COLUMN_TYPE AS COLTYPE,IS_NULLABLE AS \"NULLS\",CHARACTER_MAXIMUM_LENGTH AS \"LENGTH\",ORDINAL_POSITION AS COLNO FROM information_schema.`COLUMNS`  where 1=1  "+tableWhere+" "+scheamasWhere+" ORDER BY ORDINAL_POSITION ASC";
		}else {
			System.out.println("未知的数据库类型");
		}
		return queryTablesSql;
	}

	
	/**
	 * 转换表名称驼峰命名
	 * @param tableName
	 * @param firstIsUpper 首字母是否大写
	 * @return
	 */
	private static String convertTableNameToHum(String tableName,boolean firstIsUpper) {
		String humpName = "";
		if (tableName.contains(".")) {
			String tmpTableName = tableName.split("\\.")[1];
			humpName = convertToHump(tmpTableName,firstIsUpper);
		}else {
			humpName = convertToHump(tableName,firstIsUpper);
		}
		return humpName;
	}
	
	/**
	 * 下划线转化为驼峰命名
	 * @param name 名称
	 * @param firstIsUpper 首字母是否大写
	 * @return
	 */
	private static String convertToHump(String name,boolean firstIsUpper) {
		if (name.equals("")) {
			System.out.println();
		}
		String humpName = "";
		String[] tableNameArray = name.split("_");
		for (int i = 0; i < tableNameArray.length; i++) {
			String tmpName=tableNameArray[i];
			//首字母 && 需要大写
			if (i==0) {
				if (firstIsUpper) {
					humpName+=tmpName.substring(0,1).toUpperCase()+tmpName.substring(1,tmpName.length()).toLowerCase();;
				}else {
					humpName+=tmpName.substring(0,1).toLowerCase()+tmpName.substring(1,tmpName.length()).toLowerCase();;
				}
			}else {
				humpName+=tmpName.substring(0,1).toUpperCase()+tmpName.substring(1,tmpName.length()).toLowerCase();;
			}
		}
		return humpName;
	}
	
	/**
	 * 获取表相关的信息
	 * @param dataSource
	 * @param dataSouceName
	 * @param tableNames
	 * @return
	 * @throws Exception 
	 */
	private static List<GenCodeTableVo> getGenCodeTableVo(DataSource dataSource,String dataSouceName,String[] tableNames) throws Exception {
		List<GenCodeTableVo> list = new ArrayList<GenCodeTableVo>();
		Connection conn = dataSource.getConnection();
		for (String tableName1 : tableNames) {
			GenCodeTableVo data = new GenCodeTableVo();
			data.setListBtnStyle(bootstrapListHtmlBtnStyle);
			String schemasTableName = tableName1;//表空间
			String singleTableName = (tableName1.contains(".")? tableName1.split("\\.")[1]:tableName1);//单表名
			//从元数据里找到该表相关信息
			DatabaseMetaData dbMetaData = conn.getMetaData();
			//基本信息
			ResultSet resultSet = dbMetaData.getTables(null, "%", "%", new String[] { singleTableName });
			while (resultSet.next()) {
//	                String remarkes = resultSet.getString("REMARKS");
//	                System.out.println(singleTableName+"="+remarkes);
            }
			//主键信息
			ResultSet primaryKeyResultSet = dbMetaData.getPrimaryKeys(null,null,singleTableName);  
			String primaryKeyColumnName = "";
			//遍历查找主键
			while(primaryKeyResultSet.next()){  
				primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME"); 
			}
			if (primaryKeyColumnName==null||primaryKeyColumnName.equals("")) {
				throw new Exception(tableName1+"主键不能为空啊");
			}
			//通过sql去找表信息（元数据的方式有些信息没有）
			String queryTablesSql = getTableMetaSql(dbtype, schemasTableName);
			Record tableInfo = Db.use(dataSouceName).findFirst(queryTablesSql);
			if (tableInfo.get("REMARKS")==null||tableInfo.get("REMARKS").equals("")) {
				throw new Exception(schemasTableName+"---还没有设置备注");
			}
			data.setTableName(singleTableName.toLowerCase());
			data.setClassName(convertTableNameToHum(singleTableName,true));
			data.setFristLowerClassName(convertTableNameToHum(singleTableName,false));
			data.setPrimaryKey(primaryKeyColumnName.toLowerCase());
			data.setPrimaryKeyHumName(convertToHump(primaryKeyColumnName,true));
			data.setTableRemarks(tableInfo.get("REMARKS").toString());
			data.setDataSouceName(dataSouceName);
			
			//查找表字段
			List<GenCodeFiledVo> fiedList = new ArrayList<GenCodeFiledVo>();
			String columsSql =  getTableColumsSql(dbtype, schemasTableName);
			List<Record> tableColumns =  Db.use(dataSouceName).find(columsSql);
			for (Record colum : tableColumns) {
				GenCodeFiledVo fieldMap = new GenCodeFiledVo();
				fieldMap.setName(colum.getStr("NAME").toLowerCase());
				fieldMap.setType(colum.getStr("COLTYPE"));
				fieldMap.setRemarks(colum.getStr("REMARKS"));
				if (primaryKeyColumnName.toLowerCase().equals(fieldMap.getName())) {
					fieldMap.setIsPrimaryKey(true);
				}
				fiedList.add(fieldMap);
			}
			data.setFieldList(fiedList);
			list.add(data);
		}
		return list;
	}
}
