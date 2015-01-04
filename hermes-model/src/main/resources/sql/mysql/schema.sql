/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.21-log : Database - hermes
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`hermes` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hermes`;

/*Table structure for table `hm_advert` */

DROP TABLE IF EXISTS `hm_advert`;

CREATE TABLE `hm_advert` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 NOT NULL,
  `code` varchar(64) CHARACTER SET utf8 NOT NULL,
  `description` varchar(256) CHARACTER SET utf8 DEFAULT NULL,
  `image` longtext CHARACTER SET utf8,
  `link` varchar(256) CHARACTER SET utf8 DEFAULT NULL,
  `order` tinyint(4) NOT NULL,
  `creator` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `updater` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hm_advert` */

LOCK TABLES `hm_advert` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_area` */

DROP TABLE IF EXISTS `hm_area`;

CREATE TABLE `hm_area` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `parent` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上级',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='地区信息';

/*Data for the table `hm_area` */

LOCK TABLES `hm_area` WRITE;

insert  into `hm_area`(`id`,`parent`,`name`,`code`,`creator`,`create_time`,`updater`,`update_time`) values ('6bad933e-73ee-11e3-ae10-6cae8b21aeaa',NULL,'上海市','00210000',NULL,'2014-01-02 15:42:39',NULL,'2014-01-02 15:42:39'),('7e6fdc5c-73ee-11e3-ae10-6cae8b21aeaa','6bad933e-73ee-11e3-ae10-6cae8b21aeaa','上海市','00210100',NULL,'2014-01-02 15:43:11',NULL,'2014-01-02 15:43:11'),('87e9f55e-8449-11e3-ae10-6cae8b21aeaa','f26ef478-7c9d-11e3-ae10-6cae8b21aeaa','北京市','00100100',NULL,'2014-01-13 16:59:17',NULL,'2014-01-13 16:59:17'),('94d65ad4-73ee-11e3-ae10-6cae8b21aeaa','7e6fdc5c-73ee-11e3-ae10-6cae8b21aeaa','黄浦区','00210101',NULL,'2014-01-02 15:43:48',NULL,'2014-01-02 15:43:48'),('95ece3dc-8449-11e3-ae10-6cae8b21aeaa','87e9f55e-8449-11e3-ae10-6cae8b21aeaa','朝阳区','00100101',NULL,'2014-01-13 16:59:17',NULL,'2014-01-13 16:59:17'),('f26ef478-7c9d-11e3-ae10-6cae8b21aeaa',NULL,'北京市','00100000',NULL,'2014-01-13 16:59:17',NULL,'2014-01-13 16:59:17');

UNLOCK TABLES;

/*Table structure for table `hm_article` */

DROP TABLE IF EXISTS `hm_article`;

CREATE TABLE `hm_article` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `title` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '标题',
  `author` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '作者',
  `datetime` datetime NOT NULL COMMENT '日期',
  `summary` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '摘要',
  `mark` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标志',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='文章信息';

/*Data for the table `hm_article` */

LOCK TABLES `hm_article` WRITE;

insert  into `hm_article`(`id`,`title`,`author`,`datetime`,`summary`,`mark`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('0e1f3a3c-70fc-11e3-ae10-6cae8b21aeaa','多地房价涨幅过线挑战调控目标 专家吁兑现问责制','证大','2013-12-29 21:42:42',NULL,NULL,'10',NULL,'2013-12-29 21:42:42',NULL,'2013-12-29 21:42:42');

UNLOCK TABLES;

/*Table structure for table `hm_article_category` */

DROP TABLE IF EXISTS `hm_article_category`;

CREATE TABLE `hm_article_category` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `parent` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '上级',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代码',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='文章分类信息';

/*Data for the table `hm_article_category` */

LOCK TABLES `hm_article_category` WRITE;

insert  into `hm_article_category`(`id`,`parent`,`name`,`code`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('d6bda1c8-70fb-11e3-ae10-6cae8b21aeaa',NULL,'公告','notice','00',NULL,'2013-12-29 21:41:09',NULL,'2013-12-29 21:41:09');

UNLOCK TABLES;

/*Table structure for table `hm_article_category_reference` */

DROP TABLE IF EXISTS `hm_article_category_reference`;

CREATE TABLE `hm_article_category_reference` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `article` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '文章',
  `category` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '分类',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='文章分类关系信息';

/*Data for the table `hm_article_category_reference` */

LOCK TABLES `hm_article_category_reference` WRITE;

insert  into `hm_article_category_reference`(`id`,`article`,`category`,`creator`,`create_time`,`updater`,`update_time`) values ('5e848f04-70fc-11e3-ae10-6cae8b21aeaa','0e1f3a3c-70fc-11e3-ae10-6cae8b21aeaa','d6bda1c8-70fb-11e3-ae10-6cae8b21aeaa',NULL,'2013-12-29 21:44:57',NULL,'2013-12-29 21:44:57');

UNLOCK TABLES;

/*Table structure for table `hm_bank` */

DROP TABLE IF EXISTS `hm_bank`;

CREATE TABLE `hm_bank` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `logo` longtext COLLATE utf8_unicode_ci COMMENT '图片',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='银行信息';

/*Data for the table `hm_bank` */

LOCK TABLES `hm_bank` WRITE;

insert  into `hm_bank`(`id`,`name`,`code`,`logo`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('067ca118-74bf-11e3-ae10-6cae8b21aeaa','中国建设银行','CCB',NULL,'00',NULL,'2014-01-03 16:35:54',NULL,'2014-01-03 16:35:54'),('1d9ebe3a-74bf-11e3-ae10-6cae8b21aeaa','中国农业银行','ABCHINA',NULL,'00',NULL,'2014-01-03 16:36:33',NULL,'2014-01-03 16:36:33'),('a8587ebe-73f0-11e3-ae10-6cae8b21aeaa','中国工商银行','ICBC',NULL,'00',NULL,'2014-01-02 15:58:40',NULL,'2014-01-02 15:58:40'),('a858c05e-73f0-11e3-ae10-6cae8b21aeaa','中国银行','BOC',NULL,'00',NULL,'2014-01-02 15:58:40',NULL,'2014-01-02 15:58:40');

UNLOCK TABLES;

/*Table structure for table `hm_bank_account` */

DROP TABLE IF EXISTS `hm_bank_account`;

CREATE TABLE `hm_bank_account` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `bank` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '银行',
  `city` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市',
  `deposit` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '开户行',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '开户人',
  `account` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '帐号',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='银行帐号信息';

/*Data for the table `hm_bank_account` */

LOCK TABLES `hm_bank_account` WRITE;

insert  into `hm_bank_account`(`id`,`user`,`bank`,`city`,`deposit`,`name`,`account`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('234fae70-9617-4bff-94ef-90168a241dc4','2c5506fc-81e2-4a23-af31-f93d96559985','067ca118-74bf-11e3-ae10-6cae8b21aeaa','7e6fdc5c-73ee-11e3-ae10-6cae8b21aeaa','张江','贷款','123456','00','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-19 13:38:18','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-19 13:38:18'),('25ede0de-c6af-45b5-b4d6-ec2963abb37b','82cc6810-a943-4ba1-b579-53da72bed680','067ca118-74bf-11e3-ae10-6cae8b21aeaa','87e9f55e-8449-11e3-ae10-6cae8b21aeaa','12432424212413','隐身','2341241er','00','82cc6810-a943-4ba1-b579-53da72bed680','2014-04-21 00:46:47','82cc6810-a943-4ba1-b579-53da72bed680','2014-04-21 00:46:47'),('8bb09ed9-13a5-4e16-aa2d-bd4e3cad72fa','82cc6810-a943-4ba1-b579-53da72bed680','067ca118-74bf-11e3-ae10-6cae8b21aeaa','87e9f55e-8449-11e3-ae10-6cae8b21aeaa','1241241','zzhas','1242211411','00','82cc6810-a943-4ba1-b579-53da72bed680','2014-02-25 11:32:43','82cc6810-a943-4ba1-b579-53da72bed680','2014-02-25 11:32:43'),('a7c8795b-f44c-41c8-a7fc-3f52af276008','2c5506fc-81e2-4a23-af31-f93d96559985','067ca118-74bf-11e3-ae10-6cae8b21aeaa','87e9f55e-8449-11e3-ae10-6cae8b21aeaa','联洋支行','贷款','1122334455667788','00','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-19 13:37:21','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-19 13:37:21'),('d9b85be3-6ee9-4b9a-ab88-de6c7536b347','29218f13-38ba-496e-9f21-6ae65a05112e','067ca118-74bf-11e3-ae10-6cae8b21aeaa','87e9f55e-8449-11e3-ae10-6cae8b21aeaa','','1111111','','00','29218f13-38ba-496e-9f21-6ae65a05112e','2014-02-19 14:47:38','29218f13-38ba-496e-9f21-6ae65a05112e','2014-02-19 14:47:38');

UNLOCK TABLES;

/*Table structure for table `hm_dictionary` */

DROP TABLE IF EXISTS `hm_dictionary`;

CREATE TABLE `hm_dictionary` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `type` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `order` tinyint(4) NOT NULL COMMENT '顺序',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='字典信息';

/*Data for the table `hm_dictionary` */

LOCK TABLES `hm_dictionary` WRITE;

insert  into `hm_dictionary`(`id`,`type`,`name`,`code`,`order`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','ec445644-7fb6-11e3-ae10-6cae8b21aeaa','控制台','console',0,'00',NULL,'2014-01-17 15:36:28',NULL,'2014-01-17 15:36:28'),('ecd67c52-7103-11e3-ae10-6cae8b21aeaa','176c9150-7103-11e3-ae10-6cae8b21aeaa','经营周转','purpose_1',0,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02'),('ecd70168-7103-11e3-ae10-6cae8b21aeaa','176c9150-7103-11e3-ae10-6cae8b21aeaa','投资创业','purpose_2',1,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02'),('ecd732f0-7103-11e3-ae10-6cae8b21aeaa','176c9150-7103-11e3-ae10-6cae8b21aeaa','购房装修','purpose_3',2,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02'),('ecd76338-7103-11e3-ae10-6cae8b21aeaa','176c9150-7103-11e3-ae10-6cae8b21aeaa','教育培训','purpose_4',3,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02'),('ecd7b432-7103-11e3-ae10-6cae8b21aeaa','176c9150-7103-11e3-ae10-6cae8b21aeaa','购车借款','purpose_5',4,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02'),('f15df3f6-9230-11e3-8035-e41f13befebc','176c9150-7103-11e3-ae10-6cae8b21aeaa','测试类型','purpose_6',7,'00',NULL,'2013-12-29 22:39:02',NULL,'2013-12-29 22:39:02');

UNLOCK TABLES;

/*Table structure for table `hm_dictionary_type` */

DROP TABLE IF EXISTS `hm_dictionary_type`;

CREATE TABLE `hm_dictionary_type` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='字典类型信息';

/*Data for the table `hm_dictionary_type` */

LOCK TABLES `hm_dictionary_type` WRITE;

insert  into `hm_dictionary_type`(`id`,`name`,`code`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('176c9150-7103-11e3-ae10-6cae8b21aeaa','借款用途','loan_purpose','00',NULL,'2013-12-29 22:33:04',NULL,'2013-12-29 22:33:04'),('8fcaf41e-73e4-11e3-ae10-6cae8b21aeaa','证件类型','id_type','00',NULL,'2014-01-02 00:00:00',NULL,'2014-01-02 00:00:00'),('ec445644-7fb6-11e3-ae10-6cae8b21aeaa','导航类型','nav','00',NULL,'2014-01-17 15:35:37',NULL,'2014-01-17 15:35:37');

UNLOCK TABLES;

/*Table structure for table `hm_friend_link` */

DROP TABLE IF EXISTS `hm_friend_link`;

CREATE TABLE `hm_friend_link` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 NOT NULL,
  `link` varchar(256) CHARACTER SET utf8 NOT NULL,
  `image` longtext CHARACTER SET utf8,
  `order` tinyint(4) NOT NULL,
  `status` varchar(2) CHARACTER SET utf8 NOT NULL,
  `creator` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `updater` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hm_friend_link` */

