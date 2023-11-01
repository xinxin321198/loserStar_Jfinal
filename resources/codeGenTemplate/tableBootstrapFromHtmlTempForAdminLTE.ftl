<#setting classic_compatible=true>
<!-- 页面框架代码 -->
${r'<#setting classic_compatible=true />'}
${r'<#include "public.html" />'}
<!DOCTYPE html>
<html>
${r'<@loserStarHeader title="'}${tableRemarks}${r'">'}
    <!-- 这里放生成的引入的js--begin -->
    <!--此部分代码放在head中引入该表的相关js--begin-->
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}Action.js"></script>
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}Form.js"></script>
    <script src="../view/${fristLowerClassName}/js/${fristLowerClassName}FormEvent.js"></script>
    <!--此部分代码放在head中引入该表的相关js--end-->
    <!-- 这里放生成的引入的js--end -->
    <script>
        //这里写自定义的js代码

    </script>
${r'</@loserStarHeader>'}

${r'<@loserStarBody title="'}${tableRemarks}${r'" title2="'}${tableRemarks}${r'">'}
    <div class="row">
        <div class="col-xs-12">
            <div class="box">
                <div class="box-header">
                    <h3 class="box-title">${tableRemarks}</h3>
                </div>
                <!-- /.box-header -->
                <div class="box-body">
                    <!-- 这里放生成的html代码 -->
                    <!-- 本段代码为代码生成器根据表[${tableName}]结构自动生成的bootstrap的表单html，犯懒时候从这里粘过去用----begin-->
                    <h1><small></small></h1>
                    <div class="container-fluid">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h4>${tableRemarks}
                                </h4>
                            </div>
                            <div class="panel-body">
                                <form class="form-horizontal">
                                    <fieldset ${r'<#if op_type="view">disabled</#if>'}>
                                        <#list fieldList as field>
                                        <#--  这里实体里的bool会被freemarker转为字符串，所以要通过字符串true或false来判断  -->
                                        <#if field.isPrimaryKey=="true">
                                        <#--  主键禁用编辑，并且value添加freemarker的变量占位符  -->
                                        <div class="form-group">
                                            <label for="${field.name}" class="col-sm-2 control-label">${field.remarks}：</label>
                                            <div class="col-sm-10">
                                                <input type="text" class="form-control" id="${field.name}" value="${r'${'}${primaryKey}${r'}'}" disabled>
                                            </div>
                                        </div>
                                        <#else>
                                        <#--  非主键  -->
                                        <div class="form-group">
                                            <label for="${field.name}" class="col-sm-2 control-label">${field.remarks}：</label>
                                            <div class="col-sm-10">
                                                <#if field.type?starts_with("time")>
                                                <!-- <input type="time" class="form-control" id="begin_time" value="">可输入时分-->
                                                <!-- <input type="time" step="2" class="form-control" id="begin_time" value="">可输入时分秒-->
                                                <#--  时间日期控件  -->
                                                <input type="date" class="form-control" id="${field.name}" value="">
                                                <#elseif field.type?starts_with("tinyint")
                                                ||field.type?starts_with("int")
                                                ||field.type?starts_with("int unsigned")
                                                ||field.type?starts_with("bigint")
                                                ||field.type?starts_with("bigint unsigned")
                                                ||field.type?starts_with("decimal")
                                                ||field.type?starts_with("double")
                                                ||field.type?starts_with("float")>
                                                <input type="number" class="form-control" id="${field.name}" value="">
                                                <#else>
                                                <input type="text" class="form-control" id="${field.name}" value="">
                                                </#if>
                                            </div>
                                        </div>
                                        </#if>
                                        </#list>
                                        <!--下拉-->
                                        <!--
                                        <select id="" class="form-control select2" style="width: 100%;">
                                        </select>
                                        -->

                                        <!--单选-->
                                        <!--
                                        <div class="radio col-sm-12">
                                            <label class="radio-inline">
                                                <input type="radio" name="a1" id="" value="1"> 满足
                                            </label>
                                            <label class="radio-inline">
                                                <input type="radio" name="a1" id="" value="1"> 不满足
                                            </label>
                                        </div>
                                        -->
                                        </fieldset>
                                </form>
                            </div>
                            <div class="panel-footer">
                                <button type="button" class="btn btn-primary" ${r'<#if op_type="view">style="display:none"</#if>'} onclick="formPageEvent.save();">保存</button>
                                <button type="button" class="btn btn-primary" onclick="window.location.href='listPage.do';">返回</button>
                            </div>
                        </div>
                    </div>
                    <!-- 本段代码为代码生成器根据表[${tableName}]结构自动生成的bootstrap的表单html，犯懒时候从这里粘过去用----end-->
                </div>
                <!-- /.box-body -->
            </div>
            <!-- /.box -->
        </div>
        <!-- /.col -->
    </div>
${r'</@loserStarBody>'}

${r'<@loserStarFooter />'}

</html>




