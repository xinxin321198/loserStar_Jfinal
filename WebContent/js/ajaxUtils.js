/**
 * version:20241210
 * author：loserStar
 * github:https://github.com/xinxin321198/loserStarJsUtils
 * emial:362527240@qq.com
 * 新增了支持设置同步还是异步的请求
 * 新增了处理返回值的公共方法（需特定的格式）
 * 增加处理Ajax异常的方法
 */

/**
 * post提交json字符串数据
 * Content-Type: application/json
 * @param url 请求的url
 * @param data 请求的参数
 * @param dataType 预计的返回值类型
 * @param callBack 请求成功200时候的回调方法
 * @param isAsync true 异步调用，false 同步调用，默认为true
 * @param errorCallBack 错误时候的回调方法（返回非200时调用）
 */
function postJson(url, data, dataType, callBack, isAsync, errorCallBack) {
    if (undefined == isAsync || null == isAsync) {
        isAsync = true;
    }
    $.ajax({
        type: "POST",
        url: url,
        dataType: dataType,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        success: callBack,
        async: isAsync,
        error: errorCallBack
    });
}

/**
 * post提交json对象
 * Content-Type: application/x-www-form-urlencoded
 * @param url 请求的url
 * @param data 请求的参数
 * @param dataType 预计的返回值类型
 * @param callBack 请求成功200时候的回调方法
 * @param isAsync true 异步调用，false 同步调用，默认为true
 * @param errorCallBack 错误时候的回调方法（返回非200时调用）
 * @returns
 */
function postObj(url, data, dataType, callBack, isAsync, errorCallBack) {
    if (undefined == isAsync || null == isAsync) {
        isAsync = true;
    }
    $.ajax({
        type: "POST",
        url: url,
        dataType: dataType,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: data,
        success: callBack,
        async: isAsync,
        error: errorCallBack
    });
}

/**
 * form表单转为json提交（该方法为异步调用）
 * @param {} url 请求的url
 * @param {*} formSelector 表单节点的css选择器
 * @param {*} dataType 预计的返回值类型
 * @param {*} callBack 请求成功200时候的回调方法
 * @param errorCallBack 错误时候的回调方法（返回非200时调用）
 */
function submitFormToJson(url, formSelector, dataType, callBack, errorCallBack) {
    postJson(url, formToJson_jquerySerializeJSON(formSelector), dataType, callBack, errorCallBack);
}

/**
 * form转为json对象(原始方式)
 * @param {*} formSelector  表单节点的css选择器
 */
function formToJson(formSelector) {
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
 * @param {*} formSelector 表单节点的css选择器
 */
function formToJson_jquerySerializeJSON(formSelector) {
    return $(formSelector).serializeJSON();
}

/**
 * object转为json
 * http://www.json.org/提供了一个json.js,这样ie8(兼容模式),ie7和ie6就可以支持JSON对象以及其stringify()和parse()方法；
 可以在https://github.com/douglascrockford/JSON-js上获取到这个js，一般现在用json2.js。
 * @param {*} obj
 */
function objToJson(obj) {
    return JSON.stringify(obj); //可以将json对象转换成json对符串
}

/**
 * json字符串转为object
 * http://www.json.org/提供了一个json.js,这样ie8(兼容模式),ie7和ie6就可以支持JSON对象以及其stringify()和parse()方法；
 可以在https://github.com/douglascrockford/JSON-js上获取到这个js，一般现在用json2.js。
 * @param {*} str
 */
function jsonToObj(str) {
    return JSON.parse(str); //jQuery.parseJSON(jsonstr),可以将json字符串转换成json对象
}

/**
 * javascript支持的eval方式
 * eval可以将json字符串转换成json对象,注意需要在json字符外包裹一对小括号
 注：ie8(兼容模式),ie7和ie6也可以使用eval()将字符串转为JSON对象，但不推荐这些方式，这种方式不安全eval会执行json串中的表达式。
 */
function jsonToObj_eval(str) {
    return eval('(' + str + ')');
}

/**
 * 公共的处理同步的请求返回值(成功返回data数据，失败用原始alert提示错误信息，并返回null)
 * （基于VResult类的格式）
 * {"flag":true,"msg":"成功","data":{}}
 * @param {*} result
 */
function returnSyncData(result) {
    if (result.flag) {
        return result.data;
    } else {
        alert(result.msg);
        return null;
    }
}

/**
 * 公共的处理异步请求（成功会调用callback_ok回调，传入data数据，失败会调用callback_error,并返回错误信息及原始返回信息）
 * （基于VResult类的格式）
 * {"flag":true,"msg":"成功","data":{}}
 * @param {*} result
 */
function returnAsyncData(result, callback_ok, callback_error) {
    if (result.flag) {
        if (callback_ok) {
            callback_ok(result.data, result.msg, result);
        }
    } else {
        if (callback_error) {
            callback_error(result.data, result.msg, result);
        }
    }
}