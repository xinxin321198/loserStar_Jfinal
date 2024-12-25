var chooseUserWindowSelectedUserList = []; //窗口选中的人的数组集合
/**
 * 过滤器
 */
var gridFilter = function() {
  var filtervalue = $('#inputValue').val();
  $('#userTreeGrid').jqxTreeGrid('clearFilters');
  var filtertype = 'stringfilter';
  var filtergroup = new $.jqx.filter();
  var filter_or_operator = 8;
  var filtercondition = 'contains';
  var filter = filtergroup.createfilter(
    filtertype,
    filtervalue,
    filtercondition
  );
  filtergroup.addfilter(filter_or_operator, filter);

  var field = 'f_id';
  if (isNaN(filtervalue)) {
    field = 'name';
  }
  $('#userTreeGrid').jqxTreeGrid('addFilter', field, filtergroup);

  $('#userTreeGrid').jqxTreeGrid('applyFilters');
  var viewRows = $('#userTreeGrid').jqxTreeGrid('getView');
  if (viewRows.length > 0 && filtervalue != '') {
    if (viewRows[0].records.length == 1) {
      //查出来的子项是唯一的就展开
      expandRows(viewRows);
    }
  }
};

/**
 * 展开树结构
 * @param {*} rows 
 */
function expandRows(rows) {
  for (var i in rows) {
    if (rows[i].records != undefined) {
      $('#userTreeGrid').jqxTreeGrid('expandRow', rows[i].f_id);
      expandRows(rows[i].records);
    }
  }
}



/**
 * 常规打开选人窗口，只要右边待选列表有的人员都会返回（不常用）
 * @param {*} callBack 传入点击确定的回调方法，该回调会接收所选择的人员id字符串，以逗号分隔
 */
function OpenSelectUserWindow(callBack) {
  chooseUserWindowSelectedUserList = [];
  $('#ok').unbind('click'); //先移除事件再添加
  $('#ok').on('click', function() {
    var rows = $('#choosedUserGrid').jqxGrid('getRows');
    var userIds = '';
    var lst = [];
    var tempUserList = [];//保存用户信息的临时变量
    $.each(rows, function(i, item) {
      if (item.userId != undefined && item.userId != 'undefined') {
        //很奇怪，不知道为什么表里有undefined，先加这一句观察一段时间看看
        userIds += item.userId + ',';
        lst.push(item.userId);
        tempUserList.push(item);
      }
    });
    userIds = lst.join(',');
    $('#ok').unbind('click');
    
    //检测是否包含大领导，包含就提示--beign--
    checkBigLeaderCallback(userIds,tempUserList,callBack);
    //检测是否包含大领导，包含就提示--end--
  });
  $('#chooseUserWindow').jqxWindow('open');
}

/**
 * 打开选人窗口，右边待选列表会提供checkbox，勾上的才会返回（常用）
 * @param {*} callBack 传入点击确定的回调方法，该回调会接收所选择的人员id字符串，以逗号分隔
 */
function OpenSelectUserModeWindow(callBack) {
  chooseUserWindowSelectedUserList = [];
  $('#ok').unbind('click'); //先移除事件再添加
  $('#ok').on('click', function() {
    var rowindexes = $('#choosedUserGrid').jqxGrid('getselectedrowindexes');
    if (rowindexes.length > 0) {
      var lst = [];
      var userIds = '';
      var tempUserList = [];//保存用户信息的临时变量
      for (var i = 0; i < rowindexes.length; i++) {
        var data = $('#choosedUserGrid').jqxGrid('getrowdata', rowindexes[i]);
        userIds += data.userId + ',';
        lst.push(data.userId);
        tempUserList.push(data);
      }
      userIds = lst.join(',');
      $('#ok').unbind('click');
      
      //检测是否包含大领导，包含就提示--beign--
      checkBigLeaderCallback(userIds, tempUserList, callBack);
      //检测是否包含大领导，包含就提示--end--
    } else {
      alert('请选择人员');
      return;
    }
  });
  $('#chooseUserWindow').jqxWindow('open');
}


