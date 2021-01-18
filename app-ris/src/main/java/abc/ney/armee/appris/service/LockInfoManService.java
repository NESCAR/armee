package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 上锁信息管理服务
 * @author neyzoter
 */
public interface LockInfoManService {

    /**
     * 检查司机授权开锁的开始时间和结束时间是否重合
     * @param lockAuthInfo 授权信息
     * @return 是否覆盖，true: 覆盖了，该时间范围非法; false: 未覆盖，该时间范围合法
     */
    Boolean covered(LockAuthInfo lockAuthInfo);

    /**
     * 插入授权信息
     * @param lockAuthInfo 授权信息
     * @return 是否成功插入
     */
    Boolean addLockAuthInfo(LockAuthInfo lockAuthInfo);

    /**
     * 找到在当前时间下，需要发送的时间。满足以下条件：<br>
     * （1）starttime - 10min < now()<br>
     * （2）endtime > now()
     * @return 所有设备的需要下发的授权时间信息
     */
    List<LockAuthInfo> findDownloadInfo();

    /**
     * 根据设备id、授权开始时间、授权结束时间来找到对应的记录
     * @param lockAuthInfo 授权信息
     * @return 授权信息
     */
    List<LockAuthInfo> findLockInfoByDidStEt(LockAuthInfo lockAuthInfo);

    /**
     * 根据授权信息的gid更新记录
     * @param lockAuthInfo 授权信息，gid不能null
     * @return 更新成功结果
     */
    Boolean updateLockInfoByPrimaryKey(LockAuthInfo lockAuthInfo);

}
