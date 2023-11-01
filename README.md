# loserStar-Jfinal
## 一个简单jfinal基础开发框架
* ## 此项目是为了方便临时需要开发一些小功能时候可以很快捷的直接拿来用，其实就是jfinal的框架，但是增强了一些东西（此项目就是一个普通的基于eclipse的动态web工程，因为使用maven对新手来说有一定门槛，所以并没有使用构建工具）
* ## 增强了controller的使用，借鉴了springMVC使用注解来标注controller的方式，不必再去jfinalConfig里面配置router，用多了springMVC，这种每个controller请求地址都要去配置的方式总感觉怪怪的
* ## 增强了DB record的使用，参考自己的工具库[loserStarUtils](https://github.com/xinxin321198/loserStarUtils)的DB部分的使用，仅仅是自己的使用习惯，并不妨碍你直接使用jfinal原始的查询方式
* ## 增加了jfinal的代码生成器，可以生成jfinal的实体类，这样就可以使用jfinal的实体的开发方式开发了
* ## 集成了adminLTE2.4.18后台管理UI版本，该版本基于bootstrap3.x，因之前封装的部分js组件是基于bootstrap3.x的，为了兼容，故使用老版本。代码生成器直接生成的代码copy后直接呈现增删查改。
* ## resources/loserStar_Jfinal.sql 是mysql数据库的示例数据
*ps:eclipse上配置的坑，得参考一下这些文章[eclipse的配置](https://www.jianshu.com/p/01da85c5c02d)*