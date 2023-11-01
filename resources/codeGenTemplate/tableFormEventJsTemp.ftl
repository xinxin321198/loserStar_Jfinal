<#setting classic_compatible=true>
var formPageEvent = {}

/**
 * 保存
 */
formPageEvent.save = function () {
    <#--  保存时的代码  -->
    var saveData = {};
    <#list fieldList as field>
    saveData.${field.name} = $("#${field.name}").val();
    </#list>
    //校验值
    <#list fieldList as field>
    if (!loserStarJsUtils.checkObj(saveData.${field.name})){
        loserStarSweetAlertUtils.alertWarning("请填写“${field.remarks}”","",function(){});
        return;
    }
    </#list>

    //保存
    ${fristLowerClassName}Action.save(saveData, function (data, msg, result) {
        loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
            $("#${primaryKey}").val(data.${primaryKey});
            initData();
        });
    }, function (data, msg, result) {
        loserStarSweetAlertUtils.alertError(msg, "", function () { });
    });
}