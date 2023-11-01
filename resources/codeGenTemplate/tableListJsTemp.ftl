<#setting classic_compatible=true>

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
    <#list fieldList as field>
        var ${field.name} = sessionStorage.getItem("${field.name}");
        if(${field.name}!=null&&${field.name}!=undefined&&${field.name}!=''){
            $("#${field.name}").val(${field.name});
        }
    </#list>
}