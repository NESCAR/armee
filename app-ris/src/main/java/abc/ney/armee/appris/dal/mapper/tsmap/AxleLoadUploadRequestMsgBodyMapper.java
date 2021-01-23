package abc.ney.armee.appris.dal.mapper.tsmap;

import abc.ney.armee.enginee.tool.TimeConverter;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.AxleLoadUploadRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.MileageUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AxleLoadUploadRequestMsgBody的映射器
 * @author neyzoter
 */
public class AxleLoadUploadRequestMsgBodyMapper implements InfluxMapper {

    public static final String TIME_VARIABLE_STR = "axleTime";
    @Override
    public Map<String, Object> fields(Object msg, boolean of) {
        checkType(msg);
        Map<String, Object> map = MapMsgConvertUtils.objectToMap(msg);
        String timeStr = getTime(msg);
        // 移除 传感数据外的无关数据
        if (of) {
            map.remove(TIME_VARIABLE_STR);
        }
        System.out.println("rfc time : " + timeStr);
        System.out.println("time serie data : " + map.toString());
        return MapMsgConvertUtils.objectToMap(msg);
    }

    @Override
    public String getTime(Object msg) {
        checkType(msg);
        return TimeConverter.bcdByte2RfcString(
                ((AxleLoadUploadRequestMsgBody)msg).getAxleTime());
    }

    @Override
    public Set<MsgType> getSupportedMsgTypes() {
        Set<MsgType> set = new HashSet<>();
        set.add(Jt808MsgType.CLIENT_AXLE_LOAD_INFO_UPLOAD);
        return set;
    }

    private void checkType(Object msg) throws ClassCastException {
        if (! (msg instanceof AxleLoadUploadRequestMsgBody)) {
            throw new ClassCastException("AxleLoadUploadRequestMsgBody required");
        }
    }
}
