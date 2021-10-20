package abc.ney.armee.appris.config;

import abc.ney.armee.appris.dal.mapper.tsmap.*;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.CANMsgRequestMsgBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * dal的配置
 * @author neyzoter
 */
@Component
public class DalConfiguration {
    InfluxMapperRegister imr;
    @Autowired
    public DalConfiguration(InfluxMapperRegister register) {
        imr = register;
        configInfluxMapperRegister();
    }

    /**
     * 配置InfluxMapper的登记器
     */
    private void configInfluxMapperRegister() {
        imr.registerMapper(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD, new LocationUploadRequestMsgBodyMapper());
//        imr.registerMapper(Jt808MsgType.CLIENT_AXLE_LOAD_INFO_UPLOAD, new AxleLoadUploadRequestMsgBodyMapper());
//        imr.registerMapper(Jt808MsgType.CLIENT_MILEAGE_INFO_UPLOAD, new MileageUploadRequestMsgBodyMapper());
//        imr.registerMapper(Jt808MsgType.CLIENT_RSSEVENT_INFO_UPLOAD, new RssUploadRequestMsgBodyMapper());
//        imr.registerMapper(Jt808MsgType.CLIENT_TEBS_STATUS_INFO_UPLOAD, new TEBStatusRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_CAN_INFO_UPLOAD,new CANRequestMsgBodyMapper());
    }
}
