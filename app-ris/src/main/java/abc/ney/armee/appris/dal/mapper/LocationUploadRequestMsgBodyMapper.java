package abc.ney.armee.appris.dal.mapper;

import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.msg.RequestMsgBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 位置信息上传映射器
 * @author neyzoter
 */
public class LocationUploadRequestMsgBodyMapper implements InfluxMapper {
    public static final String direction = "direction";
    public static final String warningFlag = "warningFlag";
    public static final String status = "status";
    public static final String lat = "lat";
    public static final String lng = "lng";
    public static final String height = "height";
    public static final String speed = "speed";
    @Override
    public Map<String, Object> fields(Object msg) throws ClassCastException {
        checkType(msg);
        HashMap<String, Object> map = new HashMap<>();
        map.put(direction, ((LocationUploadRequestMsgBody) msg).getDirection());
        map.put(warningFlag, ((LocationUploadRequestMsgBody) msg).getWarningFlag());
        map.put(status, ((LocationUploadRequestMsgBody) msg).getStatus());
        map.put(lat, ((LocationUploadRequestMsgBody) msg).getLat());
        map.put(lng, ((LocationUploadRequestMsgBody) msg).getLng());
        map.put(height, ((LocationUploadRequestMsgBody) msg).getHeight());
        map.put(speed, ((LocationUploadRequestMsgBody) msg).getSpeed());
        return map;
    }

    @Override
    public String getTime(Object msg) throws ClassCastException {
        checkType(msg);
        return ((LocationUploadRequestMsgBody)msg).getTime();
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
