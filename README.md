# loserStar-Jfinal
## 一个简单jfinal基础开发框架，基于jdk1.6，以获得最大兼容性（需要部署到较老的服务器上且jdk无法升级的情况下）
* ## 此项目是为了方便临时需要开发一些小功能时候可以很快捷的直接拿来用，其实就是jfinal的框架，但是增强了一些东西（此项目就是一个普通的基于eclipse的动态web工程，因为使用maven对新手来说有一定门槛，所以并没有使用构建工具）
* ## 增强了controller的使用，借鉴了springMVC使用注解来标注controller的方式，不必再去jfinalConfig里面配置router，用多了springMVC，这种每个controller请求地址都要去配置的方式总感觉怪怪的
* ## 增强了DB record的使用，参考自己的工具库[loserStarUtils](https://github.com/xinxin321198/loserStarUtils)的DB部分的使用，仅仅是自己的使用习惯，并不妨碍你直接使用jfinal原始的查询方式
* ## 增加了jfinal的代码生成器，可以生成jfinal的实体类，这样就可以使用jfinal的实体的开发方式开发了
* ## 集成了adminLTE2.4.18后台管理UI版本，该版本基于bootstrap3.x，因之前封装的部分js组件是基于bootstrap3.x的，为了兼容，故使用老版本。代码生成器直接生成的代码copy后直接呈现增删查改。
    * [https://3vshej.cn/AdminLTE/AdminLTE-2.3.11/documentation/index.html]
    * [https://3vshej.cn/AdminLTE/AdminLTE-2.4/]
    * [https://github.com/ColorlibHQ/AdminLTE/releases/tag/v2.4.18]
* ## resources/loserStar_Jfinal.sql 是mysql数据库的示例数据
* ## ps:eclipse上配置的坑，得参考一下这些文章[eclipse的配置](https://www.jianshu.com/p/01da85c5c02d)
* ## doc目录：
    ### AdminLTE_CN-2.4.18.zip中文版的demo
    ### AdminLTE-2.4.18.zip原版的demo
    #### 解压后就是常规的web工程，可丢在Tomcat的webapp目录下即可本地访问
    #### \WebContent\bower_components\目录下存在jqwidgets5，此js框架请勿商用，如需商用请移除

* # 开发规范
    ## 前端：
    ### 1.保持UI风格一致：
    #### 请尽量使用adminLTE已有的组件或者bootstrap3的组件，如有不满足的组件，可在jQuery插件库找找是否有相关的组件，或利用bootstrap样式和组件自行封装一下

    ### 2.前端公共样式库：
    #### 统一放在/ynzyZp/WebContent/css/ynzyZpCus.css文件中，如一些公共的特殊样式。如果仅某个功能页面相关的样式就放入页面相关的路径
    * 按钮仿苍穹样式：
    ```
     <button class="btn btn-ynzyZp "type="button">苍穹按钮</button>
    ```

    ### 3.皮肤：
    #### 自定义了一个皮肤主题，/ynzyZp/WebContent/css/ynzyZpSkin.css，正常来说皮肤也属于一个公共样式库，但是皮肤不夹杂布局相关的css，文件内的css仅定义颜色和效果等，尽量夹杂和布局相关的属性。修改和增加样式需参照规范来，必须在加在.skin-ynzy样式下（更换皮肤主题在body上修改class即可）

    ### 4.常规页面开发：
    #### 仅用开发整个页面的`<body>`部分，使用freemarker渲染页面结构，Ajax渲染数据；freemarker模板内引入public.html文件，并引入公共的header、body、footer。
    ```
    <!-- 页面框架代码 -->
    <#setting classic_compatible=true />
    <#include "public.html" />
    <!DOCTYPE html>
    <html>
    <@loserStarHeader title="标题变量">
        <!-- 这里放生成的引入的js--begin -->

        <!-- 这里放生成的引入的js--end -->
        <script>
            //这里写自定义的js代码

        </script>
    </@loserStarHeader>

    <@loserStarBody title="页面主标题" title2="页面副标题">
        <div class="row">
            <div class="col-xs-12">
                <div class="box">
                    <div class="box-header">
                        <h3 class="box-title">盒子标题</h3>
                    </div>
                    <!-- /.box-header -->
                    <div class="box-body">
                        <!-- 这里放生成的html代码 -->

                    </div>
                    <!-- /.box-body -->
                </div>
                <!-- /.box -->
            </div>
            <!-- /.col -->
        </div>
    </@loserStarBody>

    <@loserStarFooter />

    </html>
    ```

    ### 5.页面公共部分：
    #### 页面公共部分在public.html文件中
    #### loserStarHeader：页头部分，通常用于放置html的`<head>`部分的内容，引入js和css或每个页面都需要执行的js
    #### loserStarBody：页面主体部分，比如放置菜单、页面框架等
    * 设置固定导航栏：body上增加fixed类
    * 修改主题：body上修改skin-* 类
    #### loserStarFooter：页脚部分，放置一些版权信息、联系方式等

    ### 6.UI组件：
    #### adminLTE已集成了众多UI组件，具体可查看目录，如自行引入的前端组件也可放入 /ynzyZp/WebContent/bower_components

    ### 7.Ajax请求:
    #### 请使用/ynzyZp/WebContent/js/ajaxUtils.js 该工具类，返回值建议保持该结构
    ```
    {
        "data": {},
        "msg": "获取数据成功",
        "flag": true
    }
    ```

    ### 8.bootstrap相关的工具类：
    #### /ynzyZp/WebContent/js/loserStarBoostrapUtils.js，包含加载遮罩、进度条生成、下拉框、radio组生成

    ### 9.内容提示框，确认框等：
    #### 可使用sweetAlert，或封装过的工具/ynzyZp/WebContent/js/loserStarSweetAlertUtils.js

    ### 10.简单渲染表格：
    #### 使用bootstrap的table样式即可，如渲染功能复杂的表格，可使用DataTables [https://datatables.net/][http://datatables.club/]

    ### 11.渲染树形表格：
    #### 可使用jquery的一个插件 [https://plugins.jquery.com/treetable]  [https://github.com/ludo/jquery-treetable]

    ### 12.弹出对话框选择数据：
    #### 可附带过滤查询框，可使用/ynzyZp/WebContent/js/loserStarDataSelectBootstrapWindow1.1.js

    ### 13.弹出对话框选择树形数据：
    #### 可使用/ynzyZp/WebContent/js/loserStarTreeDataSelectBootstrapWindow1.0.js

    ### 14.常用js方法库：
    #### /ynzyZp/WebContent/js/loserStarJsUtils.js

    ### 15.附件上传组件：
    #### 可使用/ynzyZp/WebContent/js/loserStarFileUploadBootstrapWindow_WebUploader1.2.js组件，该组件基于百度的[uploader](https://fex-team.github.io/webuploader/)上传附件组件，也可自行查看其API调用

    ### 16.前端代码：
    #### 业务模块相关的代码放入/ynzyZp/WebContent/view 目录下建立相关的文件夹存放
    #### /ynzyZp/WebContent/view/[业务]/ 下存放该业务的html
    #### /ynzyZp/WebContent/view/[业务]/css 下存放该业务相关css
    #### /ynzyZp/WebContent/view/[业务]/js 下存放该业务相关js
    * 与页面文件名一致的js作为页面的入口js
    * *Event.js文件存放页面相关的事件响应方法,如按钮点击事件等
    * *Action.js文件存放请求后端数据的相关方法
    

    ## 服务端：
    ### 1.jfinal：
    #### 目前使用jfinal-5.1.5版本，可参考官网[https://jfinal.com/]
    * 开发建议使用entity的方式，查询数据少用record模式，如数据库字段有变更容易发现
    * controller中尽量少写业务逻辑和少直接调用dao，业务逻辑量比较大的action，请把相关代码抽象后放在service中，否则以后需要抽取代码逻辑时可能不太方便
    * controller中每个action方法必须经try catch包裹住，无特殊情况，其所调用service或工具等均抛出异常让controller捕获
    * controller返回页面结构示例：
    * dao中为一张表对应一个dao，仅放置一些针对此单表的相关操作，复杂的多表操作建议放入service中
    * service类，难免互相之间调用，但容易产生依赖循环，建立service类时，请使用单例模式，避免使用new关键字去创建service实例：
    * 定义一个单例模式的Service类
    ```
    public class UserInfoService {
        private static UserInfoService userInfoService;

        private UserInfoService() {
        }

        public static UserInfoService ins() {
            if (userInfoService == null) {
                synchronized (UserInfoService.class) {
                    userInfoService = new UserInfoService();
                }
            }
            return userInfoService;
        }
    }
    ```
    * 获取实例调用相关方法
    ```
    UserInfoService userInfoService = UserInfoService.ins();
    userInfoService.getXXX();
    ```

    ```
    /**
	 * 列表页面
	 */
	public void listPage() {
		try {
			String userid = getUserId();
			renderFreeMarker("/view/sysDict/sysDictList.html");//渲染模板
		} catch (Exception e) {
			e.printStackTrace();
			renderError(e.getMessage());//捕获异常后跳转至错误页面
		}
	}
    ```
    * controller中返回数据示例：
    ```
    	/**
        * 根据主键id获取数据
        */
        public void getById() {
            VResult result = new VResult();//无特殊情况永远返回此种对象
            try {
                String userid = getUserId();
                String dict_id = getPara("dict_id");
                if (!checkNull(dict_id)) {
                    throw new Exception("没有传入数据dict_id");
                }
                SysDict sysDict = sysDictDao.getById(dict_id,SysDict.class);
                if (sysDict==null) {
                    throw new Exception("没有找到该dict_id的数据");
                }
            result.ok("获取数据成功");//设置result状态和提醒消息
            result.setData(sysDict);//设置返回的实际数据
            } catch (Exception e) {
                e.printStackTrace();
                result.error(e.getMessage());//发生异常捕获之后修改result的状态，并放入错误信息
            }
            renderJson(result);
        }
    ```

    ### 2.数据库访问dao：
    #### 数据库访问使用逆向生成的方式，基于表结构，生成对应的实体和dao，建议使用每张表对应的dao类进行访问，也可使用jfinal的实体dao或者纯sql的写法
    
    ### 3.目录结构
    #### com.kaen.config 放jfinal的相关配置类
    #### com.kaen.config.JfinalConfig 调整加载的配置文件及一些常规配置
    #### com.kaen.controller.pc 存放相关的controller类，controller中每个action的命名，尽量返回页面的以xxxPage()命名，返回数据的以xxxData()命名
    #### com.kaen.entity 存放jfinal生成的entity、自定义的vo
    #### com.kaen.service 存放service，业务逻辑方法
    #### com.kaen.test 存放一些测试用例
    #### com.kaen.utils 工具类

    ### 4.登录拦截
    #### 默认所有请求都需要登录后可访问，/ynzyZp/resources/init-cs.properties文件中可指定哪些controller请求不需要登录

    ### 5.代码生成器：
    #### com.kaen.test.EntityGennerate.java中添加要生成代码的表名，右键执行mian方法即可生成该表的相关代码，把代码copy到合适的路径，微调一下即可快速生成针对一张表的查询、新增、修改、删除的功能。生成的路径如下：
    * 实体：com.kaen.entity.*
    * 数据字典常量：com.kaen.constants.DictConstants
    * 数据访问DAO：com.kaen.dao
    * 前端使用的数据字典：/ynzyZp/WebContent/js/dict/dict.js
    * controller类：/ynzyZp/WebContent/html/bootstrapForm/[表名]/java/*
    * js代码：/ynzyZp/WebContent/html/bootstrapForm/[表名]/web/[表名]/js/*
    * html代码：/ynzyZp/WebContent/html/bootstrapForm/[表名]/web/[表名]/*.html （包含bootstrap原生的和基于adminLTE的）
    #### 代码生成器模板：/ynzyZp/resources/codeGenTemplate 下存放生成各种代码的模板文件，可根据需要修改

    ## 数据库
    ### 1.每张表必须存在主键
    ### 2.每张表必须有如下字段：
    ```
    sort 排序
    del 软删除标识
    create_time 创建时间
    create_user_code 创建人编号
    create_user_name 创建人姓名
    update_time 修改时间
    update_user_code 修改人编号
    update_user_name 修改人姓名
    ```