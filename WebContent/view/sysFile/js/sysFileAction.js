var sysFileAction = {}
sysFileAction.getPage = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysFile/getPageData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysFileAction.getListData = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysFile/getListData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysFileAction.save = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postJson("../sysFile/save.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysFileAction.getById = function (id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysFile/getById.do?id=" + id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}
sysFileAction.delById = function (id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysFile/delById.do?id=" + id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}