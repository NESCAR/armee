package abc.ney.armee.appris.service;

import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import org.influxdb.dto.QueryResult;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 挂车服务
 * @author neyzoter
 */
public interface TsdbService {
    /**
     * 插入数据
     * @param mk 测量对象Key
     * @param o 对象
     */
    void insert(MsgKey mk, Object o);

    /**
     * 查询数据
     * @param fields 数据类型
     * @param tag 数据标签
     * @param retentionPolicy 数据保留策略
     * @param st 开始时间
     * @param et 结束时间
     * @return 查询结果
     */
    QueryResult query(Set<String> fields, Map<String, String> tag, String retentionPolicy, String st, String et);
}
