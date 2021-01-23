package abc.ney.armee.enginee.tool;

import java.sql.Timestamp;
import java.util.Arrays;

public class TestTimeConverter {
    public static void main(String[] args) {
        testTimestamp2BcdString();
    }
    public static void testTimestamp2BcdString() {
        Timestamp ts = Timestamp.valueOf("2020-1-2 12:00:00");
        System.out.println(TimeConverter.timestamp2BcdString(ts));

        // 2021-01-17 12:01:09
        String tsFormat = "210117120109";
        System.out.println(TimeConverter.bcdString2Timestamp(tsFormat));

        System.out.println(Arrays.toString(TimeConverter.timestamp2BcdByte(ts)));

        // 2021-01-17 12:01:09
        byte[] tbFormat = {21, 1, 17, 12, 1, 9};
        System.out.println(TimeConverter.bcdByte2Timestamp(tbFormat));
    }
}
