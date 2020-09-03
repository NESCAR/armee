package abc.ney.armee.appris.config;

import abc.ney.armee.appris.dal.mapper.InfluxMapperRegister;
import abc.ney.armee.appris.dal.mapper.LocationUploadRequestMsgBodyMapper;
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
    }

    /**
     * 配置InfluxMapper的登记器
     */
    private void configInfluxMapperRegister() {
        imr.registerMapper(Jt808MsgType.CLIENT_LOCATION_INFO_UPLOAD, new LocationUploadRequestMsgBodyMapper());
    }
}
