package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.dto.StaffCredentialsDto;

import java.util.List;

public interface StaffCredentialsDtoMapper {
    List<StaffCredentialsDto> selectStaffCredentialsDtoByAuthority(String authority);
}
