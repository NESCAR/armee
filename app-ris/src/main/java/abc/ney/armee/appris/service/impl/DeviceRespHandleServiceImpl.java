package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.service.DeviceRespHandleService;
import abc.ney.armee.appris.service.msg.handler.LockInfoSettingsHandler;
import abc.ney.armee.appris.service.msg.handler.UpMsgHandler;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DeviceRespHandleServiceImpl implements DeviceRespHandleService {
    private Map<Integer, UpMsgHandler> handlerMap;
    public DeviceRespHandleServiceImpl() {
        handlerMap = new HashMap<>();
        register();
    }

    /**
     * 登记所有handler
     */
    private void register() {
        // todo 确定MsgType
        handlerMap.put(Jt808MsgType.CLIENT_SETTINGS_UPDATE_INFO_UPLOAD.getMsgId(),
                new LockInfoSettingsHandler());
    }
    @Override
    public void process(MsgKey key, Object value) {
        Integer msgId = key.getMsgId();
        UpMsgHandler handler = handlerMap.get(msgId);
        handler.process(key, value);
    }
}
