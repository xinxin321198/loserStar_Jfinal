/**
 * version :1.6 
 * https://fex-team.github.io/webuploader/
 * https://github.com/fex-team/webuploader
 * http://fex.baidu.com/webuploader/
 * 基于bootstrap3的模态对话框和百度上传组件集成的上传附件组件(此组件不依赖于flash，可在最新chrome中禁止flash时使用)，使用原型链的方式封装
 * 
 * 1.5版本说明：
 *      增强功能：增加配置（感谢同事曾俊、李阳的版本迭代增强）。1.可配置多个文本框，在附件上传的同时传递到后端；2.可配置关闭窗口时是否清空上传队列中的文件（o(╥﹏╥)o就是因为这个需求，所以才发现的如下的BUG，给老子从2024年大年二十九debug到大年三十凌晨）
 *      fixBug:一个页面多个实例时，“全部上传”的按钮第二次打开新的不会触发（因为之前我是渲染html时候去绑定“全部上传”按钮的事件，现在被调到open之后去绑定就修复了，具体不知道啥原因，没深究）
 *      newBug:多个实例会出现多次重复上传调用的问题，导致多次触发uploadSuccess事件,实际是触发了两次上传啦，此bug的原因分析：
 *          （1）fixBug:尝试把所有绑定事件之前都先移除事件，没效果（当然最好也应该这样，因为发现bootstrap的modal框的shown.bs.modal事件，会触发多次，可能会引起其它意想不到的bug,归根结底还是因为功底不够，jQuery多次绑定匿名的事件函数，会多次触发同一个函数，但实则并不是同一个函数，因为匿名，每次都属于是添加了一个新的函数）
 *          （2）触发场景：普通点击“选择文件”按钮进行文件选择不会触发两次，但是我习惯ctrl+c ctrl+v 粘贴文件的时候（照此逻辑，拖拽也会触发该bug，但实则没触发，具体看第4点），会触发两次上传事件，因为在1.3版本时候尝试加入了可以支持bootstrap的模态框进行复制粘贴进文件的操作，并且偷懒监听了整个docuemnt.body进行粘贴（在自己挖的坑里爬呀爬呀爬！）。所以造成第二个模态框打开进行粘贴时，会同时把该文件都粘贴进了第一个模态框和第二个模态框中，形成两个实例的文件队列里都会存在文件，所以触发两次上传。
 *          （3）BUG验证方法：可以把【上传完自动关闭】和【自动上传】和【关闭窗口时清空队列】,共3个的配置先关闭，然后打开一个上传框，粘贴进文件，关闭上传框，再打开第二个，粘贴文件，关闭，再打开第一个上传框，发现第一个上传框里出现了两个文件，也可以使用uploader.getFiles()方法查看队列中的文件数量
 *          （4）BUG修复方法：问题分析清楚了超级简单，你可以只监听上传的的那个模态框(已经包括遮罩了)，调整paste配置，不要监听整个document.body进行粘贴图片，监听模态框即可，因为触发模态框的遮罩已经覆盖了整个浏览器窗口了（这点dnd的配置就配得很nice，只监听了模态框，所以不触发该bug）
 *          （6）至此，所有事件先移除一遍再添加；粘贴的配置一改，perfect，迭代1.6版本！
 * 
 * 1.7版本说明：
 *      open时候修改为每次都新创建uploader对象，这样才会在open时候更新按钮名称，title以及上传url等相关东西时候生效
 *      取消：绑定uploader的自定义事件时候先移除事件再绑定（组件自己的事件和原生js的不一样，如果移除了会导致BUG，上传完成后不会触发finished事件，导致3个以上附件同事传，只会上传3个）
 *      已知BUG:
 *          1.上传完成自动关闭窗口的配置打开后，选择一批文件进去，传完6个后就关闭了窗口，剩下的就不再上传了（产生原因是因为上传成功一个文件后就去关闭窗口，而关闭窗口里就有重置文件队列，导致后面的文件不会上传）
 *          2.该组件会自动压缩jpg文件，而且有些jpg文件压了之后变成了0kb，所以该配置需要强制关闭，需要压缩的话去服务器端进行压缩
 * 
 * 1.8版本说明：
 *          1.把关闭窗口的操作放在全部文件上传完uploadFinished事件中，再进行关闭，就能解决1.7中的bug1
 *          2.增加配置：上传完关闭窗口的延迟，单个文件上传完是否移除进度条，以及延迟多少时间移除
 *          3.取消jpg图片压缩的配置，强制进行关闭（这个组件有bug）
 * 
 * 基本用法，引入该js文件和依赖的loserStarSweetAlertUtils.js，以及相关的bootstrap3的js
 * 1.创建配置对象
 var fileOpt = {
        //必选：自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
        flagId:"loserStar",
        //必选：标题
        title:'标题',
        //上传的url一般该参数不会在初始化时候就填，因为可能要打开窗口时候才决定得了传到哪个url上，所以可以open时候传入url
        url:"uploadFile.do",
        //最大允许上传的大小（单位byte，默认200MB=209715200byte,1G=1024MB,1MB=1024kb,1kb=1024byte）
        maxSize:209715200,
        //单个文件上传成功时候的回调,注意多文件上传时，每个文件上传成功都会触发一次（两个参数，file 文件信息, response 服务器响应信息）
        uploadSuccessCallback:uploadSuccessCallback,
        //单个文件上传时候校验的回调,可在此弹出一些相关的错误提示（此方法接收两个参数（object 文件一些相关信息, ret 服务端返回的数据），且必须有返回值，返回false则会触发uploadError事件,代表不能继续上传；返回true，则继续执行上传；不传入该方法，则默认返回true）
        uploadAcceptCallback:uploadAcceptCallback,
        //全部文件上传完成后的回调方法，一般用于刷新界面之类的(多个文件在列表的情况下，点击单个文件上传也会触发。点击全部上传的话，要全部上传完成才会触发一次)
        uploadFinishedCallback:uploadFinishedCallback,
        //附件类型集合（如果该参数有值，则该组件则可拥有一个附件类型选择的下拉框，并且上传的url会自动添加上file_type参数）
        fileTypeList:[{name:"公共文件",value:"common"},{name:"其它文件",value:"other"}],
        //允许上传的文件后缀，不配置该项则所有类型可上传
        suffix:["jpg","png"],
        //是否创建缩略图
        isMakeThumb:true,
        //选完文件是否自动上传
        autoUpload:false,
        //是否开启拖拽上传Drag And Drop，默认开启
        dnd:true,
        //是否开启粘贴上传，默认开启
        paste:true,
        //上传按钮的名称，默认为上传附件
        btnName:"选择文件",
        //是否允许选择多个文件，默认为多个文件
        multiple:true,
        // [默认值：'file'] 设置文件上传域的name。
        fileVal:"file",
        //请求方式，默认POST，还可以设置GET，GET时候formData参数就无效了
        method:"POST",
        //上传成功后是否自动关闭窗口，默认为false。当然也可以使用uploadSuccessCallback这个回调方法里自己去写关闭
        autoClose:false,
        //上传成功后多少毫秒后自动关闭窗口，默认2秒
        autoCloseDelay:2000,
        //关闭时是否清除队列中的附件
        isClose_DeleteAtt = true,
        //可配置多个text文本框，填写值
        TextMap:[{"文本框名称":"文本框的相关id"}],
        //上传成功的附件是否自动移除相关的进度显示（默认移除）
        isAutoRemoveUploadSuccessFile:true,
        //上传成功的附件移除进度条的延迟多少毫秒，默认2秒
        autoRemoveUploadSuccessFileDelay:2000
    }
 2.传入配置对象，生成附件上传组件的对象
 loserStarFileWindow = new loserStarFileUploadBootstrapWindow_WebUploader(fileOpt);
 3.打开上传窗口，可传入url,loserStarFileWindow.open(url);
 4.关闭上传窗口，loserStarFileWindow.close();
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
        this.fileTypeName = (opt.fileTypeName != undefined && opt.fileTypeName != null && opt.fileTypeList.fileTypeName != "") ? opt.fileTypeName : "";//附件类型的标题
        this.fileTypeList = (opt.fileTypeList != undefined && opt.fileTypeList != null && opt.fileTypeList.length != 0) ? opt.fileTypeList : null;//附件类型
        this.fileTypeListRemarks = (opt.fileTypeListRemarks != undefined && opt.fileTypeListRemarks != null && opt.fileTypeListRemarks == "") ? opt.fileTypeListRemarks : "指定附件类型并选择文件进行上传";//附件类型的说明
        this.url = opt.url;//默认上传的url
        this.uploadSuccessCallback = opt.uploadSuccessCallback;//上传成功时候的回调（两个参数，file 文件信息, response 服务器响应信息）
        this.uploadAcceptCallback = opt.uploadAcceptCallback;//上传时候校验的回调（此方法接收两个参数（object 文件一些相关信息, ret 服务端返回的数据），且必须有返回值，返回false则会触发uploadError事件,代表不能继续上传；返回true，则继续执行上传；不传入该方法，则默认返回true）
        this.uploadFinishedCallback = opt.uploadFinishedCallback;//上传完成的回调
        this.title = opt.title ? opt.title : "";//标题
        this.suffix = opt.suffix ? opt.suffix : [];//可上传的后缀
        this.isMakeThumb = (opt.isMakeThumb != undefined && opt.isMakeThumb != null) ? opt.isMakeThumb : true;//是否创建缩略图,默认创建
        this.autoUpload = (opt.autoUpload != undefined && opt.autoUpload != null) ? opt.autoUpload : false;//选完图片是否自动上传，默认关闭
        this.dnd = (opt.dnd != undefined && opt.dnd != null) ? opt.dnd : true;//是否开启拖拽上传Drag And Drop，默认开启
        this.paste = (opt.paste != undefined && opt.paste != null) ? opt.paste : true;//是否开启粘贴上传，默认开启
        this.btnName = opt.btnName ? opt.btnName : "选择文件";//上传按钮的名称，默认为上传附件
        this.multiple = (opt.multiple != undefined && opt.multiple != null) ? opt.multiple : true;//是否允许选择多个文件，默认为多个文件
        this.fileVal = opt.fileVal ? opt.fileVal : "file";// [默认值：'file'] 设置文件上传域的name。
        this.method = opt.method ? opt.method : "POST";//请求方式，默认POST，还可以设置GET，GET时候formData参数就无效了
        this.autoClose = (opt.autoClose!=undefined&&opt.autoClose!=null) ? opt.autoClose : false;//上传成功后是否自动关闭窗口，默认为false。当然也可以使用uploadSuccessCallback这个回调方法里自己去写关闭
        this.autoCloseDelay = (opt.autoCloseDelay != undefined && opt.autoCloseDelay != null) ? opt.autoCloseDelay : 2000;//上传成功后多少毫秒后自动关闭窗口，默认2秒
        this.isClose_DeleteAtt = (opt.isClose_DeleteAtt != undefined && opt.isClose_DeleteAtt != null) ? opt.isClose_DeleteAtt : true;//是否关闭时清除附件，传true或者不传时清除，如果传false就不请除
        this.TextMap = (opt.TextMap != undefined && opt.TextMap != null && opt.TextMap.size > 0) ? opt.TextMap : null;//可配置多个text文本框，填写值
        this.isAutoRemoveUploadSuccessFile = (opt.isAutoRemoveUploadSuccessFile != undefined && opt.isAutoRemoveUploadSuccessFile != null) ? opt.isAutoRemoveUploadSuccessFile : true;//上传成功的附件是否自动移除相关的进度显示（默认移除）
        this.autoRemoveUploadSuccessFileDelay = (opt.autoRemoveUploadSuccessFileDelay != undefined && opt.autoRemoveUploadSuccessFileDelay != null) ? opt.autoRemoveUploadSuccessFileDelay : 2000;//上传成功的附件移除进度条的延迟多少毫秒，默认2秒
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
        text += "                        <h3 class=\"modal-title\">" + self.title + "</h3>";
        if (self.fileTypeList != undefined && self.fileTypeList != null && self.fileTypeList.length > 0) {
            if (self.fileTypeListRemarks != null && self.fileTypeListRemarks != "") {
                text += "                        <h4 class=\"modal-title\">" + self.fileTypeListRemarks + "</h4>";
            }
            text += "                        <div class=\"form-group\">";
            text += "                            <label for=\"" + self.flagId + "_FileType\" >" + self.fileTypeName + "</label>";
            text += "                            <div>";
            text += "                                <select id=\"" + self.flagId + "_FileType\" name=\"loserStarFileType\"  class=\"form-control\" style=\"width: 100%; height: 35px;\">";
            for (var i = 0; i < self.fileTypeList.length; i++) {
                var tmp = self.fileTypeList[i];
                text += "                                    <option value=\"" + tmp.value + "\">" + tmp.name + "</option>";
            }
            text += "                                </select>";
            text += "                            </div>";
            text += "                        </div>";
        }
        if (self.TextMap != null) {
            for (let [key, value] of self.TextMap) {
                text += "                        <div class=\"form-group\">";
                text += "                            <label for=" + self.flagId + "_" + value + ">" + key + "</label>";
                text += "                            <div class>";
                text += "                               <input type=\"text\" id=" + self.flagId + "_" + value + " maxlength=\"100\" class=\"form-control\" style=\"width: 100%; height: 35px;\">";
                text += "                                </select>";
                text += "                            </div>";
                text += "                        </div>";
            }
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
    },
    /**
     * 打开上传窗口
     * @param {*} url 上传的url
     * @param {*} formData 附带的数据（请求方式为GET时候无效）
     */
    open: function (url, formData) {
        var self = this;
        $("#" + self.flagId + "_FileWindow").modal({
            keyboard: false,
            backdrop: 'static'
        });

        //打开完之后才能去初始化百度的上传组件，否则会有问题
        $("#" + self.flagId + "_FileWindow").off("shown.bs.modal");//绑定前要先去掉事件，否则会重复
        $("#" + self.flagId + "_FileWindow").on("shown.bs.modal", function (e) {
            console.log("---------------初始化loserStarFileUploadBootstrapWindow_WebUploader---------------------");
            // // 初始化Web Uploader
            self.uploader = WebUploader.create({
                //{Selector} [可选] [默认值：undefined] 指定Drag And Drop拖拽的容器，如果不指定，则不启动。
                dnd: self.dnd ? $("#" + self.flagId + "_FileWindow") : undefined,
                //{Selector} [可选] [默认值：false] 是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。
                disableGlobalDnd: false,
                //{Selector} [可选] [默认值：undefined] 指定监听paste事件的容器，如果不指定，不启用此功能。此功能为通过粘贴来添加截屏的图片。建议设置为document.body.
                paste: self.paste ? $("#" + self.flagId + "_FileWindow") : undefined,
                // 选完文件后，是否自动上传。
                auto: self.autoUpload,
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                // pick: "#" + self.flagId + "_Picker",
                pick: {
                    id: "#" + self.flagId + "_Picker",
                    innerHTML: self.btnName,
                    multiple: self.multiple,
                },
                //可重复上传
                duplicate: true,
                // 服务器端的文件接收地址。
                server: url ? url : self.url,
                // 上传文件时，是否附带参数
                formData: (formData != undefined && formData != null) ? formData : {},
                // 文件上传域的名称，默认为file
                fileVal: self.fileVal,
                //请求方式 GET和POST,GET时候formData参数无效
                method: self.method,
                //是否压缩图片（官方会默认压缩jpeg图片，loserStar：一定要关闭，有bug）
                compress: false,
            });
            //阻止此事件可以拒绝某些类型的文件拖入进来。目前只有 chrome 提供这样的 API，且只能通过 mime-type 验证。
            // self.uploader.off("dndAccept");//绑定前要先去掉事件，否则会重复
            self.uploader.on("dndAccept", function (items) {
                console.log('阻止此事件可以拒绝某些类型的文件拖入进来');
            });
            //当文件被加入队列之前触发，此事件的handler返回值为false，则此文件不会被添加进入队列。
            // self.uploader.off("beforeFileQueued");//绑定前要先去掉事件，否则会重复
            self.uploader.on("beforeFileQueued", function (file) {
                console.log('当文件被加入队列之前触发');
                if (self.suffix != undefined && self.suffix != null && self.suffix.length > 0) {
                    //需要检查文件后缀
                    var isOk = false;
                    self.suffix.find((value, index, arr) => {
                        if (value == file.ext) {
                            isOk = true;
                        }
                    });
                    if (!isOk) {
                        loserStarSweetAlertUtils.alertError("上传的文件不支持" + file.ext + "格式");
                        return false;
                    }
                }
                if (self.maxSize) {
                    if (file.size > self.maxSize) {
                        var mb = (file.size / 1024 / 1024).toFixed(2);
                        var maxMb = (self.maxSize / 1024 / 1024).toFixed(2);
                        loserStarSweetAlertUtils.alertError("上传的文件不能大于" + maxMb + "MB，您选择的文件有" + mb + "MB");
                        return false;
                    }
                }
            });
            // 当文件被加入队列以后触发。
            // self.uploader.off("fileQueued");//绑定前要先去掉事件，否则会重复
            self.uploader.on('fileQueued', function (file) {
                console.log('当文件被加入队列以后触发。');
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
                text += "							 <img id=\"" + self.flagId + "_" + file.id + "Thumb\" />";
                text += "                            <div id=\"" + self.flagId + "_" + file.id + "_FileListProgress\" class=\"progress\">";
                text += "                                <div id=\"" + self.flagId + "_" + file.id + "_FileListProgressSub\" class=\"progress-bar progress-bar-striped active\" role=\"progressbar\" aria-valuenow=\"45\"";
                text += "                                    aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 0%\">";
                text += "                                   <span id=\"" + self.flagId + "_" + file.id + "_FileListProgressSubSpan\"></span>";
                text += "                                </div>";
                text += "                            </div>";
                text += "                        </li>";
                $("#" + self.flagId + "_FileListUl").append(text);

                if (self.isMakeThumb) {
                    // 创建缩略图
                    // 如果为非图片文件，可以不用调用此方法。
                    // thumbnailWidth x thumbnailHeight 为 100 x 100
                    self.uploader.makeThumb(file, function (error, src) {
                        if (error) {
                            $("#" + self.flagId + "_" + file.id + "Thumb").replaceWith('<span>不能预览</span>');
                            return;
                        }

                        $("#" + self.flagId + "_" + file.id + "Thumb").attr('src', src);
                    }, 100, 100);
                }

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

            //当一批文件添加进队列以后触发。
            self.uploader.on("filesQueued", function (file) {
                console.log('当一批文件添加进队列以后触发。');
            });
            //当文件被移除队列后触发。
            self.uploader.on("fileDequeued", function (file) {
                console.log('当文件被移除队列后触发。');
            });
            //当 uploader 被重置的时候触发。
            self.uploader.on("reset", function () {
                console.log('当 uploader 被重置的时候触发。');
            });
            //当开始上传流程时触发。
            self.uploader.on("startUpload", function () {
                console.log('当开始上传流程时触发。');
            });
            //全部上传完成
            self.uploader.on('uploadFinished', function () {
                console.log('全部上传完成');
                if (self.autoClose){
                    setTimeout(function () {
                        //延迟一段时间后关闭上传窗口
                        self.close();//关闭窗口
                    }, self.autoCloseDelay);
                }
                $("#" + self.flagId + "_UploadAllBtn").attr("disabled", false);//恢复全部上传按钮禁用状态
                if (self.uploadFinishedCallback) {
                    self.uploadFinishedCallback();
                }
            });
            //某个文件开始上传前触发，一个文件只会触发一次。
            self.uploader.on('uploadStart', function (file) {
                console.log("某个文件开始上传前触发，一个文件只会触发一次。");
                var btnSelf = this;
                var file_id = $(btnSelf).attr("file_id");//取出文件id
                console.log(file_id);
                if (self.fileTypeList != undefined && self.fileTypeList != null && self.fileTypeList.length > 0) {
                    var value = $("#" + self.flagId + "_" + file.id + "_FileType").attr("value");
                    self.uploader.options.formData.file_type = value;
                    // if (self.uploader.options.server) {
                    //     self.uploader.options.server = loserStarJsUtils.replaceUrlParams(loserStarJsUtils.parseURL(self.uploader.options.server), {"file_type": value});
                    //
                    // }
                }
                if (self.TextMap != undefined && self.fileTypeList != null) {
                    for (let [key, value] of self.TextMap) {
                        self.uploader.options.formData[value] = document.getElementById(self.flagId + "_" + value).value;
                    }

                }
            });
            //当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
            self.uploader.on("uploadBeforeSend", function (object, data, headers) {
                console.log("当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数");
            });
            //当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。如果此事件handler返回值为false, 则此文件将派送server类型的uploadError事件。
            self.uploader.on("uploadAccept", function (object, ret) {
                console.log("当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。");
                if (self.uploadAcceptCallback) {
                    return self.uploadAcceptCallback(object, ret);
                } else {
                    return true;
                }
            });
            //上传过程中触发，携带上传进度。
            self.uploader.on('uploadProgress', function (file, percentage) {
                console.log("上传过程中触发，携带上传进度。");
                $("#" + self.flagId + "_" + file.id + "_FileListUploadBtn").attr("disabled", true);//上传时禁用上传按钮
                $("#" + self.flagId + "_UploadAllBtn").attr("disabled", true);//上传时禁用全部上传按钮
                var percentage_2 = (percentage * 100).toFixed(2);
                $("#" + self.flagId + "_" + file.id + "_FileListProgressSub").css("width", percentage_2 + "%");//设置进度条百分比
                $("#" + self.flagId + "_" + file.id + "_FileListProgressSubSpan").text(percentage_2 + "%");//设定上传进度百分比文字显示
            });
            //当文件上传出错时触发。
            self.uploader.on('uploadError', function (file, reason) {
                console.log("当文件上传出错时触发。");
                $("#" + self.flagId + "_" + file.id + "_FileListUploadBtn").attr("disabled", false);//上传出错时恢复禁用状态
                $("#" + self.flagId + "_UploadAllBtn").attr("disabled", true);//上传出错时恢复全部上传按钮禁用状态
                $("#" + self.flagId + "_" + file.id + "_FileListProgressSub").addClass("progress-bar-danger");//把进度条设为红色
                $("#" + self.flagId + "_" + file.id + "_FileListProgressSubSpan").text("上传出错");
            });
            //当文件上传成功时触发。
            self.uploader.on('uploadSuccess', function (file, response) {
                console.log('当文件上传成功时触发。');
                $("#" + self.flagId + "_" + file.id + "_FileListProgressSub").addClass("progress-bar-success");//把进度条设为绿色
                if (self.isAutoRemoveUploadSuccessFile){
                    //需要移除上传成功的文件
                    setTimeout(function () {
                        //延迟一段时间后移除该文件的li
                        $("#" + self.flagId + "_" + file.id + "_FileListLi").remove();
                    }, self.autoRemoveUploadSuccessFileDelay);
                }
                if (self.uploadSuccessCallback) {
                    self.uploadSuccessCallback(file, response);
                }

            });
            //不管成功或者失败，文件上传完成时触发。
            self.uploader.on('uploadComplete', function (file) {
                console.log('不管成功或者失败，文件上传完成时触发。');
            });
            /**
             当validate不通过时，会以派送错误事件的形式通知调用者。通过upload.on('error', handler)可以捕获到此类错误，目前有以下错误会在特定的情况下派送错来。
             Q_EXCEED_NUM_LIMIT 在设置了fileNumLimit且尝试给uploader添加的文件数量超出这个值时派送。
             Q_EXCEED_SIZE_LIMIT 在设置了Q_EXCEED_SIZE_LIMIT且尝试给uploader添加的文件总大小超出这个值时派送。
             Q_TYPE_DENIED 当文件类型不满足时触发。。
             */
            self.uploader.on("type", function (type) {
                console.log('当validate不通过时，会以派送错误事件的形式通知调用者');
            });
            let btn = $("#" + self.flagId + "_UploadAllBtn");
            btn.off("click");
            btn.on("click", function () {
                self.uploader.upload();//开始上传所有,修改为在此绑定，否则会出现按钮失效的问题
            });
        });
        $("#" + self.flagId + "_FileWindow").off("hidden.bs.modal");
        $("#" + self.flagId + "_FileWindow").on("hidden.bs.modal", function (e) {
            if (self.isClose_DeleteAtt) {
                self.uploader.reset();
                $("#" + self.flagId + "_FileListUl").empty();
            }
        });
    },
    close: function () {
        var self = this;
        $("#" + self.flagId + "_FileWindow").modal('hide');
    }
}
