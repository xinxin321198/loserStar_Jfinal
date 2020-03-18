/**
 * author: loserStar
 * date: 2020年3月11日上午11:15:17
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.test;

import java.util.Arrays;
import java.util.List;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.loserstar.utils.db.jfinal.base.imp.BaseService.DBType;

/**
 * author: loserStar
 * date: 2020年3月11日上午11:15:17
 * remarks:
 */
public class EntityGennerate {
	//数据库类型(根据实际使用的数据库进行调整)
	private static DBType dbtype = DBType.mysql;

	//要生成的表名
	private static String[] gennerateTableNames =  {
			"da_account",
			"sys_users"
			};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gengEntity();
	}
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
		String baseModelPackageName = "com.loserstar.entity.base";
		// base model 文件保存路径
		String baseModelOutputDir =PathKit.getWebRootPath() + "/../src/com/loserstar/entity/base";
		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.loserstar.entity";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";
		// 创建生成器
			Generator generator = new Generator(PurchaseDBConfig.start(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
			
			// 设置是否在 Model 中生成 dao 对象
			generator.setGenerateDaoInModel(false);
			
			List<Record> tableList = Db.find(queryTablesSql);
			for (Record table : tableList) {
//				System.out.println("generator.addExcludedTable(\""+record.get("name")+"\");");
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
//			generator.setGenerateRemarks(true);
			// 生成
			generator.generate();
	}

}
