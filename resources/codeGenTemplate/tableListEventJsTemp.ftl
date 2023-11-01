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
            text += "                        <tr id=\""+ tmp.${primaryKey}+ "\">";
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
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\">删除</button>";
            text += "                            </div>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='icon'>
            <#--  生成图标按钮  -->
            text += "                            <button id=\""+ tmp.${primaryKey} + "_viewBtn\" type=\"button\" class=\"btn btn-success btn-app\" onclick=\"window.open('formPageView.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-eye\"></i>查看</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary btn-app\" onclick=\"window.open('formPage.do?${primaryKey}=" + tmp.${primaryKey} +"','_self')\"><i class=\"fa fa-edit\"></i>编辑</button>";
            text += "                            <button id=\""+ tmp.${primaryKey} + "_delBtn\" type=\"button\" class=\"btn btn-danger btn-app\" onclick=\"listPageEvent.del('"+ tmp.${primaryKey}+ "')\"><i class=\"fa fa-trash\"></i>删除</button>";
            </#if>
            <#if listBtnStyle=='all'||listBtnStyle=='dropdown'>
            <#--  生成下拉按钮  -->
            text += "                               <div class=\"btn-group\">";
            text += "                                   <button type=\"button\" class=\"btn btn-primary dropdown-toggle\" data-toggle=\"dropdown\">";
            text += "                                   <i class=\"fa fa-gears \"></i>";
            text += "                                   <span class=\"caret\"></span>";
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
        //先清空现有的datatables实例的对象
        if (table) {
            //如果已经有datatables的实例，先销毁实例再从新构建dom，再初始话datatables实例，否则刷新会失效，或者排序会自动清空数据等等
            // table.clear();
            table.destroy();//销毁数据对象
        }
        //再添加dom
        $("#dataList_tbody").html(text);
        $("#dataList_thead").html(thead);
        $("#dataList_tfoot").html(thead);
        //最后初始化datatables
        initDataTables();
    });
}

/**
 * 初始化datatables，该方法得放在渲染html的dom数据之前，否则刷新数据会有问题
 * 具体配置参考 http://datatables.club/reference/option/
 */
function initDataTables(){
        table = $('#${tableName}_table').DataTable({
        //使用配置项的方式
        // language: datatablesCfg.language,
        //使用配置文件的方式
        language: {
            url:'../bower_components/DataTables-1.13.6/i18n/zh.json'
        },
        //初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
        destroy: true,
        stateSave: true,
        //移动端适配
        responsive: true,
        //选择行
        select: 'single',
        //拖动列
        colReorder: false,
        //表格周围元素显示
        dom: '<"toolbar"B>frtipl',
        buttons: [
            {
                text: '新增',
                name: 'insert',
                action: function (dt, node, config) {
                    window.open('formPage.do', '_self');
                },
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("btn-primary");
                }
            },
            {
                text: '刷新',
                action: function (dt, node, config) {
                    listPageEvent.queryList();
                },
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("btn-info");
                }
            },
            {
                extend: 'collection',
                text: '导出',
                enabled: true,
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("bg-olive");
                },
                buttons: [{
                    extend: 'copy',
                    text: '复制',
                },
                {
                    extend: 'csv',
                    text: '导出csv'
                },
                {
                    extend: 'excel',
                    text: '导出Excel'
                },
                {
                    extend: 'pdf',
                    text: '导出pdf'
                },
                {
                    extend: 'print',
                    text: '打印'
                },]
            },
        ],
    });
    //单击行事件
    table.on('click', 'tr',function (e) {
        var jqRow = $(this);//获取行的jQuery对象
        var row = table.row(this);//获取tr对象
        var rowid = row.id();;//获取tr的id
        var index = row.index();//获取序号
        var rowData = row.data();//获取行数据
    });
}

/**
 * 查询数据并渲染出分页表格（适配loserStar自己写的原生bootstrap分页组件）
 */
listPageEvent.queryPageList = function () {
    //构建查询条件
    var queryObj = {};
    <#list fieldList as field>
    queryObj.${field.name} = $("#${field.name}").val();
    </#list>
    //分页参数
    queryObj.pageNumber = dataPage.getPageNumber();//获取当前页码当做查询参数
    queryObj.pageSize = dataPage.getPageSize();//获取每页多少条当做查询参数

    //排序参数
    queryObj.sort_filed = $("#sort_filed").val();
    queryObj.sort_type = $("#sort_type").val();

    ${fristLowerClassName}Action.getPage(queryObj, function (data) {
        console.log(data);
        dataPage.setPageNumber(data.pageNumber);//把后端返回的页码设置到分页组件中
        dataPage.setPageSize(data.pageSize);//把后端返回的每页多少条设置到分页组件中
        dataPage.setTotalPage(data.totalPage);//把后端总页数设置到分页组件中
        dataPage.setTotalRow(data.totalRow);//把后端返回的数据总条数设置到分页组件中
        data.firstPage ? dataPage.hidePreBtn() : dataPage.showPreBtn();//根据后端返回的是否是第一页，隐藏或显示上一页按钮
        data.lastPage ? dataPage.hideNextBtn() : dataPage.showNextBtn();//根据后端返回的是否是尾页，隐藏或显示下一页按钮

        var text = "";
        for (var i = 0; i < data.list.length; i++) {
            var tmp = data[i];
            text += "                        <tr>";
            text += "                            <td id=\"" + tmp.${primaryKey} + "_index\">" + (i + 1) + "</td>";
            <#list fieldList as field>
            text += "                            <td id=\"" + tmp.${primaryKey} + "_${field.name}\">" + tmp.${field.name} + "</td>";
            </#list>
            text += "                            <td><button id=\""+ tmp.${primaryKey} + "_editBtn\" type=\"button\" class=\"btn btn-primary\">编辑</button>";
            text += "                        </td>";
            text += "                        </tr>";
        }
        $("#dataList_tbody").html(text);
    });
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