package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;
import abc.ney.armee.appris.dal.meta.vo.StaffVo;

import java.util.List;
import java.util.Map;

public interface AdminService {
    /**
     * 用户名是否存
     * @param name 用户名
     * @return 是否存在
     */
    boolean exist(String name);

    /**
     * 插入管理员
     * @param staffCredentialsDto 管理员信息
     * @return 插入结果
     */
    Map<String, Boolean> insertAdmin(StaffCredentialsDto staffCredentialsDto);

    /**
     * 插入司机
     * @param staffCredentialsDto 司机信息
     * @return 插入结果
     */
    Map<String, Boolean> insertDriver(StaffCredentialsDto staffCredentialsDto);
    /**
     * 查询管理员
     * @return 管理员列表
     */
    List<StaffVo> queryAdmin();
    /**
     * 查询司机
     * @return 司机列表
     */
    List<StaffVo> queryDriver();
    /**
     * 更新管理员信息<br>
     * 包括更新staff credential credentials_staff credentials_authorities
     * @param staffCredentialsDto 管理员信息
     * @return 管理员信息更新结果，<staff_id, true/false>
     */
    Map<Long, Boolean> updateAdmin(StaffCredentialsDto staffCredentialsDto);

    /**
     * 更新司机信息<br>
     * 包括staff credential credentials_staff credentials_authorities<br>
     * @apiNote 超级管理员和管理员均可操作，但是最多升级到管理员
     * @param staffCredentialsDto
     * @return
     */
    Map<Long, Boolean> updateDriver(StaffCredentialsDto staffCredentialsDto);

    /**
     * 删除管理员
     * @param ids 需要删除的管理员id
     * @return 删除结果
     */
    Map<Long, Boolean> deleteAdmins(List<Long> ids);
    /**
     * 删除司机
     * @param ids 需要删除的司机id
     * @return 删除结果
     */
    Map<Long, Boolean> deleteDrivers(List<Long> ids);

}
