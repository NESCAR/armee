package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.CredentialsStaff;

import java.util.List;

public interface CredentialsStaffMapper {
    int insert(CredentialsStaff record);

    int insertSelective(CredentialsStaff record);

    CredentialsStaff selectByStaffId(Long staffId);

    List<CredentialsStaff> selectByStaffIdList(List<Long> staffIds);

    List<Long> deleteByCredentialsIdList(List<Long> credentialsId);

    Long deleteByCredentialId(Long credentialId);

    Long deleteByStaffId(Long staffId);
}