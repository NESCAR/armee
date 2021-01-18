package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.service.DeviceRespHandleService;
import abc.ney.armee.appris.service.msg.handler.AuthUpdateSuccessRequestHandler;
import abc.ney.armee.appris.service.msg.handler.UpMsgHandler;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class DeviceRespHandleServiceImpl implements DeviceRespHandleService {
    private UpMsgHandler authUpdateSuccessRequestHandler;
    private UpMsgHandler lockStatusUploadRequestHandler;
    private final Map<Integer, UpMsgHandler> handlerMap;
    public DeviceRespHandleServiceImpl() {
        handlerMap = new HashMap<>();
    }

    @Override
    public void process(MsgKey key, Object value) {
        Integer msgId = key.getMsgId();
        UpMsgHandler handler = handlerMap.get(msgId);
        handler.process(key, value);
    }

    @Resource(name = "authUpdateSuccessRequestHandler")
    public void setAuthUpdateSuccessRequestHandler(UpMsgHandler authUpdateSuccessRequestHandler) {
        this.authUpdateSuccessRequestHandler = authUpdateSuccessRequestHandler;
    }
    @Resource(name = "lockStatusUploadRequestHandler")
    public void setLockStatusUploadRequestHandler(UpMsgHandler lockStatusUploadRequestHandler) {
        this.lockStatusUploadRequestHandler = lockStatusUploadRequestHandler;
    }
    @PostConstruct
    public void register() {
        this.handlerMap.put(Jt808MsgType.CLIENT_SETTINGS_UPDATE_INFO_UPLOAD.getMsgId(),
                this.authUpdateSuccessRequestHandler);
        this.handlerMap.put(Jt808MsgType.CLIENT_LOCK_INFO_UPLOAD.getMsgId(),
                this.lockStatusUploadRequestHandler);
    }
}
