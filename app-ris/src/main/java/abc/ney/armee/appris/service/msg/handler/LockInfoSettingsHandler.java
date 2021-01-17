package abc.ney.armee.appris.service.msg.handler;

import icu.nescar.armee.jet.broker.ext.producer.MsgKey;

public class LockInfoSettingsHandler implements UpMsgHandler {
    @Override
    public void process(MsgKey key, Object value) {
        // todo 根据消息进行处理处理
        // 这个LockInfoSettings是什么？为什么是消息下发，但是又是在resp库中
    }
}
