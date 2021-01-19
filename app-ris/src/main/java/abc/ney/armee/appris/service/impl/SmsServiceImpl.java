package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.config.ExtConfiger;
import abc.ney.armee.appris.service.SmsService;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

//@Service
@Slf4j
public class SmsServiceImpl implements SmsService {
    String accessKeyId;
    String accessSecret;
    String sign;
    String templateValidateCode;
    String templateAlarm;
    public SmsServiceImpl() throws Exception {
        accessKeyId = System.getProperty(ExtConfiger.ALIYUN_ACCESSKEY_ID);
        accessSecret = System.getProperty(ExtConfiger.ALIYUN_ACCESSKEY_SECRET);
        sign = System.getProperty(ExtConfiger.SMS_SIGN_NAME);
        templateValidateCode = System.getProperty(ExtConfiger.SMS_TEMPLATE_CODE_VALIDATE_CODE);
        templateAlarm = System.getProperty(ExtConfiger.SMS_TEMPLATE_CODE_ALARM);
        if (accessKeyId == null || accessSecret == null) {
            throw new Exception("阿里云AccessKey ID或者AccessKey Secret未配置");
        }
        if (sign == null) {
            throw new Exception("短信标签未配置");
        }
        if (templateValidateCode == null) {
            throw new Exception("短信模版未配置");
        }
    }
    @Override
    public boolean sendValidateCode(String tel, String code) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 区号+电话，比如8613198980098
        request.putQueryParameter("PhoneNumbers", tel);
        // 配置好的签名，必须在阿里云配置
        request.putQueryParameter("SignName", sign);
        // 配置好的模版，必须在阿里云配置
        request.putQueryParameter("TemplateCode", templateValidateCode);
        // 验证码
        request.putQueryParameter("TemplateParam", genTemplateParam(code));
        try {
            CommonResponse response = client.getCommonResponse(request);
            log.info(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean sendLockInfo(String tel, String start, String end, String license) {
        // TODO 签名需要企业认证
        return true;
    }

    private String genTemplateParam(String code) {
        return String.format("{\"code\":\"%s\"}", code);
    }
}
