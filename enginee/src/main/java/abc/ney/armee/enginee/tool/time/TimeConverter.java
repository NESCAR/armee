package abc.ney.armee.enginee.tool.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转化器
 * @author neyzoter
 */
public class TimeConverter {

    public static final String RCF3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final long RCF3339_TO_LONG_ERROR = -1;

    /**
     * RCF3339转化为Long
     * @return long时间
     */
    public static long rcf3339ToLong(String rcf) {
        SimpleDateFormat df = new SimpleDateFormat(RCF3339_FORMAT);
        long time = RCF3339_TO_LONG_ERROR;
        try {
            time = df.parse(rcf).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    /**
     * RCF3339转化为Double
     * @return Double时间
     */
    public static double rcf3339ToDouble(String rcf) {
        return (double) rcf3339ToLong(rcf);
    }

    /**
     * Long转化为RCF3339
     * @param time long时间
     * @return RCF3339时间
     */
    public static String longToRcf3339(long time) {
        SimpleDateFormat df = new SimpleDateFormat(RCF3339_FORMAT);
        Date date = new Date(time);
        return df.format(date);
    }

    /**
     * Double转化为RCF3339
     * @param time double时间
     * @return RCF3339时间
     */
    public static String doubleToRcf3339(double time) {
        return longToRcf3339((long) time);
    }
}
