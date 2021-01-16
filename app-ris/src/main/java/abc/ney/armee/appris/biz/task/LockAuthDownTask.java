package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;
import abc.ney.armee.appris.service.LockInfoManService;
import icu.nescar.armee.jet.broker.ext.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LockAuthDownTask implements Runnable {
    LockInfoManService lockInfoManService;
    CommandKafkaProducer commandKafkaProducer;
    @Override
    public void run() {
        List<LockAuthInfo> undownedLockAuthInfo = lockInfoManService.findDownloadInfo();
        // todo 根据授权信息下发进行信息发送
    }
    @Autowired
    public void setLockInfoManService(LockInfoManService lockInfoManService) {
        this.lockInfoManService = lockInfoManService;
    }

    @Autowired
    public void setCommandKafkaProducer(CommandKafkaProducer commandKafkaProducer) {
        this.commandKafkaProducer = commandKafkaProducer;
    }

}
