/**
 * author: loserStar
 * date: 2020年3月23日下午5:55:15
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.sms.service;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.UUID;

import com.loserstar.sms.webServiceClientEP.GsmsResponse;
import com.loserstar.sms.webServiceClientEP.MTPacks;
import com.loserstar.sms.webServiceClientEP.MessageData;
import com.loserstar.sms.webServiceClientEP.WebServiceSoapProxy;
import com.loserstar.utils.date.LoserStarDateUtils;

/**
 * author: loserStar
 * date: 2020年3月23日下午5:55:15
 * remarks:红塔集团短信webservice接口
 * wsdl地址：http://10.96.45.32:28888/Service/WebService.asmx?wsdl
 */
public class SmsService {

	/**
	 * 发送短信
	 * @param mobile 手机号
	 * @param content 内容
	 * @return
	 * @throws RemoteException
	 */
	public GsmsResponse sendSms( String mobile, String content) throws RemoteException {
		String serverName = "";
		try {
			serverName = Inet4Address.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}


		MessageData msgData = new MessageData();
		msgData.setContent(content);
		msgData.setPhone(mobile);
		msgData.setVipFlag(true);
		msgData.setCustomMsgID("HtCarManagerEvaluate");
		WebServiceSoapProxy proxy = new WebServiceSoapProxy();
		MTPacks mtpack = new MTPacks();
		mtpack.setBatchID(UUID.randomUUID().toString());
		mtpack.setBatchName("batchName");
		mtpack.setMsgType(1);
		mtpack.setBizType(0);
		mtpack.setDistinctFlag(false);
		mtpack.setSendType(0);
		MessageData[] msgsTmp = new MessageData[] {msgData};
		mtpack.setMsgs(msgsTmp);
		mtpack.setCustomNum("001");
		mtpack.setUuid(UUID.randomUUID().toString());
		GsmsResponse gsmsResponse = null;
//		serverName = "c1ep1vm14.hongta.com";//放出之后会真正发送短信
		if (serverName.equalsIgnoreCase("c1ep1vm14.hongta.com")||serverName.equalsIgnoreCase("HTWXQYH")||serverName.equalsIgnoreCase("HTGBGL")||serverName.equalsIgnoreCase("c1ep1vm26.hongta.com")||serverName.equalsIgnoreCase("c1ep1vm27.hongta.com")||serverName.equalsIgnoreCase("c1ep1vm31.hongta.com")) {
			gsmsResponse = proxy.post("账号", "密码", mtpack);
		}else {
			gsmsResponse = new GsmsResponse(0, UUID.randomUUID().toString(), "当前非生产机，不会真正发送短信，模拟返回数据成功", null);
		}
		return gsmsResponse;
	}
	public static void main(String[] args) {
		try {
			System.out.println(SmsService.class.getResource("/").getPath());
			String sendMobile = "15087010221";
			String sendMsg = "短信接口测试："+SmsService.class.getResource(File.separator).getPath()+" 时间："+LoserStarDateUtils.format(new Date());
			System.out.println("发送手机号："+sendMobile);
			System.err.println("发送内容："+sendMsg);
			GsmsResponse response = new SmsService().sendSms(sendMobile,sendMsg);
			System.out.println(response.getResult());
			System.out.println(response.getMessage());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
