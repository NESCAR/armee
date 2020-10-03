package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.Device;

public interface DeviceMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long gid);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
}