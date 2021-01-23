package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tsmap.InfluxMapperRegister;
import abc.ney.armee.appris.dal.mapper.tsmap.LocationUploadRequestMsgBodyMapper;
import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 测试TsdbServiceImpl
 * @author neyzoter
 */
@Slf4j
public class TestTsdbServiceImpl {
    public static void main(String[] args) {
        TestTsdbServiceImpl impl = new TestTsdbServiceImpl();
        impl.testInsert();
    }

    /**
     * 测试插入
     */
    public void testInsert() {
        InfluxMapperRegister imr = new InfluxMapperRegister();
        imr.registerMapper(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD, new LocationUploadRequestMsgBodyMapper());
        TsdbService tsdbService = new TsdbServiceImpl( imr);
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
                msg.setLocationTime(TimeConverter.timestamp2BcdByte(new Timestamp(System.currentTimeMillis())));
                tsdbService.insert(mk, msg);
                log.info(msg.toString() + " send");
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试SQL语句生成
     */
    public void testSqlGen() {
        TsdbServiceImpl tsdbServiceImpl = new TsdbServiceImpl(new InfluxMapperRegister());
        Set<String> fields = new HashSet<>();fields.add("fi");fields.add("bi");
        Map<String, String> tags = new HashMap<>();tags.put("imei", "1232123EF");
        String st = "2020-08-21T14:01:54.494+08:00";
        String et = "2020-08-22T14:01:54.494+08:00";
        String retentionPolicy = "ONE_MONTH";
        String sql = tsdbServiceImpl.sqlGen(null, null, null, null, null);
        System.out.println(sql);
//        "select * from iwatch where \"imei\" = '1232123EF' and \"time\" > '2020-08-21T14:01:54.494Z' tz('Asia/Shanghai')"
    }
}
