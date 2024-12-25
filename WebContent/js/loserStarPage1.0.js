
/**
 * loserStar基于bootstrap3的前端分页组件，当前是以配合着jfinal的分页查询数据的方法进行封装的，属于后端分页
 * 用法：
 * 1.首先需要一个供分页组件渲染用的div，并设置好id
 * 2.为了同一个页面上可以同时多个地方使用本组件，调用loserStarPage.initPage(cfg)方法后会返回一个创建好的对象，多个组件请用不同变量存储。返回的对象中已提供相关方法操作分页。
 * 3.初始化时候配置对象cfg的参数如下：
 * pageId：渲染用的div的id，以此作为该组件的而唯一标识
 * gotoCusPageCallback：跳转到某个分页之后（自定义pageNumber）的回调方法，一般该回调设为查询数据的方法
 * gotoPreviousPageCallback：点击上一页回调方法（pageNumber--），一般该回调设为查询数据的方法
 * gotoNextPageCallback：点下一页回调方法（pageNumber++），一般该回调设为查询数据的方法
 * 4.返回的对象中提供的常用方法如下：
 * gotoCusPage:跳转到指定的页码（设置页码请通过setPageNumber方法进行设置）
 * gotoPreviousPage:跳转到上一页
 * gotoNextPage：跳转到下一页
 * setPageNumber：设置页码
 * getPageNumber：获取当前页码
 * setPageSize：设置每页多少条数据
 * getPageSize：获取每页多少条数据
 * setTotalPage：设置总页码多少
 * getTotalPage：获取总页码多少
 * setTotalRow：设置数据总数
 * getTotalRow：获取数据总数
 * hidePreBtn：隐藏上一页按钮
 * showPreBtn：显示上一页按钮
 * hideNextBtn：隐藏下一页按钮
 * showNextBtn：显示下一页按钮
 * 5.demo：
 * var userPage = {};//先搞坨变量放分页组件
 * //构建分页的配置
 * var pageCfg = {
        pageId:"userPage",
        gotoCusPageCallback: listPageEvent.queryUserList,
        gotoPreviousPageCallback: listPageEvent.queryUserList,
        gotoNextPageCallback: listPageEvent.queryUserList,
    }
    //生成分页对象
    userPage = new loserStarPage(pageCfg);

    //自定义的查询数据的方法
    var listPageEvent = {};
    listPageEvent.queryUserList = function(){
    //构建查询条件
    var queryObj = {};
    queryObj.user_code = $("#user_code").val();
    queryObj.user_name = $("#user_name").val();
    queryObj.mobile = $("#mobile").val();

    queryObj.pageNumber = userPage.getPageNumber();//获取当前页码当做查询参数
    queryObj.pageSize = userPage.getPageSize();//获取每页多少条当做查询参数

    //进行ajax请求后端拿到数据
    userAction.getUserPage(queryObj, function (data) {
        console.log(data);
        userPage.setPageNumber(data.pageNumber);//把后端返回的页码设置到分页组件中
        userPage.setPageSize(data.pageSize);//把后端返回的每页多少条设置到分页组件中
        userPage.setTotalPage(data.totalPage);//把后端总页数设置到分页组件中
        userPage.setTotalRow(data.totalRow);//把后端返回的数据总条数设置到分页组件中
        data.firstPage ? userPage.hidePreBtn() : userPage.showPreBtn();//根据后端返回的是否是第一页，隐藏或显示上一页按钮
        data.lastPage ? userPage.hideNextBtn() : userPage.showNextBtn();//根据后端返回的是否是尾页，隐藏或显示下一页按钮

        //渲染数据列表
        var text = "";
        for (var i = 0; i < data.list.length; i++) {
            var tmp = data.list[i];
            text += "                        <tr>";
            text += "                            <td>" + tmp.user_code + "</td>";
            text += "                            <td>" + tmp.user_name + "</td>";
            text += "                            <td>" + tmp.mobile + "</td>";
            text += "                            <td><button type=\"button\" class=\"btn btn-primary\">编辑</button><button type=\"button\" class=\"btn btn-danger\">删除</button></td>";
            text += "                        </tr>";
        }
        $("#dataList_tbody").html(text);
    });
}
 */
var loserStarPage = function (cfg) {
    this.initPage(cfg);
}

