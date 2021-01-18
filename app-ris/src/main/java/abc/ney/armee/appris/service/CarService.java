package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.po.Device;
import abc.ney.armee.appris.dal.meta.po.Staff;

public interface CarService {

    /**
     * 查询设备信息
     * @param gid 设备gid
     * @return 设备信息
     */
    Device queryDeviceByGid(Long gid);

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
     * 根据设备imei号，更新设备信息<br>
     * @return 是否更新/匹配成功
     */
    boolean updateDeviceByImei(Device device);

    /**
     * 设备是否上锁
     * @param gid 设备gid
     * @return 设备上锁是否成功
     */
    boolean isLocked(Long gid);



}
