/**
 * 请勿修改此文件，因为每次生成代码会覆盖此文件
 * date:2020-8-7 15:17:32
 * remarks: 公共的字典常量（前端使用）
 */
var dict ={}
dict.TASK_BILL_H_STATE = [
    {value:"ABOLISH",name:"作废",css_style:"color: #a94442;"},
    {value:"PUBLISH",name:"发布",css_style:"color: #337ab7;"},
    {value:"COMPLETE",name:"完成",css_style:"color: #3c763d"}
];
dict.TASK_BILL_H_TYPE = [
    {value:"RK",name:"入库",css_style:""},
    {value:"CK",name:"出库",css_style:""},
    {value:"YK",name:"移库",css_style:""}
];
dict.TASK_BILL_H_TYPE2 = [
    {value:"RK",name:"入库单",css_style:""},
    {value:"CK",name:"出库单",css_style:""},
    {value:"YK",name:"移库单",css_style:""},
    {value:"ZC",name:"转储单",css_style:""},
    {value:"CG",name:"采购订单",css_style:""},
    {value:"JH",name:"交货单",css_style:""},
    {value:"YL",name:"预留单",css_style:""}
];
dict.MASTER_GOODS_LOCA_IS_USED = [
    {value:"0",name:"空闲",css_style:"background-color: #ffffff;"},
    {value:"4",name:"拟预定",css_style:"background-color: #d9edf7;"},
    {value:"2",name:"预订",css_style:"background-color: #9dff00;"},
    {value:"1",name:"占用",css_style:"background-color: #eea236;"}
];
dict.MASTER_GOODS_LOCA_IS_UNLIMITED = [
    {value:"1",name:"无限制",css_style:""},
    {value:"0",name:"限制",css_style:""}
];
dict.MASTER_BUS_TYPE_BUS_OBJ_TYPE = [
    {value:"lifnr",name:"供应商",css_style:""},
    {value:"factory",name:"工厂",css_style:""},
    {value:"warehouse",name:"仓库",css_style:""},
    {value:"dept",name:"部门",css_style:""},
    {value:"ohter",name:"其它",css_style:""}
];
dict.MASTER_BUS_TYPE_STOCK_TYPE = [
    {value:"FXZ",name:"非限制库存",css_style:""},
    {value:"ZJ",name:"质检库存",css_style:""},
    {value:"DJ",name:"冻结库存",css_style:""},
    {value:"ZT",name:"在途库存",css_style:""}
];
dict.MASTER_BUS_TYPE_TRANSFER_TYPE = [
    {value:"RK",name:"入库",css_style:""},
    {value:"CK",name:"出库",css_style:""},
    {value:"YK",name:"移库",css_style:""},
    {value:"ZC",name:"转储",css_style:""}
];
dict.MASTER_BUS_TYPE_BORROW_LOAN_FLAG = [
    {value:"S",name:"借",css_style:""},
    {value:"H",name:"贷",css_style:""}
];
dict.MOVE_VOUCHER_I_BORROW_LOAN_FLAG = [
    {value:"S",name:"借",css_style:""},
    {value:"H",name:"贷",css_style:""}
];
/**
 * 根据字典类型和值，得到名称
 * @param {*} type 字典类型
 * @param {*} value 字典值
 */
dict.getName = function(type,value){
    var name = "未知";
    var list = eval("dict."+type.toLowerCase());
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
    var list = eval("dict."+type.toUpperCase());
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
    var list = eval("dict."+type.toUpperCase());
    for(var i = 0; i<list.length;i++){
        if(list[i].value == value){
            name = list[i].name;
            cssStyle = list[i].css_style;
        }
    }
    return cssStyle;
}