package abc.ney.armee.appris.config;

import abc.ney.armee.appris.dal.mapper.msgmap.*;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
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
        imr.registerMapper(Jt808MsgType.CLIENT_AXLE_LOAD_INFO_UPLOAD, new AxleLoadUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_BRAKE_INFO_UPLOAD, new BrakeEventRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_MILEAGE_INFO_UPLOAD, new MileageUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_RSSEVENT_INFO_UPLOAD, new RssUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_TEBS_STATUS_INFO_UPLOAD, new TEBStatusRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_ALARM_INFO_UPLOAD, new AlarmUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_BINDING_INFO_UPLOAD, new BindingUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_LOCK_INFO_UPLOAD, new LockUploadRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_TEBS_ACCEPT_REPLY, new TEBSAcceptRequestMsgBodyMapper());
        imr.registerMapper(Jt808MsgType.CLIENT_UNLOCK_INFO_UPLOAD, new UnlockUploadRequestMsgBodyMapper());
    }
}
