package abc.ney.armee.appris.dal.mapper;

import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.AxleLoadUploadRequestMsgBody;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;
import io.github.hylexus.jt808.msg.RequestMsgBody;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AxleLoadUploadRequestMsgBody的映射器
 * @author neyzoter
 */
public class AxleLoadUploadRequestMsgBodyMapper implements InfluxMapper {

    @Override
    public Map<String, Object> mapFields(Object msg) {
        checkType(msg);
        return MapMsgConvertUtils.objectToMap(msg);
    }

    @Override
    public String jsonFields(Object msg) {
        checkType(msg);
        return MapMsgConvertUtils.objectToJson(msg);
    }

    @Override
    public String getTime(Object msg) {
        checkType(msg);
        return ((AxleLoadUploadRequestMsgBody)msg).getTime();
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
