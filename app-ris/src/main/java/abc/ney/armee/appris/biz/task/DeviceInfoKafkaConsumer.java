package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.service.CarService;
import icu.nescar.armee.jet.broker.ext.conf.ConfArguments;
import icu.nescar.armee.jet.broker.ext.consumer.kafka.KafkaConsumerImpl;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class DeviceInfoKafkaConsumer extends KafkaConsumerImpl<ConsumerRecord<MsgKey, byte[]>> {
    public static final long POLL_DURATION = 1L;
    private boolean start = false;
    CarService carService;
    @Autowired
    public DeviceInfoKafkaConsumer(CarService carService) {
        super(ConfArguments.KAFKA_TOPIC_DATA);
        log.info("Creating DeviceInfoKafkaConsumer");
        log.info("carService : " + carService.toString());
        this.carService = carService;
        log.info("KafkaConsumer Task Created...");
        start = true;
    }
}
