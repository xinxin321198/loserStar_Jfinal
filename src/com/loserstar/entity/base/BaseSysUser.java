package com.loserstar.entity.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends Model<M> implements IBean {

	/**
	 * 主键id，用作账号名
	 */
	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}
	
	/**
	 * 主键id，用作账号名
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

	/**
	 * 密码输入错误次数
	 */
	public M setPwdErrCount(java.lang.Integer pwdErrCount) {
		set("pwd_err_count", pwdErrCount);
		return (M)this;
	}
	
	/**
	 * 密码输入错误次数
	 */
	public java.lang.Integer getPwdErrCount() {
		return getInt("pwd_err_count");
	}

	/**
	 * 删除标志
	 */
	public M setDel(java.lang.String del) {
		set("del", del);
		return (M)this;
	}
	
	/**
	 * 删除标志
	 */
	public java.lang.String getDel() {
		return getStr("del");
	}

	/**
	 * 创建时间
	 */
	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	/**
	 * 创建时间
	 */
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	/**
	 * 创建人编号
	 */
	public M setCreateUserCode(java.lang.String createUserCode) {
		set("create_user_code", createUserCode);
		return (M)this;
	}
	
	/**
	 * 创建人编号
	 */
	public java.lang.String getCreateUserCode() {
		return getStr("create_user_code");
	}

	/**
	 * 创建人姓名
	 */
	public M setCreateUserName(java.lang.String createUserName) {
		set("create_user_name", createUserName);
		return (M)this;
	}
	
	/**
	 * 创建人姓名
	 */
	public java.lang.String getCreateUserName() {
		return getStr("create_user_name");
	}

	/**
	 * 更新时间
	 */
	public M setUpdateTime(java.util.Date updateTime) {
		set("update_time", updateTime);
		return (M)this;
	}
	
	/**
	 * 更新时间
	 */
	public java.util.Date getUpdateTime() {
		return get("update_time");
	}

	/**
	 * 更新人编号
	 */
	public M setUpdateUserCode(java.lang.String updateUserCode) {
		set("update_user_code", updateUserCode);
		return (M)this;
	}
	
	/**
	 * 更新人编号
	 */
	public java.lang.String getUpdateUserCode() {
		return getStr("update_user_code");
	}

	/**
	 * 更新人姓名
	 */
	public M setUpdateUserName(java.lang.String updateUserName) {
		set("update_user_name", updateUserName);
		return (M)this;
	}
	
	/**
	 * 更新人姓名
	 */
	public java.lang.String getUpdateUserName() {
		return getStr("update_user_name");
	}

}
