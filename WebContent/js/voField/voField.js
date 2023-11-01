/**
* 
* 请勿直接修改此文件，本文件只存储VO的原始字段信息，每次生成会覆盖。如需修改其内容，请在外部js代码中动态调用进行修改。或copy出相关对象到外部文件进行使用
* date：2020-10-27 9:41:14
* remarks:每个vo的字段（前端）
*/
var voField = {};

voField.TestVo = [
    {name: 'a1', type: 'string'} ,
    {name: 'a2', type: 'number'} ,
    {name: 'a3', type: 'date'} 
];

/**
 * 获取某个vo里的某个字段的定义，可以用时候拿出来临时增加其他定义
 * @param {*} table 
 * @param {*} name 
 */
voField.getField = function(voName,name){
    var returnField = null;
    var tempTable = eval("voField."+table.toLowerCase());
    for(var i = 0; i<tempTable.length;i++){
        if(tempTable[i].name == name){
            returnField = tempTable[i];
        }
    }
    return returnField;
}