package abc.ney.armee.appris.dal.mapper.tsmap;

import io.github.hylexus.jt.data.msg.MsgType;

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
     * @param of only fields，只包含传感数据，而不包含其他的数据（比如时间）
     * @return map数据
     */
    Map<String, Object> fields(Object msg, boolean of);

    /**
     * 获取RFC格式时间
     * @param msg 消息
     * @return RFC格式时间
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
