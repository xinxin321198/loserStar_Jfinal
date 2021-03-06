/**
* 请勿直接修改此文件，本文件只存储与表关联的原始字段信息，每次生成会覆盖。如需修改其内容，请在外部js代码中动态调用进行修改。或copy出相关对象到外部文件进行使用
* date：2020-10-27 9:41:14
* remarks: 每张表的字段（前端使用）
*/
var tableField_loserstar = {};

/* 用户信息表 */
tableField_loserstar.da_account = [
    {name: 'id', type: 'number'} ,
    {name: 'user_id', type: 'number'} ,
    {name: 'full_name', type: 'string'} ,
    {name: 'mobile', type: 'string'} ,
    {name: 'address', type: 'string'} ,
    {name: 'comment', type: 'string'} ,
    {name: 'del', type: 'string'} 
];
/* 用户表 */
tableField_loserstar.sys_users = [
    {name: 'id', type: 'string'} ,
    {name: 'user_name', type: 'string'} ,
    {name: 'password', type: 'string'} ,
    {name: 'del', type: 'string'} 
];
/* 字典表 */
tableField_loserstar.sys_dict = [
    {name: 'dict_id', type: 'string'} ,
    {name: 'dict_value', type: 'string'} ,
    {name: 'dict_name', type: 'string'} ,
    {name: 'dict_type', type: 'string'} ,
    {name: 'dict_remarks', type: 'string'} ,
    {name: 'dict_c_name', type: 'string'} ,
    {name: 'dict_css_style', type: 'string'} ,
    {name: 'dict_sort', type: 'string'} ,
    {name: 'del', type: 'string'} 
];

/**
 * 获取某张表里的某个字段的定义，可以用时候拿出来临时增加其他定义
 * @param {*} table 
 * @param {*} name 
 */
tableField_loserstar.getField = function(table,name){
    var returnField = null;
    var tempTable = eval("tableField_loserstar."+table.toLowerCase());
    for(var i = 0; i<tempTable.length;i++){
        if(tempTable[i].name == name){
            returnField = tempTable[i];
        }
    }
    return returnField;
}