loserStarPage.prototype = {
    constructor: loserStarPage,
    initPage: function (cfg) {
        if (!cfg) { alert("分页组件初始化异常，没有定义cfg配置对象"); }
        var tmp = this;
        if (!cfg.pageId) { alert("分页组件初始化异常，没有定义pageId"); }
        tmp.pageId = cfg.pageId;//div的id
        if (!cfg.gotoCusPageCallback) { alert("分页组件初始化异常，没有定义gotoCusPageCallback"); }
        tmp.gotoCusPageCallback = cfg.gotoCusPageCallback;//跳转到某个分页之后（自定义pageNumber）的回调方法
        if (!cfg.gotoPreviousPageCallback) { alert("分页组件初始化异常，没有定义gotoPreviousPageCallback"); }
        tmp.gotoPreviousPageCallback = cfg.gotoPreviousPageCallback;//点击上一页回调方法（pageNumber--）
        if (!cfg.gotoNextPageCallback) { alert("分页组件初始化异常，没有定义gotoPreviousPageCallback"); }
        tmp.gotoNextPageCallback = cfg.gotoNextPageCallback;//点下一页回调方法（pageNumber++）
        tmp.renderHtml();//初始化时候就自动执行一次渲染html的方法，否则又得麻烦自己去手工调用一次
    },
    /**
     * 渲染分页组件的html,并给各个按钮绑定上方法
     */
    renderHtml: function () {
        var tmp = this;
        var text = "";
        text += "<nav aria-label=\"...\">";
        text += "                        <ul class=\"pager\">";
        text += "                            <li>数据共<label id=\"" + tmp.pageId + "_totalRow\"></label>条，每页<label id=\"" + tmp.pageId + "_pageSize\"></label>条，共<label id=\"" + tmp.pageId + "_totalPage\"></label>页，当前第<input type=\"number\" name=\"\" id=\"" + tmp.pageId + "_pageNumber\" class=\"form-control\" style=\"display:inline-block;width:auto;width: 5%;\">页</li>";
        text += "                            <li id=\"" + tmp.pageId + "_cus_btn\"><a href=\"javascript:void(0);\">跳转</a></li>";
        text += "                            <li id=\"" + tmp.pageId + "_previous_btn\"><a  href=\"javascript:void(0);\">上一页</a></li>";
        text += "                            <li id=\"" + tmp.pageId + "_next_btn\"><a href=\"javascript:void(0);;\">下一页</a></li>";
        text += "                        </ul>";
        text += "                    </nav>";
        $("#" + tmp.pageId).html(text);

        $("#" + tmp.pageId + "_cus_btn").unbind("click");//添加事件前先移除事件，否则会造成添加多个事件，导致执行多次
        $("#" + tmp.pageId + "_cus_btn").on("click", function (e) {
            if (tmp.gotoCusPage) {
                tmp.gotoCusPage();
            }
        });

        $("#" + tmp.pageId + "_previous_btn").unbind("click");//添加事件前先移除事件，否则会造成添加多个事件，导致执行多次
        $("#" + tmp.pageId + "_previous_btn").on("click", function (e) {
            if (tmp.gotoPreviousPage) {
                tmp.gotoPreviousPage();
            }
        });

        $("#" + tmp.pageId + "_next_btn").unbind("click");//添加事件前先移除事件，否则会造成添加多个事件，导致执行多次
        $("#" + tmp.pageId + "_next_btn").on("click", function (e) {
            if (tmp.gotoNextPage) {
                tmp.gotoNextPage();
            }
        });
    },
    /**
     * 跳转到自定义页码（并调用初始化传入的gotoCusPageCallback回调）
     */
    gotoCusPage: function () {
        var tmp = this;
        var pageNumber = parseInt($("#" + tmp.pageId + "_pageNumber").val());
        $("#" + tmp.pageId + "_pageNumber").val(pageNumber);
        if (tmp.gotoCusPageCallback) { tmp.gotoCusPageCallback(); }
    },
    /**
     * 跳转上一页（并调用初始化传入的gotoPreviousPageCallback回调）
     */
    gotoPreviousPage: function () {
        var tmp = this;
        var pageNumber = parseInt($("#" + tmp.pageId + "_pageNumber").val()) - 1;
        $("#" + tmp.pageId + "_pageNumber").val(pageNumber);
        if (tmp.gotoPreviousPageCallback) { tmp.gotoPreviousPageCallback(); }
    },
    /**
     * 跳转下一页（并调用初始化传入的gotoNextPageCallback回调）
     */
    gotoNextPage: function () {
        var tmp = this;
        var pageNumber = parseInt($("#" + tmp.pageId + "_pageNumber").val()) + 1;
        $("#" + tmp.pageId + "_pageNumber").val(pageNumber);
        if (tmp.gotoNextPageCallback) { tmp.gotoNextPageCallback(); }
    },
    /**
     * 获取当前页码
     * @returns 
     */
    getPageNumber: function () {
        var tmp = this;
        return $("#" + tmp.pageId + "_pageNumber").val();
    },
    /**
     * 设置当前页码
     * @param {*} pageNumber 
     */
    setPageNumber: function (pageNumber) {
        var tmp = this;
        $("#" + tmp.pageId + "_pageNumber").val(pageNumber);
    },
    /**
     * 获取每页多少条
     * @returns 
     */
    getPageSize: function () {
        var tmp = this;
        return $("#" + tmp.pageId + "_pageSize").text();
    },
    /**
     * 设置每页多少条
     * @param {*} pageSize 
     */
    setPageSize: function (pageSize) {
        var tmp = this;
        $("#" + tmp.pageId + "_pageSize").text(pageSize);
    },
    /**
     * 设置总页数
     * @param {*} totalPage 
     */
    setTotalPage: function (totalPage) {
        var tmp = this;
        $("#" + tmp.pageId + "_totalPage").text(totalPage);
    },
    /**
     * 获取页面总数
     * @returns 
     */
    getTotalPage: function () {
        var tmp = this;
        return $("#" + tmp.pageId + "_totalPage").text();
    },
    /**
     * 设置数据总条数
     * @param {*} totalRow 
     */
    setTotalRow: function (totalRow) {
        var tmp = this;
        $("#" + tmp.pageId + "_totalRow").text(totalRow);
    },
    /**
     * 获取数据总条数
     * @returns 
     */
    getTotalRow: function () {
        var tmp = this;
        return $("#" + tmp.pageId + "_totalRow").text();
    },
    /**
     * 隐藏上一页按钮
     */
    hidePreBtn: function () {
        var tmp = this;
        $("#" + tmp.pageId + "_previous_btn").hide();
    },
    /**
     * 显示上一页按钮
     */
    showPreBtn: function () {
        var tmp = this;
        $("#" + tmp.pageId + "_previous_btn").show();
    },
    /**
     * 隐藏下一页按钮
     */
    hideNextBtn: function () {
        var tmp = this;
        $("#" + tmp.pageId + "_next_btn").hide();
    },
    /**
     * 显示下一页按钮
     */
    showNextBtn: function () {
        var tmp = this;
        $("#" + tmp.pageId + "_next_btn").show();
    }
}










