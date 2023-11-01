var sysUserAction = {}
sysUserAction.getPage = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysUser/getPageData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysUserAction.getListData = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysUser/getListData.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysUserAction.save = function (para, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postJson("../sysUser/save.do", para, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}

sysUserAction.getById = function (id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysUser/getById.do?id=" + id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}
sysUserAction.delById = function (id, callback_ok, callback_error) {
    loserStarBoostrapUtils.loading();//遮罩
    postObj("../sysUser/delById.do?id=" + id, null, "json", function (result) {
        loserStarBoostrapUtils.closeLoading();
        returnAsyncData(result, callback_ok, callback_error);
    }, true);
}