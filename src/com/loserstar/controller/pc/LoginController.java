/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.controller.pc;

import java.io.IOException;

import javax.imageio.ImageIO;

import com.loserstar.config.annotation.Controller;
import com.loserstar.constants.DsConstans;
import com.loserstar.dao.SysUserDao;
import com.loserstar.entity.SysUser;
import com.loserstar.utils.checkCode.LoserStarCheckCodeUtils;

/**
 * author: loserStar
 * date: 2019年8月23日下午3:44:12
 * remarks:登录（这个不需要登录，登录页面）
 */
@Controller(controllerKey= {"/login"})
public class LoginController extends PcBaseController {
	private SysUserDao sysUserDao = new SysUserDao(DsConstans.dataSourceName.local);


	/**
	 * 登录页面
	 */
	public void loginPage() {
		renderFreeMarker("/login.html");
	}
	
	/**
	 * 得到验证码图片
	 */
	public void getLoginCheckCodeImg() {
		try {
			String code = LoserStarCheckCodeUtils.genCheckCode(4);
			ImageIO.write(LoserStarCheckCodeUtils.getImage(code), "png", getResponse().getOutputStream());
			setSessionAttr("loginCheckCode", code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		renderNull();
	}
	
	/**
	 * 登录
	 */
	public void login() {
		int maxPwdErrorCount = 3;
		String userid = getPara("userid");
		String password = getPara("password");
		try {
			if (!checkNull(userid)) {
				throw new Exception("请填写账号");
			}
			if (!checkNull(password)) {
				throw new Exception("请填写密码");
			}
			String check_code = getPara("check_code");
			if (!checkNull(check_code)) {
				throw new Exception("请填写验证码");
			}
			String loginCheckCode = getSessionAttr("loginCheckCode");
			if (!check_code.toUpperCase().equals(loginCheckCode.toUpperCase())) {
				throw new Exception("验证码错误");
			}
			
			SysUser sysUser = sysUserDao.getById(userid, SysUser.class);
			if (sysUser==null) {
				throw new Exception("用户名或密码错误");
			}
			String dbPwd = sysUser.getPassword();//DB查出来的用户密码
			int pwdErrCount = sysUser.getPwdErrCount();//DB查出来的用户密码错误次数
			if (pwdErrCount>=maxPwdErrorCount) {
				throw new Exception("密码输入错误次数超过"+maxPwdErrorCount+"次，该账号已锁定"); 
			}
			if (!password.equals(dbPwd)) {
				pwdErrCount++;//密码错误，错误次数+1，并保存在数据库中
				if (pwdErrCount>=maxPwdErrorCount) {
					throw new Exception("密码输入错误次数超过"+maxPwdErrorCount+"次，该账号已锁定"); 
				}else {
					throw new Exception("密码错误！还剩"+(maxPwdErrorCount-pwdErrCount)+"次机会");
				}
			}
			pwdErrCount=0;//如果登录成功，错误次数清零，并更新数据库
			setSessionAttr("userid", userid);
			redirect("/index/index.do");//登录成功，重定向到首页
		} catch (Exception e) {
			e.printStackTrace();
			setAttr("error", e.getMessage());
			renderFreeMarker("/login.html");//登录失败，重新渲染登录页
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
