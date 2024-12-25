
var loserStarDatatablesCfg = {};
/**
 * 初始化datatables，该方法得放在渲染html的dom数据之前，否则刷新数据会有问题
 * 具体配置参考 http://datatables.club/reference/option/
 */
loserStarDatatablesCfg.initDataTables = function (tableId) {
    var table = $('#' + tableId).DataTable({
        // columnDefs: [{ "orderable": false, "targets": [0] }],
        //使用配置项的方式
        // language: datatablesCfg.language,
        //使用配置文件的方式
        language: {
            url: '../bower_components/DataTables-1.13.6/i18n/zh.json'
        },
        //初始化一个新的Datatables，如果已经存在，则销毁（配置和数据），成为一个全新的Datatables实例
        destroy: true,
        //当显示更少的记录时，是否允许表格减少高度
        scrollCollapse: false,
        //是否保存表格状态
        stateSave: false,
        //是否自动宽度
        autoWidth: true,
        //水平滚动
        scrollX: true,
        //垂直滚动
        scrollY: 500,
        //移动端适配
        responsive: false,
        //选择行
        select: 'multi',
        //拖动列
        colReorder: true,
        //是否开启排序
        ordering: true,
        //是否显示处理状态(排序的时候，数据很多耗费时间长的话，也会显示这个)
        processingOption: true,
        //控制是否显示表格左下角的信息
        info: false,
        // 是否开启本地分页
        paging: false,
        /**
         * 分页按钮显示选项
         *  numbers 只显示数字 （1.10.8版本）
            simple 只有上一页和下一页两个按钮
            simple_numbers 上一页和下一页两个按钮，加上页数按钮
            full 首页，尾页，上一页和下一页四个按钮
            full_numbers 首页，尾页，上一页和下一页四个按钮,加上数字按钮
            first_last_numbers 首页，尾页两个按钮,加上数字按钮
         */
        pagingTypeOption: "full_numbers",
        //改变初始化页长度（每页多少条数据）
        // pageLengthOption:10,
        // lengthMenu: [
        //     [10, 25, 50, -1],
        //     [10, 25, 50, 'All']
        // ],
        //是否允许调整每页多少条
        lengthChange: false,
        //是否允许Datatables开启本地搜索
        searching: true,
        //高亮显示表格中排序的列
        orderCellsTopOption: true,
        //表格周围元素显示
        dom: '<"toolbar"B>frtipl',
        processing: true,
        buttons: [
            {
                text: '按列表导出',
                enabled: true,
                extend: 'excel',
                key:'这是key',
                name:'这是name',
                init: function (dt, node, config) {
                    node.removeClass("btn-default");
                    node.addClass("btn-primary");
                },
            }
        ],
    });
    //移除单击行事件
    table.off("click", "tr");//先移除之前的事件（否则刷新后会多次触发事件）
    //单击行事件
    table.on('click', 'tr', function (e) {
        var jqRow = $(this);//获取行的jQuery对象
        var row = table.row(this);//获取tr对象
        var rowid = row.id();
        ;//获取tr的id
        var index = row.index();//获取序号
        var rowData = row.data();//获取行数据
    });

    //给表头的checkbox添加全选的事件方法
    $(".checkall", table.containers()).on('click', function () {
        var check = $(this).prop("checked");
        if (check) {
            table.rows().select();
        }
        else {
            table.rows().deselect();
        }
    });

    //当翻页时候，需要setTimeout才能获取到隐藏的元素
    table.on('page.dt', function () {
        setTimeout(function () {
            let arr = table.getPageRows();
            let ids = table.rows({ selected: true }).ids();
            for (var i = 0; i < ids.length; i++) {
                if (arr.indexOf(ids[i]) > -1) {
                    $('#' + ids[i] + '_checkbox').find('.checkchild').prop("checked", true);
                }
            }
            ids = table.rows({ selected: false }).ids();
            for (var i = 0; i < ids.length; i++) {
                if (arr.indexOf(ids[i]) > -1) {
                    $('#' + ids[i] + '_checkbox').find('.checkchild').prop("checked", false);
                }
            }
        }, 1);
    });

    //当有行变成选中
    table.on('select', function (e, dt, type, indexes) {
        let ids = table.rows(indexes).ids().toArray();
        let arr = table.getPageRows();
        for (var i = 0; i < ids.length; i++) {
            if (arr.indexOf(ids[i]) > -1) {
                $('#' + ids[i] + '_checkbox').find('.checkchild').prop("checked", true);
            }
        }
    });

    //当有行变成未选中
    table.on('deselect', function (e, dt, type, indexes) {
        let ids = table.rows(indexes).ids().toArray();
        let arr = table.getPageRows();
        for (var i = 0; i < ids.length; i++) {
            if (arr.indexOf(ids[i]) > -1) {
                $('#' + ids[i] + '_checkbox').find('.checkchild').prop("checked", false);
            }
        }
    });

    //获取到当前分页的行
    table.getPageRows = function () {
        var info = table.page.info();
        let arr = [];
        for (var i = info.start; i < info.end; i++) {
            arr.push(table.row(i).id());
        }
        return arr;
    }

    //添加一个方法，可获取到选中行的数据
    table.getCheckedRows = function () {
        return table.rows('.selected').data().toArray();
    }

    //添加一个方法，可获取到选中行row的id集合
    table.getChkedIds = function () {
        return table.rows('.selected').ids().toArray();
    }

    //获取到已选中的数据对象
    table.getCheckedDatas = function () {
        var ids = table.rows('.selected').ids().toArray();
        var datas = [];
        for (var i = 0; i < ids.length; i++) {
            datas.push($("#" + ids[i]).data());
        }
        return datas;
    }
    return table;
}