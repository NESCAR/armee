package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class CredentialsAuthorities implements Serializable {
    private Long credentialsId;

    private Long authoritiesId;

    private static final long serialVersionUID = 1L;

    public CredentialsAuthorities(){
        super();
    }
    public CredentialsAuthorities(Long cid, Long aid) {
        this.credentialsId = cid;
        this.authoritiesId = aid;
    }

    public Long getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(Long credentialsId) {
        this.credentialsId = credentialsId;
    }

    public Long getAuthoritiesId() {
        return authoritiesId;
    }

    public void setAuthoritiesId(Long authoritiesId) {
        this.authoritiesId = authoritiesId;
    }
}