/**
 * version:20230922
 * author：loserStar
 * github:https://github.com/xinxin321198/loserStarJsUtils
 * emial:362527240@qq.com
 * 新增了浏览器的版本检测
 * 新增了获取浏览器窗口宽高的方法
 * 20230922 selectedOption里最后触发一下change方法，以便兼容第三方插件，比如select2
 * 20241204 不适用const，提高浏览器兼容性
 */
var loserStarJsUtils = {};

/**
 * 检查IE浏览器版本
 * @returns
 */
loserStarJsUtils.IEVersion = function () {
  var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
  var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
  var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
  var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
  if (isIE) {
    var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
    reIE.test(userAgent);
    var fIEVersion = parseFloat(RegExp["$1"]);
    if (fIEVersion == 7) {
      return 7;
    } else if (fIEVersion == 8) {
      return 8;
    } else if (fIEVersion == 9) {
      return 9;
    } else if (fIEVersion == 10) {
      return 10;
    } else {
      return 6;//IE版本<=7
    }
  } else if (isEdge) {
    return 'edge';//edge
  } else if (isIE11) {
    return 11; //IE11  
  } else {
    return -1;//不是ie浏览器
  }
}

/**
 * 检查浏览器版本
 * @returns
 */
loserStarJsUtils.BorwserVersion = function () {
  var isIE = loserStarJsUtils.IEVersion();
  if (isIE == -1) {//非IE
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器  
    var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器  
    var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器  
    var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器
    var isEdge = userAgent.indexOf("Edg") > -1 || userAgent.indexOf("Edge") > -1; // 判断是否Edge浏览器
    if (isFF) { return "FF"; }
    if (isOpera) { return "Opera"; }
    if (isSafari) { return "Safari"; }
    if (isChrome) { return "Chrome"; }
    if (isEdge) { return "Edge"; }
  } else {
    return isIE;
  }
}

/**
 * crhome6以上的版本
 */
loserStarJsUtils.Chrome6NewVersion = function () {
  var flag = true;
  browserType = loserStarJsUtils.checkBrowserType();
  if (browserType != 'Chrome6+') {
    flag = false;
  }
  return flag;
}

/**
 * 检查浏览器类型
 */
loserStarJsUtils.checkBrowserType = function () {
  var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
  var isOpera = userAgent.indexOf('Opera') > -1;
  if (isOpera) {
    return 'Opera';
  }

  if (userAgent.indexOf('Firefox') > -1) {
    return 'FF';
  }

  if (userAgent.indexOf('Chrome') > -1) {
    if (loserStarJsUtils.getChromeVersion() < 60) {
      return 'Chrome6-';
    } else {
      return 'Chrome6+';
    }
  }

  if (userAgent.indexOf('Safari') > -1) {
    return 'Safari';
  }

  if (!!window.ActiveXObject || 'ActiveXObject' in window) {
    return 'IE10+';
  }

  if (
    userAgent.indexOf('compatible') > -1 &&
    userAgent.indexOf('MSIE') > -1 &&
    !isOpera
  ) {
    return 'IE10-';
  }

  return '';
}

/**
 * 检查chrome浏览器的版本
 */
loserStarJsUtils.getChromeVersion = function () {
  var arr = navigator.userAgent.split(' ');
  var chromeVersion = '';
  for (var i = 0; i < arr.length; i++) {
    if (/chrome/i.test(arr[i])) chromeVersion = arr[i];
  }
  if (chromeVersion) {
    return Number(chromeVersion.split('/')[1].split('.')[0]);
  } else {
    return false;
  }
}

/**
 * 判断访问终端
 */