LOCK TABLES `hm_friend_link` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_invest` */

DROP TABLE IF EXISTS `hm_invest`;

CREATE TABLE `hm_invest` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `loan` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `amount` decimal(16,4) NOT NULL COMMENT '投资金额',
  `datetime` datetime NOT NULL COMMENT '投资日期',
  `ratio` decimal(16,8) NOT NULL COMMENT '所占比例',
  `other_repay` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '垫付',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='理财信息';

/*Data for the table `hm_invest` */

LOCK TABLES `hm_invest` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_invest_profit` */

DROP TABLE IF EXISTS `hm_invest_profit`;

CREATE TABLE `hm_invest_profit` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `invest` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '理财',
  `loan_repay` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款还款',
  `date` datetime NOT NULL COMMENT '日期',
  `amount` decimal(16,4) NOT NULL COMMENT '总金额',
  `principal` decimal(16,4) NOT NULL COMMENT '本金',
  `interest` decimal(16,4) NOT NULL COMMENT '利息',
  `overdue_interest` decimal(16,4) NOT NULL COMMENT '逾期利息',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='理财收益信息';

/*Data for the table `hm_invest_profit` */

LOCK TABLES `hm_invest_profit` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_label` */

DROP TABLE IF EXISTS `hm_label`;

CREATE TABLE `hm_label` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hm_label` */

