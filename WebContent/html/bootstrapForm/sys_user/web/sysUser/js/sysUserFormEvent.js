var formPageEvent = {}

/**
 * 保存
 */
formPageEvent.save = function () {
    var saveData = {};
    saveData.id = $("#id").val();
    saveData.user_name = $("#user_name").val();
    saveData.password = $("#password").val();
    saveData.pwd_err_count = $("#pwd_err_count").val();
    saveData.del = $("#del").val();
    saveData.create_time = $("#create_time").val();
    saveData.create_user_code = $("#create_user_code").val();
    saveData.create_user_name = $("#create_user_name").val();
    saveData.update_time = $("#update_time").val();
    saveData.update_user_code = $("#update_user_code").val();
    saveData.update_user_name = $("#update_user_name").val();
    //校验值
    if (!loserStarJsUtils.checkObj(saveData.id)){
        loserStarSweetAlertUtils.alertWarning("请填写“主键id，用作账号名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.user_name)){
        loserStarSweetAlertUtils.alertWarning("请填写“用户名”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.password)){
        loserStarSweetAlertUtils.alertWarning("请填写“密码”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.pwd_err_count)){
        loserStarSweetAlertUtils.alertWarning("请填写“密码输入错误次数”","",function(){});
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
    sysUserAction.save(saveData, function (data, msg, result) {
        loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
            $("#id").val(data.id);
            initData();
        });
    }, function (data, msg, result) {
        loserStarSweetAlertUtils.alertError(msg, "", function () { });
    });
}