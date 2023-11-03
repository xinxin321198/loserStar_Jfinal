
var dataPage = {};
$(function(){
    //使用page需要引入loserStarPage1.1.js组件，否则如果用其它方式则移除该部分代码--------begin
    var pageCfg = {
        pageId:"userPage",
        gotoCusPageCallback: listPageEvent.queryList,
        gotoPreviousPageCallback: listPageEvent.queryList,
        gotoNextPageCallback: listPageEvent.queryList,
    }
    dataPage = new loserStarPage(pageCfg);
    // 这两个顺序不能放前面
    initQueryParam();
    listPageEvent.queryPageList();
    //使用page需要引入loserStarPage1.1.js组件，否则如果用其它方式则移除该部分代码--------end
});

/**
 * 清空查询条件（适配原生的查询条件字段方式）
 */
function initQueryParam(){
        var id = sessionStorage.getItem("id");
        if(id!=null&&id!=undefined&&id!=''){
            $("#id").val(id);
        }
        var name = sessionStorage.getItem("name");
        if(name!=null&&name!=undefined&&name!=''){
            $("#name").val(name);
        }
        var path = sessionStorage.getItem("path");
        if(path!=null&&path!=undefined&&path!=''){
            $("#path").val(path);
        }
        var upload_time = sessionStorage.getItem("upload_time");
        if(upload_time!=null&&upload_time!=undefined&&upload_time!=''){
            $("#upload_time").val(upload_time);
        }
        var sort = sessionStorage.getItem("sort");
        if(sort!=null&&sort!=undefined&&sort!=''){
            $("#sort").val(sort);
        }
        var del = sessionStorage.getItem("del");
        if(del!=null&&del!=undefined&&del!=''){
            $("#del").val(del);
        }
        var create_time = sessionStorage.getItem("create_time");
        if(create_time!=null&&create_time!=undefined&&create_time!=''){
            $("#create_time").val(create_time);
        }
        var create_user_code = sessionStorage.getItem("create_user_code");
        if(create_user_code!=null&&create_user_code!=undefined&&create_user_code!=''){
            $("#create_user_code").val(create_user_code);
        }
        var create_user_name = sessionStorage.getItem("create_user_name");
        if(create_user_name!=null&&create_user_name!=undefined&&create_user_name!=''){
            $("#create_user_name").val(create_user_name);
        }
        var update_time = sessionStorage.getItem("update_time");
        if(update_time!=null&&update_time!=undefined&&update_time!=''){
            $("#update_time").val(update_time);
        }
        var update_user_code = sessionStorage.getItem("update_user_code");
        if(update_user_code!=null&&update_user_code!=undefined&&update_user_code!=''){
            $("#update_user_code").val(update_user_code);
        }
        var update_user_name = sessionStorage.getItem("update_user_name");
        if(update_user_name!=null&&update_user_name!=undefined&&update_user_name!=''){
            $("#update_user_name").val(update_user_name);
        }
        var suffix = sessionStorage.getItem("suffix");
        if(suffix!=null&&suffix!=undefined&&suffix!=''){
            $("#suffix").val(suffix);
        }
        var from_id = sessionStorage.getItem("from_id");
        if(from_id!=null&&from_id!=undefined&&from_id!=''){
            $("#from_id").val(from_id);
        }
        var from_table = sessionStorage.getItem("from_table");
        if(from_table!=null&&from_table!=undefined&&from_table!=''){
            $("#from_table").val(from_table);
        }
}