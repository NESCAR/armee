package abc.ney.armee.appris.dal.mapper.tsmap;

import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.CANMsgRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther whale
 * @Date 2021/4/30
 */
@Slf4j
public class CANRequestMsgBodyMapper implements InfluxMapper {
    public static final String TIME_VARIABLE_STR = "canTime";
    public static final String MSG_ITEM="msgItem";
    public static final String CAN_ID="canID";
    @Override
    public Map<String, Object> fields(Object msg, boolean of) {
        checkType(msg);
        Map<String, Object> map = MapMsgConvertUtils.objectToMap(msg);
        // 移除 无关数据。对于can来说 时间和数据项都是无用的。
        if (of) {
            map.remove(TIME_VARIABLE_STR);
            map.remove(MSG_ITEM);
        }
        //TODO 针对canmsg body将其map数据解析出来返回所需的can数据
        CANMsgRequestMsgBody canData = (CANMsgRequestMsgBody)map.get("canData");
        map.put("canData",MapMsgConvertUtils.objectToMap(canData));
        log.info(getTime(msg) + " : " + map.toString());
        return map;
    }

    @Override
    public String getTime(Object msg) throws ClassCastException {
        checkType(msg);
        return TimeConverter.bcdByte2RfcString(
                ((CANMsgRequestMsgBody)msg).getCanTime());
    }

    @Override
    public Set<MsgType> getSupportedMsgTypes() {
        Set<MsgType> set = new HashSet<>();
        set.add(Jt808MsgType.CLIENT_CAN_INFO_UPLOAD);
        return set;
    }

    private void checkType(Object msg) throws ClassCastException {
        if (! (msg instanceof CANMsgRequestMsgBody)) {
            throw new ClassCastException("CANMsgRequestMsgBody required");
        }
    }
}

