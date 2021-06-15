package abc.ney.armee.appris.biz.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @Auther whale
 * @Date 2021/6/15
 * 类型转换
 */
public class LongToBytes {
    /**
     * 将long类型转换为字节数组
     */
    public static byte[] longToByteArray(long l) throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        DataOutputStream dos =new DataOutputStream(bao);
        dos.writeLong(l);
        byte [] buf =bao.toByteArray();
        return buf;
    }
}



