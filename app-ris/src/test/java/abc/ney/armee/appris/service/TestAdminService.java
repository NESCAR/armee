package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.dal.meta.po.AuthorityRole;
import abc.ney.armee.appris.dal.meta.po.Staff;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application-ris.properties")
@Slf4j
public class TestAdminService {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

//    @Test
    public void testQueryDriverByIcCode() {
        Staff driver = adminService.queryDriverByIcCode("1230980");
        log.info(driver.toString());
    }
//    @Test
    public void testUpdateAdmin() {
        StaffCredentialsDto staffCredentialsDto2 = new StaffCredentialsDto();
        staffCredentialsDto2.setRealName("合法管理员");staffCredentialsDto2.setStaffGid(4L);
        staffCredentialsDto2.setName("legal_admin");
        staffCredentialsDto2.setAuthority(AuthorityRole.ROLE_ADMIN.getAuthority());
        Map<Long, Boolean> res = adminService.updateAdmin(staffCredentialsDto2);
        log.info(res.toString());
    }
//    @Test
    public void testInsertAdmin() {
        StaffCredentialsDto staffCredentialsDto1 = new StaffCredentialsDto();
        staffCredentialsDto1.setAuthority("ROLE_ADMIN");staffCredentialsDto1.setName("scc");
        staffCredentialsDto1.setRealName("胡婧");staffCredentialsDto1.setEmail("hujing@zju.edu.cn");
        staffCredentialsDto1.setNo("012");staffCredentialsDto1.setPassword("1234");
        staffCredentialsDto1.setTel("12345678990");staffCredentialsDto1.setPosition("管理员");
        staffCredentialsDto1.setTelArea("86");
//        StaffCredentialsDto staffCredentialsDto2 = new StaffCredentialsDto();
//        staffCredentialsDto2.setAuthority("ROLE_ADMIN");staffCredentialsDto2.setName("oie");
//        staffCredentialsDto2.setRealName("徐");staffCredentialsDto2.setEmail("xushuai@zju.edu.cn");
//        staffCredentialsDto2.setNo("010");staffCredentialsDto2.setPassword("321");
//        staffCredentialsDto2.setTel("13123123123");staffCredentialsDto2.setPosition("管理员");
//        staffCredentialsDto2.setTelArea("86");
        Map<String, Boolean> res1 = adminService.insertAdmin(staffCredentialsDto1);
//        Map<String, Boolean> res2 = adminService.insertAdmin(staffCredentialsDto2);
        log.info(res1.toString());
//        log.info(res2.toString());
    }
    @Test
    public void testInsertDriver() {
        StaffCredentialsDto staffCredentialsDto1 = new StaffCredentialsDto();
        staffCredentialsDto1.setAuthority("ROLE_COMMON_STAFF");staffCredentialsDto1.setName("zhangsan");
        staffCredentialsDto1.setRealName("张三");staffCredentialsDto1.setEmail("zhangsan@zju.edu.cn");
        staffCredentialsDto1.setNo("015");staffCredentialsDto1.setPassword("1234");
        staffCredentialsDto1.setTel("12345678990");staffCredentialsDto1.setPosition("司机");
        staffCredentialsDto1.setTelArea("86");staffCredentialsDto1.setIcCode("1230989");
        Map<String, Boolean> res1 = adminService.insertDriver(staffCredentialsDto1);
        log.info(res1.toString());
    }
//    @Test
    public void testDeleteAdmins() {
        List<Long> ids = new LinkedList<>();
//        ids.add(4L);ids.add(5L);
        ids.add(8L);ids.add(9L);
        Map<Long, Boolean> res = adminService.deleteAdmins(ids);
        log.info(res.toString());
    }
}
