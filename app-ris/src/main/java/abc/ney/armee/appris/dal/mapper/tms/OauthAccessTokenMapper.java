package abc.ney.armee.appris.dal.mapper.tms;

import abc.ney.armee.appris.dal.meta.po.OauthAccessTokenWithBLOBs;

public interface OauthAccessTokenMapper {
    int insert(OauthAccessTokenWithBLOBs record);

    int insertSelective(OauthAccessTokenWithBLOBs record);
}