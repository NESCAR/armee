package abc.ney.armee.appris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用
 * (scanBasePackages = "abc.ney.armee.appris")
 * @author Charles Song
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "abc.ney.armee.appris.dal.mapper.tms")
public class ApplicationBoot {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
