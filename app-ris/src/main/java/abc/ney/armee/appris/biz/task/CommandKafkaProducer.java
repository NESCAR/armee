package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.config.KafkaConfig;
import icu.nescar.armee.jet.broker.ext.producer.kafka.KafkaProducerImpl;
import icu.nescar.armee.jet.broker.ext.producer.kafka.msg.KafkaMsgKey;
import org.springframework.stereotype.Component;

@Component
public class CommandKafkaProducer extends KafkaProducerImpl<KafkaMsgKey, Object> {
    public CommandKafkaProducer() {
        super(KafkaConfig.KAFKA_TOPIC_CMD, KafkaConfig.IS_ASYNC);
    }
}


