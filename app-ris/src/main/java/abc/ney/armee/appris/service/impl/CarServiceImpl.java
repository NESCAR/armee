package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.DeviceMapper;
import abc.ney.armee.appris.dal.mapper.tms.StaffMapper;
import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.PoConstant;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.CarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CarServiceImpl implements CarService {
    @Resource
    DeviceMapper deviceMapper;
    @Resource
    StaffMapper staffMapper;

    @Override
    public Device queryDeviceByGid(Long gid) {
        return deviceMapper.selectByPrimaryKey(gid);
    }

    @Override
    public Staff queryDriverByGid(Long gid) {
        return staffMapper.selectByPrimaryKey(gid);
    }

    @Override
    public Staff queryDriverByDeviceGid(Long gid) {
        Device device = queryDeviceByGid(gid);
        Long driverGid = device.getDriverGid();
        return queryDriverByGid(driverGid);
    }

    @Override
    public boolean updateDevicePsw(Long gid, String psw) {
        return deviceMapper.updatePswByPrimaryKey(gid, psw) != ServiceConstant.MYSQL_NO_UPDATE_MATCHED_RTN;
    }

    @Override
    public boolean updateDeviceByImei(Device device) {
        if (device.getImei() == null) {
            return false;
        }
        return deviceMapper.updateByImeiSelective(device) != ServiceConstant.MYSQL_NO_UPDATE_MATCHED_RTN;
    }

    @Override
    public boolean isLocked(Long gid) {
        Device device = queryDeviceByGid(gid);
        return device.getLockStatus() == PoConstant.DEVICE_LOCKED;
    }

}
