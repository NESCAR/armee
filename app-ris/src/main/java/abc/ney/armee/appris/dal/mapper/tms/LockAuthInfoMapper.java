package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;

import java.util.List;

public interface LockAuthInfoMapper {
    int deleteByPrimaryKey(Long gid);

    int deleteByDeviceId(Long deviceId);

    int insert(LockAuthInfo record);

    int insertSelective(LockAuthInfo record);

    LockAuthInfo selectByPrimaryKey(Long gid);
    /**
     * 查询范围覆盖的个数
     * @param lockAuthInfo 查询的时间范围信息
     * @return 覆盖个数
     */
    int countCoveredInfo(LockAuthInfo lockAuthInfo);

    int updateByPrimaryKeySelective(LockAuthInfo record);

    int updateByPrimaryKey(LockAuthInfo record);

    /**
     * 找到在当前时间下，需要发送的时间。满足以下条件：<br>
     * （1）starttime - 10min < now()<br>
     * （2）endtime > now()
     * @return 所有设备的需要下发的授权时间信息
     */
    List<LockAuthInfo> selectDownloadInfoOfDevices();

    /**
     * 根据lockAuthInfo信息查询数据
     * @param lockAuthInfo  包含设备信息、开始时间、结束时间
     * @return 查询到达的信息
     */
    List<LockAuthInfo> selectByDidStEt(LockAuthInfo lockAuthInfo);
}