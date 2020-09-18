package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.config.ApolloConfiger;
import abc.ney.armee.appris.dal.mapper.msgmap.InfluxMapper;
import abc.ney.armee.appris.dal.mapper.msgmap.InfluxMapperRegister;
import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.enginee.data.influxdb.InfluxConnection;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.util.TimeConverter;
import io.github.hylexus.jt.data.msg.MsgType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * TSDB服务实例
 * @author neyzoter
 */
@Component
@Slf4j
public class TsdbServiceImpl implements TsdbService {
    /**
     * 测量对象
     */
    public static final String MEASUREMENT = "Trailer";
    /**
     * 中断ID的tag名称
     */
    public static final String TERMINAL_ID_TAG = "terminalId";
    /**
     * 单位为毫秒
     */
    public static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    /**
     * 数据只保留传感数据，而不要把时间等信息作为fields写入到influx
     */
    public static final boolean REMAIN_FIELDS_ONLY = true;

    /**
     * influxdb 连接池
     */
    public static ThreadLocal<InfluxConnection> threadLocal4Influx = new ThreadLocal<InfluxConnection>() {
        @Override
        public InfluxConnection initialValue () {
            return new InfluxConnection(null, null, "http://influxdb:8086", "ris","ONE_DAY");
        }
    };
    public String username;
    public String password;
    public String openurl;
    public String database;
    public String retentionPolicy;
    private InfluxMapperRegister influxMapperRegister;
    @Autowired
    public TsdbServiceImpl(ApolloConfiger ac, InfluxMapperRegister imr) {
        influxMapperRegister = imr;
    }
    @Override
    public void insert(MsgKey mk, Object o) {
        InfluxConnection ic = threadLocal4Influx.get();
//        InfluxConnection ic = new InfluxConnection(null, null, "http://influxdb:8086",
//                "ris","ONE_DAY");
        int msgid = mk.getMsgId();
        Optional<MsgType> mt = Jt808MsgType.CLIENT_AUTH.parseFromInt(msgid);
        if (mt.isPresent()) {
            try {
                Optional<InfluxMapper> imOptional = influxMapperRegister.getMapper(mt.get());
                if (imOptional.isPresent()) {
                    InfluxMapper im = imOptional.get();
                    Map<String, Object> fields = im.fields(o, REMAIN_FIELDS_ONLY);
                    Map<String, String> tags = new HashMap<>();
//                    long t = Long.parseLong(im.getTime(o));
                    long t = TimeConverter.rcf3339ToLong(im.getTime(o));
                    tags.put(TERMINAL_ID_TAG, mk.getTerminalId());
                    ic.insert(MEASUREMENT, tags, fields, t, TIME_UNIT);
                } else {
                    log.warn("Influx Mapper Not Found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            log.warn("MsgType Not Found");
        }
    }
}
