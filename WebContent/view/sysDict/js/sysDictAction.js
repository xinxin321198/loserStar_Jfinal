var sysDictAction = {}
sysDictAction.getPage = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysDict/getPageData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysDictAction.getListData = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysDict/getListData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysDictAction.save = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postJson("../sysDict/save.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysDictAction.getById = function (dict_id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysDict/getById.do?dict_id=" + dict_id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}
sysDictAction.delById = function (dict_id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysDict/delById.do?dict_id=" + dict_id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}