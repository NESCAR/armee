package abc.ney.armee.appris.dal.meta.po;

/**
 * 权限级别
 * @author neyzoter
 */
public enum AuthorityRole {
    ROLE_SUPER_ADMIN(1L, "ROLE_SUPER_ADMIN"),
    ROLE_ADMIN(2L, "ROLE_ADMIN"),
    ROLE_COMMON_STAFF(3L, "ROLE_COMMON_STAFF");
    private Long id;
    private String authority;

    AuthorityRole(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long i) {
        this.id = i;
    }
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String a) {
        this.authority = a;
    }
}
