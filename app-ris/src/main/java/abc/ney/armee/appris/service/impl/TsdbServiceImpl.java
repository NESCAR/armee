package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tsmap.InfluxMapper;
import abc.ney.armee.appris.dal.mapper.tsmap.InfluxMapperRegister;
import abc.ney.armee.appris.service.TsdbService;
import abc.ney.armee.enginee.data.influxdb.InfluxConnection;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.util.TimeConverter;
import io.github.hylexus.jt.data.msg.MsgType;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * TSDB服务实例
 * @author neyzoter
 */
@Service
@Slf4j
public class TsdbServiceImpl implements TsdbService {
    /**
     * 测量对象 measurement对应 table
     */
    public static final String MEASUREMENT = "Trailer";
    /**
     * 终端ID的tag名称
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
            return new InfluxConnection(null, null, "http://influxdb:8086", "ris","ONE_MONTH");
        }
    };
    public String username;
    public String password;
    public String openurl;
    public String database;
    private InfluxMapperRegister influxMapperRegister;
    @Autowired
    public TsdbServiceImpl(InfluxMapperRegister imr) {
        influxMapperRegister = imr;
        log.info("TSDB Service Created");
        log.info("InfluxMapperRegister : " + imr.toString());
    }
    @Override
    public void insert(MsgKey mk, Object o) {
        InfluxConnection ic = threadLocal4Influx.get();
        System.out.println(ic.ping());
//        InfluxConnection ic = new InfluxConnection(null, null, "http://influxdb:8086",
//                "ris","ONE_MONTH");
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
                    long t = TimeConverter.RFC3339ToLong(im.getTime(o));
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

    @Override
    public QueryResult query(Set<String> fields, Map<String, String> tag, String retentionPolicy, String st, String et) {
        String sql = sqlGen(fields, tag, retentionPolicy, st, et);
        log.info("Query SQL : " + sql);
        InfluxConnection ic = threadLocal4Influx.get();
        QueryResult qr = ic.query(sql);
        return qr;
//        List<QueryResult.Result> res = qr.getResults();
//        for (QueryResult.Result r : res) {
//            List<QueryResult.Series> series = r.getSeries();
//            for (QueryResult.Series s : series) {
//                series.
//            }
//        }
//        System.out.println(qr.toString());
    }

    /**
     * 生成SQL语句
     * @param fields 查询的数据
     * @param tag 标签
     * @param retentionPolicy 数据保留策略
     * @param st 开始时间，可以设置为null
     * @param et 结束时间，可以设置为null
     * @return SQL语句
     */
    public String sqlGen(Set<String> fields, Map<String, String> tag, String retentionPolicy, String st, String et) {
        StringBuilder sb = new StringBuilder();
        if (fields == null || fields.isEmpty()) {
            sb.append("*");
        } else {
            for (String f : fields) {
                sb.append(f).append(",");
            }
            // 删掉最后一个逗号
            sb.deleteCharAt(sb.length() - 1);
        }
        String fieldStr = sb.toString();
        sb = new StringBuilder();
        if (tag != null) {
            Set<Map.Entry<String, String>> entrySet = tag.entrySet();
            int entrySize = entrySet.size();
            for (Map.Entry<String, String> entry : entrySet) {
                // 只要是时间约束不是null，入口还不是最后一个
                if (entrySize > 1 || st != null || et != null) {
                    sb.append("\"").append(entry.getKey()).append("\"").append(" = ")
                            .append("'").append(entry.getValue()).append("'").append(" and ");
                } else {
                    sb.append("\"").append(entry.getKey()).append("\"").append(" = ")
                            .append("'").append(entry.getValue()).append("'").append(" ");
                }
                entrySize--;
            }
        }
        if (st != null) {
            sb.append("\"time\" > ").append("'").append(st).append("'");
        }
        if (et != null) {
            if (st != null) {
                sb.append(" and ");
            }
            sb.append("\"time\" < ").append("'").append(et).append("'");
        }
        if (st != null || et != null) {
            sb.append(" tz('Asia/Shanghai')");
        }
        String cons = sb.length() > 0 ? "where " + sb.toString() : "";
        retentionPolicy = retentionPolicy == null || retentionPolicy.equals("") ? "" : String.format("\"%s\".", retentionPolicy);
        return String.format("select %s from %s%s %s", fieldStr, retentionPolicy, TsdbServiceImpl.MEASUREMENT, cons);
    }
}
