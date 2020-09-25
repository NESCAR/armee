package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.service.SouthwardCmdService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 汽车相关
 * @author neyzoter
 */
@RestController
@RequestMapping(value = "carControl")
public class CarControl {

    private static final String PREFIX = "carControl";

    @Autowired
    SouthwardCmdService southwardCmdService;
    @ApiOperation(value = "汽车上锁", tags = {"汽车"}, notes = "汽车上锁")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "access_token", value = "access_token", required = true),
//            @ApiImplicitParam(name = "username", value = "非必选，不加此字段默认返回所有")
//    })
    @PostMapping(value = "/lock")
    public BaseResp lock(String carId, String st, String et) {
//        System.out.print(carId + " " + st + " " + et);
        // TODO
        southwardCmdService.sendLockInfo(carId, "12344", "123", st, et);
        return new BaseResp(ResultStatus.http_status_ok);
    }
}
