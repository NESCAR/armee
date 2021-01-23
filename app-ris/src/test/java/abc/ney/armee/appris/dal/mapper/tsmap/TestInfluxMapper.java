package abc.ney.armee.appris.dal.mapper.tsmap;

import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.appris.service.impl.TsdbServiceImpl;
import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;

import java.sql.Timestamp;

/**
 * 测试InfluxMapper
 * @author neyzoter
 */
public class TestInfluxMapper {
    public static void main(String[] args) {
        LocationUploadRequestMsgBodyMapper locationUploadRequestMsgBodyMapper = new LocationUploadRequestMsgBodyMapper();
        InfluxMapperRegister influxMapperRegister = new InfluxMapperRegister();
        influxMapperRegister.registerMapper(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD, locationUploadRequestMsgBodyMapper);
        LocationUploadRequestMsgBody locationUploadRequestMsgBody = new LocationUploadRequestMsgBody();
        locationUploadRequestMsgBody.setWarningFlag(1);locationUploadRequestMsgBody.setDirection((short)2);
        locationUploadRequestMsgBody.setHeight((short)10);locationUploadRequestMsgBody.setLat(1.0);
        locationUploadRequestMsgBody.setLng(2.0);locationUploadRequestMsgBody.setSpeed((short)20);
        locationUploadRequestMsgBody.setStatus(1);
        MsgKey key = new KafkaMsgKey("12312", Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD.getMsgId());
        TsdbService tsdbService = new TsdbServiceImpl(influxMapperRegister);
        boolean run = true;
        while (run) {
            for (int i = 0; i < 1000; i++) {
                locationUploadRequestMsgBody.setLocationTime(
                        TimeConverter.timestamp2BcdByte(new Timestamp(System.currentTimeMillis())));
                tsdbService.insert(key, locationUploadRequestMsgBody);
                try {
                    Thread.sleep(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            run = false;
        }
    }
}
