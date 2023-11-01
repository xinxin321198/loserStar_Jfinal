
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
        var dict_id = sessionStorage.getItem("dict_id");
        if(dict_id!=null&&dict_id!=undefined&&dict_id!=''){
            $("#dict_id").val(dict_id);
        }
        var dict_value = sessionStorage.getItem("dict_value");
        if(dict_value!=null&&dict_value!=undefined&&dict_value!=''){
            $("#dict_value").val(dict_value);
        }
        var dict_name = sessionStorage.getItem("dict_name");
        if(dict_name!=null&&dict_name!=undefined&&dict_name!=''){
            $("#dict_name").val(dict_name);
        }
        var dict_type = sessionStorage.getItem("dict_type");
        if(dict_type!=null&&dict_type!=undefined&&dict_type!=''){
            $("#dict_type").val(dict_type);
        }
        var dict_remarks = sessionStorage.getItem("dict_remarks");
        if(dict_remarks!=null&&dict_remarks!=undefined&&dict_remarks!=''){
            $("#dict_remarks").val(dict_remarks);
        }
        var dict_c_name = sessionStorage.getItem("dict_c_name");
        if(dict_c_name!=null&&dict_c_name!=undefined&&dict_c_name!=''){
            $("#dict_c_name").val(dict_c_name);
        }
        var dict_css_style = sessionStorage.getItem("dict_css_style");
        if(dict_css_style!=null&&dict_css_style!=undefined&&dict_css_style!=''){
            $("#dict_css_style").val(dict_css_style);
        }
        var dict_sort = sessionStorage.getItem("dict_sort");
        if(dict_sort!=null&&dict_sort!=undefined&&dict_sort!=''){
            $("#dict_sort").val(dict_sort);
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
}