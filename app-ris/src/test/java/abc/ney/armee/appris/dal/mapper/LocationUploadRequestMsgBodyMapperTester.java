package abc.ney.armee.appris.dal.mapper;

import abc.ney.armee.appris.dal.mapper.msgmap.LocationUploadRequestMsgBodyMapper;
import abc.ney.armee.appris.dal.mapper.msgmap.MapMsgConvertUtils;
import icu.nescar.armee.jet.broker.config.Jt808MsgType;
import icu.nescar.armee.jet.broker.msg.req.LocationUploadRequestMsgBody;
import io.github.hylexus.jt.data.msg.MsgType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.Set;

/**
 * 测试LocationUploadRequestMsgBodyMapper
 * @author neyzoter
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationUploadRequestMsgBodyMapperTester {
    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    @Test
    public void testFields() {
        LocationUploadRequestMsgBodyMapper mapper = new LocationUploadRequestMsgBodyMapper();
        LocationUploadRequestMsgBody body = new LocationUploadRequestMsgBody();
        body.setTime(String.valueOf(System.currentTimeMillis()));
        body.setStatus(10);body.setSpeed((short) 70);body.setLng(30.0);
        body.setLat(23.0);body.setHeight((short) 12);body.setDirection((short) 12);
        body.setWarningFlag(0);
        String json = MapMsgConvertUtils.objectToJson(body);
        System.out.println("json : " + json);
        String time = mapper.getTime(body);
        Optional<MsgType> jt808MsgType = Jt808MsgType.CLIENT_AUTH.parseFromInt(512);
        String key;
        if (jt808MsgType.isPresent()) {
            key = String.valueOf(jt808MsgType.get().getMsgId());
            strRedisTemplate.opsForZSet().add(key, json, Double.parseDouble(time));
            Set<ZSetOperations.TypedTuple<String>> set = strRedisTemplate.opsForZSet()
                    .rangeByScoreWithScores(key, Long.parseLong(time) - 10, Long.parseLong(time) + 10);
            if (set != null) {
                for (ZSetOperations.TypedTuple<String> val : set) {
                    System.out.println("Read from Redis , Score : " + val.getScore() + " Value : " + val.getValue());
                }
                System.out.println("Get " + set.size() + " datas..");
            } else {
                System.err.println("ERROR!!");
            }
        }

    }
}
