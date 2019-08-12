package com.loserstar.config;

import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.Prop;
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
import com.loserstar.utils.proerties.LoserStarPropertiesUtil;
import com.loserstar.utils.system.LoserStarSystemUtil;

public class JfinalConfig extends JFinalConfig {
	final String propertiesFileNameString = "init-cs.properties";//加载的配置文件
	
	
	private static Properties properties;//原生方式拿到的配置全局缓存
	public static Properties getProperties() {
		return properties;
	}
	
	@Override
	public void configConstant(Constants me) {
		//jfinal的方法获取配置文件
		Prop prop =  PropKit.use(propertiesFileNameString);
		String jfinal_jdbc_url_test = prop.get("ht313db.jdbcUrl");
		System.out.println("jfinal工具加载配置："+jfinal_jdbc_url_test);
		//原生的方式，通过class路径获取配置文件
		properties = LoserStarPropertiesUtil.getProperties(JFinalConfig.class.getResource("/").getPath()+propertiesFileNameString);
		System.out.println("原生的方式获取配置：");
		LoserStarPropertiesUtil.printPropertiesInfo(properties);//打印配置信息
		System.out.println("系统信息");
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
		me.setMaxPostSize(209715200);
	}

	@Override
	public void configHandler(Handlers me) {
		// me.add(new ContextPathHandler("base_path"));
		// log.info("configHandler 全局配置处理器，主要是记录日志和request域值处理");
		me.add(new GlobalHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// log.info("configInterceptor 权限认证拦截器");
		me.add(new ParamPkgInterceptor());
	}

	@Override
	public void configPlugin(Plugins me) {
		
		DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("ht313db.jdbcUrl"),
				PropKit.get("ht313db.userName"),
				PropKit.get("ht313db.passWord"));
		druidPlugin.setDriverClass(PropKit.get("ht313db.driverClass"));
		druidPlugin.set(1, 1, 2);
		me.add(druidPlugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		me.add(arp);
		arp.setDialect(new AnsiSqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
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