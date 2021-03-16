package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.dal.meta.po.Credentials;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.dal.meta.vo.StaffVo;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 用户管理Controller
 * @author neyzoter
 */
@Slf4j
@RestController
@RequestMapping(value = "userManage")
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders = "*")
public class UserManage {
    AdminService adminService;

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
        if (staffCredentialDto.getIcCode() == null) {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "驾驶员信息缺失--IC卡");
        }
        Map<String, Boolean> rtn = adminService.insertDriver(staffCredentialDto);
        if (rtn.get(staffCredentialDto.getName())) {
            return new BaseResp<>(ResultStatus.http_status_ok, "驾驶员插入成功", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "驾驶员不可插入", rtn);
        }
    }

    @ApiOperation(value = "查询管理员", tags = {"用户管理"}, notes = "查询管理员")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/queryAdmin")
    public BaseResp<List<StaffCredentialsDto>> queryAdmin() {
        List<StaffCredentialsDto> adminList = adminService.queryAdmin();
        return new BaseResp<>(ResultStatus.http_status_ok, "管理员信息", adminList);
    }
    @ApiOperation(value = "查询司机", tags = {"用户管理"}, notes = "查询管理员")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/queryDriver")
    public BaseResp<List<StaffCredentialsDto>> queryDriver() {
        List<StaffCredentialsDto> driverList = adminService.queryDriver();
        return new BaseResp<>(ResultStatus.http_status_ok, "司机信息", driverList);
    }

    @ApiOperation(value = "更新管理员", tags = {"用户管理"}, notes = "更新管理员")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/updateAdmin")
    public BaseResp<Map<Long, Boolean>> updateAdmin(@RequestBody StaffCredentialsDto staffCredentialsDto) {
        Map<Long, Boolean> res = adminService.updateAdmin(staffCredentialsDto);
        Set<Map.Entry<Long, Boolean>> set = res.entrySet();
        for (Map.Entry<Long, Boolean> e : set) {
            // 插入失败
            if (!e.getValue()) {
                return new BaseResp<>(ResultStatus.error_update_failed, "修改失败", res);
            }
        }
        return new BaseResp<>(ResultStatus.http_status_ok, "修改成功", res);
    }
    @ApiOperation(value = "更新司机", tags = {"用户管理"}, notes = "更新管理员")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/updateDriver")
    public BaseResp<Map<Long, Boolean>> updateDriver(@RequestBody StaffCredentialsDto staffCredentialsDto) {
        Map<Long, Boolean> res = adminService.updateDriver(staffCredentialsDto);
        Set<Map.Entry<Long, Boolean>> set = res.entrySet();
        for (Map.Entry<Long, Boolean> e : set) {
            // 插入失败
            if (!e.getValue()) {
                return new BaseResp<>(ResultStatus.error_update_failed, "修改失败", res);
            }
        }
        return new BaseResp<>(ResultStatus.http_status_ok, "修改成功", res);
    }

    @ApiOperation(value = "删除管理员", tags = {"用户管理"}, notes = "更新管理员")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/deleteAdmin")
    public BaseResp<Map<Long, Boolean>> deleteAdmin(@RequestBody List<Long> deleteId) {
        Map<Long, Boolean> res = adminService.deleteAdmins(deleteId);
        return new BaseResp<>(ResultStatus.http_status_ok, "删除结果", res);
    }


    @ApiOperation(value = "删除司机", tags = {"用户管理"}, notes = "更新管理员")
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/deleteDriver")
    public BaseResp<Map<Long, Boolean>> deleteDrive(@RequestBody List<Long> deleteId) {
        Map<Long, Boolean> res = adminService.deleteDrivers(deleteId);
        return new BaseResp<>(ResultStatus.http_status_ok, "", res);
    }

    @Autowired
    private void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
