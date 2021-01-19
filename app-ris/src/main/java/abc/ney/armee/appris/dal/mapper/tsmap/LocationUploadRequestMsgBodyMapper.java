package abc.ney.armee.appris.dal.mapper.tsmap;

import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 位置信息上传映射器
 * @author neyzoter
 */
public class LocationUploadRequestMsgBodyMapper implements InfluxMapper {
    public static final String time = "LocationTime";
    @Override
    public Map<String, Object> fields(Object msg, boolean of) {
        checkType(msg);
        Map<String, Object> map = MapMsgConvertUtils.objectToMap(msg);
        System.out.println(map.toString());
        // 移除 传感数据外的无关数据
        if (of) {
            map.remove(time);
        }
        return MapMsgConvertUtils.objectToMap(msg);
    }

    @Override
    public String getTime(Object msg) throws ClassCastException {
        checkType(msg);
        return ((LocationUploadRequestMsgBody)msg).getLocationTime();
    }

    @Override
    public Set<MsgType> getSupportedMsgTypes() {
        Set<MsgType> set = new HashSet<>();
        set.add(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD);
        return set;
    }

    private void checkType(Object msg) throws ClassCastException {
        if (! (msg instanceof LocationUploadRequestMsgBody)) {
            throw new ClassCastException("LocationUploadRequestMsgBody required");
        }
    }
}
