package abc.ney.armee.appris.biz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DataKafkaConsumerThreadPool {
    ThreadPoolExecutor tpe;
    @Autowired
    public DataKafkaConsumerThreadPool(DataKafkaConsumer dataKafkaConsumer) {
        log.info("Creating ThreadPoolExecutor");
        tpe = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        tpe.submit(dataKafkaConsumer);
        log.info("ThreadPoolExecutor Running");
    }
}
