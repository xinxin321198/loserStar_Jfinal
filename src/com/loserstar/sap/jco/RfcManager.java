package com.loserstar.sap.jco;

import java.io.File;
import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.Environment;

public class RfcManager {
	//14生产机连接要是出问题了换一换这个配置
//	private static final String ABAP_AS_POOLED = "ABAP_AS_POOL";
//	private static final String ABAP_AS_POOLED = "ERP_Dest_em";
	private static final String ABAP_AS_POOLED = "purch_adv";
	
	private static JCOProvider provider;
	private static JCoDestination destination;

	static {
		Properties properties = loadProperties();
		// catch IllegalStateException if an instance is already registered
		try {
		File file = new File(ABAP_AS_POOLED+".jcoDestination");
	
		if (!file.exists()) {		
			//System.out.println(logonProperties.stringPropertyNames());
			FileOutputStream stream = new FileOutputStream(file, false);
			properties.store(stream, "SAP connection properties");
			stream.close();
			
		}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			provider = new JCOProvider();
			Environment.registerDestinationDataProvider(provider);
			provider.changePropertiesForABAP_AS(ABAP_AS_POOLED, properties);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
	}

	public static Properties loadProperties() {
		OrderedProperties logonProperties = null;
		try {
			String serverName = Inet4Address.getLocalHost().getHostName();
//			serverName = "c1ep1vm14.hongta.com";
			if (serverName.equalsIgnoreCase("c1ep1vm14.hongta.com")) {
				logonProperties = OrderedProperties.load("/SAPConnectionPool.properties");
			} else if (serverName.equalsIgnoreCase("c1ep1vm23.hongta.com")) {
				logonProperties = OrderedProperties.load("/SAPConnectionPool201.properties");
			}
			else{
				logonProperties = OrderedProperties.load("/SAPConnectionPool201.properties");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logonProperties;
	}

	public static JCoDestination getDestination() throws JCoException {
		if (destination == null) {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		}
		return destination;
	}

	public static void execute(JCoFunction function) {
		try {
			function.execute(getDestination());
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	public static JCoFunction getFunction(String functionName) {
		JCoFunction function = null;
		try {
			function = getDestination().getRepository().getFunctionTemplate(functionName).getFunction();
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return function;
	}
}
