package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class CredentialsStaff implements Serializable {
    private Long credentialId;

    private Long staffId;

    private static final long serialVersionUID = 1L;

    public CredentialsStaff() {
        super();
    }
    public CredentialsStaff(Long cid, Long sid) {
        this.credentialId = cid;
        this.staffId = sid;
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", credentialId=").append(credentialId);
        sb.append(", staffId=").append(staffId);
        sb.append("]");
        return sb.toString();
    }
}