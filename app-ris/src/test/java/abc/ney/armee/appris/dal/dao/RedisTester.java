package abc.ney.armee.appris.dal.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTester {

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Test
    public void testString() {
        /**
         * 说明：只会保留一个值
         */
        strRedisTemplate.opsForValue().set("key", "val2");
        strRedisTemplate.opsForValue().set("key", "val2");
        System.out.println("Read from Redis : " + strRedisTemplate.opsForValue().get("key"));
    }
    @Test
    public void testHmSet() {
        /**
         * 说明：只会保留一个值
         */
        strRedisTemplate.opsForHash().put("imei4Hm", "hashkey", "hashvalue1");
        strRedisTemplate.opsForHash().put("imei4Hm", "hashkey", "hashvalue2");
        strRedisTemplate.expire("imei4Hm", 5, TimeUnit.SECONDS);
        System.out.println("Read from Redis : " + strRedisTemplate.opsForHash().get("imei4Hm", "hashkey"));
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Read from Redis : " + strRedisTemplate.opsForHash().get("imei4Hm", "hashkey"));

    }
    @Test
    public void testZset() {
        long time = System.currentTimeMillis();
        /**
         * 说明：
         * ZSet会基于Score进行排序，每个元素都是K-V结构
         * 虽然key相同，但是Score不同，所以不会把旧的数据覆盖
         */
        strRedisTemplate.opsForZSet().add("imei", "{data:{123,1232}}", 10);
        strRedisTemplate.opsForZSet().add("imei", "{data:{321,41}}", 2);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,2}}", 3);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,221231231213213}}", time);
        strRedisTemplate.expire("imei", 5, TimeUnit.SECONDS);
        Set<ZSetOperations.TypedTuple<String>> set = strRedisTemplate.opsForZSet().rangeByScoreWithScores("imei", 0, time + 1);
        if (set != null) {
            for (ZSetOperations.TypedTuple<String> val : set) {
                System.out.println("Read from Redis , Score : " + val.getScore() + " Value : " + val.getValue());
            }
            System.out.println("Get " + set.size() + " datas..");
        } else {
            System.err.println("ERROR!!");
        }
        /**
         * 这些数据会被一块删除
         */
        strRedisTemplate.opsForZSet().add("imei", "{data:{123,1232}}", 10);
        strRedisTemplate.opsForZSet().add("imei", "{data:{321,41}}", 2);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,2}}", 3);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,221231231213213}}", time);
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Set<String> s = strRedisTemplate.opsForZSet().rangeByScore("imei", 0, time + 1, 1 ,1);
        if (set != null) {
            for (String val : s) {
                System.out.println("Read from Redis , Score : " + val);
            }
            System.out.println("Get " + set.size() + " datas..");
        } else {
            System.err.println("ERROR!!");
        }

    }
    @Test
    public void testSerializable() {
        serializableRedisTemplate.opsForValue().set("user", "user");
        String user = (String) serializableRedisTemplate.opsForValue().get("user");
        System.out.println("Read from Redis" + "user : " + user);
    }

}