/**
 * 打开单选人的窗口，右边待选列表会提供checkbox，勾上的才会返回（常用）
 * @param {*} callBack 传入点击确定的回调方法，该回调会接收所选择的人员id字符串，以逗号分隔
 */
function OpenSelectUserSingleModeWindow(callBack) {
  chooseUserWindowSelectedUserList = [];
  $('#ok').unbind('click'); //先移除事件再添加
  $('#ok').on('click', function() {
    var rowindexes = $('#choosedUserGrid').jqxGrid('getselectedrowindexes');
    if (rowindexes.length == 1) {
      var tempUserList = [];//保存用户信息的临时变量
      var item = $('#choosedUserGrid').jqxGrid('getrowdata', rowindexes[0]);
      var userIds = item.userId;
      tempUserList.push(item);
      $('#ok').unbind('click');

      //检测是否包含大领导，包含就提示--beign--
      checkBigLeaderCallback(userIds, tempUserList, callBack);
      //检测是否包含大领导，包含就提示--end--
    } else {
      //alert('请选择一个有效的用户数据！');
      alert('只能提交给一个用户！');

      alert('此窗口为单选用户窗口，请选择一个有效的用户数据，不允许选择多个！');

      return;
    }
  });
  $('#chooseUserWindow').jqxWindow('open');
}

/**
 * 打开单选人的窗口，右边待选列表会提供checkbox，勾上的才会返回，并且不允许从组织结构自由选择人员，只能选择后端提供的默认待选取的人员（常用）
 */
function OpenSelectUserSingleModeWindowNotOrg(callBack) {
  chooseUserWindowSelectedUserList = [];
  $('#ok').unbind('click'); //先移除事件再添加
  $('#ok').on('click', function() {
    var rowindexes = $('#choosedUserGrid').jqxGrid('getselectedrowindexes');
    if (rowindexes.length == 1) {
      var tempUserList = [];//保存用户信息的临时变量
      var item = $('#choosedUserGrid').jqxGrid('getrowdata', rowindexes[0]);
      var userIds = item.userId;
      tempUserList.push(item);
      $('#ok').unbind('click');

      //检测是否包含大领导，包含就提示--beign--
      checkBigLeaderCallback(userIds, tempUserList, callBack);
      //检测是否包含大领导，包含就提示--end--
    } else {
      //alert('请选择一个有效的用户数据！');
      alert('只能提交给一个用户！');

      alert('此窗口为单选用户窗口，请选择一个有效的用户数据，不允许选择多个！');

      return;
    }
  });
  $('#chooseUserWindow').jqxWindow('open');
  $("#treePanel").hide();
  $("#inputUser").hide();
}

/**
 * 以单选人员模态对话框显示，并指定弹出window的标题名称
 */
function OpenSelectUserSingleModeWindow_title(title, callBack) {
  $('#chooseUserWindow').jqxWindow('setTitle', title);
  OpenSelectUserSingleModeWindow(callBack);
}
/**
 * 以多选人员模态对话框显示，并制定弹出window的标题名称
 */
function OpenSelectUserModeWindow_title(title, callBack) {
  $('#chooseUserWindow').jqxWindow('setTitle', title);
  OpenSelectUserModeWindow(callBack);
}

/**
 * 以多选人员模态对话框显示，并制定弹出window的标题名称,并可以设置默认的人员
 */
function OpenSelectUserModeWindow_title_defaltUser(
  title,
  defaultUserUrl,
  callBack
) {
  $('#chooseUserWindow').jqxWindow('setTitle', title);
  OpenSelectUserModeWindow(callBack);
  initChoosedUserGrid(defaultUserUrl);
}

/**
 * 以单选人员模态对话框显示，并指定弹出window的标题名称，并可以设置默认的人员
 */
