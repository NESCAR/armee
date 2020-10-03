package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.Credentials;

import java.util.List;

public interface CredentialsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Credentials record);

    int insertSelective(Credentials record);

    List<Credentials> selectByName(String name);

    Credentials selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Credentials record);

    int updateByPrimaryKey(Credentials record);
}