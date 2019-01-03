/*
Navicat MySQL Data Transfer

Source Server         : xt1
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : taifan

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-12-29 15:35:18
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `password` int(50) DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `user_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '123', '张三', 'ooo');
INSERT INTO `user` VALUES ('2', '李四', '123', '李四', 'ppp');
INSERT INTO `user` VALUES ('3', '王五', '555', '王五', 'aaa');
INSERT INTO `user` VALUES ('4', '赵六', '632', '赵六', 'qqq');