LOCK TABLES `hm_label` WRITE;

insert  into `hm_label`(`id`,`name`,`creator`,`create_time`,`updater`,`update_time`) values ('77a46ff7-b408-4ed0-9846-fff6e2238ea5','信用报告','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-27 10:44:32','2c5506fc-81e2-4a23-af31-f93d96559985','2014-02-27 10:44:32'),('9ee07838-a767-4ab5-8d0f-b081766cdbac','收入证明','f15d044e-6dd4-11e3-ae10-6cae8b21aeaa','2014-01-09 13:21:31','f15d044e-6dd4-11e3-ae10-6cae8b21aeaa','2014-01-09 13:21:31'),('b257df00-78ae-11e3-ae10-6cae8b21aeaa','身份证',NULL,'2014-01-08 00:00:00',NULL,'2014-01-08 00:00:00'),('c21d17fc-78ae-11e3-ae10-6cae8b21aeaa','房产证',NULL,'2014-01-08 00:00:00',NULL,'2014-01-08 00:00:00'),('cc017dd0-78ae-11e3-ae10-6cae8b21aeaa','工作证明',NULL,'2014-01-08 00:00:00',NULL,'2014-01-08 00:00:00');

UNLOCK TABLES;

/*Table structure for table `hm_loan` */

DROP TABLE IF EXISTS `hm_loan`;

CREATE TABLE `hm_loan` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `product` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品',
  `repay` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '还款方式',
  `purpose` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款用途',
  `loan_no` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款编号',
  `amount` decimal(16,4) NOT NULL COMMENT '金额',
  `proceeds` decimal(16,4) NOT NULL COMMENT '已筹金额',
  `period` tinyint(4) NOT NULL COMMENT '期限',
  `rate` decimal(8,4) NOT NULL COMMENT '利率',
  `deadline` tinyint(4) NOT NULL COMMENT '招标期限',
  `datetime` datetime DEFAULT NULL COMMENT '发布日期',
  `description` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `manage_fee` decimal(16,4) NOT NULL COMMENT '管理费',
  `manage_fee_type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '管理费类型',
  `remark` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='借款信息';

/*Data for the table `hm_loan` */

LOCK TABLES `hm_loan` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_loan_audit` */

DROP TABLE IF EXISTS `hm_loan_audit`;

CREATE TABLE `hm_loan_audit` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `loan` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `auditor` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '审核人',
  `datetime` datetime NOT NULL COMMENT '日期',
  `remark` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '备注',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='借款审核信息';

/*Data for the table `hm_loan_audit` */

LOCK TABLES `hm_loan_audit` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_loan_auth` */

DROP TABLE IF EXISTS `hm_loan_auth`;

CREATE TABLE `hm_loan_auth` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `loan` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `label` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '标签',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='借款审核信息';

/*Data for the table `hm_loan_auth` */

LOCK TABLES `hm_loan_auth` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_loan_log` */

DROP TABLE IF EXISTS `hm_loan_log`;

CREATE TABLE `hm_loan_log` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户',
  `loan` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `datetime` datetime NOT NULL COMMENT '日期',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `amount` decimal(16,4) DEFAULT NULL COMMENT '金额',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='借款日志';

