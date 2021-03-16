package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.dal.meta.dto.CarQueryParam;
import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.vo.DeviceVo;
import abc.ney.armee.appris.service.CarService;
import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.common.BaseMapper;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "carInfo")
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
@CrossOrigin(origins = "http://localhost:3000", methods = {RequestMethod.GET, RequestMethod.POST},maxAge = 3600, allowedHeaders = "*")
public class CarInfo {
    TsdbService tsdbService;

    CarService carService;

    @ApiOperation(value = "插入汽车信息", tags = {"汽车信息"}, notes = "数据添加")
    @PostMapping("/addDevice")
    public BaseResp<Device> addDevice(@RequestBody Device device) {
        if (device.getGmtCreate() == null) {
            Timestamp thisTime = new Timestamp(System.currentTimeMillis());
            device.setGmtCreate(thisTime);
            device.setGmtUpdate(thisTime);
        }
        if (!carService.addDevice(device)) {
            return new BaseResp<>(ResultStatus.error_create_failed, "添加失败", device);
        }
        return new BaseResp<>(ResultStatus.http_status_ok, "添加成功", device);
    }

    @ApiOperation(value = "查询汽车时序数据", tags = {"汽车信息"}, notes = "数据查询")
    @PostMapping("/queryTsData")
    public BaseResp<QueryResult> queryTsData(@RequestBody CarQueryParam carQueryParam) {
        log.info("car query param " + carQueryParam.toString());
        QueryResult qr = tsdbService.query(carQueryParam.getFields(), carQueryParam.getTags(), carQueryParam.getRetentionPolicy(), carQueryParam.getSt(), carQueryParam.getEt());
        return new BaseResp<>(ResultStatus.http_status_ok, "查询成功", qr);
    }

    @ApiOperation(value = "查询汽车信息", tags = {"汽车信息"}, notes = "数据查询")
    @PostMapping("/queryDevice")
    public BaseResp<List<DeviceVo>> queryDevice() {
        List<Device> deviceList = carService.queryDevice();
        if (deviceList != null) {
            List<DeviceVo> res = new LinkedList<>();
            for (Device d : deviceList) {
                res.add(new DeviceVo(d));
            }
            return new BaseResp<>(ResultStatus.http_status_ok, "查询成功", res);
        } else {
            return new BaseResp<>(ResultStatus.error_search_failed, "查询失败", null);
        }
    }

    @ApiOperation(value = "更新汽车信息", tags = {"汽车信息"}, notes = "数据更新")
    @PostMapping("/updateDevice")
    public BaseResp<Device> updateDevice(@RequestBody Device device) {
        if (!carService.updateDeviceByGid(device)) {
            return new BaseResp<>(ResultStatus.error_update_failed, "更新失败", device);
        }
        return new BaseResp<>(ResultStatus.http_status_ok, "更新成功", device);
    }

    @ApiOperation(value = "删除汽车信息", tags = {"汽车信息"}, notes = "数据删除")
    @PostMapping("/delete")
    public BaseResp<Device> delete(@RequestParam Long deviceId) {
        carService.deleteDeviceByGid(deviceId);
        return new BaseResp<>(ResultStatus.http_status_ok, "删除成功");
    }

    @Autowired
    public void setTsdbService(TsdbService tsdbService) {
        this.tsdbService = tsdbService;
    }
    @Autowired
    public void setCarService(CarService carService) {
        this.carService = carService;
    }
}
