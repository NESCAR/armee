package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.OauthClientDetailsStaff;

public interface OauthClientDetailsStaffMapper {
    int insert(OauthClientDetailsStaff record);

    int insertSelective(OauthClientDetailsStaff record);
}