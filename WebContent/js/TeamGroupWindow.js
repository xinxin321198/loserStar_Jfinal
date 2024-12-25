/**
 * 领导小组选择框
 * @param windowId 窗口的div元素的id
 * @param toolbarSelector 工具条panel的css选择器
 * @param gridSelector grid的选择器
 * 
 * html示例：
 * 1.如果一个页面中要出现多个不同定制的选择框，请切记html规范，同一个页面不能出现id重复的元素，否则可能会造成组件的混乱 
 * 
 * <!-- 领导小组选择框 -->
            <div id="leaderGroupWindow">
                <div id="leaderGroupWindowHeader">
                    <span>
                        选择专业领导小组
                    </span>
                </div>
                <div id="leaderGroupGridPanle">
                    <div id="leaderGroupGrid">
                    </div>
                </div>
            </div>

    js示例：
    var teamGroupWindow1 = {};//全局的存储，用来存储不同的选择框的一些基本信息
    teamGroupWindow1 = TeamGroup.initWindow("leaderGroupWindow","#leaderGroupGridPanle","#leaderGroupGrid");//初始化界面时调用初始化方法
    
    //在某一事件方法中调用此方法打开窗口，传入获取数据的url，回调方法，以及用于定位的元素对象
    teamGroupWindow1.open("getLeaderGroupList.do",function(teamList){
        console.log(teamList);
    },el);
 */
var TeamGroup = {};
TeamGroup.initWindow = function(windowId,toolbarSelector,gridSelector){
    var tempWindow={};
    tempWindow.windowId = windowId;
    tempWindow.toolbarSelector = toolbarSelector;
    tempWindow.gridSelector = gridSelector;
    tempWindow.open = TeamGroup.open;
	var window = $("#"+windowId);
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    $("#"+windowId).jqxWindow({
        position: { x: browserWidth/3, y: browserHeight/2} ,
        showCollapseButton: true,
        minHeight: 200,
        minWidth: 200,
        height: 500,
        width: 1000,
        resizable: true,
        autoOpen: false,
        initContent: function () {
   
        }
    });
    return tempWindow;
}

/**
 * 
 */
/**
 * 打开小组选择
 * @param url 获取组的url
 * @param callback 回调方法
 * @param element 用于定位window显示位置的元素，一般传入所点击的按钮元素对象
 */
TeamGroup.open = function(url,callback,element){
    var tempWindow = this;
    if (undefined != element && null != element && "" != element) {
        var offset = $(element).offset();
        $("#" + tempWindow.windowId).jqxWindow({
            position: {
                x: offset.left,
                y: offset.top
            }
        });
    }
    $("#"+tempWindow.windowId).jqxWindow('open');
    var source =
    {
        url:url,
        datatype: "json",
        datafields:
        [
            { name: 'teamid', type: 'string' },
            { name: 'teamname', type: 'string' }
        ],
        id:"teamid"
    };

    var dataAdapter = new $.jqx.dataAdapter(source);
    $(tempWindow.toolbarSelector).jqxPanel({ width: '99%', height: '93%',autoUpdate: true});
       // initialize jqxGrid
    $(tempWindow.gridSelector).jqxGrid(
               {
                   width: "99%",
                   height: '100%',
                   source: dataAdapter,
                   showtoolbar: true,
				   editable: false,
				   selectionmode: 'checkbox',
                   rendertoolbar: function (toolbar) {
                       var me = this;
                       var container = $("<div style='margin: 5px;'></div>");
                       toolbar.append(container);
                       container.append('<input style="margin-left: 5px;" id="'+tempWindow.windowId+'_ok" type="button" value="确定" />');
                       $("#"+tempWindow.windowId+"_ok").jqxButton({ template: 'success'});
                       $("#"+tempWindow.windowId+"_ok").unbind("click");
                      
                       // save all row.
                       $("#"+tempWindow.windowId+"_ok").on('click', function () {
                        // var rows = $(tempWindow.gridSelector).jqxGrid('getrows');//获取素有
                        var rowindexs = $(tempWindow.gridSelector).jqxGrid('getselectedrowindexes');//获取选中的索引
                        var rows = [];
                        for(index in rowindexs){
                            var data = $(tempWindow.gridSelector).jqxGrid('getrowdata', rowindexs[index]);//获取选中的索引的值
                            rows.push(data);
                        }
                        
                        $("#"+tempWindow.windowId).jqxWindow('close');
                        if(callback){
                            callback(rows);
                        }
                       });
                   },
                   columns: [
                   {
                   text: '序号', sortable: false, filterable: false, editable: false,
                   groupable: false, draggable: false, resizable: false,
                   datafield: '', columntype: 'number', width: "5%",
                   cellsrenderer: function (row, column, value) {
                       return "<div style='margin:4px;'>" + (value + 1) + "</div>";
                   }
               },
             { text: '专业领导小组名称', datafield: 'teamname', width: "95%" ,editable: true,},
           ]
       });

  
}
