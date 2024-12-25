/**
 * 基于bootstrap3的模态对话框和百度上传组件集成的上传附件组件(此组件不依赖于flash，可在最新chrome中禁止flash时使用)，使用原型链的方式封装
 * 
 * 基本用法，引入该js文件和依赖的loserStarSweetAlertUtils.js，以及相关的bootstrap3的js
 * 1.创建配置对象
	  var fileOpt = {
		//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
		flagId:"loserStar",
		//最大允许上传的大小（单位byte，默认200MB=209715200byte,1G=1024MB,1MB=1024kb,1kb=1024byte）
		maxSize:209715200,
		//一般该参数不会在初始化时候就填，因为可能要打开窗口时候才决定得了传到哪个url上，所以可以使用setUrl(url)方法替代
		url:"uploadFile.do",
		//附件类型集合（如果该参数有值，则该组件则可拥有一个附件类型选择的下拉框，并且上传的url会自动添加上file_type参数）
		fileTypeList:[{name:"公共文件",value:"common"},{name:"其它文件",value:"other"}],
		//上传完成后的回调方法，一般用于刷新界面之类的
		uploadFinishedCallback:uploadFinishedCallback
	}
	2.传入配置对象，生成附件上传组件的对象
	loserStarFileWindow = new loserStarFileUploadBootstrapWindow_WebUploader(fileOpt);
	3.打开上传窗口，可传入url,loserStarFileWindow.open(url);
*/
/**
 * 构造方法
 * @param {*} opt 配置项

 * @returns 
 */
var loserStarFileUploadBootstrapWindow_WebUploader = function (opt) {
	this.init(opt);
}

