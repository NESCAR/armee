package abc.ney.armee.appris.service;

import icu.nescar.armee.jet.broker.ext.producer.MsgKey;

import java.util.Map;
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
}
