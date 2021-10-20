package abc.ney.armee.appris.config;


import java.util.Arrays;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Auther whale
 * @Date 2021/6/8
 */
public class TestStringto6bytes {
    public static void main(String[] args) {
        String icCode = "12345";
        byte[] a=icCode.getBytes();
        System.out.println(icCode.length());
        byte[] b=new byte[6];
        Arrays.fill(b,(byte)'#');
        for(int i=0;i<a.length;i++){
            b[i]=a[i];
        }

        String icCode3=new String(b);
        System.out.println(icCode3);

    }

}