loserStarFileUploadBootstrapWindow_WebUploader.prototype = {
	constructor: loserStarFileUploadBootstrapWindow_WebUploader,
	init: function (opt) {
		this.flagId = opt.flagId;//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
		this.maxSize = opt.maxSize ? opt.maxSize : 209715200;//最大允许上传的大小（单位bit）
		this.fileTypeList = opt.fileTypeList;//附件类型
		this.url = opt.url;//默认上传的url
		this.uploadFinishedCallback = opt.uploadFinishedCallback;
		//执行一些初始化的方法
		this.createElement();//初始化时候就执行一次渲染html
	},
	//渲染必要的html
	createElement: function () {
		var self = this;
		var text = "";
		text += "        <!-- loserStarFileUploadBootstrapWindow_WebUploader--begin -->";
		text += "        <div id=\"" + self.flagId + "_FileWindow\" class=\"modal fade\" tabindex=\"-1\" role=\"dialog\">";
		text += "            <div class=\"modal-dialog\" role=\"document\">";
		text += "                <div class=\"modal-content\">";
		text += "                    <div class=\"modal-header\">";
		text += "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span";
		text += "                                aria-hidden=\"true\">&times;</span></button>";
		if (self.fileTypeList != undefined && self.fileTypeList != null && self.fileTypeList.length > 0) {
			text += "                        <h4 class=\"modal-title\">指定附件类型并选择文件进行上传</h4>";
			text += "                        <div class=\"form-group\">";
			text += "                            <label for=\"" + self.flagId + "_FileType\" class=\"col-sm-3 control-label\">附件分类：</label>";
			text += "                            <div class=\"col-sm-9\">";
			text += "                                <select id=\"" + self.flagId + "_FileType\" name=\"loserStarFileType\"  class=\"form-control\">";
			for (var i = 0; i < self.fileTypeList.length; i++) {
				var tmp = self.fileTypeList[i];
				text += "                                    <option value=\"" + tmp.value + "\">" + tmp.name + "</option>";
			}
			text += "                                </select>";
			text += "                            </div>";
			text += "                        </div>";
		}
		text += "                    </div>";
		text += "                    <!--用来存放文件信息-->";
		text += "                    <div class=\"modal-body\">";
		text += "                        <div id=\"" + self.flagId + "_Picker\">选择文件</div>";
		text += "                        ";
		text += "                    <h3>待上传文件队列</h3>";
		text += "                    <ul id=\"" + self.flagId + "_FileListUl\" class=\"list-group\" style=\"overflow: auto;\">";
		text += "                        ";
		text += "                    </ul>";
		text += "                    </div>";
		text += "";
		text += "                    <div class=\"modal-footer\">";
		text += "                        <button id=\"" + self.flagId + "_UploadAllBtn\" type=\"button\" class=\"btn btn-primary\">全部上传</button>";
		text += "                        <button id=\"" + self.flagId + "_CloseBtn\" type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">关闭</button>";
		text += "                    </div>";
		text += "                </div><!-- /.modal-content -->";
		text += "            </div><!-- /.modal-dialog -->";
		text += "        </div><!-- /.modal -->";
		text += "        <!-- loserStarFileUploadBootstrapWindow_WebUploader--end -->";
		$("body").append(text);

		//绑定按钮的事件
		$("#" + self.flagId + "_UploadAllBtn").on("click", function () {
			self.uploader.upload();//开始上传所有
		});
	},
	//打开窗口
	open: function (url) {
		var self = this;
		$("#" + self.flagId + "_FileWindow").modal({
			keyboard: false,
			backdrop: 'static'
		});

		//打开完之后才能去初始化百度的上传组件，否则会有问题
		$("#" + self.flagId + "_FileWindow").on("shown.bs.modal", function (e) {
			if (!self.uploader) {
				console.log("---------------初始化loserStarFileUploadBootstrapWindow_WebUploader---------------------");
				// 初始化Web Uploader
				self.uploader = WebUploader.create({
					// 选完文件后，是否自动上传。
					auto: false,
					// 选择文件的按钮。可选。
					// 内部根据当前运行是创建，可能是input元素，也可能是flash.
					pick: "#" + self.flagId + "_Picker",
					//可重复上传
					duplicate: true,
					server: url ? url : self.url
				});
				// 当有文件添加进来的时候
				self.uploader.on('fileQueued', function (file) {
					if (self.maxSize) {
						if (file.size > self.maxSize) {
							var mb = (file.size / 1024 /1024).toFixed(2);
							var maxMb = (self.maxSize / 1024 / 1024).toFixed(2);
							loserStarSweetAlertUtils.alertError("上传文件不能大于" + maxMb + "MB，您选择的文件有" + mb + "MB");
							return;
						}
					}
					var text = "";
					text += "                       <li id=\"" + self.flagId + "_" + file.id + "_FileListLi\" class=\"list-group-item\">";
					text += "                            <button id=\"" + self.flagId + "_" + file.id + "_FileListRemBtn\" type=\"button\" class=\"btn btn-danger\" file_id=\"" + file.id + "\">移除</button>";
					text += "                            <button id=\"" + self.flagId + "_" + file.id + "_FileListUploadBtn\" type=\"button\" class=\"btn btn-primary\" file_id=\"" + file.id + "\">上传</button>";
					if (self.fileTypeList != undefined && self.fileTypeList != null && self.fileTypeList.length > 0) {

						var selectedFileTypeValue = loserStarJsUtils.getSelectedValueForSelect1("#" + self.flagId + "_FileType");
						var selectedFileTypeName = loserStarJsUtils.getSelectedTextForSelect("#" + self.flagId + "_FileType");
						text += "                            <span id=\"" + self.flagId + "_" + file.id + "_FileType\" class=\"label label-default\" value=\"" + selectedFileTypeValue + "\">" + selectedFileTypeName + "</span>";
					}
					text += "                            <span>" + file.name + "</span>";
					text += "                            <div id=\"" + self.flagId + "_" + file.id + "_FileListProgress\" class=\"progress\">";
					text += "                                <div id=\"" + self.flagId + "_" + file.id + "_FileListProgressSub\" class=\"progress-bar progress-bar-striped active\" role=\"progressbar\" aria-valuenow=\"45\"";
					text += "                                    aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 0%\">";
					text += "                                   <span id=\"" + self.flagId + "_" + file.id + "_FileListProgressSubSpan\"></span>";
					text += "                                </div>";
					text += "                            </div>";
					text += "                        </li>";
					$("#" + self.flagId + "_FileListUl").append(text);

					//每个文件加入队列之后，绑定移除队列的按钮事件
					$("#" + self.flagId + "_" + file.id + "_FileListRemBtn").on("click", function () {
						var btnSelf = this;
						var file_id = $(btnSelf).attr("file_id");//取出文件id
						self.uploader.removeFile(file_id, true);//移除队列
						$("#" + self.flagId + "_" + file.id + "_FileListLi").remove();//移除改文件的li
					});
					//每个文件加入队列之后，绑定单个上传的按钮事件
					$("#" + self.flagId + "_" + file.id + "_FileListUploadBtn").on("click", function () {
						var btnSelf = this;
						var file_id = $(btnSelf).attr("file_id");//取出文件id
						self.uploader.upload(file_id);//开始上传
					});
				});
				//某个文件开始上传前触发，一个文件只会触发一次。
				self.uploader.on('uploadStart', function (file) {
					var btnSelf = this;
					var file_id = $(btnSelf).attr("file_id");//取出文件id
					if (self.fileTypeList != undefined && self.fileTypeList != null && self.fileTypeList.length > 0) {
						var value = $("#" + self.flagId + "_" + file.id + "_FileType").attr("value");
						if (self.uploader.options.server) {
							self.uploader.options.server = loserStarJsUtils.replaceUrlParams(loserStarJsUtils.parseURL(self.uploader.options.server), { "file_type": value });
						}
					}
				});
				//上传进度
				self.uploader.on('uploadProgress', function (file, percentage) {
					$("#" + self.flagId + "_" + file.id + "_FileListUploadBtn").attr("disabled", true);//上传时禁用上传按钮
					$("#" + self.flagId + "_UploadAllBtn").attr("disabled", true);//上传时禁用全部上传按钮
					var percentage_2 = (percentage * 100).toFixed(2);
					$("#" + self.flagId + "_" + file.id + "_FileListProgressSub").css("width", percentage_2 + "%");//设置进度条百分比
					$("#" + self.flagId + "_" + file.id + "_FileListProgressSubSpan").text(percentage_2 + "%");//设定上传进度百分比文字显示
				});
				//上传成功
				self.uploader.on('uploadSuccess', function (file) {
					console.log('上传成功');
					$("#" + self.flagId + "_" + file.id + "_FileListProgressSub").addClass("progress-bar-success");//把进度条设为绿色
					setTimeout(function () { $("#" + self.flagId + "_" + file.id + "_FileListLi").remove(); }, 2000);//延迟一段时间后移除该文件的li

				});
				//上传出错
				self.uploader.on('uploadError', function (file) {
					console.log("上传出错");
					$("#" + self.flagId + "_" + file.id + "_FileListUploadBtn").attr("disabled", false);//上传出错时恢复禁用状态
					$("#" + self.flagId + "_UploadAllBtn").attr("disabled", true);//上传出错时恢复全部上传按钮禁用状态
					$("#" + self.flagId + "_" + file.id + "_FileListProgressSub").addClass("progress-bar-danger");//把进度条设为红色
					$("#" + self.flagId + "_" + file.id + "_FileListProgressSubSpan").text("上传出错");
				});
				//上传完成
				self.uploader.on('uploadComplete', function (file) {
				});
				//全部上传完成
				self.uploader.on('uploadFinished', function (file) {
					console.log('全部上传完成');
					$("#" + self.flagId + "_UploadAllBtn").attr("disabled", false);//恢复全部上传按钮禁用状态
					if (self.uploadFinishedCallback) {
						self.uploadFinishedCallback();
					}
				});
			}
		});
	}
}