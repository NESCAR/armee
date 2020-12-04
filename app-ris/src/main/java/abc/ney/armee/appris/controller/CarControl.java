package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.biz.util.PswGen;
import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.CarService;
import abc.ney.armee.appris.service.SmsService;
import abc.ney.armee.appris.service.SouthwardCmdService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * 汽车相关
 * @author neyzoter
 */
@Slf4j
@RestController
@RequestMapping(value = "carControl")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
public class CarControl {
    SouthwardCmdService southwardCmdService;
    CarService carService;
    SmsService smsService;
    @ApiOperation(value = "汽车上锁，需要通过输入密码实现开锁", tags = {"汽车控制"}, notes = "汽车上锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备gid", required = true),
            @ApiImplicitParam(name = "st", value = "开始时间", required = true),
            @ApiImplicitParam(name = "et", value = "结束时间", required = true)
    })
    @PostMapping(value = "/lock-with-random-psw")
    public BaseResp<String> lockWithRandomPsw(Long deviceId, String st, String et) {
        Device device = carService.queryDeviceByGid(deviceId);
        Staff driver = carService.queryDriverByGid(device.getDriverGid());
        String randomSixNum = PswGen.genBrakePsw();
        if (!carService.updateDevicePsw(deviceId, randomSixNum)) {
            log.info("设备密码设置失败");
            return new BaseResp<>(ResultStatus.error_update_failed, "设备密码设置失败");
        }
        // 发送短息到司机
        // TODO
        log.info(String.format("!!在部署前请将SMS服务打开，发送开锁密码【%s】到司机", randomSixNum));
//        smsService.sendValidateCode(driver.getTel(), randomSixNum);
        // 发送上锁信息到设备
        southwardCmdService.sendLockInfo(device.getImei(), String.valueOf(driver.getGid()), randomSixNum, st, et);
        return new BaseResp<>(ResultStatus.http_status_ok);
    }
    @ApiOperation(value = "汽车上锁，需要通过IC卡解锁", tags = {"汽车控制"}, notes = "汽车上锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备gid", required = true),
            @ApiImplicitParam(name = "st", value = "开始时间", required = true),
            @ApiImplicitParam(name = "et", value = "结束时间", required = true)
    })
    @PostMapping(value = "/lock")
    public BaseResp<String> lock(Long deviceId, String st, String et) {
        Device device = carService.queryDeviceByGid(deviceId);
        Staff driver = carService.queryDriverByGid(device.getDriverGid());
        String icCode = driver.getIcCode();
        if (!carService.updateDevicePsw(deviceId, icCode)) {
            log.info("设备密码设置失败");
            return new BaseResp<>(ResultStatus.error_update_failed, "设备密码设置失败");
        }
        // 发送短息到司机
        // TODO 平台接收到resp后再进行短信发送
        log.info(String.format("!!在部署前请将SMS服务打开，发送可以开锁的信息到司机"));
//        smsService.sendValidateCode(driver.getTel(), randomSixNum);
        // 发送上锁信息到设备
        southwardCmdService.sendLockInfo(device.getImei(), String.valueOf(driver.getGid()), icCode, st, et);
        return new BaseResp<>(ResultStatus.http_status_ok);
    }

    @Autowired
    private void setSouthwardCmdService(SouthwardCmdService southwardCmdService) {
        this.southwardCmdService = southwardCmdService;
    }
    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
    @Autowired
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }
}
