package abc.ney.armee.appris.service;

/**
 * 短信服务
 * @author neyzoter
 */
public interface SmsService {

    /**
     * 发送验证码
     * @param tel 手机号
     * @param code 验证码
     * @return 是否成功发送
     */
    boolean sendValidateCode(String tel, String code);

    /**
     * 发送上锁和解锁信息到司机
     * @param tel 手机号
     * @param start 开始时间
     * @param end 结束时间
     * @param license 车牌
     * @return 是否成功发送
     */
    boolean sendLockInfo(String tel, String start, String end, String license);
}
