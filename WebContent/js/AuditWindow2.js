/**
 * 审核窗口第2个版本 此组件不绑定死窗口及按钮的id，需要初始化时候传入（为了让同一页面可定制多个不同的审核窗口）
 * @param windowId 窗口的容器id
 * @param okBtnSelector 确定按钮的容器选择器
 * @param cancelBtnSelector 取消按钮的容器选择器
 * @returns
 * 
 * html示例（
 * 1.如果一个页面中要出现多个不同定制的审核框，请切记html规范，同一个页面不能出现id重复的元素，否则可能会造成组件的混乱 
 * 2.不同组的radio或checkbox元素的name名称请区别定义
 * ）：
 *     <!-- 审核窗口_带退回选项 -->
    <div id="auditWindow">
        <div>
            <span>审核意见</span>
        </div>
        <div>
            <div>
                <input type="radio" name="result" value="1">同意
                <input type="radio" name="result" value="0">不同意
            </div>
            <div>
                审核意见：<br>
                <textarea name="auditContent_back" id="auditContent" cols="80" rows="10"></textarea>
            </div>
            <div >
                <input id="auditWindowOk" type="button" value="确定" style="margin-right: 10px;">
                <input id="auditWindowCancel" type="button" value="取消" style="margin-right: 10px;">
            </div>
        </div>
    </div>
    js示例：
    //假如要建立两个不同的审核弹框
    //1.声明两个你页面中的全局对象，用来储存生成审核框对象
    var auditWindow1 = {};
	var auditWindow2 = {};
	//2.使用组件的initWindow方法，生成审核框对象
    auditWindow1 = AuditWindow2.initWindow("auditWindow","#auditWindowOk","#auditWindowCancel");
    auditWindow2 = AuditWindow2.initWindow("auditWindow2","#auditWindowOk2","#auditWindowCancel2");
    //3.在某个事件方法中，使用全局对象里的open方法，弹出需要弹出的框，传入两个回调方法，以及在哪个页面元素上弹出审核框，没有的话传null,默认弹出位置是浏览器1/3宽度，1/2高度的地方
    var elment  = $("#要弹出的位置所在的元素的id");
    auditWindow1.open(function callBackOK(){alert("点击确定")},function callBackCancel(){alert("点击取消")});
 */
var AuditWindow2 = {};
AuditWindow2.initWindow = function (windowId, okBtnSelector, cancelBtnSelector) {
    var tempWindow = {};
    // 记录下初始化时候的参数，便于其它方法调用
    tempWindow.windowId = windowId;
    tempWindow.okBtnSelector = okBtnSelector;
    tempWindow.cancelBtnSelector = cancelBtnSelector;
    tempWindow.open = AuditWindow2.open;
    // 初始化window
    var window = $("#" + windowId);
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    window.jqxWindow({
        position: {
            x: browserWidth / 3,
            y: browserHeight / 2
        },
        isModal: true,
        autoOpen: false,
        draggable: true,
        resizable: true,
        showCollapseButton: false,
        maxHeight: 500,
        maxWidth: 1000,
        minHeight: 200,
        minWidth: 200,
        height: 300,
        width: 700
    });
    // 初始化确定按钮
    $(okBtnSelector).jqxButton({
        height: 30,
        width: 80
    });
    // 初始化取消按钮
    $(cancelBtnSelector).jqxButton({
        height: 30,
        width: 80
    });

    return tempWindow
}

/**
 * 打开审核窗口，传入确定和取消按钮的事件方法回调
 */
AuditWindow2.open = function (okCallBack, cancelCallBack, element) {
    var tempWindow = this;
    //为确定按钮绑定传入的点击事件
    $(tempWindow.okBtnSelector).unbind("click");//添加事件前先移除事件，否则会造成添加多个事件，导致执行多次
    $(tempWindow.okBtnSelector).on('click', function (e) {
        if (okCallBack != undefined) {
            okCallBack();
        }
        $("#" + tempWindow.windowId).jqxWindow('close');
    });

    //为取消按钮绑定传入的取消事件
    $(tempWindow.cancelBtnSelector).unbind("click");//添加事件前先移除事件，否则会造成添加多个事件，导致执行多次
    $(tempWindow.cancelBtnSelector).on('click', function (e) {
        if (cancelCallBack != undefined) {
            cancelCallBack();
        }
        $("#" + tempWindow.windowId).jqxWindow('close');
    });
    if (undefined != element && null != element && "" != element) {
        var offset = $(element).offset();
        $("#" + tempWindow.windowId).jqxWindow({
            position: {
                x: offset.left,
                y: offset.top
            }
        });
    }
    //执行打开窗口操作
    $("#" + tempWindow.windowId).jqxWindow('open');
}
