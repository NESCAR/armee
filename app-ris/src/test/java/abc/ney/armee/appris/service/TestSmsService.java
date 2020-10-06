package abc.ney.armee.appris.service;

import abc.ney.armee.appris.service.impl.SmsServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSmsService {
    public static void main(String[] args) {
        try {
            SmsService sm = new SmsServiceImpl();
            boolean res = sm.sendValidateCode("8615658007838", "012930");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
