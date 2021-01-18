package abc.ney.armee.appris.biz.task;

import abc.ney.armee.appris.service.TsdbService;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.ext.conf.ConfArguments;
import icu.nescar.armee.jet.broker.ext.consumer.kafka.KafkaConsumerImpl;
import icu.nescar.armee.jet.broker.ext.producer.MsgKey;
import icu.nescar.armee.jet.broker.util.SerializationUtil;
import io.github.hylexus.jt.data.msg.MsgType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Kafka消费者
 * @author neyzoter
 */
@Slf4j
@Component
public class DataKafkaConsumer extends KafkaConsumerImpl<ConsumerRecord<MsgKey, byte[]>> {
    public static final long POLL_DURATION = 1L;
    private boolean start = false;
    TsdbService tsi;
    @Autowired
    public DataKafkaConsumer(TsdbService tsdbService) {
        super(ConfArguments.KAFKA_TOPIC_DATA, "Data_", "1");
        log.info("Creating DataKafkaConsumer");
        log.info("TsdbService : " + tsdbService.toString());
        tsi = tsdbService;
        log.info("DataKafkaConsumer Task Created...");
        start = true;
    }

    @Override
    public void run() {
        while (start) {
            ConsumerRecords<MsgKey, byte[]> records = (ConsumerRecords<MsgKey, byte[]>)this.receive(Duration.ofSeconds(POLL_DURATION));
            for (TopicPartition partition : records.partitions()) {
                List<ConsumerRecord<MsgKey, byte[]>> partitionRecords = records.records(partition);
                for (ConsumerRecord<MsgKey, byte[]> record : partitionRecords) {
                    System.out.println("[ " + broker + " ] Received message: (" + record.key() + ", " + Arrays.toString(record.value()) + ") at offset " + record.offset() + " from partition " + record.partition());
                    try {
                        process(record);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 获取最近的offset
                long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                // 提交offset
                try {
                    System.out.println("Start sending offset [ " + lastOffset + " ]");
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)), Duration.ofSeconds(2));
                    System.out.println("Sent offset successfully");
                } catch (TimeoutException te) {
                    te.printStackTrace();
                }
            }
        }

    }

    public void process(ConsumerRecord<MsgKey, byte[]> cr) throws Exception {
        if (cr == null) {
            throw new Exception("ConsumerRecord is Null");
        }
        MsgKey mk = cr.key();
        int msgId = mk.getMsgId();
        byte[] byteMsg = cr.value();
        Optional<MsgType> jmt = Jt808MsgType.CLIENT_AUTH.parseFromInt(msgId);
        if (jmt.isPresent()) {
            try {
                log.info("Insert into InfluxDB...");
                tsi.insert(mk, SerializationUtil.deserialize(byteMsg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new Exception("No Jt808MsgType Matched");
        }
    }
    public void stop() {
        start = false;
    }
}
