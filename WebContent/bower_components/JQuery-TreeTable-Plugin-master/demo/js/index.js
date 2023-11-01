/**
 * Created by harris on 2017/1/17.
 */
$(function () {
    "use strict";

    var treetable = $('#treetable').BootstrapTreeTable({
//        levelSpacing: 20,//级次间距 px
//        column: 0,//指定排序列号
        expandlevel: 1,//默认展开级次
        expandAll: false,//是否全部展开
        collapseAll: false,//是否全部关闭
//        expendedIcon: '<span class="glyphicon glyphicon-menu-down"></span>',//非叶子节点展开图标
//        collapsedIcon: '<span class="glyphicon glyphicon-menu-right"></span>',//非叶子节点关闭图标
        maxResult: ''//搜索最大结果集，超过将停止返回结果
    }).on("initialized.bs.treetable", function () {
        var count = treetable.BootstrapTreeTable('getMaxLevel');
        createExpandButton(count);
        doDelete()
    });

    function createExpandButton(count) {
        for (var i = 1; i <= count; i++) {
            var $btn = $('<li><a href="#" class="expendlevel" data-level="' + i + '">' + i + '</a></li>');
            $('.pagination').append($btn);
        }
        $('.expendlevel').click(function (e) {
            e.preventDefault();
            var level = $(this).data('level');
            $('#treetable').BootstrapTreeTable('expendLevel', level);
        })
    }

    treetable.BootstrapTreeTable('searchNodeId', '337A2575C9404167AFE020D8C27D1C45');

    //    $('#treetable').BootstrapTreeTable('searchNodeName', '计算机');
    //    $('#treetable').BootstrapTreeTable('collapseAll');
    //    $('#treetable').BootstrapTreeTable('expendAll');
    //    $('#treetable').BootstrapTreeTable('expendLevel','2');
    //    $('#treetable').BootstrapTreeTable('searchNodeName');

    treetable.BootstrapTreeTable().on("show.bs.treetable", function (e, node) {
        //console.log('show', node);
    })
    treetable.BootstrapTreeTable().on("shown.bs.treetable", function (e, node) {
        //console.log('shown', node);

    })
    treetable.BootstrapTreeTable().on("hide.bs.treetable", function (e, node) {
        //console.log('hide', node);
    })
    treetable.BootstrapTreeTable().on("hidden.bs.treetable", function (e, node) {
        //console.log('hidden', node);
    })
    //    treetable.BootstrapTreeTable().on("initialized.bs.treetable", function (e) {
    //        console.log('loaded', this);
    //    })
    //    ;


    $('#serach').click(function () {
        var value = $('#serachvalue').val();
        var result = $('#treetable').BootstrapTreeTable('searchNodeName', value);
        $(this).text('查询(' + result + ')');
    })

    $('#expendAll').click(function () {
        $('#treetable').BootstrapTreeTable('expendAll');
    })

    $('#collapseAll').click(function () {
        $('#treetable').BootstrapTreeTable('collapseAll');
    })

    $('#reset').click(function () {
        $('#treetable').BootstrapTreeTable('reset');
        doDelete();
    })

    $('#destroy').click(function () {
        $('#treetable').BootstrapTreeTable('destroy');
    })

    $('#dodel').on('click', function (e) {
        $('#treetable').BootstrapTreeTable('removeById', '337A2575C9404167AFE020D8C27D1C45');
    })

    function doDelete(){
        $('.dodel').on('click', function (e) {
            e.preventDefault();
            var node = $(this).parents('tr');
            $('#treetable').BootstrapTreeTable('removeByNode', node);
        })
    }
});