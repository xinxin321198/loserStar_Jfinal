package com.loserstar.controller.pc;

import com.loserstar.config.annotation.Controller;
import com.loserstar.constants.DsConstans;
import com.loserstar.dao.SysUserDao;
import com.loserstar.entity.SysUser;

@Controller(controllerKey = "/index")
public class IndexController extends PcBaseController {

	public void index() {
		renderFreeMarker("/index.html");
	}
}
