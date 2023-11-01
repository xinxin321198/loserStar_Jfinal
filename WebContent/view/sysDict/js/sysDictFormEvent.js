var formPageEvent = {}

/**
 * 保存
 */
formPageEvent.save = function () {
    var saveData = {};
    saveData.dict_id = $("#dict_id").val();
    saveData.dict_value = $("#dict_value").val();
    saveData.dict_name = $("#dict_name").val();
    saveData.dict_type = $("#dict_type").val();
    saveData.dict_remarks = $("#dict_remarks").val();
    saveData.dict_c_name = $("#dict_c_name").val();
    saveData.dict_css_style = $("#dict_css_style").val();
    saveData.dict_sort = $("#dict_sort").val();
    saveData.del = $("#del").val();
    saveData.create_time = $("#create_time").val();
    saveData.create_user_code = $("#create_user_code").val();
    saveData.create_user_name = $("#create_user_name").val();
    saveData.update_time = $("#update_time").val();
    saveData.update_user_code = $("#update_user_code").val();
    saveData.update_user_name = $("#update_user_name").val();
    //校验值
    if (!loserStarJsUtils.checkObj(saveData.dict_id)){
        loserStarSweetAlertUtils.alertWarning("请填写“主键”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_value)){
        loserStarSweetAlertUtils.alertWarning("请填写“值”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“名称”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_type)){
        loserStarSweetAlertUtils.alertWarning("请填写“类型”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_remarks)){
        loserStarSweetAlertUtils.alertWarning("请填写“备注”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_c_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“java后端使用的常量名称”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_css_style)){
        loserStarSweetAlertUtils.alertWarning("请填写“前端附加的样式”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.dict_sort)){
        loserStarSweetAlertUtils.alertWarning("请填写“排序码”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.del)){
        loserStarSweetAlertUtils.alertWarning("请填写“删除标志”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_time)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建时间”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_user_code)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建人编号”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.create_user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“创建人姓名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_time)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新时间”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_user_code)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新人编号”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.update_user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“更新人姓名”","",function(){});
        return;
    }

    //保存
    sysDictAction.save(saveData, function (data, msg, result) {
        loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
            $("#dict_id").val(data.dict_id);
            initData();
        });
    }, function (data, msg, result) {
        loserStarSweetAlertUtils.alertError(msg, "", function () { });
    });
}