/**
 * author: loserStar
 * date: 2019年5月17日下午12:10:27
 * email:362527240@qq.com
 * github:https://github.com/xinxin321198
 * remarks:
 */
package com.loserstar.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.loserstar.config.annotation.Controller;
import com.loserstar.constants.DsConstans;
import com.loserstar.dao.SysDictDao;
import com.loserstar.entity.SysDict;
import com.loserstar.utils.db.jfinal.base.imp.WhereHelper;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.json.LoserStarJsonUtil;

/**
 * author: loserStar
 * date: 2019年5月17日下午12:10:27
 * remarks:此controller不需要登录，用来测试工程是否正常
 */
@Controller(controllerKey= {"/test"})
public class TestController extends BaseController {

	private SysDictDao sysDictDao = new SysDictDao(DsConstans.dataSourceName.local);
	/**
	 * 测试数据库连接
	 */
	public void testDb1() {
		List<Record> list =  Db.find("select * from sys_users");
		renderJson(list);
	}
	
	/**
	 * 测试数据库连接
	 */
	public void testDb2() {
		List<Record> list =  Db.find("select * from "+SysDictDao.TABLE_NAME+"");
		renderJson(list);
	}
	/**
	 * 测试数据库连接
	 */
	public void testDb3() {
		List<Record> list =  sysDictDao.getList(new WhereHelper(),null);
		renderJson(list);
	}
	/**
	 * 测试数据库连接
	 */
	public void testDb4() {
		List<SysDict> list =  SysDict.dao.find("select * from "+SysDict.TABLE_NAME+" where "+sysDictDao.getSoftDelWhere());
		renderJson(list);
	}
	/**
	 * 测试数据库连接
	 */
	public void testDb5() {
		List<SysDict> list =  sysDictDao.getList(new WhereHelper(), SysDict.class,null);
		renderJson(list);
	}
	
	/**
	 * 测试freemarker渲染
	 */
	public void index() {
		setAttr("name", "这是通过test/index.do进入的页面（test不需要登录）");
		renderFreeMarker("/index.ftl");
	}
	
	/**
	 * 测试jodd的json工具类可用
	 */
	public void testLoserStarJson() {
		VResult result = new VResult();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("aaa", "111");
			data.put("bbb", 222);
			data.put("ccc", 3.333);
			data.put("ddd", new Date());
			result.setData(data);
			result.ok("测试成功，该数据使用的是loserStarJsonUtil序列化");
			System.out.println(LoserStarJsonUtil.toJsonDeep(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderText(LoserStarJsonUtil.toJsonDeep(result));
	}
	
	/**
	 * 查看系统信息和工程配置信息
	 */
	public void systemInfo() {
		VResult result= new VResult();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("systemInfo", System.getProperties());
			map.put("projectProperties", PropKit.getProp().getProperties());
			map.put("contextPath", getRequest().getContextPath());
			result.ok("成功");
			result.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			renderJson(result);
		}
	}
}
