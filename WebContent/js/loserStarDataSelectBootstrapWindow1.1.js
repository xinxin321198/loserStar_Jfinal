/**
 * 基于bootstrap3的模态对话框封装的简单查询数据和选择数据的弹出窗口，使用原型链的方式封装
 * 
用法：
    //构建查询条件配置项
    //filedName显示的字段名，
    //filedId字段的key，
    //value默认值，(如果是select类型的，该值得传入一个数组对象，数组内每一个元素必须包含name和value两个key）
    //type控件类型（目前支持text和select类型）
    var queryFiledList = [
        { filedName: "数据编号", filedId:"query_num",value:"",type:"text"},
        { filedName: "数据名称", filedId:"query_name",value:"",type:"text"},
        { filedName: "数据类型", filedId: "query_type", value: dict.main_data_type,type:"select"},
    ];
    //构建组件初始化参数
    var fileOpt = {
        //自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
        flagId: "loserStarDataSelect",
        title:"loserStar的数据选择窗口",
        //是否打开时候自动查询
        isAutoQuery:false,
        //查询条件配置（目前支持text和select两种组件）
        queryFiledList: queryFiledList,
        //点击查询按钮时的回调方法，不配置则隐藏查询按钮（该回调会传入otherData数据进去，方便外部使用open时候传入的参数对象,查到数据后需调用renfreshTableData方法把数据传进去进行table的刷新）
        clickQueryCallback: querSelectData,
        //配置table展示的数据的字段
        displayTableFiled: [{ name: "数据编号", id: "num" }, { name: "数据名称", id: "name" }, ],
        //配置数据的主键字段
        querSelectDataPrimaryKey:"data_id",
        //是否可重复选择（true：选了之后不会禁用选择按钮，false:选过了之后的数据就禁用选择按钮，方便是被已选过哪些数据）
        isRepeatSelect:false,
        //配置操作列的按钮（
        id：按钮的id标识，
        name：按钮显示的名称、
        sight：情景颜色（就bootstrap那几个）、
        isRepeatSelect：是否可重复选择（true：选了之后不会禁用选择按钮，false:选过了之后的数据就禁用选择按钮，方便是被已选过哪些数据）、
        clickRowCallback：点击按钮回调方法，会传入三个参数，分别为，第一个：传入这一行的数据主键；第二个：传入这一整行的数据；第三个：传入open时候外部传进来的对象）
        operationBtnOpt:[{ id: "addBtn", name: "添加", sight: "primary",isRepeatSelect: false, clickRowCallback: addMainDataRow }]
        //是否显示序号
        isShowIndex:true,
    }
    addMainDataWindow = new loserStarDataSelectBootstrapWindow(fileOpt);
    addMainDataWindow.open({});//打开窗口,可以额外附个数据，选中一条数据后会把该数据以第三个参数的方式带到回调方法上）
    addMainDataWindow.renfreshTableData([],[]);//刷新数据(数据得自己取数并以数组的形式传入该方法进行刷新)第一个参数：需要刷新的数据；第二个参数：刷新之后需要禁用选择的数据主键id集合
    addMainDataWindow.closeWindow(function{alert('窗口已关闭，这是关闭后的回调');})//代码关闭窗口，关闭结束后会触发对话框的关闭事件，可从外部传入一个回调方法进行些相关操作
*/
/**
 * 构造方法
 * @param {*} opt 配置项

 * @returns 
 */
var loserStarDataSelectBootstrapWindow = function (opt) {
    this.init(opt);
}

