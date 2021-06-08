package abc.ney.armee.enginee.data.influxdb;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther whale
 * @Date 2021/5/25
 */
public class TestInfluxDb {
    public static void main(String[] args) {
        InfluxConnection ic = new InfluxConnection(null, null, "http://influxdb:8086", "test", null);
        Map<String, String> tags = new HashMap<String, String>();
        tags.put("tag1", "标签值");
        Map<String, Object> fields = new HashMap<String, Object>();
        fields.put("field1", "String类型");
        // 数值型，InfluxDB的字段类型，由第一天插入的值得类型决定
        fields.put("field2", 3.141592657);
        // 时间使用毫秒为单位

        ic.insert("Trailer", tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }
}

