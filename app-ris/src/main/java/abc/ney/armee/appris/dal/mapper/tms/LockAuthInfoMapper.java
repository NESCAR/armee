package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.LockAuthInfo;

public interface LockAuthInfoMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(LockAuthInfo record);

    int insertSelective(LockAuthInfo record);

    LockAuthInfo selectByPrimaryKey(Long gid);

    int updateByPrimaryKeySelective(LockAuthInfo record);

    int updateByPrimaryKey(LockAuthInfo record);
}