/**
 * version:20181210
 * author：loserStar
 * github:https://github.com/xinxin321198/loserStarJsUtils
 * emial:362527240@qq.com
 */
var loserStarJsUtils = {};

/**
 * 检查IE浏览器版本
 * @returns
 */
loserStarJsUtils.IEVersion = function() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }   
    } else if(isEdge) {
        return 'edge';//edge
    } else if(isIE11) {
        return 11; //IE11  
    }else{
        return -1;//不是ie浏览器
    }
}

/**
 * 检查浏览器版本
 * @returns
 */
loserStarJsUtils.BorwserVersion = function(){
	var isIE = IEVersion();
	if(isIE==-1){//非IE
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
		var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器  
		var isFF = userAgent.indexOf("Firefox") > -1; //判断是否Firefox浏览器  
		var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") == -1; //判断是否Safari浏览器  
		var isChrome = userAgent.indexOf("Chrome") > -1 && userAgent.indexOf("Safari") > -1; //判断Chrome浏览器  
		if (isFF) {  return "FF";}  
		if (isOpera) {  return "Opera";}  
		if (isSafari) {  return "Safari";}  
		if (isChrome) { return "Chrome";}  
		if (isEdge) { return "Edge";} 
	}else{
		return isIE;
	}
}

/**
 * crhome6以上的版本
 */
loserStarJsUtils.Chrome6NewVersion = function(){
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
loserStarJsUtils.checkBrowserType =function(){
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
  loserStarJsUtils.getChromeVersion = function(){
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
   * flash是否打开
   */
  loserStarJsUtils.isFlashOpen = function(){
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
  if(rtn==false){
       alert("您未开启Flash！");
    }else{ }
}

/**
 * 得到鼠标坐标
 */
loserStarJsUtils.getMousePos = function(event) {
    var e = event || window.event;
    var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var x = e.pageX || e.clientX + scrollX;
    var y = e.pageY || e.clientY + scrollY;
    //alert('x: ' + x + '\ny: ' + y);
    return { 'x': x, 'y': y };
}

/**
 * 检测数据是否有值，有返回true，没有返回false
 * @param {*} o 
 */
loserStarJsUtils.checkObj = function(o){
  var flag = true;
  if(o ==undefined||o==null){
      flag = false;
  }else{
      if(o instanceof String){
          if(o==''){
              flag = false;
          }
      }else if(o instanceof Array){
          if(o.length<=0){
              flag = false;
          }
      }
  }
  return flag;
}

/**
 * 替换所有匹配的字符串
 */
loserStarJsUtils.replaceAll = function(sourceStr,search, replacement) {
    return sourceStr.replace(new RegExp(search, 'g'), replacement);
};

/**
 * 把某个checkbox里选中的值以逗号分隔放入第二个参数的元素的value上
 * @param checkBoxName
 * @param strSelector
 * @returns
 */
loserStarJsUtils.checkedBoxToStr = function(checkBoxSelector, strSelector) {
    var accessoryTypeList = $(checkBoxSelector);
    var attachedListStr = "";
    accessoryTypeList.each(function() {
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
loserStarJsUtils.strToCheckedBox = function(checkBoxSelector, strSelector) {
    var accessoryTypeList = $(strSelector).val();
    var checkBoxList = $(checkBoxSelector);
    var attachedArray = accessoryTypeList.split(",");
    for (var i = 0; i < attachedArray.length; i++) {
        checkBoxList.each(function() {
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
loserStarJsUtils.hideShowTab = function(selector, checkBoxSelector) {
    //初始化Nav tabs样式，全隐藏
    $(selector).attr("class", "hidden");
    $("#Nav0").attr("class", "hidden");
    $("#Nav1").attr("class", "hidden");
    $("#Nav2").attr("class", "hidden");
    $(checkBoxSelector + ":checked").each(function() {
        var tabIndex = $(this).val();
        $(selector).removeClass("hidden"); //取消整个navList隐藏
        $("#Nav" + tabIndex).removeClass("hidden"); //根据勾选来决定是否显示某个nav
        $("#tabList #Nav" + tabIndex + " a").tab("show"); //插件命令，出发点击某个nav的超链接，显示实际内容
    });
};

//勾选副表方法定义
loserStarJsUtils.checkedAccessoryType = function() {
    checkedBoxToStr("input.accessoryType", "#attachedList"); //勾选checkbox，然后把值以逗号分隔放在某个元素的value上
    hideShowTab("#fileNavList", "input.accessoryType");
};

/**
 * 让select元素中的某个option被选中
 *  @param selector  select元素的选择器
 * @param value 需要选中的option的值
 * @returns
 */
loserStarJsUtils.selectedOption = function(selector, value) {
    var yearSelect = $(selector)[0];
    for (var i = 0; i < yearSelect.options.length; i++) {
        var tmp = yearSelect.options[i].value;
        if (tmp == value) {
            yearSelect.options[i].selected = "selected";
            break;
        }
    }
};

/**
 * 日期格式化
 */
loserStarJsUtils.dateFormat = function(dateString, format) {
	  if (dateString==undefined||dateString==null) return "";
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

loserStarJsUtils.formatCurrency = function(num) {
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
loserStarJsUtils.setSelectedForSelect = function (selector,value) {
    $(selector).find("[value="+value+"]").attr("selected",true);
}

/**
 * 获取某个radio元素的选中的值
 * @param {*} elementName name名称
 */
loserStarJsUtils.getSelectedForRadio = function(elementName){
  return $("input[name='"+elementName+"']:checked").val();
}

/**
 * 让某个radio元素的值选中，基于jquery
 * @param {*} elementName radio的name
 * @param {*} value 
 */
loserStarJsUtils.setSelectedForRadio = function(elementName,value){
	$("input[name='"+elementName+"'][value="+value+"]").attr("checked",true); 
}

/**
 * 让某个radio元素的值选中，针对jquery mobile的组件的方法,因为jqueryMobile需要调用一个刷新方法
 */
loserStarJsUtils.setSelectedForRadio_forJqueryMobile = function(name,value){
	$("input[name='"+name+"'][value='"+value+"'] ").attr("checked",true).checkboxradio("refresh");
}

loserStarJsUtils.disabledForRadio = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true);    ;
  });
}

loserStarJsUtils.enabledForRadio = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false);
  });
}
loserStarJsUtils.disabledForRadio_forJqueryMobile = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true).checkboxradio( "refresh" );    ;
  });
}

