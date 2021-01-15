package abc.ney.armee.appris.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application-ris.properties")
@Slf4j
public class TestLockInfoManService {
    LockInfoManService lockInfoManService;

    @Autowired
    public void setLockInfoManService(LockInfoManService lockInfoManService) {
        this.lockInfoManService = lockInfoManService;
    }
    @Test
    public void testCovered() {
        Timestamp st1 = Timestamp.valueOf("2021-1-16 1:21:21");
        Timestamp et1 = Timestamp.valueOf("2021-1-16 5:22:21");
        Boolean covered = lockInfoManService.covered(1L, st1, et1);
        if (covered) {
            log.info("日期非法 : " + st1 + " - " + et1);
        } else {
            log.info("日期合法 : " + st1 + " - " + et1);
        }

        st1 = Timestamp.valueOf("2021-1-16 21:21:21");
        et1 = Timestamp.valueOf("2021-1-16 23:21:21");
        covered = lockInfoManService.covered(1L, st1, et1);
        if (covered) {
            log.info("日期非法 : " + st1 + " - " + et1);
        } else {
            log.info("日期合法 : " + st1 + " - " + et1);
        }
    }
}
