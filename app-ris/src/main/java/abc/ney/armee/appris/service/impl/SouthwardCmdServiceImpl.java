package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.service.SouthwardCmdService;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.conf.ConfArguments;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.ext.producer.Producer;
import icu.nescar.armee.jet.broker.ext.producer.kafka.KafkaProducerImpl;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import icu.nescar.armee.jet.broker.msg.resp.RespLockInfoSettings;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class SouthwardCmdServiceImpl implements SouthwardCmdService {
    Producer<KafkaMsgKey, Object> producer;
    ReentrantLock lock;
    public SouthwardCmdServiceImpl() {
        producer = new KafkaProducerImpl<>(ConfArguments.KAFKA_TOPIC_CMD, false);
        lock = new ReentrantLock();
    }
    @Override
    public void sendLockInfo(String carId, String driverId, String psw, String st, String et) {
        RespLockInfoSettings rlis = new RespLockInfoSettings();
        // TODO
        rlis.setCarID(1);rlis.setDriverID(1);
//        rlis.setCarID(carId);rlis.setDriverID(driverId);
        rlis.setLockTimeStart(st);rlis.setLockTimeEnd(et);rlis.setPassword(psw);
        lock.lock();
        try {
            KafkaMsgKey mk = new KafkaMsgKey( carId, Jt808MsgType.RESP_LOCK_INFO_SETTINGS.getMsgId());
            producer.send(mk, rlis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
