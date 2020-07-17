package com.loserstar.bpm;

import org.json.JSONException;
import org.json.JSONObject;

import com.jfinal.kit.PropKit;
import com.loserstar.ldap.LDAPSearcher;
import com.loserstar.ldap.LDAPUser;

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
	private static LDAPUser user;
	private static AuthenticationTokenHandler handler;
    
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
	
	  public Boolean finishTask(String userId, int taskId, org.json.JSONObject params)
			    throws Exception
			  {
			    Boolean bflag = Boolean.valueOf(false);

			    if ((handler == null) || (!handler.getUserid().equals(userId)))
			    {
			      if ((user == null) || (!user.getUserId().equals(userId))) {
			        user = LDAPSearcher.searchBPMUser(userId);
			      }
			      handler = new WASAuthenticationTokenHandler(user.getUserId(), user.getPassword());
			    }

			    BPMClient bpmClient = new BPMClientImpl(PropKit.get("bpm.hostname"), PropKit.getInt("bpm.port"), handler);
			    bpmClient.assignTask(taskId);
			    org.json.JSONObject ob = bpmClient.finishTask(taskId, params);

			    int status = ob.getInt("status");
			    org.json.JSONObject data = ob.getJSONObject("data");

			    if ((status == 200) && (data != null)) {
			      bflag = Boolean.valueOf(true);
			    }
			    else {
			      throw new IllegalArgumentException(ob.toString());
			    }
			    return bflag;
			  }
}

