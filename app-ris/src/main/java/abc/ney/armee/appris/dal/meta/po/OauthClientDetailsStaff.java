package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class OauthClientDetailsStaff implements Serializable {
    private Long credentialId;

    private Long staffId;

    private static final long serialVersionUID = 1L;

    public OauthClientDetailsStaff(Long cid, Long sid) {
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
}