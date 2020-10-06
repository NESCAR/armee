package abc.ney.armee.appris.dal.meta.po;

import java.io.Serializable;

public class OauthAccessToken implements Serializable {
    private String tokenId;

    private String authenticationId;

    private String userName;

    private String clientId;

    private String refreshToken;

    private static final long serialVersionUID = 1L;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId == null ? null : tokenId.trim();
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId == null ? null : authenticationId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken == null ? null : refreshToken.trim();
    }
}