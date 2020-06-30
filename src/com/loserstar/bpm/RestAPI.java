package com.loserstar.bpm;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.kit.PropKit;

import bpm.rest.client.BPMClient;
import bpm.rest.client.BPMClientException;
import bpm.rest.client.BPMClientImpl;
import bpm.rest.client.authentication.AuthenticationTokenHandler;
import bpm.rest.client.authentication.AuthenticationTokenHandlerException;
import bpm.rest.client.authentication.was.WASAuthenticationTokenHandler;

/**
 * @author Once
 * 2018-04-22
 */

public class RestAPI {
	
	public static BPMClient bpmClient;
    
	/**
	 * 初始化BPM RestApi Client
	 * @return
	 */
	public BPMClient init() {
		String hostName = PropKit.get("bpm.hostname");  
		int port = PropKit.getInt("bpm.port");  
		String userName = PropKit.get("bpm.userid");  
		String passWord = PropKit.get("bpm.password");  

		BPMClient client = null;
		try {
			AuthenticationTokenHandler handler = new WASAuthenticationTokenHandler(userName, passWord);
			client = new BPMClientImpl(hostName, port , handler);
		} catch (AuthenticationTokenHandlerException e) {
			e.printStackTrace();
		} catch (BPMClientException e) {
			e.printStackTrace();
		}
		
		return client;
	}

	/**
	 * @param jsonObject 流程参数
	 * @param bpdId 业务流程定义标识(BPD ID)
	 * @param processAppId 流程应用程序标识
	 * @return 流程编号
	 * @throws JSONException
	 * @throws BPMClientException
	 * @throws AuthenticationTokenHandlerException
	 */
	public String runFlow(String bpdId, String processAppId ,JSONObject jsonObject) throws JSONException, BPMClientException, AuthenticationTokenHandlerException{
		if (bpmClient == null) {
			RestAPI restAPI = new RestAPI();
			bpmClient = restAPI.init();
		}
		JSONObject ob = bpmClient.runBPD(bpdId, processAppId, jsonObject);
		JSONObject data = ob.getJSONObject("data");
		return data.getString("piid");
	}
}

