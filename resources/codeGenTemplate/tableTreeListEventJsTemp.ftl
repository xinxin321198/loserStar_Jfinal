<#setting classic_compatible=true>
var table;
<#--查询table表格的js代码(适配datatables) -->
var listPageEvent = {}
/**
 * 查询数据并渲染出表格（附带初始化datatables）
 */
listPageEvent.queryList = function () {
    //构建查询条件
    var queryObj = {};
    ${fristLowerClassName}Action.getListData(queryObj, function (data) {
        //绘制表头和表尾
        var thead = "";
        thead += "                            <tr>";
        thead += "                                <th>#</th>";
        <#list fieldList as field>
        thead += "                                <th>${field.remarks}</th>";
        </#list>
        thead += "                                <th>操作</th>";
        thead += "                            </tr>";

        //绘制表体
        var text = "";
        for (var i = 0; i < data.length; i++) {
            var tmp = data[i];
            text += "                        <tr id=\""+ tmp.${primaryKey}+ "\" data-id=\""+ tmp.${primaryKey}+ "\" data-pid=\""+ tmp.p_id+ "\">";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_index\">" + (i + 1) + "</td>";
            <#list fieldList as field>
            text += "                            <td id=\"" + tmp.${primaryKey} + "_${field.name}\">" + tmp.${field.name} + "</td>";
            </#list>
            text += "                            <td>";
            <#if listBtnStyle=='all'||listBtnStyle=='original'>
            <#--  生成原始按钮  -->
            text += "                            <div class=\"btn-group\">";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\">编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"${fristLowerClassName}ListEvent.del('tmp.${primaryKey}')\">删除</button>";
            text += "                            </div>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='icon'>
            <#--  生成图标按钮  -->
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"${fristLowerClassName}ListEvent.del('tmp.${primaryKey}')\"><i class=\"fa fa-trash\"></i>删除</button>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='dropdown'>
            <#--  生成下拉按钮  -->
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\" style=\"padding:revert\"></span>";
            text += "                                   </button>";
            text += "                                   <ul class=\"dropdown-menu\">";
            text += "                                       <li><a href=\"javascript:window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-green\">查看</a></li>";
            text += "                                       <li><a href=\"javascript:window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\" class=\"text-light-blue\">编辑</a></li>";
            text += "                                       <li class=\"divider\"></li>";
            text += "                                       <li><a href=\"javascript:listPageEvent.del('"+ tmp.${primaryKey} +"')\" class=\"text-red\">删除</a></li>";
            text += "                                   </ul>";
            text += "                               </div>";
            </#if>
            text += "                            </td>";
            text += "                        </tr>";
        }
        $("#dataList_tbody").html(text);
        //再添加dom
        $("#dataList_tbody").html(text);
        $("#dataList_thead").html(thead);
        $("#dataList_tfoot").html(thead);
        //最后初始化tTreeTables
        initTreeTables();
    });
}


/**
 * 初始化datatables，该方法得放在渲染html的dom数据之前，否则刷新数据会有问题
 * 具体配置参考 http://datatables.club/reference/option/
 */
function initTreeTables() {
    table = $("#${tableName}_table").treetable({
        //树是否可以展开收缩
        expandable: true,
        //点击节点名称也可以展开
        clickableNodeNames: true,
        //用做树节点的列序号
        column: 0,
        //树的单元格类型td th
        columnElType: 'td',
        //可展开的节点的前置html
        // expanderTemplate: '<i class="fa fa-folder-open" style="color:#db9a34"></i>',
        //缩进
        indent: 19,
        //叶子节点的前置html
        // indenterTemplate: '<span class="indenter" style="text-align: center;"></span>',
        //初始的形式展开全部还是收缩："expanded" or "collapsed".
        initialState: 'collapsed',
        // 用于标识节点的数据属性名称。在HTML中转换为data-tt-id。
        nodeIdAttr: 'id',
        //用于设置父节点的数据属性的名称。在HTML中转换为data-tt-parent-id
        parentIdAttr: 'pid',
    }, true).find("tr").on("click", function () {
        //选中的事件
        //添加选中效果
        $("tr.selected").removeClass("selected");
        //处理展开收缩图标
        $(this).toggleClass("selected");
        //选中一个才可添加下级
        //---------业务相关--------------------

    });
}

/**
 * 全部收缩
 */
listPageEvent.collapseAll = function () {
    $("#${tableName}_table").treetable("collapseAll");
}
/**
 * 全部展开
 */
listPageEvent.expandAll = function () {
    $("#${tableName}_table").treetable("expandAll");
}

/**
 * 刷新列表
 */
listPageEvent.refresh = function () {
    listPageEvent.queryList();
}

/**
 * 新增一个顶层节点
 */
listPageEvent.addTopNode = function () {
    window.open("formPage.do?p_id=0", "_self");
}



/**
 * 新增一个下级节点
 * @param {*} p_id 当前的下级节点要添加在哪个父节点id下
 */
listPageEvent.addSubNode = function () {
    var selectedList = $("#${tableName}_table tr.selected");
    if (selectedList.length <= 0) {
        loserStarSweetAlertUtils.alertError("请选中一个节点再进行添加下级节点");
        return;
    }
    var selectedNode = $(selectedList[0]);
    var selectedId = selectedNode.attr("data-id");
    window.open("formPage.do?p_id=" + selectedId, "_self");
}


/**
 * 更新del字段
 * @param {*} del 
 */
listPageEvent.del = function (${primaryKey}){
    var title = "是否删除" + ${primaryKey};
    loserStarSweetAlertUtils.confirm(title,"",function(){
        ${fristLowerClassName}Action.delById(${primaryKey},function(data,msg,result){
            loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
                listPageEvent.queryList();
            });
        }, function (data, msg, result){
            loserStarSweetAlertUtils.alertError(msg,"",function(){
            });
        });
    });
}