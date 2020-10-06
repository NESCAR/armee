package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class Authority implements Serializable {
    private Long id;

    private String authority;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority == null ? null : authority.trim();
    }
}