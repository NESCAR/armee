package abc.ney.armee.appris.service;

import java.util.Set;

/**
 * 时序数据库缓存
 * @author neyzoter
 */
public interface TsdbCache {
    /**
     * 查询时间范围内的数据
     * @param tid 设备id
     * @param t1 开始时间，不包含
     * @param t2 结束时间，不包含
     * @return 数据
     */
    Set<String> getRange(String tid, long t1, long t2);
    /**
     * 查询时间范围内的数据
     * @param tid 设备id
     * @param s 开始时间，不包含
     * @return 数据
     */
    Set<String> getRange(String tid, long s);
}
