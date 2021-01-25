package abc.ney.armee.enginee.tool;

import java.sql.Timestamp;
import java.util.Arrays;

public class TestTimeConverter {
    public static void main(String[] args) {
        testTimestamp2BcdString();
    }
    public static void testTimestamp2BcdString() {
        // to 23 3 21 23 9 35
        Timestamp ts = Timestamp.valueOf("2017-3-15 17:09:23");
        System.out.println(Arrays.toString(TimeConverter.timestamp2BcdByte(ts)));

        // 2017-3-15 17:09:23
        byte[] tbFormat = {23, 3, 21, 23, 9, 35};
        System.out.println(TimeConverter.bcdByte2RfcString(tbFormat));
        System.out.println(TimeConverter.bcdByte2SqlTimestampString(tbFormat));
        System.out.println(TimeConverter.bcdByte2Timestamp(tbFormat));

    }
}
