/**
 * author: loserStar
 * date: 2020年3月26日上午11:51:23
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.config.generator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

/**
 * author: loserStar
 * date: 2020年3月26日上午11:51:23
 * remarks:这鬼jfinal自己的生成器，默认会把DB2的字段名搞成大写了，所以要自己重写一下这个方法，让字段名小写
 */
public class LoserStarMetaBuilderDB2 extends MetaBuilder {

	protected boolean generateRemarks = true;//是否生成备注，3.4这种老版本的jfinal是不支持生成字段备注的，只能自己扩展
	
	/**
	 * @param dataSource
	 */
	public LoserStarMetaBuilderDB2(DataSource dataSource) {
		super(dataSource);
	}
	@Override
	protected void buildTableNames(List<TableMeta> ret) throws SQLException {
		ResultSet rs = getTablesResultSet();
		while (rs.next()) {
			String tableName = rs.getString("TABLE_NAME");
			
			if (excludedTables.contains(tableName)) {
				System.out.println("Skip table :" + tableName);
				continue ;
			}
			if (isSkipTable(tableName)) {
				System.out.println("Skip table :" + tableName);
				continue ;
			}
			
			// jfinal 4.3 新增过滤 table 机制
			/*			if (filterPredicate != null && filterPredicate.test(tableName)) {
							System.out.println("Skip table :" + tableName);
							continue ;
						}*/
			
			//拿出表的元数据所有字段值看看，不同数据库存储的不一样
			Map<String, Object> map = new HashMap<String, Object>();
			ResultSetMetaData meta = rs.getMetaData();
			int colNum = meta.getColumnCount();
			for (int i = 1; i <= colNum; i++) {
				String name = meta.getColumnLabel(i); // i+1
				Object value = rs.getObject(i);
				map.put(name, value);
			}
			
			TableMeta tableMeta = new TableMeta();
			//db2可以获取到
			String schemas = rs.getString("TABLE_SCHEM");
			if (schemas==null||schemas.equals("")) {
				//MYSQL获取的字段不一样
				schemas = rs.getString("TABLE_CAT");
			}
			tableMeta.name = schemas+"."+tableName;
			tableMeta.remarks = rs.getString("REMARKS");
			
			tableMeta.modelName = buildModelName(tableName);
			tableMeta.baseModelName = buildBaseModelName(tableMeta.modelName);
			ret.add(tableMeta);
		}
		rs.close();
	}
	@Override
	protected void buildPrimaryKey(TableMeta tableMeta) throws SQLException {
		ResultSet rs =null;
		if(tableMeta.name.contains(".")){
			rs=dbMeta.getPrimaryKeys(conn.getCatalog(), tableMeta.name.split("\\.")[0], tableMeta.name.split("\\.")[1]);
		}
		else{
			rs=dbMeta.getPrimaryKeys(conn.getCatalog(), null, tableMeta.name);
		}
		
		
		String primaryKey = "";
		int index = 0;
		while (rs.next()) {
			String cn = rs.getString("COLUMN_NAME");
			
			// 避免 oracle 驱动的 bug 生成重复主键，如：ID,ID
			if (primaryKey.equals(cn)) {
				continue ;
			}
			
			if (index++ > 0) {
				primaryKey += ",";
			}
			primaryKey += cn;
		}
		
		// 无主键的 table 将在后续的 removeNoPrimaryKeyTable() 中被移除，不再抛出异常
		// if (StrKit.isBlank(primaryKey)) {
			// throw new RuntimeException("primaryKey of table \"" + tableMeta.name + "\" required by active record pattern");
		// }
		
		tableMeta.primaryKey = primaryKey;
		rs.close();
	}
	
	
	/**
	 * 文档参考：
	 * http://dev.mysql.com/doc/connector-j/en/connector-j-reference-type-conversions.html
	 * 
	 * JDBC 与时间有关类型转换规则，mysql 类型到 java 类型如下对应关系：
	 * DATE				java.sql.Date
	 * DATETIME			java.sql.Timestamp
	 * TIMESTAMP[(M)]	java.sql.Timestamp
	 * TIME				java.sql.Time
	 * 
	 * 对数据库的 DATE、DATETIME、TIMESTAMP、TIME 四种类型注入 new java.util.Date()对象保存到库以后可以达到“秒精度”
	 * 为了便捷性，getter、setter 方法中对上述四种字段类型采用 java.util.Date，可通过定制 TypeMapping 改变此映射规则
	 */
	protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
		String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		
		
		Map<String, ColumnMeta> columnMetaMap = new HashMap<String, ColumnMeta>();
		
