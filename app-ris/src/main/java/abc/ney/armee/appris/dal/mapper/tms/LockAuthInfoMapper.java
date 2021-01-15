package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;

public interface LockAuthInfoMapper {
    int deleteByPrimaryKey(Long gid);

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
}