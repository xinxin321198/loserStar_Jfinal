
var DeptSelectTree = {}
/**
 * 初始化部门的tree
 * @param buttonSelector 下拉按钮的css选择器（id）
 * @param treeGridSelector treeGrid的选择器（id）
 * @param url 取数的url SELECT '12530401' AS ORGEH_UP,ORGEH,ORGTX FROM ERP_DEP_VIEW UNION ALL SELECT ORGEH_UP,ORGEH,ORGTX FROM ERP_KS_VIEW
 * @param treeGridReadyCallback treegrid的初始化完成调用的回调方法
 * @param selectType checkbox,checkboxChild,click,dbclick
 * @param selectedEventCallBck 选中内容后的事件回调方法,接收选中的数据
 * @returns 创建好的对象
 */
DeptSelectTree.createDeptTree = function(buttonSelector,treeGridSelector,url,treeGridReadyCallback,selectType,selectedEventCallBck){
    var deptTreeTmp = {};
    deptTreeTmp.buttonSelector = buttonSelector;
    deptTreeTmp.treeGridSelector = treeGridSelector;
    deptTreeTmp.url = url;
    deptTreeTmp.init = DeptSelectTree.init;//初始化组件以及数据
    deptTreeTmp.getSelectedDeptStrs = DeptSelectTree.getSelectedDeptStrs;//获取已选中的项,以字符串分割的形式返回
    deptTreeTmp.setSelectedDeptStrs = DeptSelectTree.setSelectedDeptStrs;//设置哪些项选中，参数以字符串分割的形式传入
    deptTreeTmp.treeGridReadyCallback = treeGridReadyCallback;//treegrid的初始化完成调用的回调方法
    deptTreeTmp.selectType = selectType?selectType:"";//选择的模式
    deptTreeTmp.selectedEventCallBck = selectedEventCallBck;//选中内容后的事件回调方法,接收选中的数据
    deptTreeTmp.init();//初次调用初始化
    // $('#jqxLoader').jqxLoader('open');
    return deptTreeTmp;
		
}

/**
 * 初始化数据以及各组件
 */
DeptSelectTree.init = function(){
    var deptTreeTmp = this;
    $(deptTreeTmp.buttonSelector).jqxDropDownButton({width: 150,height: 25});
    $.get(deptTreeTmp.url,function(result){
        // $('#jqxLoader').jqxLoader('close');
        if(result.flag){
            var data = result.data;
            // prepare the data
            var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'orgeh' },
                    { name: 'orgeh_up' },
                    { name: 'orgtx' }
                ],
                id: 'orgeh',
                localdata: data
            };
            // create data adapter.
            var dataAdapter = new $.jqx.dataAdapter(source);
            // perform Data Binding.
            dataAdapter.dataBind();
            // get the tree items. The first parameter is the item's id. The second parameter is the parent item's id. The 'items' parameter represents 
            // the sub items collection name. Each jqxTree item has a 'label' property, but in the JSON data, we have a 'text' field. The last parameter 
            // specifies the mapping between the 'text' and 'label' fields.  
            // var records = dataAdapter.getRecordsHierarchy('orgeh', 'orgeh_up', 'items', [{ name: 'orgtx', map: 'label'},{ name: 'orgeh', map: 'value'}]);
            // $(deptTreeTmp.treeGridSelector).jqxTree({ source: records, width: '300px',hasThreeStates:true,checkboxes:true});
            // $(deptTreeTmp.treeGridSelector).on('checkChange', function (event) {
            //     var args = event.args;
            //     var element = args.element;
            //     var checked = args.checked;
            //     var items = $(treeGridSelector).jqxTree('getCheckedItems');
            //     var dropDownContent = "";
            //     for(var i=0;i<items.length;i++){
            //         dropDownContent +=items[i].label+",";
            //     }
            //     $(deptTreeTmp.buttonSelector).jqxDropDownButton('setContent', dropDownContent);
            // }); 

            var source =
            {
                dataType: "json",
                dataFields: [
                    { name: "orgeh", type: "string" },
                    { name: "orgeh_up", type: "string" },
                    { name: "orgtx", type: "string" },
                ],
                hierarchy:
                    {
                        keyDataField: { name: 'orgeh' },
                        parentDataField: { name: 'orgeh_up' }
                    },
                localData: data,
                id: "orgeh"
            };
            var dataAdapter = new $.jqx.dataAdapter(source, {
                loadComplete: function () {
                }
            });
            // create jqxTreeGrid.
            $(deptTreeTmp.treeGridSelector).jqxTreeGrid(
            {
                source: dataAdapter,
                    checkboxes: (deptTreeTmp.selectType == "checkbox" || deptTreeTmp.selectType == "checkboxChild")?true:false,
                    hierarchicalCheckboxes: deptTreeTmp.selectType == "checkboxChild"? true : false,
                ready: function () {
                    if(deptTreeTmp.treeGridReadyCallback){
                        deptTreeTmp.treeGridReadyCallback();
                    }
                },
                columns: [
                { text: "组织机构名称", align: "center", dataField: "orgtx", width: '100%' },
                ]
            });
            if (deptTreeTmp.selectType == "checkbox" || deptTreeTmp.selectType == "checkboxChild"){
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
            if (deptTreeTmp.selectType=="click"){
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
            if (deptTreeTmp.selectType == "dbclick"){
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
                        if (deptTreeTmp.selectedEventCallBck){
                            deptTreeTmp.selectedEventCallBck(row);
                        }
                    });
            }
        }else{
            alert(result.msg);
        }
    });
}

/**
 * 获取已选中的项,以字符串分割的形式返回
 */
DeptSelectTree.getSelectedDeptStrs = function(){
    //获取选中的部门
	var deptTreeItems = $(this.treeGridSelector).jqxTreeGrid('getCheckedRows');
    var deptIdStr = "";
    if(deptTreeItems){
        for(var i=0;i<deptTreeItems.length;i++){
            deptIdStr +=deptTreeItems[i].orgeh;
            if(i<(deptTreeItems.length-1)){
                deptIdStr+=",";
            }
        }
    }
    return deptIdStr;
}

/**
 * 设置哪些项选中，参数以字符串分割的形式传入
 */
DeptSelectTree.setSelectedDeptStrs = function(deptIds){
    //默认选中当前查询的值
    if(deptIds!=undefined&&deptIds!=null&&deptIds!=""){
        var dept_id_List = deptIds.split(",");
        for(var i =0;i<dept_id_List.length;i++){
            var deptId = dept_id_List[i];
            $(this.treeGridSelector).jqxTreeGrid('checkRow', deptId);
        }
    }
}

