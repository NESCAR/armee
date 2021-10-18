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
import icu.nescar.armee.jet.broker.msg.comd.AuthInfoSettingsMsgBody;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static abc.ney.armee.appris.biz.util.LongToBytes.longToByteArray;

/**
 * 授权信息下发任务
 * @author neyzoter
 */
@Component
@Slf4j
public class LockAuthDownTask implements Runnable {
    LockInfoManService lockInfoManService;
    CommandKafkaProducer commandKafkaProducer;
    AdminService adminService;
    CarService carService;
    @SneakyThrows
    @Override
    public void run() {
        // step1. 查询可以下发的授权信息
        List<LockAuthInfo> undownedLockAuthInfo = lockInfoManService.findDownloadInfo();
        if (undownedLockAuthInfo.size() == 0) {
            log.info("未发现可以下发的消息");
        }
        AuthInfoSettingsMsgBody authInfoSettingsMsgBody = new AuthInfoSettingsMsgBody();
        // step2. 针对每个授权信息处理
        for (LockAuthInfo lai : undownedLockAuthInfo) {
            // step2.1 检查汽车是否上锁
            //         如果当前处于未上锁状态，不下发授权信息
            Staff driver = adminService.queryDriver(lai.getDriverId());
            Device car = carService.queryDeviceByGid(lai.getDeviceId());
            if (car.getLockStatus() == Device.LOCK_STATUS) {
                log.info(String.format("[device imei : %s] 汽车处于上锁状态，不能下发授权信息", car.getImei()));
                continue;
            }
            // step2.2 下发授权信息
            if (driver.getIcCode() == null) {
                log.warn("司机无IC卡信息");
            }
            String icCode = driver.getIcCode();
            StringBuilder icCodeStringBuilder = new StringBuilder(icCode);
            //直接前端不允许输入超过6个字节的数 如果不满6个字节就在开头补0
            //在终端解析的时候会把0去掉 解析出iccode
            for (int i = 0; i < 6 - icCode.length(); i++) {
                icCodeStringBuilder.insert(0,"0");
            }
            icCode = icCodeStringBuilder.toString();
            authInfoSettingsMsgBody.setDriverID(icCode);

            authInfoSettingsMsgBody.setLockTimeStart(TimeConverter.timestamp2BcdByte(
                    new Timestamp(lai.getStartTime().getTime())));
            authInfoSettingsMsgBody.setLockTimeEnd(TimeConverter.timestamp2BcdByte(
                    new Timestamp(lai.getEndTime().getTime())));
            KafkaMsgKey key = new KafkaMsgKey(car.getImei(), Jt808MsgType.CMD_AUTH_INFO_SETTINGS.getMsgId());
            commandKafkaProducer.send(key, authInfoSettingsMsgBody);
            log.info("Kafka (Topic : Command) send , key : " + key.toString() +
                    " value : " + authInfoSettingsMsgBody.toString());

//            step3 下发授权信息后将下发信息的标志位改成已下发
//            当消息下发后，将下发消息的数据库的位置设为true，说明已下发。消息下发一次。
            log.debug("[From DB]  <<<<<<  " + lai.toString());
            lai.setDowned(true);
            lockInfoManService.updateLockInfoByPrimaryKey(lai);
            log.debug("[To DB]  >>>>>>  " + lai.toString());
            log.info("消息成功下发");

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
