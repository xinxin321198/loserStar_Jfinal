var table;
var listPageEvent = {}
/**
 * 查询数据并渲染出表格（附带初始化datatables）
 */
listPageEvent.queryList = function () {
    //构建查询条件
    var queryObj = {};
    sysUserAction.getListData(queryObj, function (data) {
        //绘制表头和表尾
        var thead = "";
        thead += "                            <tr>";
        thead += "                                <th>#</th>";
        thead += "                                <th>主键id，用作账号名</th>";
        thead += "                                <th>用户名</th>";
        thead += "                                <th>密码</th>";
        thead += "                                <th>密码输入错误次数</th>";
        thead += "                                <th>删除标志</th>";
        thead += "                                <th>创建时间</th>";
        thead += "                                <th>创建人编号</th>";
        thead += "                                <th>创建人姓名</th>";
        thead += "                                <th>更新时间</th>";
        thead += "                                <th>更新人编号</th>";
        thead += "                                <th>更新人姓名</th>";
        thead += "                                <th>操作</th>";
        thead += "                            </tr>";

        //绘制表体
        var text = "";
        for (var i = 0; i < data.length; i++) {
            var tmp = data[i];
            text += "                        <tr id=\""+ tmp.id+ "\" data-id=\""+ tmp.id+ "\" data-pid=\""+ tmp.p_id+ "\">";
            text += "                            <td id=\"" + tmp.id + "_index\">" + (i + 1) + "</td>";
            text += "                            <td id=\"" + tmp.id + "_id\">" + tmp.id + "</td>";
            text += "                            <td id=\"" + tmp.id + "_user_name\">" + tmp.user_name + "</td>";
            text += "                            <td id=\"" + tmp.id + "_password\">" + tmp.password + "</td>";
            text += "                            <td id=\"" + tmp.id + "_pwd_err_count\">" + tmp.pwd_err_count + "</td>";
            text += "                            <td id=\"" + tmp.id + "_del\">" + tmp.del + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_time\">" + tmp.create_time + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_user_code\">" + tmp.create_user_code + "</td>";
            text += "                            <td id=\"" + tmp.id + "_create_user_name\">" + tmp.create_user_name + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_time\">" + tmp.update_time + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_user_code\">" + tmp.update_user_code + "</td>";
            text += "                            <td id=\"" + tmp.id + "_update_user_name\">" + tmp.update_user_name + "</td>";
            text += "                            <td>";
            text += "                            <div class=\"btn-group\">";
            text += "                            <button id=\""+ tmp.id + "_viewBtn\" type=\"button\" class=\"btn btn-success\" onclick=\"window.open('formPageView.do?id=" + tmp.id +"','_self')\">查看</button>";
            text += "                            <button id=\""+ tmp.id + "_editBtn\" type=\"button\" class=\"btn btn-primary\" onclick=\"window.open('formPage.do?id=" + tmp.id +"','_self')\">编辑</button>";
            text += "                            <button id=\""+ tmp.id + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"sysUserListEvent.del('tmp.id')\">删除</button>";
            text += "                            </div>";
            text += "                            <button id=\""+ tmp.id + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?id=" + tmp.id +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.id + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?id=" + tmp.id +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.id + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"sysUserListEvent.del('tmp.id')\"><i class=\"fa fa-trash\"></i>删除</button>";
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\" style=\"padding:revert\"></span>";
            text += "                                   </button>";
            text += "                                   <ul class=\"dropdown-menu\">";
            text += "                                       <li><a href=\"javascript:window.open('formPageView.do?id=" + tmp.id +"','_self')\" class=\"text-green\">查看</a></li>";
            text += "                                       <li><a href=\"javascript:window.open('formPage.do?id=" + tmp.id +"','_self')\" class=\"text-light-blue\">编辑</a></li>";
            text += "                                       <li class=\"divider\"></li>";
            text += "                                       <li><a href=\"javascript:listPageEvent.del('"+ tmp.id +"')\" class=\"text-red\">删除</a></li>";
            text += "                                   </ul>";
            text += "                               </div>";
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
    table = $("#sys_user_table").treetable({
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
    $("#sys_user_table").treetable("collapseAll");
}
/**
 * 全部展开
 */
listPageEvent.expandAll = function () {
    $("#sys_user_table").treetable("expandAll");
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
    var selectedList = $("#sys_user_table tr.selected");
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
listPageEvent.del = function (id){
    var title = "是否删除" + id;
    loserStarSweetAlertUtils.confirm(title,"",function(){
        sysUserAction.delById(id,function(data,msg,result){
            loserStarSweetAlertUtils.alertSuccess(msg, "", function () {
                listPageEvent.queryList();
            });
        }, function (data, msg, result){
            loserStarSweetAlertUtils.alertError(msg,"",function(){
            });
        });
    });
}