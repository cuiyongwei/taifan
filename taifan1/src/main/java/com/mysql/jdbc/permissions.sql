/*
Navicat MySQL Data Transfer

Source Server         : xt1
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : permissions

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2018-12-29 15:57:37
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `category` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '教学过程');
INSERT INTO `menu` VALUES ('2', '资源库');
INSERT INTO `menu` VALUES ('3', '管理仓库');

-- ----------------------------
-- Table structure for `operation`
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `function` varchar(20) DEFAULT NULL,
  `mu_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mu_id` (`mu_id`),
  CONSTRAINT `mu_id` FOREIGN KEY (`mu_id`) REFERENCES `menu` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES ('1', '添加', '2');
INSERT INTO `operation` VALUES ('2', '删除', '2');
INSERT INTO `operation` VALUES ('3', '修改', null);
INSERT INTO `operation` VALUES ('4', '查询', '3');

-- ----------------------------
-- Table structure for `us`
-- ----------------------------
DROP TABLE IF EXISTS `us`;
CREATE TABLE `us` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL,
  `menu_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of us
-- ----------------------------
INSERT INTO `us` VALUES ('1', '2', '1');
INSERT INTO `us` VALUES ('2', '1', '2');
INSERT INTO `us` VALUES ('3', '2', '2');
INSERT INTO `us` VALUES ('4', '4', '2');
INSERT INTO `us` VALUES ('5', '4', '3');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '1111');
INSERT INTO `user` VALUES ('2', '李四', '222');
INSERT INTO `user` VALUES ('3', 'ooo', '424');
INSERT INTO `user` VALUES ('4', 'xiaoming', '555');
