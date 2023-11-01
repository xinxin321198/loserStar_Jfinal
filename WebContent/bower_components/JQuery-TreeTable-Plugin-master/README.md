###  **Treetable Plugin** v1.2.7
基于JQuery的表格树插件，按 data-id和data-pid计算布局，在表格中展现树型结构。

插件包含初始化选项，监听事件和常用方法。

内置样式使用bootstrap3

[在线演示](http://harris992.oschina.io/jquery-treetable-plugin)

当行属性 data-pid='' 或 data-pid='root' 时识别为“根目录”。
```
<tr data-id="scml" data-pid=""></tr>
<tr data-id="scml" data-pid="root"></tr>
```

### Download 下载
> src/bootstrap-treetable.js

### Import script 标签引入
```
<script src="js/bootstrap-treetable.js"></script>
```

### initialized 初始化
```
var treetable = $('#treetable').BootstrapTreeTable({
    expandlevel: 1,
    expandAll: false,
    collapseAll: false,
    maxResult: ''
})
```
### Core options 选项
- levelSpacing	integer	20	级次间距 px
- column	integer	0	指定排序列号
- expandlevel	integer	0	默认展开级次
- expandAll	boolean	false	是否全部展开
- collapseAll	boolean	false	是否全部关闭
- expendedIcon	string	'<span class="glyphicon glyphicon-minus"></span>'	节点展开图标
- collapsedIcon	string	'<span class="glyphicon glyphicon-plus"></span>'	节点关闭图标
- leafIcon	string	'<span class="glyphicon glyphicon-leaf"></span>'	节点展开图标
- maxResult	integer	20	搜索最大结果集，超过将停止返回结果
- matchClass	string	'text-danger'	查询匹配节点class
- choseClass	string	'bg-info'	定位匹配节点class

### Events 事件
- show.bs.treetable	        展开节点时触发事件	Event e,Object node
- shown.bs.treetable	        展开节点后触发事件	Event e,Object node
- hide.bs.treetable	        关闭节点时触发事件	Event e,Object node
- hidden.bs.treetable	        关闭节点后触发事件	Event e,Object node
- initialized.bs.treetable	初始化插件完成后触发事件	Event e

```
$('#treetable').BootstrapTreeTable().on("show.bs.treetable", function (e, node) {
    //console.log('show', node);
})
```
### Methods 方法
- searchNodeName	查询节点，返回结果数	string查询字符	string
- searchNodeId	根据节点data-id查询，并高亮显示	stringdata-id	
- removeById	根据节点data-id删除节点	stringdata-id	
- removeByNode	根据节点Object删除节点	Objectnode	
- expendLevel	展开全部指定级次	integerlevel	
- expendAll	展开全部节点		
- collapseAll	关闭全部节点		
- destroy	注销插件		
- reset	        重置插件

```
$('#treetable').BootstrapTreeTable('searchNodeName', '计算机');
$('#treetable').BootstrapTreeTable('searchNodeId', '337A2575C9404167AFE020D8C27D1C45');
$('#treetable').BootstrapTreeTable('removeById', '337A2575C9404167AFE020D8C27D1C45');

var node = $(this).parents('tr');
$('#treetable').BootstrapTreeTable('removeByNode', node);

$('#treetable').BootstrapTreeTable('removeById', '337A2575C9404167AFE020D8C27D1C45');
$('#treetable').BootstrapTreeTable('expendLevel','2');
$('#treetable').BootstrapTreeTable('expendAll');
$('#treetable').BootstrapTreeTable('collapseAll');
$('#treetable').BootstrapTreeTable('destroy');
$('#treetable').BootstrapTreeTable('reset');
```
### Properties 属性
- getMaxLevel	获取层级数	integer

```
$('#treetable').BootstrapTreeTable('getMaxLevel');
```
![](http://git.oschina.net/uploads/images/2017/0118/151621_d1ae5e05_563221.jpeg "")