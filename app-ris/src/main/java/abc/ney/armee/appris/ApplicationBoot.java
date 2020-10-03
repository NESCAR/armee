package abc.ney.armee.appris;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 应用
 * @author Charles Song
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2Doc
@MapperScan(basePackages = "abc.ney.armee.appris.dal.mapper.tms")
public class ApplicationBoot {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
