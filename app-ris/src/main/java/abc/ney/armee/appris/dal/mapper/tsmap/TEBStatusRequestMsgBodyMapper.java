//package abc.ney.armee.appris.dal.mapper.tsmap;
//
//import abc.ney.armee.enginee.tool.TimeConverter;
//import icu.nescar.armee.jet.broker.config.Jt808MsgType;
//import io.github.hylexus.jt.data.msg.MsgType;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * TEBStatusRequestMsgBody的映射器
// * @author neyzoter
// */
//@Slf4j
//public class TEBStatusRequestMsgBodyMapper implements InfluxMapper {
//
//    public static final String TIME_VARIABLE_STR = "time";
//    @Override
//    public Map<String, Object> fields(Object msg, boolean of) {
//        checkType(msg);
//        Map<String, Object> map = MapMsgConvertUtils.objectToMap(msg);
//        // 移除 传感数据外的无关数据
//        if (of) {
//            map.remove(TIME_VARIABLE_STR);
//        }
//        log.info(getTime(msg) + " : " + map.toString());
//        return map;
//    }
//
//    @Override
//    public String getTime(Object msg) {
//        checkType(msg);
//        return TimeConverter.bcdByte2RfcString(
//                ((TEBStatusRequestMsgBody)msg).getTebsTime());
//    }
//
//    @Override
//    public Set<MsgType> getSupportedMsgTypes() {
//        Set<MsgType> set = new HashSet<>();
//        set.add(Jt808MsgType.CLIENT_TEBS_STATUS_INFO_UPLOAD);
//        return set;
//    }
//
//    private void checkType(Object msg) throws ClassCastException {
//        if (! (msg instanceof TEBStatusRequestMsgBody)) {
//            throw new ClassCastException("TEBStatusRequestMsgBody required");
//        }
//    }
//}
