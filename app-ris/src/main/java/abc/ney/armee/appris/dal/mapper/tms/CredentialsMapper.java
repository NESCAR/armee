package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.Credentials;

import java.util.List;

public interface CredentialsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Credentials record);

    int insertSelective(Credentials record);

    Credentials selectByPrimaryKey(Long id);

    List<Credentials> selectByNameLimit1(String name);

    int updateByPrimaryKeySelective(Credentials record);

    int updateByPrimaryKey(Credentials record);
}