loserStarJsUtils.getBrowser = function () {
  var browser = {
    versions: function () {
      var u = navigator.userAgent, app = navigator.appVersion;
      return {
        trident: u.indexOf('Trident') > -1,                            //IE内核
        presto: u.indexOf('Presto') > -1,                              //opera内核
        webKit: u.indexOf('AppleWebKit') > -1,                         //苹果、谷歌内核
        gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,    //火狐内核
        mobile: !!u.match(/AppleWebKit.*Mobile.*/),                    //是否为移动终端
        ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),               //ios终端
        android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
        iPhone: u.indexOf('iPhone') > -1,                              //是否为iPhone或者QQHD浏览器
        iPad: u.indexOf('iPad') > -1,                                  //是否iPad
        webApp: u.indexOf('Safari') == -1,                             //是否web应该程序，没有头部与底部
        weixin: u.indexOf('MicroMessenger') > -1,                      //是否微信 （2015-01-22新增）
        qq: u.match(/\sQQ/i) == " qq"                                  //是否QQ
      };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
  };
  return browser;
}

/**
 * 判断是否pc端
 */
loserStarJsUtils.checkIsPc = function () {
  if (browser.versions.mobile || browser.versions.android || browser.versions.ios) {
    console.info("移动端");
    return false;
  } else {
    console.info("非移动端");
    return true;
  }
}

/**
 * flash是否打开
 */
loserStarJsUtils.isFlashOpen = function () {
  var rtn = true;
  var isIE = !-[1,];
  if (isIE) {
    try {
      var swf1 = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
    }
    catch (e) {
      rtn = false;
    }
  }
  else {
    try {
      var swf2 = navigator.plugins['Shockwave Flash'];
      if (swf2 == undefined) {
        rtn = false;
      }
    } catch (e) {

    }
  };
  if (rtn == false) {
    alert("您未开启Flash！");
  } else { }
}

/**
 * 得到鼠标坐标
 */
loserStarJsUtils.getMousePos = function (event) {
  var e = event || window.event;
  var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
  var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
  var x = e.pageX || e.clientX + scrollX;
  var y = e.pageY || e.clientY + scrollY;
  //alert('x: ' + x + '\ny: ' + y);
  return { 'x': x, 'y': y };
}

/**
 * 得到浏览器窗口宽度
 */
loserStarJsUtils.getWinWidth = function () {
  var winWidth = 0;
  // 获取窗口宽度
  if (window.innerWidth)
    winWidth = window.innerWidth;
  else if ((document.body) && (document.body.clientWidth)) {
    winWidth = document.body.clientWidth;
  }
  // 通过深入 Document 内部对 body 进行检测，获取窗口大小
  if (document.documentElement && document.documentElement.clientWidth) {
    winWidth = document.documentElement.clientWidth;
  }
  return winWidth;
}

/**
 * 得到浏览器窗口宽度
 */
loserStarJsUtils.getWinHeight = function () {
  var winHeight = 0;
  // 获取窗口高度
  if (window.innerHeight)
    winHeight = window.innerHeight;
  else if ((document.body) && (document.body.clientHeight)) {
    winHeight = document.body.clientHeight;
  }
  // 通过深入 Document 内部对 body 进行检测，获取窗口大小
  if (document.documentElement && document.documentElement.clientHeight) {
    winHeight = document.documentElement.clientHeight;
  }
  return winHeight;
}

/**
 * 检测数据是否有值，有返回true，没有返回false
 * @param {*} o 
 */
loserStarJsUtils.checkObj = function (o) {
  var flag = true;
  if (o == undefined || o == null) {
    flag = false;
  } else {
    if ("[object Number]" == Object.prototype.toString.call(o)) {
      if (0 >= o) {
        flag = false;
      }
    } else if ("[object String]" == Object.prototype.toString.call(o)) {
      if ('' == o) {
        flag = false;
      }
    } else if ("[object Boolean]" == Object.prototype.toString.call(o)) {
      flag = o;
    } else if ("[object Array]" == Object.prototype.toString.call(o)) {
      if (0 >= o.length) {
        flag = false;
      }
    }
  }
  return flag;
}

/**
 * 替换所有匹配的字符串
 */
