/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.controller;

import com.loserstar.config.annotation.Controller;

/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * remarks:登录（这个不需要登录，登录页面）
 */
@Controller(controllerKey= {"/login"})
public class LoginController extends BaseController {

	/**
	 * 登录页面
	 */
	public void loginPage() {
		renderFreeMarker("/login.ftl");
	}
	/**
	 * 登录
	 */
	public void login() {
		String userid = getPara("userid");
		String password = getPara("password");
		try {
			if (userid==null||userid.equals("")) {
				throw new Exception("没有传入userid");
			}
			if (password==null||password.equals("")) {
				throw new Exception("没有传入password");
			}
			if (!password.equals("admin")) {
				throw new Exception("登录失败：密码错误！密码是admin!");
			}
			setSessionAttr("userid", userid);
			redirect("/index/index.do");//登录成功，重定向到首页
		} catch (Exception e) {
			e.printStackTrace();
			setAttr("error", e.getMessage());
			renderFreeMarker("/login.ftl");//登录失败，重定向到登录页
//			redirect("/login/loginPage.do");//登录失败，重定向到登录页
		}
	}
	
	/**
	 * 注销
	 */
	public void loginOut() {
		removeSessionAttr("userid");
		getSession().invalidate();
		String userid = getSessionAttr("userid");
		if (userid==null||userid.equals("")) {
			redirect("/login/loginPage.do");//重定向到登录页
		}else {
			renderText("seesion清除失败");
		}
	}
}
