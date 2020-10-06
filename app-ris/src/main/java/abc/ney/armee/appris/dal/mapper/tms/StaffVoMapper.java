package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.vo.StaffVo;

import java.util.List;

public interface StaffVoMapper {
    List<StaffVo> selectStaffVoByAuthorityId(Long id);
}
