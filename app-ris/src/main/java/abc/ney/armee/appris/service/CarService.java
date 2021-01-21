package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.DeviceLockRecord;
import abc.ney.armee.appris.dal.meta.po.Staff;

import java.util.List;

public interface CarService {

    /**
     * 添加设备/汽车信息
     * @param device 设备/汽车信息
     * @return 是否插入成功
     */
    Boolean addDevice(Device device);

    /**
     * 查询设备信息
     * @param gid 设备gid
     * @return 设备信息
     */
    Device queryDeviceByGid(Long gid);

    /**
     * 根据imei号查询设备信息
     * @param imei 设备imei
     * @return 设备信息
     */
    Device queryDeviceByImei(String imei);

    /**
     * 查询所有设备信息
     * @return 设备列表
     */
    List<Device> queryDevice();

    /**
     * 查询驾驶员信息
     * @param gid 驾驶员gid
     * @return 驾驶员信息
     */
    Staff queryDriverByGid(Long gid);

    /**
     * 根据设备gid查询对应的司机
     * @param gid 设备gid
     * @return 驾驶员信息
     */
    Staff queryDriverByDeviceGid(Long gid);

    /**
     * 更新设备密码
     * @param gid 设备gid
     * @param psw 设备密码
     * @return 是否更新成功
     */
    boolean updateDevicePsw(Long gid, String psw);

    /**
     * 根据设备imei号，更新设备信息
     * @return 是否更新/匹配成功
     */
    boolean updateDeviceByImei(Device device);

    /**
     * 根据gid，更新设备信息
     * @param device
     * @return 是否更新/匹配成功
     */
    boolean updateDeviceByGid(Device device);

    /**
     * 根据gid，删除设备
     * @param deviceId 设备id
     */
    void deleteDeviceByGid(Long deviceId);

    /**
     * 设备是否上锁
     * @param gid 设备gid
     * @return 设备上锁是否成功
     */
    boolean isLocked(Long gid);

    /**
     * 添加设备上锁/解锁信息
     * @param record 上锁/解锁记录
     * @return 是否成功
     */
    boolean addLockRecord(DeviceLockRecord record);


}