/*Data for the table `hm_loan_log` */

LOCK TABLES `hm_loan_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_loan_overdue` */

DROP TABLE IF EXISTS `hm_loan_overdue`;

CREATE TABLE `hm_loan_overdue` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `loan` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `rank` tinyint(4) NOT NULL COMMENT '等级',
  `interest` decimal(16,8) NOT NULL COMMENT '罚息',
  `penalty` decimal(16,8) NOT NULL COMMENT '违约金',
  `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='借款逾期费率信息';

/*Data for the table `hm_loan_overdue` */

LOCK TABLES `hm_loan_overdue` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_loan_repay` */

DROP TABLE IF EXISTS `hm_loan_repay`;

CREATE TABLE `hm_loan_repay` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `loan` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '借款',
  `sequence` tinyint(4) NOT NULL COMMENT '期数',
  `plan_datetime` datetime NOT NULL COMMENT '计划日期',
  `repay_datetime` datetime DEFAULT NULL COMMENT '还款日期',
  `amount` decimal(16,4) NOT NULL COMMENT '总金额',
  `principal` decimal(16,4) NOT NULL COMMENT '本金',
  `interest` decimal(16,4) NOT NULL COMMENT '利息',
  `overdue_days` int(11) NOT NULL COMMENT '逾期天数',
  `overdue_interest` decimal(16,4) NOT NULL COMMENT '逾期罚息',
  `overdue_penalty` decimal(16,4) NOT NULL COMMENT '逾期违约金',
  `other_amount` decimal(16,4) NOT NULL COMMENT '其他金额',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='借款还款信息';

/*Data for the table `hm_loan_repay` */

LOCK TABLES `hm_loan_repay` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_navigation` */

DROP TABLE IF EXISTS `hm_navigation`;

CREATE TABLE `hm_navigation` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `parent` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '父级',
  `type` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `subname` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '副称',
  `code` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代码',
  `path` varchar(128) COLLATE utf8_unicode_ci NOT NULL COMMENT '地址',
  `target` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '目标',
  `order` tinyint(4) NOT NULL COMMENT '顺序',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='导航信息';

/*Data for the table `hm_navigation` */

LOCK TABLES `hm_navigation` WRITE;

insert  into `hm_navigation`(`id`,`parent`,`type`,`name`,`subname`,`code`,`path`,`target`,`order`,`creator`,`create_time`,`updater`,`update_time`) values ('3bb6a574-7fb7-11e3-ae10-6cae8b21aeaa',NULL,'0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','首页',NULL,'home','@/home','_main',0,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('7649d102-7fb7-11e3-ae10-6cae8b21aeaa',NULL,'0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','信息查询',NULL,'search','@/menu','_menu',1,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('8290abe8-7fb7-11e3-ae10-6cae8b21aeaa',NULL,'0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','资金管理',NULL,'jpy','@/menu','_menu',2,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('8661957a-820f-11e3-ae10-6cae8b21aeaa','7649d102-7fb7-11e3-ae10-6cae8b21aeaa','0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','借款信息',NULL,'loan','@/loan/index','_main',0,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('8dfd503a-7fb7-11e3-ae10-6cae8b21aeaa',NULL,'hiden','网站维护',NULL,'globe','@/menu','_menu',3,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('9dde88a6-8215-11e3-ae10-6cae8b21aeaa','8290abe8-7fb7-11e3-ae10-6cae8b21aeaa','0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','提现处理',NULL,'withdraw','@/withdraw/index','_main',1,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('9f76614e-7fb7-11e3-ae10-6cae8b21aeaa',NULL,'hiden','系统管理',NULL,'cogs','@/menu','_menu',4,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50'),('9f893350-820f-11e3-ae10-6cae8b21aeaa','7649d102-7fb7-11e3-ae10-6cae8b21aeaa','0aaafbb0-7fb7-11e3-ae10-6cae8b21aeaa','客户信息',NULL,'user','@/user/index','_main',1,NULL,'2014-01-17 15:37:50',NULL,'2014-01-17 15:37:50');

UNLOCK TABLES;

/*Table structure for table `hm_payment` */

DROP TABLE IF EXISTS `hm_payment`;

CREATE TABLE `hm_payment` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `channel` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '渠道',
  `datetime` datetime NOT NULL COMMENT '时间',
  `sequence` bigint(20) NOT NULL COMMENT '序号',
  `amount` decimal(16,4) NOT NULL COMMENT '金额',
  `fee` decimal(16,4) NOT NULL COMMENT '手续费',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='支付信息';

/*Data for the table `hm_payment` */

LOCK TABLES `hm_payment` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_payment_channel` */

DROP TABLE IF EXISTS `hm_payment_channel`;

CREATE TABLE `hm_payment_channel` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `subname` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '副称',
  `code` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '代码',
  `logo` longtext COLLATE utf8_unicode_ci COMMENT '图片',
  `order` smallint(6) NOT NULL COMMENT '顺序',
  `clazz` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '实现类',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='支付渠道信息';

/*Data for the table `hm_payment_channel` */

LOCK TABLES `hm_payment_channel` WRITE;

