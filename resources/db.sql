CREATE TABLE `da_account` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT 'user id',
  `full_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '全名',
  `mobile` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `address` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `comment` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `del` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';



CREATE TABLE `sys_users` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `del` varchar(10) COLLATE utf8_bin NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

CREATE TABLE `sys_dict` (
  `dict_id` varchar(100) NOT NULL,
  `dict_value` varchar(100) DEFAULT NULL,
  `dict_name` varchar(100) DEFAULT NULL,
  `dict_type` varchar(100) DEFAULT NULL,
  `dict_remarks` varchar(100) DEFAULT NULL,
  `dict_c_name` varchar(100) DEFAULT NULL,
  `dict_css_style` varchar(100) DEFAULT NULL,
  `dict_sort` int DEFAULT '0',
  `del` varchar(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21000', 'RK', '入库', 'TASK_BILL_H_TYPE', '单据：类型', 'RK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21001', 'CK', '出库', 'TASK_BILL_H_TYPE', '单据：类型', 'CK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21002', 'YK', '移库', 'TASK_BILL_H_TYPE', '单据表：类型', 'YK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21003', 'RK', '入库单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'RK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21004', 'CK', '出库单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'CK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21005', 'YK', '移库单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'YK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21006', 'ZC', '转储单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'ZC', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21007', 'CG', '采购订单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'CG', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21008', 'JH', '交货单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'JH', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b7ea8c21009', 'YL', '预留单', 'TASK_BILL_H_TYPE2', '单据表：类型', 'YL', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b8bc4821000', 'PUBLISH', '发布', 'TASK_BILL_H_STATE', '单据表：状态', 'PUBLISH', 'color: #337ab7;', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a0919ce2e421001', 'ABOLISH', '作废', 'TASK_BILL_H_STATE', '单据表：状态', 'ABOLISH', 'color: #a94442;', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98efc21000', 'COMPLETE', '完成', 'TASK_BILL_H_STATE', '单据表：状态', 'COMPLETE', 'color: #3c763d', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021000', '0', '空闲', 'MASTER_GOODS_LOCA_IS_USED', '货位表：占用状态', 'free', 'background-color: #ffffff;', 1, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021001', '2', '预订', 'MASTER_GOODS_LOCA_IS_USED', '货位表：占用状态', 'reserve', 'background-color: #9dff00;', 3, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021002', '1', '占用', 'MASTER_GOODS_LOCA_IS_USED', '货位表：占用状态', 'used', 'background-color: #eea236;', 4, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021003', '1', '无限制', 'MASTER_GOODS_LOCA_IS_UNLIMITED', '货位表：是否无限制容量', 'yes', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021004', '0', '限制', 'MASTER_GOODS_LOCA_IS_UNLIMITED', '货位表：是否无限制容量', 'no', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021005', 'lifnr', '供应商', 'MASTER_BUS_TYPE_BUS_OBJ_TYPE', '业务类型表：业务对象类型', 'lifnr', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021006', 'factory', '工厂', 'MASTER_BUS_TYPE_BUS_OBJ_TYPE', '业务类型表：业务对象类型', 'factory', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021007', 'warehouse', '仓库', 'MASTER_BUS_TYPE_BUS_OBJ_TYPE', '业务类型表：业务对象类型', 'warehouse', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448b98f0021008', 'dept', '部门', 'MASTER_BUS_TYPE_BUS_OBJ_TYPE', '业务类型表：业务对象类型', 'dept', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821000', 'ohter', '其它', 'MASTER_BUS_TYPE_BUS_OBJ_TYPE', '业务类型表：业务对象类型', 'ohter', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821001', 'FXZ', '非限制库存', 'MASTER_BUS_TYPE_STOCK_TYPE', '业务类型表：库存类型', 'FXZ', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821002', 'ZJ', '质检库存', 'MASTER_BUS_TYPE_STOCK_TYPE', '业务类型表：库存类型', 'ZJ', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821003', 'DJ', '冻结库存', 'MASTER_BUS_TYPE_STOCK_TYPE', '业务类型表：库存类型', 'DJ', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821004', 'ZT', '在途库存', 'MASTER_BUS_TYPE_STOCK_TYPE', '业务类型表：库存类型', 'ZT', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821005', 'RK', '入库', 'MASTER_BUS_TYPE_TRANSFER_TYPE', '业务类型表：移库类型', 'RK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821006', 'S', '借', 'MASTER_BUS_TYPE_BORROW_LOAN_FLAG', '业务类型表：借贷标识', 'S', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821007', 'H', '贷', 'MASTER_BUS_TYPE_BORROW_LOAN_FLAG', '业务类型表：借贷标识', 'H', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821008', 'CK', '出库', 'MASTER_BUS_TYPE_TRANSFER_TYPE', '业务类型表：移库类型', 'CK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448ba2e2821009', 'YK', '移库', 'MASTER_BUS_TYPE_TRANSFER_TYPE', '业务类型表：移库类型', 'YK', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448bab17021000', 'ZC', '转储', 'MASTER_BUS_TYPE_TRANSFER_TYPE', '业务类型表：移库类型', 'ZC', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448bab17021007', 'S', '借', 'MOVE_VOUCHER_I_BORROW_LOAN_FLAG', '移动凭证行项目表：借贷标识', 'S', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448bab17021008', 'H', '贷', 'MOVE_VOUCHER_I_BORROW_LOAN_FLAG', '移动凭证行项目表：借贷标识', 'H', '', 0, '0');
INSERT INTO loserstar.sys_dict
(dict_id, dict_value, dict_name, dict_type, dict_remarks, dict_c_name, dict_css_style, dict_sort, del)
VALUES('a448bab17021009', '4', '拟预定', 'MASTER_GOODS_LOCA_IS_USED', '货位表：占用状态', 'draft_reserve', 'background-color: #d9edf7;', 2, '0');
