/*
Navicat MySQL Data Transfer

Source Server         : xt1
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : education

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2019-01-09 15:52:55
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` char(36) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `parent_id` char(36) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `order` int(10) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `description` text,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `is_leaf` varchar(50) DEFAULT NULL,
  `default_menus` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('7f34daee13e011e9b7e7fc459644994c', 'string', '仓库管理', 'string', 'string', 'string', 'string', '0', 'string', 'string', 'string', '2019-01-09 15:30:54', 'string', null, 'string', 'string');
INSERT INTO `menu` VALUES ('9db4225613e111e9b7e7fc459644994c', 'string', '教学过程', 'string', 'string', '7f34daee13e011e9b7e7fc459644994c', 'string', '0', 'string', 'string', 'string', '2019-01-09 15:38:55', 'string', null, 'string', 'string');

-- ----------------------------
-- Table structure for `menu_resources`
-- ----------------------------
DROP TABLE IF EXISTS `menu_resources`;
CREATE TABLE `menu_resources` (
  `id` char(36) NOT NULL,
  `menu1_id` char(36) DEFAULT NULL,
  `resources_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `menu1_id` (`menu1_id`),
  KEY `resources_id` (`resources_id`),
  CONSTRAINT `menu1_id` FOREIGN KEY (`menu1_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resources_id` FOREIGN KEY (`resources_id`) REFERENCES `resources` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu_resources
-- ----------------------------
INSERT INTO `menu_resources` VALUES ('5c207f4a13e111e9b7e7fc459644994c', '7f34daee13e011e9b7e7fc459644994c', '4632d7d913e111e9b7e7fc459644994c');

-- ----------------------------
-- Table structure for `resources`
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` char(36) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `description` text,
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('4632d7d913e111e9b7e7fc459644994c', 'string', '添加', 'string', 'string', '2019-01-09 15:36:28', 'string', null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` char(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('0518eef513de11e9b7e7fc459644994c', '管理员', '159');
INSERT INTO `role` VALUES ('42b923d613d411e9b7e7fc459644994c', 'ppp', '111');

-- ----------------------------
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` char(36) NOT NULL,
  `role1_id` char(36) DEFAULT NULL,
  `menu_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role1_id` (`role1_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role1_id` FOREIGN KEY (`role1_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('9af1e2c413e011e9b7e7fc459644994c', '0518eef513de11e9b7e7fc459644994c', '7f34daee13e011e9b7e7fc459644994c');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2feaafbc13de11e9b7e7fc459644994c', '张三', '456');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` char(36) NOT NULL,
  `user_id` char(36) DEFAULT NULL,
  `role_id` char(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('77cbb6bb13df11e9b7e7fc459644994c', '2feaafbc13de11e9b7e7fc459644994c', '0518eef513de11e9b7e7fc459644994c');
