package com.loserstar.controller.pc;

import java.io.File;
import java.util.List;

import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Page;
import com.loserstar.config.annotation.Controller;
import com.loserstar.constants.DsConstans;
import com.loserstar.dao.SysFileDao;
import com.loserstar.entity.SysFile;
import com.loserstar.utils.db.jfinal.base.imp.WhereHelper;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.idgen.LoserStarIdGenUtil;
import com.loserstar.utils.json.LoserStarJsonUtil;

/**
 * 附件
 * @author loserStar
 *
 */
@Controller(controllerKey = "/sysFile")
public class SysFileController extends PcBaseController {
	
	private SysFileDao sysFileDao = new SysFileDao(DsConstans.dataSourceName.local);

	/**
	 * 列表页面
	 */
	public void listPage() {
		try {
			String userid = getUserId();
			renderFreeMarker("/view/sysFile/sysFileList.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}
	}
	
	/**
	 * 获取分页数据
	 */
	public void getPageData() {
		VResult result = new VResult();
		try {
            String id = getPara("id");
            String name = getPara("name");
            String path = getPara("path");
            String upload_time = getPara("upload_time");
            String sort = getPara("sort");
            String del = getPara("del");
            String create_time = getPara("create_time");
            String create_user_code = getPara("create_user_code");
            String create_user_name = getPara("create_user_name");
            String update_time = getPara("update_time");
            String update_user_code = getPara("update_user_code");
            String update_user_name = getPara("update_user_name");
            String suffix = getPara("suffix");
            String from_id = getPara("from_id");
            String from_table = getPara("from_table");
			//排序字段
			String sort_filed = getPara("sort_filed");
			String sort_type = getPara("sort_type");
			
			WhereHelper whereHelper = new WhereHelper();
            if (checkNull(id)) {
				whereHelper.addStrWhere("and id like '%"+id+"%'");
			}
            if (checkNull(name)) {
				whereHelper.addStrWhere("and name like '%"+name+"%'");
			}
            if (checkNull(path)) {
				whereHelper.addStrWhere("and path like '%"+path+"%'");
			}
            if (checkNull(upload_time)) {
				whereHelper.addStrWhere("and upload_time like '%"+upload_time+"%'");
			}
            if (checkNull(sort)) {
				whereHelper.addStrWhere("and sort like '%"+sort+"%'");
			}
            if (checkNull(del)) {
				whereHelper.addStrWhere("and del like '%"+del+"%'");
			}
            if (checkNull(create_time)) {
				whereHelper.addStrWhere("and create_time like '%"+create_time+"%'");
			}
            if (checkNull(create_user_code)) {
				whereHelper.addStrWhere("and create_user_code like '%"+create_user_code+"%'");
			}
            if (checkNull(create_user_name)) {
				whereHelper.addStrWhere("and create_user_name like '%"+create_user_name+"%'");
			}
            if (checkNull(update_time)) {
				whereHelper.addStrWhere("and update_time like '%"+update_time+"%'");
			}
            if (checkNull(update_user_code)) {
				whereHelper.addStrWhere("and update_user_code like '%"+update_user_code+"%'");
			}
            if (checkNull(update_user_name)) {
				whereHelper.addStrWhere("and update_user_name like '%"+update_user_name+"%'");
			}
            if (checkNull(suffix)) {
				whereHelper.addStrWhere("and suffix like '%"+suffix+"%'");
			}
            if (checkNull(from_id)) {
				whereHelper.addStrWhere("and from_id like '%"+from_id+"%'");
			}
            if (checkNull(from_table)) {
				whereHelper.addStrWhere("and from_table like '%"+from_table+"%'");
			}
			//排序
			if (checkNull(sort_filed)) {
				whereHelper.addStrOrder("order by "+getSortField(sort_filed)+" "+sort_type);
			}
			whereHelper.addStrWhere("and CREATE_USER_CODE='"+getUserId()+"'");
			Page<SysFile> dataPage =  sysFileDao.getListPage(getPageNumber(), getPageSize(), whereHelper, SysFile.class, null);
			result.ok("获取数据成功");
			result.setData(dataPage);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}

    /**
	 * 获取数据list不带分页
	 */
	public void getListData() {
		VResult result = new VResult();
		try {
            String id = getPara("id");
            String name = getPara("name");
            String path = getPara("path");
            String upload_time = getPara("upload_time");
            String sort = getPara("sort");
            String del = getPara("del");
            String create_time = getPara("create_time");
            String create_user_code = getPara("create_user_code");
            String create_user_name = getPara("create_user_name");
            String update_time = getPara("update_time");
            String update_user_code = getPara("update_user_code");
            String update_user_name = getPara("update_user_name");
            String suffix = getPara("suffix");
            String from_id = getPara("from_id");
            String from_table = getPara("from_table");
			//排序字段
			String sort_filed = getPara("sort_filed");
			String sort_type = getPara("sort_type");
			
			WhereHelper whereHelper = new WhereHelper();
            if (checkNull(id)) {
				whereHelper.addStrWhere("and id like '%"+id+"%'");
			}
            if (checkNull(name)) {
				whereHelper.addStrWhere("and name like '%"+name+"%'");
			}
            if (checkNull(path)) {
				whereHelper.addStrWhere("and path like '%"+path+"%'");
			}
            if (checkNull(upload_time)) {
				whereHelper.addStrWhere("and upload_time like '%"+upload_time+"%'");
			}
            if (checkNull(sort)) {
				whereHelper.addStrWhere("and sort like '%"+sort+"%'");
			}
            if (checkNull(del)) {
				whereHelper.addStrWhere("and del like '%"+del+"%'");
			}
            if (checkNull(create_time)) {
				whereHelper.addStrWhere("and create_time like '%"+create_time+"%'");
			}
            if (checkNull(create_user_code)) {
				whereHelper.addStrWhere("and create_user_code like '%"+create_user_code+"%'");
			}
            if (checkNull(create_user_name)) {
				whereHelper.addStrWhere("and create_user_name like '%"+create_user_name+"%'");
			}
            if (checkNull(update_time)) {
				whereHelper.addStrWhere("and update_time like '%"+update_time+"%'");
			}
            if (checkNull(update_user_code)) {
				whereHelper.addStrWhere("and update_user_code like '%"+update_user_code+"%'");
			}
            if (checkNull(update_user_name)) {
				whereHelper.addStrWhere("and update_user_name like '%"+update_user_name+"%'");
			}
            if (checkNull(suffix)) {
				whereHelper.addStrWhere("and suffix like '%"+suffix+"%'");
			}
            if (checkNull(from_id)) {
				whereHelper.addStrWhere("and from_id like '%"+from_id+"%'");
			}
            if (checkNull(from_table)) {
				whereHelper.addStrWhere("and from_table like '%"+from_table+"%'");
			}
			//排序
			if (checkNull(sort_filed)) {
				whereHelper.addStrOrder("order by "+getSortField(sort_filed)+" "+sort_type);
			}
			List<SysFile> listData =  sysFileDao.getList(whereHelper, SysFile.class, null);
			result.ok("获取数据成功");
			result.setData(listData);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
	
	/**
	 * 表单页面
	 */
	public void formPage() {
		try {
			String userid = getUserId();
			String id = getPara("id");
			if (checkNull(id)) {
				SysFile sysFile = sysFileDao.getById(id, SysFile.class);
			}
			String op_type = getPara("op_type");
			setAttr("id", id);
			setAttr("op_type", op_type);
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/sysFile/sysFileForm.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}

	}

	/**
	 * 表单页面查看
	 */
	public void formPageView() {
		try {
			String userid = getUserId();
			String id = getPara("id");
			if (checkNull(id)) {
				SysFile sysFile = sysFileDao.getById(id, SysFile.class);
			}
			String op_type = getPara("op_type");
			setAttr("id", id);
			setAttr("op_type", "view");
			setAttr("currentTimeMillis", System.currentTimeMillis());
			renderFreeMarker("/view/sysFile/sysFileForm.html");
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());
		}
	}
	
	
	/**
	 * 保存（新增和修改一体的）
	 */
	public void save() {
		VResult result = new VResult();
		try {
			String userid = getUserId();
			String json = HttpKit.readData(getRequest());
			System.out.println(json);
			SysFile sysFile = LoserStarJsonUtil.toModel(json, SysFile.class);
			boolean flag = false;
			if (sysFileDao.getById(sysFile.getId())==null) {
				sysFile.setId(LoserStarIdGenUtil.uuidHex());
				setCreateUser(sysFile);
				sysFile.removeNullValueAttrs();
				flag = sysFileDao.insert(sysFile);
				result.ok("新增成功");
			}else {
				setUpdateUser(sysFile);
				sysFile.removeNullValueAttrs();
				flag = sysFileDao.update(sysFile);
				result.ok("更新成功");
			}
			if (!flag) {
				throw new Exception("操作失败，请联系管理员");
			}
			
			result.setData(sysFile);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
	
	/**
	 * 根据主键id获取数据
	 */
	public void getById() {
		VResult result = new VResult();
		try {
			String userid = getUserId();
			String id = getPara("id");
			if (!checkNull(id)) {
				throw new Exception("没有传入数据id");
			}
			SysFile sysFile = sysFileDao.getById(id,SysFile.class);
			if (sysFile==null) {
				throw new Exception("没有找到该id的数据");
			}
		 result.ok("获取数据成功");
		 result.setData(sysFile);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}

	/**
	 * 根据id，删除数据
	 */
	public void delById() {
		VResult result = new VResult();
		try {
			String id = getPara("id");
			if (!checkNull(id)) {
				throw new Exception("没有传入id");
			}
			boolean flag = true;
			SysFile sysFile = sysFileDao.getById(id,SysFile.class);
			if (sysFile==null) {
				throw new Exception("没有找到该数据，不能删除"+id);
			}
			flag = sysFileDao.deleteSoftById(id);
			if (!flag) {
				throw new Exception("删除失败");
			}
			result.ok("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
	
	/**
	 * 下载附件
	 */
	public void downFile() {
		try {
			String id = getPara("id");
			SysFile sysFile = sysFileDao.getById(id,SysFile.class);
			renderFile(new File(sysFile.getPath()),sysFile.getName());
		} catch (Exception e) {
			e.printStackTrace();
			renderText(e.getMessage());
		}
	}
}
