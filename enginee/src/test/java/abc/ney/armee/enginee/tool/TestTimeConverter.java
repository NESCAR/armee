package abc.ney.armee.enginee.tool;

import org.junit.Test;

import java.sql.Timestamp;

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
    }
}
