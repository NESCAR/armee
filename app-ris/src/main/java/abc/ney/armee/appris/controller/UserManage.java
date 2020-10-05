package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 * @author neyzoter
 */
@Slf4j
@RestController
@RequestMapping(value = "userManage")
public class UserManage {
    AdminService adminService;

    @Autowired
    public UserManage(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "添加普通管理员", tags = {"用户管理"}, notes = "添加管理管理员")
    @ApiResponses({
            @ApiResponse(code = 200, message = "驾驶员插入成功"),
            @ApiResponse(code = 406, message = "管理员未全部插入")
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addAdmin")
    public BaseResp<Map<String, Boolean>> addAdmin(@RequestBody StaffCredentialsDto staffCredentialDto) {
        Map<String, Boolean> rtn = adminService.insertAdmin(staffCredentialDto);
        if (rtn.get(staffCredentialDto.getName())) {
            return new BaseResp<>(ResultStatus.http_status_ok, "管理员插入成功", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "管理员不可插入", rtn);
        }
    }
    @ApiOperation(value = "批量添加普通管理员", tags = {"用户管理"}, notes = "添加管理管理员")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "StaffCredentialsDto列表", value = "管理员", required = true)
//    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "驾驶员插入成功"),
            @ApiResponse(code = 406, message = "管理员未全部插入")
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addAdmins")
    public BaseResp<HashMap<String, Boolean>> addAdmins(@RequestBody List<StaffCredentialsDto> staffCredentialDtos) {
        HashMap<String, Boolean> rtn = new HashMap<>();
        // 是否全部插入成功
        boolean allInserted = true;
        for (StaffCredentialsDto s : staffCredentialDtos) {
            // 打印插入的
            Map<String, Boolean> res = adminService.insertAdmin(s);
            if (!res.get(s.getName())) {
                allInserted = false;
            }
            rtn.putAll(res);
        }
        if (allInserted) {
            return new BaseResp<>(ResultStatus.http_status_ok, "管理员全部正确插入", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "管理员未全部插入", rtn);
        }
    }
    @ApiOperation(value = "添加驾驶员", tags = {"用户管理"}, notes = "添加驾驶员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "StaffCredentialsDto", value = "驾驶员", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "驾驶员插入成功"),
            @ApiResponse(code = 406, message = "驾驶员不可插入")
    })
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/addDriver")
    public BaseResp<Map<String, Boolean>> addDriver(@RequestBody StaffCredentialsDto staffCredentialDto) {
        Map<String, Boolean> rtn = adminService.insertDriver(staffCredentialDto);
        if (rtn.get(staffCredentialDto.getName())) {
            return new BaseResp<>(ResultStatus.http_status_ok, "驾驶员插入成功", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "驾驶员不可插入", rtn);
        }
    }
}
