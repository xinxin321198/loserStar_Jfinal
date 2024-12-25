/**
 * 上传excel文件
 * @param url 上传的处理地址
 * @param callBack 上传的完成的回调函数
 * @returns
 */
var FileUploadWindow={};
FileUploadWindow.initFileUploadWindow= function (callBack) {
  var uploadWindow = $("#uploadWindow");
  var offset = uploadWindow.offset();
  uploadWindow.jqxWindow({
    autoOpen: false,
    draggable: true,
    resizable: true,
    showCollapseButton: false,
    minHeight: 200,
    minWidth: 200,
    height: 500,
    width: 400,
    initContent: function() {
      $("#jqxFileUpload").jqxFileUpload({
        multipleFilesUpload: true,
        width: 300,
        fileInputName: "fileToUpload"
      });
      $("#jqxFileUpload").unbind("uploadEnd"); //先移除事件再添加，否则会造成事件重复
      //上传完成的方法，调一下回调
      $("#jqxFileUpload").on("uploadEnd", function(event) {
        var args = event.args;
        var fileName = args.file;
        var serverResponce = args.response;
        callBack(event);
        uploadWindow.jqxWindow("close"); //关闭window
      });
    }
  });
}

FileUploadWindow.openWindow = function (uploadUrl){
  FileUploadWindow.uploadUrl = uploadUrl;
  $("#jqxFileUpload").jqxFileUpload({
    uploadUrl: FileUploadWindow.uploadUrl,
  });
  $("#uploadWindow").jqxWindow('open');
}


FileUploadWindow.onSelectChange = function(){
  var fileType = $("#uploadWindowSelect").val();
  //改变文件上传的url
}