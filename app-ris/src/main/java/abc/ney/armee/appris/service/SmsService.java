package abc.ney.armee.appris.service;

/**
 * 短信服务
 * @author neyzoter
 */
public interface SmsService {

    boolean send(String tel, String msg);
}
