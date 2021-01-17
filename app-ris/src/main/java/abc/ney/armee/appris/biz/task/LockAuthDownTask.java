package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.appris.service.LockInfoManService;
import icu.nescar.armee.jet.broker.ext.producer.Producer;
import icu.nescar.armee.jet.broker.msg.command.LockInfoSettingsMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LockAuthDownTask implements Runnable {
    LockInfoManService lockInfoManService;
    CommandKafkaProducer commandKafkaProducer;
    AdminService adminService;
    @Override
    public void run() {
        List<LockAuthInfo> undownedLockAuthInfo = lockInfoManService.findDownloadInfo();
        LockInfoSettingsMsgBody lockInfoSettingsMsgBody = new LockInfoSettingsMsgBody();
        for (LockAuthInfo lai : undownedLockAuthInfo) {
            Staff driver = adminService.queryDriver(lai.getDriverId());
            if (driver.getIcCode() == null) {
                log.warn("司机无IC卡信息");
            }
            lockInfoSettingsMsgBody.setICID(driver.getIcCode());lockInfoSettingsMsgBody.setCarID(lai.getDeviceId());
            lockInfoSettingsMsgBody.setLockTimeStart();
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

}
