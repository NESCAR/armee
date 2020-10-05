package abc.ney.armee.appris.biz.util;

/**
 * 密码生成器
 * @author neyzoter
 */
public class PswGen {
    /**
     * 生成6位数字
     */
    public static String genBrakePsw() {
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }
}
