package abc.ney.armee.appris.service.msg.handler;

import icu.nescar.armee.jet.broker.ext.producer.MsgKey;

/**
 * 上行非时序数据处理器
 * @author neyzoter
 */
public interface UpMsgHandler {
    /**
     * 处理上行非时序数据
     * @param key 数据键
     * @param value 数据消息体
     */
    void process(MsgKey key, Object value);
}
