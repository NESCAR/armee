package abc.ney.armee.appris.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application-ris.properties")
@Slf4j
public class TestMobileNotificaion {
    MobileNotification dingNotification;

    @Test
    public void testSendLockAuthInfo() {
        dingNotification.sendLockAuthInfo("15658007838", Timestamp.valueOf("2021-01-19 12:00:00"),
                Timestamp.valueOf("2021-01-19 19:00:00"), "浙A 123212", "浙大路38号");
    }

    @Resource(name = "dingNotification")
    public void setDingNotification(MobileNotification dingNotification) {
        this.dingNotification = dingNotification;
    }
}
