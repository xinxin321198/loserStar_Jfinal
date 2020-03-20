/**
 * post提交json字符串数据
 * Content-Type: application/json
 */
function postJson(url,data,dataType,callBack,isAsync){
        if(!isAsync){isAsync = true;}
	$.ajax({ 
        type:"POST", 
        url:url, 
        dataType:dataType,      
        contentType:"application/json; charset=utf-8",               
        data:JSON.stringify(data), 
        success:callBack,
        async:isAsync
     }); 
}
/**
 * post提交json对象
 * Content-Type: application/x-www-form-urlencoded
 */
function postObj(url,data,dataType,callBack,isAsync){
        if(!isAsync){isAsync = true;}
	$.ajax({ 
        type:"POST", 
        url:url, 
        dataType:dataType,      
        contentType:"application/x-www-form-urlencoded; charset=utf-8",               
        data:data, 
        success:callBack,
        async:isAsync
     }); 
}

/**
 * form表单转为json提交
 * @param {} url 
 * @param {*} formSelector 
 * @param {*} dataType 
 * @param {*} callBack 
 */
function submitFormToJson(url,formSelector,dataType,callBack){
        postJson(url, formToJson_jquerySerializeJSON(formSelector),dataType, callBack);
}

/**
 * form转为json对象(原始方式)
 * @param {*} formSelector 
 */
function formToJson(formSelector){
        var data = {};
        var formArray = $(formSelector).serializeArray();
        for (var index = 0; index < formArray.length; index++) {
            var element = formArray[index];
            data[element.name] = element.value;
        }
        return data;
}

/**
 * form转为json对象(插件方式https://github.com/marioizquierdo/jquery.serializeJSON)
 * @param {*} formSelector 
 */
function formToJson_jquerySerializeJSON(formSelector){
        return $(formSelector).serializeJSON();
}

/**
 * object转为json
 * http://www.json.org/提供了一个json.js,这样ie8(兼容模式),ie7和ie6就可以支持JSON对象以及其stringify()和parse()方法； 
可以在https://github.com/douglascrockford/JSON-js上获取到这个js，一般现在用json2.js。
 * @param {*} obj 
 */
function objToJson(obj){
        return JSON.stringify(obj); //可以将json对象转换成json对符串 
}

/**
 * json字符串转为object
 * http://www.json.org/提供了一个json.js,这样ie8(兼容模式),ie7和ie6就可以支持JSON对象以及其stringify()和parse()方法； 
可以在https://github.com/douglascrockford/JSON-js上获取到这个js，一般现在用json2.js。
 * @param {*} str 
 */
function jsonToObj(str){
        return JSON.parse(str); //jQuery.parseJSON(jsonstr),可以将json字符串转换成json对象 
}
/**
 * javascript支持的eval方式
 * eval可以将json字符串转换成json对象,注意需要在json字符外包裹一对小括号 
注：ie8(兼容模式),ie7和ie6也可以使用eval()将字符串转为JSON对象，但不推荐这些方式，这种方式不安全eval会执行json串中的表达式。 
 */
function jsonToObj_eval(str){
        return eval('(' + str + ')');
}