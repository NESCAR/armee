package abc.ney.armee.appris.service;

import icu.nescar.armee.jet.broker.ext.producer.MsgKey;

/**
 * 设备响应信息处理服务
 * @author neyzoter
 */
public interface DeviceRespHandleService {

    /**
     * 处理所有数据类型
     * @param key 数据键
     * @param value 数据消息体
     */
    public void process(MsgKey key, Object value);
}
