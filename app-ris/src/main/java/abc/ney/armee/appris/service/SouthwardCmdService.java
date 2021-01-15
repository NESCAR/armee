package abc.ney.armee.appris.service;

/**
 * 南向命令服务
 * @author neyzoter
 */
public interface SouthwardCmdService {
    /**
     * 下发上锁信息
     * @param carId 汽车设备imei
     * @param driverId 司机id
     * @param icCode ic密码
     * @param st 开始时间
     * @param et 结束时间
     */
    void sendLockInfo(String carId, String driverId, String icCode, String st, String et);
}
