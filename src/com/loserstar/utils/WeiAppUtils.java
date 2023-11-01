package com.loserstar.utils;

import com.loserstar.utils.http.LoserStarHttpUtil;
import com.loserstar.utils.json.LoserStarJsonUtil;

/**
 * 微信小程序相关api
 * author: loserStar
 * date: 2023年6月5日下午4:56:26
 * remarks:
 */
public class WeiAppUtils {
	
	/**
	 * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
	 * 该接口的返回值vo
	 */
	public static class ApiCode2SessionVo{
		/**
		 * 会话密钥
		 */
		private String session_key;
		/**
		 * 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
		 */
		private String unionid;
		/**
		 * 错误信息
		 */
		private String errmsg;
		/**
		 * 用户唯一标识
		 */
		private String openid;
		
		/**
		 * 错误码
		 */
		private int errcode;

		public String getSession_key() {
			return session_key;
		}

		public void setSession_key(String session_key) {
			this.session_key = session_key;
		}

		public String getUnionid() {
			return unionid;
		}

		public void setUnionid(String unionid) {
			this.unionid = unionid;
		}

		public String getErrmsg() {
			return errmsg;
		}

		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}

		public String getOpenid() {
			return openid;
		}

		public void setOpenid(String openid) {
			this.openid = openid;
		}

		public int getErrcode() {
			return errcode;
		}

		public void setErrcode(int errcode) {
			this.errcode = errcode;
		}
		
	}

	/**
	 * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
	 * https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-login/code2Session.html
	 * @param appid 小程序 appId
	 * @param secret 小程序 appSecret（去登录小程序的后台-开发-开发管理，里面找）
	 * @param js_code 用户的ocde
	 * @param grant_type 固定值 authorization_code
	 * @return
	 * @throws Exception 
	 */
	public static ApiCode2SessionVo code2Session(String appid,String secret,String js_code) throws Exception{
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code";
		String resStr = LoserStarHttpUtil.getEx(url, null);
		System.out.println(resStr);
		ApiCode2SessionVo apiCode2SessionVo = LoserStarJsonUtil.toModel(resStr, ApiCode2SessionVo.class);
		return apiCode2SessionVo;
	}
}
