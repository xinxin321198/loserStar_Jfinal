package com.loserstar.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysUsers<M extends BaseSysUsers<M>> extends Model<M> implements IBean {

	/**
	 * 主键id
	 */
	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 主键id
	 */
	public java.lang.String getId() {
		return getStr("id");
	}

	/**
	 * 用户名
	 */
	public M setUserName(java.lang.String userName) {
		set("user_name", userName);
		return (M)this;
	}
	
	/**
	 * 用户名
	 */
	public java.lang.String getUserName() {
		return getStr("user_name");
	}

	/**
	 * 密码
	 */
	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}
	
	/**
	 * 密码
	 */
	public java.lang.String getPassword() {
		return getStr("password");
	}

	public M setDel(java.lang.String del) {
		set("del", del);
		return (M)this;
	}
	
	public java.lang.String getDel() {
		return getStr("del");
	}

}
