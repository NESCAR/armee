package abc.ney.armee.appris.dal.mapper.tsmap;

import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.CANMsgRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
            map.remove(CAN_ID);
        }
        //重构了map的结构 将canid和candata中的数据放入新map
        //TODO 是否应该把canID去掉
//        Object canID=map.get("canID");

        Object canData = map.get("canData");
        Map<String,Object> newMap=MapMsgConvertUtils.objectToMap(canData);
//        newMap.put("canID",canID);
        log.info(getTime(msg) + " : " + newMap.toString());
        return newMap;
    }

    @Override
    public String getTime(Object msg) throws ClassCastException {
        checkType(msg);
        return ((CANMsgRequestMsgBody)msg).getCanTime();
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

