package abc.ney.armee.appris.service.msg.handler;

import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.req.AuthUpdateSuccessRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.LockStatusUploadRequestMsgBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试上行消息处理器
 * @author neyzoter
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource(value = "classpath:application-ris.properties")
@Slf4j
public class TestUpMsgHandler {
    UpMsgHandler authUpdateSuccessRequestHandler;
    UpMsgHandler lockStatusUploadRequestHandler;

//    @Test
    public void testLockInfoSettingsHandler() {
        MsgKey key = new KafkaMsgKey("123",
                Jt808MsgType.CLIENT_SETTINGS_UPDATE_INFO_UPLOAD.getMsgId());
        AuthUpdateSuccessRequestMsgBody authUpdateSuccessRequestMsgBody = new AuthUpdateSuccessRequestMsgBody();
        authUpdateSuccessRequestMsgBody.setDriverId("1230980");
        authUpdateSuccessRequestMsgBody.setLockTimeStart("2021-01-17 09:00:00");
        authUpdateSuccessRequestMsgBody.setLockTimeEnd("2021-01-17 12:00:00");
        this.authUpdateSuccessRequestHandler.process(key, authUpdateSuccessRequestMsgBody);
    }

    @Test
    public void testLockStatusUploadRequestHandler() {
        MsgKey key = new KafkaMsgKey("123",
                Jt808MsgType.CLIENT_LOCK_INFO_UPLOAD.getMsgId());
        LockStatusUploadRequestMsgBody lockStatusUploadRequestMsgBody = new LockStatusUploadRequestMsgBody();
        lockStatusUploadRequestMsgBody.setLockStatus((byte)1);
        lockStatusUploadRequestMsgBody.setLockStatusTime("210117150101");
        this.lockStatusUploadRequestHandler.process(key, lockStatusUploadRequestMsgBody);
    }

    @Autowired
    public void setUpMsgHandler(@Qualifier("authUpdateSuccessRequestHandler") UpMsgHandler authUpdateSuccessRequestHandler) {
        this.authUpdateSuccessRequestHandler = authUpdateSuccessRequestHandler;
    }
    @Autowired
    public void setLockStatusUploadRequestHandler(@Qualifier("lockStatusUploadRequestHandler") UpMsgHandler lockStatusUploadRequestHandler) {
        this.lockStatusUploadRequestHandler = lockStatusUploadRequestHandler;
    }
}
