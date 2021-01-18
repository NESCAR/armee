package abc.ney.armee.enginee.tool;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 时间转化器
 * @author neyzoter
 */
public class TimeConverter {
    public static final int BCD_STRING_LENGTH = 12;
    // 当前是21世纪，减去1
    public static final String YEAR_CENTURY_MINUS_ONE = "20";
    /**
     *
     * @param timestamp
     * @return 12字符长度的String，分别代表年-月-日-时-分-秒，比如210117120000表示21年1月17日12点00时00分
     */
    public static String timestamp2BcdString(Timestamp timestamp) {
        LocalDateTime ldt = timestamp.toLocalDateTime();
        StringBuilder sb = new StringBuilder();
        //截取2位年    2021 -> 21
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
    public static Timestamp bcdString2Timestamp(String bcdTime) {
        if (bcdTime.length() != BCD_STRING_LENGTH) {
            throw new IllegalArgumentException("BCD 时间长度错误");
        }
        String timestampFormat = String.format("%s-%s-%s %s:%s:%s",
                YEAR_CENTURY_MINUS_ONE + bcdTime.substring(0,2),
                bcdTime.substring(2,4),
                bcdTime.substring(4,6),
                bcdTime.substring(6,8),
                bcdTime.substring(8,10),
                bcdTime.substring(10,12));
        return Timestamp.valueOf(timestampFormat);
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
