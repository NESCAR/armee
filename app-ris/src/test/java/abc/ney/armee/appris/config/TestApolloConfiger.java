package abc.ney.armee.appris.config;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestApolloConfiger {

    @Autowired
    ApolloConfiger configer;
    @Test
    public void testApolloConfiger () {
        int wait = 10;
        while (true) {
            wait --;
            if (wait < 0) {
                break;
            }
            System.out.println(configer.len);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
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
