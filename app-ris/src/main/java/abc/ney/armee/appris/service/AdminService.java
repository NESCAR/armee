package abc.ney.armee.appris.service;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;

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
}