function OpenSelectUserSingleModeWindow_title_defaltUser(
  title,
  defaultUserUrl,
  callBack
) {
  $('#chooseUserWindow').jqxWindow('setTitle', title);
  OpenSelectUserSingleModeWindow(callBack);
  initChoosedUserGrid(defaultUserUrl);
}
/**
 *
 * @param {*} title 标题
 * @param {*} orgUrl 整个组织机构连接（可以使用 erphrinfo/getHRTreeData.do）
 * @param {*} defaultUserUrl 默认人员的链接
 * @param {*} chooseType 选择类型1有checkbox
 */
function initUserWindow(title, orgUrl, defaultUserUrl, chooseType) {
  $('#chooseDockPanel').jqxDockPanel({
    width: 900,
    height: 400,
    lastchildfill: true,
  });
  $('#chooseUserWindow').jqxWindow({
    autoOpen: false,
    draggable: true,
    resizable: false,
    showCollapseButton: true,
    maxHeight: 500,
    title: title,
    maxWidth: 1366,
    minHeight: 200,
    minWidth: 200,
    height: 500,
    width: 920,
    cancelButton: $('#cancel'),
    initContent: function() {
      $('#inputUser').jqxInput({
        placeHolder: '请输入员工编号或姓名',
        height: 23,
        width: 300,
        minLength: 1,
      });
      $('#search').click(function() {
        gridFilter();
      });
      $('input').keydown(function(args) {
        if (args.key == 'Enter') {
          gridFilter();
        }
      });
      $('#ok').jqxButton({
        width: '65px',
      });

      $('#cancel').jqxButton({
        width: '65px',
      });
      $('#ok').focus();

      $('#jqxExpander').jqxExpander({
        showArrow: false,
        toggleMode: 'none',
        width: '300px',
        height: '369px',
      });
    },
  });

  //如果不传取数路劲，则加载红塔集团全部组织数据
  if (orgUrl == null) {
    orgUrl = '../plan/getHRTreeData.do';
  }

  //定义树的数据源
  var source = {
    dataType: 'json',
    async: true,
    url: orgUrl,
    dataFields: [
      {
        name: 'f_id',
        type: 'string',
      },
      {
        name: 'f_pid',
        type: 'string',
      },
      {
        name: 'name',
        type: 'string',
      },
      {
        name: 'rtype',
        type: 'string',
      },
      {
        name: 'sobjname',
        type: 'string',
      },
      {
        name: 'plan_erp',
        type: 'string',
      },
    ],
    hierarchy: {
      keyDataField: {
        name: 'f_id',
      },
      parentDataField: {
        name: 'f_pid',
      },
    },
    id: 'f_id',
    loadComplete: function(data) {},
    beforeLoadComplete: function(records) {
      for (var i = 0; i < records.length; i++) {
        var imgUrl = '../imgs/common/org.gif';
        if (records[i].rtype == 'P') {
          var imgUrl = '../imgs/common/user.gif';
        }
        records[i].icon = imgUrl;
      }
      return records;
    },
  };

  var dataAdapter = new $.jqx.dataAdapter(source);
  window.winSuSource = source;
  window.winSuDataAdapter = dataAdapter;

  $('#userTreeGrid').jqxTreeGrid({
    width: 300,
    height: '100%',
    source: dataAdapter,
    icons: true,
    ready: function() {
      $('#userTreeGrid').jqxTreeGrid('expandRow', '12530401');
    },
    editable: true,
    columnsResize: true,
    editable: false,
    sortable: true,
    showHeader: false,
    autoRowHeight: false,
    columns: [
      {
        text: 'f_id',
        dataField: 'f_id',
        width: 150,
        hidden: true,
      },
      {
        text: 'f_pid',
        dataField: 'f_pid',
        width: 150,
        hidden: true,
      },
      {
        text: 'name',
        dataField: 'name',
        width: 280,
      },
      {
        text: 'rtype',
        dataField: 'rtype',
        width: 200,
        hidden: true,
      },
      {
        text: 'sobjname',
        dataField: 'sobjname',
        width: 200,
        hidden: true,
      },
      {
        text: 'plan_erp',
        dataField: 'plan_erp',
        width: 200,
        hidden: true,
      }
    ],
  });

  $('#userTreeGrid').on('rowDoubleClick', function(event) {
    var args = event.args;
    var row = args.row;

    if (row.rtype == 'P') {
      var rows = $('#choosedUserGrid').jqxGrid('getRows');
      var bFlag = true;//检测是否已添加到待选列表了，默认没有（true）,查到如果已添加过就是false
      $.each(rows, function(i, record) {
        if (record.userId == row.f_id) {
          bFlag = false;
        }
      });
      if (bFlag) {
        if (checkIsBigLeader(row.plan_erp)){
          if (confirm("当前选中的人“"+row.name+"”属于“" + getZwDjName(row.plan_erp)+"”，是否添加至右边待选列表？")){
            $('#choosedUserGrid').jqxGrid('addrow', row.f_id, {
              userId: row.f_id,
              userName: row.name,
              sobjname: row.sobjname,
              plan_erp: row.plan_erp,
              plan_erp_name: getZwDjName(row.plan_erp),
            });
          }
        }else{
          $('#choosedUserGrid').jqxGrid('addrow', row.f_id, {
            userId: row.f_id,
            userName: row.name,
            sobjname: row.sobjname,
            plan_erp: row.plan_erp,
            plan_erp_name: getZwDjName(row.plan_erp),
          });
        }
  
      }
    } else {
      if (
        !(
          row.f_id == '12530401' ||
          row.f_id == '00005198' ||
          row.f_id == '00005298' ||
          row.f_id == '00005398' ||
          row.f_id == '00005498'
        )
      ) {
        if (confirm('确定添加该部门下的所有员工吗？')) {
          recursionAddUser(row.records);
        }
      }
    }
  });

  initChoosedUserGrid(defaultUserUrl);

  if (chooseType == 1) {
    $('#choosedUserGrid').jqxGrid({
      selectionmode: 'checkbox',
    });
  }
}

