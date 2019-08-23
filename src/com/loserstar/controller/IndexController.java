/**
 * author: loserStar
 * date: 2019年8月23日下午3:45:23
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.controller;

import com.loserstar.config.annotation.Controller;

/**
 * author: loserStar
 * date: 2019年8月23日下午3:45:23
 * remarks:这个就要登录了，首页
 */
@Controller(controllerKey= {"/index"})
public class IndexController extends BaseController {

	/**
	 * 首页
	 */
	public void index() {
		setAttr("name", "这是通过index/index.do进入的页面（需要登录才能进入）");
		setAttr("userid", getSessionAttr("userid"));
		renderFreeMarker("/index.ftl");
	}
}
