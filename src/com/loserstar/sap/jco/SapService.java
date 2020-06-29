package com.loserstar.sap.jco;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;
import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class SapService {
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
	 * 调用RFC，获取数据
	 * @param rfcName
	 * @param importParameter
	 * @return
	 */
	public List<SapVo> get(String rfcName,Map<String,Object> importParameter){
		List<SapVo> sapVoList = new ArrayList<SapService.SapVo>();
        try {
            // 获取function
            JCoFunction function = RfcManager.getFunction(rfcName);
            // 获取参数并且设置参数的值
			JCoParameterList importParameterList = function.getImportParameterList();
			if (importParameter!=null) {
				for(Map.Entry<String, Object> entry : importParameter.entrySet()) {
					importParameterList.setValue(entry.getKey(), entry.getValue());
				}
			}
            // 开启一个事务
            JCoContext.begin(RfcManager.getDestination());
            // 开始执行function
            RfcManager.execute(function);
            // 获取Table参数列表
            JCoParameterList tableParameterList = function
                    .getTableParameterList();
            for (JCoField jCoField : tableParameterList) {
            	JCoTable table = jCoField.getTable();
            	List<Record> list = new ArrayList<Record>();
            	for (int i = 0; i < table.getNumRows(); i++) {
            		table.setRow(i);
            		Record record = new Record();
            		JCoFieldIterator iterator = table.getRecordFieldIterator();
					//遍历每张表的列数
					while (iterator.hasNextField()) {
						JCoField field = iterator.nextField();
						String filedName = field.getName();
						record.set(filedName, table.getValue(filedName));
					}
            		list.add(record);
            	}
            	SapVo sapVo = new SapVo();
            	sapVo.setTableName(jCoField.getName());
            	sapVo.setList(list);
            	sapVoList.add(sapVo);
			}
            try {
                // 远程调用结束后关闭
                JCoContext.end(RfcManager.getDestination());
            } catch (JCoException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sapVoList;
	}

	/**
	 * 调用RFC，保存数据
	 * @param rfcName
	 * @param sapVoList
	 * @throws Exception
	 */
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
