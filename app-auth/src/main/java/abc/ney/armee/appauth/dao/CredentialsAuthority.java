package abc.ney.armee.appauth.dao;

import abc.ney.armee.appris.dal.meta.po.Authority;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录凭证，映射credentials表
 * @author Charles Song
 * @date 2020-6-25
 */
@ToString
public class CredentialsAuthority implements Serializable {
    private Long id;
    private Integer version;
    private String name;
    private String password;
    private boolean enabled;

    private List<Authority> authorities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(authorities!=null) {
            for (Authority authority : getAuthorities()) {
                SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
                authorities.add(grantedAuthority);
            }
        }
        return authorities;
    }
}
