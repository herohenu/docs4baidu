/*
Navicat MySQL Data Transfer

Source Server         : docs4baidu
Source Server Version : 50171
Source Host           : 192.168.17.106:3306
Source Database       : docs4baidu

Target Server Type    : MYSQL
Target Server Version : 50171
File Encoding         : 65001

Date: 2014-05-15 14:09:25
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `FILEVIEW`
-- ----------------------------
DROP TABLE IF EXISTS `FILEVIEW`;
CREATE TABLE `FILEVIEW` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `PATH` varchar(512) NOT NULL,
  `SIZE` int(11) NOT NULL,
  `TYPE` varchar(32) NOT NULL,
  `ACCESSTOKEN` varchar(512) NOT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of FILEVIEW
-- ----------------------------
