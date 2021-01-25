-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: tms
-- ------------------------------------------------------
-- Server version	5.7.31-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL COMMENT '权限id',
  `authority` varchar(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ROLE_SUPER_ADMIN'),(2,'ROLE_ADMIN'),(3,'ROLE_COMMON_STAFF');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials`
--

DROP TABLE IF EXISTS `credentials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credentials` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '凭证id',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用',
  `name` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `version` int(11) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials`
--

LOCK TABLES `credentials` WRITE;
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` VALUES (1,1,'super_admin','$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2',1);
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials_authorities`
--

DROP TABLE IF EXISTS `credentials_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credentials_authorities` (
  `credentials_id` bigint(20) NOT NULL COMMENT '凭证id',
  `authorities_id` bigint(20) NOT NULL COMMENT '权限id',
  KEY `credentials_id_cons` (`credentials_id`),
  KEY `authorities_id_cons` (`authorities_id`),
  CONSTRAINT `authorities_id_cons` FOREIGN KEY (`authorities_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `credentials_id_cons` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials_authorities`
--

LOCK TABLES `credentials_authorities` WRITE;
/*!40000 ALTER TABLE `credentials_authorities` DISABLE KEYS */;
INSERT INTO `credentials_authorities` VALUES (1,1);
/*!40000 ALTER TABLE `credentials_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credentials_staff`
--

DROP TABLE IF EXISTS `credentials_staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credentials_staff` (
  `credential_id` bigint(20) DEFAULT NULL COMMENT '客户端id',
  `staff_id` bigint(20) unsigned DEFAULT NULL COMMENT '员工id',
  KEY `credential_id_id_cons` (`credential_id`),
  KEY `staff_id_cons` (`staff_id`),
  CONSTRAINT `credential_id_id_cons` FOREIGN KEY (`credential_id`) REFERENCES `credentials` (`id`),
  CONSTRAINT `staff_id_cons` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credentials_staff`
--

LOCK TABLES `credentials_staff` WRITE;
/*!40000 ALTER TABLE `credentials_staff` DISABLE KEYS */;
INSERT INTO `credentials_staff` VALUES (1,1);
/*!40000 ALTER TABLE `credentials_staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device` (
  `gid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '挂车唯一id',
  `imei` varchar(32) NOT NULL COMMENT 'IMEI',
  `imsi` varchar(32) NOT NULL COMMENT 'IMSI',
  `psw` varchar(255) DEFAULT NULL COMMENT '设备密码/或者是IC信息，对应到一个挂车',
  `lock_status` int(2) DEFAULT '1' COMMENT '上锁状态，1：上锁；0：未上锁',
  `lock_start_time` datetime(6) DEFAULT NULL COMMENT '上锁开始时间，只有lock_status为1时有效',
  `lock_end_time` datetime(6) DEFAULT NULL COMMENT '上锁结束时间，只有lock_status为1时有效',
  `license_plate` varchar(16) DEFAULT NULL COMMENT '车牌号',
  `driver_gid` bigint(20) unsigned DEFAULT NULL COMMENT '驾驶员唯一id',
  `gmt_create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime(6) DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`gid`),
  UNIQUE KEY `imei` (`imei`),
  UNIQUE KEY `imsi` (`imsi`),
  KEY `driver_gid_cons` (`driver_gid`),
  CONSTRAINT `driver_gid_cons` FOREIGN KEY (`driver_gid`) REFERENCES `staff` (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `device_lock_record`
--

DROP TABLE IF EXISTS `device_lock_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_lock_record` (
  `gid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `device_id` bigint(20) unsigned DEFAULT NULL COMMENT '汽车唯一ID',
  `change_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上锁/解锁时间',
  `lock_status` int(2) NOT NULL COMMENT '上锁状态，1：上锁；0：未上锁',
  PRIMARY KEY (`gid`),
  KEY `device_id_cons` (`device_id`),
  CONSTRAINT `device_id_cons` FOREIGN KEY (`device_id`) REFERENCES `device` (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device_lock_record`
--

LOCK TABLES `device_lock_record` WRITE;
/*!40000 ALTER TABLE `device_lock_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `device_lock_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lock_auth_info`
--

DROP TABLE IF EXISTS `lock_auth_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lock_auth_info` (
  `gid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '鉴权信息',
  `device_id` bigint(20) unsigned NOT NULL COMMENT '挂车唯一ID',
  `driver_id` bigint(20) unsigned NOT NULL COMMENT '司机唯一ID',
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '授权开始时间',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '授权结束时间',
  `downed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`gid`),
  KEY `lock_auth_info_device_cons` (`device_id`),
  KEY `lock_auth_info_staff_cons` (`driver_id`),
  CONSTRAINT `lock_auth_info_device_cons` FOREIGN KEY (`device_id`) REFERENCES `device` (`gid`),
  CONSTRAINT `lock_auth_info_staff_cons` FOREIGN KEY (`driver_id`) REFERENCES `staff` (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lock_auth_info`
--

LOCK TABLES `lock_auth_info` WRITE;
/*!40000 ALTER TABLE `lock_auth_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `lock_auth_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token的值',
  `token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
  `authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,client_id,scope',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
  `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
  `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
  `refresh_token` varchar(255) DEFAULT NULL COMMENT '加密的refresh_token的值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_access_token`
--

LOCK TABLES `oauth_access_token` WRITE;
/*!40000 ALTER TABLE `oauth_access_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_access_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_client_details`
--

DROP TABLE IF EXISTS `oauth_client_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '客户端密匙',
  `scope` varchar(255) DEFAULT NULL COMMENT '客户端申请的权限范围',
  `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '客户端支持的grant_type',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '重定向URI',
  `authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
  `additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_client_details`
--

LOCK TABLES `oauth_client_details` WRITE;
/*!40000 ALTER TABLE `oauth_client_details` DISABLE KEYS */;
INSERT INTO `oauth_client_details` VALUES ('user_client','project_api','$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2','read,write','password,refresh_token','http://127.0.0.1','ROLE_SUPER_ADMIN',7200,1800,NULL,'true'),('web_application','project_api','$2a$10$.XShZVO1r92kcA4jaW66A.1eiO/G312rgXmZivQFAwKkn80ZlUT9i','read,write','password,refresh_token','http://127.0.0.1','ROLE_SUPER_ADMIN',7200,1800,NULL,'true');
/*!40000 ALTER TABLE `oauth_client_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(255) DEFAULT NULL COMMENT '加密过的refresh_token的值',
  `token` longblob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据 ',
  `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth_refresh_token`
--

LOCK TABLES `oauth_refresh_token` WRITE;
/*!40000 ALTER TABLE `oauth_refresh_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth_refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `gid` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `real_name` varchar(255) NOT NULL COMMENT '真实姓名',
  `no` varchar(255) NOT NULL COMMENT '工号',
  `position` varchar(255) DEFAULT NULL COMMENT '职位',
  `tel` varchar(32) NOT NULL COMMENT '手机号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `gmt_create` datetime(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_update` datetime(6) DEFAULT NULL COMMENT '更新时间',
  `tel_area` varchar(16) DEFAULT '86' COMMENT '手机号区号',
  `ic_code` varchar(255) DEFAULT NULL COMMENT 'ic卡信息(如果有的话)',
  PRIMARY KEY (`gid`),
  UNIQUE KEY `ic_code` (`ic_code`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'超级管理员','000000','超级管理员','13100000000','xxxx@example.com',now(),now(),'86',NULL);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-23 15:46:50
