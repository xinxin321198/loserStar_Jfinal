
/**
 * loserStar 基于 www.sweetalert.cn 的提示框方法
 * https://sweetalert.js.org/
 * version:20231024
 * author：loserStar
 * github:https://github.com/xinxin321198/loserStarJsUtils
 * emial:362527240@qq.com
 */
var loserStarSweetAlertUtils = {};

loserStarSweetAlertUtils.alertWarning = function (title, content, callback) {
	loserStarSweetAlertUtils.alert(title, content, "warning", callback);
}

loserStarSweetAlertUtils.alertError = function (title, content, callback) {
	loserStarSweetAlertUtils.alert(title, content, "error", callback);
}

loserStarSweetAlertUtils.alertSuccess = function (title, content, callback) {
	loserStarSweetAlertUtils.alert(title, content, "success", callback);
}

loserStarSweetAlertUtils.alertInfo = function (title, content, callback) {
	loserStarSweetAlertUtils.alert(title, content, "info", callback);
}

loserStarSweetAlertUtils.alert = function (title, content, type, callback) {
	if (!title) { title = ""; }
	if (!content) { content = ""; }
	swal({
		title: title,
		icon: type,
		text: content,
		closeOnClickOutside: false,
	}).then(function (value) {
		if (callback) { callback(value); }
	});
}

/**
 * 确认框，类似于alert
 * @param {*} title 标题
 * @param {*} content 内容
 * @param {*} okCallback 确定的回调函数
 * @param {*} cancelCallback 取消的回调函数
 */
loserStarSweetAlertUtils.confirm = function(title,content,okCallback,cancelCallback){
	swal({
	    title: title,
	    text: content,
	    icon: "warning",
	    buttons: ["取消","确定"],
	    dangerMode: true,
	    closeOnClickOutside: false
	  }).then(function(will){
		  if(will){
			  if(okCallback){
				  okCallback();
			  }
		  }else{
			  if(cancelCallback){
				  cancelCallback();
			  }
		  }
	  });
}

/**
 * 弹出能填写内容的提示框
 * @param {*} title 标题
 * @param {*} content 内容
 * @param {*} placeholder input框中的提示
 * @param {*} okCallback 确定后的回调函数
 * @param {*} cancelCallback 取消时的回调函数（点击框外即取消）
 */
loserStarSweetAlertUtils.prompt = function (title, content, placeholder, okCallback, cancelCallback) {
	swal({
		title : title,
		text : content,
		icon : "warning",
		button:"确定",
		content : {
			element : "input",
			attributes : {
				placeholder : placeholder,
				type : "text",
			}
		},
	}).then(function(will) {
		if(null != will){
			if (okCallback) {
				okCallback(will);
			}
		}else{
			if(cancelCallback){
				cancelCallback();
			}
		}
	});
}

/**
 * 自定义confirm
 * @param {*} title 
 * @param {*} content 
 * @param {*} icon "warning" "error" "success" "info"
 * @param {*} buttons buttons配置对象 
 * @param {*} okCallback 点击其它选项时调用的回调方法，接收该按钮的value
 * @param {*} cancelCallback 点击cancel时调用的回调方法 
 * buttons: {
			cancel: "取消",
			ok: {
				text: "赞成",
				value: "1",
				className: "btn-success",
			}, opposeCount: {
				text: "反对",
				value: "0",
				className: "btn-danger",
			}, no: {
				text: "弃权",
				value: "2",
				className: "btn-warning",
			}
		}
 */
loserStarSweetAlertUtils.confirmCus = function (title, content,icon,buttonsCfg,okCallback, cancelCallback) {
	swal({
		title: title,
		text: content,
		icon: icon,
		buttons: buttonsCfg,
		dangerMode: true,
		closeOnClickOutside: false
	}).then(function (will) {
		if (will) {
			if (okCallback) {
				okCallback(will);
			}
		} else {
			if (cancelCallback) {
				cancelCallback();
			}
		}
	});
}