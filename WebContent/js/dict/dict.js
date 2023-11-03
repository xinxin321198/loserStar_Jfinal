/**
 * 请勿修改此文件，因为每次生成代码会覆盖此文件
 * date:2023-11-2 16:41:55
 * remarks: 公共的字典常量（前端使用）
 */
var dict ={}
dict.com_if = [
    {value:"1",name:"是",css_style:""},
    {value:"0",name:"否",css_style:""}
];
/**
 * 根据字典类型和值，得到名称
 * @param {*} type 字典类型
 * @param {*} value 字典值
 */
dict.getName = function(type,value){
    var name = "未知";
    var list = eval("dict."+type);
    for(var i = 0; i<list.length;i++){
        if(list[i].value == value){
            name = list[i].name;
        }
    }
    return name;
}

/**
 * 根据字典类型和值，得到名称，带<span>标签，设好了css的
 * @param {*} type 字典类型
 * @param {*} value 字典值
 */
dict.getNameSpan = function(type,value){
    var name = "未知";
    var cssStyle = "";
    var list = eval("dict."+type);
    for(var i = 0; i<list.length;i++){
        if(list[i].value == value){
            name = list[i].name;
            cssStyle = list[i].css_style;
        }
    }
    return "<span style=\""+cssStyle+"\">"+name+"</span>";
}

/**
 * 根据字典类型和值，得到样式
 * @param {*} type 字典类型
 * @param {*} value 字典值
 */
dict.getCss = function(type,value){
    var name = "未知";
    var cssStyle = "";
    var list = eval("dict."+type);
    for(var i = 0; i<list.length;i++){
        if(list[i].value == value){
            name = list[i].name;
            cssStyle = list[i].css_style;
        }
    }
    return cssStyle;
}