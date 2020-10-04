package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.*;
import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.dal.meta.po.*;
import abc.ney.armee.appris.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
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
    OauthClientDetailsStaffMapper oauthClientDetailsStaffMapper;

    public AdminServiceImpl() {
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean exist(String name) {
        return credentialsMapper.selectByName(name).size() > 0;
    }

    /* REQUIRED : 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。*/
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String, Boolean> insertAdmin(StaffCredentialsDto staffCredentialsDto) {
        return staffCredDtoHandler(staffCredentialsDto, AuthorityRole.ROLE_ADMIN);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String, Boolean> insertDriver(StaffCredentialsDto staffCredentialsDto) {
        return staffCredDtoHandler(staffCredentialsDto, AuthorityRole.ROLE_COMMON_STAFF);
    }

    private Map<String, Boolean> staffCredDtoHandler(StaffCredentialsDto staffCredentialsDto, AuthorityRole ar) {
        Map<String, Boolean> map = new HashMap<>();
        map.put(staffCredentialsDto.getName(), true);
        if (exist(staffCredentialsDto.getName())) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        // 对密码进行加密
        String encodePsw = encoder.encode(staffCredentialsDto.getPassword());
        staffCredentialsDto.setPassword(encodePsw);
        Credentials credentials = new Credentials(staffCredentialsDto);
        Staff staff = new Staff(staffCredentialsDto);
        if (credentialsMapper.insert(credentials) == ServiceConstant.MYSQL_OP_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        if (staffMapper.insert(staff) == ServiceConstant.MYSQL_OP_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        if (credentialsAuthoritiesMapper.insert(new CredentialsAuthorities(
                credentials.getId(), ar.getId())) == ServiceConstant.MYSQL_OP_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        if (oauthClientDetailsStaffMapper.insert(new OauthClientDetailsStaff(
                credentials.getId(), staff.getGid())) == ServiceConstant.MYSQL_OP_ERR_RTN) {
            map.put(staffCredentialsDto.getName(), false);
            return map;
        }
        log.info("Credentials : " + credentials.getId());
        log.info("Staff : " + staff.getGid());
        return map;
    }
}
