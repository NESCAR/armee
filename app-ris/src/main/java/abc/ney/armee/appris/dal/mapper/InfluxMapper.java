package abc.ney.armee.appris.dal.mapper;

import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.msg.RequestMsgBody;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 适用于时序数据库的值域映射器
 * @author neyzoter
 */
public interface InfluxMapper {
    /**
     * 从消息中解析出数据
     * @param msg 消息
     * @return 数据Map
     */
    Map<String, Object> fields(Object msg);

    /**
     * 获取时间
     * @param msg 消息
     * @return 时间
     */
    String getTime(Object msg);

    /**
     * 支持的MsgType
     * @return 支持的MsgType集合
     */
    default Set<MsgType> getSupportedMsgTypes() {
        return Collections.emptySet();
    }
}
