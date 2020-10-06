package abc.ney.armee.appauth.dao;

import abc.ney.armee.appris.dal.meta.po.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 * @author Charles Song
 * @date 2020-6-25
 */
@Repository
public class CredentialsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户名获得凭证信息
     *
     * @param username
     * @return
     */
    public CredentialsAuthority findByName(String username) {
        String sql = "select * from credentials where name = ?";
        Map<String, Object> credentialsMap = null;
        try {
            credentialsMap = jdbcTemplate.queryForMap(sql, username);
        } catch (Exception e){
            return null;
        }

        CredentialsAuthority credentialsAuthority = new CredentialsAuthority();

        Long id = (Long)credentialsMap.get("id");
        Integer version = (Integer)credentialsMap.get("version");
        String name = (String)credentialsMap.get("name");
        String password = (String)credentialsMap.get("password");
        boolean enabled = (boolean)credentialsMap.get("enabled");

        credentialsAuthority.setId(id);
        credentialsAuthority.setVersion(version);
        credentialsAuthority.setName(name);
        credentialsAuthority.setPassword(password);
        credentialsAuthority.setEnabled(enabled);
        credentialsAuthority.setAuthorities(getAuthorities(id));

//        System.out.println(credentials.toString());
        return credentialsAuthority;
    }

    /**
     * 获得角色
     * @param credentialsId
     * @return
     */
    public List<Authority> getAuthorities(Long credentialsId){
        String sql = "select a.* from authority a left join credentials_authorities ca on a.id = ca.authorities_id where ca.credentials_id = ?";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, credentialsId);
        List<Authority> authorities = new ArrayList<>();
        for(Map<String, Object> item:mapList){
            Long id = (Long)item.get("id");
            String authority = (String)item.get("authority");
            Authority value = new Authority();
            value.setId(id);
            value.setAuthority(authority);
            authorities.add(value);
        }
        return authorities;
    }
}
