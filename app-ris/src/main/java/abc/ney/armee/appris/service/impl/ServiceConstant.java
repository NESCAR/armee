package abc.ney.armee.appris.service.impl;

/**
 * 服务常数
 * @author neyzoter
 */
public class ServiceConstant {
    public static final int MYSQL_INSERT_ERR_RTN = -1;

    /**
     * update操作没有匹配到任何行的返回结果<br>
     * update返回匹配条数或者更新条数由mysql url参数useAffectedRows决定
     */
    public static final int MYSQL_NO_UPDATE_MATCHED_RTN = 0;
}
