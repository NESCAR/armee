package abc.ney.armee.appris.service.msg.handler;

import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.appris.service.CarService;
import abc.ney.armee.appris.service.LockInfoManService;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.msg.req.AuthUpdateSuccessRequestMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Component
@Slf4j
public class LockInfoSettingsHandler implements UpMsgHandler {
    CarService carService;
    LockInfoManService lockInfoManService;
    AdminService adminService;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void process(MsgKey key, Object value) {
        AuthUpdateSuccessRequestMsgBody authUpdateSuccessRequestMsgBody = (AuthUpdateSuccessRequestMsgBody) value;
        // step1. 找到ic卡对应司机
        String icId = authUpdateSuccessRequestMsgBody.getDriverId();
        Staff driver = adminService.queryDriverByIcCode(icId);
        Long driverId = driver.getGid();
        // step2. 更新设备（汽车）的状态:
        //        开始、结束时间；绑定司机信息gid
        Device device = new Device();
        device.setGmtUpdate(new Timestamp(System.currentTimeMillis()));
        device.setImei(key.getTerminalId());device.setLockStartTime(Timestamp.valueOf(authUpdateSuccessRequestMsgBody.getLockTimeStart()));
        device.setLockEndTime(Timestamp.valueOf(authUpdateSuccessRequestMsgBody.getLockTimeEnd()));device.setDriverGid(driverId);
        carService.updateDeviceByImei(device);
        // step3. 更新lock_auth_info的downed
        //        downed
        LockAuthInfo lockAuthInfo = new LockAuthInfo();
        lockAuthInfo.setDeviceId(device.getGid());lockAuthInfo.setStartTime(device.getLockStartTime());
        lockAuthInfo.setEndTime(device.getLockEndTime());
        // todo 打印出来，为什么是null？
        List<LockAuthInfo> lockAuthInfoList = lockInfoManService.findLockInfoByDidStEt(lockAuthInfo);
        if (lockAuthInfoList.size() > 1) {
            throw new IllegalArgumentException("数据库查询到过多记录");
        } else if (lockAuthInfoList.size() == 0) {
            throw new IllegalArgumentException("数据库未查询到记录");
        }
        lockAuthInfo.setGid(lockAuthInfoList.get(0).getGid());
        lockAuthInfo.setDowned(true);
        lockInfoManService.updateLockInfoByPrimaryKey(lockAuthInfo);
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
    @Autowired
    public void setLockInfoManService(LockInfoManService lockInfoManService) {
        this.lockInfoManService = lockInfoManService;
    }
    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
