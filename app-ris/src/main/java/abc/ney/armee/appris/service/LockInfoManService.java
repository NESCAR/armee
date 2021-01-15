package abc.ney.armee.appris.service;

import java.sql.Timestamp;

/**
 * 上锁信息管理服务
 * @author neyzoter
 */
public interface LockInfoManService {

    /**
     * 检查司机授权开锁的开始时间和结束时间是否重合
     * @param deviceId 设备gid
     * @param st 开始时间
     * @param et 结束时间
     * @return 是否覆盖，true: 覆盖了，该时间范围非法; false: 未覆盖，该时间范围合法
     */
    boolean covered(Long deviceId, Timestamp st, Timestamp et);
}
