package abc.ney.armee.enginee.tool;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 时间转化器
 * @author neyzoter
 */
public class TimeConverter {
    public static String timestamp2BcdString(Timestamp timestamp) {
        LocalDateTime ldt = timestamp.toLocalDateTime();
        StringBuilder sb = new StringBuilder();
        //截取2位年
        String year = intToBcdString(ldt.getYear());
        String month = intToBcdString(ldt.getMonthValue());
        String day = intToBcdString(ldt.getDayOfMonth());
        String hour = intToBcdString(ldt.getHour());
        String min = intToBcdString(ldt.getMinute());
        String sec = intToBcdString(ldt.getSecond());
        sb.append(year);sb.append(month);sb.append(day);
        sb.append(hour);sb.append(min);sb.append(sec);
        return sb.toString();
    }

    /**
     * 取数字低2位，转化为BCD String
     * @param value 数字
     * @return BCD String，比如数字12转化为字符串"12"，数字1转化为字符串"01"
     */
    private static String intToBcdString(int value) {
        value %= 100;
        String valueStr = Integer.toString(value);
        valueStr = valueStr.length() > 1 ? valueStr : "0" + valueStr;
        return valueStr;
    }
}
