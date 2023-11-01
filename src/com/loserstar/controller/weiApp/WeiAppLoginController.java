package com.loserstar.controller.weiApp;

import com.jfinal.kit.PropKit;
import com.loserstar.config.annotation.Controller;
import com.loserstar.utils.WeiAppUtils;
import com.loserstar.utils.WeiAppUtils.ApiCode2SessionVo;
import com.loserstar.utils.db.jfinal.vo.VResult;
import com.loserstar.utils.idgen.LoserStarIdGenUtil;

@Controller(controllerKey= {"/weiAppLogin"})
public class WeiAppLoginController extends WeiAppBaseController {
	
	/**
	 * 微信小程序登录
	 */
	public void weiAppLogin() {
		VResult result = new VResult();
		try {
			String code = getPara("code");
			if (!checkNull(code)) {
				throw new Exception("没有接收到code");
			}
			boolean flag = true;
			//获取openid和seesion_key
			ApiCode2SessionVo code2SessionVo =  WeiAppUtils.code2Session(PropKit.get("weiApp.appid"), PropKit.get("weiApp.secret"), code);
			String token = LoserStarIdGenUtil.uuidHex();//生成token
			if (code2SessionVo.getErrcode()==0) {
				//请求成功
				//查找是否存在该openid的用户
			}else {
				//请求失败
				throw new Exception(code2SessionVo.getErrmsg());
			}
			
			if (!flag) {
				throw new Exception("保存hthy_base_user数据失败");
			}
			result.ok("成功");
			result.setData(null);
		} catch (Exception e) {
			e.printStackTrace();
			result.error(e.getMessage());
		}
		renderJson(result);
	}
	
}
