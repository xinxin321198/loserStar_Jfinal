/**
 * 该组件为基于jqwidgets的通用的下拉树组件，通过dropdownButton和treegrid组合而成
 * @param buttonSelector 下拉按钮的css选择器（id）
 * @param treeGridSelector treeGrid的选择器（id）
 * @param url 取数的url,返回vresult结构数据，例如：组织机构的数据取数：SELECT '12530401' AS ORGEH_UP,ORGEH,ORGTX FROM ERP_DEP_VIEW UNION ALL SELECT ORGEH_UP,ORGEH,ORGTX FROM ERP_KS_VIEW
 * @param treeGridReadyCallback treegrid的初始化完成调用的回调方法
 * @param selectType 选取的模式 checkbox,checkboxChild,click,dbclick
 * @param selectedEventCallBck 选中内容后的事件回调方法,接收选中的数据
 * @param dataFields 数据集结构 例如组织机构的数据结构
 * [
                        { name: "orgeh", type: "string" },
                        { name: "orgeh_up", type: "string" },
                        { name: "orgtx", type: "string" },
                    ]
 * @param id 作为数据唯一标识的id字段名称
 * @param pid 数据所属父id的字段名称
 * @param displayTitle 显示字段的列名（标题）
 * @param displayFieldName 用于显示的字段名称
 */
var DropDownTree = function (buttonSelector, treeGridSelector, url, treeGridReadyCallback, selectType, selectedEventCallBck, dataFields, id, pid, displayTitle, displayFieldName) {
    this.buttonSelector = buttonSelector;
    this.treeGridSelector = treeGridSelector;
    this.url = url;
    this.treeGridReadyCallback = treeGridReadyCallback;//treegrid的初始化完成调用的回调方法
    this.selectType = selectType ? selectType : "";//选择的模式
    this.selectedEventCallBck = selectedEventCallBck;//选中内容后的事件回调方法,接收选中的数据
    this.dataFields = dataFields;//jqwidgets数据集结构
    this.id = id;//作为数据唯一标识的id字段名称
    this.pid = pid;//数据所属父id的字段名称
    this.displayTitle = displayTitle;//标题
    this.displayFieldName = displayFieldName;//用于显示的字段名称
    this.init();//初次调用初始化
}

/**
 * 初始化数据以及各组件
 */
