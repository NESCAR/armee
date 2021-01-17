package abc.ney.armee.enginee.data.influxdb;

import abc.ney.armee.enginee.net.http.HttpClient;
import abc.ney.armee.enginee.net.http.HttpClientIf;

import java.util.Iterator;
import java.util.Map;

/**
 * InfluxDB数据库数据发送器<br>
 * <pre>
 *     curl --request POST \
 *   --url http://localhost:9999/api/v2/write?org=my-org&bucket=example-bucket \
 *   --header 'Authorization: Token YOURAUTHTOKEN'
 * </pre>
 * @deprecated
 * @author neyzoter
 */
public class InfluxPoster {
    /**
     * V2版本和InfluxDB 1.8+兼容
     */
    private static final String INFLUXDB_V2_URL_FORMAT = "http://%s:%s/api/v2/write?org=%s&bucket=%s&precision=%s";
    /**
     * measurement,tag[,tag...] field[,field...] [timestamp]
     */
    private final static String INFLUXDB_V2_BODY_LINE_PROTO_FORMAT = "%s,%s %s %s";
    /**
     * prefix of the token, note the last char (space)
     */
    private final static String HEADERS_AUTHORIZATION_TOKEN_PREFIX = "Token ";
    /**
     * 多个line protocal的分隔符
     */
    private final static String MULTI_LINE_SPLITER = "\n";
    /**
     * rest template
     */
    private HttpClientIf httpClient;
    /**
     * Organization
     */
    private String org;
    /**
     * InfluxDB bucket
     */
    private String bucket;
    /**
     * precision
     */
    private String precision;
    /**
     * host ip
     */
    private String host;
    /**
     * port
     */
    private String port;

    /**
     * 验证信息<br>
     * 需要包含前缀HEADERS_AUTHORIZATION_TOKEN_PREFIX
     */
    private String token;

    /**
     * url : come from this.host port org bucket precision<br>
     * url 来自于以上参数，遵循INFLUXDB_V2_URL_FORMAT格式
     */
    private String url;

    private InfluxPoster(Builder builder) {
        org = builder.org;
        bucket = builder.bucket;
        precision = builder.precision;
        token = builder.token;
        host = builder.host;
        port = builder.port;
        url = builder.url;
        httpClient = new HttpClient();
    }

    /**
     * 发送数据
     * @param measurement 测量对象
     * @param tag 标签
     * @param field 数据
     * @param timestamp 时间戳
     * @return 结果
     */
    public String post(String measurement, Map<String, String> tag, Map<String, String> field, String timestamp) {
        String line = getInfluxLine(measurement, tag, field, timestamp);
        return httpClient.doPost(url, token, line);
    }

    /**
     * 发送多行
     * @param lines 符合Line Protocal的数组
     * @return 发送结果
     */
    public String post(String[] lines) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            sb.append(lines[i]);
            if (i != lines.length - 1) {
                sb.append(MULTI_LINE_SPLITER);
            }
        }
        return httpClient.doPost(url, token, sb.toString());
    }

    /**
     * 获取一条InfluxDB协议的Line
     * @param measurement 测量对象
     * @param tag 标签
     * @param field 数据
     * @param timestamp 时间戳
     * @return Line Protocal 数据点
     */
    public String getInfluxLine(String measurement, Map<String, String> tag, Map<String, String> field, String timestamp) {
        String tags = getTags(tag);
        String fields = getFields(field);
        return String.format(INFLUXDB_V2_BODY_LINE_PROTO_FORMAT, measurement, tags, fields, timestamp);
    }

    public static String getTags(Map<String, String> tag) {
        return getFields(tag);
    }

    /**
     * 获取数据Line
     * @param field 数据映射
     * @return 字符串格式的数据
     */
    public static String getFields(Map<String, String> field) {
        StringBuilder fieldSb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iter = field.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry= iter.next();
            fieldSb.append(entry.getKey());
            fieldSb.append("=");
            fieldSb.append(entry.getValue());
            // 如果不是最后一个
            if (iter.hasNext()) {
                fieldSb.append(",");
            }
        }
        return fieldSb.toString();
    }

    public static class Builder implements abc.ney.armee.enginee.cmif.Builder<InfluxPoster> {
        /**
         * Organization
         */
        private String org;
        /**
         * InfluxDB bucket
         */
        private String bucket;
        /**
         * precision
         */
        private String precision;
        /**
         * host ip
         */
        private String host;
        /**
         * port
         */
        private String port;

        /**
         * url : come from this.host port org bucket precision
         */
        private String url;

        /**
         * 验证信息
         */
        private String token;

        public Builder() {
        }

        public Builder org(String o) {
            this.org = o;
            return this;
        }

        public Builder bucket(String b) {
            this.bucket = b;
            return this;
        }

        public Builder precision(String p) {
            this.precision = p;
            return this;
        }

        public Builder host(String h) {
            this.host = h;
            return this;
        }

        public Builder port(String p) {
            this.port = p;
            return this;
        }

        public Builder token(String a) {
            this.token = HEADERS_AUTHORIZATION_TOKEN_PREFIX + a;
            return this;
        }

        private Builder url(String u) {
            this.url = u;
            return this;
        }

        /**
         * 清空
         */
        public void reset() {
            this.org = null;
            this.bucket = null;
            this.precision = null;
            this.token = null;
            this.host = null;
            this.port = null;
            this.url = null;
        }
        @Override
        public InfluxPoster build() {
            InfluxPoster poster = null;
            if (this.org != null && this.bucket != null && this.precision != null &&
                    this.host != null && this.port != null) {
                url(String.format(INFLUXDB_V2_URL_FORMAT, this.host, this.port, this.org, this.bucket, this.precision));
                poster = new InfluxPoster(this);
            }
            reset();
            return poster;
        }
    }
}
