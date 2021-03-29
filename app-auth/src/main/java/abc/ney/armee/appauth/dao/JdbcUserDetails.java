package abc.ney.armee.appauth.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 用户的JDB服务
 * @author Charles Song
 * @date 2020-6-25
 */
@Service("userDetailsService")
public class JdbcUserDetails implements UserDetailsService {

    /**
     * 自定义用户查询操作
     */
    @Autowired
    private CredentialsDao credentialsDao;
    // jdbc操作oauth_client_details
//    JdbcClientDetailsService jdbcClientDetailsService;
    /**
     * 根据用户名来获取User
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户名未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CredentialsAuthority credentialsAuthority = credentialsDao.findByName(username);
        if (credentialsAuthority == null) {
            throw new UsernameNotFoundException("User '" + username + "' can not be found");
        }
        System.out.println(credentialsAuthority.toString());
        System.out.println(credentialsAuthority.getGrantedAuthorities().get(0).getAuthority());
        //此处授权也可以用credentials.getAuthorities(),但是在资源服务器解析的时候要引入自定义的com.oauth2.authorization.userdetails.Authority类否则会报找不到类对象而解析失败
        return new User(credentialsAuthority.getName(), credentialsAuthority.getPassword(), credentialsAuthority.isEnabled(), true, true, true, credentialsAuthority.getGrantedAuthorities());
    }

}
