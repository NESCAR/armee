DROP DATABASE tms;
CREATE DATABASE tms;
USE tms;

DROP TABLE IF EXISTS `authority`;
# 创建权限
CREATE TABLE `authority` (
                             `id` BIGINT(20) NOT NULL COMMENT '权限id',
                             `authority` VARCHAR(255) NOT NULL COMMENT '权限',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
BEGIN;
INSERT INTO `authority` VALUES (1, 'ROLE_SUPER_ADMIN');
INSERT INTO `authority` VALUES (2, 'ROLE_ADMIN');
INSERT INTO `authority` VALUES (3, 'ROLE_COMMON_STAFF');
COMMIT;
# DELETE FROM `authority` WHERE id = 4;
# SELECT * FROM authority;
# UPDATE authority SET authority = 'ROLE_SUPER_ADMIN' where id = 1;
# UPDATE authority SET authority = 'ROLE_ADMIN' where id = 2;
# UPDATE authority SET authority = 'ROLE_COMMON_STAFF' where id = 3;

DROP TABLE IF EXISTS `credentials`;
CREATE TABLE `credentials` (
                               `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '凭证id',
                               `enabled` TINYINT(1) NOT NULL COMMENT '是否可用',
                               `name` VARCHAR(255) NOT NULL UNIQUE COMMENT '用户名',
                               `password` VARCHAR(255) NOT NULL COMMENT '密码',
                               `version` INT(11) DEFAULT NULL COMMENT '版本号',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# ALTER TABLE `credentials` ADD UNIQUE(`name`);
# ALTER TABLE `credentials` MODIFY COLUMN `id` BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '凭证id';
BEGIN;
# 创建oauth用户，密码是"user"经过加密的结果
INSERT INTO `credentials` VALUES (1, 1, 'super_admin', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
INSERT INTO `credentials` VALUES (2, 1, 'admin', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
INSERT INTO `credentials` VALUES (3, 1, 'common_staff', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 0);
COMMIT;
SELECT * FROM `credentials`;

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
                                        `autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作',
                                        PRIMARY KEY (client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/** 客户端信息 */
# user -> $2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG
INSERT INTO oauth_client_details VALUES('user_client','project_api',
                                        '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2',
                                        'read,write', 'password,refresh_token', 'http://127.0.0.1',
                                        'ROLE_SUPER_ADMIN', 7200, 1800, NULL, 'true');
# web_application_123456 -> $2a$10$.XShZVO1r92kcA4jaW66A.1eiO/G312rgXmZivQFAwKkn80ZlUT9i
INSERT INTO oauth_client_details VALUES('web_application','project_api',
                                        '$2a$10$.XShZVO1r92kcA4jaW66A.1eiO/G312rgXmZivQFAwKkn80ZlUT9i',
                                        'read,write', 'password,refresh_token', 'http://127.0.0.1',
                                        'ROLE_SUPER_ADMIN', 7200, 1800, NULL, 'true');
SELECT * FROM `oauth_client_details`;
# UPDATE oauth_client_details SET authorities = 'ROLE_SUPER_ADMIN' where client_id = 'user_client';
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

DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
                         `gid` BIGINT(20) UNSIGNED AUTO_INCREMENT COMMENT '唯一id',
                         `real_name` VARCHAR(255) NOT NULL COMMENT '真实姓名',
                         `no` VARCHAR(255) NOT NULL COMMENT '工号',
                         `position` VARCHAR(255) DEFAULT NULL COMMENT '职位',
                         `tel_area` VARCHAR(16) DEFAULT '+86' COMMENT '手机号区号',
                         `tel` VARCHAR(32) NOT NULL COMMENT '手机号',
                         `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
                         `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
                         `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更新时间',
                         PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
# ALTER TABLE `staff` ADD `tel_area` VARCHAR(16) DEFAULT '86' COMMENT '手机号区号';
ALTER TABLE `staff` CHANGE `tel_area` `tel_area` VARCHAR(16) DEFAULT '86' COMMENT '手机号区号';
DESC `staff`;
SELECT * FROM `staff`;
INSERT INTO `staff` (real_name, no, position, tel_area, tel, email, gmt_create, gmt_update)
VALUES ('宋超超', 'armee_0001', '超级管理员', '86','15658007838', 'sonechaochao@gmail.com', NOW(), NOW());
# DELETE FROM `staff`;

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
                          `driver_gid` BIGINT(20) UNSIGNED DEFAULT NULL COMMENT '驾驶员唯一id',
                          `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
                          `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更改时间',
                          CONSTRAINT driver_gid_cons FOREIGN KEY (driver_gid) REFERENCES staff(gid),
                          PRIMARY KEY (`gid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DESC `device`;

DROP TABLE IF EXISTS `credentials_staff`;
CREATE TABLE `credentials_staff` (
                                     `credential_id` BIGINT(20) COMMENT '客户端id',
                                     `staff_id` BIGINT(20) UNSIGNED COMMENT '员工id',
                                     CONSTRAINT credential_id_id_cons FOREIGN KEY (credential_id) REFERENCES credentials(id),
                                     CONSTRAINT staff_id_cons FOREIGN KEY (staff_id) REFERENCES staff(gid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `credentials_staff` (credential_id, staff_id) VALUES (1, 3);
SELECT * FROM credentials_staff;
DESC credentials_staff;


use tms;
select * from authority;

select f.id credentialId, f.enabled credentialEnable, f.name credentialName, g.gid staffGid, g.real_name staffRealName, g.no staffNo, g.position staffPosition, g.tel_area staffTelArea, g.tel staffTel, g.email staffEmail from
    ((select d.id, d.enabled, d.name, e.staff_id from
        ((select c.* from
            (select a.credentials_id from credentials_authorities a where authorities_id = 1) b
                left join credentials c on b.credentials_id = c.id) d)
            left join credentials_staff e on d.id = e.credential_id) f)
        left join staff g on g.gid = f.staff_id;

select count(*) from authority left join credentials on 1 = 1;

select * from credentials;