loserStarJsUtils.enabledForRadio_forJqueryMobile = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false).checkboxradio( "refresh" );
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
loserStarJsUtils.getSelectedValueForSelect1 = function (selector){
	return $(selector).find("option:selected").val();
}
/**
 * 得到seelct元素选中项的value(第二种方式，基于jquery自动取值的)
 * @param {*} selector 
 */
loserStarJsUtils.getSelectedValueForSelect2 = function (selector){
   return $(selector).val();
}



/**
 * textarea自动高度
 */
loserStarJsUtils.autoTextAreaHeight = function (o) {
    o.style.height = (o.scrollTop + o.scrollHeight +2)+ "px";
}

/**
 * 给input date一个默认值
 */
loserStarJsUtils.setInputDateDefault = function(selector){
    // 给input  date设置默认值
    var now = new Date();
    //格式化日，如果小于9，前面补0
    var day = ("0" + now.getDate()).slice(-2);
    //格式化月，如果小于9，前面补0
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    //拼装完整日期格式
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
    //完成赋值
    $(selector).val(today);
}

/**
 * 弹出提示框
 */
loserStarJsUtils.showMsg = function(text, position) {
    var div = $('<div></div>');
    // div.addClass('show_msg');
    // .show_msg {
    // 	width:100%;
    // 	text-align:center;
    // 	position:fixed;
    // 	left:0;
    // 	z-index:999;
    // }
    div.css("width","100%");
    div.css("text-align","center");
    div.css("position","fixed");
    div.css("left","0");
    div.css("z-index","999");

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
    span.css("display","inline-block");
    span.css("padding","10px 20px");
    span.css("line-height","0.35rem");
    span.css("background","rgba(0,0,0,0.8)");
    span.css("border-radius","50px");
    span.css("color","#fff");
    span.css("font-size","20px");
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
loserStarJsUtils.GetQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg); //search,查询？后面的参数，并匹配正则
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * 获取选中的checkbox的值,基于jquery选择器,传入元素的name
 */
loserStarJsUtils.getSelectedForCheckbox = function(elementName){
  var selectedList = [];
  for(var i=0;i<$("input[name='"+elementName+"']:checked").length;i++){
    selectedList[i]= $("input[name='"+elementName+"']:checked")[i].value;
  }
  return selectedList;
}

/**
 * 获取选中的checkbox的值,以某个字符分隔，传入元素的name
 */
loserStarJsUtils.getSelectedStrForCheckbox = function(elementName,splitChar){
    var selectedList = loserStarJsUtils.getSelectedForCheckbox(elementName);
    var str = "";
    for(var i=0;i<selectedList.length;i++){
      str+=selectedList[i];
      if(i<(selectedList.length-1)){
      str+=splitChar;
      }
    }
    return str;
}

/**
 * 传入想要选中的值的集合，让这些值相等的checkbox选中
 * @param {*} elmentName 元素的name
 * @param {*} list 要选中的值的列表
 */
loserStarJsUtils.setSelectedForCheckbox = function(elmentName,valueList){
  var checkBoxList = $("input[name='"+elmentName+"']");
    for (var i = 0; i < valueList.length; i++) {
        checkBoxList.each(function() {
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
loserStarJsUtils.setSelectedForCheckbox_forJqueryMobile = function(elmentName,valueList){
  loserStarJsUtils.setSelectedForCheckbox(elmentName,valueList);
  $("input[name='"+elmentName+"']").checkboxradio("refresh");
}

loserStarJsUtils.disabledForCheckbox = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true);    ;
  });
}

loserStarJsUtils.enabledForCheckbox = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false);
  });
}
loserStarJsUtils.disabledForCheckbox_forJqueryMobile = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", true).checkboxradio( "refresh" );    ;
  });
}

loserStarJsUtils.enabledForCheckbox_forJqueryMobile = function(name){
  $("input[name=" + name + "]").each(function () {
    $(this).attr("disabled", false).checkboxradio( "refresh" );
  });
}



loserStarJsUtils.copyObj = function(o){
	return JSON.parse(JSON.stringify(o));
}

