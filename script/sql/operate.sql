/**添加一个用户**/
INSERT INTO `staff` (real_name, no, position, tel_area, tel, email, gmt_create, gmt_update)
VALUES ('宋超超', 'armee_0001', '超级管理员', '86','15109890989', 'sonechaochao@gmail.com', NOW(), NOW());
INSERT INTO `credentials` (enabled, name, password, version) VALUES (1, 'super_admin', '$2a$10$BurTWIy5NTF9GJJH4magz.9Bd4bBurWYG8tmXxeQh1vs7r/wnCFG2', 1);
INSERT INTO `credentials_staff` (credential_id, staff_id) VALUES (1, 1);
INSERT INTO `credentials_authorities` (credentials_id, authorities_id) VALUES (1, 1);

/*清空表*/
SET foreign_key_checks = 0;
truncate table credentials_authorities;
truncate table credentials_staff;
truncate table device;
truncate table staff;
truncate table credentials;
SET foreign_key_checks = 1;



select f.id credentialId, f.enabled credentialEnable, f.name credentialName, g.gid staffGid, g.real_name staffRealName, g.no staffNo, g.position staffPosition, g.tel_area staffTelArea, g.tel staffTel, g.email staffEmail from
    ((select d.id, d.enabled, d.name, e.staff_id from
        ((select c.* from
            (select a.credentials_id from credentials_authorities a where authorities_id = 1) b
                left join credentials c on b.credentials_id = c.id) d)
            left join credentials_staff e on d.id = e.credential_id) f)
        left join staff g on g.gid = f.staff_id;