function recursionAddUser(nodes) {
  $.each(nodes, function(i, node) {
    if (node.rtype == 'P') {
      var rows = $('#choosedUserGrid').jqxGrid('getRows');
      var bFlag = true;
      $.each(rows, function(j, record) {
        if (record.userId == node.f_id) {
          bFlag = false;
        }
      });
      if (bFlag) {
        $('#choosedUserGrid').jqxGrid('addrow', node.f_id, {
          userId: node.f_id,
          userName: node.name,
          sobjname: node.sobjname,
          plan_erp: node.plan_erp,
          plan_erp_name: getZwDjName(node.plan_erp),
        });
      }
    } else {
      recursionAddUser(node.records);
    }
  });
}

/**没有用到（lxx：20201130） */
function initCheckTree() {
  var bCanCheck = true;
  $('#userTreeGrid').jqxTreeGrid({
    hierarchicalCheckboxes: true,
    checkboxes: true,
  });
  $('#userTreeGrid')
    .on('rowCheck', function(event) {
      var args = event.args;
      var row = args.row;
      var key = args.key;

      if (!bCanCheck) {
      } else {
        if (row.rtype == 'P') {
          var rows = $('#choosedUserGrid').jqxGrid('getRows');
          var bFlag = true;
          $.each(rows, function(i, record) {
            if (record.userId == row.f_id) {
              bFlag = false;
            }
          });
          if (bFlag) {
            $('#choosedUserGrid').jqxGrid('addrow', row.f_id, {
              userId: row.f_id,
              userName: row.name,
            });
          }
        }
      }
    })
    .on('rowUncheck', function(event) {
      var args = event.args;
      var row = args.row;
      var key = args.key;

      if (!bCanCheck) {
      } else {
        var rows = $('#choosedUserGrid').jqxGrid('getRows');
        var bFlag = true;
        for (var i = rows.length - 1; i >= 0; i--) {
          if (row.f_id == rows[i].uid) {
            $('#choosedUserGrid').jqxGrid('deleterow', rows[i].uid);
          }
        }
      }
    })
    .on('rowClick', function(event) {
      var args = event.args;
      var row = args.row;
      var key = args.key;
      if (
        row.f_id == '12530401' ||
        row.f_id == '00005098' ||
        row.f_id == '00005198' ||
        row.f_id == '00005298' ||
        row.f_id == '00005398' ||
        row.f_id == '00008198' ||
        row.f_id == '00002798'
      ) {
        bCanCheck = false;
      } else {
        bCanCheck = true;
      }
    });
}

