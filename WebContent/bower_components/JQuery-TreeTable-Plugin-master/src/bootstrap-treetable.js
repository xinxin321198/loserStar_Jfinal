/*!
 * Bootstrap-treetable v1.2.7
 *
 * Copyright 2017 by huanglin992@163.com
 * http://git.oschina.net/harris992/JQuery-TreeTable-Plugin
 */

(function ($) {
    'use strict';
    var namespace = ".bs.treetable";
    var BootstrapTreeTable = function (element, options) {
        this.$element = $(element);
        this.origin = this.$element.clone();
        this.nodes = this.$element.find('tbody > tr');
        this.root = "tr[data-pid=''],tr[data-pid='root']";
        this.options = options;
        this.maxlevel = 0;
        // Expose public methods
        this.searchNodeId = BootstrapTreeTable.prototype.searchNodeId;
        this.searchNodeName = BootstrapTreeTable.prototype.searchNodeName;
        this.expendAll = BootstrapTreeTable.prototype.expendAll;
        this.collapseAll = BootstrapTreeTable.prototype.collapseAll;
        this.expendLevel = BootstrapTreeTable.prototype.expendLevel;
        this.destroy = BootstrapTreeTable.prototype.destroy;
        this.reset = BootstrapTreeTable.prototype.reset;
        this.removeByNode = BootstrapTreeTable.prototype.removeByNode;
        this.removeById = BootstrapTreeTable.prototype.removeById;

        this.init();
    };

    BootstrapTreeTable.VERSION = '1.2.6';

    BootstrapTreeTable.prototype = {

        /**
         * 组件初始化
         */
        init: function () {
            var that = this;
            this.nodes.filter(this.root).each(function () {
                var $node = $(this);
                $node.data('level', 0);
                that.dataHandler($node);
            });
            this.clickListener();

            setTimeout(function () {
                that.$element.trigger('initialized' + namespace);
            });
        },
        /**
         * 重新计算列，计算级次，是否叶子节点等属性
         * @param element
         */
        dataHandler: function (element) {
            var that = this;
            var id = element.data('id');
            var level = parseInt(element.data('level')) + 1;

            if (that.maxlevel < level) {
                that.maxlevel = level;
            }
            var childrenlist = this.nodes.filter('tr[data-pid=' + id + ']');

            element.data('leaf', childrenlist.length === 0 ? 1 : 0);//1:leaf,0:not leaf
            /* element.attr('leaf', element.data('leaf')); */

            that.setbranch(element);
            that.setDefaultExpand(element);

            childrenlist.each(function () {
                $(this).data('level', level);
                /*$(this).attr('level', level);*/
                var column = that.options.column !== '' ? that.options.column : 0;
                var levelSpacing = that.options.levelSpacing !== '' ? that.options.levelSpacing : 20;
                var $td = $(this).children().eq(column);
                $td.css('padding-left', level * levelSpacing + 'px');
                that.dataHandler($(this));
            })
        },
        /**
         * 设置节点
         * @param element
         */
        setbranch: function (element) {
            var column = this.options.column !== '' ? this.options.column : 0;
            var $td = element.children().eq(column);
            $td.wrapInner("<span class='nodename'></span>");
            if (element.data('leaf') === 0) {
                var $a = $('<a href="#"></a>').addClass('nodepoint').css('padding-right', '5px').append(this.options.collapsedIcon);
                $td.prepend($a);
            } else {
                var $span;
                if (this.options.leafIcon) {
                    $span = $(this.options.leafIcon).css({'padding-left': '0', 'padding-right': '5px'});
                } else {
                    $span = $('<span></span>').css('padding-left', '17px');
                }
                $td.prepend($span);
            }
        },
        /**
         * 页面初始化层级展示
         * @param element
         */
        setDefaultExpand: function (element) {

            var expandlevel = this.options.expandlevel !== 'undefined' ? this.options.expandlevel : 0;
            var expandAll = this.options.expandAll !== 'undefined' ? this.options.expandAll : false;
            var collapseAll = this.options.collapseAll !== 'undefined' ? this.options.collapseAll : false;
            var trlevel = element.data('level');

            if (expandAll) {
                this.setExpanded(element);
                element.show();
                return;

            }
            if (collapseAll) {
                this.setCollapsed(element);
                if (trlevel !== 0) {
                    element.hide();
                }
                return;
            }
            if (expandlevel === trlevel) {
                this.setCollapsed(element);
                element.show();
            } else if (expandlevel > trlevel) {
                this.setExpanded(element);
                element.show();
            } else {
                this.setCollapsed(element);
                element.hide();
            }


        },
        /**
         * 展开、闭合事件监听
         */
        clickListener: function () {
            var that = this;
            this.nodes.find('.nodepoint').on('click.bs.treetable', function (e) {
                e.preventDefault();
                var $node = $(this).parents('tr');

                if ($node.hasClass('expended')) {
                    $node.trigger('hide' + namespace, $node);
                    that.setCollapsed($node);
                    that.collapseHandler($node);
                    $node.trigger('hidden' + namespace, $node);
                } else {
                    $node.trigger('show' + namespace, $node);
                    that.setExpanded($node);
                    that.expendHandler($node);
                    $node.trigger('shown' + namespace, $node);
                }

            });
        },
        /**
         * 设置闭合效果
         * @param element
         */
        setCollapsed: function (element) {
            if (element.data('leaf') === 0 && !element.hasClass('collapsed')) {
                element.removeClass('expended');
                element.addClass('collapsed');
                element.find('.nodepoint').children().replaceWith(this.options.collapsedIcon);
            }
        },
        /**
         * 设置展开效果
         * @param element
         */
        setExpanded: function (element) {
            if (element.data('leaf') === 0 && !element.hasClass('expended')) {
                element.removeClass('collapsed');
                element.addClass('expended');
                element.find('.nodepoint').children().replaceWith(this.options.expendedIcon);
            }
        },
        /**
         * 闭合事件处理
         * @param element
         */
        collapseHandler: function (element) {
            var that = this;
            var id = element.data('id');
            var childrenlist = this.nodes.filter('tr[data-pid=' + id + ']');
            childrenlist.hide();
            childrenlist.each(function () {
                that.collapseHandler($(this));
            })
        },
        /**
         * 展开事件处理
         * @param element
         */
        expendHandler: function (element) {
            var that = this;
            var id = element.data('id');
            var childrenlist = this.nodes.filter('tr[data-pid=' + id + ']');
            childrenlist.show();
            childrenlist.filter('tr[class*="expended"]').each(function () {
                that.expendHandler($(this));
            })
        },
        /**
         * 根据节点ID查找
         * @param value
         */
        searchNodeId: function (value) {
            var that = this;
            var findNode = this.nodes.filter(function () {
                var id = $(this).data('id');
                return id === value;
            });
            findNode.addClass(that.options.choseClass);
            that.setExpanded(findNode);
            that.expendHandler(findNode);
            that.expendParent(findNode);
        },
        /**
         * 根据节点名称查找
         * @param value
         * @returns {*}
         */
        searchNodeName: function (value) {

            if (value === '' || typeof value === 'undefined') {
                return 0;
            }
            var that = this;
            that.collapseAll();
            that.clearMatchStyle();
            var matchlist = that.nodes.filter(function () {
                var nodename = $(this).find('.nodename').text();
                return nodename.indexOf($.trim(value)) !== -1;
            });
            var maxResult = that.options.maxResult !== '' ? that.options.maxResult : 20;
            var matchClass = that.options.matchClass !== '' ? that.options.matchClass : 'text-danger';
            if (matchlist.length > maxResult) {
                alert('查询匹配结果过多，请重新查询');
            } else {
                matchlist.each(function () {
                    var $this = $(this);
                    var nodename = $this.find('.nodename');
                    nodename.addClass(matchClass);
                    that.expendParent($this);
                })
            }
            return matchlist.length;
        },
        /**
         * 展开父级节点
         * @param element
         */
        expendParent: function (element) {
            var that = this;
            var pid = element.data('pid');
            var parent = this.nodes.filter("tr[data-id='" + pid + "']");
            if (parent.length > 0) {
                that.setExpanded(parent);
                parent.show();
                var id = parent.data('id');
                that.nodes.each(function () {
                    var $sibling = $(this);
                    if ($sibling.data('pid') === id) {
                        $sibling.show();
                    }
                });
                that.expendParent(parent);
            }
        },
        /**
         * 全部展开
         */
        expendAll: function () {
            var that = this;
            that.nodes.each(function () {
                var $this = $(this);
                that.setExpanded($this);
                $this.show();
            })
        },
        /**
         * 全部关闭
         */
        collapseAll: function () {
            var that = this;
            that.nodes.each(function () {
                var $this = $(this);
                that.setCollapsed($this);
                if ($this.data('level') !== 0) {
                    $this.hide();
                }
            })
        },
        /**
         * 打开指定层级
         */
        expendLevel: function (value) {
            var that = this;
            that.nodes.each(function () {
                var $this = $(this);
                var trlevel = $this.data('level');
                if (value === trlevel) {
                    that.setCollapsed($this);
                    $this.show();
                } else if (value > trlevel) {
                    that.setExpanded($this);
                    $this.show();
                } else {
                    that.setCollapsed($this);
                    $this.hide();
                }
            })
        },
        /**
         * 注销
         */
        destroy: function () {
            this.nodes.find('.nodepoint').off('.bs.treetable');
            this.$element.before(this.origin).remove();
        },
        /**
         * 重置
         */
        reset: function () {
            var that = this;
            this.nodes.each(function () {
                that.setDefaultExpand($(this));
            });
            that.clearMatchStyle();

            /* this.destroy();
             var $table = $(this.$element.selector);
             Plugin.call($table, $table.data());*/
        },
        /**
         * 清除选中效果
         */
        clearMatchStyle: function () {
            var matchClass = this.options.matchClass !== '' ? this.options.matchClass : 'text-danger';
            this.nodes.find('.nodename').removeClass(matchClass);
        },
        /**
         * 该点如果没有孩子，则变成叶子节点
         */
        setNode2Leaf: function (nodeid) {
            var that = this;
            this.nodes = this.$element.find('tbody > tr');
            this.nodes.each(function () {
                var $this = $(this);
                var id = $this.data('id');
                if (id === nodeid) {
                    var children = that.nodes.filter("tr[data-pid='" + id + "']");
                    if (children.length === 0) {
                        $this.data('leaf', '1');
                        $this.removeClass('expended');
                        $this.find('.nodepoint').remove();
                        var column = that.options.column !== '' ? that.options.column : 0;
                        var $td = $this.children().eq(column);
                        var $span;
                        if (that.options.leafIcon) {
                            $span = $(that.options.leafIcon).css({'padding-left': '0', 'padding-right': '5px'});
                        } else {
                            $span = $('<span></span>').css('padding-left', '17px');
                        }
                        $td.prepend($span);
                    }
                    return false;
                }
            })
        },
        /**
         * 根据节点删除节点
         */
        removeByNode: function (node) {
            var that = this;
            if (node && node.data('leaf') === 1) {

                var nodepid = node.data('pid');
                node.remove();

                // 重置parent节点
                that.setNode2Leaf(nodepid);

            } else if (node.data('leaf') === 0) {
                alert('请先删除子节点');
            }
        },
        /**
         * 根据节点ID删除节点
         */
        removeById: function (id) {
            var node = this.nodes.filter('tr[data-id="' + id + '"]');
            this.removeByNode(node);
        },

        showLoading: function () {

            // this.$element.find('tbody').hide();
            var tbody = this.$element.children('tbody').first();
            window.setTimeout(function () {
                var firstRow = tbody.find("tr").first();
                var count = firstRow.children().length;
                var newRow = $('<td></td>').attr('colspan', count).appendTo($('<tr></tr>'));
                tbody.prepend(newRow);
            }, 5000);
        },
        /**
         * 根据节点ID删除节点
         */
        getMaxLevel: function () {
            return this.maxlevel;
        }
    };

    BootstrapTreeTable.DEFAULTS = {
        levelSpacing: 20,//级次间距 px
        column: 0,//指定排序列号
        expandlevel: 0,//默认展开级次
        expandAll: false,//是否全部展开
        collapseAll: false,//是否全部关闭
        expendedIcon: '<span class="glyphicon glyphicon-minus"></span>',//节点展开图标
        collapsedIcon: '<span class="glyphicon glyphicon-plus"></span>',//节点关闭图标
        leafIcon: '<span class="glyphicon glyphicon-leaf" ></span>',
        maxResult: 20,//搜索最大结果集，超过将停止返回结果
        matchClass: 'text-danger',
        choseClass: 'bg-info'
    };

    function Plugin(option, event) {
        var args = arguments;
        var $this = $(this), _option = option, _event = event;
        [].shift.apply(args);

        var value;
        if ($this.is('table')) {
            var data = $this.data('BootstrapTreeTable');
            var options = typeof _option == 'object' && _option;

            if (!data) {
                var config = $.extend(true, BootstrapTreeTable.DEFAULTS, $this.data(), options);

                $this.data('BootstrapTreeTable', (data = new BootstrapTreeTable(this, config, _event)));

            } else if (options) {
                for (var i in options) {
                    if (options.hasOwnProperty(i)) {
                        data.options[i] = options[i];
                    }
                }
            }
            if (typeof _option == 'string') {

                if (data[_option] instanceof Function) {
                    value = data[_option].apply(data, args);

                } else {
                    value = data.options[_option];
                }
            }
        }
        if (typeof value !== 'undefined') {
            return value;
        } else {
            return this;
        }
    }

    var old = $.fn.BootstrapTreeTable;
    $.fn.BootstrapTreeTable = Plugin;
    $.fn.BootstrapTreeTable.Constructor = BootstrapTreeTable;
    $.fn.BootstrapTreeTable.noConflict = function () {
        $.fn.BootstrapTreeTable = old;
        return this;
    };
})(jQuery);