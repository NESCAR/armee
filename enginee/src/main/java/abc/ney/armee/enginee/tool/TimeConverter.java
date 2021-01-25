package abc.ney.armee.enginee.tool;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 时间转化器
 * @author neyzoter
 */
public class TimeConverter {
    public static final int BCD_STRING_LENGTH = 12;
    public static final int BCD_BYTE_LENGTH = 6;

    // rcf时间格式
    public static final String RCF_TIME_FORMAT = "%s-%s-%sT%s:%s:%sZ";
    // sql timestamp格式
    public static final String SQL_TIMESTAMP_TIME_FORMAT = "%s-%s-%s %s:%s:%s";

    // 当前是21世纪，减去1
    public static final String YEAR_CENTURY_MINUS_ONE = "20";

    /**
     * sql时间戳转化为BCD String
     * @param timestamp 时间戳
     * @return 12字符长度的String，分别代表年-月-日-时-分-秒，比如210117120000表示21年1月17日12点00时00分
     */
    public static byte[] timestamp2BcdByte(Timestamp timestamp) {
        LocalDateTime ldt = timestamp.toLocalDateTime();
        byte[] res = new byte[6];
        //截取2位年    2021 -> 21
        res[0] = intToBcdByte(ldt.getYear());
        res[1] = intToBcdByte(ldt.getMonthValue());
        res[2] = intToBcdByte(ldt.getDayOfMonth());
        res[3] = intToBcdByte(ldt.getHour());
        res[4] = intToBcdByte(ldt.getMinute());
        res[5] = intToBcdByte(ldt.getSecond());
        return res;
    }

    /**
     * BCD Byte转化为sql时间戳
     * @param bcdTime BCD Byte时间
     * @return sql时间戳
     */
    public static Timestamp bcdByte2Timestamp(byte[] bcdTime) {
        String sqlTs = bcdByte2SqlTimestampString(bcdTime);
        return Timestamp.valueOf(sqlTs);
    }


    /**
     * BCD Byte转化为sql timstamp时间
     * @param bcdTime BCD Byte时间
     * @return sql timestamp
     */
    public static String bcdByte2SqlTimestampString(byte[] bcdTime) {
        return bcdByte2String(bcdTime, SQL_TIMESTAMP_TIME_FORMAT);
    }
    /**
     * BCD Byte转化为rfc时间
     * @param bcdTime BCD Byte时间
     * @return rfc时间戳
     */
    public static String bcdByte2RfcString(byte[] bcdTime) {
        return bcdByte2String(bcdTime, RCF_TIME_FORMAT);
    }

    /**
     * bcd byte转化为String类型
     * @param bcdTime bcd时间
     * @param format String格式，目前支持RCF、Timestamp等
     * @return String格式时间
     */
    private static String bcdByte2String(byte[] bcdTime, String format) {
        if (bcdTime.length != BCD_BYTE_LENGTH) {
            throw new IllegalArgumentException("BCD 时间长度错误");
        }
        return String.format(format,
                YEAR_CENTURY_MINUS_ONE + byteToBcdString(bcdTime[0]),
                byteToBcdString(bcdTime[1]),
                byteToBcdString(bcdTime[2]),
                byteToBcdString(bcdTime[3]),
                byteToBcdString(bcdTime[4]),
                byteToBcdString(bcdTime[5]));
    }

    /**
     * 将byte转化为BCD格式的String，比如in = 23（ox17），转化为字符串17
     * @param in 输入字符，高低4bits不能大于9
     * @return bcd String
     */
    private static String byteToBcdString(byte in) {
        byte tens = (byte) ((in & 0xF0) >>> 4);
        byte ones = (byte) (in & 0x0F);
        if (tens > 9 || ones > 9) {
            throw new NumberFormatException();
        }
        return String.format("%d%d", tens, ones);
    }

    /**
     * 取数字低2位，转化为BCD Byte
     * @param value 数字
     * @return BCD Byte，比如数字12转化为字符串0x12，数字1转化为字符串0x01
     */
    private static byte intToBcdByte(int value) {
        value %= 100;
        byte tens = (byte) (value / 10);
        byte ones = (byte) (value % 10);
        return (byte) (tens << 4 | ones);
    }
}