function initChoosedUserGrid(defaultUserUrl) {
  var source = {
    datatype: 'json',
    datafields: [
      {
        name: 'userId',
        type: 'string',
      },
      {
        name: 'userName',
        type: 'string',
      },
      {
        name: 'sobjname',
        type: 'string',
      },
      {
        name: 'plan_erp',
        type: 'string',
      },
      ,
      {
        name: 'plan_erp_name',
        type: 'string',
      }
    ],
    deleterow: function(rowid, commit) {
      commit(true);
    },
  };

  var dataAdapter = new $.jqx.dataAdapter(source);
  $('#choosedUserGrid').jqxGrid({
    width: 592,
    height: '368px',
    sortable: false,
    columnsresize: true,
    source: dataAdapter,
    altrows: true,
    editable: true,
    enabletooltips: true,
    columns: [
      {
        text: '人员编号',
        cellsalign: 'center',
        datafield: 'userId',
        width: 130,
        editable: false,
        align: 'center',
      },
      {
        text: '人员姓名',
        cellsalign: 'center',
        editable: false,
        datafield: 'userName',
        align: 'center',
      },
      {
        text: '所属岗位',
        cellsalign: 'center',
        editable: false,
        datafield: 'sobjname',
        align: 'center',
        width:200
      },
      {
        text: '职位等级',
        cellsalign: 'center',
        editable: false,
        datafield: 'plan_erp_name',
        align: 'center',
      },
      {
        text: '操作',
        hidden:true,
        datafield: 'edit',
        columntype: 'button',
        width: 100,
        align: 'center',
        cellsrenderer: function() {
          return '删除';
        },
        buttonclick: function(row) {
          var id = $('#choosedUserGrid').jqxGrid('getrowid', row);
          $('#choosedUserGrid').jqxGrid('deleterow', id);
        },
      },
    ],
  });

  if (
    defaultUserUrl != undefined &&
    defaultUserUrl != null &&
    defaultUserUrl != ''
  ) {
    $.ajax({
      url: defaultUserUrl,
      type: 'POST',
      async: false,
      success: function(data) {
        if (data.length > 0) {
          for (var i in data) {
            $('#choosedUserGrid').jqxGrid('addrow', data[i].userid, {
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
      error: function(a, b, c) {
        alert('初始化默认人员失败！');
      },
    });
  }
}

/**
 * 得到选中的人的数组
 */
function getChooseUserWindowSelectedUserList() {
  return chooseUserWindowSelectedUserList;
}

/**
 * 检测当前用户的职位等级是否是大领导
 * @param {*} plan_erp 
 */
function checkIsBigLeader(plan_erp){
  var flag = false;
  if ("K12"==plan_erp){
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
}

/**
 * 获取职位等级的名称
 */
function getZwDjName(plan_erp){
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
}

/**
 * 附带检测是否是大领导提示弹出框的处理回调的方法
 * @param {*} userIds 
 * @param {*} tempUserList 
 * @param {*} callBack 
 */
function checkBigLeaderCallback(userIds,tempUserList,callBack){
  //检测是否包含大领导，包含就提示--beign--
  var isHaveBigLeader = false
  for (var i = 0; i < tempUserList.length; i++) {
    if (tempUserList[i].plan_erp){
      if (checkIsBigLeader(tempUserList[i].plan_erp)) {
        isHaveBigLeader = true;
      }
    }
  }
  if (isHaveBigLeader) {
    if (confirm("所选的人员当中包含有部级(含)以上领导，是否确定？")) {
      chooseUserWindowSelectedUserList = tempUserList;
      callBack(userIds);
    }
  } else {
    chooseUserWindowSelectedUserList = tempUserList;
    callBack(userIds);
  }
    //检测是否包含大领导，包含就提示--end--
}