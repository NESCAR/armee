package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.appris.service.CarService;
import abc.ney.armee.appris.service.LockInfoManService;
import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.command.LockInfoSettingsMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@Slf4j
public class LockAuthDownTask implements Runnable {
    LockInfoManService lockInfoManService;
    CommandKafkaProducer commandKafkaProducer;
    AdminService adminService;
    CarService carService;
    @Override
    public void run() {
        List<LockAuthInfo> undownedLockAuthInfo = lockInfoManService.findDownloadInfo();
        LockInfoSettingsMsgBody lockInfoSettingsMsgBody = new LockInfoSettingsMsgBody();
        for (LockAuthInfo lai : undownedLockAuthInfo) {
            Staff driver = adminService.queryDriver(lai.getDriverId());
            Device car = carService.queryDeviceByGid(lai.getDeviceId());
            if (driver.getIcCode() == null) {
                log.warn("司机无IC卡信息");
            }
            lockInfoSettingsMsgBody.setICID(driver.getIcCode());lockInfoSettingsMsgBody.setCarID(car.getImei());
            lockInfoSettingsMsgBody.setLockTimeStart(TimeConverter.timestamp2BcdString(
                    new Timestamp(lai.getStartTime().getTime())));
            lockInfoSettingsMsgBody.setLockTimeEnd(TimeConverter.timestamp2BcdString(
                    new Timestamp(lai.getEndTime().getTime())));
            KafkaMsgKey key = new KafkaMsgKey(car.getImei(), Jt808MsgType.CMD_LOCK_INFO_SETTINGS.getMsgId());
            commandKafkaProducer.send(key, lockInfoSettingsMsgBody);
            log.info("Kafka (Topic : Command) send , key : " + key.toString() +
                    " value : " + lockInfoSettingsMsgBody.toString());
        }
    }
    @Autowired
    public void setLockInfoManService(LockInfoManService lockInfoManService) {
        this.lockInfoManService = lockInfoManService;
    }

    @Autowired
    public void setCommandKafkaProducer(CommandKafkaProducer commandKafkaProducer) {
        this.commandKafkaProducer = commandKafkaProducer;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }

}
