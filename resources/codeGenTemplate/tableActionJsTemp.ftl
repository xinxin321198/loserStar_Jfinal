<#setting classic_compatible=true>

<#--  前端CURD代码  -->
var ${fristLowerClassName}Action = {}
${fristLowerClassName}Action.getPage = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../${fristLowerClassName}/getPageData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

${fristLowerClassName}Action.getListData = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../${fristLowerClassName}/getListData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

${fristLowerClassName}Action.save = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postJson("../${fristLowerClassName}/save.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

${fristLowerClassName}Action.getById = function (${primaryKey}, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../${fristLowerClassName}/getById.do?${primaryKey}=" + ${primaryKey}, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}
${fristLowerClassName}Action.delById = function (${primaryKey}, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../${fristLowerClassName}/delById.do?${primaryKey}=" + ${primaryKey}, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}