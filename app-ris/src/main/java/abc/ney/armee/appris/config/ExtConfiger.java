package abc.ney.armee.appris.config;

/**
 * JVM Option配置
 * @author neyzoter
 */
public class ExtConfiger {
    /**
     * 短信签名sign name，需要从阿里云申请
     * https://dysms.console.aliyun.com/dysms.htm?spm=5176.10695662.1128094.4.27b83583qxIJ2O#/quickStart
     */
    public static final String SMS_SIGN_NAME = "sms.sign.name";
    public static final String DEFAULT_SMS_SIGN_NAME = "ARMEE物流管理";
    /**
     * 短信格式模版 template code，需要从阿里云申请
     * https://dysms.console.aliyun.com/dysms.htm?spm=5176.10695662.1128094.4.27b83583qxIJ2O#/quickStart
     */
    public static final String SMS_TEMPLATE_CODE_VALIDATE_CODE = "sms.template.code.validate-code";
    public static final String DEFAULT_SMS_TEMPLATE_CODE = "SMS_204111562";

    public static final String SMS_TEMPLATE_CODE_ALARM = "sms.template.code.alarm";
    public static final String DEFAULT_SMS_TEMPLATE_CODE_ALARM = "SMS_206535176";
    /**
     * accesskey id : LTAI4FykeyqB176bTzna47mm
     * accesskey secret : 2rd10iVClvX5xbkicLGCD8OzXMInjb
     */
    public static final String ALIYUN_ACCESSKEY_ID = "aliyun.accesskey.id";
    public static final String ALIYUN_ACCESSKEY_SECRET = "aliyun.accesskey.secret";

    public static final String DING_NOTIFICATION_SECRET_PROPERTY_NAME = "ding.notif.secret";
    public static final String DING_NOTIFICATION_ACCESS_TOKEN_PROPERTY_NAME = "ding.notif.access_token";

}