		if (generateRemarks) {
			ResultSet colMetaRs = null;
			try {
				if(tableMeta.name.contains(".")){
					colMetaRs = dbMeta.getColumns(conn.getCatalog(), tableMeta.name.split("\\.")[0], tableMeta.name.split("\\.")[1], null);
				}
				else{
					colMetaRs = dbMeta.getColumns(conn.getCatalog(), null, tableMeta.name, null);
				}
				while (colMetaRs.next()) {
					ColumnMeta columnMeta = new ColumnMeta();
					columnMeta.name = colMetaRs.getString("COLUMN_NAME");
					columnMeta.remarks = colMetaRs.getString("REMARKS");
					columnMetaMap.put(columnMeta.name, columnMeta);
				}
			} catch (Exception e) {
				System.out.println("无法生成 REMARKS");
			} finally {
				if (colMetaRs != null) {
					colMetaRs.close();
				}
			}
		}
		
		
		for (int i=1; i<=columnCount; i++) {
			ColumnMeta cm = new ColumnMeta();
			cm.name = rsmd.getColumnName(i).toLowerCase();//lxx:字段转为小写(否则会导致从实体里get字段获取不要值，查数据时数据库返回的是小写字段，这里生成的实体时候读出来的字段元数据是大写字段);;
			String typeStr = null;
			if (dialect.isKeepByteAndShort()) {
				int type = rsmd.getColumnType(i);
				if (type == Types.TINYINT) {
					typeStr = "java.lang.Byte";
				} else if (type == Types.SMALLINT) {
					typeStr = "java.lang.Short";
				}
			}
			
			if (typeStr == null) {
				String colClassName = rsmd.getColumnClassName(i);
				typeStr = typeMapping.getType(colClassName);
			}
			
			if (typeStr == null) {
				int type = rsmd.getColumnType(i);
				if (type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY || type == Types.BLOB) {
					typeStr = "byte[]";
				} else if (type == Types.CLOB || type == Types.NCLOB) {
					typeStr = "java.lang.String";
				}
				// 支持 oracle 的 TIMESTAMP、DATE 字段类型，其中 Types.DATE 值并不会出现
				// 保留对 Types.DATE 的判断，一是为了逻辑上的正确性、完备性，二是其它类型的数据库可能用得着
				else if (type == Types.TIMESTAMP || type == Types.DATE) {
					typeStr = "java.util.Date";
				}
				// 支持 PostgreSql 的 jsonb json
				else if (type == Types.OTHER) {
					typeStr = "java.lang.Object";
				} else {
					typeStr = "java.lang.String";
				}
			}
			
//			typeStr = handleJavaType(typeStr, rsmd, i);
			
			cm.javaType = typeStr;
			
			// 构造字段对应的属性名 attrName
			cm.attrName = buildAttrName(cm.name);
			
			// 备注字段赋值
			//lxx:这里要用大写字段名去匹配，否则匹配不对应字段的备注
			if (generateRemarks && columnMetaMap.containsKey(cm.name.toUpperCase())) {
				cm.remarks = columnMetaMap.get(cm.name.toUpperCase()).remarks;
			}else if(columnMetaMap.containsKey(cm.name.toLowerCase())) {
				cm.remarks = columnMetaMap.get(cm.name.toLowerCase()).remarks;
			}
			
			tableMeta.columnMetas.add(cm);
		}
		
		rs.close();
		stm.close();
	}
	
	
	protected void buildColumnMetas2(TableMeta tableMeta) throws SQLException {
		String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
		
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		for (int i=1; i<=rsmd.getColumnCount(); i++) {
			ColumnMeta cm = new ColumnMeta();
			cm.name = rsmd.getColumnName(i).toLowerCase();//字段转为小写
			cm.remarks=rsmd.getColumnLabel(i);
			String typeStr = null;
			if (dialect.isKeepByteAndShort()) {
				int type = rsmd.getColumnType(i);
				if (type == Types.TINYINT) {
					typeStr = "java.lang.Byte";
				} else if (type == Types.SMALLINT) {
					typeStr = "java.lang.Short";
				}
			}
			
			if (typeStr == null) {
				String colClassName = rsmd.getColumnClassName(i);
				typeStr = typeMapping.getType(colClassName);
			}
			
			if (typeStr == null) {
				int type = rsmd.getColumnType(i);
				if (type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY || type == Types.BLOB) {
					typeStr = "byte[]";
				} else if (type == Types.CLOB || type == Types.NCLOB) {
					typeStr = "java.lang.String";
				}
				// 支持 oracle 的 TIMESTAMP、DATE 字段类型，其中 Types.DATE 值并不会出现
				// 保留对 Types.DATE 的判断，一是为了逻辑上的正确性、完备性，二是其它类型的数据库可能用得着
				else if (type == Types.TIMESTAMP || type == Types.DATE) {
					typeStr = "java.util.Date";
				}
				// 支持 PostgreSql 的 jsonb json
				else if (type == Types.OTHER) {
					typeStr = "java.lang.Object";
				} else {
					typeStr = "java.lang.String";
				}
			}
			cm.javaType = typeStr;
			
			// 构造字段对应的属性名 attrName
			cm.attrName = buildAttrName(cm.name);
			
			tableMeta.columnMetas.add(cm);
		}
		
		rs.close();
		stm.close();
	}

	@Override
	protected String buildAttrName(String colName) {
		if (dialect instanceof AnsiSqlDialect) {
			colName = colName.toLowerCase();
		}
		return StrKit.toCamelCase(colName);
	}

}