DropDownTree.prototype = {
    constructor: DropDownTree,
    init: function () {
        var deptTreeTmp = this;
        $(deptTreeTmp.buttonSelector).jqxDropDownButton({ width: 150, height: 25 });
        $.get(deptTreeTmp.url, function (result) {
            // $('#jqxLoader').jqxLoader('close');
            if (result.flag) {
                var data = result.data;
                var source =
                {
                    dataType: "json",
                    dataFields: deptTreeTmp.dataFields,
                    hierarchy:
                    {
                        keyDataField: { name: deptTreeTmp.id },
                        parentDataField: { name: deptTreeTmp.pid }
                    },
                    localData: data,
                    id: deptTreeTmp.id
                };
                var dataAdapter = new $.jqx.dataAdapter(source, {
                    loadComplete: function () {
                    }
                });
                dataAdapter.dataBind();
                // create jqxTreeGrid.
                $(deptTreeTmp.treeGridSelector).jqxTreeGrid(
                    {
                        // width: '100%',
                        source: dataAdapter,
                        checkboxes: (deptTreeTmp.selectType == "checkbox" || deptTreeTmp.selectType == "checkboxChild") ? true : false,
                        hierarchicalCheckboxes: deptTreeTmp.selectType == "checkboxChild" ? true : false,
                        ready: function () {
                            if (deptTreeTmp.treeGridReadyCallback) {
                                deptTreeTmp.treeGridReadyCallback();
                            }
                        },
                        columns: [
                            { text: deptTreeTmp.displayTitle, align: "center", dataField: deptTreeTmp.displayFieldName, width: '100%' },
                        ]
                    });
                if (deptTreeTmp.selectType == "checkbox" || deptTreeTmp.selectType == "checkboxChild") {
                    $(deptTreeTmp.treeGridSelector).on('rowCheck',
                        function (event) {
                            // event args.
                            var args = event.args;
                            // row data.
                            var row = args.row;
                            // row key.
                            var key = args.key;
                            var checkedRows = $(deptTreeTmp.treeGridSelector).jqxTreeGrid('getCheckedRows');
                            var dropDownContent = "";
                            for (var i = 0; i < checkedRows.length; i++) {
                                // get a row.
                                var rowData = checkedRows[i];
                                dropDownContent += rowData.orgtx + ",";
                            }
                            $(deptTreeTmp.buttonSelector).jqxDropDownButton('setContent', dropDownContent);
                            if (deptTreeTmp.selectedEventCallBck) {
                                deptTreeTmp.selectedEventCallBck(checkedRows);
                            }
                        });
                    $(deptTreeTmp.treeGridSelector).on('rowUncheck',
                        function (event) {
                            // event args.
                            var args = event.args;
                            // row data.
                            var row = args.row;
                            // row key.
                            var key = args.key;
                            var checkedRows = $(deptTreeTmp.treeGridSelector).jqxTreeGrid('getCheckedRows');
                            var dropDownContent = "";
                            for (var i = 0; i < checkedRows.length; i++) {
                                // get a row.
                                var rowData = checkedRows[i];
                                dropDownContent += rowData.orgtx + ",";
                            }
                            $(deptTreeTmp.buttonSelector).jqxDropDownButton('setContent', dropDownContent);
                            if (deptTreeTmp.selectedEventCallBck) {
                                deptTreeTmp.selectedEventCallBck(checkedRows);
                            }
                        });
                }
                if (deptTreeTmp.selectType == "click") {
                    $(deptTreeTmp.treeGridSelector).on('rowSelect',
                        function (event) {
                            // event args.
                            var args = event.args;
                            // row data.
                            var row = args.row;
                            // row key.
                            var key = args.key;
                            $(deptTreeTmp.buttonSelector).jqxDropDownButton('setContent', row.orgtx);
                            if (deptTreeTmp.selectedEventCallBck) {
                                deptTreeTmp.selectedEventCallBck(row);
                            }
                        });
                }
                if (deptTreeTmp.selectType == "dbclick") {
                    $(deptTreeTmp.treeGridSelector).on('rowDoubleClick',
                        function (event) {
                            // event args.
                            var args = event.args;
                            // row data.
                            var row = args.row;
                            // row key.
                            var key = args.key;
                            // data field
                            var dataField = args.dataField;
                            // original double click event.
                            var clickEvent = args.originalEvent;
                            $(deptTreeTmp.buttonSelector).jqxDropDownButton('setContent', row.orgtx);
                            if (deptTreeTmp.selectedEventCallBck) {
                                deptTreeTmp.selectedEventCallBck(row);
                            }
                        });
                }
            } else {
                alert(result.msg);
            }
        });
    },
    /**
     * 获取已选中的项,以字符串分割的形式返回
     */
    getSelectedStrs: function () {
        //获取选中的值
        var deptTreeItems ;
        if (this.selectType == "checkbox" || this.selectType == "checkboxChild") {
            deptTreeItems = $(this.treeGridSelector).jqxTreeGrid('getCheckedRows');
        } else if (this.selectType == "click" || this.selectType == "dbclick"){
            deptTreeItems = $(this.treeGridSelector).jqxTreeGrid('getSelection');
        }
        var deptIdStr = "";
        if (deptTreeItems) {
            for (var i = 0; i < deptTreeItems.length; i++) {
                deptIdStr += deptTreeItems[i][this.id];
                if (i < (deptTreeItems.length - 1)) {
                    deptIdStr += ",";
                }
            }
        }
        return deptIdStr;
    },
    /**
     * 设置哪些项选中，参数以字符串分割的形式传入
     */
    setSelectedStrs: function (deptIds) {
        //默认选中当前查询的值
        if (deptIds != undefined && deptIds != null && deptIds != "") {
            var dept_id_List = deptIds.split(",");
            for (var i = 0; i < dept_id_List.length; i++) {
                var deptId = dept_id_List[i];
                $(this.treeGridSelector).jqxTreeGrid('checkRow', deptId);
            }
        }
    },
    setButtonContent:function(text){
        $(this.buttonSelector).jqxDropDownButton('setContent', text);
    }
}