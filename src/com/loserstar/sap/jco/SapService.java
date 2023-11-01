package com.loserstar.sap.jco;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.utils.idgen.SnowflakeIdWorker;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class SapService {

	/**
	 * 调用RFC，获取数据
	 * 
	 * @param rfcName
	 *            rfc名称
	 * @param importParameter
	 *            输入参数，其中特别注意：输入的表参数请使用List < Record >
	 * @return 输出参数，表参数为List < Record >
	 */
	public Record callRfc(String rfcName, Record importParameter) {
		Record ret = new Record();
		Record rfcLog = new Record().set("RFCLG_ID", SnowflakeIdWorker.FakeGuid());
		System.out.println(JsonKit.toJson(importParameter));
		rfcLog.set("IN_PARAM", JsonKit.toJson(importParameter));
		rfcLog.set("STAR_TIME", new Date());
		rfcLog.set("RFC_NAME", rfcName);
		JCoDestination destination=null;
		
		Exception ex = null;
		try {

			// 获取function
			JCoFunction function = RfcManager.getFunction(rfcName);
			// 获取参数并且设置参数的值
			// 设置输入参数 对应rfc导入参数部分
			JCoParameterList importParameterList = function.getImportParameterList();
			if (importParameterList != null) {
				JCoFieldIterator iterator = importParameterList.getFieldIterator();
				while (iterator.hasNextField()) {
					JCoField field = iterator.nextField();
					Object inputValue = importParameter.get(field.getName());
					if (inputValue != null) {
						field.setValue(inputValue);
					}
				}
			}
			// 设置输入参数 对应rfc表参数部分 sap的表参数可导入+导出 需特殊处理
			JCoParameterList tableParameterListIn = function.getTableParameterList();
			if (tableParameterListIn != null) {
				for (JCoField jCoField : tableParameterListIn) {
					JCoTable table = jCoField.getTable();
					List<Record> list = importParameter.get(jCoField.getName());
					if (list != null) {
						for (int i = 0; i < list.size(); i++) {
							Record inTableRow = list.get(i);
							table.appendRow();
							table.setRow(i);
							JCoFieldIterator iterator = table.getRecordFieldIterator();
							// 遍历每张表的列数
							while (iterator.hasNextField()) {
								JCoField field = iterator.nextField();
								String filedName = field.getName();
								Object valueTmp = inTableRow.get(filedName);
								if (valueTmp != null) {
									field.setValue(valueTmp);
								}
							}
						}
					}
				}
			}

			// 开启一个事务
			 destination = RfcManager.getDestination();
			//System.out.println(destination);
			JCoContext.begin(destination);
			// 开始执行function
			RfcManager.execute(function);
			// 获取Table参数列表
			JCoParameterList tableParameterList = function.getTableParameterList();

			// 设置输出参数 对应RFC 表参数部分 sap的表参数可导入+导出 需特殊处理
			if (tableParameterList != null) {
				for (JCoField jCoField : tableParameterList) {
					JCoTable table = jCoField.getTable();
					List<Record> list = new ArrayList<Record>();
					for (int i = 0; i < table.getNumRows(); i++) {
						table.setRow(i);
						Record record = new Record();
						JCoFieldIterator iterator = table.getRecordFieldIterator();
						// 遍历每张表的列数
						while (iterator.hasNextField()) {
							JCoField field = iterator.nextField();
							String filedName = field.getName();
							record.set(filedName, table.getValue(filedName));
						}
						list.add(record);
					}

					ret.set(jCoField.getName(), list);
				}
			}
			JCoParameterList exportParameterList = function.getExportParameterList();
			if (exportParameterList != null) {
				JCoFieldIterator iterator = exportParameterList.getFieldIterator();
				while (iterator.hasNextField()) {
					JCoField field = iterator.nextField();
					String filedName = field.getName();
					ret.set(filedName, field.getValue());
				}
			}
			try {
				// 远程调用结束后关闭
				JCoContext.end(destination);
			} catch (JCoException e1) {
				ex = e1;
				e1.printStackTrace();
			}
		} catch (Exception e) {
			ex = e;
			e.printStackTrace();
		} finally {
			rfcLog.set("OUT_PARAM", JsonKit.toJson(ret));
			rfcLog.set("END_TIME", new Date());
			if(destination!=null){
				rfcLog.set("destination", destination.toString());
			}
			if (ex != null) {
				rfcLog.set("EXCEPTION_DETAIL", ex.toString());
			}
			try {
				Db.save("RFC_LOG", rfcLog);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return ret;
	}

	/**
	 * 调用RFC，获取数据
	 * 
	 * @param rfcName
	 *            rfc名称
	 * @param importParameter
	 *            输入参数，其中特别注意：输入的表参数请使用List < Record >
	 * @return 输出参数，表参数为List < Record >
	 */
	public Record callRfc(String rfcName, Map<String, Object> importParameter) {
		Record rfcInputParam = new Record().setColumns(importParameter);
		return callRfc(rfcName, rfcInputParam);
	}
	
	
	//调用sap的返回值vo
	public static class SapVo{
		private String tableName;
		private List<Record> list;
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public List<Record> getList() {
			return list;
		}
		public void setList(List<Record> list) {
			this.list = list;
		}
		
	}
	/**
	 * 调用RFC，保存数据
	 * @param rfcName
	 * @param sapVoList
	 * @throws Exception
	 */
	@Deprecated
	public List<Record> save(String rfcName,Map<String,Object> importParameter,List<SapVo> sapVoList) throws Exception {
		List<Record> returnRecordList = new ArrayList<Record>();
		JCoDestination destination = RfcManager.getDestination();
		System.out.println( " SapService.save() debug------------------");
		System.out.println( destination.getUser());
		try {
			JCoContext.begin(destination);
			for (SapVo sapVo : sapVoList) {
				JCoFunction function = destination.getRepository().getFunctionTemplate(rfcName).getFunction();
				JCoParameterList tableParameterList = function.getTableParameterList();
				// 获取参数并且设置参数的值
				JCoParameterList importParameterList = function.getImportParameterList();
				if (importParameter!=null) {
					for(Map.Entry<String, Object> entry : importParameter.entrySet()) {
						importParameterList.setValue(entry.getKey(), entry.getValue());
					}
				}
				JCoTable jcotable = tableParameterList.getTable(sapVo.getTableName());
				for (int i = 0; i < sapVo.getList().size(); i++) {
					Record record = sapVo.getList().get(i);
					jcotable.appendRow();
					jcotable.setRow(i);
	
					JCoFieldIterator iterator = jcotable.getRecordFieldIterator();
					while (iterator.hasNextField()) {
						JCoField field = iterator.nextField();
						String filedName = field.getName();
						Object valueTmp = record.get(filedName);
						if (null != valueTmp) {
							jcotable.setValue(filedName, valueTmp);
						}
					}
				}
				function.execute(destination);
				JCoParameterList returnList = function.getExportParameterList();
				Iterator<JCoField>  iterator = returnList.iterator();
				Record returnRecord = new Record();
				while (iterator.hasNext()) {
					JCoField field = iterator.next();
					String fieldName = field.getName();
					Object fielValue = field.getValue();
					returnRecord.set(fieldName, fielValue);
				}
				returnRecordList.add(returnRecord);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (destination != null) {
					// 远程调用结束后关闭
					JCoContext.end(destination);
				}
			} catch (JCoException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return returnRecordList;
	}
}