loserStarJsUtils.replaceAll = function (sourceStr, search, replacement) {
  return sourceStr.replace(new RegExp(search, 'g'), replacement);
};

/**
 * 获取除了undefined和null之外的值
 */
loserStarJsUtils.getNotNULLVal = function (o) {
  if (o == undefined || o == null) {
    return "";
  } else {
    return o;
  }
}

/**
 * 把某个checkbox里选中的值以逗号分隔放入第二个参数的元素的value上
 * @param checkBoxName
 * @param strSelector
 * @returns
 */
loserStarJsUtils.checkedBoxToStr = function (checkBoxSelector, strSelector) {
  var accessoryTypeList = $(checkBoxSelector);
  var attachedListStr = "";
  accessoryTypeList.each(function () {
    if (this.checked) {
      attachedListStr += this.value;
      attachedListStr += ",";
    }
  });
  attachedListStr = attachedListStr.substring(0, attachedListStr.length - 1);
  $(strSelector).val(attachedListStr);
};
/**
 * 把某个元素的value里的值取出来（以逗号分隔的字符串），设置让checkbox选中
 * @param checkBoxName
 * @param strSelector
 * @returns
 */
loserStarJsUtils.strToCheckedBox = function (checkBoxSelector, strSelector) {
  var accessoryTypeList = $(strSelector).val();
  var checkBoxList = $(checkBoxSelector);
  var attachedArray = accessoryTypeList.split(",");
  for (var i = 0; i < attachedArray.length; i++) {
    checkBoxList.each(function () {
      if (this.value == attachedArray[i]) {
        this.setAttribute("checked", "checked");
      }
    });
  }
};

/**
 * 根据勾选checkbox，隐藏显示对应的tab
 * selector tab的外层div的选择器（包裹着navigation和具体的tab）
 * 此方法依赖bootstrap的Tab插件
 * @param selector tab的外层div的选择器（包裹着navigation和具体的tab）
 * @param checkBoxSelector 用来勾选的checkbox的选择器
 * @returns
 */
loserStarJsUtils.hideShowTab = function (selector, checkBoxSelector) {
  //初始化Nav tabs样式，全隐藏
  $(selector).attr("class", "hidden");
  $("#Nav0").attr("class", "hidden");
  $("#Nav1").attr("class", "hidden");
  $("#Nav2").attr("class", "hidden");
  $(checkBoxSelector + ":checked").each(function () {
    var tabIndex = $(this).val();
    $(selector).removeClass("hidden"); //取消整个navList隐藏
    $("#Nav" + tabIndex).removeClass("hidden"); //根据勾选来决定是否显示某个nav
    $("#tabList #Nav" + tabIndex + " a").tab("show"); //插件命令，出发点击某个nav的超链接，显示实际内容
  });
};

//勾选副表方法定义
loserStarJsUtils.checkedAccessoryType = function () {
  checkedBoxToStr("input.accessoryType", "#attachedList"); //勾选checkbox，然后把值以逗号分隔放在某个元素的value上
  hideShowTab("#fileNavList", "input.accessoryType");
};

/**
 * 让select元素中的某个option被选中
 *  @param selector  select元素的选择器
 * @param value 需要选中的option的值
 * @returns
 */
loserStarJsUtils.selectedOption = function (selector, value) {
  var yearSelect = $(selector)[0];
  for (var i = 0; i < yearSelect.options.length; i++) {
    var tmp = yearSelect.options[i].value;
    if (tmp == value) {
      yearSelect.options[i].selected = "selected";
      break;
    }
  }
  //最后触发一下change事件，以便兼容第三方插件，比如select2
  $(selector).trigger("change");
};

/**
 * 日期格式化
 */
