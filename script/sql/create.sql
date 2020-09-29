USE tms;

DROP TABLE IF EXISTS `authority`;
# 创建权限
CREATE TABLE `authority` (
 `id` BIGINT(20) NOT NULL COMMENT '权限id',
 `authority` VARCHAR(255) DEFAULT NULL COMMENT '权限',
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
BEGIN;
INSERT INTO `authority` VALUES (1, 'ROLE_SUPER_ADMIN');
INSERT INTO `authority` VALUES (2, 'ROLE_ADMIN');
INSERT INTO `authority` VALUES (3, 'ROLE_COMMON_STAFF');
COMMIT;
# SELECT * FROM authority;
# UPDATE authority SET authority = 'ROLE_SUPER_ADMIN' where id = 1;
# UPDATE authority SET authority = 'ROLE_ADMIN' where id = 2;
# UPDATE authority SET authority = 'ROLE_COMMON_STAFF' where id = 3;

DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials` (
   `id` BIGINT(20) NOT NULL COMMENT '凭证id',
   `enabled` TINYINT(1) NOT NULL COMMENT '是否可用',
   `name` VARCHAR(255) NOT NULL COMMENT '用户名',
   `password` VARCHAR(255) NOT NULL COMMENT '密码',
   `version` INT(11) DEFAULT NULL COMMENT '版本号',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
BEGIN;
# 创建oauth用户，密码是"user"经过加密的结果
INSERT INTO `credentials` VALUES (1, 1, 'super_admin', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
INSERT INTO `credentials` VALUES (2, 1, 'admin', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
INSERT INTO `credentials` VALUES (3, 1, 'common_staff', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
COMMIT;

DROP TABLE IF EXISTS `credentials_authorities`;
# credentials和authority的关联表
CREATE TABLE `credentials_authorities` (
   `credentials_id` BIGINT(20) NOT NULL COMMENT '凭证id',
   `authorities_id` BIGINT(20) NOT NULL COMMENT '权限id',
    CONSTRAINT credentials_id_cons FOREIGN KEY (credentials_id) REFERENCES credentials(id),
    CONSTRAINT authorities_id_cons FOREIGN KEY (authorities_id) REFERENCES authority(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
BEGIN;
INSERT INTO `credentials_authorities` VALUES (1, 1);
INSERT INTO `credentials_authorities` VALUES (2, 2);
INSERT INTO `credentials_authorities` VALUES (3, 3);
COMMIT;

DROP TABLE IF EXISTS `oauth_client_details`;
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
    `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/** 客户端信息 */
INSERT INTO oauth_client_details VALUES('user_client','project_api',
                                        '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2',
                                        'read,write', 'password,refresh_token', 'http://127.0.0.1',
                                        'ROLE_SUPER_ADMIN', 7200, 1800, NULL, 'true');
SELECT * FROM `oauth_client_details`;
UPDATE oauth_client_details SET authorities = 'ROLE_SUPER_ADMIN' where client_id = 'user_client';
#DELETE FROM oauth_client_details where client_id = 'user_client';

DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
      `token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token的值',
      `token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
      `authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,client_id,scope',
      `user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
      `client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
      `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
      `refresh_token` varchar(255) DEFAULT NULL COMMENT '加密的refresh_token的值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
   `token_id` varchar(255) DEFAULT NULL COMMENT '加密过的refresh_token的值',
   `token` longblob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据 ',
   `authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

USE tms;

DROP TABLE IF EXISTS `driver`;
CREATE TABLE `driver` (
  `gid` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '驾驶员唯一id',
  `user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
  `real_name` VARCHAR(255) NOT NULL COMMENT '用户真实姓名',
  `no` VARCHAR(255) NOT NULL COMMENT '工号',
  `psw` VARCHAR(255) NOT NULL COMMENT '用户密码',
  `tel` VARCHAR(32) DEFAULT NULL COMMENT '手机号',
  `role_id` BIGINT NOT NULL COMMENT '司机角色',
  `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
  `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更新时间',
  CONSTRAINT driver_role_id_cons FOREIGN KEY (role_id) REFERENCES role(gid),
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DESC `driver`;

DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `gid` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '挂车唯一id',
  `imei` VARCHAR(32) NOT NULL COMMENT 'IMEI',
  `imsi` VARCHAR(32) NOT NULL COMMENT 'IMSI',
  `psw` VARCHAR(32) NOT NULL COMMENT '设备密码，对应到一个挂车',
  `lock_status` INT(2) DEFAULT 1 COMMENT '上锁状态，1：上锁；0：未上锁',
  `lock_start_time` DATETIME(6) DEFAULT NULL COMMENT '上锁开始时间，只有lock_status为1时有效',
  `lock_end_time` DATETIME(6) DEFAULT NULL COMMENT '上锁结束时间，只有lock_status为1时有效',
  `license_plate` VARCHAR(16) DEFAULT NULL COMMENT '车牌号',
  `driver_gid` BIGINT UNSIGNED DEFAULT NULL  COMMENT '驾驶员唯一id',
  `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更改时间',
  CONSTRAINT driver_gid_cons FOREIGN KEY (driver_gid) REFERENCES driver(gid),
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DESC `device`;

DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
   `gid` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '管理员唯一id',
   `real_name` VARCHAR(255) NOT NULL COMMENT '真实姓名',
   `user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
   `psw` VARCHAR(255) NOT NULL COMMENT '密码',
   `role_id` BIGINT NOT NULL COMMENT '角色id',
   `no` VARCHAR(255) NOT NULL COMMENT '工号',
   `oauth_id` VARCHAR(255) NOT NULL COMMENT '密码',
   `tel` VARCHAR(32) NOT NULL COMMENT '手机号',
   `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
   `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
   `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更新时间',
   CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(gid),
   PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DESC `manager`;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `gid` BIGINT UNSIGNED COMMENT '用户角色id',
    `info` VARCHAR(255) NOT NULL COMMENT '角色说明',
    `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
    `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更改时间',
    PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `role` (gid, info, gmt_create, gmt_update)
VALUES (0, '超级管理员', NOW(), NOW());
INSERT INTO `role` (gid, info, gmt_create, gmt_update)
VALUES (1, '普通管理员', NOW(), NOW());
INSERT INTO `role` (gid, info, gmt_create, gmt_update)
VALUES (2, '普通员工', NOW(), NOW());
SELECT * FROM `role`;

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `gid` BIGINT UNSIGNED COMMENT '权限id',
  `url` VARCHAR(128) NOT NULL COMMENT '权限url模式',
  `info` VARCHAR(255) NOT NULL COMMENT '权限信息',
  `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
  `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `permission` (gid, url, info, gmt_create, gmt_update)
VALUES (0, 'carInfo/**', '汽车信息', NOW(), NOW());
INSERT INTO `permission` (gid, url, info, gmt_create, gmt_update)
VALUES (1, 'carControl/**', '汽车控制', NOW(), NOW());
INSERT INTO `permission` (gid, url, info, gmt_create, gmt_update)
VALUES (2, 'userManage/manager/**', '普通管理员管理（超级管理员拥有的权限）', NOW(), NOW());
INSERT INTO `permission` (gid, url, info, gmt_create, gmt_update)
VALUES (3, 'userManage/commonStaff/**', '普通员工管理（超级管理员和普通管理员均拥有的权限）', NOW(), NOW());

DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
   `role_id` BIGINT UNSIGNED COMMENT '角色id，对应到role表到gid',
   `permission_id` BIGINT UNSIGNED COMMENT '权限id',
   CONSTRAINT role_cons FOREIGN KEY (role_id) REFERENCES role(gid),
   CONSTRAINT permission_cons FOREIGN KEY (permission_id) REFERENCES permission(gid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `role_permission` (role_id, permission_id)
VALUES (0, 0), (0, 1), (0, 2), (0, 3);
INSERT INTO `role_permission` (role_id, permission_id)
VALUES (1, 0), (1, 2), (1, 3);
SELECT * FROM `role_permission`;

USE tms;
SHOW tables;

select * from authority;