package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.msgmap.InfluxMapperRegister;
import abc.ney.armee.appris.dal.mapper.msgmap.LocationUploadRequestMsgBodyMapper;
import abc.ney.armee.appris.service.TsdbService;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import lombok.extern.slf4j.Slf4j;

/**
 * 测试TsdbServiceImpl
 * @author neyzoter
 */
@Slf4j
public class TestTsdbServiceImpl {
    public static void main(String[] args) {
        InfluxMapperRegister imr = new InfluxMapperRegister();
        imr.registerMapper(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD, new LocationUploadRequestMsgBodyMapper());
        TsdbService tsdbService = new TsdbServiceImpl(null, imr);
        MsgKey mk = new KafkaMsgKey("SE12309", Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD.getMsgId());
        LocationUploadRequestMsgBody msg = new LocationUploadRequestMsgBody();
        msg.setDirection((short)1);
        msg.setHeight((short)2);
        msg.setLat(1.808);
        msg.setLng(2.121);
        msg.setSpeed((short)60);
        msg.setStatus(10);
        int i = 0;
        while (i++ < 10) {
            try {
                msg.setTime(String.valueOf(System.currentTimeMillis()));
                tsdbService.insert(mk, msg);
                log.info(msg.toString() + " send");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
