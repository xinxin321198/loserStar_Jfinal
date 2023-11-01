package com.loserstar.config.interceptor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.PropKit;
import com.loserstar.constants.DsConstans;
import com.loserstar.controller.BaseController;
import com.loserstar.controller.pc.PcBaseController;
import com.loserstar.controller.weiApp.WeiAppBaseController;
import com.loserstar.dao.SysUserDao;
import com.loserstar.entity.SysUser;

/**
 * 参数封装拦截器
 */
public class ParamPkgInterceptor implements Interceptor {
	
	private static Logger log = Logger.getLogger(ParamPkgInterceptor.class);
	private SysUserDao sysUserDao = new SysUserDao(DsConstans.dataSourceName.local);
	@Override
	public void intercept(Invocation ai) {
		BaseController controller = (BaseController) ai.getController();
		String action = ai.getControllerKey();
		String methodName = ai.getMethodName(); 
		Object userid = controller.getRequest().getSession().getAttribute("userid");
		String token = controller.getRequest().getHeader("token");
		//小程序端需要跳过登录的请求
		String[] weiSkipAppActionArray = PropKit.get("interceptor.weiSkipAppActionArray").split(",");
		//pc端需要跳过登录验证的请求
		String[] pcSkipActionArray = PropKit.get("interceptor.pcSkipActionArray").split(",");
		
		boolean isRun = true;//是否可以继续请求
		if (controller instanceof WeiAppBaseController) {
			//说明是小程序的请求
			if (Arrays.asList(weiSkipAppActionArray).contains(action)) {
				//跳过校验
			}else {
				//如果请求是不需要跳过验证的，需要从请求头获取token
				if (token==null||token.equals("")) {
					isRun = false;
					controller.renderText("不允许的请求");
				}else {
					//token对应不上（客户端修改过token或者数据库修改过token，或者其它方式把token清理了让token过期，总之因为客户端和数据库的token对应不上）
					controller.renderError(401);
				}
			}
		}else if(controller instanceof PcBaseController) {
			//说明是pc端的请求
			if (Arrays.asList(pcSkipActionArray).contains(action)) {
				//调过校验
			}else {
				//如果请求是不需要跳过验证的，需要校验seesion中是否存在userid
				if (userid==null) {
					isRun = false;
					//未登录，重定向到登录页
					controller.redirect("/login/loginPage.do");
				}else {
					SysUser sysUser = sysUserDao.getById(userid, SysUser.class);
					controller.setAttr("user", sysUser);
				}
			}
		}
		
 		if(isRun) {
 			Class<?> controllerClass = controller.getClass();
 			Class<?> superControllerClass = controllerClass.getSuperclass();
 			
 			Field[] fields = controllerClass.getDeclaredFields();
 			Field[] parentFields = superControllerClass.getDeclaredFields();
 			log.debug("*********************** 封装参数值到 controller 全局变量  start ***********************");
 			
 			// 封装controller变量值
 			for (Field field : fields) {
 				setControllerFieldValue(controller, field);
 			}
 			
 			// 封装baseController变量值
 			for (Field field : parentFields) {
 				setControllerFieldValue(controller, field);
 			}
 			log.debug("*********************** 封装参数值到 controller 全局变量  end ***********************");
 			
 			ai.invoke();
 			
 			log.debug("*********************** 设置全局变量值到 request start ***********************");
 			// 封装controller变量值
 			for (Field field : fields) {
 				setRequestValue(controller, field);
 			}
 			
 			// 封装baseController变量值
 			for (Field field : parentFields) {
 				setRequestValue(controller, field);
 			}
 			log.debug("*********************** 设置全局变量值到 request end ***********************");
 			
 			if(controller instanceof PcBaseController) {
 				
 				controller.setAttr("actionKey", ai.getActionKey()+".do");
 			}
 		}else {
//			controller.renderText("不允许的请求");
		}
		
		
	}
	

	
	/**
	 * 反射set值到全局变量
	 * @param controller
	 * @param field
	 */
	 public void setControllerFieldValue(BaseController controller, Field field){
		  try {
		   field.setAccessible(true);
		   String name = field.getName();
		   String value = controller.getPara(name);
		   
		   if("userid".equals(name)){
			   if (controller.getRequest().getHeader("iv-user") != null) {
					String userid = controller.getRequest().getHeader("iv-user");
					 field.set(controller, userid);
					 log.debug("iv-user中获取到了userid的值");
					 field.setAccessible(false);
					 return;
				}else if(controller.getSessionAttr("userid")!=null){
					String userid = controller.getSessionAttr("userid").toString();
					 field.set(controller, userid);
					 log.debug("sesson中获取到了userid的值");
					 field.setAccessible(false);
					 return;
				}
		   }
		  
		   if(null == value || value.isEmpty()){// 参数值为空则从Sesson中尝试获取直接结束
		    log.debug("参数值为空");
		    	if(controller.getSessionAttr(name)!=null){
		    		//值类型转换为全局变量的类型
		    		Object sValue=getSessonValue(controller.getSessionAttr(name),field.getType().getSimpleName());
		    		if(sValue!=null){ 
		    			field.set(controller, sValue);
		    			log.debug("sesson中获取到了"+name+"的值");
		    			field.setAccessible(false);
		    		}
		    	}
		    return;
		   }
		   
		   String fieldType = field.getType().getSimpleName();
		   if(fieldType.equals("String")){
		    field.set(controller, value);
		    
		   }else if(fieldType.equals("int")){
		    field.set(controller, Integer.parseInt(value));
		     
		   }else if(fieldType.equals("Date")){
		    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		    field.set(controller, format.parse(value));
		    
		   }else if(fieldType.equals("BigDecimal")){
		    BigDecimal bdValue = new BigDecimal(value);
		    field.set(controller, bdValue);
		     
		   }else{
		    field.setAccessible(false);
		    log.debug("没有解析到有效字段类型");
		   }
		    
		   field.setAccessible(false);
		  } catch (IllegalArgumentException e1) {
		   e1.printStackTrace();
		  } catch (IllegalAccessException e1) {
		   e1.printStackTrace();
		  } catch (ParseException e) {
		   e.printStackTrace();
		  }
		 } 

	/**
	 * 反射全局变量值到request
	 * @param controller
	 * @param field
	 */
	public void setRequestValue(BaseController controller, Field field){
		try {
			field.setAccessible(true);
			String name = field.getName();
			Object value = field.get(controller);
			if(null == value || 
					(value instanceof String && ((String)value).isEmpty())
					){// 参数值为空直接结束
				log.debug("设置全局变量到request：field name = " + name + " value = 空");
				return;
			}
			log.debug("设置全局变量到request：field name = " + name + " value = " + value);
			controller.setAttr(name, value);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} finally {
			field.setAccessible(false);
		}
	}
	
	/**
	 * sesson值类型向全局变量类型转换
	 * @param svalue
	 * @param needtype
	 * @return
	 */
	private Object getSessonValue(Object sessonValue,String needtype){
		if(needtype.equals(sessonValue.getClass().getSimpleName())){
			return sessonValue;
		}else{
			String value=sessonValue.toString();
			if("String".equals(needtype)){
				return value;
			}if("Integer".equals(needtype)||"int".equals(needtype)){
				Double db=Double.parseDouble(value);
				return db.intValue();
			}else if("Double".equals(needtype)){
				return Double.parseDouble(value);
			}else{
				return null;
			}
		}
		
	}
}
