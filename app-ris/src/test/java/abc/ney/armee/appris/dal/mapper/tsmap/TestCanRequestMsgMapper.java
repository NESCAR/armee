package abc.ney.armee.appris.dal.mapper.tsmap;

import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.appris.service.impl.TsdbServiceImpl;
import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.CANMessage.CANMsgBody;
import icu.nescar.armee.jet.broker.msg.CANMessage.EBS11;
import icu.nescar.armee.jet.broker.msg.req.CANMsgRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;

import java.sql.Timestamp;

/**
 * @Auther whale
 * @Date 2021/5/20
 * 测试canreqeust msgbody mapper
 */
public class TestCanRequestMsgMapper {
    public static void main(String[] args) {
        CANRequestMsgBodyMapper canRequestMsgBodyMapper = new CANRequestMsgBodyMapper();
        InfluxMapperRegister influxMapperRegister = new InfluxMapperRegister();
        influxMapperRegister.registerMapper(Jt808MsgType.CLIENT_CAN_INFO_UPLOAD, canRequestMsgBodyMapper);
        CANMsgRequestMsgBody canMsgRequestMsgBody=new CANMsgRequestMsgBody();
        canMsgRequestMsgBody.setCanID(1234);
        CANMsgBody ebs11=new EBS11();
        canMsgRequestMsgBody.setCanData(ebs11);
        MsgKey key = new KafkaMsgKey("12312", Jt808MsgType.CLIENT_CAN_INFO_UPLOAD.getMsgId());
        TsdbService tsdbService = new TsdbServiceImpl(influxMapperRegister);
        boolean run = true;
        while (run) {
            for (int i = 0; i < 1000; i++) {
//                canMsgRequestMsgBody.setCanTime();
                tsdbService.insert(key, canMsgRequestMsgBody);
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

