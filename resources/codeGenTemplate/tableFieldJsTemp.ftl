<#setting classic_compatible=true>
/**
* 请勿直接修改此文件，本文件只存储与表关联的原始字段信息，每次生成会覆盖。如需修改其内容，请在外部js代码中动态调用进行修改。或copy出相关对象到外部文件进行使用
* date：${.now}
* remarks: 每张表的字段（前端使用）
*/
var ${variableName} = {};

<#list data as table>
/* ${table.tableRemarks} */
${variableName}.${table.tableName} = [
    <#list table.fieldList as field>
    {name: '${field.name}', type: '${field.type}'} <#if field_index!=(table.fieldList?size-1)>,</#if>
    </#list>
];
</#list>

/**
 * 获取某张表里的某个字段的定义，可以用时候拿出来临时增加其他定义
 * @param {*} table 
 * @param {*} name 
 */
${variableName}.getField = function(table,name){
    var returnField = null;
    var tempTable = eval("${variableName}."+table.toLowerCase());
    for(var i = 0; i<tempTable.length;i++){
        if(tempTable[i].name == name){
            returnField = tempTable[i];
        }
    }
    return returnField;
}