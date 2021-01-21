package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.DeviceLockRecord;

public interface DeviceLockRecordMapper {
    int deleteByPrimaryKey(Long gid);

    int deleteByDeviceId(Long deviceId);

    int insert(DeviceLockRecord record);

    int insertSelective(DeviceLockRecord record);

    DeviceLockRecord selectByPrimaryKey(Long gid);

    int updateByPrimaryKeySelective(DeviceLockRecord record);

    int updateByPrimaryKey(DeviceLockRecord record);

    int countByDidTime(DeviceLockRecord record);
}