package abc.ney.armee.appris.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApolloConfiger {
    public static final Logger logger = LoggerFactory.getLogger(TestApolloConfiger.class);

    @Autowired
    ApolloConfiger configer;
    @Test
    public void testApolloConfiger () {
        System.out.println(configer.len);
    }


    @Before
    public void init() {
        System.out.println("TestApolloConfiger 开始-----------------");
    }

    @After
    public void after() {
        System.out.println("TestApolloConfiger 结束-----------------");
    }
}
