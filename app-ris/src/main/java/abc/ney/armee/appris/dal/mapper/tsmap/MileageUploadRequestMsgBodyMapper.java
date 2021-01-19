package abc.ney.armee.appris.dal.mapper.tsmap;

import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.MileageUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * MileageUploadRequestMsgBody的映射器
 * @author neyzoter
 */
public class MileageUploadRequestMsgBodyMapper implements InfluxMapper {

    public static final String time = "mileageTime";
    @Override
    public Map<String, Object> fields(Object msg, boolean of) {
        checkType(msg);
        Map<String, Object> map = MapMsgConvertUtils.objectToMap(msg);
        // 移除 传感数据外的无关数据
        if (of) {
            map.remove(time);
        }
        return map;
    }

    @Override
    public String getTime(Object msg) {
        checkType(msg);
        return ((MileageUploadRequestMsgBody)msg).getMileageTime();
    }

    @Override
    public Set<MsgType> getSupportedMsgTypes() {
        Set<MsgType> set = new HashSet<>();
        set.add(Jt808MsgType.CLIENT_MILEAGE_INFO_UPLOAD);
        return set;
    }

    private void checkType(Object msg) throws ClassCastException {
        if (! (msg instanceof MileageUploadRequestMsgBody)) {
            throw new ClassCastException("MileageUploadRequestMsgBody required");
        }
    }
}
