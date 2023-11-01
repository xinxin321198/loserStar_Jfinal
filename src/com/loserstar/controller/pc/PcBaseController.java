package com.loserstar.controller.pc;

import java.util.Date;
import java.util.Map.Entry;
import java.util.Set;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.controller.BaseController;

/**
 * pc端基类，继承此类的controller的请求，都需先登录，即seesion中存在userid的值
 * author: loserStar
 * date: 2023年6月28日上午10:48:10
 * remarks:
 */
public class PcBaseController extends BaseController {
	
	
	/**
	 * 剔除值为null或空的key，便于保存不报错
	 * @param record
	 * @return
	 */
	public static void removeIsNullKey(Model<?> model) {
		Set<Entry<String, Object>> set = model._getAttrsEntrySet();
		for (Entry<String, Object> entry : set) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			if (entry.getValue()==null||entry.getValue().equals("")||entry.getValue().equals("null")) {
				model.remove(entry.getKey());
			}
		}
	}
	
	/**
	 * 获取userId
	 * 
	 * @return
	 */
	protected String getUserId() {
		String userid = getPara("userid");
		if (getRequest().getHeader("iv-user") != null) {
			userid = getRequest().getHeader("iv-user");
		} else {
			userid = (String) getRequest().getSession().getAttribute("userid");
		}
		return userid;
	}
	
	/**
	 * 把用户数据设置到全局
	 */
//	public void setCurrentUser() {
//		try {
//			setAttr("currentUser",getBaseUser(getUserId()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 获取系统用户
	 * @param userid
	 * @return
	 */
//	public HthyBaseUser getBaseUser(String userid) {
//		return hthyBaseUserDao.getById(userid,HthyBaseUser.class);
//	}
	
	/**
	 * 设置常规基础数据（菜单、用户信息之类的）
	 */
//	public void setCommonData() {
//		List<HthyBatch> batchList = hthyBatchDao.getList(new WhereHelper().addStrOrder("order by sort desc"),HthyBatch.class,null);
//		setAttr("batchList", batchList);
//	}

	protected <T extends Model<T>> void setCreateUser(T t) {
		String userid = getUserId();
		t.set("CREATE_TIME", new Date());
		t.set("CREATE_USER_CODE", userid);
//		t.set("CREATE_USER_NAME", user.getUserName());
	}
	protected  void setCreateUser(Record record) {
		String userid = getUserId();
		record.set("CREATE_TIME", new Date());
		record.set("CREATE_USER_CODE", userid);
//		record.set("CREATE_USER_NAME", user.getUserName());
	}
	
	protected <T extends Model<T>> void setUpdateUser(T t) {
		String userid = getUserId();
		t.set("UPDATE_TIME", new Date());
		t.set("UPDATE_USER_CODE", userid);
//		t.set("UPDATE_USER_NAME", user.getUserName());
	}
	
	protected void setUpdateUser(Record record) {
		String userid = getUserId();
		record.set("UPDATE_TIME", new Date());
		record.set("UPDATE_USER_CODE", userid);
//		record.set("UPDATE_USER_NAME", user.getUserName());
	}
}
