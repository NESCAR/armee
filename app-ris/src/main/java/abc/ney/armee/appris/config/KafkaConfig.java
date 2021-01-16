package abc.ney.armee.appris.config;

public class KafkaConfig {
    /**
     * Kafka主题：命令<br>
     * Jt808消费，并发送给对应到终端；业务生产到Kafka
     */
    public static final String KAFKA_TOPIC_CMD = "Command";

    public static final boolean IS_ASYNC = true;
}
