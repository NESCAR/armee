package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.CredentialsAuthorities;

import java.util.Map;

public interface CredentialsAuthoritiesMapper {
    int insert(CredentialsAuthorities record);

    int insertSelective(CredentialsAuthorities record);

    CredentialsAuthorities selectByCredentialId(Long credentialId);

    Long updateAuthorityByCredentialId(CredentialsAuthorities credentialsAuthorities);

    Long deleteByCredentialId(Long credentialId);
}