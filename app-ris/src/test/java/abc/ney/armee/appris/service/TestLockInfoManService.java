package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
//    @Test
    public void testUpdateLockInfoByPrimaryKey() {
        LockAuthInfo lockAuthInfo = new LockAuthInfo();
        lockAuthInfo.setDeviceId(1L);lockAuthInfo.setStartTime(Timestamp.valueOf("2021-01-17 14:00:00"));
        lockAuthInfo.setEndTime(Timestamp.valueOf("2021-01-17 20:00:00"));
        lockAuthInfo.setGid(2L);
        lockAuthInfo.setDowned(true);
        log.info("update result : " + lockInfoManService.updateLockInfoByPrimaryKey(lockAuthInfo));
    }

//    @Test
    public void testFindDownloadInfo() {
        List<LockAuthInfo> downinfo = lockInfoManService.findDownloadInfo();
        log.info(downinfo.toString());
    }
    @Test
    public void testAddLockAuthInfo() {
        LockAuthInfo lockAuthInfo = new LockAuthInfo();
        lockAuthInfo.setDowned(false);
        lockAuthInfo.setStartTime(Timestamp.valueOf("2021-1-28 16:00:00"));
        lockAuthInfo.setEndTime(Timestamp.valueOf("2021-2-28 21:00:00"));
        lockAuthInfo.setDeviceId(1L);lockAuthInfo.setDriverId(13L);
        Boolean res1 = lockInfoManService.addLockAuthInfo(lockAuthInfo);
//        lockAuthInfo.setDowned(false);lockAuthInfo.setEndTime(Timestamp.valueOf("2021-1-17 23:00:00"));
//        lockAuthInfo.setStartTime(Timestamp.valueOf("2021-1-17 19:00:00"));
//        Boolean res2 = lockInfoManService.addLockAuthInfo(lockAuthInfo);
        log.info("result 1 : " + res1);
//        log.info("result 2 : " + res2);
    }
//    @Test
    public void testCovered() {
        Timestamp st1 = Timestamp.valueOf("2021-1-16 1:21:21");
        Timestamp et1 = Timestamp.valueOf("2021-1-16 5:22:21");
        LockAuthInfo lockAuthInfo = new LockAuthInfo();
        lockAuthInfo.setDeviceId(1L);lockAuthInfo.setStartTime(st1);lockAuthInfo.setEndTime(et1);
        Boolean covered = lockInfoManService.covered(lockAuthInfo);
        if (covered) {
            log.info("日期非法 : " + st1 + " - " + et1);
        } else {
            log.info("日期合法 : " + st1 + " - " + et1);
        }

        st1 = Timestamp.valueOf("2021-1-16 21:21:21");
        et1 = Timestamp.valueOf("2021-1-16 23:21:21");
        lockAuthInfo.setDeviceId(1L);lockAuthInfo.setStartTime(st1);lockAuthInfo.setEndTime(et1);
        covered = lockInfoManService.covered(lockAuthInfo);
        if (covered) {
            log.info("日期非法 : " + st1 + " - " + et1);
        } else {
            log.info("日期合法 : " + st1 + " - " + et1);
        }
    }
}