loserStarJsUtils.dateFormat = function (dateString, format) {
  if (dateString == undefined || dateString == null) return "";
  var time =
    dateString instanceof Date ?
      dateString :
      new Date(
        dateString
          .replace("年", "-")
          .replace("月", "-")
          .replace("日", "")
          .replace(/-/g, "/")
          .replace(/T|Z/g, " ")
          .trim()
      );
  var o = {
    "M+": time.getMonth() + 1, // 月份
    "d+": time.getDate(), // 日
    "h+": time.getHours(), // 小时
    "m+": time.getMinutes(), // 分
    "s+": time.getSeconds(), // 秒
    "q+": Math.floor((time.getMonth() + 3) / 3), // 季度
    S: time.getMilliseconds()
    // 毫秒
  };
  if (/(y+)/.test(format))
    format = format.replace(
      RegExp.$1,
      (time.getFullYear() + "").substr(4 - RegExp.$1.length)
    );
  for (var k in o)
    if (new RegExp("(" + k + ")").test(format))
      format = format.replace(
        RegExp.$1,
        RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length)
      );
  return format;
}

/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num
 *            数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */

loserStarJsUtils.formatCurrency = function (num) {
  num = num.toString().replace(/\$|\,/g, "");
  if (isNaN(num)) num = "0";
  sign = num == (num = Math.abs(num));
  num = Math.floor(num * 100 + 0.50000000001);
  cents = num % 100;
  num = Math.floor(num / 100).toString();
  if (cents < 10) cents = "0" + cents;
  for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
    num =
      num.substring(0, num.length - (4 * i + 3)) +
      "," +
      num.substring(num.length - (4 * i + 3));
  return (sign ? "" : "-") + num + "." + cents;
}

/**
 * 让某个select元素的值选中，基于jquery
 * @param {*} selector id选择器
 * @param {*} value 
 */
loserStarJsUtils.setSelectedForSelect = function (selector, value) {
  $(selector).find("[value=" + value + "]").attr("selected", true);
}

/**
 * 获取某个radio元素的选中的值
 * @param {*} elementName name名称
 */
loserStarJsUtils.getSelectedForRadio = function (elementName) {
  return $("input[name='" + elementName + "']:checked").val();
}

/**
 * 让某个radio元素的值选中，基于jquery
 * @param {*} elementName radio的name
 * @param {*} value 
 */
loserStarJsUtils.setSelectedForRadio = function (elementName, value) {
  $("input[name='" + elementName + "'][value=" + value + "]").attr("checked", true);
}

/**
 * 让某个radio元素的值选中，针对jquery mobile的组件的方法,因为jqueryMobile需要调用一个刷新方法
 */
loserStarJsUtils.setSelectedForRadio_forJqueryMobile = function (name, value) {
  $("input[name='" + name + "'][value='" + value + "'] ").attr("checked", true).checkboxradio("refresh");
}

loserStarJsUtils.disabledForRadio = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true);;
  });
}

loserStarJsUtils.enabledForRadio = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false);
  });
}
loserStarJsUtils.disabledForRadio_forJqueryMobile = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true).checkboxradio("refresh");;
  });
}

loserStarJsUtils.enabledForRadio_forJqueryMobile = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false).checkboxradio("refresh");
  });
}


/**
 * 得到select元素选中项的text
 * @param {*} selector 
 */
loserStarJsUtils.getSelectedTextForSelect = function (selector) {
  return $(selector).find("option:selected").text();
}

/**
 * 得到seelct元素选中项的value(第一种方式，基于jquery选择器的)
 * @param {*} selector 
 */
loserStarJsUtils.getSelectedValueForSelect1 = function (selector) {
  return $(selector).find("option:selected").val();
}
/**
 * 得到seelct元素选中项的value(第二种方式，基于jquery自动取值的)
 * @param {*} selector 
 */
loserStarJsUtils.getSelectedValueForSelect2 = function (selector) {
  return $(selector).val();
}



/**
 * textarea自动高度
 */
loserStarJsUtils.autoTextAreaHeight = function (o) {
  if (o) {
    o.style.height = (o.scrollTop + o.scrollHeight + 2) + "px";
  }
}