loserStarDataSelectBootstrapWindow.prototype = {
    constructor: loserStarDataSelectBootstrapWindow,
    init: function (opt) {
        this.flagId = opt.flagId;//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
        this.title = opt.title;//标题
        this.isAutoQuery = opt.isAutoQuery;//是否打开时候自动查询
        this.queryFiledList = opt.queryFiledList ? opt.queryFiledList : [];//查询的条件
        this.clickQueryCallback = opt.clickQueryCallback;//点击查询时候的方法
        this.displayTableFiled = opt.displayTableFiled ? opt.displayTableFiled : [];//table列表显示的字段有哪些
        this.querSelectDataPrimaryKey = opt.querSelectDataPrimaryKey;//指定唯一标识字段
        this.operationBtnOpt = opt.operationBtnOpt ? opt.operationBtnOpt : [];//操作列的按钮配置
        this.isShowIndex = opt.isShowIndex ? true : opt.isShowIndex;//是否显示序号
        //执行一些初始化的方法
        this.createElement();//初始化时候就执行一次渲染html
    },
    //渲染必要的html
    createElement: function () {
        var self = this;
        var text = "";
        text += "<!-- loserStarDataSelectBootstrapWindow--begin -->";
        text += "        <div id=\"" + self.flagId + "_dataSelectWindow\" class=\"modal fade\" tabindex=\"-1\" role=\"dialog\">";
        text += "            <div class=\"modal-dialog modal-lg\" role=\"document\">";
        text += "                <div class=\"modal-content\">";
        text += "                    <div class=\"modal-header\">";
        text += "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span";
        text += "                                aria-hidden=\"true\">×</span>";
        text += "                        </button>";
        text += "                        <h3 style=\"text-align: center;\"><span class=\"label label-default\">" + self.title + "</span></h3>";
        for (var i = 0; i < self.queryFiledList.length; i++) {
            var filedTmp = self.queryFiledList[i];
            if ("text" == filedTmp.type) {
                text += "                        <div class=\"form-group\">";
                text += "                            <label for=\"" + filedTmp.filedId + "\">" + filedTmp.filedName + "</label>";
                text += "                            <input type=\"text\" class=\"form-control\" id=\"" + filedTmp.filedId + "\" placeholder=\"\" value=\"" + (filedTmp.value ? filedTmp.value : "") + "\">";
                text += "                        </div>";
            }
            if ("select" == filedTmp.type) {
                text += "                        <div class=\"form-group\">";
                text += "                            <label for=\"" + filedTmp.filedId + "\">" + filedTmp.filedName + "</label>";
                text += "                            <select name=\"type\" id=\"" + filedTmp.filedId + "\" class=\"form-control\">";
                var filedTmpValue = filedTmp.value;
                for (var j = 0; j < filedTmpValue.length; j++) {
                    var valueList = filedTmpValue[j];
                    text += "                                <option value=\"" + valueList.value + "\">" + valueList.name + "</option>";
                }
                text += "                            </select>";
                text += "                        </div>";
            }
        }

        text += "                        <button id=\"" + self.flagId + "_queryBtn\" type=\"button\" class=\"btn btn-success\">搜索</button>";
        text += "                    </div>";
        text += "                    <div class=\"modal-body\"  style=\"height: 500px; overflow: auto;\">";
        text += "                        <table id=\"" + self.flagId + "_dataTable\" class=\"table table-bordered table-condensed\">";
        text += "                            <thead>";
        text += "                                <tr>";
        if (self.isShowIndex) {
            text += "                                    <th>#</th>";
        }
        for (j = 0; j < self.displayTableFiled.length; j++) {
            var jTmp = self.displayTableFiled[j];
            text += "                                    <th>" + jTmp.name + "</th>";
        }
        text += "                                    <th>操作</th>";
        text += "                                </tr>";
        text += "                            </thead>";
        text += "                            <tbody id=\"" + self.flagId + "_dataList_tbody\">";

        text += "                            </tbody>";
        text += "                        </table>";
        text += "                    </div>";
        text += "                    <div class=\"modal-footer\">";
        text += "                        <button id=\"" + self.flagId + "_CloseBtn\" type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">关闭</button>";
        text += "                    </div>";
        text += "                </div>";
        text += "                <!-- /.modal-content -->";
        text += "            </div>";
        text += "            <!-- /.modal-dialog -->";
        text += "        </div>";
        text += "        <!-- /.modal -->";
        text += "        <!-- loserStarDataSelectBootstrapWindow--end -->";

        $("body").append(text);

        //绑定按钮的事件
        $("#" + self.flagId + "_queryBtn").on("click", function () {
            self.clickQueryCallback(self.otherData);
        });
        if (self.queryFiledList == undefined || self.queryFiledList == null || self.queryFiledList.length <= 0) {
            $("#" + self.flagId + "_queryBtn").hide();
        }
    },
    //打开窗口（可以额外附件个数据，选中一条数据后会把该数据以第二个参数的方式带到回调方法上）
    open: function (otherData) {
        var self = this;
        self.otherData = otherData;
        $("#" + self.flagId + "_dataSelectWindow").modal({
            keyboard: false,
            backdrop: 'static'
        });

        //打开完之后的事件（先移除事件再绑定，否则重复open就会重复触发该事件）
        $("#" + self.flagId + "_dataSelectWindow").unbind("shown.bs.modal");
        $("#" + self.flagId + "_dataSelectWindow").on("shown.bs.modal", function (e) {
            if (self.isAutoQuery) {
                self.clickQueryCallback(self.otherData);
            }
        });
    },
    //获取查询条件
    getQueryParam: function () {
        var self = this;
        var queryParam = {};
        for (var i = 0; i < self.queryFiledList.length; i++) {
            var tmp = self.queryFiledList[i];
            queryParam[tmp.filedId] = $("#" + tmp.filedId + "").val();
        }
        return queryParam;
    },
    //刷新table列表数据
    renfreshTableData: function (list, disabledIdList) {
        var self = this;
        self.list = list ? list : [];
        self.disabledIdList = disabledIdList ? disabledIdList : [];
        $("#" + self.flagId + "_dataList_tbody").html("");
        var text = "";
        for (var i = 0; i < self.list.length; i++) {
            var tmp = self.list[i];
            text += "                                <tr>";
            if (self.isShowIndex) {
                text += "                                    <td>" + (i + 1) + "</td>";
            }
            for (j = 0; j < self.displayTableFiled.length; j++) {
                var jTmp = self.displayTableFiled[j];
                text += "                                    <td>" + tmp[jTmp.id] + "</td>";
            }
            text += "                                    <td>";
            //根据操作按钮配置，渲染操作按钮
            for (var j = 0; j < self.operationBtnOpt.length; j++) {
                var btnOpt = self.operationBtnOpt[j];
                text += "                                        <button id=\"" + tmp[self.querSelectDataPrimaryKey] + "_" + btnOpt.id + "\" data-id=\"" + tmp[self.querSelectDataPrimaryKey] + "\" type=\"button\" class=\"btn btn-" + btnOpt.sight + "\">" + btnOpt.name + "</button>";
            }
            text += "                                    </td>";
            text += "                                </tr>";
        }
        $("#" + self.flagId + "_dataList_tbody").html(text);

        for (var i = 0; i < self.list.length; i++) {
            var tmp = self.list[i];
            //绑定按钮的click事件
            for (var j = 0; j < self.operationBtnOpt.length; j++) {
                var btnOptTmp = self.operationBtnOpt[j];
                $("#" + tmp[self.querSelectDataPrimaryKey] + "_" + btnOptTmp.id).on("click", function () {
                    var btnSelf = this;
                    var btn_data_id = $(btnSelf).attr("data-id");
                    var btn_data = $(btnSelf).data("data");
                    var btnOpt = $(btnSelf).data("btnOpt");
                    if (btnOpt.clickRowCallback) {
                        btnOpt.clickRowCallback(btn_data_id, btn_data, self.otherData);
                        self.disabledIdList.push(btn_data_id);
                        self.refreshTableDataDisabled();
                    }
                });
                //绑定数据到按钮上
                $("#" + tmp[self.querSelectDataPrimaryKey] + "_" + btnOptTmp.id).data("data", list[i]);
                $("#" + tmp[self.querSelectDataPrimaryKey] + "_" + btnOptTmp.id).data("btnOpt", btnOptTmp);
            }

            self.refreshTableDataDisabled();
        }
    },
    //以当前查询出来的数据匹配刷新按钮的禁用
    refreshTableDataDisabled: function () {
        var self = this;
        var list = self.list;
        var disabledIdList = self.disabledIdList;
        for (var i = 0; i < list.length; i++) {
            var tmp = list[i];
            for (var j = 0; j < self.operationBtnOpt.length; j++) {
                var btnOptTmp = self.operationBtnOpt[j];
                var isDisabled = false;//是否禁用，true是禁用，false启用
                var btnIsRepeatSelect = btnOptTmp.isRepeatSelect;//此按钮是否可重复选择（基于self.disabledIdList内的数据，如果关闭了重复选择，并且匹配到self.disabledIdList，就禁用）
                if (!btnIsRepeatSelect) {
                    for (var k = 0; k < disabledIdList.length; k++) {
                        if (disabledIdList[k] == tmp[self.querSelectDataPrimaryKey]) {
                            isDisabled = true;
                        }
                    }
                    if (isDisabled) {
                        $("#" + tmp[self.querSelectDataPrimaryKey] + "_" + btnOptTmp.id).attr("disabled", true);//添加禁用
                    }
                }
            }
        }
    },
    //关闭对话框，可传入一个回调方法
    closeWindow: function (callback) {
        var self = this;
        $("#" + self.flagId + "_dataSelectWindow").modal('hide');
        if (callback) {
            // （先移除事件再绑定，否则多次调用就会重复触发该事件）
            $("#" + self.flagId + "_dataSelectWindow").unbind('hidden.bs.modal');
            $("#" + self.flagId + "_dataSelectWindow").on('hidden.bs.modal', function (e) {
                callback();
                $("#" + self.flagId + "_dataSelectWindow").unbind('hidden.bs.modal');
            });
        }
    }
}