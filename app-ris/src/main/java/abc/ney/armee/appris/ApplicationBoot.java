package abc.ney.armee.appris;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用
 * @author Charles Song
 */
@SpringBootApplication
@MapperScan(basePackages = "abc.ney.armee.appris.dal.mapper.tms")
public class ApplicationBoot {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
