package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.*;
import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.dal.meta.po.*;
import abc.ney.armee.appris.dal.meta.vo.StaffVo;
import abc.ney.armee.appris.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final BCryptPasswordEncoder encoder;
    @Resource
    CredentialsAuthoritiesMapper credentialsAuthoritiesMapper;
    @Resource
    CredentialsMapper credentialsMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    CredentialsStaffMapper credentialsStaffMapper;
//    @Resource
//    StaffVoMapper staffVoMapper;
    @Resource
    AuthorityMapper authorityMapper;
    @Resource
    StaffCredentialsDtoMapper staffCredentialsDtoMapper;

    public AdminServiceImpl() {
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean exist(String name) {
        return credentialsMapper.selectByNameLimit1(name).size() > 0;
    }

    /* REQUIRED : 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。*/
    @Override
    public Map<String, Boolean> insertAdmin(StaffCredentialsDto staffCredentialsDto) {
        return staffCredDtoHandler(staffCredentialsDto, AuthorityRole.ROLE_ADMIN);
    }
    @Override
    public Map<String, Boolean> insertDriver(StaffCredentialsDto staffCredentialsDto) {
        return staffCredDtoHandler(staffCredentialsDto, AuthorityRole.ROLE_COMMON_STAFF);
    }

    @Override
    public List<StaffCredentialsDto> queryAdmin() {
        List<StaffCredentialsDto> superAdminList =
                staffCredentialsDtoMapper.selectStaffCredentialsDtoByAuthority(AuthorityRole.ROLE_SUPER_ADMIN.getAuthority());
        List<StaffCredentialsDto> adminList =
                staffCredentialsDtoMapper.selectStaffCredentialsDtoByAuthority(AuthorityRole.ROLE_ADMIN.getAuthority());
        superAdminList.addAll(adminList);
        return superAdminList;
    }
    @Override
    public List<StaffCredentialsDto> queryDriver() {
        List<StaffCredentialsDto> driverList =
                staffCredentialsDtoMapper.selectStaffCredentialsDtoByAuthority(AuthorityRole.ROLE_COMMON_STAFF.getAuthority());
        return driverList;
    }

    @Override
    public Staff queryDriver(Long driverId) {
        return staffMapper.selectByPrimaryKey(driverId);
    }

    @Override
    public Staff queryDriverByIcCode(String icCode) {
        return staffMapper.selectByIcCode(icCode);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<Long, Boolean> updateAdmin(StaffCredentialsDto staffCredentialsDto) {
        Map<Long, Boolean> res = new HashMap<>();
        res.put(staffCredentialsDto.getStaffGid(), true);
        if (staffCredentialsDto.getStaffGid() != null) {
            CredentialsStaff credentialsStaff = credentialsStaffMapper.selectByStaffId(staffCredentialsDto.getStaffGid());
            Credentials credentials = credentialsMapper.selectByPrimaryKey(credentialsStaff.getCredentialId());
            CredentialsAuthorities credentialsAuthorities = credentialsAuthoritiesMapper.selectByCredentialId(credentials.getId());
            // 确认是管理员
            if (credentialsAuthorities.getAuthoritiesId().equals(AuthorityRole.ROLE_ADMIN.getId())) {
                Staff staff = staffCredentialsDto.toStaff();
                staffMapper.updateByPrimaryKeySelective(staff);
                // 设置密码，需要注意的是，密码需要进行加密操作
                if (staffCredentialsDto.getPassword() != null) {
                    credentials.setPassword(encoder.encode(staffCredentialsDto.getPassword()));
                }
                credentials.setName(staffCredentialsDto.getName());
                credentialsMapper.updateByPrimaryKeySelective(credentials);
                // 指定更新Authority
                if (staffCredentialsDto.getAuthority() != null) {
                    // 检查新角色是否合法
                    boolean legal = false;
                    for (AuthorityRole authorityRole : AuthorityRole.values()) {
                        // 更新角色
                        if (authorityRole.getAuthority().equals(staffCredentialsDto.getAuthority())) {
                            log.info(staffCredentialsDto.getAuthority());
                            credentialsAuthorities.setAuthoritiesId(authorityRole.getId());
                            credentialsAuthoritiesMapper.updateAuthorityByCredentialId(credentialsAuthorities);
                            legal = true;
                            break;
                        }
                    }
                    // 非法
                    if (!legal) {
                        res.put(staffCredentialsDto.getStaffGid(), false);
                        log.info("illegal argument");
                        throw new IllegalArgumentException("非法角色");
                    }
                } else {
                    // 不更新角色
                }
                log.info("管理员更新成功 : " + staffCredentialsDto.getName());
            } else {
                res.put(staffCredentialsDto.getStaffGid(), false);
                log.warn("非管理员");
            }
        } else {
            res.put(staffCredentialsDto.getStaffGid(), false);
        }
        return res;

    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<Long, Boolean> updateDriver(StaffCredentialsDto staffCredentialsDto) {
        Map<Long, Boolean> res = new HashMap<>();
        res.put(staffCredentialsDto.getStaffGid(), true);
        if (staffCredentialsDto.getStaffGid() != null) {
            CredentialsStaff credentialsStaff = credentialsStaffMapper.selectByStaffId(staffCredentialsDto.getStaffGid());
            Credentials credentials = credentialsMapper.selectByPrimaryKey(credentialsStaff.getCredentialId());
            CredentialsAuthorities credentialsAuthorities = credentialsAuthoritiesMapper.selectByCredentialId(credentials.getId());
            // 确认是管理员
            if (credentialsAuthorities.getAuthoritiesId().equals(AuthorityRole.ROLE_COMMON_STAFF.getId())) {
                Staff staff = staffCredentialsDto.toStaff();
                staffMapper.updateByPrimaryKeySelective(staff);
                // 设置密码，需要注意的是，密码需要进行加密操作
                if (staffCredentialsDto.getPassword() != null) {
                    credentials.setPassword(encoder.encode(staffCredentialsDto.getPassword()));
                }
                credentials.setName(staffCredentialsDto.getName());
                credentialsMapper.updateByPrimaryKeySelective(credentials);
                // 指定更新Authority
                if (staffCredentialsDto.getAuthority() != null) {
                    // 检查新角色是否合法
                    boolean legal = false;
                    for (AuthorityRole authorityRole : AuthorityRole.values()) {
                        // 更新角色
                        if (authorityRole.getAuthority().equals(staffCredentialsDto.getAuthority())) {
                            if (authorityRole.getId().equals(AuthorityRole.ROLE_SUPER_ADMIN.getId())) {
                                log.warn("司机无法直接设置为超级管理员");
                                throw new IllegalArgumentException("司机无法直接设置为超级管理员");
                            }
                            log.info(staffCredentialsDto.getAuthority());
                            credentialsAuthorities.setAuthoritiesId(authorityRole.getId());
                            credentialsAuthoritiesMapper.updateAuthorityByCredentialId(credentialsAuthorities);
                            legal = true;
                            break;
                        }
                    }
                    // 非法
                    if (!legal) {
                        res.put(staffCredentialsDto.getStaffGid(), false);
                        log.info("非法角色");
                        throw new IllegalArgumentException("非法角色");
                    }
                } else {
                    // 不更新角色
                }
                log.info("司机更新成功 : " + staffCredentialsDto.getName());
            } else {
                res.put(staffCredentialsDto.getStaffGid(), false);
                log.warn("非司机");
            }
        } else {
            res.put(staffCredentialsDto.getStaffGid(), false);
        }
        return res;

    }

    @Override
    public Map<Long, Boolean> deleteAdmins(List<Long> ids) {
        Map<Long, Boolean> res = new HashMap<>();
        for (Long id : ids) {
            Boolean r = deleteStaff(id, false);
            res.put(id, r);
        }
        return res;
    }

    @Override
    public Map<Long, Boolean> deleteDrivers(List<Long> ids) {
        Map<Long, Boolean> res = new HashMap<>();
        for (Long id : ids) {
            Boolean r = deleteStaff(id, true);
            res.put(id, r);
        }
        return res;
    }

    /**
     * 删除用户
     * @param staffId 用户id
     * @param driver 是否司机
     * @return 删除结果
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteStaff(Long staffId, boolean driver) {
        CredentialsStaff credentialsStaff = credentialsStaffMapper.selectByStaffId(staffId);
        log.info("staff : " + staffId);
        log.info("credentials staff : " + credentialsStaff.toString());
        CredentialsAuthorities credentialsAuthorities = credentialsAuthoritiesMapper.
                selectByCredentialId(credentialsStaff.getCredentialId());
        // 是否driver和实际无法对应的情况
        if ((driver && !credentialsAuthorities.getAuthoritiesId().equals(AuthorityRole.ROLE_COMMON_STAFF.getId())) ||
                (!driver && credentialsAuthorities.getAuthoritiesId().equals(AuthorityRole.ROLE_COMMON_STAFF.getId()))) {
            return false;
        }
        // staff <--> credentials_staff <--> credentials <--> credentials_authorities <--> authority
        // 删除前四个，最后一个共用不需要删除
        credentialsStaffMapper.deleteByStaffId(staffId);
        credentialsAuthoritiesMapper.deleteByCredentialId(credentialsAuthorities.getCredentialsId());
        staffMapper.deleteByPrimaryKey(staffId);
        credentialsMapper.deleteByPrimaryKey(credentialsAuthorities.getCredentialsId());
        return true;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public Map<String, Boolean> staffCredDtoHandler(StaffCredentialsDto staffCredentialsDto, AuthorityRole ar) {
        Map<String, Boolean> map = new HashMap<>();
        map.put(staffCredentialsDto.getName(), true);
        if (exist(staffCredentialsDto.getName())) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        // 对密码进行加密
        String encodePsw = encoder.encode(staffCredentialsDto.getPassword());
        Credentials credentials = new Credentials();
        credentials.setName(staffCredentialsDto.getName());
        credentials.setPassword(encodePsw);
        credentials.setEnabled(Credentials.ENABLE);credentials.setVersion(Credentials.VERSION);
        Staff staff = staffCredentialsDto.toStaff();
        // 如果是司机，必须有IC code
        if (ar.getId().equals(AuthorityRole.ROLE_COMMON_STAFF.getId()) && staff.getIcCode() == null) {
            throw new IllegalArgumentException("司机无IC卡信息");
        }
        if (credentialsMapper.insert(credentials) == ServiceConstant.MYSQL_INSERT_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            throw new IllegalArgumentException("credentials 插入失败");
        }
        if (staffMapper.insert(staff) == ServiceConstant.MYSQL_INSERT_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            throw new IllegalArgumentException("staff 插入失败");
        }
        if (credentialsAuthoritiesMapper.insert(new CredentialsAuthorities(
                credentials.getId(), ar.getId())) == ServiceConstant.MYSQL_INSERT_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            throw new IllegalArgumentException("credentials_authorities 插入失败");
        }
        if (credentialsStaffMapper.insert(new CredentialsStaff(credentials.getId(), staff.getGid()))
                == ServiceConstant.MYSQL_INSERT_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            throw new IllegalArgumentException("credentials_staff 插入失败");
        }
        log.info("插入鉴权信息 gid = " + credentials.getId());
        log.info("插入用户信息 gid = " + staff.getGid());
        return map;
    }
}
