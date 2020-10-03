package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.AuthorityMapper;
import abc.ney.armee.appris.dal.meta.po.Authority;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthService {
    @Resource
    AuthorityMapper authorityMapper;

    public AuthService() {

    }
    public void test() {
        System.out.println("Running Test!");
        Authority authority = new Authority();
        authority.setId(4L);
        authority.setAuthority("ROLE_ADMIN_PLUS");
        authorityMapper.insert(authority);
//        authorityMapper.updateByPrimaryKey(authority);
//        authority = authorityMapper.selectByPrimaryKey(1L);
//        System.out.println(authority.toString());
    }
}
