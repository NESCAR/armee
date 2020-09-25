package abc.ney.armee.appris.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理Controller
 * @author neyzoter
 */
@RestController
@RequestMapping(value = "userManage")
public class UserManage {
    public static final String MANAGER_PREFIX = "userManage/manager";
    public static final String COMMON_STAFF_PREFIX = "userManage/commonStaff";
}
