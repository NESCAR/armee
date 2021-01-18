package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.Staff;

public interface StaffMapper {
    int deleteByPrimaryKey(Long gid);

    int insert(Staff record);

    int insertSelective(Staff record);

    Staff selectByPrimaryKey(Long gid);

    Staff selectByIcCode(String icCode);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);
}