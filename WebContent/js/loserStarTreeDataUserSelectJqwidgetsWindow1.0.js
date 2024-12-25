/**
 * 基于jqwidget的一个弹窗选人组件，从之前使用的userWindow.js中移植出来的组件，重新使用原型链的方式进行封装，可进行变量隔离
 * 用法：
 * 初始化
    var opt = {};
    opt.flagId = "loserStarUserSelect";//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）\
    opt.title = "发送用户选择";//标题
    opt.isCheckBigLeader = false;//是否在选择的人当中包含大领导时进行提示
    opt.isFreeSelect = false;//是否允许自由选人（如fals不允许，则只会显示右边待选列表中的人）
    opt.treeDataUrl = "getHRTree.do";//加载左边树节点的url
    opt.defaultUserUrl = "getManagerUserCodeList.do";//默认已选择数据的url
    //点击确定后的回调方法
    opt.okCallback = function(userids,userList){
        console.log(userids);
        console.log(userList);
    }
    userSelectWindow = new loserStarTreeDataSelectJqwidgetsWindow(opt);


    //调用
    userSelectWindow.open("打开的标题","默认右边待选列表的人员链接",function(userids, userList){
      //选择人员之后的回调方法，userids是人员字符串逗号分隔，userList是人员对象
      userList结构为
      boundindex: 0
      plan_erp: "Z16",
      plan_erp_name: undefined
      sobjname: "系统运行科副科长"
      uid: "06000205"
      uniqueid: "2425-29-29-20-252619"
      userId: "06000205"
      userName: "普通"
      userType: undefined
      visibleindex: 0
    });

    //说明：
    treeDataUrl返回的数据结构必须为如下结构
    [
    {
        "f_id": "1",
        "f_pid": "0",
        "name": "科室1",
        "plan_erp": "职级",
        "rtype": "P",
        "sobjname": "岗位名称"
    },
        {
        "f_id": "1-1",
        "f_pid": "1",
        "name": "科室1-1",
        "plan_erp": "职级",
        "rtype": "P",
        "sobjname": "岗位名称"
    }
  ]

  defaultUserUrl返回的数据必须为erp_org里的结构


 */

var loserStarTreeDataUserSelectJqwidgetsWindow = function (opt) {
  this.init(opt);
}

