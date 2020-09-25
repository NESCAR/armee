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
);
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
);
DESC `device`;

DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
   `gid` BIGINT UNSIGNED AUTO_INCREMENT COMMENT '管理员唯一id',
   `user_name` VARCHAR(255) NOT NULL COMMENT '用户名',
   `real_name` VARCHAR(255) NOT NULL COMMENT '真实姓名',
   `role_id` BIGINT NOT NULL COMMENT '角色id',
   `no` VARCHAR(255) NOT NULL COMMENT '工号',
   `psw` VARCHAR(255) NOT NULL COMMENT '密码',
   `tel` VARCHAR(32) NOT NULL COMMENT '手机号',
   `email` VARCHAR(128) DEFAULT NULL COMMENT '邮箱',
   `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
   `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更新时间',
   CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(gid),
   PRIMARY KEY (`gid`)
);
DESC `manager`;

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `gid` BIGINT UNSIGNED COMMENT '用户角色id',
    `info` VARCHAR(255) NOT NULL COMMENT '角色说明',
    `gmt_create` DATETIME(6) DEFAULT NULL COMMENT '创建时间',
    `gmt_update` DATETIME(6) DEFAULT NULL COMMENT '更改时间',
    PRIMARY KEY (`gid`)
);
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
);
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
);
INSERT INTO `role_permission` (role_id, permission_id)
VALUES (0, 0), (0, 1), (0, 2), (0, 3);
INSERT INTO `role_permission` (role_id, permission_id)
VALUES (1, 0), (1, 2), (1, 3);
SELECT * FROM `role_permission`;