
$(function () {
    //初始化下拉框数据
    //var selectOptionHtml = loserStarBoostrapUtils.getSelectHtml(dict.字典类型, true, "value", "name");
    //$("#select的id").html(selectOptionHtml);
    initData();//初始化主数据
});

/**
 * 初始化数据
 */
function initData() {
    var dict_id = $("#dict_id").val();
    if (!loserStarJsUtils.checkObj(dict_id)) {
        $("#form_title2").text("新增");
        initControl();//初始化控件
    } else {
        $("#form_title2").text("修改");
        sysDictAction.getById(dict_id, function (data, msg, result) {
            $("#dict_id").val(data.dict_id);
            $("#dict_value").val(data.dict_value);
            $("#dict_name").val(data.dict_name);
            $("#dict_type").val(data.dict_type);
            $("#dict_remarks").val(data.dict_remarks);
            $("#dict_c_name").val(data.dict_c_name);
            $("#dict_css_style").val(data.dict_css_style);
            $("#dict_sort").val(data.dict_sort);
            $("#del").val(data.del);
            $("#create_time").val(data.create_time);
            $("#create_user_code").val(data.create_user_code);
            $("#create_user_name").val(data.create_user_name);
            $("#update_time").val(data.update_time);
            $("#update_user_code").val(data.update_user_code);
            $("#update_user_name").val(data.update_user_name);
            initControl();//初始化控件

            //select控件赋值
            //loserStarJsUtils.selectedOption("#select的id", data.字段值);

            //radio控件赋值
            //loserStarJsUtils.setSelectedForRadio("#radio的name", data.字段值);

            //textarea自动高度
            //loserStarJsUtils.autoTextAreaHeight($("#remarks")[0]);
        });
    }
}

/**
 * 初始化一些页面上的组件（通常在dom数据加载完成后再执行，否则会有诡异问题，比如颜色选择器点开都是白色，而且会自动给你赋值白色）
 */
function initControl(){
    //初始话颜色选择器bootstrap-colorpicker：https://github.com/farbelous/bootstrap-colorpicker
    $('.my-colorpicker1').colorpicker();
    //初始化高级下拉框select2:https://select2.github.io/
    $('.select2').select2({
        placeholder: '请选择'
    });
}