package abc.ney.armee.appris.dal.meta.po;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;

import java.io.Serializable;

public class Credentials implements Serializable {

    private Long id;

    private Boolean enabled;

    private String name;

    private String password;

    private Integer version;

    private static final long serialVersionUID = 1L;

    public static final int VERSION = 1;
    public static final Boolean ENABLE = true;
    public Credentials() {

    }
    public Credentials(StaffCredentialsDto staffCredentialsDto) {
        enabled = true;
        name = staffCredentialsDto.getName();
        password = staffCredentialsDto.getPassword();
        version = PoConstant.CREDENTIALS_VERSION;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", enabled=").append(enabled);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append(", version=").append(version);
        sb.append("]");
        return sb.toString();
    }
}