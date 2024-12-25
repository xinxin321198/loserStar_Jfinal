/**
 * version:20240204
 * author：loserStar
 * github:https://github.com/xinxin321198/loserStarJsUtils
 * emial:362527240@qq.com
 * 这是bootstrap3的一些相关方法，兼容部分adminLTE2.x的东西
 */
var loserStarBoostrapUtils = {};
/**
 * 通过bootstrap的模态框 modal.js组件封装实现的一个loading方法
 * @param {*} title 标题
 * @param {*} content 内容
 */
loserStarBoostrapUtils.loading = function (title, content) {
    if (undefined == title || null == title || "" == title) {
        title = "提示";//默认提示语
    }
    if (undefined == content || null == content || "" == content) {
        title = "处理中，请勿关闭页面...";//默认提示语
    }
    if ($("#loserStarBoostrapUtils_loadingDlg")[0] == undefined || $("#loserStarBoostrapUtils_loadingDlg")[0] == null) {
        //如果元素不存在就创建
        var text = "";
        text += "<div class=\"modal fade\" id=\"loserStarBoostrapUtils_loadingDlg\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\">";
        text += "    <div class=\"modal-dialog modal-sm\" role=\"document\">";
        text += "        <div class=\"modal-content\">";
        text += "            <div class=\"modal-header\">";
        text += "                <h4 class=\"modal-title\" id=\"myModalLabel\">" + title +"</h4>";
        text += "            </div>";
        text += "            <div class=\"modal-body\">";
        text += "                "+title;
        text += "            </div>";
        text += "        </div>";
        text += "    </div>";
        text += "</div>";
        $("body").append(text);
    }
    $('#loserStarBoostrapUtils_loadingDlg').modal({
        keyboard: false,//按下esc键不会被关闭  
        backdrop: 'static'//true:有遮罩，单击关闭，false:无遮罩,单击不会关闭，'static':有遮罩，单击不会关闭
    });
}

/**
 * 关闭加载对话框
 */
loserStarBoostrapUtils.closeLoading = function () {
    $('#loserStarBoostrapUtils_loadingDlg').modal("hide");
}


/**
 * 给loading对话框设置事件回调方法（具体参考bootstrap3官网modal.js组件的事件）
 * @param {*} eventType 事件名称（参考https://getbootstrap.com/docs/3.4/javascript/#modals）
 * @param {*} callBack 回调方法
 */
loserStarBoostrapUtils.setLoadingCallback = function (eventType, callBack) {
    $('#loserStarBoostrapUtils_loadingDlg').on(eventType, function (e) {
        if (callBack) {
            callBack(e);
        }
    });
}

/**
 * 获取一个进度条html
 * @param {*} scale 小数，最大1
 * @param {*} pointCount 小数点位数（默认值是2，即保留小数点后2位）
 */
loserStarBoostrapUtils.getProgressHtml = function (scale, pointCount) {
    var votingProportionHtml = "";
    var votingProportion = scale * 100;
    var votingProportionClass = "";
    if (votingProportion >= 75) {
        votingProportionClass = "progress-bar-success";
    } else if (votingProportion >= 50) {
        votingProportionClass = "progress-bar-info";
    } else if (votingProportion >= 25) {
        votingProportionClass = "progress-bar-warning";
    } else {
        votingProportionClass = "progress-bar-danger";
    }

    if (pointCount) {
        votingProportion = votingProportion.toFixed(pointCount);
    } else {
        votingProportion = votingProportion.toFixed(2);
    }

    votingProportionHtml += "<div class=\"progress \">";
    votingProportionHtml += "  <div class=\"progress-bar " + votingProportionClass + " progress-bar-striped active\" role=\"progressbar \" aria-valuenow=\"" + votingProportion + "\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"min-width:2em;width: " + votingProportion + "%;\">";
    votingProportionHtml += "    " + votingProportion + "%";
    votingProportionHtml += "  </div>";
    votingProportionHtml += "</div>";
    return votingProportionHtml;
}


/**
 * 生成下拉列表的html（已知完美兼容select2）
 * @param {*} objList array 可选的值数组合集，通过keyFieldName和valueFieldname指定使用数组中的那些字段
 * @param {*} isEmpty bool 是否添加一个空元素
 * @param {*} textFieldName stirng 用作从objList中取值时的key，获取到的值将作为option的选项名称
 * @param {*} valueFieldName stirng 用作从objList中取值时的key，获取到的值将作为option的值
 * @returns 
 */
loserStarBoostrapUtils.getSelectHtml = function (objList, isEmpty, textFieldName, valueFieldName) {
    var text = "";
    if (isEmpty != undefined && isEmpty != null && (typeof isEmpty) == "boolean" && isEmpty) {
        text += "<option value=\"\"></option>";
    }
    for (var i = 0; i < objList.length; i++) {
        var tmp = objList[i];
        text += "<option value=\"" + tmp[valueFieldName] + "\">" + tmp[textFieldName] + "</option>";
    }
    return text;
}

/**
 * 生成radio组的html
 * @param {*} objList array 可选的值数组合集，通过keyFieldName和valueFieldname指定使用数组中的那些字段
 * @param {*} groupId 分组的id，也就相当于name，同一组的不能重复选择
 * @param {*} keyFieldName stirng 用作从objList中取值时的字段名，用作radio的名称
 * @param {*} valueFieldname stirng 用作从objList中取值时的字段名，用作radio的value
 * @returns 
 */
loserStarBoostrapUtils.getRadioHtml = function (objList, groupId, valueFieldName, keyFieldName) {
    var text = "";
    for (var i = 0; i < objList.length; i++) {
        var tmp = objList[i];
        text += "<div class=\"radio\">";
        text += "                                                            <label>";
        text += "                                                                <input type=\"radio\" name=\"" + groupId + "\"  value=\"" + tmp[valueFieldName] + "\">";
        text += "                                                                " + tmp[keyFieldName];
        text += "                                                            </label>";
        text += "                                                        </div>";
    }
    return text;
}

/**
 * 基于bootstrap的modal的弹窗消息，里面放了一个textarea，方便复制消息内容
 * @param {*} title 
 * @param {*} msg 
 */
loserStarBoostrapUtils.alertMsg = function(title,msg){
    var text = "";
    text += "<div class=\"modal fade\" id=\"loserStarAlertMsgModal\" tabindex=\"-1\" role=\"dialog\">";
    text += "            <div class=\"modal-dialog\" role=\"document\">";
    text += "                <div class=\"modal-content\">";
    text += "                    <div class=\"modal-header\">";
    text += "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span";
    text += "                                aria-hidden=\"true\">&times;</span></button>";
    text += "                        <h4 class=\"modal-title\">" + title +"</h4>";
    text += "                    </div>";
    text += "                    <div class=\"modal-body\">";
    text += "                        <label for=\"loserStarAlertMsgTextArea\">消息</label>";
    text += "                        <textarea id=\"loserStarAlertMsgTextArea\" cols=\"40\" rows=\"20\"  class=\"form-control\">";
    text += msg;
    text += "                        </textarea>";
    text += "                    </div>";
    text += "                    <div class=\"modal-footer\">";
    text += "                        <button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\">关闭</button>";
    text += "                    </div>";
    text += "                </div>";
    text += "            </div>";
    text += "        </div>";
    if ($("#loserStarAlertMsgModal")[0] == undefined || $("#loserStarAlertMsgModal")[0] == null) {
        $("body").append(text);
    }
    $("#loserStarAlertMsgModal").modal({ backdrop: 'static' });
}