/**
 * 给input date一个默认值
 */
loserStarJsUtils.setInputDateDefault = function (selector) {
  // 给input  date设置默认值
  var now = new Date();
  //格式化日，如果小于9，前面补0
  var day = ("0" + now.getDate()).slice(-2);
  //格式化月，如果小于9，前面补0
  var month = ("0" + (now.getMonth() + 1)).slice(-2);
  //拼装完整日期格式
  var today = now.getFullYear() + "-" + (month) + "-" + (day);
  //完成赋值
  $(selector).val(today);
}

/**
 * 弹出提示框
 */
loserStarJsUtils.showMsg = function (text, position) {
  var div = $('<div></div>');
  // div.addClass('show_msg');
  // .show_msg {
  // 	width:100%;
  // 	text-align:center;
  // 	position:fixed;
  // 	left:0;
  // 	z-index:999;
  // }
  div.css("width", "100%");
  div.css("text-align", "center");
  div.css("position", "fixed");
  div.css("left", "0");
  div.css("z-index", "999");

  if (position == 'top') {
    div.css('top', '10%');
  } else if (position == 'center') {
    div.css('top', '');
    div.css('top', '50%');
  } else {
    div.css('top', '95%');
  }

  var span = $('<span></span>');
  // span.addClass('show_span');
  // .show_span {
  // 	display:inline-block;
  // 	padding:10px 20px;
  // 	line-height:0.35rem;
  // 	background:rgba(0,0,0,0.8);
  // 	border-radius:50px;
  // 	color:#fff;
  // 	font-size:20px;
  // }
  span.css("display", "inline-block");
  span.css("padding", "10px 20px");
  span.css("line-height", "0.35rem");
  span.css("background", "rgba(0,0,0,0.8)");
  span.css("border-radius", "50px");
  span.css("color", "#fff");
  span.css("font-size", "20px");
  span.text(text);

  div.append(span);
  $('body').append(div);
  div.hide();
  div.fadeIn(1000);
  div.fadeOut(1000);
};

/**
 * 获取url参数
 * @param name
 * @returns
 */
loserStarJsUtils.GetQueryString = function (name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg); //search,查询？后面的参数，并匹配正则
  if (r != null) return unescape(r[2]);
  return null;
}


/**
 * 解析url（来源： 司徒正美 https://www.cnblogs.com/rubylouvre/archive/2010/06/09/1755051.html）
 * @param {*} url 
 * @returns 
 */
