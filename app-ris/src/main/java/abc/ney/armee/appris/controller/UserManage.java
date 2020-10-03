package abc.ney.armee.appris.controller;

import abc.ney.armee.appris.dal.meta.po.PoConstant;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.service.AdminService;
import abc.ney.armee.enginee.net.http.ResultStatus;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理Controller
 * @author neyzoter
 */
@RestController
@RequestMapping(value = "userManage")
public class UserManage {
    AdminService adminService;

    @Autowired
    public UserManage(AdminService adminService) {
        this.adminService = adminService;
    }

    @ApiOperation(value = "添加普通管理员", tags = {"管理员"}, notes = "添加管理管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "StaffCredentialsDto", value = "staff", required = true)
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addAdmin")
    public BaseResp<Map<String, Boolean>> addAdmin(@RequestBody StaffCredentialsDto staffCredentialDtos) {
        System.out.println("add admin : " + staffCredentialDtos.toString());
        Map<String, Boolean> rtn = adminService.insertAdmin(staffCredentialDtos);
        if (rtn.get(staffCredentialDtos.getName())) {
            return new BaseResp<>(ResultStatus.http_status_ok, "插入成功", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "用户不可插入", rtn);
        }
    }
    @ApiOperation(value = "添加普通管理员", tags = {"管理员"}, notes = "添加管理管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "StaffCredentialsDto列表", value = "staff", required = true)
    })
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    @PostMapping("/addAdmins")
    public BaseResp<HashMap<String, Boolean>> addAdmins(@RequestBody List<StaffCredentialsDto> staffCredentialDtos) {
        HashMap<String, Boolean> rtn = new HashMap<>();
        boolean allInserted = true;
        for (StaffCredentialsDto s : staffCredentialDtos) {
            // 打印插入的
            System.out.println("add admin : " + s.toString());
            Map<String, Boolean> res = adminService.insertAdmin(s);
            if (!res.get(s.getName())) {
                allInserted = false;
            }
            rtn.putAll(res);
        }
        if (allInserted) {
            return new BaseResp<>(ResultStatus.http_status_ok, "全部正确插入", rtn);
        } else {
            return new BaseResp<>(ResultStatus.http_status_not_acceptable, "未全部插入", rtn);
        }
    }
    @ApiOperation(value = "汽车上锁", tags = {"汽车"}, notes = "汽车上锁")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staff", value = "staff", required = true),
    })
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @PostMapping("/addCommonStaff")
    public BaseResp<String> addCommonStaff(@RequestBody List<Staff> staff) {
        System.out.println(staff.get(0).toString());
        System.out.println(staff.get(1).toString());
        return new BaseResp<>(ResultStatus.http_status_ok, "接受成功");
    }
}
