var formPageEvent = {}

/**
 * 保存
 */
formPageEvent.save = function () {
    var saveData = {};
    saveData.id = $("#id").val();
    saveData.name = $("#name").val();
    saveData.path = $("#path").val();
    saveData.upload_time = $("#upload_time").val();
    saveData.sort = $("#sort").val();
    saveData.del = $("#del").val();
    saveData.create_time = $("#create_time").val();
    saveData.create_user_code = $("#create_user_code").val();
    saveData.create_user_name = $("#create_user_name").val();
    saveData.update_time = $("#update_time").val();
    saveData.update_user_code = $("#update_user_code").val();
    saveData.update_user_name = $("#update_user_name").val();
    saveData.suffix = $("#suffix").val();
    saveData.from_id = $("#from_id").val();
    saveData.from_table = $("#from_table").val();
    //校验值
    if (!loserStarJsUtils.checkObj(saveData.id)){
        loserStarSweetAlertUtils.alertWarning("请填写“主键id”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.name)){
        loserStarSweetAlertUtils.alertWarning("请填写“名称”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.path)){
        loserStarSweetAlertUtils.alertWarning("请填写“文件存储路径”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.upload_time)){
        loserStarSweetAlertUtils.alertWarning("请填写“上传日期”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.sort)){
        loserStarSweetAlertUtils.alertWarning("请填写“排序”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.del)){
        loserStarSweetAlertUtils.alertWarning("请填写“软删除字段”","",function(){});
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
    if (!loserStarJsUtils.checkObj(saveData.suffix)){
        loserStarSweetAlertUtils.alertWarning("请填写“文件后缀”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.from_id)){
        loserStarSweetAlertUtils.alertWarning("请填写“附件所属记录id”","",function(){});
        return;
    }
    if (!loserStarJsUtils.checkObj(saveData.from_table)){
        loserStarSweetAlertUtils.alertWarning("请填写“附件所属记录的表”","",function(){});
        return;
    }

    //保存
    sysFileAction.save(saveData, function (data, msg, result) {
        loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
            $("#id").val(data.id);
            initData();
        });
    }, function (data, msg, result) {
        loserStarSweetAlertUtils.alertError(msg, "", function () { });
    });
}