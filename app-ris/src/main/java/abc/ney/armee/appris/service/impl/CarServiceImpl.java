package abc.ney.armee.appris.service.impl;

import abc.ney.armee.appris.dal.mapper.tms.DeviceLockRecordMapper;
import abc.ney.armee.appris.dal.mapper.tms.DeviceMapper;
import abc.ney.armee.appris.dal.mapper.tms.StaffMapper;
import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.DeviceLockRecord;
import abc.ney.armee.appris.dal.meta.po.PoConstant;
import abc.ney.armee.appris.dal.meta.po.Staff;
import abc.ney.armee.appris.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class CarServiceImpl implements CarService {
    @Resource
    DeviceMapper deviceMapper;
    @Resource
    StaffMapper staffMapper;
    @Resource
    DeviceLockRecordMapper deviceLockRecordMapper;

    @Override
    public Device queryDeviceByGid(Long gid) {
        return deviceMapper.selectByPrimaryKey(gid);
    }

    @Override
    public Device queryDeviceByImei(String imei) {
        return deviceMapper.selectByImei(imei);
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
            log.warn("Device IMEI 是Null，无法更新");
            return false;
        }
        return deviceMapper.updateByImeiSelective(device) != ServiceConstant.MYSQL_NO_UPDATE_MATCHED_RTN;
    }

    @Override
    public boolean updateDeviceByGid(Device device) {
        if (device.getGid() == null) {
            log.warn("Device Gid 是Null，无法更新");
            return false;
        }
        return deviceMapper.updateByPrimaryKeySelective(device) != ServiceConstant.MYSQL_INSERT_ERR_RTN;
    }

    @Override
    public boolean isLocked(Long gid) {
        Device device = queryDeviceByGid(gid);
        return device.getLockStatus() == PoConstant.DEVICE_LOCKED;
    }

    @Override
    public boolean addLockRecord(DeviceLockRecord record) {
        // 如果已经存在记录（device+time可以确定一条记录），不能添加则返回false
        if (deviceLockRecordMapper.countByDidTime(record) > 0) {
            return false;
        }
        return deviceLockRecordMapper.insertSelective(record) != ServiceConstant.MYSQL_INSERT_ERR_RTN;
    }
}