loserStarJsUtils.parseURL = function (url) {
  var a = document.createElement('a');
  a.href = url;
  return {
    source: url,
    protocol: a.protocol.replace(':', ''),
    host: a.hostname,
    port: a.port,
    query: a.search,
    params: (function () {
      var ret = {},
        seg = a.search.replace(/^\?/, '').split('&'),
        len = seg.length, i = 0, s;
      for (; i < len; i++) {
        if (!seg[i]) { continue; }
        s = seg[i].split('=');
        ret[s[0]] = s[1];
      }
      return ret;

    })(),
    file: (a.pathname.match(/\/([^\/?#]+)$/i) || [, ''])[1],
    hash: a.hash.replace('#', ''),
    path: a.pathname.replace(/^([^\/])/, '/$1'),
    relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [, ''])[1],
    segments: a.pathname.replace(/^\//, '').split('/')
  };
}


/**
 * 替换url中同名的参数值，没有的话就添加上
 * @param {*} myUrl 并非url字符串，需要调用司徒正美的loserStarJsUtils.parseURL(url)方法生成的对象
 * @param {*} newParams  { id: 101, m: "World", page: 1,"page":2 }
 * @returns 
 */
loserStarJsUtils.replaceUrlParams = function (myUrl, newParams) {
  /*
  for (var x in myUrl.params) {
      for (var y in newParams) {
          if (x.toLowerCase() == y.toLowerCase()) {
              myUrl.params[x] = newParams[y];
          }
      }
  }
  */

  for (var x in newParams) {
    var hasInMyUrlParams = false;
    for (var y in myUrl.params) {
      if (x.toLowerCase() == y.toLowerCase()) {
        myUrl.params[y] = newParams[x];
        hasInMyUrlParams = true;
        break;
      }
    }
    //原来没有的参数则追加
    if (!hasInMyUrlParams) {
      myUrl.params[x] = newParams[x];
    }
  }
  var _result = myUrl.protocol + "://" + myUrl.host + ":" + myUrl.port + myUrl.path + "?";

  for (var p in myUrl.params) {
    _result += (p + "=" + myUrl.params[p] + "&");
  }

  if (_result.substr(_result.length - 1) == "&") {
    _result = _result.substr(0, _result.length - 1);
  }

  if (myUrl.hash != "") {
    _result += "#" + myUrl.hash;
  }
  return _result;
}

/**
 * 获取选中的checkbox的值,基于jquery选择器,传入元素的name
 */
loserStarJsUtils.getSelectedForCheckbox = function (elementName) {
  var selectedList = [];
  for (var i = 0; i < $("input[name='" + elementName + "']:checked").length; i++) {
    selectedList[i] = $("input[name='" + elementName + "']:checked")[i].value;
  }
  return selectedList;
}

/**
 * 获取选中的checkbox的值,以某个字符分隔，传入元素的name
 */
loserStarJsUtils.getSelectedStrForCheckbox = function (elementName, splitChar) {
  var selectedList = loserStarJsUtils.getSelectedForCheckbox(elementName);
  var str = "";
  for (var i = 0; i < selectedList.length; i++) {
    str += selectedList[i];
    if (i < (selectedList.length - 1)) {
      str += splitChar;
    }
  }
  return str;
}

/**
 * 传入想要选中的值的集合，让这些值相等的checkbox选中
 * @param {*} elmentName 元素的name
 * @param {*} list 要选中的值的列表
 */
loserStarJsUtils.setSelectedForCheckbox = function (elmentName, valueList) {
  var checkBoxList = $("input[name='" + elmentName + "']");
  for (var i = 0; i < valueList.length; i++) {
    checkBoxList.each(function () {
      if (this.value == valueList[i]) {
        this.setAttribute("checked", "checked");
      }
    });
  }
}

/**
 * 传入想要选中的值的集合，让这些值相等的checkbox选中(针对jquery mobile)
 * @param {*} elmentName 元素的name
 * @param {*} list 要选中的值的列表
 */
loserStarJsUtils.setSelectedForCheckbox_forJqueryMobile = function (elmentName, valueList) {
  loserStarJsUtils.setSelectedForCheckbox(elmentName, valueList);
  $("input[name='" + elmentName + "']").checkboxradio("refresh");
}

loserStarJsUtils.disabledForCheckbox = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true);;
  });
}

loserStarJsUtils.enabledForCheckbox = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false);
  });
}
loserStarJsUtils.disabledForCheckbox_forJqueryMobile = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true).checkboxradio("refresh");;
  });
}

loserStarJsUtils.enabledForCheckbox_forJqueryMobile = function (name) {
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false).checkboxradio("refresh");
  });
}

/**
 * 设置元素的readonly属性，并且添加白色的背景色class
 * @param selector
 * @returns
 */
loserStarJsUtils.setReadOnly = function (selector) {
  $(selector).removeClass("kaen-formtable-input-write");
  $(selector).attr("readonly", "readonly");
}

/**
 * 移除元素的readonly属性，并且添加黄色的背景色class
 * @param selector
 * @returns
 */
loserStarJsUtils.setWrite = function (selector) {
  $(selector).removeAttr("readonly");
  $(selector).attr("class", "kaen-formtable-input-write");
}

/**
 * 仅添加黄色的背景色样式
 * @param selector
 * @returns
 */