loserStarTreeDataUserSelectJqwidgetsWindow.prototype = {
  constructor: loserStarTreeDataUserSelectJqwidgetsWindow,
  init: function (opt) {
    this.flagId = opt.flagId ? opt.flagId : new Date().getTime();//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）\
    this.title = opt.title ? opt.title : "树形选择窗口";//标题
    this.isCheckBigLeader = (opt.isCheckBigLeader != undefined && opt.isCheckBigLeader != null) ? opt.isCheckBigLeader :false;//是否在选择的人当中包含大领导时进行提示
    this.isFreeSelect = (opt.isFreeSelect!=undefined&&opt.isFreeSelect!=null) ? opt.isFreeSelect:false;//是否允许自由选人（如fals不允许，则只会显示右边待选列表中的人）
    this.treeDataUrl = opt.treeDataUrl ? opt.treeDataUrl : '';//加载左边树节点的url
    this.searchLogoUrl = opt.searchLogoUrl ? opt.searchLogoUrl : "../img/search.png";//查询条件小框口的logo
    this.userLogoUrl = opt.userLogoUrl ? opt.userLogoUrl : "../img/user.gif";//树形列表人员的logo
    this.postLogoUrl = opt.postLogoUrl ? opt.postLogoUrl : "../img/post.png";//树形列表岗位的logo
    this.orgLogoUrl = opt.orgLogoUrl ? opt.orgLogoUrl : "../img/org.gif";//树形列表组织的logo
    this.defaultUserUrl = opt.defaultUserUrl ? opt.defaultUserUrl:'';//默认已选择数据的url
    this.okCallback = opt.okCallback;//点击确定后的回调方法
    this.createElement();//创建节点
    this.initUserWindow();//初始化jqwidgets组件
    this.selectedNodeList = [];
  },
  //创建html节点
  createElement: function () {
    var self = this;
    var text = "";
    var text = "";
    text += "    <!-- 选人窗口--begin !-->";
    text += "    <div id=\"" + self.flagId + "_chooseUserWindow\">";
    text += "        <div>&nbsp;";
    text += "        </div>";
    text += "        <div>";
    text += "            <div id=\"" + self.flagId + "_chooseDockPanel\">";
    text += "                <div id=\"" + self.flagId + "_inputUser\" dock=\"top\" style=\"height:30px;margin-top: 3px\">";
    text += "                    <label style=\"font-family: Verdana;\">查找员工：</label>";
    text += "                    <input id=\"" + self.flagId + "_inputValue\" type=\"text\" />";
    text += "                    <div id=\"" + self.flagId + "_search\">";
    text += "                        <img alt=\"search\" width=\"16\" height=\"16\" src=\"" + self.searchLogoUrl +"\" />";
    text += "                    </div>";
    text += "                </div>";
    text += "                <div id=\"" + self.flagId + "_treePanel\" style=\"width:300px;height:250px\" dock=\"left\">";
    text += "                    <div id=\"" + self.flagId + "_jqxExpander\">";
    text += "                        <div>组织机构</div>";
    text += "                        <div style=\"overflow: hidden;\">";
    text += "                            <div style=\"border: none;\" id=\"" + self.flagId + "_userTreeGrid\">";
    text += "                            </div>";
    text += "                        </div>";
    text += "                    </div>";
    text += "                </div>";
    text += "                <div id=\"" + self.flagId + "_div2\">";
    text += "                    <div style=\"margin-left: 5px;\" id=\"" + self.flagId + "_choosedUserGrid\"></div>";
    text += "                </div>";
    text += "            </div>";
    text += "    ";
    text += "            <div style=\"text-align: center; margin-top: 20px;\">";
    text += "                <input style=\"width:80px;height:25px; margin-right: 15px\" type=\"button\" id=\"" + self.flagId + "_ok\" value=\"确定\" />";
    text += "                <input style=\"width:80px;height:25px;margin-left: 15px\" type=\"button\" id=\"" + self.flagId + "_cancel\" value=\"关闭\" />";
    text += "            </div>";
    text += "        </div>";
    text += "    </div>";
    text += "    <!-- 选人窗口--end !-->";
    $("body").append(text);
    $("#" + self.flagId + "ok").unbind("click");
    $("#" + self.flagId +"_ok").on("click", function () {
      var tempUserList = [];//保存用户信息的临时变量
      var lst = [];//存用户id的临时变量
      var rowindexes = $("#" + self.flagId + "_choosedUserGrid").jqxGrid('getselectedrowindexes');
      var userIds = '';
      var lst = [];
      var tempUserList = [];//保存用户信息的临时变量
      for (var i = 0; i < rowindexes.length; i++) {
        var data = $("#" + self.flagId + "_choosedUserGrid").jqxGrid("getrowdata", rowindexes[i]);
        lst.push(data.userId);
        tempUserList.push(data);
      }
      // $.each(rows, function (i, item) {
      //   if (item.userId != undefined && item.userId != "undefined") {
      //     lst.push(item.userId);
      //     tempUserList.push(item);
      //   }
      // });
      userIds = lst.join(',');
      
      if (self.isCheckBigLeader){
        //检测是否包含大领导
        var isHaveBigLeader = false;
        for (var i = 0; i < tempUserList.length; i++) {
          if (tempUserList[i].plan_erp) {
            if (self.checkIsBigLeader(tempUserList[i].plan_erp)) {
              isHaveBigLeader = true;
            }
          }
        }
        if (isHaveBigLeader) {
          if (confirm("所选的人员当中包含有部级(含)以上领导，是否确定？")) {
            if(self.okCallback){
              self.okCallback(userIds, tempUserList);
            }
          }
        } else {
          if (self.okCallback) {
            self.okCallback(userIds, tempUserList);
          }
        }
      }else{
        //不用检测
        if (self.okCallback) {
          self.okCallback(userIds, tempUserList);
        }
      }
    });
  },
  //初始化Window
  initUserWindow: function () {
    var self = this;
    var flagId = self.flagId;
    $("#" + flagId + "_chooseDockPanel").jqxDockPanel({
      width: 900,
      height: 400,
      lastchildfill: true,
    });
    $("#" + flagId + "_chooseUserWindow").jqxWindow({
      autoOpen: false,
      draggable: true,
      resizable: false,
      showCollapseButton: true,
      maxHeight: 500,
      title: self.title,
      maxWidth: 1366,
      minHeight: 200,
      minWidth: 200,
      height: 500,
      width: 920,
      cancelButton: $("#" + flagId + "_cancel"),
      initContent: function () {
        $("#" + flagId + "_inputUser").jqxInput({
          placeHolder: "请输入员工编号或姓名",
          height: 23,
          width: 300,
          minLength: 1,
        });
        $("#" + flagId + "_search").click(function () {
          self.gridFilter();
        });
        $("" + flagId + "_input").keydown(function (args) {
          if (args.key == "Enter") {
            self.gridFilter();
          }
        });
        $("#" + flagId + "_ok").jqxButton({
          width: "65px",
        });

        $("#" + flagId + "_cancel").jqxButton({
          width: "65px",
        });
        $("#" + flagId + "_ok").focus();

        $("#" + flagId + "_jqxExpander").jqxExpander({
          showArrow: false,
          toggleMode: "none",
          width: "300px",
          height: "369px",
        });
      },
    });

    //定义树的数据源
    var source = {
      dataType: "json",
      async: true,
      url: self.treeDataUrl,
      dataFields: [
        {
          name: "f_id",
          type: "string",
        },
        {
          name: "f_pid",
          type: "string",
        },
        {
          name: "name",
          type: "string",
        },
        {
          name: "rtype",
          type: "string",
        },
        {
          name: "sobjname",
          type: "string",
        },
        {
          name: "plan_erp",
          type: "string",
        },
      ],
      hierarchy: {
        keyDataField: {
          name: "f_id",
        },
        parentDataField: {
          name: "f_pid",
        },
      },
      id: "f_id",
      loadComplete: function (data) { },
      beforeLoadComplete: function (records) {
        for (var i = 0; i < records.length; i++) {
          var tmp = records[i];
          if (tmp.rtype == "P") {
            //用户logo
            tmp.icon = self.userLogoUrl;
          } else if(tmp.rtype == "S"){
            //岗位logo
            tmp.icon = self.postLogoUrl;
          }else if(tmp.rtype == "O"){
            //组织logo
            tmp.icon = self.orgLogoUrl;
          }
        }
        return records;
      },
    };

    var dataAdapter = new $.jqx.dataAdapter(source);
    window.winSuSource = source;
    window.winSuDataAdapter = dataAdapter;

    $("#" + flagId + "_userTreeGrid").jqxTreeGrid({
      width: 300,
      height: "100%",
      source: dataAdapter,
      icons: true,
      ready: function () {
        $("#" + flagId + "_userTreeGrid").jqxTreeGrid("expandRow", "12530401");
      },
      editable: true,
      columnsResize: true,
      editable: false,
      sortable: true,
      showHeader: false,
      autoRowHeight: false,
      columns: [
        {
          text: "f_id",
          dataField: "f_id",
          width: 150,
          hidden: true,
        },
        {
          text: "f_pid",
          dataField: "f_pid",
          width: 150,
          hidden: true,
        },
        {
          text: "name",
          dataField: "name",
          width: 280,
        },
        {
          text: "rtype",
          dataField: "rtype",
          width: 200,
          hidden: true,
        },
        {
          text: "sobjname",
          dataField: "sobjname",
          width: 200,
          hidden: true,
        },
        {
          text: "plan_erp",
          dataField: "plan_erp",
          width: 200,
          hidden: true,
        }
      ],
    });

    //双击坐边树节点，得到选中的节点值
    $("#" + flagId + "_userTreeGrid").on("rowDoubleClick", function (event) {
      var args = event.args;
      var row = args.row;
      if (row.rtype == "P") {
        if (self.checkGridIsAdd(row.f_id)) {
          if (self.isCheckBigLeader&&self.checkIsBigLeader(row.plan_erp)) {
            if (confirm("当前选中的人“" + row.name + "”属于“" + self.getZwDjName(row.plan_erp) + "”，是否添加至右边待选列表？")) {
              $("#" + flagId + "_choosedUserGrid").jqxGrid("addrow", row.f_id, {
                userId: row.f_id,
                userName: row.name,
                sobjname: row.sobjname,
                plan_erp: row.plan_erp,
                plan_erp_name: self.getZwDjName(row.plan_erp),
              });
            }
          } else {
            $("#" + flagId + "_choosedUserGrid").jqxGrid("addrow", row.f_id, {
              userId: row.f_id,
              userName: row.name,
              sobjname: row.sobjname,
              plan_erp: row.plan_erp,
              plan_erp_name: self.getZwDjName(row.plan_erp),
            });
          }

        }
      } else {
          loserStarSweetAlertUtils.confirm("确定添加该部门下的所有员工吗", "",function(){
            loserStarBoostrapUtils.loading();
            self.recursionAddUser(row.records);//递归把节点下的员工都放入self.selectedNodeList中
            //遍历右边grid，把这些员工都放过去
            var addList = [];
            var addIdList = [];
            for(var i=0;i<self.selectedNodeList.length;i++){
              var node = self.selectedNodeList[i];
              if (self.checkGridIsAdd(node.userId)){
                addIdList.push(node.userId);
                addList.push(node);
              }
            }
            $("#" + self.flagId + "_choosedUserGrid").jqxGrid("addrow", addIdList, addList);
            loserStarBoostrapUtils.closeLoading();
          });
      }
    });

    self.initChoosedUserGrid(self.defaultUserUrl);
    $("#" + flagId + "_choosedUserGrid").jqxGrid({
      selectionmode: "checkbox",
    });
    if (!self.isFreeSelect){
      $("#" + self.flagId +"_treePanel").hide();
      $("#" + self.flagId +"_inputUser").hide();
    }
  },
  //过滤器
  gridFilter: function () {
    var self = this;
    var flagId = self.flagId;
    var filtervalue = $("#" + flagId + "_inputValue").val();
    $("#" + flagId + "_userTreeGrid").jqxTreeGrid("clearFilters");
    var filtertype = "stringfilter";
    var filtergroup = new $.jqx.filter();
    var filter_or_operator = 8;
    var filtercondition = "contains";
    var filter = filtergroup.createfilter(
      filtertype,
      filtervalue,
      filtercondition
    );
    filtergroup.addfilter(filter_or_operator, filter);

    var field = "f_id";
    if (isNaN(filtervalue)) {
      field = "name";
    }
    $("#" + flagId + "_userTreeGrid").jqxTreeGrid("addFilter", field, filtergroup);

    $("#" + flagId + "_userTreeGrid").jqxTreeGrid("applyFilters");
    var viewRows = $("#" + flagId + "_userTreeGrid").jqxTreeGrid("getView");
    if (viewRows.length > 0 && filtervalue != "") {
      if (viewRows[0].records.length == 1) {
        //查出来的子项是唯一的就展开
        self.expandRows(viewRows);
      }
    }
  },
  //展开树结构
  expandRows: function (rows) {
    var self = this;
    for (var i in rows) {
      if (rows[i].records != undefined) {
        $("#" + self.flagId + "_userTreeGrid").jqxTreeGrid("expandRow", rows[i].f_id);
        self.expandRows(rows[i].records);
      }
    }
  },
  //递归获取该节点下的所有员工
  recursionAddUser: function (nodes) {
    var self = this;
    // $.each(nodes, function (i, node) {
    //   if (node.rtype == "P") {
    //     var rows = $("#" + self.flagId + "_choosedUserGrid").jqxGrid("getRows");
    //     var bFlag = true;
    //     $.each(rows, function (j, record) {
    //       if (record.userId == node.f_id) {
    //         bFlag = false;
    //       }
    //     });
    //     if (bFlag) {
    //       $("#" + self.flagId + "_choosedUserGrid").jqxGrid("addrow", node.f_id, {
    //         userId: node.f_id,
    //         userName: node.name,
    //         sobjname: node.sobjname,
    //         plan_erp: node.plan_erp,
    //         plan_erp_name: self.getZwDjName(node.plan_erp),
    //       });
    //     }
    //   } else {
    //     self.recursionAddUser(node.records);
    //   }
    // });
    if(nodes==undefined||nodes==null||nodes.length<=0){
      return;
    }
    for(var i=0;i<nodes.length;i++){
      var node = nodes[i];
      if("P" == node.rtype){
        self.selectedNodeList.push({
          userId: node.f_id,
          userName: node.name,
          sobjname: node.sobjname,
          plan_erp: node.plan_erp,
          plan_erp_name: self.getZwDjName(node.plan_erp),
        });
      }else{
        self.recursionAddUser(node.records);
      }
    }
  },
  //初始化已选人员列表
  initChoosedUserGrid: function (defaultUserUrl) {
    var self = this;
    var flagId = self.flagId;
    var source = {
      datatype: "json",
      datafields: [
        {
          name: "userId",
          type: "string",
        },
        {
          name: "userName",
          type: "string",
        },
        {
          name: "sobjname",
          type: "string",
        },
        {
          name: "plan_erp",
          type: "string",
        },
        ,
        {
          name: "plan_erp_name",
          type: "string",
        }
      ],
      loadComplete: function (data) { 
        
      },
      beforeLoadComplete: function (records) {
      },
      deleterow: function (rowid, commit) {
        commit(true);
      },
    };

    var dataAdapter = new $.jqx.dataAdapter(source);
    $("#" + flagId + "_choosedUserGrid").jqxGrid({
      width: 592,
      height: "368px",
      sortable: false,
      columnsresize: true,
      source: dataAdapter,
      altrows: true,
      editable: true,
      enabletooltips: true,
      ready: function () {
        
      },
      columns: [
        {
          text: "人员编号",
          cellsalign: "center",
          datafield: "userId",
          width: 130,
          editable: false,
          align: "center",
        },
        {
          text: "人员姓名",
          cellsalign: "center",
          editable: false,
          datafield: "userName",
          align: "center",
        },
        {
          text: "所属岗位",
          cellsalign: "center",
          editable: false,
          datafield: "sobjname",
          align: "center",
          width: 200
        },
        {
          text: "职位等级",
          cellsalign: "center",
          editable: false,
          datafield: "plan_erp_name",
          align: "center",
        },
        {
          text: "操作",
          hidden: false,
          datafield: "edit",
          columntype: "button",
          width: 100,
          align: "center",
          cellsrenderer: function () {
            return "删除";
          },
          buttonclick: function (row) {
            $("#" + flagId + "_choosedUserGrid").jqxGrid("clearselection");
            var id = $("#" + flagId + "_choosedUserGrid").jqxGrid("getrowid", row);
            $("#" + flagId + "_choosedUserGrid").jqxGrid("deleterow", id);
          },
        },
      ],
    });
    $("#" + flagId + "_choosedUserGrid").on("bindingcomplete", function (event) {
      
    }); 
    // $("#" + flagId + "_choosedUserGrid").jqxGrid("clearselection");//清除选中的项
    if (
      defaultUserUrl != undefined &&
      defaultUserUrl != null &&
      defaultUserUrl != ""
    ) {
      $.ajax({
        url: defaultUserUrl,
        type: "POST",
        async: false,
        success: function (data) {
          if (data.length > 0) {
            for (var i in data) {
              $("#" + flagId + "_choosedUserGrid").jqxGrid("addrow", data[i].userid, {
                userId: data[i].userid,
                userName: data[i].username,
                userType: data[i].usertype,
                sobjname: data[i].sobjname,
                plan_erp: data[i].plan_erp,
                plan_erp_name: data[i].plan_erp_name
              });
            }

          }
        },
        error: function (a, b, c) {
          loserStarSweetAlertUtils.alertError("初始化默认人员失败！", "");
        },
      });
    }
  },
  /**
   * 检测当前用户的职位等级是否是大领导
   * @param {*} plan_erp 
   */
  checkIsBigLeader: function (plan_erp) {
    var flag = false;
    if ("K12" == plan_erp) {
      //工厂正职
      flag = true;
    }
    if ("K13" == plan_erp) {
      //工厂副职
      flag = true;
    }
    if ("K14" == plan_erp) {
      //工厂总监
      flag = true;
    }
    if ("Z11" == plan_erp) {
      //总裁/董事长/书记
      flag = true;
    }
    if ("Z12" == plan_erp) {
      //副总/副书记/主席
      flag = true;
    }
    if ("Z13" == plan_erp) {
      //正部级
      flag = true;
    }
    if ("Z14" == plan_erp) {
      //副部级
      flag = true;
    }
    return flag;
  },

  /**
   * 获取职位等级的名称
   */
  getZwDjName: function (plan_erp) {
    var zwDjName = "";
    if ("K12" == plan_erp) {
      //工厂正职
      zwDjName = "工厂正职";
    }
    if ("K13" == plan_erp) {
      //工厂副职
      zwDjName = "工厂副职";
    }
    if ("K14" == plan_erp) {
      //工厂总监
      zwDjName = "工厂总监";
    }
    if ("Z11" == plan_erp) {
      //总裁/董事长/书记
      zwDjName = "总裁/董事长/书记";
    }
    if ("Z12" == plan_erp) {
      //副总/副书记/主席
      zwDjName = "副总/副书记/主席";
    }
    if ("Z13" == plan_erp) {
      //正部级
      zwDjName = "正部级";
    }
    if ("Z14" == plan_erp) {
      //副部级
      zwDjName = "副部级";
    }
    if ("Z15" == plan_erp) {
      //正部级
      zwDjName = "正科级";
    }
    if ("Z16" == plan_erp) {
      //副部级
      zwDjName = "副科级";
    }
    return zwDjName;
  },
  /**
   * 得到选中的人的数组
   */
  getChooseUserWindowSelectedUserList: function () {
    return chooseUserWindowSelectedUserList;
  },

  /**
   * 打开选择窗口
   * @param {*} title 传入显示的标题
   */
  open:function(title,defaultUserUrl,callBack){
    var self = this;
    
    if(title){
      $("#" + self.flagId + "_chooseUserWindow").jqxWindow({ title: title });
    }
    if (defaultUserUrl){
      self.initChoosedUserGrid(defaultUserUrl);
    }
    if(callBack){
      self.okCallback = callBack;
    }
    $("#" + self.flagId +"_chooseUserWindow").jqxWindow("open");
  },
  close:function(){
    var self = this;
    $("#" + self.flagId + "_chooseUserWindow").jqxWindow("close");
  },
  /**
   * 检测待选列表中是否可以添加数据，如果已存在则不允许添加，
   */
  checkGridIsAdd:function(nodeId){
    var self = this;
    var rows = $("#" + self.flagId + "_choosedUserGrid").jqxGrid("getRows");
    var bFlag = true;//
        $.each(rows, function (i, record) {
          if (nodeId == record.userId) {
            bFlag = false;
          }
        });
    return bFlag;
  }
}