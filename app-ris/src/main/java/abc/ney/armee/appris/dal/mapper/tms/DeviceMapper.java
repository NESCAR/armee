package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.Device;

import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long gid);

    Device selectByImei(String imei);

    List<Device> select();

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    int updatePswByPrimaryKey(Long gid, String psw);

    int updateByImeiSelective(Device record);
}