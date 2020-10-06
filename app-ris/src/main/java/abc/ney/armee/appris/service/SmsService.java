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
}
