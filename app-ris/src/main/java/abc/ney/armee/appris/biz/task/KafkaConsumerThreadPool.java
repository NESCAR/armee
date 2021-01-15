package abc.ney.armee.appris.biz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Kafka消费者线程池
 * @author neyzoter
 */
@Slf4j
@Component
public class KafkaConsumerThreadPool {
    ThreadPoolExecutor tpe;
    @Autowired
    public KafkaConsumerThreadPool(KafkaConsumer kafkaConsumer) {
        log.info("Creating ThreadPoolExecutor");
        tpe = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        tpe.submit(kafkaConsumer);
        log.info("ThreadPoolExecutor Running");
    }
}
