<#setting classic_compatible=true>
package com.kaen.dao;

import com.loserstar.utils.db.jfinal.base.imp.BaseService;

/**
 * author: autoGenerate
 * date: ${.now}
 * remarks:${tableRemarks}
 */
public class ${className} extends BaseService {
	public static final String TABLE_NAME = "${creator}.${tableName}";
	public static final String PRIMARY_KEY = "${primaryKey}";
	public static final String SOFT_DEL_FIELD= "DEL";
	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	protected String getPrimaryKey() {
		// TODO Auto-generated method stub
		return PRIMARY_KEY;
	}

	@Override
	protected String getSoftDelField() {
		// TODO Auto-generated method stub
		return SOFT_DEL_FIELD;
	}

}
