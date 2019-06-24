/*
Navicat MySQL Data Transfer

Source Server         : 10.3.20.108
Source Server Version : 50546
Source Host           : 10.3.20.108:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50546
File Encoding         : 65001

Date: 2018-03-26 21:34:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名称',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别 0 女  /  1 男',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `create_time` int(11) DEFAULT NULL COMMENT '创建时间',
  `update_time` int(11) DEFAULT NULL COMMENT '更新时间',
  `state` tinyint(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
