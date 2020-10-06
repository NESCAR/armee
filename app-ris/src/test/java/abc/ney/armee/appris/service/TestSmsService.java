package abc.ney.armee.appris.service;

import abc.ney.armee.appris.service.impl.SmsServiceImpl;

public class TestSmsService {
    public static void main(String[] args) {
        try {
            SmsService sm = new SmsServiceImpl();
            boolean res = sm.sendValidateCode("8615658007838", "012930");
            System.out.println("SMS Res : " + res);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
