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

import javax.sql.DataSource;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.activerecord.generator.ColumnMeta;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;

/**
 * author: loserStar
 * date: 2020年3月26日上午11:51:23
 * remarks:这个是为了适应DB2生成实体而创建的。因为DB2生成的实体，set时候都是大写，很恶心，扩展一下代码生成器，让其生成的实体里的属性都是小写，便于操作
 */
public class LoserStarMetaBuilderDB2 extends MetaBuilder {

	/**
	 * @param dataSource
	 */
	public LoserStarMetaBuilderDB2(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	protected void buildColumnMetas(TableMeta tableMeta) throws SQLException {
		String sql = dialect.forTableBuilderDoBuild(tableMeta.name);
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		
		for (int i=1; i<=rsmd.getColumnCount(); i++) {
			ColumnMeta cm = new ColumnMeta();
			cm.name = rsmd.getColumnName(i).toLowerCase();//字段转为小写
			
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
