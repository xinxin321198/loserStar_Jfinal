package com.loserstar.config.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

/**
 * 全局Handler，设置一些通用功能
 * 描述：主要是一些全局变量的设置，再就是日志记录开始和结束操作
 */
public class GlobalHandler extends Handler {
	

	public static final String reqSysLogKey = "reqSysLog";
	
	@SuppressWarnings("deprecation")
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		//log.info("设置 服务器端.do过滤");
		if (target.endsWith(".do")) {
			target = target.substring(0, target.length() - 3);  
		}
		
		//这里要注意：iv-user是单点使用的。userid获取到值就设到seesion里，这种方式在有独立登录的工程非常危险
		if(StrKit.notBlank(request.getHeader("iv-user"))) {
			request.getSession().setAttribute("userid", request.getHeader("iv-user"));
		}else if(StrKit.notBlank(request.getParameter("userid"))){
//			request.getSession().setAttribute("userid", request.getParameter("userid"));
		}

		//log.info("设置Header");
		request.setAttribute("decorator", "none");
		response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
		response.setHeader("Pragma","no-cache"); //HTTP 1.0
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, HEAD, DELETE, PUT");
		response.setHeader("Access-Control-Allow-Headers",
	                "X-Requested-With, Content-Type, Authorization, Accept, Origin, User-Agent, Content-Range, Content-Disposition, Content-Description");
		response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
		
		nextHandler.handle(target, request, response, isHandled);
		
		//log.info("请求处理完毕，计算耗时");
		
	}
}
