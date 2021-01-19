package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.config.ExtConfiger;
import abc.ney.armee.appris.service.MobileNotification;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Arrays;

@Slf4j
@Service("dingNotification")
public class DingNotification implements MobileNotification {
    private String secret;
    private String url;

    public DingNotification() {
        secret = System.getProperty(ExtConfiger.DING_NOTIFICATION_SECRET_PROPERTY_NAME);
        url = "https://oapi.dingtalk.com/robot/send?access_token=" +
                System.getProperty(ExtConfiger.DING_NOTIFICATION_ACCESS_TOKEN_PROPERTY_NAME);
    }

    @Override
    public void sendLockAuthInfo(@NotNull String atMobile, @NotNull Timestamp st, @NotNull Timestamp et, @NotNull String license, String location) {
        try {
            Long timestamp = System.currentTimeMillis();
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            System.out.println(timestamp);
            System.out.println(sign);
            DingTalkClient client = new DefaultDingTalkClient(String.format("%s&timestamp=%s&sign=%s", url, timestamp, sign));
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Arrays.asList(atMobile));
            request.setAt(at);
            request.setMsgtype("markdown");
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("授权信息");
            markdown.setText(
                    String.format("#### 授权信息 @%s\n\n", atMobile) +
                    String.format("> 车牌: %s\n\n", license) +
                    String.format("> 位置: %s\n\n", location != null ? location : "未知") +
                    String.format("> 授权时间段: %s ~ %s", st.toString(), et.toString()));
            request.setMarkdown(markdown);
            OapiRobotSendResponse response = client.execute(request);
            if (!response.isSuccess()) {
                log.info("DingDing消息发送失败");
            }
            log.info(" >>>>>> " + markdown.getText().replaceAll("\n", " "));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
