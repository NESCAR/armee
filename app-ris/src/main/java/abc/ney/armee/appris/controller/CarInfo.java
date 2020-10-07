package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.dal.meta.dto.CarQueryParam;
import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "carInfo")
@PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
public class CarInfo {
    TsdbService tsdbService;

    @ApiOperation(value = "查询汽车信息", tags = {"数据查询"}, notes = "数据查询")
    @PostMapping("/query")
    public BaseResp<QueryResult> query(@RequestBody CarQueryParam carQueryParam) {
//        log.info("car query param " + carQueryParam.toString());
        QueryResult qr = tsdbService.query(carQueryParam.getFields(), carQueryParam.getTags(), carQueryParam.getRetentionPolicy(), carQueryParam.getSt(), carQueryParam.getEt());
        return new BaseResp<>(ResultStatus.http_status_ok, "查询成功", qr);
    }

    @Autowired
    public void setTsdbService(TsdbService tsdbService) {
        this.tsdbService = tsdbService;
    }
}
