package abc.ney.armee.enginee.tool.time;

import org.junit.Test;

/**
 * 测试时间转化器
 * @author neyzoter
 */
public class TimeConverterTester {
    @Test
    public void testRcf3339ToLong() {
        String date = "2020-9-4T12:10:20Z";
        long time = TimeConverter.rcf3339ToLong(date);
        System.out.println(time);
    }

    @Test
    public void testLongToRcf3339() {
        long time = 1599192620000L;
        String rcf = TimeConverter.longToRcf3339(time);
        System.out.println(rcf);
    }
}
