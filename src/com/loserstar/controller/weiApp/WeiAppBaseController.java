package com.loserstar.controller.weiApp;

import java.util.Date;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.controller.BaseController;

/**
 * 微信小程序的后端服务器接口，controller基类，继承此类的controller请求都需携带token，除非配置文件中配置过可以跳过
 * author: loserStar
 * date: 2023年6月28日上午10:45:47
 * remarks:
 */
public class WeiAppBaseController extends BaseController {
	/**
	 * 获取token
	 * @return
	 */
	protected String getToken() {
		return getRequest().getHeader("token");
	}
	
	/**
	 * 获取会议批次号
	 * @return
	 */
	protected String getBatchId() {
		return getRequest().getHeader("batch_id");
	}
	
	/**
	 * 获取当前登录的微信用户
	 * @return
	 * @throws Exception 
	 */
	protected String getCurrentWeUser() throws Exception{
		String token = getToken();
		return token;
	}
	
	protected <T extends Model<T>> void setCreateUser(T t) {
		t.set("CREATE_TIME", new Date());
		t.set("CREATE_USER_CODE", getToken());
	}
	protected  void setCreateUser(Record record) {
		record.set("CREATE_TIME", new Date());
		record.set("CREATE_USER_CODE", getToken());
	}
	
	protected <T extends Model<T>> void setUpdateUser(T t) {
		t.set("UPDATE_TIME", new Date());
		t.set("UPDATE_USER_CODE", getToken());
	}
	
	protected void setUpdateUser(Record record) {
		record.set("UPDATE_TIME", new Date());
	}
}
