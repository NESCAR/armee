package abc.ney.armee.appris;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 应用
 * @author Charles Song
 * @date 2020-5-26
 */
@SpringBootApplication
public class ApplicationBoot {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationBoot.class);
    public static void main(String[] args) {
        logger.info("ApplicationBoot Application Start...");
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
