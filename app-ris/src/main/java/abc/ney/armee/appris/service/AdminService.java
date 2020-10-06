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
}
