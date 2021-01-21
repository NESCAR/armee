package abc.ney.armee.appris.dal.meta.vo;


import abc.ney.armee.appris.dal.meta.po.Device;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceVo implements Serializable {
    private static final long serialVersionUID = -92724186878799795L;
    private Long gid;

    private String imei;

    private String imsi;

    private Long driverGid;

    private Integer lockStatus;

    private Date lockStartTime;

    private Date lockEndTime;

    private String licensePlate;

    public DeviceVo(Device device) {
        this.gid = device.getGid();
        this.imei = device.getImei();
        this.imsi = device.getImsi();
        this.driverGid = device.getDriverGid();
        this.lockStatus = device.getLockStatus();
        this.lockStartTime = device.getLockStartTime();
        this.lockEndTime = device.getLockEndTime();
        this.licensePlate = device.getLicensePlate();
    }
}
