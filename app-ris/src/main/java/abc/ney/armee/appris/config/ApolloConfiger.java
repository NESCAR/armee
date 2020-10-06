package abc.ney.armee.appris.config;

import abc.ney.armee.enginee.tool.Configer;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;

/**
 * apollo配置器<br/>
 * 所有的参数都要求可以toString
 * @author Charles Song
 * @date 2020-5-26
 */
//@Configuration
//@EnableApolloConfig({"dev"})
public class ApolloConfiger implements Configer {
    private static final Logger logger = LoggerFactory.getLogger(ApolloConfiger.class);

    @Value("${dev.config.len}")
    public String len;

    @Override
    public String getProp (String name){
        try {
            Field field = this.getClass().getDeclaredField(name);
            return String.valueOf(field.get(this));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public void updateProp (String addr) {
        logger.warn("Apollo Configer通过外部配置，不可以在程序内更改");
        logger.warn("Apollo Configer自动更新，不需要手动!");
    }
    @Override
    public void updateProp () {
        logger.warn("Apollo Configer自动更新，不需要手动!");
    }
}
