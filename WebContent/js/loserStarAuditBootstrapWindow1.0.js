/**
 * 基于bootstrap3的模态对话框封装的审核窗口，可进行radio选项选择和填写审核意见
 * 依赖：loserStarJsUtils.js
 * 
用法：
    var auditOpt = {};
    auditOpt.flagId = "loserStarAuditWindow";
    auditOpt.title = "审核对话框";
    auditOpt.initCallback = function(){console.log("初始化审核框");};
    auditOpt.radioCfg = [{ id: "yes", value: "yes", name: "同意", isCheck:true }, { id: "no", value: "no", name: "不同意" }];
    auditOpt.btnCfg = [{
        id: "ok",
        name: "同意",
        //按钮情景，就bootstrap那积累，success，danger，info，waring等
        sight:"danger",
        //点击按钮的回调方法（参数1：选中的radio值 参数2：审核意见框的内容 参数3：open时候传进去的对象值）
        clickCallback:function(value,content,otherData){
            console.log(value);
            console.log(content);
        }}]
    auditWindow = new loserStarAuditBootstrapWindow(auditOpt);
   
*/
/**
 * 构造方法
 * @param {*} opt 配置项

 * @returns 
 */
var loserStarAuditBootstrapWindow = function (opt) {
    this.init(opt);
}

loserStarAuditBootstrapWindow.prototype = {
    constructor: loserStarAuditBootstrapWindow,
    init: function (opt) {
        this.flagId = opt.flagId;//自定义标识id（所有生成的元素的id都会以此开头，以防html节点互相污染）
        this.title = opt.title ? opt.title : '';//标题
        this.initCallback = opt.initCallback;//窗口打开时候的初始化事件回调方法（可在里面设置一些默认值）
        this.radioCfg = opt.radioCfg;//radio的配置
        this.btnCfg = opt.btnCfg;//按钮的配置
        //执行一些初始化的方法
        this.createElement();//初始化时候就执行一次渲染html
    },
    //渲染必要的html
    createElement: function () {
        var self = this;
        var text = "";
        text += "<!-- loserStarAuditBootstrapWindow---begin -->";
        text += "<div class=\"modal fade\" id=\"" + self.flagId + "_auditWindow\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"" + self.flagId + "_ModalLabel\">";
        text += "    <div class=\"modal-dialog\" role=\"document\">";
        text += "        <div class=\"modal-content\">";
        text += "            <div class=\"modal-header\">";
        text += "                <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\"><span";
        text += "                        aria-hidden=\"true\">&times;</span></button>";
        text += "                <h4 class=\"modal-title\" id=\"" + self.flagId + "_ModalLabel\">" + self.title + "</h4>";
        text += "            </div>";
        text += "            <div class=\"modal-body\">";
        text += "                <form>";
        text += "                    <div class=\"form-group\">";
        for (var i = 0; i < self.radioCfg.length; i++) {
            var tmp = self.radioCfg[i];
            text += "                        <div class=\"radio\">";
            text += "                            <label>";
            text += "                                <input type=\"radio\" name=\"" + self.flagId + "_radio\" id=\"" + self.flagId + "_" + tmp.id + "\" value=\"" + tmp.value + "\" " + (tmp.isCheck ? "checked" : "") + ">";
            text += "                                " + tmp.name;
            text += "                            </label>";
            text += "                        </div>";
        }
        text += "                    </div>";
        text += "                    <div class=\"form-group\">";
        text += "                        <label for=\"" + self.flagId + "_textArea\" class=\"control-label\">审批意见:</label>";
        text += "                        <textarea class=\"form-control\" id=\"" + self.flagId + "_textArea\"></textarea>";
        text += "                    </div>";
        text += "                </form>";
        text += "            </div>";
        text += "            <div class=\"modal-footer\">";
        text += "                <button id=\"" + self.flagId + "_cancel\" type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>";
        for (var i = 0; i < self.btnCfg.length; i++) {
            var tmp = self.btnCfg[i];
            text += "                <button id=\"" + self.flagId + "_" + tmp.id + "\" type=\"button\" class=\"btn btn-" + tmp.sight +"\">" + tmp.name + "</button>";
        }
        text += "            </div>";
        text += "        </div>";
        text += "    </div>";
        text += "</div>";
        text += "<!-- loserStarAuditBootstrapWindow---end -->";
        $("body").append(text);
        //绑定按钮的事件
        for (var i = 0; i < self.btnCfg.length; i++) {
            var tmp = self.btnCfg[i];
            $("#" + self.flagId + "_" + tmp.id + "").on("click", function () {
                var checkedValue = loserStarJsUtils.getSelectedForRadio(self.flagId + "_radio");
                var content = $("#" + self.flagId + "_textArea").val();
                if (tmp.clickCallback) {
                    tmp.clickCallback(checkedValue, content, self.otherData)
                }
                self.closeWindow();
            });
        }

    },
    //打开窗口（可以额外附件个数据，会回传到回调方法上）
    open: function (otherData) {
        var self = this;
        self.otherData = otherData;
        $("#" + self.flagId + "_auditWindow").modal({
            keyboard: false,
            backdrop: 'static'
        });

        //打开完之后的事件（先移除事件再绑定，否则重复open就会重复触发该事件）
        $("#" + self.flagId + "_auditWindow").unbind("shown.bs.modal");
        $("#" + self.flagId + "_auditWindow").on("shown.bs.modal", function (e) {
            if (self.initCallback) {
                self.initCallback(otherData);
            }
        });
    },
    //关闭对话框，可传入一个回调方法
    closeWindow: function (callback) {
        var self = this;
        $("#" + self.flagId + "_auditWindow").modal('hide');
        if (callback) {
            // （先移除事件再绑定，否则多次调用就会重复触发该事件）
            $("#" + self.flagId + "_auditWindow").unbind('hidden.bs.modal');
            $("#" + self.flagId + "_auditWindow").on('hidden.bs.modal', function (e) {
                callback();
                $("#" + self.flagId + "_auditWindow").unbind('hidden.bs.modal');
            });
        }
    }
}