package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class OauthRefreshToken implements Serializable {
    private String tokenId;

    private byte[] token;

    private byte[] authentication;

    private static final long serialVersionUID = 1L;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }
}