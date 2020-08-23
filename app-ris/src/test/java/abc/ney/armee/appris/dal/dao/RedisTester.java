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
        strRedisTemplate.opsForValue().set("key", "val");
        System.out.println("Read from Redis : " + strRedisTemplate.opsForValue().get("key"));
    }
    @Test
    public void testHmSet() {
        strRedisTemplate.opsForHash().put("imei4Hm", "hashkey", "hashvalue");
        System.out.println("Read from Redis : " + strRedisTemplate.opsForHash().get("imei4Hm", "hashkey"));
    }
    @Test
    public void testZset() {
        long time = System.currentTimeMillis();
        strRedisTemplate.opsForZSet().add("imei", "{data:{123,1232}}", 10);
        strRedisTemplate.opsForZSet().add("imei", "{data:{321,41}}", 2);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,2}}", 3);
        strRedisTemplate.opsForZSet().add("imei", "{data:{1,221231231213213}}", time);
        Set<ZSetOperations.TypedTuple<String>> set = strRedisTemplate.opsForZSet().rangeByScoreWithScores("imei", 0, time + 1);
        if (set != null) {
            for (ZSetOperations.TypedTuple<String> val : set) {
                System.out.println("Read from Redis , Score : " + val.getScore() + " Value : " + val.getValue());
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