loserStarJsUtils.setWriteClass = function (selector) {
  $(selector).attr("class", "kaen-formtable-input-write");
}

/**
 * 设置禁用，两种方法设置
 */
loserStarJsUtils.setDisabled = function (selector) {
  $(selector).attr("disabled", true);
  //	$(selector).attr("disabled","disabled");
}

/**
 * 设置启用，三种方法移除disabled属性
 */
loserStarJsUtils.setEnabled = function (selector) {
  $(selector).attr("disabled", false);
  //	$(selector).removeAttr("disabled");
  //	$(selector).attr("disabled","");
}

/**
 * 深拷贝js对象（转为json字符串，再转回去。缺点：a. 如果你的对象里有函数, 函数无法被拷贝下来   b. 无法拷贝copyObj对象原型链上的属性和方法）
 */
loserStarJsUtils.copyObj = function (o) {
  return JSON.parse(JSON.stringify(o));
}




/**
 * 深拷贝JS对象2(此方法原样copy对象，包括对象中的属性，方法等)
 * 深拷贝JS对象  深拷贝, 就是遍历那个被拷贝的对象。判断对象里每一项的数据类型。如果不是对象类型, 就直接赋值, 如果是对象类型, 就再次调用递归的方法去赋值。
 */
loserStarJsUtils.copyObj2 = function (obj) {
  var result, oClass = Object.prototype.toString.call(obj).slice(8, -1);
  if (oClass == "Object") result = {}; //判断传入的如果是对象，继续遍历
  else if (oClass == "Array") result = []; //判断传入的如果是数组，继续遍历
  else return obj; //如果是基本数据类型就直接返回
  for (var i in obj) {
    var copy = obj[i];
    if (Object.prototype.toString.call(obj).slice(8, -1) == "Object") result[i] = loserStarJsUtils.copyObj2(copy); //递归方法 ，如果对象继续变量obj[i],下一级还是对象，就obj[i][i]
    else if (Object.prototype.toString.call(obj).slice(8, -1) == "Array") result[i] = loserStarJsUtils.copyObj2(copy); //递归方法 ，如果对象继续数组obj[i],下一级还是数组，就obj[i][i]
    else result[i] = copy; //基本数据类型则赋值给属性
  }
  return result;
}

/**
 * 密码强度校验，返回一个密码强度级别的数字
 * 0：表示第一个级别 长度小于8位
 * 1：表示第二个级别 长度大于等于8位，且包含了数字、小写字母、大写字母、特殊字符 任意1种
 * 2：表示第三个级别 长度大于等于8位，且包含了数字、小写字母、大写字母、特殊字符 任意2种
 * 3：表示第四个级别 长度大于等于8位，且包含了数字、小写字母、大写字母、特殊字符 任意3种
 * 4：表示第五个级别 长度大于等于8位，且包含了数字、小写字母、大写字母、特殊字符 任意4种
 */
loserStarJsUtils.checkPassWord = function (value) {
  var modes = 0;
  if (value.length < 8) {//最初级别
    return modes;
  }
  if (/\d/.test(value)) {//如果用户输入的密码 包含了数字
    modes++;
  }
  if (/[a-z]/.test(value)) {//如果用户输入的密码 包含了小写的a到z
    modes++;
  }
  if (/[A-Z]/.test(value)) {//如果用户输入的密码 包含了大写的A到Z
    modes++;
  }
  if (/\W/.test(value)) {//如果是非数字 字母 下划线
    modes++;
  }
  switch (modes) {
    case 1:
      return 1;
      break;
    case 2:
      return 2;
      break;
    case 3:
      return 3;
      break;
    case 4:
      return 4;
      break;
  }
}

loserStarJsUtils.emty = function (obj) {
  if (obj == undefined || obj == null) {
    return "";
  } else {
    return obj;
  }
}

/**
 * 返回上一页并刷新
 */
