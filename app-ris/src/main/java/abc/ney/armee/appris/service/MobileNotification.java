package abc.ney.armee.appris.service;

import java.sql.Timestamp;

/**
 * 移动端消息推送服务
 * @author neyzoter
 */
public interface MobileNotification {
    public void sendLockAuthInfo(String atMobile, Timestamp st, Timestamp et, String license, String location);
}
