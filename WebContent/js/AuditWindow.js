/**
 *  html示例:
    <!-- 审核窗口 -->
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
                <textarea name="auditContent" id="auditContent" cols="80" rows="10"></textarea>
            </div>
            <div >
                <input id="auditWindowOk" type="button" value="确定" style="margin-right: 10px;">
                <input id="auditWindowCancel" type="button" value="取消" style="margin-right: 10px;">
            </div>
        </div>
    </div>
    js调用示例：
    AuditWindow.initWindow();//初始化组件，window的id，确定和取消按钮的id必须是示例上的id，不能定制
    AuditWindow.open(auditOkCallBack,auditCancelCallBack);//打开窗口时，传入确定按钮和取消按钮的回调方法
    
    //定义确定按钮的回调方法
    function auditOkCallBack(){
    	//回调方法自己定义去取结果值做操作
    	 var result = $("input[name='result']:checked").val();
	    if(result==undefined||result==null||result==""){
	        alert("请选择一个审核结果！");
	        return;
	    }else{
			var auditContent = $("#auditContent").val();
	        alert(auditContent);
	    }
    }
    //定义取消按钮的回调方法
    function auditCancelCallBack(){
    	
    }
 */
var AuditWindow = {};
AuditWindow.initWindow= function () {
  //初始化window
 var selfWindow = $("#auditWindow");
 var browserWidth = $(window).width();
  var browserHeight = $(window).height();
  selfWindow.jqxWindow({
    position: { x: browserWidth/3, y: browserHeight/2} ,
	isModal:true,
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
  //初始化确定按钮
  $("#auditWindowOk").jqxButton({
    height : 30,
    width : 80
  });
  //初始化取消按钮
  $("#auditWindowCancel").jqxButton({
    height : 30,
    width : 80
  });
}

/**
 * 打开审核窗口，传入确定和取消按钮的事件方法回调
 */
AuditWindow.open = function(okCallBack,cancelCallBack,element){

	
  $("#auditWindowOk").on('click', function(e) {
    if(okCallBack!=undefined){
      okCallBack();
    }
    $("#auditWindow").jqxWindow('close');
  });

  $("#auditWindowCancel").on('click', function(e) {
    if(cancelCallBack!=undefined){
      cancelCallBack();
    }
    $("#auditWindow").jqxWindow('close');
  });
  if(undefined!=element&&null!=element&&""!=element){
	  var offset = $(element).offset();
	  $('#auditWindow').jqxWindow({position: { x: offset.left, y: offset.top}}); 
  }
  $('#auditWindow').jqxWindow('open'); 

}
