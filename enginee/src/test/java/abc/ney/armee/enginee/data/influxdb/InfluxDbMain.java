package abc.ney.armee.enginee.data.influxdb;

import org.influxdb.dto.QueryResult;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试InfluxDB数据写入
 * @author neyzoter
 */
public class InfluxDbMain {
    public static void main(String[] args) {
        testInfluxConnection();
    }

    /**
     * 测试InfluxConnection
     */
    public static void testInfluxConnection() {
        String measurement = "iwatch";
        InfluxConnection influxConnection = new InfluxConnection(null, null,"http://influxdb:8086", "ris","ONE_DAY");
        HashMap<String, String> tag = new HashMap<>();
        tag.put("imei", "1232123EF");
        HashMap<String, Object> field = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            long time = System.currentTimeMillis();
            field.clear();
            field.put("heartbeat", Math.abs(70 * Math.sin((double) time)));
            field.put("speed", Math.abs(100 * Math.cos((double) time)));
            System.out.println("Millis : " + time);
            influxConnection.insert(measurement, tag, field, time, TimeUnit.MILLISECONDS);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        QueryResult qr = influxConnection.query("select * from iwatch where \"imei\" = '1232123EF' and \"time\" > '2020-08-21T14:01:54.494Z' tz('Asia/Shanghai')");
        List<QueryResult.Result> list = qr.getResults();
        System.out.println("QueryResult Size : " + list.size());
        for (QueryResult.Result r : list) {
            System.out.println(r);
        }
        System.out.println();

        int resultNum = 0;
        for (QueryResult.Result r : list) {
            resultNum++;
            System.out.println("-------- " + resultNum + " --------");
            List<QueryResult.Series> l = r.getSeries();
            System.out.println("List Size : " + list.size());
            System.out.println(l);
            System.out.println();

            for (QueryResult.Series s : l) {
                System.out.println(s);
            }
        }


    }

    /**
     * 测试InfluxPoster
     */
    public static void testInfluxPoster() {
        String token = "9awRYjBTnFxXVFRjwhxNugaonkOQQ_x22IYBh9RnLE-0uZU_UZsB2kmYMM_ucvo0L2tSqpYLjMjMtE75dzwwtw==";
        String org = "zju";
        String bucket = "ris";
        String host = "influxdb";
        String port = "9999";
        String precision = "ms";
        String measurement = "watch";
        InfluxPoster influxPoster = new InfluxPoster.Builder().org(org).bucket(bucket).token(token)
                .host(host).port(port).precision(precision).build();
        HashMap<String, String> tag = new HashMap<>();
        tag.put("imei", "1232123EF");
        HashMap<String, String> field = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            long time = System.currentTimeMillis();
            field.clear();
            field.put("heartbeat", Double.toString(Math.abs(70 * Math.sin((double) time))));
            field.put("speed", Double.toString(Math.abs(100 * Math.cos((double) time))));
            System.out.println("Millis : " + time);
            influxPoster.post(measurement, tag, field, Long.toString(time));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}