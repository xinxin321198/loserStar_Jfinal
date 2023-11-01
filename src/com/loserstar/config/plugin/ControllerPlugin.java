package com.loserstar.config.plugin;

import java.util.Arrays;
import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.loserstar.config.annotation.Controller;
import com.loserstar.config.tools.ToolClassSearcher;
import com.loserstar.controller.BaseController;
import com.loserstar.utils.string.LoserStarStringUtils;

/**
 * 扫描Controller上的注解，绑定Controller和controllerKey
 */
public class ControllerPlugin implements IPlugin {

    protected final Log log = Log.getLog(getClass());
    
    private Routes me;

	public ControllerPlugin(Routes me){
		this.me = me;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean start() {
		// 查询所有继承BaseController的类
		List<String> jars = Arrays.asList(LoserStarStringUtils.toString(PropKit.get("config.scan.jar")).split(","));
		List<Class<? extends BaseController>> controllerClasses = null;
		if(jars.size() > 0){
			controllerClasses = ToolClassSearcher.of(BaseController.class).includeAllJarsInLib(ToolClassSearcher.isValiJar()).injars(jars).search();// 可以指定查找jar包，jar名称固定，避免扫描所有文件
		}else{
			controllerClasses = ToolClassSearcher.of(BaseController.class).search();
		}
		
		// 循环处理自动注册映射
		for (Class controller : controllerClasses) {
			// 获取注解对象
			Controller controllerBind = (Controller) controller.getAnnotation(Controller.class);
			if (controllerBind == null) {
				log.error(controller.getName() + "继承了BaseController，但是没有注解绑定映射路径");
				continue;
			}

			// 获取映射路径数组
			String[] controllerKeys = controllerBind.controllerKey();
			for (String controllerKey : controllerKeys) {
				controllerKey = controllerKey.trim();
				if(controllerKey.equals("")){
					log.error(controller.getName() + "注解错误，映射路径为空");
					continue;
				}
				// 注册映射
				me.add(controllerKey, controller);
				log.debug("Controller注册： controller = " + controller + ", controllerKey = " + controllerKey);
			}
		}
		return true;
	}

	public boolean stop() {
		return true;
	}

}
