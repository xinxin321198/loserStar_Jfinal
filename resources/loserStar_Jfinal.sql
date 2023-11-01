-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.20 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 loserstar 的数据库结构
CREATE DATABASE IF NOT EXISTS `loserstar` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `loserstar`;


-- 导出  表 loserstar.sys_dict 结构
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `dict_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `dict_value` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '值',
  `dict_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '名称',
  `dict_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '类型',
  `dict_remarks` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  `dict_c_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'java后端使用的常量名称',
  `dict_css_style` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '前端附加的样式',
  `dict_sort` int DEFAULT '0' COMMENT '排序码',
  `del` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人编号',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人编号',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- 正在导出表  loserstar.sys_dict 的数据：~2 rows (大约)
DELETE FROM `sys_dict`;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` (`dict_id`, `dict_value`, `dict_name`, `dict_type`, `dict_remarks`, `dict_c_name`, `dict_css_style`, `dict_sort`, `del`, `create_time`, `create_user_code`, `create_user_name`, `update_time`, `update_user_code`, `update_user_name`) VALUES
	('1006ab08d7421000', '1', '是', 'com_if', '通用类型，是否', 'IF_1', '', 1, '0', '2023-09-22 15:16:36', '15087010221', '', NULL, '', ''),
	('1006ab3cd1021000', '0', '否', 'com_if', '通用类型，是否', 'IF_0', '', 2, '0', '2023-09-22 15:17:29', '15087010221', '', '2023-09-22 15:17:48', '15087010221', NULL);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;


-- 导出  表 loserstar.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id，用作账号名',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `pwd_err_count` int NOT NULL DEFAULT '0' COMMENT '密码输入错误次数',
  `del` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标志',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人编号',
  `create_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建人姓名',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人编号',
  `update_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新人姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- 正在导出表  loserstar.sys_user 的数据：~3 rows (大约)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `user_name`, `password`, `pwd_err_count`, `del`, `create_time`, `create_user_code`, `create_user_name`, `update_time`, `update_user_code`, `update_user_name`) VALUES
	('15087010221', '老罗手机号', '123', 0, '0', NULL, NULL, NULL, '2023-11-01 17:25:05', '15087010221', NULL),
	('loserStar', '老罗', 'loserStar', 0, '0', '2023-11-01 17:23:26', '15087010221', NULL, '2023-11-01 17:23:38', '15087010221', NULL),
	('loserStar2', '老罗2', 'loserStar2', 1, '0', '2023-11-01 17:25:22', '15087010221', NULL, NULL, NULL, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