insert  into `hm_payment_channel`(`id`,`name`,`subname`,`code`,`logo`,`order`,`clazz`,`status`,`type`,`creator`,`create_time`,`updater`,`update_time`) values ('33f1865c-9a55-11e3-85fa-6cae8b21aeaa','国付宝','测试','goPaymentChannelImpl','data:image/png;base64,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',1,NULL,'00','00',NULL,'2014-02-20 12:34:08',NULL,'2014-02-20 12:34:08'),('5320f65a-788d-11e3-ae10-6cae8b21aeaa','中国工商银行','模拟','fakePaymentChannelImpl','data:image/png;base64,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',0,NULL,'00','01',NULL,'2014-01-08 12:50:13',NULL,'2014-01-08 12:50:13'),('ee8d5adc-7885-11e3-ae10-6cae8b21aeaa','中国银联','模拟',NULL,'data:image/png;base64,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',0,'com.jlfex.hermes.service.payment.FakePaymentChannelImpl','00','00',NULL,'2014-01-08 11:57:17',NULL,'2014-01-08 11:57:17');

UNLOCK TABLES;

/*Table structure for table `hm_payment_channel_attribute` */

DROP TABLE IF EXISTS `hm_payment_channel_attribute`;

CREATE TABLE `hm_payment_channel_attribute` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `channel` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '支付网关',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `value` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '内容',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='支付渠道参数信息';

/*Data for the table `hm_payment_channel_attribute` */

LOCK TABLES `hm_payment_channel_attribute` WRITE;

