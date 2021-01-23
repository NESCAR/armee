package abc.ney.armee.appris.service.msg.handler;

import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.DeviceLockRecord;
import abc.ney.armee.appris.service.CarService;
import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.msg.req.LockStatusUploadRequestMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * LockStatusUploadRequestMsgBody处理
 * @author neyzoter
 */
@Component("lockStatusUploadRequestHandler")
@Slf4j
public class LockStatusUploadRequestHandler implements UpMsgHandler {
    CarService carService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void process(MsgKey key, Object value) {
        String imei = key.getTerminalId();
        LockStatusUploadRequestMsgBody lockStatusUploadRequest = (LockStatusUploadRequestMsgBody) value;
        // step1. 更新device状态
        Device device = carService.queryDeviceByImei(imei);
        device.setLockStatus((int)lockStatusUploadRequest.getLockStatus());
        carService.updateDeviceByGid(device);
        log.debug("[To DB]  >>>>>>  " + device.toString());
        // step2. 增加device lock record
        DeviceLockRecord deviceLockRecord = new DeviceLockRecord();
        deviceLockRecord.setDeviceId(device.getGid());
        deviceLockRecord.setChangeTime(TimeConverter.bcdByte2Timestamp(lockStatusUploadRequest.getLockStatusTime()));
        deviceLockRecord.setLockStatus((int)lockStatusUploadRequest.getLockStatus());
        carService.addLockRecord(deviceLockRecord);
        log.debug("[To DB]  >>>>>>  " + deviceLockRecord.toString());
    }

    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}
