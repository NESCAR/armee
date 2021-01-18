package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {
    private Long gid;

    private String imei;

    private String imsi;

    private String psw;

    private Integer lockStatus;

    private Date lockStartTime;

    private Date lockEndTime;

    private String licensePlate;

    private Long driverGid;

    private Date gmtCreate;

    private Date gmtUpdate;

    private static final long serialVersionUID = 1L;

    public static final int LOCK_STATUS = 1;
    public static final int UN_LOCK_STATUS = 0;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw == null ? null : psw.trim();
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Date getLockStartTime() {
        return lockStartTime;
    }

    public void setLockStartTime(Date lockStartTime) {
        this.lockStartTime = lockStartTime;
    }

    public Date getLockEndTime() {
        return lockEndTime;
    }

    public void setLockEndTime(Date lockEndTime) {
        this.lockEndTime = lockEndTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate == null ? null : licensePlate.trim();
    }

    public Long getDriverGid() {
        return driverGid;
    }

    public void setDriverGid(Long driverGid) {
        this.driverGid = driverGid;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gid=").append(gid);
        sb.append(", imei=").append(imei);
        sb.append(", imsi=").append(imsi);
        sb.append(", psw=").append(psw);
        sb.append(", lockStatus=").append(lockStatus);
        sb.append(", lockStartTime=").append(lockStartTime);
        sb.append(", lockEndTime=").append(lockEndTime);
        sb.append(", licensePlate=").append(licensePlate);
        sb.append(", driverGid=").append(driverGid);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append("]");
        return sb.toString();
    }
}