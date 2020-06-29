package com.loserstar.sap.jco;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class CallRFCProvider implements DestinationDataProvider {
	private Map<String, Properties> providers = new HashMap<String, Properties>();

	@Override
	public Properties getDestinationProperties(String destName) {
		if (destName == null)
            throw new NullPointerException("请指定目的名称");
        if (providers.size() == 0)
            throw new IllegalStateException("请加入一个目的连接参数属性给提供者");
        return providers.get(destName);
	}

	@Override
	public void setDestinationDataEventListener(
			DestinationDataEventListener arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsEvents() {
		return false;
	}
	
	public void addDestinationProperties(String destName, Properties provider) {
        providers.put(destName, provider);
    }
}
