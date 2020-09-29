package abc.ney.armee.appris.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理Controller
 * @author neyzoter
 */
@RestController
@RequestMapping(value = "userManage")
@PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
public class UserManage {
}
