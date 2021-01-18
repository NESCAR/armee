package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;
import java.util.Date;

public class DeviceLockRecord implements Serializable {
    private Long gid;

    private Long deviceId;

    private Date changeTime;

    private Integer lockStatus;

    private static final long serialVersionUID = 1L;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gid=").append(gid);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", changeTime=").append(changeTime);
        sb.append(", lockStatus=").append(lockStatus);
        sb.append("]");
        return sb.toString();
    }
}