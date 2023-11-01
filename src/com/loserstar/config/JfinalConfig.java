package com.loserstar.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.RenderFactory;
import com.jfinal.template.Engine;
import com.loserstar.config.handler.GlobalHandler;
import com.loserstar.config.interceptor.ParamPkgInterceptor;
import com.loserstar.config.plugin.ControllerPlugin;
import com.loserstar.constants.DsConstans;
import com.loserstar.entity._MappingKit;
import com.loserstar.utils.system.LoserStarSystemUtil;

public class JfinalConfig extends JFinalConfig {
	public static final String propertiesFileNameString_test = "init-cs.properties";//加载的配置文件
	public static final String propertiesFileNameString_product = "init-cs.properties";//加载的配置文件
	
	
/*	private static Properties properties;//原生方式拿到的配置全局缓存
	public static Properties getProperties() {
		return properties;
	}*/
	
	@Override
	public void configConstant(Constants me) {
		//看情况是否使用判断服务器名称加载配置文件的方法
		/*		String serverName = "";
				try {
					serverName = Inet4Address.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				//jfinal的方法获取配置文件
				if (serverName.equalsIgnoreCase("c1ep1vm14.hongta.com")||serverName.equalsIgnoreCase("HTWXQYH")) {
					prop = PropKit.use(propertiesFileNameString_product);
				}else {
					prop = PropKit.use(propertiesFileNameString_test);
				}*/
		
		//手动直接指定加载哪个配置文件
		prop = PropKit.use(propertiesFileNameString_test);
		String jfinal_jdbc_url = prop.get("local.jdbcUrl");
		System.out.println("jfinal工具加载配置："+jfinal_jdbc_url);
		
		
		//原生的方式，通过class路径获取配置文件,这鬼方式在was上好像是有问题的，不用了
/*		properties = LoserStarPropertiesUtil.getProperties(JFinalConfig.class.getResource("/").getPath()+propertiesFileNameString);
		System.out.println("原生的方式获取配置：");
		LoserStarPropertiesUtil.printPropertiesInfo(properties);//打印配置信息
*/		System.out.println("系统信息");
		LoserStarSystemUtil.printSystemInfo();
		
		// log.info("configConstant 设置字符集");
		me.setEncoding("UTF-8");

		// log.info("configConstant 设置是否开发模式");
		me.setDevMode(true);

		// log.info("configConstant 视图error page设置");
		me.setError401View("/common/401.html");
		me.setError403View("/common/403.html");
		me.setError404View("/common/404.html");
		me.setError500View("/common/500.html");

		RenderFactory renderFactory = new RenderFactory();
		me.setRenderFactory(renderFactory);
//		me.setViewType(ViewType.FREE_MARKER);
		//设置上传文件大小200 M=209715200 b
//		me.setMaxPostSize(209715200);
		//1G = 1000000000Byte
		me.setMaxPostSize(1000000000);
		
		
		//fastjson全局配置，是否取消循环引用的检测,如果取消该配置，则代码上要注意不能循环引用，否则会造成内存溢出
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
	}

	@Override
	public void configHandler(Handlers me) {
		// me.add(new ContextPathHandler("base_path"));
		// log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
		me.add(new GlobalHandler());
		me.add(new UrlSkipHandler(".*/services.*",false));//用于过滤掉webservice，否则生成服务端都会报错
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// log.info("configInterceptor 权限认证拦截器");
		me.add(new ParamPkgInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("local.jdbcUrl"),
				PropKit.get("local.userName"),
				PropKit.get("local.passWord"));
		druidPlugin.setDriverClass(PropKit.get("local.driverClass"));
		druidPlugin.set(1, 1, 2);
		me.add(druidPlugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(DsConstans.dataSourceName.local,druidPlugin);
		me.add(arp);
		arp.setDialect(new AnsiSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));//ture是小写，false大写
		_MappingKit.mapping(arp);
	}

	@Override
	public void configRoute(Routes me) {
		//路由扫描注册
		new ControllerPlugin(me).start();
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
		
	}
}