loserStarJsUtils.backPage = function () {
  window.location.replace(document.referrer);
  // history.go(-1); location.reload();
}


/**
 * 验证身份证号的正确性
 */
loserStarJsUtils.validateIdCard = function(idCard) {
  // 正则表达式匹配身份证号格式
  var regex = /(^\d{15}$)|(^\d{17}(\d|X)$)/i;
  if (!regex.test(idCard)) {
    return false; // 格式不正确，直接返回false
  }

  // 检查身份证号最后一位校验码
  if (idCard.length === 18) {
    var numArr = idCard.split("");
    var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
    var checkCode = ["1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
    var sum = 0;
    for (var i = 0; i < 17; i++) {
      sum += parseInt(numArr[i]) * factor[i];
    }
    var mod = sum % 11;
    if (numArr[17].toUpperCase() !== checkCode[mod]) {
      return false; // 校验码不匹配，直接返回false
    }
  }

  return true; // 身份证号格式正确
}

/**
 * 通过身份证号计算出出生年月
 */
loserStarJsUtils.parseIdCardToBirthday = function (idCard) {
  var birthday;
  if (idCard.length === 15) {
    var year = "19" + idCard.substring(6, 8);
    var month = idCard.substring(8, 10);
    var day = idCard.substring(10, 12);
    birthday = year + "-" + month + "-" + day;
  } else if (idCard.length === 18) {
    var year = idCard.substring(6, 10);
    var month = idCard.substring(10, 12);
    var day = idCard.substring(12, 14);
    birthday = year + "-" + month + "-" + day;
  }
  return birthday;
}

/**
 * 通过身份证号计算出性别
 */
loserStarJsUtils.parseIdCardToSex = function (idCard) {
  var sexCode;
  if (idCard.length === 15) {
    sexCode = idCard.substring(14, 15);
  } else if (idCard.length === 18) {
    sexCode = idCard.substring(16, 17);
  }
  return parseInt(sexCode) % 2 === 1 ? "男" : "女";
}

/**
 * 根据身份证号和一个当前日期计算出当前年龄
 * @param {*} idCard 身份证号
 * @param {*} date 当前日期
 */
loserStarJsUtils.parseIdCardToAage = function (idCard, now) {
  var birthdayDate = new Date(loserStarJsUtils.parseIdCardToBirthday(idCard));
  var age = now.getFullYear() - birthdayDate.getFullYear();
  var monthDiff = now.getMonth() - birthdayDate.getMonth();
  if (monthDiff < 0 || (monthDiff === 0 && now.getDate() < birthdayDate.getDate())) {
    age--;
  }
  return age;
}

/**
 * 验证手机号码格式是否有效
 * @param {string} phone - 手机号码
 * @returns {boolean} - 如果有效则返回true，否则返回false
 */
loserStarJsUtils.validatePhoneNumber = function (phone) {
  var regex = /^1[3456789]\d{9}$/; // 中国大陆手机号码格式
  return regex.test(phone);
}

/**
 * 校验email格式
 * @param {*} email 
 * @returns 正确返回true，错误返回false
 */
loserStarJsUtils.validateEmail = function(email) {
  var regex = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
  return regex.test(email);
}


/**
 * 移除输入框中的空格
 * 
 * @param {Event} event - 事件对象
 */
loserStarJsUtils.removeSpaces = function(event) {
  var input = event.target;
  input.value = input.value.replace(/\s/g, '');
}

/**
 * form下载
 * @param url
 * @param params
 */
loserStarJsUtils.downloadFile = function (url, params) {
  var form = document.createElement('form');
  form.id = new Date().getTime();
  form.action = url
  form.method = 'post'
  form.style.display = 'none'
  form.target = '_blank'
  for (var key in params) {
    if (params.hasOwnProperty(key)) {
      var input = document.createElement('input')
      input.name = key
      input.value = params[key]?params[key]:''
      form.appendChild(input) }
  }
  document.body.appendChild(form);
  form.submit()
}