insert  into `hm_payment_channel_attribute`(`id`,`channel`,`name`,`code`,`value`,`creator`,`create_time`,`updater`,`update_time`) values ('0a752ef4-9a79-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','识别码','VerficationCode','12345678',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('2ede7946-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','提交地址','postUrl','https://mertest.gopay.com.cn/PGServer/Trans/WebClientAction.do',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('3a1903fc-9a78-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','币种','currencyType','156',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('4b35862a-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','版本','version','2.1',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('63961220-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','字符集','charset','2',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('6edcb23a-7969-11e3-ae10-6cae8b21aeaa','5320f65a-788d-11e3-ae10-6cae8b21aeaa','支付地址','url','http://172.16.230.180:8080/hermes/fake/payment',NULL,'2014-01-09 15:05:48',NULL,'2014-01-09 15:05:48'),('708dce44-7977-11e3-ae10-6cae8b21aeaa','ee8d5adc-7885-11e3-ae10-6cae8b21aeaa','支付地址','url','http://172.16.230.180:8080/hermes/fake/payment',NULL,'2014-01-09 16:46:04',NULL,'2014-01-09 16:46:04'),('77c3bbee-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','语言','language','1',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('a6572b0c-9a78-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','转入帐号','virCardNoIn','0000000001000000584',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('a8f45ce6-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','加密方式','signType','1',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('be63a2e8-9a78-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','重复提交','isRepeatSubmit','1',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('c0db7a4c-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','交易代码','tranCode','8888',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('d1f42982-9a68-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','商户代码','merchantID','0000003358',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00'),('daaccf1a-9a78-11e3-85fa-6cae8b21aeaa','33f1865c-9a55-11e3-85fa-6cae8b21aeaa','商品名称','goodsName','Hermes充值',NULL,'2014-02-20 14:50:00',NULL,'2014-02-20 14:50:00');

UNLOCK TABLES;

/*Table structure for table `hm_payment_log` */

DROP TABLE IF EXISTS `hm_payment_log`;

CREATE TABLE `hm_payment_log` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `payment` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '支付',
  `datetime` datetime NOT NULL COMMENT '时间',
  `method` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '方式',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `raw` text COLLATE utf8_unicode_ci NOT NULL COMMENT '原始数据',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='支付日志信息';

/*Data for the table `hm_payment_log` */

LOCK TABLES `hm_payment_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_product` */

DROP TABLE IF EXISTS `hm_product`;

CREATE TABLE `hm_product` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `repay` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '还款方式',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `amount` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '金额',
  `period` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '期限',
  `rate` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '利率',
  `deadline` tinyint(4) NOT NULL COMMENT '招标期限',
  `logo` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '图片',
  `description` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
  `view` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '视图',
  `manage_fee` decimal(16,4) NOT NULL COMMENT '管理费',
  `manage_fee_type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '管理费类型',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='产品信息';

/*Data for the table `hm_product` */

LOCK TABLES `hm_product` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_product_overdue` */

DROP TABLE IF EXISTS `hm_product_overdue`;

CREATE TABLE `hm_product_overdue` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `product` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '产品',
  `rank` tinyint(4) NOT NULL COMMENT '等级',
  `interest` decimal(16,8) NOT NULL COMMENT '罚息',
  `penalty` decimal(16,8) NOT NULL COMMENT '违约金',
  `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品逾期费率信息';

/*Data for the table `hm_product_overdue` */

LOCK TABLES `hm_product` WRITE;
/*!40000 ALTER TABLE `hm_product` DISABLE KEYS */;
INSERT INTO `hm_product` VALUES ('e4a83c6f-cf4c-4bb4-bb34-2cf0e6c883dd','0070e4bf-ba52-4c6e-99e4-3f1dd3ad7531','工薪贷','01','5000,30000','3,24','10,24',5,NULL,'针对工薪阶层',NULL,'0.0050','00','00',NULL,'2014-01-01 16:38:00',NULL,'2014-01-01 16:38:08'),('e4a83c6f-cf4c-4bb4-bb34-2cf0e6c883ec','0070e4bf-ba52-4c6e-99e4-3f1dd3ad7531','学生贷','02','1000,10000','3,6','10,15',3,NULL,'面向学生人群',NULL,'0.0040','00','00',NULL,'2013-12-23 10:48:54',NULL,'2013-12-23 10:48:54'),('e4a83c6f-cf4c-4bb4-bb34-2cf0e6c883ee','0070e4bf-ba52-4c6e-99e4-3f1dd3ad7531','经营贷','03','20000,100000','6,36','12,24',7,NULL,'淘宝经销商等',NULL,'0.0030','00','00',NULL,'2014-01-01 16:40:21',NULL,'2014-01-02 16:40:27');
/*!40000 ALTER TABLE `hm_product` ENABLE KEYS */;
UNLOCK TABLES;

/*Table structure for table `hm_properties` */

DROP TABLE IF EXISTS `hm_properties`;

CREATE TABLE `hm_properties` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `value` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '内容',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系统属性信息';

/*Data for the table `hm_properties` */

LOCK TABLES `hm_properties` WRITE;

insert  into `hm_properties`(`id`,`name`,`code`,`value`,`creator`,`create_time`,`updater`,`update_time`) values ('37c10838-793b-11e3-ae10-6cae8b21aeaa','充值手续费','fee.charge.rate','0.01',NULL,'2014-01-09 09:34:59',NULL,'2014-01-09 09:34:59'),('3fc8c856-795c-11e3-ae10-6cae8b21aeaa','客服电话','site.service.tel','400-123-45678',NULL,'2014-01-09 13:31:26',NULL,'2014-01-09 13:31:26'),('3fc922ba-795c-11e3-ae10-6cae8b21aeaa','工作时间','site.service.time','9:00~21:01',NULL,'2014-01-09 13:31:26',NULL,'2014-01-09 13:31:26'),('446c46f6-9b14-11e3-85fa-6cae8b21aeaa','提现手续费说明','fee.withdraw.desc','20000及以下每笔3元，20000-100000每笔5元',NULL,'2014-02-21 11:21:50',NULL,'2014-02-21 11:21:50'),('4ca151d8-74b1-11e3-ae10-6cae8b21aeaa','提现手续费','fee.withdraw.config','0:20000,3;20000:100000,5',NULL,'2014-01-03 14:57:39',NULL,'2014-01-03 14:57:39'),('4f0aaf8e-7559-11e3-ae10-6cae8b21aeaa','提现手续费方式','fee.withdraw.name','intervalWithdrawFee',NULL,'2014-01-04 11:00:18',NULL,'2014-01-04 11:00:18'),('55bc5546-7106-11e3-ae10-6cae8b21aeaa','首页借款数量','index.loan.size','5',NULL,'2013-12-29 22:56:17',NULL,'2013-12-29 22:56:17'),('59483e98-296f-460f-99b1-71decb4c96a8','公司名称','app.company.name','上海金鹿金融信息服务有限公司',NULL,'2014-02-25 09:35:50',NULL,'2014-02-25 09:35:50'),('5c73655e-6df6-11e3-ae10-6cae8b21aeaa','实名认证开关','auth.realname.switch','1',NULL,'2013-12-26 14:00:00',NULL,'2013-12-26 14:00:00'),('72aae572-6df6-11e3-ae10-6cae8b21aeaa','手机认证开关','auth.cellphone.switch','1',NULL,'2013-12-26 14:00:00',NULL,'2013-12-26 14:00:00'),('8affae86-6c4e-11e3-ae10-6cae8b21aeaa','用户认证标签','user.auth.labels','身份证,信用报告,房产证,工作证明',NULL,'2013-12-24 13:38:50',NULL,'2013-12-24 13:38:55'),('98757e78-9b39-11e3-85fa-6cae8b21aeaa','充值手续费说明','fee.charge.desc','每笔1%手续费',NULL,'2014-01-09 09:34:59',NULL,'2014-01-09 09:34:59'),('ab864f3b-d75c-43ff-a182-f5cc619cfcb9','公司地址','app.company.address','上海市浦东新区东方路969号中油阳光大厦12楼 ',NULL,'2014-02-25 09:42:09',NULL,'2014-02-25 09:42:09'),('c344e9de-7953-11e3-ae10-6cae8b21aeaa','应用版权','app.copyright','&copy; 2014 Hermes.com All rights reserved.',NULL,'2014-01-09 12:30:41',NULL,'2014-01-09 12:30:41'),('c76c686e-712b-11e3-ae10-6cae8b21aeaa','邮件服务器','mail.smtp.host','smtpcom.263xmail.com',NULL,'2013-12-30 03:24:19',NULL,'2013-12-30 03:24:19'),('c76cab62-712b-11e3-ae10-6cae8b21aeaa','邮件端口','mail.smtp.port','25',NULL,'2013-12-30 03:24:19',NULL,'2013-12-30 03:24:19'),('c8d8b45a-6c5d-11e3-ae10-6cae8b21aeaa','邮件认证失效','auth.mail.expire','2d',NULL,'2013-12-24 13:00:00',NULL,'2013-12-24 13:00:00'),('ca16e1f0-6616-478e-be93-5378476130cf','投标金额倍数','invest.bid.multiple','100',NULL,'2014-02-25 09:48:13',NULL,'2014-02-25 09:48:13'),('d3b950ec-95ba-11e3-85fa-6cae8b21aeaa','自动任务通知','address.job.notice','yanlei@jinlufund.com',NULL,'2014-02-14 15:58:59',NULL,'2014-02-14 15:58:59'),('d4289700-7953-11e3-ae10-6cae8b21aeaa','应用备案','app.icp','沪ICP证 100002号',NULL,'2014-01-09 12:31:09',NULL,'2014-01-09 12:31:09'),('e18c83d2-712b-11e3-ae10-6cae8b21aeaa','邮件帐号','mail.smtp.username','yanlei@jinlufund.com',NULL,'2013-12-30 03:25:03',NULL,'2013-12-30 03:25:03'),('e18ea2e8-712b-11e3-ae10-6cae8b21aeaa','邮件密码','mail.smtp.password','Wise_123',NULL,'2013-12-30 03:25:03',NULL,'2013-12-30 03:25:03'),('eed2b812-6df8-11e3-ae10-6cae8b21aeaa','短信失效时间','auth.sms.expire','30min',NULL,'2013-12-26 14:00:00',NULL,'2013-12-26 14:00:00'),('f5871988-712b-11e3-ae10-6cae8b21aeaa','邮件发送地址','mail.from','yanlei@jinlufund.com',NULL,'2013-12-30 03:25:36',NULL,'2013-12-30 03:25:36'),('f955fdfe-70fe-11e3-ae10-6cae8b21aeaa','应用标题','app.title','Hermes',NULL,'2013-12-29 22:03:35',NULL,'2013-12-29 22:03:35');

UNLOCK TABLES;

/*Table structure for table `hm_rate` */

DROP TABLE IF EXISTS `hm_rate`;

CREATE TABLE `hm_rate` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `product` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '产品',
  `rate` decimal(16,8) NOT NULL COMMENT '费率',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='费率信息';

/*Data for the table `hm_rate` */

LOCK TABLES `hm_rate` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_rate_log` */

DROP TABLE IF EXISTS `hm_rate_log`;

CREATE TABLE `hm_rate_log` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `rate` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '费率',
  `datetime` datetime NOT NULL COMMENT '日期',
  `before` decimal(16,8) NOT NULL COMMENT '变更前',
  `after` decimal(16,8) NOT NULL COMMENT '变更后',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='费率日志信息';

/*Data for the table `hm_rate_log` */

LOCK TABLES `hm_rate_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_repay` */

DROP TABLE IF EXISTS `hm_repay`;

CREATE TABLE `hm_repay` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `clazz` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '实现类',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='还款方式信息';

/*Data for the table `hm_repay` */

LOCK TABLES `hm_repay` WRITE;

insert  into `hm_repay`(`id`,`name`,`code`,`clazz`,`status`,`creator`,`create_time`,`updater`,`update_time`) values ('0070e4bf-ba52-4c6e-99e4-3f1dd3ad7531','等额本息','Principal','com.jlfex.hermes.service.repay.MatchingRepayMethod','00',NULL,'2013-12-23 10:17:19',NULL,'2013-12-23 10:17:19');

UNLOCK TABLES;

/*Table structure for table `hm_role` */

DROP TABLE IF EXISTS `hm_role`;

CREATE TABLE `hm_role` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色信息';

/*Data for the table `hm_role` */

LOCK TABLES `hm_role` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_role_resource` */

DROP TABLE IF EXISTS `hm_role_resource`;

CREATE TABLE `hm_role_resource` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `role` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色',
  `resource` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '资源',
  `type` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='角色资源关系信息';

/*Data for the table `hm_role_resource` */

LOCK TABLES `hm_role_resource` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_text` */

DROP TABLE IF EXISTS `hm_text`;

CREATE TABLE `hm_text` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL,
  `reference` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `text` longtext CHARACTER SET utf8 NOT NULL,
  `type` varchar(2) CHARACTER SET utf8 NOT NULL,
  `creator` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `updater` varchar(36) CHARACTER SET utf8 DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Data for the table `hm_text` */

LOCK TABLES `hm_text` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_transaction` */

DROP TABLE IF EXISTS `hm_transaction`;

CREATE TABLE `hm_transaction` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `source_user_account` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '来源账户',
  `target_user_account` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '目标账户',
  `reference` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '关系',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `datetime` datetime NOT NULL COMMENT '日期',
  `amount` decimal(16,4) NOT NULL COMMENT '金额',
  `source_before_balance` decimal(16,4) NOT NULL COMMENT '来源账户交易前余额',
  `source_after_balance` decimal(16,4) NOT NULL COMMENT '来源账户交易后余额',
  `target_before_balance` decimal(16,4) NOT NULL COMMENT '目标账户交易前余额',
  `target_after_balance` decimal(16,4) NOT NULL COMMENT '目标账户交易后余额',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='交易流水信息';

/*Data for the table `hm_transaction` */

LOCK TABLES `hm_transaction` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user` */

DROP TABLE IF EXISTS `hm_user`;

CREATE TABLE `hm_user` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `account` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '帐号',
  `email` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮件',
  `cellphone` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机',
  `sign_password` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '登录密码',
  `pay_password` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '支付密码',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户信息';

/*Data for the table `hm_user` */

LOCK TABLES `hm_user` WRITE;

insert  into `hm_user`(`id`,`account`,`email`,`cellphone`,`sign_password`,`pay_password`,`status`,`type`,`creator`,`create_time`,`updater`,`update_time`) values ('3989f3f4-6848-4e17-a4a9-77b5369c120e','aaaaa','yanlei@jinlufund.com','18702168698','0b4e7a0e5fe84ad35fb5f95b9ceeac79',NULL,'01','00',NULL,'2014-12-30 10:07:00',NULL,'2014-12-30 10:07:10'),('e74428d8-7fb4-11e3-ae10-6cae8b21aeaa','admin','test_liwu@163.com','18702168698','21232f297a57a5a743894a0e4a801fc3',NULL,'00','10',NULL,'2014-01-17 15:21:10',NULL,'2014-01-17 15:21:10');

UNLOCK TABLES;

/*Table structure for table `hm_user_account` */

DROP TABLE IF EXISTS `hm_user_account`;

CREATE TABLE `hm_user_account` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `balance` decimal(16,4) NOT NULL COMMENT '余额',
  `minus` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '负数',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户账户信息';

/*Data for the table `hm_user_account` */

LOCK TABLES `hm_user_account` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_address` */

DROP TABLE IF EXISTS `hm_user_address`;

CREATE TABLE `hm_user_address` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `province` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '省份',
  `city` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市',
  `county` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '区县',
  `address` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '地址',
  `zip` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮编',
  `phone` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '固定电话',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户联系地址信息';

/*Data for the table `hm_user_address` */

LOCK TABLES `hm_user_address` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_auth` */

DROP TABLE IF EXISTS `hm_user_auth`;

CREATE TABLE `hm_user_auth` (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '代码',
  `type` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `expire` datetime NOT NULL COMMENT '过期时间',
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户认证信息';



/*Table structure for table `hm_user_car` */

DROP TABLE IF EXISTS `hm_user_car`;

CREATE TABLE `hm_user_car` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `brand` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '品牌',
  `purchase_year` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '购入年份',
  `purchase_amount` decimal(16,4) NOT NULL COMMENT '购入价格',
  `licence_plate` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '牌照',
  `mortgage` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '按揭',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户车辆信息';

/*Data for the table `hm_user_car` */

LOCK TABLES `hm_user_car` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_contacter` */

DROP TABLE IF EXISTS `hm_user_contacter`;

CREATE TABLE `hm_user_contacter` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '姓名',
  `relationship` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '关系',
  `address` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `phone` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '电话',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户联系人信息';

/*Data for the table `hm_user_contacter` */

LOCK TABLES `hm_user_contacter` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_education` */

DROP TABLE IF EXISTS `hm_user_education`;

CREATE TABLE `hm_user_education` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `school` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '毕业学校',
  `year` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '毕业年份',
  `degree` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学历',
  `degree_number` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '学历编号',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户学历信息';

/*Data for the table `hm_user_education` */

LOCK TABLES `hm_user_education` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_house` */

DROP TABLE IF EXISTS `hm_user_house`;

CREATE TABLE `hm_user_house` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `province` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '省份',
  `city` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '城市',
  `county` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '区县',
  `address` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '地址',
  `certificate` varchar(64) COLLATE utf8_unicode_ci NOT NULL COMMENT '产证编号',
  `area` decimal(8,2) NOT NULL COMMENT '产证面积',
  `year` varchar(8) COLLATE utf8_unicode_ci NOT NULL COMMENT '建成年份',
  `mortgage` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '按揭',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户房产信息';

/*Data for the table `hm_user_house` */

LOCK TABLES `hm_user_house` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_image` */

DROP TABLE IF EXISTS `hm_user_image`;

CREATE TABLE `hm_user_image` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `label` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标签',
  `image` longtext COLLATE utf8_unicode_ci NOT NULL COMMENT '图片',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户图片信息';

/*Data for the table `hm_user_image` */

LOCK TABLES `hm_user_image` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_job` */

DROP TABLE IF EXISTS `hm_user_job`;

CREATE TABLE `hm_user_job` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `name` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '公司名称',
  `properties` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '公司性质',
  `scale` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '公司规模',
  `registered_capital` decimal(16,4) DEFAULT NULL COMMENT '公司注册资金',
  `license` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司营业执照',
  `address` varchar(256) COLLATE utf8_unicode_ci NOT NULL COMMENT '公司地址',
  `phone` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '公司电话',
  `position` varchar(32) COLLATE utf8_unicode_ci NOT NULL COMMENT '职位',
  `annual_salary` decimal(16,4) NOT NULL COMMENT '年收入',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户工作信息';

/*Data for the table `hm_user_job` */

LOCK TABLES `hm_user_job` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_log` */

DROP TABLE IF EXISTS `hm_user_log`;

CREATE TABLE `hm_user_log` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `datetime` datetime NOT NULL COMMENT '日期',
  `type` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '类型',
  `remark` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户日志信息';

/*Data for the table `hm_user_log` */

LOCK TABLES `hm_user_log` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_user_properties` */

DROP TABLE IF EXISTS `hm_user_properties`;

CREATE TABLE `hm_user_properties` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `real_name` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '实际姓名',
  `gender` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '性别',
  `age` tinyint(4) DEFAULT NULL COMMENT '年龄',
  `married` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '婚姻状况',
  `children` tinyint(4) DEFAULT NULL COMMENT '子女数量',
  `id_number` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '证件号码',
  `id_type` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '证件类型',
  `is_mortgagor` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '是否借款人',
  `auth_name` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '实名认证',
  `auth_cellphone` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '手机认证',
  `auth_email` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '邮件认证',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户属性信息';


/*Table structure for table `hm_user_role` */

DROP TABLE IF EXISTS `hm_user_role`;

CREATE TABLE `hm_user_role` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `role` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '角色',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户角色关系信息';

/*Data for the table `hm_user_role` */

LOCK TABLES `hm_user_role` WRITE;

UNLOCK TABLES;

/*Table structure for table `hm_withdraw` */

DROP TABLE IF EXISTS `hm_withdraw`;

CREATE TABLE `hm_withdraw` (
  `id` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '编号',
  `user` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '用户',
  `bank_account` varchar(36) COLLATE utf8_unicode_ci NOT NULL COMMENT '银行帐号',
  `datetime` datetime NOT NULL COMMENT '日期',
  `amount` decimal(16,4) NOT NULL COMMENT '金额',
  `fee` decimal(16,4) NOT NULL COMMENT '手续费',
  `remark` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(2) COLLATE utf8_unicode_ci NOT NULL COMMENT '状态',
  `creator` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `updater` varchar(36) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `version` bigint(20) NOT NULL DEFAULT '0' COMMENT '版本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='提现信息';

/*Data for the table `hm_withdraw` */

LOCK TABLES `hm_withdraw` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
