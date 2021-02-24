package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.biz.task.CommandKafkaProducer;
import abc.ney.armee.appris.biz.util.PswGen;
import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.*;
import abc.ney.armee.appris.service.impl.LockInfoManServiceImpl;
import abc.ney.armee.enginee.net.http.ResultStatus;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.comd.LockControlMsgBody;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Random;

/**
 * 汽车相关
 * @author neyzoter
 */
@Slf4j
@RestController
@RequestMapping(value = "carControl")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
//@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = {"Authorization"})
public class CarControl {
    CarService carService;
    LockInfoManService lockInfoManService;
    AdminService adminService;
    CommandKafkaProducer commandKafkaProducer;
    @ApiOperation(value = "汽车上锁，需要通过IC卡解锁", tags = {"汽车控制"}, notes = "汽车上锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备gid", required = true),
            @ApiImplicitParam(name = "driverId", value = "司机gid", required = true),
            @ApiImplicitParam(name = "st", value = "开始时间", required = true),
            @ApiImplicitParam(name = "et", value = "结束时间", required = true)
    })
    @PostMapping(value = "/addLockInfo")
    public BaseResp<String> addLockInfo(Long deviceId, Long driverId, String st, String et) {
        try {
            Timestamp stimestamp = Timestamp.valueOf(st);
            Timestamp etimestamp = Timestamp.valueOf(et);
            LockAuthInfo lockAuthInfo = new LockAuthInfo();
            lockAuthInfo.setDriverId(driverId);lockAuthInfo.setDeviceId(deviceId);
            lockAuthInfo.setEndTime(etimestamp);
            lockAuthInfo.setEndTime(stimestamp);
            if (lockInfoManService.addLockAuthInfo(lockAuthInfo)) {
                return new BaseResp<>(ResultStatus.error_invalid_argument, "时间参数已存在");
            }
            return new BaseResp<>(ResultStatus.http_status_ok, "设备上锁信息保存成功", lockAuthInfo.toString());
        } catch (IllegalArgumentException illegalArgumentException) {
            return new BaseResp<>(ResultStatus.error_invalid_argument, "时间参数不合法");
        }
    }

    @ApiOperation(value = "汽车立即上锁", tags = {"汽车控制"}, notes = "汽车上锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备gid", required = true),
    })
    @PostMapping(value = "/lockIm")
    public BaseResp<String> lockIm(Long deviceId) {
        Device car = carService.queryDeviceByGid(deviceId);
        KafkaMsgKey key = new KafkaMsgKey(car.getImei(), Jt808MsgType.CMD_LOCK_INFO_SETTINGS.getMsgId());
        LockControlMsgBody lockControlMsgBody = new LockControlMsgBody();
        lockControlMsgBody.setLockControl((byte)Device.LOCK_STATUS);
        commandKafkaProducer.send(key, lockControlMsgBody);
        return new BaseResp<>(ResultStatus.http_status_ok, "立即上锁信息已发送");
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
    @Autowired
    public void setCommandKafkaProducer(CommandKafkaProducer commandKafkaProducer) {
        this.commandKafkaProducer = commandKafkaProducer;
    }
}
